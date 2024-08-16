package project.wideWebsite.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService { //이미지 파일 저장 로직 담당

    // 이미지 파일 업로드
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{

        // UUID를 이용해 파일명 새로 작성
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;

        // 경로 + 파일명
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        // FileOutputStream 객체를 이용해 경로 지정 후 파일 저장
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();

        return savedFileName;
    }

    // 이미지 파일 삭제
    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);

        if(deleteFile.exists()){
            deleteFile.delete();
            log.info("파일을 삭제했습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
