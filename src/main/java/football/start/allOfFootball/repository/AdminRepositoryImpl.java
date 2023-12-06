package football.start.allOfFootball.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import football.start.allOfFootball.controller.admin.SearchFieldDto;
import football.start.allOfFootball.controller.admin.SearchMatchDto;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.domain.QMatch;
import football.start.allOfFootball.enums.LocationEnum;
import football.start.allOfFootball.jpaRepository.JpaFieldImageRepository;
import football.start.allOfFootball.jpaRepository.JpaFieldRepository;
import football.start.allOfFootball.jpaRepository.JpaMatchRepository;
import football.start.allOfFootball.jpaRepository.JpaOrderRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static football.start.allOfFootball.domain.QField.field;
import static football.start.allOfFootball.domain.QMatch.*;
import static football.start.allOfFootball.enums.LocationEnum.전체;

@Slf4j
@Repository
public class AdminRepositoryImpl implements AdminRepository{

    private final JpaOrderRepository jpaOrderRepository;
    private final JPAQueryFactory query;

    public AdminRepositoryImpl(JpaOrderRepository jpaOrderRepository, EntityManager em) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Field> findByAllField(SearchFieldDto searchDto) {
        return query.select(field)
            .from(field)
            .where(region(searchDto.getRegion()), word(searchDto.getWord()))
            .orderBy(field.fieldLocation.asc())
            .orderBy(field.fieldTitle.asc())
            .fetch();
    }


    @Override
    public Integer findByMatchCount(Match match) {
        return jpaOrderRepository.findByMatch(match).size();
    }

    @Override
    public List<Match> findByAllMatch(SearchMatchDto searchDto) {
        return query.selectFrom(match)
                .join(match.field, field)
                .where(match.matchDate.between(searchDto.getStartDate(), searchDto.getEndDate())
                    ,joinWord(searchDto.getWord())
                    ,joinRegion(searchDto.getRegion()))
                .orderBy(match.matchDate.desc(), match.matchHour.asc())
                .fetch();
    }


    private BooleanExpression word(String word) {
        if (word == null || word.equals("") || word.equals(" ")) {
            return null;
        }
        return field.fieldTitle.like("%" + word + "%");
    }
    private BooleanExpression joinWord(String word) {
        if (word == null || word.equals("") || word.equals(" ")) {
            return null;
        }
        return match.field.fieldTitle.like("%" + word + "%");
    }

    private BooleanExpression region(List<LocationEnum> region) {
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
    private BooleanExpression joinRegion(List<LocationEnum> region) {
        if (region.contains(전체)) {
            return null;
        }
        BooleanExpression be = null;
        for (LocationEnum location : region) {
            BooleanExpression locationBe = match.field.fieldLocation.eq(location);

            be = (be == null) ? locationBe : be.or(locationBe);
        }
        return be;
    }


}
