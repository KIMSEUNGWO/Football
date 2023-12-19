package football.start.allOfFootball.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "MATCH_CANCEL")
@SequenceGenerator(name = "SEQ_MATCH_CANCEL", sequenceName = "SEQ_MATCH_CANCEL", allocationSize = 1)
public class MatchCancel {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MATCH_CANCEL")
    private Long matchCancelId;

    private String playCancelReason;
}
