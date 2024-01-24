package football.start.allOfFootball.controller.api.smsAPI.exception;

import football.start.allOfFootball.dto.json.JsonDefault;
import org.springframework.http.HttpStatus;

public class NotFoundCertificationNumberException extends CertificationException{


    public NotFoundCertificationNumberException(HttpStatus code, JsonDefault jsonDefault) {
        super(code, jsonDefault);
    }
}
