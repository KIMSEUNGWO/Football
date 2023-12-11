package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.common.alert.AlertUtils;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.domain.Match;
import football.start.allOfFootball.enums.GenderEnum;
import football.start.allOfFootball.enums.LocationEnum;
import football.start.allOfFootball.service.AdminService;
import football.start.allOfFootball.service.domainService.FieldService;
import football.start.allOfFootball.service.domainService.MatchService;
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

    private final MatchService matchService;
    private final FieldService fieldService;
    @GetMapping
    public String match(Model model) {
        LocationEnum[] locations = LocationEnum.values();
        model.addAttribute("locations", locations);
        return "/admin/admin_match";
    }

    @GetMapping("/{fieldId}/add")
    public String matchAdd(@PathVariable Long fieldId, @ModelAttribute SaveMatchForm saveMatchForm, Model model, HttpServletResponse response) {
        Optional<Field> findField = fieldService.findByField(fieldId);
        if (findField.isEmpty()) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 구장입니다.", "/admin/ground");
        }
        Field field = findField.get();
        ViewMatchFieldForm fieldForm = ViewMatchFieldForm.build(field);
        model.addAttribute("fieldInfo", fieldForm);
        model.addAttribute("fieldId", fieldId);
        return "/admin/admin_match_add";
    }

    @PostMapping("/{fieldId}/add")
    public String matchAddPost(@PathVariable Long fieldId, @ModelAttribute SaveMatchForm saveMatchForm, HttpServletResponse response) {
        System.out.println("saveMatchForm = " + saveMatchForm);
        Optional<Field> findField = fieldService.findByField(fieldId);
        if (findField.isEmpty()) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 구장입니다.", "/admin/ground");
        }
        Field field = findField.get();
        matchService.saveMatch(field, saveMatchForm);

        return "redirect:/admin/match";
    }

    @GetMapping("/{matchId}")
    public String matchView(@PathVariable Long matchId, HttpServletResponse response, Model model) {
        Optional<Match> findMatch = matchService.findByMatch(matchId);
        if (findMatch.isEmpty()) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 구장입니다.", "/admin/ground");
        }
        Match match = findMatch.get();
        Field field = match.getField();
        ViewMatchFieldForm fieldForm = ViewMatchFieldForm.build(field);
        EditMatchForm editMatchForm = EditMatchForm.build(match);

        model.addAttribute("fieldId", field.getFieldId());
        model.addAttribute("matchId", match.getMatchId());
        model.addAttribute("fieldInfo", fieldForm);
        model.addAttribute("editMatchForm", editMatchForm);
        return "/admin/admin_match_view";
    }


    @GetMapping("/{matchId}/edit")
    public String matchEdit(@PathVariable Long matchId, HttpServletResponse response, Model model) {
        Optional<Match> findMatch = matchService.findByMatch(matchId);
        if (findMatch.isEmpty()) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 구장입니다.", "/admin/ground");
        }
        Match match = findMatch.get();
        Field field = match.getField();
        ViewMatchFieldForm fieldForm = ViewMatchFieldForm.build(field);
        EditMatchForm editMatchForm = EditMatchForm.build(match);

        model.addAttribute("fieldId", field.getFieldId());
        model.addAttribute("matchId", match.getMatchId());
        model.addAttribute("fieldInfo", fieldForm);
        model.addAttribute("editMatchForm", editMatchForm);

        return "/admin/admin_match_edit";
    }

    @PostMapping("/{matchId}/edit")
    public String matchEditPost(@PathVariable Long matchId, @ModelAttribute EditMatchForm editMatchForm, HttpServletResponse response) {
        System.out.println("editMatchForm = " + editMatchForm);
        Optional<Match> findMatch = matchService.findByMatch(matchId);
        if (findMatch.isEmpty()) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 구장입니다.", "/admin/ground");
        }
        Match match = findMatch.get();

        matchService.editMatch(match, editMatchForm);

        return "redirect:/admin/match/" + matchId;
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

}
