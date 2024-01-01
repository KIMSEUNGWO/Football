package football.start.allOfFootball.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
@RequiredArgsConstructor
public class MessageConvert {

    private final MessageSource messageSource;

    public String getErrorMessage(FieldError fieldError) {
        String[] codes = fieldError.getCodes();
        for (String code : codes) {
            try {
                return messageSource.getMessage(code, null, null);
            } catch (NoSuchMessageException e) {
                continue;
            }
        }
        return messageSource.getMessage(fieldError.getDefaultMessage(), null, null);
    }

}
