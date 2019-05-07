package org.capvalue.recrute.metier;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;


public interface IUploadMetier {

    void uploadFile(MultipartHttpServletRequest request) throws IOException;
}
