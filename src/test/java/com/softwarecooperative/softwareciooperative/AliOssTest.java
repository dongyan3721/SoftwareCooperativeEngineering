package com.softwarecooperative.softwareciooperative;

import com.softwarecooperative.softwareciooperative.utils.AliOssUtil;
import com.softwarecooperative.softwareciooperative.utils.uuid.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@SpringBootTest
class AliOssTest {

    @Test
    void contextLoads() {
    }

    @Autowired
    private AliOssUtil aliOSSUtil;
    @Test
    void ossTest() throws IOException {
        String fileName = "javaweb答辩.pptx";
        BufferedInputStream bis = new BufferedInputStream(
                this.getClass().getClassLoader().getResourceAsStream("testfile" + "/" + fileName));
        byte[] fileData = bis.readAllBytes();
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String uuidName = UUID.randomUUID() + ext;
        aliOSSUtil.upload(fileData, uuidName);
    }

}
