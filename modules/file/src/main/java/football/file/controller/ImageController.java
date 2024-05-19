package football.file.controller;

import football.file.enums.FileUploadType;
import football.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageController {

    private final FileService fileService;

    @ResponseBody
    @GetMapping("/images/{fileUploadType}/{filename}")
    public Resource downloadImage(@PathVariable String fileUploadType, @PathVariable String filename) throws MalformedURLException {
        System.out.println("fileUploadType = " + fileUploadType);
        FileUploadType type = FileUploadType.findDir(fileUploadType);
        return new UrlResource("file:" + fileService.getFullPath(filename, type));
    }
}