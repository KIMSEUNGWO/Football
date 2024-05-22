package football.admin.controller;

import football.admin.dto.SearchFieldRequest;
import football.admin.dto.SearchFieldResponse;
import football.admin.exception.NotExistsFieldException;
import football.common.domain.Field;
import football.common.dto.field.EditFieldRequest;
import football.common.enums.domainenum.LocationEnum;
import football.common.dto.field.SaveFieldRequest;
import football.admin.service.AdminService;
import football.admin.service.AdminFieldService;
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

    private final AdminFieldService adminFieldService;
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
    public String fieldAdd(Model model, SaveFieldRequest saveFieldRequest) {
        model.addAttribute("locations", LocationEnum.values());
        return "/admin/admin_ground_add";
    }

    @PostMapping("/add")
    public String fieldAddPost(@ModelAttribute SaveFieldRequest saveFieldRequest) {
        adminFieldService.saveField(saveFieldRequest);
        return "redirect:/admin/ground";
    }

    @GetMapping("/{fieldId}")
    public String fieldView(@PathVariable Long fieldId, Model model) throws NotExistsFieldException {
        Field field = adminFieldService.findByField(fieldId);
        EditFieldRequest form = adminFieldService.getEditFieldRequest(field);

        model.addAttribute("editFieldRequest", form);
        return "/admin/admin_ground_view";
    }

    @GetMapping("/{fieldId}/edit")
    public String fieldEdit(@PathVariable Long fieldId, Model model) throws NotExistsFieldException {
        Field field = adminFieldService.findByField(fieldId);
        EditFieldRequest form = adminFieldService.getEditFieldRequest(field);

        model.addAttribute("locations", LocationEnum.values());
        model.addAttribute("fieldId", fieldId);
        model.addAttribute("editFieldRequest", form);
        return "/admin/admin_ground_edit";
    }

    @PostMapping("/{fieldId}/edit")
    public String fieldEditPost(@PathVariable Long fieldId, @ModelAttribute EditFieldRequest editFieldRequest) throws NotExistsFieldException {
        Field field = adminFieldService.findByField(fieldId);
        adminFieldService.editField(field, editFieldRequest);

        return "redirect:/admin/ground/" + fieldId;
    }
}
