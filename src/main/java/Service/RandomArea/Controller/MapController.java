package Service.RandomArea.Controller;

import Service.RandomArea.Config.API.KakaoConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@RequiredArgsConstructor
@Controller
public class MapController {
    private final KakaoConfig kakaoConfig;
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("javascript_key", kakaoConfig.getKakaoJavascriptKey());
        return "map/Kakaomap";
    }
    @GetMapping("/random")
    public String random(Model model) {
        model.addAttribute("javascript_key", kakaoConfig.getKakaoJavascriptKey());
        return "map/random";
    }

    @GetMapping("/event")
    public String event(Model model) {
        model.addAttribute("javascript_key", kakaoConfig.getKakaoJavascriptKey());
        return "map/event";
    }


}
