package football.admin.controller;

import football.admin.dto.SearchMatchRequest;
import football.admin.dto.SearchMatchResponse;
import football.admin.dto.ViewMatchFieldForm;
import football.admin.exception.NotExistsFieldException;
import football.common.common.alert.AlertUtils;
import football.common.domain.Field;
import football.common.domain.Match;
import football.common.enums.domainenum.LocationEnum;
import football.common.dto.match.EditMatchRequest;
import football.common.dto.match.SaveMatchRequest;
import football.admin.service.AdminService;
import football.admin.service.FieldService;
import football.common.exception.match.NotExistsMatchException;
import football.start.allOfFootball.service.domainService.MatchService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/match")
public class AdminMatchController {

    private final MatchService matchService;
    private final FieldService fieldService;
    private final AdminService adminService;

    @ResponseBody
    @PostMapping("/get")
    public List<SearchMatchResponse> matchGet(@RequestBody SearchMatchRequest searchDto) {
        return adminService.getSearchMatchResult(searchDto);
    }
    @GetMapping
    public String match(Model model) {
        LocationEnum[] locations = LocationEnum.values();
        model.addAttribute("locations", locations);
        return "/admin/admin_match";
    }

    @GetMapping("/{fieldId}/add")
    public String matchAdd(@PathVariable Long fieldId, @ModelAttribute SaveMatchRequest saveMatchForm, Model model, HttpServletResponse response) throws NotExistsFieldException {
        Field field = fieldService.findByField(fieldId);
        ViewMatchFieldForm fieldForm = new ViewMatchFieldForm(field);
        model.addAttribute("fieldInfo", fieldForm);
        model.addAttribute("fieldId", fieldId);
        return "/admin/admin_match_add";
    }

    @PostMapping("/{fieldId}/add")
    public String matchAddPost(@PathVariable Long fieldId, @ModelAttribute SaveMatchRequest saveMatchForm, HttpServletResponse response) throws NotExistsFieldException {
        Field field = fieldService.findByField(fieldId);
        matchService.saveMatch(field, saveMatchForm);

        return "redirect:/admin/match";
    }

    @GetMapping("/{matchId}")
    public String matchView(@PathVariable Long matchId, HttpServletResponse response, Model model) throws NotExistsMatchException {
        Match match = matchService.findByMatch(matchId, "/admin/ground");
        Field field = match.getField();
        ViewMatchFieldForm fieldForm = new ViewMatchFieldForm(field);
        EditMatchRequest editMatchForm = new EditMatchRequest(match);

        model.addAttribute("fieldInfo", fieldForm);
        model.addAttribute("editMatchForm", editMatchForm);
        return "/admin/admin_match_view";
    }


    @GetMapping("/{matchId}/edit")
    public String matchEdit(@PathVariable Long matchId, HttpServletResponse response, Model model) throws NotExistsMatchException {
        Match match = matchService.findByMatch(matchId, "/admin/ground");
        Field field = match.getField();
        ViewMatchFieldForm fieldForm = new ViewMatchFieldForm(field);
        EditMatchRequest editMatchForm = new EditMatchRequest(match);

        if (editMatchForm.isExit()) {
            return AlertUtils.alertAndMove(response, "이미 경기가 진행된 경기입니다.", "/admin/match");
        }
        model.addAttribute("fieldInfo", fieldForm);
        model.addAttribute("editMatchForm", editMatchForm);

        return "/admin/admin_match_edit";
    }

    @PostMapping("/{matchId}/edit")
    public String matchEditPost(@PathVariable Long matchId, @ModelAttribute EditMatchRequest editMatchForm, HttpServletResponse response) throws NotExistsMatchException {
        System.out.println("editMatchForm = " + editMatchForm);
        Match match = matchService.findByMatch(matchId, "/admin/ground");

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
