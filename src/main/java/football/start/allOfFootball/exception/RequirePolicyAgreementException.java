package football.start.allOfFootball.exception;

import lombok.Getter;

@Getter
public class RequirePolicyAgreementException extends OrderException {
    private final String redirectURI;

    public RequirePolicyAgreementException(String redirectURI) {
        this.redirectURI = redirectURI;
    }
}
