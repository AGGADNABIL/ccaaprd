package org.capvalue.recrute.service;


import org.capvalue.recrute.conf.services;
import org.capvalue.recrute.domaine.Candidat;
import org.capvalue.recrute.domaine.NosInfos;
import org.capvalue.recrute.domaine.NosMetier;
import org.capvalue.recrute.domaine.Reference;
import org.capvalue.recrute.metier.ICandidatMetier;
import org.capvalue.recrute.metier.IPostulerMetier;
import org.capvalue.recrute.metier.IReferenceMetier;
import org.capvalue.recrute.metier.IUploadMetier;
import org.capvalue.recrute.metier.NosInfosMetier;
import org.capvalue.recrute.metier.NosMetierMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Random;



@CrossOrigin(origins = "*")
@RestController
	public class UploadService {
		@Autowired
		IUploadMetier uploadMetier;
		@Autowired
		IPostulerMetier postulerMetier;
		@Autowired
		IReferenceMetier referenceMetier;
		
		@Autowired    
		private ICandidatMetier candidatMetier;	
		
//		@Autowired
//		private NosMetierMetier nosMetierMetier;
//		
//		@Autowired
//		private NosInfosMetier nosInfosMetier;
		
		public static String  imgInfos ;
		
		//distant
	    //private String pathImg="/home/upload/images/";
		//private String pathCV="/home/upload/CVS/";
		
		private Random r = new Random();
		//upload dans projet
		
		private  String pathImg="D:\\jobs\\cap\\apps\\recruteCapvalue\\src\\main\\resources\\static\\app\\fileUpload\\";
		private  String pathCV="D:\\jobs\\cap\\apps\\recruteCapvalue\\src\\main\\resources\\static\\app\\fileUpload\\CV\\";
		
		// local windows
		 //private String pathCV="C:\\upload\\CVS\\";
		 //private String pathImg="C:\\upload\\images\\";
		
		private static final int BUFFER_SIZE = 4096;

		@RequestMapping(value="/fileUpload", method = RequestMethod.POST)
		public void UploadFile(MultipartHttpServletRequest request) {
			try {
				uploadMetier.uploadFile(request);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@RequestMapping (value="/downloadPostuler/{name}/f", method=RequestMethod.GET )
		public void doDownloadPostuler(HttpServletRequest request,@PathVariable(value="name")String name,
		        HttpServletResponse response) throws IOException {

			String filename =name;
		    ServletContext ctx=request.getServletContext();
		    String filePath=pathCV+filename;
		    
		    File downloadFile = new File(pathCV,filename);
		    FileInputStream inputStream = new FileInputStream(downloadFile);
		    String mimeType = ctx.getMimeType(filePath);
		    if (mimeType == null) {
		        mimeType = "application/octet-stream";
		    }
		    System.out.println("MIME type: " + mimeType);
		    response.setContentType(mimeType);
		    response.setContentLength((int) downloadFile.length());
		    String headerKey = "Content-Disposition";
		    String headerValue = String.format("attachment; filename=\"%s\"",
		            downloadFile.getName());
		    response.setHeader(headerKey, headerValue);
		    OutputStream outStream = response.getOutputStream();
		    byte[] buffer = new byte[BUFFER_SIZE];
		    int bytesRead = -1;
		    while ((bytesRead = inputStream.read(buffer)) != -1) {
		        outStream.write(buffer, 0, bytesRead);
		    }

		    inputStream.close();
		    outStream.close();
		}
		
		@RequestMapping (value="/download", method=RequestMethod.GET )
		public void doDownload(HttpServletRequest request,
		        HttpServletResponse response) throws IOException {

			String filename =candidatMetier.findOneCandidat(services.username).getCv();
		    ServletContext ctx=request.getServletContext();
		    String filePath=pathCV+filename;
		    
		    File downloadFile = new File(pathCV,filename);
		    FileInputStream inputStream = new FileInputStream(downloadFile);
		    String mimeType = ctx.getMimeType(filePath);
		    if (mimeType == null) {
		        mimeType = "application/octet-stream";
		    }
		    System.out.println("MIME type: " + mimeType);
		    response.setContentType(mimeType);
		    response.setContentLength((int) downloadFile.length());
		    String headerKey = "Content-Disposition";
		    String headerValue = String.format("attachment; filename=\"%s\"",
		            downloadFile.getName());
		    response.setHeader(headerKey, headerValue);
		    OutputStream outStream = response.getOutputStream();
		    byte[] buffer = new byte[BUFFER_SIZE];
		    int bytesRead = -1;
		    while ((bytesRead = inputStream.read(buffer)) != -1) {
		        outStream.write(buffer, 0, bytesRead);
		    }

		    inputStream.close();
		    outStream.close();
		}

	
		@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
		@ResponseBody
		public ResponseEntity<?> uploadFile(
		    @RequestParam("uploadfile") MultipartFile uploadfile,HttpServletRequest request) {
		  
		  try {
		   
			  String file=uploadfile.getOriginalFilename();
				String extens=file.split("\\.")[1];
				String ex[]=file.split("\\.");
				System.out.println("extension : "+ex[ex.length-1]);
				System.out.println("extens :"+extens);
				String filename="img_"+services.username +"_"+r.nextInt(Math.round(new Date().getTime() / 1000)) +"_."+extens;    
		     String filepath=filename;
		    saveFile(filename);
		    // Save the file locally
		    BufferedOutputStream stream =
		        new BufferedOutputStream(new FileOutputStream(new File(pathImg,filename)));
		    System.out.println("path est :"+filepath);
		    
		    stream.write(uploadfile.getBytes());
		    System.out.println("filename is : "+filename); 
		    stream.close();
		    
		  }
		  catch (Exception e) {
		    System.out.println(e.getMessage());
		    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		  }
		  
		  return new ResponseEntity<>(HttpStatus.OK);
		} // method uploadFile
	
		@RequestMapping(value = "/uploadReference", method = RequestMethod.POST)
		@ResponseBody
		public ResponseEntity<?> uploadReference(
		    @RequestParam("uploadfile") MultipartFile uploadfile,HttpServletRequest request) {
		  
		  try {
		   
			    String file=uploadfile.getOriginalFilename();
				String extens=file.split("\\.")[1];
				//String ex[]=file.split("\\.");
				//System.out.println("extension : "+ex[ex.length-1]);
				System.out.println("extens :"+extens);
				
				

				String filename="ref_"+r.nextInt(Math.round(new Date().getTime() / 1000)) +"_."+extens;
		    Reference reference=new Reference(filename,new Date());
		    //reference.setReference();
		    referenceMetier.saveReference(reference);
		    // Save the file locally
		    BufferedOutputStream stream =
		        new BufferedOutputStream(new FileOutputStream(new File(pathImg,filename)));
		  //  System.out.println("path est :"+filepath);
		    stream.write(uploadfile.getBytes());
		    System.out.println("filename is : "+filename);
		    stream.close();
		    
		  }
		  catch (Exception e) {
		    System.out.println("msgError :"+e.getMessage());
		    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		  }
		  
		  return new ResponseEntity<>(HttpStatus.OK);
		} // method uploadFile
	
		
		@RequestMapping(value = "/uploadCv", method = RequestMethod.POST)
		@ResponseBody
		public ResponseEntity<?> uploadCV(
		    @RequestParam("uploadfile") MultipartFile uploadfile, HttpServletRequest request) {
		  
		  try {
		   
			String file=uploadfile.getOriginalFilename();
			String extens=file.split("\\.")[1];
			//String ex[]=file.split("\\.");
			//System.out.println("extension : "+ex[ex.length-1]);
			System.out.println("extens :"+extens);
			String filename="cv_"+services.username +"_"+r.nextInt(Math.round(new Date().getTime() / 1000))+"_."+extens;
		    saveCV(filename);
		    // Save the file locally
		    BufferedOutputStream stream =
		        new BufferedOutputStream(new FileOutputStream(new File(pathCV,filename)));
		    System.out.println("path est :"+filename);
		    
		    stream.write(uploadfile.getBytes());
		    System.out.println("filename is : "+filename);
		    
		    stream.close();
		    
		  }
		  catch (Exception e) {
		    System.out.println(e.getMessage());
		    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		  }
		  
		  return new ResponseEntity<>(HttpStatus.OK);
		} // method uploadFile
		
		
		
		@RequestMapping(value = "/uploadMetier", method = RequestMethod.POST)
		@ResponseBody
		public ResponseEntity<?> uploadMetier(
		    @RequestParam("uploadfile") MultipartFile uploadfile,HttpServletRequest request) {
		  
		  try {
			    String file=uploadfile.getOriginalFilename();
				String extens=file.split("\\.")[1];
				String ex[]=file.split("\\.");
				System.out.println("extension : "+ex[ex.length-1]);
				System.out.println("extens :"+extens);
				String filename="Metier_"+r.nextInt(Math.round(new Date().getTime() / 1000)) +"_."+extens;
				imgInfos=filename;
				//saveInfos(filename);
				// Save the file locally
		    BufferedOutputStream stream =
		        new BufferedOutputStream(new FileOutputStream(new File(pathImg,filename)));
		        //  System.out.println("path est :"+filepath);
		    stream.write(uploadfile.getBytes());
		    System.out.println("filename is : "+filename);
		    stream.close();
		    
		  }
		  catch (Exception e) {
		    System.out.println(e.getMessage());
		    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		  }
		  
		  return new ResponseEntity<>(HttpStatus.OK);
		} // method uploadFile

		public void saveFile(String s){
			                                      
			Candidat c=candidatMetier.findOneCandidat(services.username);
			System.out.println("current User name :"+services.username);
			System.out.println("name Candidat :"+c.getNom());
			c.setPhoto(s);
			candidatMetier.updateCandidat(c);
		}
		
		public void saveCV(String s){  
			Candidat c=candidatMetier.findOneCandidat(services.username);
			System.out.println("current User name :"+services.username);
			System.out.println("name Candidat :"+c.getNom());
			c.setCv(s);
			candidatMetier.updateCandidat(c);
		} 
			
}

//String realpath=ctx.getRealPath("/");
//String path=realpath.substring(0,realpath.length()-7);
//System.out.println("path   :"+path);
//String filePath=path+"resources\\static\\app\\fileUpload\\CV\\"+filename;
//System.out.println("file path :"+filePath);

//String path="/home/capvalue/upload/CVS/";
//String path="/home/capvalue/upload/CVS/";

//download
//String realpath=ctx.getRealPath("/");
//String path=realpath.substring(0,realpath.length()-7);
//System.out.println("path   :"+path);
//String filePath=path+"resources\\static\\app\\fileUpload\\CV\\"+filename;
//System.out.println("file path :"+filePath);

//String path="/home/capvalue/upload/CVS/";
//String path="\\home\\capvalue\\upload\\CVS\\";

//uploadFile
//ServletContext ctx=request.getServletContext();
//		    String realpath=ctx.getRealPath("/");
//		    String path=realpath.substring(0,realpath.length()-7);
//		    System.out.println("path   :"+path); 
//String filepath=path+"resources\\static\\app\\fileUpload\\"+filename;
// String filepath=null;    /* path+"resources\\static\\app\\fileUpload\\"+filename;*/
//System.out.println("file path :"+filepath);	    
// String path="\\opt\\tomcat\\webapps\\upload\\images\\";

 //String path="/home/capvalue/upload/images/";
 //String path="\\home\\capvalue\\upload\\images\\";


//uploadReference

//ServletContext ctx=request.getServletContext();
//String realpath=ctx.getRealPath("/");
//String path=realpath.substring(0,realpath.length()-7);
//System.out.println("path   :"+path);
//String filepath=path+"resources\\static\\app\\refrences\\"+filename;
//System.out.println("file path :"+filepath);

//String path="\\opt\\tomcat\\webapps\\upload\\images\\";
//String path="/home/capvalue/upload/images/";
//String path="\\home\\capvalue\\upload\\images\\";

//uploadCv

//System.out.println("name :"+filename);
//ServletContext ctx=request.getServletContext();
// String realpath=ctx.getRealPath("/");

//String path=realpath.substring(0,realpath.length()-7);
//System.out.println("path   :"+path);
//String filepath=path+"resources\\static\\app\\fileUpload\\CV\\"+filename;
//System.out.println("file path :"+filepath);

//String path="E:\\tmp\\images\\CV\\";
//String path="\\opt\\tomcat\\webapps\\upload\\CVS\\";

//String path="/home/capvalue/upload/CVS/";
//String path="\\home\\capvalue\\upload\\CVS\\";
