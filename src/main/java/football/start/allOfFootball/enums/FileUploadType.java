package football.start.allOfFootball.enums;

import lombok.Getter;

@Getter
public enum FileUploadType {

    FIELD_IMAGE("fieldImage"),
    PROFILE("profile"),
    BANNER("banner");

    private String dir;


    FileUploadType(String dir) {
        this.dir = dir;
    }

    public static FileUploadType findDir(String dir) {
        FileUploadType[] values = FileUploadType.values();
        for (FileUploadType value : values) {
            if (value.dir.equals(dir)) {
                return value;
            }
        }
        return null;
    }
}
