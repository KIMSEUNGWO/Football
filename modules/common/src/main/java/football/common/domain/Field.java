package football.common.domain;

import football.common.dto.field.FieldForm;
import football.common.enums.domainenum.LocationEnum;
import football.common.enums.groundEnums.ParkingEnum;
import football.common.enums.groundEnums.ShowerEnum;
import football.common.enums.groundEnums.ToiletEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "FIELDS")
public class Field extends BaseTimeEntity implements ImageParent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fieldId;
    private String fieldTitle;
    @Enumerated(EnumType.STRING)
    private LocationEnum fieldLocation;
    private String fieldAddress;

    private String fieldSize;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String fieldInformation;

    @Enumerated(EnumType.STRING)
    private ParkingEnum fieldParking;

    @Enumerated(EnumType.STRING)
    private ToiletEnum fieldToilet;

    @Enumerated(EnumType.STRING)
    private ShowerEnum fieldShower;

    @OneToMany(mappedBy = "field")
    private List<FieldImage> fieldImages = new ArrayList<>();

    public Field(FieldForm form) {
        setEditField(form);
    }

    public void setEditField(FieldForm form) {
        fieldTitle = form.getFieldName();
        fieldLocation = form.getRegion();
        fieldAddress = form.getFieldAddress();
        fieldSize = String.format("%sm x %sm", form.getXSize(), form.getYSize());
        fieldParking = form.getParking();
        fieldToilet = form.getToilet();
        fieldShower = form.getShower();
        fieldInformation = form.getFieldDetails();
    }

}
