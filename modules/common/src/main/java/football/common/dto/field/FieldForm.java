package football.common.dto.field;

import football.common.domain.Field;
import football.common.enums.domainenum.LocationEnum;
import football.common.enums.groundEnums.ParkingEnum;
import football.common.enums.groundEnums.ShowerEnum;
import football.common.enums.groundEnums.ToiletEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class FieldForm {

    private String fieldName;
    private LocationEnum region;
    private String fieldAddress;

    private String xSize;
    private String ySize;

    private ParkingEnum parking;
    private ToiletEnum toilet;
    private ShowerEnum shower;

    private String fieldDetails;

    public FieldForm(Field field) {
        String fieldSize = field.getFieldSize();
        String replace = fieldSize.replaceAll("m", "");
        String[] split = replace.split("x");

        fieldName = field.getFieldTitle();
        region = field.getFieldLocation();
        fieldAddress = field.getFieldAddress();
        xSize = split[0];
        ySize = split[1];
        parking = field.getFieldParking();
        toilet = field.getFieldToilet();
        shower = field.getFieldShower();
        fieldDetails = field.getFieldInformation();
    }
}
