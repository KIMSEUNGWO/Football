package football.start.allOfFootball.dto;

import football.start.allOfFootball.common.file.FileUploadDto;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Profile;

public class File {

    public static Profile build(FileUploadDto dto, Member member) {
        return new Profile().builder()
            .member(member)
            .profileName(dto.getImageUploadName())
            .profileStoreName(dto.getImageStoreName())
            .build();
    }

    public static FieldImage build(FileUploadDto dto, Field field) {
        return new FieldImage().builder()
            .field(field)
            .fieldImageName(dto.getImageUploadName())
            .fieldImageStoreName(dto.getImageStoreName())
            .build();
    }
}
