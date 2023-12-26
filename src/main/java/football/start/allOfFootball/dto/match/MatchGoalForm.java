package football.start.allOfFootball.dto.match;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MatchGoalForm {

    private String name;
    private Integer time;
}
