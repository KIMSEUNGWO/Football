package football.admin.controller;

import football.admin.dto.SearchFieldRequest;
import football.admin.dto.SearchFieldResponse;
import football.admin.exception.NotExistsFieldException;
import football.common.domain.Field;
import football.common.enums.domainenum.LocationEnum;
import football.common.dto.field.EditFieldForm;
import football.common.dto.field.SaveFieldRequest;
import football.admin.service.AdminService;
import football.admin.service.FieldService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/ground")
public class AdminFieldController {

    private final FieldService fieldService;
    private final AdminService adminService;

    @ResponseBody
    @PostMapping("/get")
    public List<SearchFieldResponse> fieldGet(@RequestBody SearchFieldRequest searchDto) {
        return adminService.getSearchFieldResult(searchDto);
    }

    @GetMapping
    public String field(Model model) {
        model.addAttribute("locations", LocationEnum.values());
        model.addAttribute("myName", "김승우");
        return "/admin/admin_ground";
    }

    @GetMapping("/add")
    public String fieldAdd(Model model) {
        model.addAttribute("locations", LocationEnum.values());
        return "/admin/admin_ground_add";
    }

    @PostMapping("/add")
    public String fieldAddPost(@ModelAttribute SaveFieldRequest saveFieldForm) {
        fieldService.saveField(saveFieldForm);
        return "redirect:/admin/ground";
    }

    @GetMapping("/{fieldId}")
    public String fieldView(@PathVariable Long fieldId, Model model) throws NotExistsFieldException {
        Field field = fieldService.findByField(fieldId);
        EditFieldForm form = fieldService.getEditFieldForm(field);

        model.addAttribute("editFieldForm", form);
        return "/admin/admin_ground_view";
    }

    @GetMapping("/{fieldId}/edit")
    public String fieldEdit(@PathVariable Long fieldId, Model model) throws NotExistsFieldException {
        Field field = fieldService.findByField(fieldId);
        EditFieldForm form = fieldService.getEditFieldForm(field);

        model.addAttribute("locations", LocationEnum.values());
        model.addAttribute("fieldId", fieldId);
        model.addAttribute("editFieldForm", form);
        return "/admin/admin_ground_edit";
    }

    @PostMapping("/{fieldId}/edit")
    public String fieldEditPost(@PathVariable Long fieldId, @ModelAttribute EditFieldForm editFieldForm) throws NotExistsFieldException {
        Field field = fieldService.findByField(fieldId);
        fieldService.editField(field, editFieldForm);

        return "redirect:/admin/ground/" + fieldId;
    }
}
