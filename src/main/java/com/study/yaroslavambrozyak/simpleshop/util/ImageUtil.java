package com.study.yaroslavambrozyak.simpleshop.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class ImageUtil {

    private final String ROOT = System.getProperty("java.io.tmpdir");

    public String saveImage(CommonsMultipartFile image) throws IOException {
        String path = ROOT+ File.separator+image.getOriginalFilename();
        File file = new File(path);
        image.transferTo(file);
        return image.getOriginalFilename();
    }
}
