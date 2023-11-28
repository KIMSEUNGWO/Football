package football.start.allOfFootball.enums;

import lombok.Getter;

@Getter
public enum FileUploadType {

    FIELD_IMAGE("fieldImage"),
    PROFILE("profile");

    private String dir;


    FileUploadType(String dir) {
        this.dir = dir;
    }
}
