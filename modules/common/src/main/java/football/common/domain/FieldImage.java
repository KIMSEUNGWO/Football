package football.common.domain;

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
public class FieldImage extends ImageChild {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FIELD_IMAGE")
    private Long fieldImageId;

    @ManyToOne
    @JoinColumn(name = "fieldId")
    private Field field;

    public FieldImage(Field field, String originalName, String storeName) {
        super(originalName, storeName);
        this.field = field;
    }
}
