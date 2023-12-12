package football.start.allOfFootball.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GenderEnum {

    전체("남녀무관", "mix"),
    남자("남자만", "male"),
    여자("여자만", "female");

    public String matchInfo;
    public String className;

}
