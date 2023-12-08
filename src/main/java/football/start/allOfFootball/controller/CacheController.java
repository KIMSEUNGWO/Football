package football.start.allOfFootball.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CacheController {

    @GetMapping("/cache/charge")
    public String cache() {

        return "cache_charge";
    }
}
