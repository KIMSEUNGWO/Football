package football.start.allOfFootball.controller.api.smsAPI.exception;


public class MessageSendException extends RuntimeException {


    public MessageSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
