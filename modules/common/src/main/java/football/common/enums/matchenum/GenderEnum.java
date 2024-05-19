package football.common.enums.matchenum;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GenderEnum {

    전체("남녀무관", "mix"),
    남자("남자만", "male"),
    여자("여자만", "female");

    public final String matchInfo;
    public final String className;

    public static GenderEnum getGender(String gender) {
        if ("male".equals(gender)) {
            return 남자;
        }
        return 여자;
    }

    GenderEnum(String matchInfo, String className) {
        this.matchInfo = matchInfo;
        this.className = className;
    }
}
