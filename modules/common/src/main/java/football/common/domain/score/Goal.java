package football.common.domain.score;

import football.common.domain.Orders;
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
public class Goal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;

    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "ordersId")
    private Orders orders;

    private Integer time;

}
