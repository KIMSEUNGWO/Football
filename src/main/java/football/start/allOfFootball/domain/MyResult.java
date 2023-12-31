package football.start.allOfFootball.domain;

import football.start.allOfFootball.domain.score.Score;
import football.start.allOfFootball.enums.TeamEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "MYRESULT")
@SequenceGenerator(name = "SEQ_MYRESULT", sequenceName = "SEQ_MYRESULT_ID", allocationSize = 1)
public class MyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MYRESULT")
    private Long myResultId;

    @ManyToOne
    @JoinColumn(name = "ordersId")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "scoreId")
    private Score score;


}
