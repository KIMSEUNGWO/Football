package football.start.allOfFootball.repository;

import football.start.allOfFootball.dto.SearchDto;
import football.common.domain.Match;

import java.util.List;

public interface MainRepository {
    List<Match> findByAllMatch(SearchDto searchDto);
}
