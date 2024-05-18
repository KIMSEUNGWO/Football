package football.common.dto.field;

import football.common.domain.Field;
import football.common.domain.FieldImage;
import football.common.enums.domainenum.LocationEnum;
import football.common.enums.groundEnums.ParkingEnum;
import football.common.enums.groundEnums.ShowerEnum;
import football.common.enums.groundEnums.ToiletEnum;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveFieldRequest extends FieldForm {

    private List<MultipartFile> images;

    public SaveFieldRequest(String fieldName, LocationEnum region, String fieldAddress, String xSize, String ySize, ParkingEnum parking, ToiletEnum toilet, ShowerEnum shower, String fieldDetails) {
        super(fieldName, region, fieldAddress, xSize, ySize, parking, toilet, shower, fieldDetails);
    }
}
