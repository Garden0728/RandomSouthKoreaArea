package Service.RandomArea.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/vi/profiles")
public class ProfileController {
    private final Environment environment; //횐경 설정 값을 다루는 environment Bean을 의존성 주입을 받는다.
    @GetMapping
    public String profile(){
        final List<String> profiles = Arrays.asList(environment.getActiveProfiles());
        final List<String> prodProfiles = Arrays.asList("prod1", "prod2");
        final String defaultProfile = profiles.get(0);
         return Arrays.stream(environment.getActiveProfiles())
                .filter(prodProfiles::contains)
                .findAny()
                .orElse(defaultProfile);
    }
}
