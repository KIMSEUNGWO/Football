package football.start.allOfFootball.common.file;

import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.domain.Profile;
import football.start.allOfFootball.jpaRepository.JpaFieldImageRepository;
import football.start.allOfFootball.jpaRepository.JpaFieldRepository;
import football.start.allOfFootball.jpaRepository.JpaMemberRepository;
import football.start.allOfFootball.jpaRepository.JpaProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class FileRepository {

    private final JpaMemberRepository jpaMemberRepository;
    private final JpaProfileRepository jpaProfileRepository;

    private final JpaFieldRepository jpaFieldRepository;
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
