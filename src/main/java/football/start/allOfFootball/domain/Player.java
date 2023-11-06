package football.start.allOfFootball.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "PLAYER")
@SequenceGenerator(name = "SEQ_PLAYER", sequenceName = "SEQ_PLAYER_ID")
 class Player {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLAYER")
    private Long playerId;

    private Long playId;
    private Long memberId;
}
