package football.start.allOfFootball.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "FIELD_IMAGE")
@SequenceGenerator(name = "SEQ_FIELD_IMAGE", sequenceName = "SEQ_FIELD_IMAGE_ID")
public class FieldImage {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FIELD_IMAGE")
    private Long fieldImageId;

    @ManyToOne
    @JoinColumn(name = "fieldId")
    private Field field;
    private String fieldImageName;
    private String fieldImageStoreName;
}
