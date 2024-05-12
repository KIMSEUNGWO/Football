package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.common.alert.AlertUtils;
import football.internal.database.domain.Field;
import football.start.allOfFootball.enums.LocationEnum;
import football.start.allOfFootball.service.AdminService;
import football.start.allOfFootball.service.domainService.FieldService;
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
@RequestMapping("/admin/ground")
public class AdminGroundController {

    private final FieldService fieldService;
    private final AdminService adminService;

    @ResponseBody
    @PostMapping("/get")
    public List<SearchFieldForm> groundGet(@RequestBody SearchFieldDto searchDto) {
        return adminService.getSearchFieldResult(searchDto);
    }

    @GetMapping
    public String ground(Model model) {
        model.addAttribute("locations", LocationEnum.values());
        model.addAttribute("myName", "김승우");
        return "/admin/admin_ground";
    }

    @GetMapping("/add")
    public String groundAdd(@ModelAttribute SaveFieldForm saveFieldForm, Model model) {
        model.addAttribute("locations", LocationEnum.values());
        return "/admin/admin_ground_add";
    }

    @PostMapping("/add")
    public String groundAddPost(@ModelAttribute SaveFieldForm saveFieldForm) {
        fieldService.saveField(saveFieldForm);
        return "redirect:/admin/ground";
    }

    @GetMapping("/{fieldId}")
    public String groundView(@PathVariable Long fieldId, HttpServletResponse response, Model model) {
        Optional<Field> byField = fieldService.findByField(fieldId);
        if (byField.isEmpty()) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 구장입니다.", "/admin/ground");
        }
        Field field = byField.get();
        EditFieldForm form = fieldService.getEditFieldForm(field);

        model.addAttribute("editFieldForm", form);
        return "/admin/admin_ground_view";
    }

    @GetMapping("/{fieldId}/edit")
    public String groundEdit(@PathVariable Long fieldId, HttpServletResponse response, Model model) {
        Optional<Field> byField = fieldService.findByField(fieldId);
        if (byField.isEmpty()) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 구장입니다.", "/admin/ground");
        }
        Field field = byField.get();
        EditFieldForm form = fieldService.getEditFieldForm(field);

        model.addAttribute("locations", LocationEnum.values());
        model.addAttribute("fieldId", fieldId);
        model.addAttribute("editFieldForm", form);
        return "/admin/admin_ground_edit";
    }

    @PostMapping("/{fieldId}/edit")
    public String groundEditPost(@PathVariable Long fieldId, HttpServletResponse response, @ModelAttribute EditFieldForm editFieldForm) {
        Optional<Field> findField = fieldService.findByField(fieldId);
        if (findField.isEmpty()) {
            return AlertUtils.alertAndMove(response, "존재하지 않는 구장입니다.", "/admin/ground");
        }
        Field field = findField.get();
        fieldService.editField(field, editFieldForm);

        return "redirect:/admin/ground/" + fieldId;
    }
}
