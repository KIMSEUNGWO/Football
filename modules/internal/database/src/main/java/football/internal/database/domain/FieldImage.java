package football.internal.database.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "FIELD_IMAGE")
@SequenceGenerator(name = "SEQ_FIELD_IMAGE", sequenceName = "SEQ_FIELD_IMAGE_ID")
@AllArgsConstructor
@NoArgsConstructor
public class FieldImage {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FIELD_IMAGE")
    private Long fieldImageId;

    @ManyToOne
    @JoinColumn(name = "fieldId")
    private Field field;
    private String fieldImageName;
    private String fieldImageStoreName;


}
