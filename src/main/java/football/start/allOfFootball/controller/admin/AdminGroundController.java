package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.common.alert.AlertUtils;
import football.start.allOfFootball.domain.Field;
import football.start.allOfFootball.enums.LocationEnum;
import football.start.allOfFootball.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/ground")
public class AdminGroundController {

    private final AdminService adminService;

    @GetMapping
    public String ground(Model model) {
        LocationEnum[] locations = LocationEnum.values();
        model.addAttribute("locations", locations);
        return "admin_ground";
    }

    @GetMapping("/add")
    public String groundAdd(@ModelAttribute SaveFieldForm saveFieldForm, Model model) {
        LocationEnum[] locations = LocationEnum.values();
        model.addAttribute("locations", locations);
        return "admin_ground_add";
    }

    @PostMapping("/add")
    public String groundAddPost(@ModelAttribute SaveFieldForm saveFieldForm) {
        System.out.println("saveGroundForm = " + saveFieldForm);

        adminService.saveField(saveFieldForm);

        return "redirect:/admin/ground";
    }

    @GetMapping("/{fieldId}")
    public String groundView(@PathVariable Long fieldId, HttpServletResponse response, Model model) {

        EditFieldForm form = adminService.findByFieldId(fieldId);
        if (form == null) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 구장입니다.", "/admin/ground");
        }
        model.addAttribute("editFieldForm", form);

        return "admin_ground_view";
    }

    @GetMapping("/{fieldId}/edit")
    public String groundEdit(@PathVariable Long fieldId, HttpServletResponse response, Model model) {

        EditFieldForm form = adminService.findByFieldId(fieldId);

        if (form == null) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 구장입니다.", "/admin/ground");
        }
        LocationEnum[] locations = LocationEnum.values();
        model.addAttribute("locations", locations);
        model.addAttribute("fieldId", fieldId);
        model.addAttribute("editFieldForm", form);
        return "admin_ground_edit";
    }

    @PostMapping("/{fieldId}/edit")
    public String groundEditPost(@PathVariable Long fieldId, HttpServletRequest request, HttpServletResponse response, @ModelAttribute EditFieldForm editFieldForm) {

        System.out.println("editFieldForm = " + editFieldForm);
        Long requestURIFieldId = getFieldId(request.getRequestURI());
        if (requestURIFieldId == null || fieldId != requestURIFieldId) {
            return AlertUtils.alertAndMove(response, "잘못된 경로입니다.", "/admin/ground");
        }

        Optional<Field> findField = adminService.findByField(fieldId);
        if (findField.isEmpty()) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 구장입니다.", "/admin/ground");
        }
        Field field = findField.get();
        adminService.editField(field, editFieldForm);

        return "redirect:/admin/ground/" + fieldId;
    }

    private Long getFieldId(String requestURI) {
        // /ground/{fieldId}/edit 형식 -> {fieldId} Long 타입으로 변환
        String numStr = requestURI.replaceAll("[^0-9]", "");
        try {
            Long fieldId = Long.parseLong(numStr);
            return fieldId;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
