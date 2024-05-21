package football.login.controller;

import football.common.common.MessageConvert;
import football.login.dto.EmailDto;
import football.common.dto.json.JsonDefault;
import football.login.service.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static football.common.consts.Constant.ERROR;

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
            return ResponseEntity.badRequest().body(new JsonDefault(ERROR, messageConvert.getErrorMessage(fieldError)));
        }
        return registerService.validEmail(emailDto);
    }
}
