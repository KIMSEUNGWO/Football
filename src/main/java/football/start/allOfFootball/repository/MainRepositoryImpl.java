package football.start.allOfFootball.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import football.start.allOfFootball.dto.SearchDto;
import football.internal.database.domain.Match;
import football.internal.database.enums.matchEnums.GenderEnum;
import football.internal.database.enums.LocationEnum;
import football.internal.database.enums.matchEnums.MatchEnum;
import football.internal.database.enums.matchEnums.MatchStatus;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static football.start.allOfFootball.domain.QField.field;
import static football.start.allOfFootball.domain.QMatch.match;

@Repository
@Slf4j
public class MainRepositoryImpl implements MainRepository{

    private final JPAQueryFactory query;

    @Autowired
    public MainRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Match> findByAllMatch(SearchDto searchDto) {
        LocalDate date = searchDto.getMatchDate();
        return query.selectFrom(match)
            .join(match.field, field)
            .where(match.matchDate.between(
                    LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 0, 0),
                    LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 23, 59))
                ,joinWord(searchDto.getWord()) // 구장명이 포함된것만
                ,joinRegion(searchDto.getRegion()) // 그 지역만
                ,getGender(searchDto.getGender())
                ,getGrade(searchDto.getGrade())
                ,match.matchStatus.in(MatchStatus.모집중, MatchStatus.마감임박, MatchStatus.마감)
            )
            .orderBy(match.matchDate.asc())
            .fetch();
    }

    private BooleanExpression joinWord(String word) {
        if (word == null || word.equals("") || word.equals(" ")) {
            return null;
        }
        return match.field.fieldTitle.like("%" + word + "%");
    }

    private BooleanExpression joinRegion(LocationEnum region) {
        if (region == null || region == LocationEnum.전체) {
            return null;
        }
        return match.field.fieldLocation.eq(region);
    }

    private BooleanExpression getGender(GenderEnum gender) {
        if (gender == null || gender == GenderEnum.전체) {
            return null;
        }
        return match.matchGender.eq(gender);
    }
    private BooleanExpression getGrade(MatchEnum grade) {
        if (grade == null || grade == MatchEnum.전체) {
            return null;
        }
        return match.matchGrade.eq(grade);
    }
}
