package football.admin.controller;

import football.admin.dto.SearchMatchRequest;
import football.admin.dto.SearchMatchResponse;
import football.admin.dto.ViewMatchFieldForm;
import football.admin.exception.NotExistsFieldException;
import football.admin.service.AdminMatchService;
import football.common.common.alert.AlertUtils;
import football.common.domain.Field;
import football.common.domain.Match;
import football.common.enums.domainenum.LocationEnum;
import football.common.dto.match.EditMatchRequest;
import football.common.dto.match.SaveMatchRequest;
import football.admin.service.AdminService;
import football.admin.service.AdminFieldService;
import football.common.exception.match.NotExistsMatchException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/match")
public class AdminMatchController {

    private final AdminMatchService matchService;
    private final AdminFieldService adminFieldService;
    private final AdminService adminService;

    @ResponseBody
    @PostMapping("/get")
    public List<SearchMatchResponse> matchGet(@RequestBody SearchMatchRequest searchDto) {
        return adminService.getSearchMatchResult(searchDto);
    }
    @GetMapping
    public String match(Model model) {
        model.addAttribute("locations", LocationEnum.values());
        return "/admin/admin_match";
    }

    @GetMapping("/{fieldId:[0-9]}/add")
    public String matchAdd(@PathVariable Long fieldId, @ModelAttribute SaveMatchRequest saveMatchRequest, Model model, HttpServletResponse response) throws NotExistsFieldException {
        Field field = adminFieldService.findByField(fieldId);
        ViewMatchFieldForm fieldForm = new ViewMatchFieldForm(field);
        model.addAttribute("fieldInfo", fieldForm);
        model.addAttribute("fieldId", fieldId);
        return "/admin/admin_match_add";
    }

    @PostMapping("/{fieldId:[0-9]}/add")
    public String matchAddPost(@PathVariable Long fieldId, @ModelAttribute SaveMatchRequest saveMatchRequest) throws NotExistsFieldException {
        Field field = adminFieldService.findByField(fieldId);
        Match saveMatch = Match.build(field, saveMatchRequest);
        matchService.saveMatch(saveMatch);

        return "redirect:/admin/match";
    }

    @GetMapping("/{matchId:[0-9]}")
    public String matchView(@PathVariable Long matchId, Model model) throws NotExistsMatchException {
        Match match = matchService.findByMatch(matchId, "/admin/ground");
        Field field = match.getField();
        ViewMatchFieldForm fieldForm = new ViewMatchFieldForm(field);
        EditMatchRequest editMatchRequest = new EditMatchRequest(match);

        model.addAttribute("fieldInfo", fieldForm);
        model.addAttribute("editMatchRequest", editMatchRequest);
        return "/admin/admin_match_view";
    }


    @GetMapping("/{matchId:[0-9]}/edit")
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

    @PostMapping("/{matchId:[0-9]}/edit")
    public String matchEditPost(@PathVariable Long matchId, @ModelAttribute EditMatchRequest editMatchForm, HttpServletResponse response) throws NotExistsMatchException {
        System.out.println("editMatchForm = " + editMatchForm);
        Match match = matchService.findByMatch(matchId, "/admin/ground");

        matchService.editMatch(match, editMatchForm);

        return "redirect:/admin/match/" + matchId;
    }

}
