package football.admin.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import football.admin.dto.SearchFieldRequest;
import football.admin.dto.SearchMatchRequest;
import football.common.domain.*;
import football.common.jpaRepository.JpaAdminRepository;
import football.common.jpaRepository.JpaOrderRepository;
import football.common.enums.domainenum.LocationEnum;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static football.common.domain.QField.field;
import static football.common.domain.QMatch.match;
import static football.common.enums.domainenum.LocationEnum.전체;

@Slf4j
@Repository
public class AdminRepositoryImpl implements AdminRepository {

    private final JpaOrderRepository jpaOrderRepository;
    private final JpaAdminRepository jpaAdminRepository;
    private final JPAQueryFactory query;

    @Autowired
    public AdminRepositoryImpl(JpaOrderRepository jpaOrderRepository, JpaAdminRepository jpaAdminRepository, EntityManager em) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.jpaAdminRepository = jpaAdminRepository;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Field> findByAllField(SearchFieldRequest searchDto) {
        return query.select(field)
            .from(field)
            .where(
                region(field.fieldLocation, searchDto.getRegion()),
                word(field.fieldTitle, searchDto.getWord()))
            .orderBy(field.fieldLocation.asc(), field.fieldTitle.asc())
            .fetch();
    }


    @Override
    public Integer findByMatchCount(Match match) {
        return jpaOrderRepository.countByMatch(match);
    }


    @Override
    public List<Match> findByAllMatch(SearchMatchRequest searchDto) {
        LocalDate start = searchDto.getStartDate();
        LocalDate end = searchDto.getEndDate();
        return query.selectFrom(match)
            .join(match.field, field)
            .where(
                match.matchDate.between(LocalDateTime.of(start, LocalTime.MIN),LocalDateTime.of(end, LocalTime.MAX))
                ,word(match.field.fieldTitle, searchDto.getWord())
                ,region(match.field.fieldLocation, searchDto.getRegion()))
            .orderBy(match.matchDate.desc())
            .fetch();
    }

    @Override
    public boolean isAdmin(Member member) {
        return jpaAdminRepository.existsByMember(member);
    }


    private BooleanExpression word(StringPath stringPath, String word) {
        if (word == null || word.isEmpty() || word.isBlank()) return null;
        return stringPath.like(String.format("%%%s%%", word));
    }

    private BooleanExpression region(EnumPath<LocationEnum> enumPath, List<LocationEnum> region) {
        if (region.contains(전체)) return null;
        BooleanExpression be = null;
        for (LocationEnum location : region) {
            BooleanExpression locationBe = enumPath.eq(location);

            be = (be == null) ? locationBe : be.or(locationBe);
        }
        return be;
    }

}
