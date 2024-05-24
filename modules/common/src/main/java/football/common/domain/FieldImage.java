package football.common.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "FIELD_IMAGE")
@AllArgsConstructor
@NoArgsConstructor
public class FieldImage extends ImageChild {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fieldImageId;

    @ManyToOne
    @JoinColumn(name = "FIELD_ID")
    private Field field;

    public FieldImage(Field field, String originalName, String storeName) {
        super(originalName, storeName);
        this.field = field;
    }
}
