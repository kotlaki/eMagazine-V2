package com.kurganov.webserver.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String saveFile(MultipartFile file);

}
