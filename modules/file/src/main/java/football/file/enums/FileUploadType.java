package football.file.enums;

import football.common.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.Banner;

@Getter
@AllArgsConstructor
public enum FileUploadType {

    FIELD_IMAGE("fieldImage", FieldImage.class),
    PROFILE("profile", Profile.class),
    BANNER("banner", null),;

    private final String dir;
    private final Class<? extends ImageChild> aClass;


    public static FileUploadType findDir(String dir) {
        FileUploadType[] values = FileUploadType.values();
        for (FileUploadType value : values) {
            if (value.dir.equals(dir)) {
                return value;
            }
        }
        return null;
    }

    public ImageChild createEntity(ImageParent parent, String originalName, String storeName) {

        if (this.aClass.isAssignableFrom(FieldImage.class)) {
            return new FieldImage((Field) parent, originalName, storeName);
        }
        if (this.aClass.isAssignableFrom(Profile.class)) {
            return new Profile((Member) parent, originalName, storeName);
        }

        return null;
    }
}
