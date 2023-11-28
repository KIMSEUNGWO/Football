package football.start.allOfFootball.controller.admin;

import football.start.allOfFootball.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminRestController {

    private final AdminService adminService;

    @PostMapping("/ground/get")
    public List<SearchFieldForm> groundGet(@RequestBody SearchDto searchDto) {
        System.out.println("searchDto = " + searchDto);

        List<SearchFieldForm> result = adminService.getSearchResult(searchDto);
        for (SearchFieldForm searchFieldForm : result) {
            System.out.println("searchFieldForm = " + searchFieldForm);
        }
        return result;
    }
}
