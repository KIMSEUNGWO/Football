package football.start.allOfFootball.common.file;

import football.common.domain.Field;
import football.common.domain.FieldImage;
import football.common.domain.Member;
import football.common.domain.Profile;
import football.common.jpaRepository.JpaFieldImageRepository;
import football.common.jpaRepository.JpaProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class FileRepository {

    private final JpaProfileRepository jpaProfileRepository;
    private final JpaFieldImageRepository jpaFieldImageRepository;

    public int saveFieldImage(FileUploadDto form) {

        Field field = (Field) form.getParent();

        // fieldImage domain 객체로 변환
        FieldImage fieldImage = FieldImage.builder()
            .field(field)
            .fieldImageName(form.getImageUploadName())
            .fieldImageStoreName(form.getImageStoreName())
            .build();

        // fieldImage DB 저장
        jpaFieldImageRepository.save(fieldImage);

        return 1;
    }

    public int saveProfile(FileUploadDto form) {

        Member member = (Member) form.getParent();

        // profile domain 객체로 변환
        Profile profile = Profile.builder()
            .member(member)
            .profileName(form.getImageUploadName())
            .profileStoreName(form.getImageStoreName())
            .build();

        // profile DB 저장
        jpaProfileRepository.save(profile);

        return 1;
    }

}
