package football.start.allOfFootball.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import football.common.domain.*;
import football.common.jpaRepository.JpaAdminRepository;
import football.common.jpaRepository.JpaOrderRepository;
import football.start.allOfFootball.controller.admin.SearchFieldDto;
import football.start.allOfFootball.controller.admin.SearchMatchDto;
import football.common.enums.domainenum.LocationEnum;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static football.common.domain.QField.field;
import static football.common.domain.QMatch.match;
import static football.common.enums.domainenum.LocationEnum.전체;

@Slf4j
@Repository
public class AdminRepositoryImpl implements AdminRepository{

    private final JpaOrderRepository jpaOrderRepository;
    private final JpaAdminRepository jpaAdminRepository;
    private final JPAQueryFactory query;

    public AdminRepositoryImpl(JpaOrderRepository jpaOrderRepository, JpaAdminRepository jpaAdminRepository, EntityManager em) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.jpaAdminRepository = jpaAdminRepository;
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
        LocalDate start = searchDto.getStartDate();
        LocalDate end = searchDto.getEndDate();
        return query.selectFrom(match)
            .join(match.field, field)
            .where(match.matchDate.between(
                    LocalDateTime.of(start.getYear(), start.getMonthValue(), start.getDayOfMonth(), 0, 0),
                    LocalDateTime.of(end.getYear(), end.getMonthValue(), end.getDayOfMonth(), 23, 59))
                ,joinWord(searchDto.getWord())
                ,joinRegion(searchDto.getRegion()))
            .orderBy(match.matchDate.desc())
            .fetch();
    }

    @Override
    public boolean isAdmin(Member member) {
        return jpaAdminRepository.existsByMember(member);
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
