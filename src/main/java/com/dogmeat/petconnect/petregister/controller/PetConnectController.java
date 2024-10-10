package com.dogmeat.petconnect.petregister.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dogmeat.petconnect.petregister.service.PetConnectService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PetConnectController {

    @Autowired
    private PetConnectService petConnectService;

    @Value("${aws.s3.bucket.prefix}")
    private String s3Prefix;

    @PostMapping("/addOrphan")
    public int addOrphan(@RequestBody Map<String, Object> orphan) throws Exception {

        @SuppressWarnings("unchecked")
        List<Integer> photoByteList = ((List<Integer>) orphan.get("photo"));
        byte[] photoBytes = new byte[photoByteList.size()];

        // idot copy
        for (int i = 0; i < photoBytes.length; i++) {
            photoBytes[i] = photoByteList.get(i).byteValue();
        }

        String filename = String.format("%s.%s", UUID.randomUUID(), orphan.get("fileExt").toString());

        log.info(filename);

        // Decrypt
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                FileOutputStream fos = new FileOutputStream(String.format("/tmp/%s", filename))) {

            GZIPInputStream gzipInputStream = new GZIPInputStream(
                    new BufferedInputStream(new ByteArrayInputStream(photoBytes)));

            // Into byte arr
            int size = 0;
            byte buffer[] = new byte[1_024];
            while ((size = gzipInputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, size);
            }
            byteArrayOutputStream.flush();

            fos.write(byteArrayOutputStream.toByteArray());
            fos.flush();

            File file = new File(String.format("/tmp/%s", filename));

            log.info("addOrphan doin some shit {}", petConnectService.uploadFile(file, filename));
        } catch (Exception e) {
            throw e;
        }

        orphan.put("photo", String.format("%s/%s", s3Prefix, filename));

        log.debug("addOrphan called {}", orphan);
        return petConnectService.addOrphan(orphan);
    }
}
