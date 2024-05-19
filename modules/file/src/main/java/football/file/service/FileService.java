package football.file.service;

import football.common.domain.ImageChild;
import football.common.domain.Member;
import football.common.domain.ImageParent;
import football.file.dto.FileUploadDto;
import football.file.enums.FileUploadType;
import football.file.repository.FileRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    @Value("${file.dir}")
    private String FILE_DIR;

    private final FileRepository fileRepository;

    public int saveFile(List<MultipartFile> files, ImageParent parent, FileUploadType type) {

        List<ImageChild> images = new ArrayList<>(files.size());

        for (MultipartFile file : files) {
            String contentType = file.getContentType();

            if (getBytes(file) == null || contentType == null || !contentType.startsWith("image/") || file.isEmpty()) {
                continue;
            }

            String originalFileName = file.getOriginalFilename();
            String storeFileName = createFileName(originalFileName);

            String fullPath = getFullPath(storeFileName, type);
            try {
                file.transferTo(new File(fullPath));
            } catch (IOException e) {
                log.error("saveFile transferTo error = {}", file.getName());
                continue;
            }

            ImageChild entity = type.createEntity(parent, originalFileName, storeFileName);
            if (entity != null) {
                images.add(entity);
            }
        }
        fileRepository.saveAll(images);
        return images.size();
    }

    public void saveImage(String imageUrl, Member member, FileUploadType type) {
        try {
            URL url = new URL(imageUrl);
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());

            String originalFileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            String storeFileName = createFileName(originalFileName);

            String fullPath = getFullPath(storeFileName, type);
            FileOutputStream fos = new FileOutputStream(fullPath);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();

            ImageChild entity = type.createEntity(member, originalFileName, storeFileName);
            fileRepository.save(entity);

        } catch (IOException e) {
            log.error("링크 파일 저장 실패 : ", imageUrl);
        }
    }

    public boolean removeFile(String fileStoreName, FileUploadType type) {
        String fullPath = getFullPath(fileStoreName, type);
        File file = new File(fullPath);
        log.info("removeFile fullPath = {}", fullPath);
        return file.delete();
    }

    @Nullable
    private byte[] getBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            log.error("saveFile getBytes error = {}", file.getName());
            return null;
        }
    }

    public String getFullPath(String fileName, FileUploadType type) {
        StringBuffer sb = new StringBuffer(FILE_DIR);

        if (type == null) {
            return sb.append("/").append(fileName).toString();
        }

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
