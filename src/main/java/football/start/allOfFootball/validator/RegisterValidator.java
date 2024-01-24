package football.start.allOfFootball.validator;

import football.start.allOfFootball.controller.api.smsAPI.SmsRequest;
import football.start.allOfFootball.controller.api.smsAPI.SmsService;
import football.start.allOfFootball.controller.api.smsAPI.exception.CertificationException;
import football.start.allOfFootball.controller.login.RegisterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterValidator implements Validator {

    private final Pattern p = Pattern.compile("[0-9]{2}");
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
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setPhone(dto.getPhone());
        smsRequest.setCertificationNumber(dto.getPhoneCheck());
        try {
            smsService.regexPhone(smsRequest);
            smsService.distinctPhone(smsRequest);
            smsService.isValid(smsRequest);
        } catch (CertificationException e) {
            errors.rejectValue("phoneCheck", "NotFound", e.getJsonDefault().getMessage());
        }
    }

    private void validBirthday(RegisterDto dto, Errors errors) {
        String birthday = dto.getBirthday();
        a:if (birthday.length() == 6) {
            Matcher m = p.matcher(birthday);
            if (!m.find()) {
                break a;
            }
            int year = Integer.parseInt(m.group());
            if (!m.find()) {
                break a;
            }
            int month = Integer.parseInt(m.group());
            if (!m.find()) {
                break a;
            }
            int day = Integer.parseInt(m.group());
            int nowYear = LocalDate.now().getYear();
            int compareYear = nowYear % 100;
            if (year <= compareYear) {
                year += 2000;
            } else {
                year += 1900;
            }
            if (nowYear >= year) {
                if (month > 0 && month <= 12) {
                    int lastDay = LocalDate.of(year, month, 1).lengthOfMonth();
                    if (day > 0 && day <= lastDay) {
                        LocalDate now = LocalDate.now();
                        LocalDate birth = LocalDate.of(year, month, day);
                        if (birth.isBefore(now)) {
                            return;
                        }
                    }
                }
            }
        }
        errors.rejectValue("birthday", "Pattern", "생년월일 입력 정보가 잘못되었습니다.");
    }
}
