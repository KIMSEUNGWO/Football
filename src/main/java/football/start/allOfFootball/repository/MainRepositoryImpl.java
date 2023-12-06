package football.start.allOfFootball.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import football.start.allOfFootball.dto.SearchDto;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.LocationEnum;
import football.start.allOfFootball.enums.gradeEnums.MatchEnum;
import football.start.allOfFootball.jpaRepository.JpaMatchRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static football.start.allOfFootball.domain.QField.field;
import static football.start.allOfFootball.domain.QMatch.match;

@Repository
@Slf4j
public class MainRepositoryImpl implements MainRepository{

    private final JpaMatchRepository jpaMatchRepository;
    private final JPAQueryFactory query;

    public MainRepositoryImpl(JpaMatchRepository jpaMatchRepository, EntityManager em) {
        this.jpaMatchRepository = jpaMatchRepository;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Match> findByAllMatch(SearchDto searchDto) {
        return query.selectFrom(match)
            .join(match.field, field)
            .where(match.matchDate.eq(searchDto.getMatchDate()) // 해당날짜만
                ,joinWord(searchDto.getWord()) // 구장명이 포함된것만
                ,joinRegion(searchDto.getRegion()) // 그 지역만
                ,getGender(searchDto.getGender())
                ,getGrade(searchDto.getGrade())
            )
            .orderBy(match.matchHour.asc())
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
