package football.start.allOfFootball.exception;

import lombok.Getter;

@Getter
public class NotEnoughCashException extends OrderException {

    private final String redirectURI;

    public NotEnoughCashException(String redirectURI) {
        this.redirectURI = redirectURI;
    }
}
