package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;
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
public class EditFieldForm {

    private List<MultipartFile> images; // 새로 추가될 사진목록
    private List<FieldImage> savedImages; // 이미 DB에 저장되어있는 사진목록
    private String deleteImages;
    private String fieldName;
    private LocationEnum region;
    private String fieldAddress;

    private String xSize;
    private String ySize;

    private ParkingEnum parking;
    private ToiletEnum toilet;
    private ShowerEnum shower;

    private String fieldDetails;

    public static EditFieldForm build(Field field, List<FieldImage> savedImages) {
        String fieldSize = field.getFieldSize();
        String replace = fieldSize.replaceAll("m", "");
        String[] split = replace.split("x");
        String x = split[0];
        String y = split[1];

        return EditFieldForm.builder()
            .savedImages(savedImages)
            .fieldName(field.getFieldTitle())
            .region(field.getFieldLocation())
            .fieldAddress(field.getFieldAddress())
            .xSize(x)
            .ySize(y)
            .parking(field.getFieldParking())
            .toilet(field.getFieldToilet())
            .shower(field.getFieldShower())
            .fieldDetails(field.getFieldInformation())
            .build();
    }
}
