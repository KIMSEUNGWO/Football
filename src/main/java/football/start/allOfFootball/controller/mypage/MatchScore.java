package football.start.allOfFootball.controller.mypage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchScore {

    private int all;
    private int win;
    private int draw;
    private int lose;

    @Override
    public String toString() {
        String template = "#s전 #s승 #s무 #패";
        return template.formatted(all, win, draw, lose);
    }

}
