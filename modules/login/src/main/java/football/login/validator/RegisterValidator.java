package football.login.validator;

import football.api.sms.service.SmsService;
import football.api.sms.exception.CertificationException;
import football.common.formatter.DateFormatter;
import football.login.dto.RegisterDto;
import football.login.service.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.View;

import java.time.DateTimeException;
import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterValidator implements Validator {

    private final SmsService smsService;
    private final RegisterService registerService;
    private final View error;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterDto dto = (RegisterDto) target;
        log.info("RegisterValidator.validate 실행");
        System.out.println("dto = " + dto);

        validEmail(dto, errors);
        validPassword(dto, errors);
        validBirthday(dto, errors);
        validCertification(dto, errors);

    }

    private void validEmail(RegisterDto dto, Errors errors) {
        boolean distinct = registerService.distinctEmail(dto.getEmail());
        if (distinct) {
            errors.rejectValue("email", null, "중복된 이메일입니다.");
        }
    }

    private void validPassword(RegisterDto dto, Errors errors) {
        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            errors.rejectValue("passwordCheck", null, "비밀번호가 일치하지 않습니다.");
        }
    }

    private void validCertification(RegisterDto dto, Errors errors) {
        try {
            dto.setPhone(dto.getPhone().replace("-", ""));
            smsService.isValid(dto.getPhone(), dto.getPhoneCheck());
        } catch (CertificationException e) {
            errors.rejectValue("phoneCheck", "NotFound", "인증번호가 일치하지 않습니다.");
        }
    }

    private void validBirthday(RegisterDto dto, Errors errors) {
        String birthday = dto.getBirthday();

        try {
            LocalDate now = LocalDate.now();
            LocalDate birth = DateFormatter.toLocalDate(birthday, "yyyyMMdd");
            if (birth.isAfter(now)) {
                invalidBirthday(errors);
            }
        } catch (DateTimeException e) {
            invalidBirthday(errors);
        }
    }

    private void invalidBirthday(Errors errors) {
        errors.rejectValue("birthday", "Pattern", "유효하지 않은 생년월일입니다.");
    }
}
