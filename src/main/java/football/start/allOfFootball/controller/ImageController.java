package football.start.allOfFootball.controller;

import football.start.allOfFootball.common.file.FileService;
import football.start.allOfFootball.enums.FileUploadType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;

import static football.start.allOfFootball.enums.FileUploadType.FIELD_IMAGE;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageController {

    private final FileService fileService;

    @ResponseBody
    @GetMapping("/images/fieldImage/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileService.getFullPath(filename, FIELD_IMAGE));
    }
}
