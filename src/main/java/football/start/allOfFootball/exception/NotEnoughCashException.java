package football.start.allOfFootball.exception;

import lombok.Getter;

@Getter
public class NotEnoughCashException extends Exception {

    private final String redirectURI;

    public NotEnoughCashException(String redirectURI) {
        this.redirectURI = redirectURI;
    }
}
