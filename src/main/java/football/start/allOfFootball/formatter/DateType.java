package football.start.allOfFootball.formatter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DateType {

    M월_D일_W요일_HH_00,
    YYYY년_M월_D일_W,
    YYYY년_MM월_DD일,
    YYYY년_M월_D일,
    YYYY_MM_DD;
}
