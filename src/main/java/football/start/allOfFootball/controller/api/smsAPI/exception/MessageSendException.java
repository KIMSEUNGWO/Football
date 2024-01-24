package football.start.allOfFootball.controller.api.smsAPI.exception;


import java.io.IOException;

public class MessageSendException extends IOException {


    public MessageSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
