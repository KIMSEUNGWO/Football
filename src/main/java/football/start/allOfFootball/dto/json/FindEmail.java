package football.start.allOfFootball.dto.json;

import football.common.dto.JsonDefault;
import football.internal.database.enums.SocialEnum;


public class FindEmail extends JsonDefault {

    private SocialEnum social;
    private String email;

    public FindEmail(String result, String message, SocialEnum social, String email) {
        super(result, message);
        this.social = social;
        this.email = email;
    }
    public FindEmail(String result, String message) {
        super(result, message);
    }

    public FindEmail(String message) {
        super(message);
    }

    public SocialEnum getSocial() {
        return social;
    }

    public String getEmail() {
        return email;
    }
}
