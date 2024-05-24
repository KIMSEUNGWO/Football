package football.common.domain;

import football.common.domain.score.Score;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "MYRESULT")
public class MyResult {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myResultId;

    @ManyToOne
    @JoinColumn(name = "ORDERS_ID")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "SCORE_ID")
    private Score score;


}
