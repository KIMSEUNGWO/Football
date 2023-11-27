package football.start.allOfFootball.domain;

import football.start.allOfFootball.enums.ResultEnum;
import football.start.allOfFootball.enums.TeamEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ORDERS")
@SequenceGenerator(name = "SEQ_ORDERS", sequenceName = "SEQ_ORDERS_ID", allocationSize = 1)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDERS")
    private Long ordersId;

    @ManyToOne
    @JoinColumn(name = "playId")
    private Play play;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "playResultId")
    private PlayResult playResult;

    @Enumerated(EnumType.STRING)
    private TeamEnum team;

    @Enumerated(EnumType.STRING)
    private ResultEnum result;
}
