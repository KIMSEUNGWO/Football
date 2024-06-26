package football.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    USER("ROLE_USER", "사용자"),
    MANAGER("ROLE_MANAGER", "매니저"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String roleName;
    private final String title;

}
