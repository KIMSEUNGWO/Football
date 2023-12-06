package football.start.allOfFootball.service;

import football.start.allOfFootball.dto.SearchDto;
import football.start.allOfFootball.dto.SearchResultForm;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.repository.MainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainServiceImpl implements MainService{

    private final MainRepository mainRepository;
    @Override
    public List<SearchResultForm> getSearchResult(SearchDto searchDto) {
        List<Match> matchList = mainRepository.findByAllMatch(searchDto);

        return matchList.stream().map(x -> SearchResultForm.build(x)).collect(Collectors.toList());
    }
}
