package Service.RandomArea.Controller;

import Service.RandomArea.domain.verification.VerificationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import Service.RandomArea.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/verification")
@RequiredArgsConstructor
public class VerificationController {
    private final VerificationService verificationService;

    @PostMapping("/submit")
    public Map<String, String> submit(
        @RequestParam("region") String region,
        @RequestParam("contact") String contact,
        @RequestParam("capture") MultipartFile capture,
        @RequestParam("travel") MultipartFile travel,
        HttpServletRequest request
    ) throws IOException {
        verificationService.submit(region, contact, capture, travel, request);
        return Map.of("message", "업로드가 완료되었습니다.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", exception.getMessage()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> handleCustomException(CustomException exception) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
            .body(Map.of(
                "message", exception.getMessage(),
                "code", String.valueOf(exception.getCoderInterface().getCode())
            ));
    }
}
