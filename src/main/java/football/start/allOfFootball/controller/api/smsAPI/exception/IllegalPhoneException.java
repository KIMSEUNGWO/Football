package football.start.allOfFootball.controller.api.smsAPI.exception;

import football.start.allOfFootball.dto.json.JsonDefault;
import org.springframework.http.HttpStatus;

public class IllegalPhoneException extends CertificationException{

    public IllegalPhoneException(HttpStatus code, JsonDefault jsonDefault) {
        super(code, jsonDefault);
    }


}
