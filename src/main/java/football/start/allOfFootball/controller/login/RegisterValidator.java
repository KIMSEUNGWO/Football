package football.start.allOfFootball.controller.login;

import football.api.sms.service.SmsService;
import football.api.sms.exception.CertificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterValidator implements Validator {

    private final SmsService smsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterDto dto = (RegisterDto) target;
        log.info("RegisterValidator.validate 실행");
        System.out.println("dto = " + dto);

        validBirthday(dto, errors);
        validCertification(dto, errors);

    }

    private void validCertification(RegisterDto dto, Errors errors) {
        try {
            smsService.isValid(dto.getPhone(), dto.getPhoneCheck());
        } catch (CertificationException e) {
            errors.rejectValue("phoneCheck", "NotFound", "인증번호가 일치하지않습니다.");
        }
    }

    private void validBirthday(RegisterDto dto, Errors errors) {
        String birthday = dto.getBirthday();

        if (birthday == null || birthday.length() != 6) {
            invalidBirthday(errors);
            return;
        }

        int year = Integer.parseInt(birthday.substring(0, 2));
        int month = Integer.parseInt(birthday.substring(2, 4));
        int day = Integer.parseInt(birthday.substring(4, 6));

        LocalDate now = LocalDate.now();

        int nowYear = now.getYear();
        int compareYear = nowYear % 100;
        year += (year <= compareYear) ? 2000 : 1900;

        if (nowYear < year || (month < 1 || month > 12)) {
            invalidBirthday(errors);
            return;
        }

        int lastDay = LocalDate.of(year, month, 1).lengthOfMonth();
        if (day < 1 || day > lastDay) {
            invalidBirthday(errors);
            return;
        }
        LocalDate birth = LocalDate.of(year, month, day);
        if (birth.isAfter(now)) {
            invalidBirthday(errors);
            return;
        }
    }

    private void invalidBirthday(Errors errors) {
        errors.rejectValue("birthday", "Pattern", "잘못된 생년월일입니다.");
    }
}
