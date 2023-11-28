package football.start.allOfFootball.domain;

import football.start.allOfFootball.controller.admin.SaveGroundForm;
import football.start.allOfFootball.enums.LocationEnum;
import football.start.allOfFootball.enums.groundEnums.ParkingEnum;
import football.start.allOfFootball.enums.groundEnums.ShowerEnum;
import football.start.allOfFootball.enums.groundEnums.ToiletEnum;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "FIELD")
@SequenceGenerator(name = "SEQ_FIELD", sequenceName = "SEQ_FIELD_ID", allocationSize = 1)
public class Field {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FIELD")
    private Long fieldId;
    private String fieldTitle;
    @Enumerated(EnumType.STRING)
    private LocationEnum fieldLocation;
    private String fieldAddress;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String fieldInformation;

    @Enumerated(EnumType.STRING)
    private ParkingEnum fieldParking;

    @Enumerated(EnumType.STRING)
    private ToiletEnum fieldToilet;

    @Enumerated(EnumType.STRING)
    private ShowerEnum fieldShower;

    public static Field build(SaveGroundForm form) {
        return new Field().builder()
            .fieldTitle(form.getFieldName())
            .fieldLocation(form.getRegion())
            .fieldAddress(form.getFieldAddress())
            .fieldParking(form.getParking())
            .fieldToilet(form.getToilet())
            .fieldShower(form.getShower())
            .fieldInformation(form.getFieldDetails())
            .build();
    }

}
