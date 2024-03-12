package com.softwarecooperative.softwareciooperative;

import com.softwarecooperative.softwareciooperative.utils.alioss.AliOSSUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@SpringBootTest
class SoftwareciooperativeApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private AliOSSUtils aliOSSUtils;
    @Test
    void ossTest() throws IOException {
        File file = new File("C:\\Users\\14838\\Desktop\\102014650_p0.jpg");
        byte[] fileData = Files.readAllBytes(file.toPath());
        MultipartFile testFile = new MockMultipartFile(file.getName(), file.getName(), "image/jpeg", fileData);
//        System.out.println(testFile.getName());
//        System.out.println(testFile.getOriginalFilename());
        aliOSSUtils.upload(testFile);
    }

}
