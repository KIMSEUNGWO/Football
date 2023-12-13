package football.start.allOfFootball.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorLevel {

    LEVEL_1(0),
    LEVEL_2(1),
    LEVEL_3(2),
    LEVEL_4(3);

    private int level;

}
