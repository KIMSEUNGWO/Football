package football.start.allOfFootball.service;

import football.start.allOfFootball.common.redis.RankService;
import football.start.allOfFootball.controller.mypage.ManagerDataForm;
import football.start.allOfFootball.controller.mypage.MatchDataForm;
import football.start.allOfFootball.controller.mypage.MyProfileDto;
import football.start.allOfFootball.controller.mypage.MypageMainDto;
import football.start.allOfFootball.domain.*;
import football.start.allOfFootball.dto.ChangePasswordForm;
import football.start.allOfFootball.dto.match.TeamInfo;
import football.start.allOfFootball.enums.TeamEnum;
import football.start.allOfFootball.enums.matchEnums.MatchStatus;
import football.start.allOfFootball.formatter.DateFormatter;
import football.start.allOfFootball.formatter.NumberFormatter;
import football.start.allOfFootball.repository.MypageRepository;
import football.start.allOfFootball.repository.domainRepository.MatchRepository;
import football.start.allOfFootball.repository.domainRepository.MemberRepository;
import football.start.allOfFootball.service.domainService.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

import static football.start.allOfFootball.formatter.NumberFormatter.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageServiceImpl implements MypageService{

    private final MypageRepository mypageRepository;
    private final MemberRepository memberRepository;
    private final RankService rankService;
    private final MatchRepository matchRepository;

    @Override
    public Optional<Member> findById(Long memberId) {
        if (memberId == null) Optional.empty();
        return mypageRepository.findById(memberId);
    }

    @Override
    public MyProfileDto getMyProfile(Member findMember) {
        MyProfileDto myProfileDto = MyProfileDto.builder()
            .profileImage("base.jpeg")
            .name(findMember.getMemberName())
            .social(findMember.getMemberSocial())
            .email(findMember.getMemberEmail())
            .score(format(findMember.getMemberScore()))
            .rank(format(rankService.getRank(findMember)))
            .grade(findMember.getGrade())
            .cash(format(findMember.getMemberCash()))
            .build();
        Profile profile = findMember.getProfile();
        if (profile != null) {
            myProfileDto.setProfileImage(profile.getProfileStoreName());
        }
//        myProfileDto.setMatchScore();
        return myProfileDto;
    }

    @Override
    public Map<String, String> changePassword(Optional<Member> findMember, ChangePasswordForm form, BindingResult bindingResult) {
        HashMap<String, String> result = new HashMap<>();
        if (findMember.isEmpty()) {
            result.put("result", "fail");
            result.put("message", "회원을 찾지 못했습니다.");
            return result;
        }
        Member member = findMember.get();
        if (bindingResult.hasErrors()) {
            List<FieldError> nowPassword = bindingResult.getFieldErrors("nowPassword");
            mypageRepository.validNowPw(result, nowPassword);
            List<FieldError> changePassword = bindingResult.getFieldErrors("changePassword");
            mypageRepository.validChangePw(result, changePassword);
            List<FieldError> checkPassword = bindingResult.getFieldErrors("checkPassword");
            mypageRepository.validCheckPw(result, checkPassword);
            result.put("result", "fail");
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
            String addSalt = member.combineSalt(changePassword);
            boolean isSame = memberRepository.isExactPassword(bfPassword, addSalt);
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
        String imgName = "base.jpeg";
        Profile profile = findMember.getProfile();
        if (profile != null) {
            imgName = profile.getProfileStoreName();
        }
        String name = findMember.getMemberName();
        String phone = findMember.getMemberPhone();
        Optional<BeforePassword> findBeforePassword = memberRepository.findByBeforePassword(findMember);
        String date = "변경된 기록 없음";
        if (findBeforePassword.isPresent()) {
            date = DateFormatter.dateFormat(findBeforePassword.get().getPasswordChangeDate());
        }
        return MypageMainDto.builder()
            .profileImage(imgName)
            .name(name)
            .phone(phone)
            .changePasswordDate(date)
            .build();
    }

    @Override
    public Map<String, List<ManagerDataForm>> getManagerList(Member findMember) {
        Manager manager = findMember.getManager();
        List<Match> matchList = matchRepository.findAllMatchBefore(manager);
        if (matchList.isEmpty()) return null;

        Map<String, List<ManagerDataForm>> result = new LinkedHashMap<>();

        for (Match match : matchList) {
            List<Orders> ordersList = match.getOrdersList();

            MatchDataForm matchDataForm = matchRepository.getMatchDataForm(match, ordersList);
            Map<TeamEnum, List<TeamInfo>> teamInfo = null;
            if (match.getMatchStatus() == MatchStatus.경기시작전) {
                teamInfo = matchRepository.getTeamInfo(match, ordersList);
            }

            String date = DateFormatter.dateFormatAndWeek(match.getMatchDate());

            ManagerDataForm build = ManagerDataForm.builder()
                .teamInfo(teamInfo)
                .topInfo(matchDataForm)
                .build();

            if (!result.containsKey(date)) {
                result.put(date, new ArrayList<>());
            }
            result.get(date).add(build);
        }
        return result;
    }
}
