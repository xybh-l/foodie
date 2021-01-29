package com.xybh.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 23:43 2021/1/27
 * @Modified:
 */
@PropertySource("classpath:file-upload-prod.properties")
@Component
public class FileUpload {

    @Value("${file.imageUserFaceLocation}")
    private String imageUserFaceLocation;
    @Value("${file.imageServerUrl}")
    private String imageServerUrl;

    public String getImageServerUrl() {
        return imageServerUrl;
    }

    public void setImageServerUrl(String imageServerUrl) {
        this.imageServerUrl = imageServerUrl;
    }

    public String getImageUserFaceLocation() {
        return imageUserFaceLocation;
    }

    public void setImageUserFaceLocation(String imageUserFaceLocation) {
        this.imageUserFaceLocation = imageUserFaceLocation;
    }
}
