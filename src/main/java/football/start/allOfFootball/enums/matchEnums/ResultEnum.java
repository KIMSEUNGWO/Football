package football.start.allOfFootball.enums.matchEnums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum {

    승("WIN", "win"),
    패("LOSW", "lose"),
    무("DRAW", "draw");

    private final String display;
    private final String className;
}
