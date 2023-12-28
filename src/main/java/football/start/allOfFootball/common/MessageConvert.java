package football.start.allOfFootball.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
@RequiredArgsConstructor
public class MessageConvert {

    private final MessageSource messageSource;

    public String getErrorMessage(FieldError fieldError) {
        return messageSource.getMessage(fieldError.getCodes()[0], null, fieldError.getDefaultMessage(), null);
    }

}
