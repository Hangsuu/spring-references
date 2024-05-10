package com.references.common.fileUpload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Data
@Component
@ConfigurationProperties(prefix = "custom.fileupload")
public class FileUploadProperties {
    private String path;

    public File getDirectory() {
        return new File(path);
    }
}