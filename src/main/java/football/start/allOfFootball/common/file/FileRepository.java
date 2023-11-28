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

        Long fieldId = form.getId();
        Optional<Field> findField = jpaFieldRepository.findById(fieldId);
        if (findField.isEmpty()) {
            log.error("FileRepository NotFoundField fieldId = {}", fieldId);
            return 0;
        }
        Field field = findField.get();

        // fieldImage domain 객체로 변환
        FieldImage fieldImage = FieldImage.build(form, field);

        // fieldImage DB 저장
        jpaFieldImageRepository.save(fieldImage);

        return 1;
    }

    public int saveProfile(FileUploadDto form) {

        Long memberId = form.getId();
        Optional<Member> findMember = jpaMemberRepository.findById(memberId);
        if (findMember.isEmpty()) {
            log.error("FileRepository NotFoundField memberId = {}", memberId);
            return 0;
        }
        Member member = findMember.get();

        // profile domain 객체로 변환
        Profile profile = Profile.build(form, member);

        // profile DB 저장
        jpaProfileRepository.save(profile);

        return 1;
    }

}
