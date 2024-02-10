package com.example.insiderback.common.fileUpload.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttachmentVO {
    private int attachmentNo;
    private String attachmentName;
    private String attachmentType;
    private long attachmentSize;
    private String attachmentRoute;
}
