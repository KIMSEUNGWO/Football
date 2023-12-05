package football.start.allOfFootball.domain;

import football.start.allOfFootball.controller.admin.EditFieldForm;
import football.start.allOfFootball.controller.admin.SaveFieldForm;
import football.start.allOfFootball.enums.LocationEnum;
import football.start.allOfFootball.enums.groundEnums.ParkingEnum;
import football.start.allOfFootball.enums.groundEnums.ShowerEnum;
import football.start.allOfFootball.enums.groundEnums.ToiletEnum;
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

    private String fieldSize;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String fieldInformation;

    @Enumerated(EnumType.STRING)
    private ParkingEnum fieldParking;

    @Enumerated(EnumType.STRING)
    private ToiletEnum fieldToilet;

    @Enumerated(EnumType.STRING)
    private ShowerEnum fieldShower;

    @OneToMany(mappedBy = "field")
    private List<FieldImage> fieldImages = new ArrayList<>();

    public static Field build(SaveFieldForm form) {
        return new Field().builder()
            .fieldTitle(form.getFieldName())
            .fieldLocation(form.getRegion())
            .fieldAddress(form.getFieldAddress())
            .fieldSize(getSize(form.getXSize(), form.getYSize()))
            .fieldParking(form.getParking())
            .fieldToilet(form.getToilet())
            .fieldShower(form.getShower())
            .fieldInformation(form.getFieldDetails())
            .build();
    }

    public void setEditField(EditFieldForm form) {
        fieldTitle = form.getFieldName();
        fieldLocation = form.getRegion();
        fieldAddress = form.getFieldAddress();
        fieldSize = getSize(form.getXSize(), form.getYSize());
        fieldParking = form.getParking();
        fieldToilet = form.getToilet();
        fieldShower = form.getShower();
        fieldInformation = form.getFieldDetails();
    }

    private static String getSize(String xSize, String ySize) {
        return xSize + "x" + ySize;
    }

}
