package com.devano.blog_app.service;

import com.google.cloud.Identity;
import com.google.cloud.Policy;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@PropertySource("classpath:cloud-storage.properties")
public class CloudStorageService {

    @Value("${storage.project-id}")
    private String projectId;
    @Value("${storage.bucket-name}")
    private String bucketName;

    public void uploadFile(MultipartFile multipartFile) throws IOException {
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        Policy originalPolicy = storage.getIamPolicy(bucketName);
        storage.setIamPolicy(bucketName, originalPolicy.toBuilder()
                .addIdentity(StorageRoles.objectViewer(), Identity.allUsers()).build()
        );
        String fileName = multipartFile.getName() + ".jpg";
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .build();

        InputStream fileInputStream = multipartFile.getInputStream();


        Storage.BlobWriteOption precondition;
        if (storage.get(bucketName, fileName) == null) {
            precondition = Storage.BlobWriteOption.doesNotExist();
        } else {
            precondition =
                    Storage.BlobWriteOption.generationMatch(
                            storage.get(bucketName, fileName).getGeneration());
        }
        storage.createFrom(blobInfo, fileInputStream, precondition);
    }
}
