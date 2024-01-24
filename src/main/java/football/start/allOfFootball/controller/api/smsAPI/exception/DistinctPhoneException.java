package football.start.allOfFootball.controller.api.smsAPI.exception;

import football.start.allOfFootball.dto.json.JsonDefault;
import org.springframework.http.HttpStatus;

public class DistinctPhoneException extends CertificationException{

    public DistinctPhoneException(HttpStatus code, JsonDefault jsonDefault) {
        super(code, jsonDefault);
    }
}
