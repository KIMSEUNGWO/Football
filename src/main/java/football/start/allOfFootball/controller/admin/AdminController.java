package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.common.alert.AlertUtils;
import football.start.allOfFootball.enums.LocationEnum;
import football.start.allOfFootball.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/ground")
    public String ground(Model model) {
        LocationEnum[] locations = LocationEnum.values();
        model.addAttribute("locations", locations);
        return "admin_ground";
    }

    @GetMapping("/ground/add")
    public String groundAdd(@ModelAttribute SaveFieldForm saveFieldForm, Model model) {
        LocationEnum[] locations = LocationEnum.values();
        model.addAttribute("locations", locations);
        return "admin_ground_add";
    }

    @PostMapping("/ground/add")
    public String groundAddPost(@ModelAttribute SaveFieldForm saveFieldForm) {
        System.out.println("saveGroundForm = " + saveFieldForm);

        adminService.saveField(saveFieldForm);

        return "redirect:/admin/ground";
    }

    @GetMapping("/ground/{fieldId}")
    public String groundView(@PathVariable Long fieldId, HttpServletResponse response, Model model) {

        EditFieldForm form = adminService.findByFieldId(fieldId);
        if (form == null) {
            AlertUtils.alertAndBack(response, "존재하지 않는 구장입니다.");
            return null;
        }
        model.addAttribute("fieldForm", form);

        return "admin_ground_view";
    }

    @GetMapping("/ground/{fieldId}/edit")
    public String groundEdit(@PathVariable Long fieldId, HttpServletResponse response, Model model) {

        EditFieldForm form = adminService.findByFieldId(fieldId);

        if (form == null) {
            AlertUtils.alertAndMove(response, "존재하지 않는 구장입니다.", "/admin/ground");
            return null;
        }
        LocationEnum[] locations = LocationEnum.values();
        model.addAttribute("locations", locations);
        model.addAttribute("fieldId", fieldId);
        model.addAttribute("editFieldForm", form);
        return "admin_ground_edit";
    }
}
