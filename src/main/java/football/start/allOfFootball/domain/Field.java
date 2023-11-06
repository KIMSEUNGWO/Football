package football.start.allOfFootball.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "FIELD")
@SequenceGenerator(name = "SEQ_FIELD", sequenceName = "SEQ_FIELD_ID")
public class Field {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FIELD")
    private Long fieldId;
    private String fieldTitle;
    private String fieldAddress;
    private String fieldLocation1; // enum으로 대체 예정
    private String fieldLocation2;
    private String fieldInformation;
    private char fieldParkingStatus;
    private char fieldShowerStatus;
    private int fieldMinPerson;
    private int fieldMaxPerson;
}
