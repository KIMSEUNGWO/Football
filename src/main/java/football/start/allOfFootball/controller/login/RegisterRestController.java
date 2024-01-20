package football.start.allOfFootball.controller.login;

import football.start.allOfFootball.common.MessageConvert;
import football.start.allOfFootball.dto.json.JsonDefault;
import football.start.allOfFootball.service.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
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
    private final MessageConvert messageConvert;
    @PostMapping("/register/check")
    public ResponseEntity<JsonDefault> distinctEmail(@RequestBody @Validated EmailDto emailDto, BindingResult bindingResult) {
        System.out.println("emailDto = " + emailDto);
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            return new ResponseEntity<>(new JsonDefault("error", messageConvert.getErrorMessage(fieldError)), HttpStatus.BAD_REQUEST);
        }
        return registerService.validEmail(emailDto);
    }
}
