package football.start.allOfFootball.service;

import football.start.allOfFootball.common.file.FileService;
import football.start.allOfFootball.controller.admin.*;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.FieldImage;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.enums.FileUploadType;
import football.start.allOfFootball.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public List<SearchFieldForm> getSearchFieldResult(SearchFieldDto searchDto) {

        List<Field> list = adminRepository.findByAllField(searchDto);
        return list.stream().map(x -> SearchFieldForm.build(x)).collect(Collectors.toList());
    }

    @Override
    public List<SearchMatchForm> getSearchMatchResult(SearchMatchDto searchDto) {
        List<Match> list = adminRepository.findByAllMatch(searchDto);
        return list.stream().map( x -> SearchMatchForm.build(x, adminRepository.findByMatchCount(x))).collect(Collectors.toList());
    }


}
