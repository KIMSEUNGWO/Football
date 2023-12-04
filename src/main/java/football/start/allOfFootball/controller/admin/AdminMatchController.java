package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.common.alert.AlertUtils;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.LocationEnum;
import football.start.allOfFootball.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/match")
public class AdminMatchController {

    private final AdminService adminService;

    @GetMapping
    public String match(Model model) {
        LocationEnum[] locations = LocationEnum.values();
        model.addAttribute("locations", locations);
        return "admin_match";
    }

    @GetMapping("/add")
    public String matchAdd(@ModelAttribute SaveFieldForm saveFieldForm, Model model) {
        LocationEnum[] locations = LocationEnum.values();
        model.addAttribute("locations", locations);
        return "admin_match_add";
    }

    @PostMapping("/add")
    public String matchAddPost(@ModelAttribute SaveFieldForm saveFieldForm) {
        System.out.println("savematchForm = " + saveFieldForm);

        adminService.saveField(saveFieldForm);

        return "redirect:/admin/match";
    }

    @GetMapping("/{fieldId}")
    public String matchView(@PathVariable Long fieldId, HttpServletResponse response, Model model) {

        EditFieldForm form = adminService.findByFieldId(fieldId);
        if (form == null) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 구장입니다.", "/admin/match");
        }
        model.addAttribute("editFieldForm", form);

        return "admin_match_view";
    }

    @GetMapping("/{fieldId}/edit")
    public String matchEdit(@PathVariable Long fieldId, HttpServletResponse response, Model model) {

        EditFieldForm form = adminService.findByFieldId(fieldId);

        if (form == null) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 구장입니다.", "/admin/match");
        }
        LocationEnum[] locations = LocationEnum.values();
        model.addAttribute("locations", locations);
        model.addAttribute("fieldId", fieldId);
        model.addAttribute("editFieldForm", form);
        return "admin_match_edit";
    }

    @PostMapping("/{fieldId}/edit")
    public String matchEditPost(@PathVariable Long fieldId, HttpServletRequest request, HttpServletResponse response, @ModelAttribute EditFieldForm editFieldForm) {

        System.out.println("editFieldForm = " + editFieldForm);
        Long requestURIFieldId = getFieldId(request.getRequestURI());
        if (requestURIFieldId == null || fieldId != requestURIFieldId) {
            return AlertUtils.alertAndMove(response, "잘못된 경로입니다.", "/admin/match");
        }

        Optional<Field> findField = adminService.findByField(fieldId);
        if (findField.isEmpty()) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 구장입니다.", "/admin/match");
        }
        Field field = findField.get();
        adminService.editField(field, editFieldForm);

        return "redirect:/admin/match/" + fieldId;
    }

    private Long getFieldId(String requestURI) {
        // /match/{fieldId}/edit 형식 -> {fieldId} Long 타입으로 변환
        String numStr = requestURI.replaceAll("[^0-9]", "");
        try {
            Long fieldId = Long.parseLong(numStr);
            return fieldId;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Match Test 파일
    @GetMapping("/test")
    public String test() {
        Match match = new Match();
        match.setField(adminService.findByField(1L).get());
        match.setMatchDate(LocalDate.now());
        match.setMatchTime(2);
        match.setMatchGender(GenderEnum.남자);
        match.setMaxPerson(5);
        match.setMatchEndStatus('N');
        adminService.matchTest(match);

        return "redirect:/admin/match";
    }
}
