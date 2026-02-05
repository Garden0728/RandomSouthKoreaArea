package Service.RandomArea.domain.verification;

import Service.RandomArea.Config.API.SlackConfig;
import Service.RandomArea.Config.UploadConfig;
import Service.RandomArea.exception.CustomException;
import Service.RandomArea.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationService {
    private static final long MAX_FILE_SIZE = 5L * 1024L * 1024L;
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png");

    private final UploadConfig uploadConfig;
    private final SlackConfig slackConfig;
    private final RestTemplate restTemplate;

    public void submit(
        String region,
        String contact,
        MultipartFile capture,
        MultipartFile travel,
        HttpServletRequest request
    ) throws IOException {
        if (region == null || region.isBlank()) {
            throw new IllegalArgumentException("지역을 입력해주세요.");
        }
        if (contact == null || contact.isBlank()) {
            throw new IllegalArgumentException("연락처(전화번호 또는 인스타 아이디)를 입력해주세요.");
        }
        if (capture == null || capture.isEmpty()) {
            throw new IllegalArgumentException("캡처 이미지를 업로드해주세요.");
        }
        if (travel == null || travel.isEmpty()) {
            throw new IllegalArgumentException("방문 인증샷을 업로드해주세요.");
        }

        validateFile(capture, "캡처");
        validateFile(travel, "방문 인증샷");

        String uploadDir = uploadConfig.getUploadDir();
        Path baseDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(baseDir);

        Path capturePath = storeFile(baseDir, capture, "capture");
        Path travelPath = storeFile(baseDir, travel, "travel");

        String captureUrl = buildPublicUrl(request, capturePath.getFileName().toString());
        String travelUrl = buildPublicUrl(request, travelPath.getFileName().toString());

        String webhookUrl = slackConfig.getWebhookUrl();
        if (webhookUrl == null || webhookUrl.isBlank()) {
            log.warn("Slack webhook URL is not configured.");
            return;
        }

        String message = """
            :camera: 방문 인증 업로드
            - 지역: %s
            - 연락처: %s
            - 캡처: %s
            - 인증샷: %s
            - 시간: %s
            """.formatted(region.trim(), contact.trim(), captureUrl, travelUrl, LocalDateTime.now());

        try {
            restTemplate.postForEntity(webhookUrl, Map.of("text", message), String.class);
        } catch (Exception ex) {
            log.warn("Slack 전송 실패", ex);
            throw new CustomException(
                ErrorCode.SLACK_SEND_FAIL,
                " 슬랙 전송에 실패했습니다. 파일은 삭제되었으니 다시 제출해주세요."
            );
        } finally {
            Files.deleteIfExists(capturePath);
            Files.deleteIfExists(travelPath);
        }
    }

    private Path storeFile(Path baseDir, MultipartFile file, String prefix) throws IOException {
        String originalName = file.getOriginalFilename();
        String extension = "";
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        String filename = "%s_%s%s".formatted(prefix, UUID.randomUUID(), extension);
        Path target = baseDir.resolve(filename);
        file.transferTo(target.toFile());
        return target;
    }

    private String buildPublicUrl(HttpServletRequest request, String filename) {
        String baseUrl = uploadConfig.getBaseUrl();
        if (baseUrl != null && !baseUrl.isBlank()) {
            return baseUrl.replaceAll("/$", "") + "/uploads/" + filename;
        }
        String scheme = request.getScheme();
        String host = request.getServerName();
        int port = request.getServerPort();
        String portPart = (port == 80 || port == 443) ? "" : ":" + port;
        return scheme + "://" + host + portPart + "/uploads/" + filename;
    }

    private void validateFile(MultipartFile file, String label) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(label + " 파일 용량은 5MB 미만이어야 합니다.");
        }
        String originalName = file.getOriginalFilename();
        if (originalName == null || !originalName.contains(".")) {
            throw new IllegalArgumentException(label + " 파일 확장자를 확인해주세요.");
        }
        String extension = originalName.substring(originalName.lastIndexOf('.') + 1).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(label + " 파일은 jpg, jpeg, png만 가능합니다.");
        }
    }
}
