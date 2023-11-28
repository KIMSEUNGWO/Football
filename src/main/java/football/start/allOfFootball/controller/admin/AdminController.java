package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/ground")
    public String ground() {
        return "admin_ground";
    }

    @GetMapping("/ground/add")
    public String groundAdd(@ModelAttribute SaveGroundForm saveGroundForm) {
        return "admin_ground_add";
    }

    @PostMapping("/ground/add")
    public String groundAddPost(@ModelAttribute SaveGroundForm saveGroundForm) {
        System.out.println("saveGroundForm = " + saveGroundForm);

        adminService.saveField(saveGroundForm);

        return "redirect:/admin/ground";
    }
}
