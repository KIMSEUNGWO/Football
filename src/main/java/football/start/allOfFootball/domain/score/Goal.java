package football.start.allOfFootball.domain.score;

import football.start.allOfFootball.domain.Orders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "GOAL")
@SequenceGenerator(name = "SEQ_GOAL", sequenceName = "SEQ_GOAL_ID", allocationSize = 1)
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GOAL")
    private Long goalId;

    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "ordersId")
    private Orders orders;

    private Integer time;

}
