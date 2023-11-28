package football.start.allOfFootball.common.file;

import football.start.allOfFootball.enums.FileUploadType;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final TypeConvert typeConvert;

    public int saveFile(List<MultipartFile> files, Long id, FileUploadType type) {

        int res = 0;

        for (MultipartFile file : files) {
            byte[] fileBytes = getBytes(file);
            String contentType = file.getContentType();

            if (fileBytes == null || contentType == null || !contentType.startsWith("image/") || file.isEmpty()) {
                continue;
            }

            String originalFilename = file.getOriginalFilename();
            String storeFileName = createFileName(originalFilename);

            String fullPath = getFullPath(storeFileName, type);
            try {
                file.transferTo(new java.io.File(fullPath));
            } catch (IOException e) {
                log.error("saveFile transferTo error = {}", file.getName());
                continue;
            }

            FileUploadDto fileDto = new FileUploadDto();
            fileDto.setId(id);
            fileDto.setImageUploadName(originalFilename);
            fileDto.setImageStoreName(storeFileName);
            fileDto.setType(type);

            res += typeConvert.saveFile(fileDto);

        }

        return res;
    }

    @Nullable
    private static byte[] getBytes(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            return bytes;
        } catch (IOException e) {
            log.error("saveFile getBytes error = {}", file.getName());
            return null;
        }
    }

    private String getFullPath(String fileName, FileUploadType type) {
        StringBuffer sb = new StringBuffer(fileDir);
        String folderName = type.getDir();

        sb.append(folderName).append("/").append(fileName);
        return sb.toString();
    }

    private String createFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();

        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos);

        return uuid + ext;
    }

    private boolean imageFile(byte[] fileBytes) {
        if (fileBytes == null) {
            return false;
        }
        if(fileBytes.length>=2 && fileBytes[0] == (byte) 0xFF && fileBytes[1] == (byte) 0xD8 ) {
            // JPEG
            return true;
        }
        if(fileBytes.length >= 3 && fileBytes[0] == (byte) 'G' && fileBytes[1] == (byte) 'I' && fileBytes[2] == (byte) 'F') {
            // GIF
            return true;
        }
        if(fileBytes.length >= 8 && fileBytes[0] == (byte) 0x89 && fileBytes[1] == (byte) 0x50 &&
            fileBytes[2] == (byte) 0x4E && fileBytes[3] == (byte) 0x47 && fileBytes[4] == (byte) 0x0D &&
            fileBytes[5] == (byte) 0x0A && fileBytes[6] == (byte) 0x1A && fileBytes[7] == (byte) 0x0A) {
            // PNG
            return true;
        }
        return false;
    }
}