package football.common.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "MATCH_CANCEL")
public class MatchCancel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchCancelId;

    private String playCancelReason;
}
