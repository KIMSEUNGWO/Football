package football.start.allOfFootball.controller.api.smsAPI.exception;

import football.start.allOfFootball.dto.json.JsonDefault;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Getter
public class CertificationException extends IOException {

    private final HttpStatus code;
    private final JsonDefault jsonDefault;


    public CertificationException(HttpStatus code, JsonDefault jsonDefault) {
        this.code = code;
        this.jsonDefault = jsonDefault;
    }
}
