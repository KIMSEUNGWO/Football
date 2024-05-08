package football.start.allOfFootball.controller;

import football.start.allOfFootball.customAnnotation.SessionLogin;
import football.start.allOfFootball.domain.Member;
import football.start.allOfFootball.dto.json.JsonDefault;
import football.start.allOfFootball.dto.ManagerApplyDto;
import football.start.allOfFootball.service.domainService.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ManagerController {

    private final ManagerService managerService;


    @GetMapping("/manager")
    public String manager(@SessionLogin Member member, Model model) {
        String phone = member.getMemberPhone();
        String date = getDate(member.getMemberBirthday());
        model.addAttribute("phone", phone);
        model.addAttribute("birth", date);
        return "manager";
    }

    private String getDate(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        return String.format("%d-%02d-%02d", year, month, day);
    }

    @ResponseBody
    @PostMapping("/manager/apply/confirm")
    public ResponseEntity<JsonDefault> apply(@SessionLogin Member member, @RequestBody ManagerApplyDto data) {
        managerService.save(member, data);

        return ResponseEntity.ok(new JsonDefault("매니저 신청 완료"));
    }

}
