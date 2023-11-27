package football.start.allOfFootball.controller.login;

import football.start.allOfFootball.common.ResultMessage;
import football.start.allOfFootball.service.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RegisterRestController {

    private final RegisterService registerService;

    @PostMapping("/register/check")
    public Map<String, String> distinctEmail(@RequestBody EmailDto emailDto, BindingResult bindingResult) {
        System.out.println("emailDto = " + emailDto);
        Map<String, String> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            ResultMessage.bindingMessageToMap(map, bindingResult);
            return map;
        }
        registerService.validEmail(emailDto, map);
        return map;
    }
}
