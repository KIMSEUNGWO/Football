package football.start.allOfFootball.controller.admin;

import football.internal.database.domain.Field;
import football.internal.database.domain.FieldImage;
import football.start.allOfFootball.enums.LocationEnum;
import football.start.allOfFootball.enums.groundEnums.ParkingEnum;
import football.start.allOfFootball.enums.groundEnums.ShowerEnum;
import football.start.allOfFootball.enums.groundEnums.ToiletEnum;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SaveFieldForm {

    private List<MultipartFile> images;
    private String fieldName;
    private LocationEnum region;
    private String fieldAddress;

    private String xSize;
    private String ySize;

    private ParkingEnum parking;
    private ToiletEnum toilet;
    private ShowerEnum shower;

    private String fieldDetails;


    public static SaveFieldForm build(Field field, FieldImage fieldImage) {

        return null;
    }

    public SaveFieldForm(String fieldName, LocationEnum region, String fieldAddress, String xSize, String ySize, ParkingEnum parking, ToiletEnum toilet, ShowerEnum shower) {
        this.fieldName = fieldName;
        this.region = region;
        this.fieldAddress = fieldAddress;
        this.xSize = xSize;
        this.ySize = ySize;
        this.parking = parking;
        this.toilet = toilet;
        this.shower = shower;
    }
}
