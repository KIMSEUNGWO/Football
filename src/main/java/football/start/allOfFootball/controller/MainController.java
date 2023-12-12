package football.start.allOfFootball.controller;

import football.start.allOfFootball.dto.MainSideInfoForm;
import football.start.allOfFootball.dto.SearchDto;
import football.start.allOfFootball.dto.SearchResultForm;
import football.start.allOfFootball.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static football.start.allOfFootball.SessionConst.LOGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping
    public String main(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, Model model) {
        if (memberId != null) {
            MainSideInfoForm form = mainService.getSideInfo(memberId);
            model.addAttribute("side", form);
        }
        return "main";
    }

    @ResponseBody
    @PostMapping("/get")
    public List<SearchResultForm> search(@RequestBody SearchDto searchDto) {
        System.out.println("searchDto = " + searchDto);

        List<SearchResultForm> result = mainService.getSearchResult(searchDto);
        for (SearchResultForm searchResultForm : result) {
            System.out.println("searchResultForm = " + searchResultForm);
        }
        return result;
    }
}
