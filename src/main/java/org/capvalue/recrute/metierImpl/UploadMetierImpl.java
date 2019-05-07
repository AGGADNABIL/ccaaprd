package org.capvalue.recrute.metierImpl;


import org.capvalue.recrute.metier.IUploadMetier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;


@Service
public class UploadMetierImpl implements IUploadMetier {
	
	    //private String path="C:\\upload\\CVS\\";
	    
	  //private String path="/home/upload/CVS/";
	  private String path="D:\\jobs\\cap\\apps\\recruteCapvalue\\src\\main\\resources\\static\\app\\fileUpload\\CV\\";
    // @Override
    public void uploadFile(MultipartHttpServletRequest request) throws IOException {

        //List of files to upload
        Iterator<String> files=request.getFileNames();
        //Directory used to save files
        //String path="/home/upload/CVS/";
        File dir = new File(path);
        
        //File from
        MultipartFile file = null;
        //Instance of file to be stored
        File serverFile = null;

        BufferedOutputStream stream = null;

        if (dir.isDirectory()) {    
            while(files.hasNext()){
                file=request.getFile(files.next());
                serverFile = new File(dir,file.getName());
                stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(file.getBytes());
                stream.close();
            }
        }else{
            System.out.print("Directory not found !");
        }

    }
}
