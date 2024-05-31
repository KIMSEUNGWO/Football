package football.start.allOfFootball.service;

import football.common.domain.*;
import football.start.allOfFootball.controller.mypage.ManagerDataForm;
import football.start.allOfFootball.controller.mypage.MatchDataForm;
import football.start.allOfFootball.controller.mypage.MypageMainDto;
import football.start.allOfFootball.dto.ChangePasswordForm;
import football.start.allOfFootball.dto.match.TeamInfo;
import football.common.enums.domainenum.TeamEnum;
import football.common.enums.matchenum.MatchStatus;
import football.common.formatter.DateFormatter;
import football.start.allOfFootball.mapper.Mapper;
import football.start.allOfFootball.repository.MypageRepository;
import football.start.allOfFootball.repository.domainRepository.MatchRepository;
import football.common.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageServiceImpl implements MypageService{

    private final MypageRepository mypageRepository;
    private final MemberRepository memberRepository;
    private final MatchRepository matchRepository;

    @Override
    public Map<String, String> changePassword(Member member, ChangePasswordForm form, BindingResult bindingResult) {
        HashMap<String, String> result = new HashMap<>();

        if (bindingResult.hasErrors()) {
            result.put("result", "fail");
            List<FieldError> nowPassword = bindingResult.getFieldErrors("nowPassword");
            mypageRepository.validNowPw(result, nowPassword);
            List<FieldError> changePassword = bindingResult.getFieldErrors("changePassword");
            mypageRepository.validChangePw(result, changePassword);
            List<FieldError> checkPassword = bindingResult.getFieldErrors("checkPassword");
            mypageRepository.validCheckPw(result, checkPassword);
            return result;
        }
        // 비밀번호 변경 로직
        String nowPassword = form.getNowPassword();
        boolean isExact = memberRepository.isExactPassword(member, nowPassword);
        if (!isExact) {
            result.put("nowPwError", "비밀번호가 일치하지 않습니다");
            result.put("result", "fail");
            return result;
        }
        String changePassword = form.getChangePassword();
        String checkPassword = form.getCheckPassword();
        if (!changePassword.equals(checkPassword)) {
            result.put("checkPwError", "변경 비밀번호와 일치하지 않습니다");
            result.put("result", "fail");
            return result;
        }
        BeforePassword beforePassword = member.getBeforePassword();
        if (beforePassword != null) {
            String bfPassword = beforePassword.getBeforePassword();
            boolean isSame = memberRepository.isExactPassword(bfPassword, changePassword);
            if (isSame) {
                result.put("changePwError", "이전 비밀번호와 일치합니다. 다른 비밀번호를 사용해주세요");
                result.put("result", "fail");
                return result;
            }
        }

        memberRepository.changePassword(member, form.getChangePassword());
        result.put("result", "ok");
        result.put("message", "비밀번호를 변경했습니다.");
        return result;
    }

    @Override
    public MypageMainDto getMypageMain(Member findMember) {
        return Mapper.getMyPageMain(findMember, memberRepository.findByBeforePassword(findMember));
    }

    @Override
    public Map<String, List<ManagerDataForm>> getManagerList(Member findMember) {
        Manager manager = findMember.getManager();
        List<Match> matchList = matchRepository.findAllMatchBefore(manager);

        Map<String, List<ManagerDataForm>> result = new LinkedHashMap<>();

        for (Match match : matchList) {
            List<Orders> ordersList = match.getOrdersList();

            MatchDataForm matchDataForm = Mapper.getMatchDataForm(match, ordersList);
            Map<TeamEnum, List<TeamInfo>> teamInfo = matchRepository.getTeamInfo(match, ordersList);
            String date = DateFormatter.format("yyyy년 M월 d일 (E)", match.getMatchDate());

            ManagerDataForm build = ManagerDataForm.builder()
                                    .topInfo(matchDataForm)
                                    .teamInfo(teamInfo)
                                    .isMatchPlaying(match.getMatchStatus() == MatchStatus.경기중)
                                    .isRecordScore(match.getMatchStatus() == MatchStatus.기록중)
                                    .build();

            if (!result.containsKey(date)) result.put(date, new ArrayList<>());
            result.get(date).add(build);
        }
        return result;
    }
}
