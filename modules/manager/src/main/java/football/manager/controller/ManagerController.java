package football.manager.controller;

import football.common.config.auth.PrincipalDetails;
import football.common.domain.Member;
import football.common.dto.json.JsonDefault;
import football.manager.dto.ManagerApplyDto;
import football.manager.service.ManagerService;
import football.common.formatter.DateFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ManagerController {

    private final ManagerService managerService;


    @GetMapping("/manager")
    public String manager(@AuthenticationPrincipal PrincipalDetails user, Model model) {
        Member member = user.getMember();
        String phone = member.getMemberPhone();
        String date = DateFormatter.format("yyyy-MM-dd", member.getMemberBirthday());
        model.addAttribute("phone", phone);
        model.addAttribute("birth", date);
        return "manager";
    }

    @ResponseBody
    @PostMapping("/manager/apply/confirm")
    public ResponseEntity<JsonDefault> apply(@AuthenticationPrincipal PrincipalDetails user, @RequestBody ManagerApplyDto data) {
        Member member = user.getMember();
        managerService.save(member, data);

        return ResponseEntity.ok(new JsonDefault("매니저 신청 완료"));
    }

}
