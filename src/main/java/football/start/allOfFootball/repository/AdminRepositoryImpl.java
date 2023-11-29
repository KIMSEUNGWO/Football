package football.start.allOfFootball.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import football.start.allOfFootball.controller.admin.SearchDto;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;
import football.start.allOfFootball.domain.QField;
import football.start.allOfFootball.enums.LocationEnum;
import football.start.allOfFootball.jpaRepository.JpaFieldImageRepository;
import football.start.allOfFootball.jpaRepository.JpaFieldRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static football.start.allOfFootball.domain.QField.field;
import static football.start.allOfFootball.enums.LocationEnum.전체;

@Slf4j
@Repository
public class AdminRepositoryImpl implements AdminRepository{

    private final JpaFieldImageRepository jpaFieldImageRepository;
    private final JpaFieldRepository jpaFieldRepository;
    private final JPAQueryFactory query;

    public AdminRepositoryImpl(JpaFieldImageRepository jpaFieldImageRepository, JpaFieldRepository jpaFieldRepository, EntityManager em) {
        this.jpaFieldImageRepository = jpaFieldImageRepository;
        this.jpaFieldRepository = jpaFieldRepository;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public void saveField(Field field) {
        jpaFieldRepository.save(field);
    }

    @Override
    public List<Field> findByAllField(SearchDto searchDto) {
        return query.select(field)
            .from(field)
            .where(region(searchDto), word(searchDto))
            .orderBy(field.fieldLocation.asc())
            .orderBy(field.fieldTitle.asc())
            .fetch();
    }

    @Override
    public Optional<Field> findByField(Long fieldId) {
        return jpaFieldRepository.findById(fieldId);
    }

    @Override
    public List<FieldImage> findByAllFieldImage(Field field) {
        return jpaFieldImageRepository.findAllByField(field);
    }

    private BooleanExpression word(SearchDto searchDto) {
        String word = searchDto.getWord();
        if (word == null || word.equals("") || word.equals(" ")) {
            return null;
        }
        return field.fieldTitle.like("%" + word + "%");
    }

    private BooleanExpression region(SearchDto searchDto) {
        List<LocationEnum> region = searchDto.getRegion();
        if (region.contains(전체)) {
            return null;
        }
        BooleanExpression be = null;
        for (LocationEnum location : region) {
            BooleanExpression locationBe = field.fieldLocation.eq(location);

            be = (be == null) ? locationBe : be.or(locationBe);
        }
        return be;
    }


}
