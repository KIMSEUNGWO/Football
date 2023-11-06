package football.start.allOfFootball.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "PLAY_CANCEL")
@SequenceGenerator(name = "SEQ_PLAY_CANCEL", sequenceName = "SEQ_PLAY_CANCEL", allocationSize = 1)
public class PlayCancel {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLAY_CANCEL")
    private Long playCancelId;
    private Long playId;

    private String playCancelReason;
}
