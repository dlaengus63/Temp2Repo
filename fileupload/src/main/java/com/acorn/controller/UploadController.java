package com.acorn.controller;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.tomcat.jni.File;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UploadController {

	
	// 1. 파일업로드 화면을 보여주는 URI : /uploadForm, Method:
	@GetMapping("/uploadForm")
	public void uploadForm() {
		
		log.info("UploadController::uploadForm() invoked.");
		
	} // uploadForm
	
	// 2. 파일업로드 요청을 처리하는 URI : /uploadFormAction, Method:
	@PostMapping("/uploadFormAction")
	public void uploadFormAction(
			Model model, 
			String name,
			@RequestParam("uploadFile") MultipartFile[] uploadFiles
			) throws IOException {
		
		log.info("UploadController::uploadFormAction() invoked.");
		log.info("\t+ name: " + name);
		log.info("\t+ uploadFiles: ", Arrays.toString(uploadFiles));
		
		List<String> successFiles = new ArrayList<>();
		
		for(MultipartFile file : uploadFiles) {
//			log.info("\t\t* file: " +file);
//			log.info("=======================================");
//			log.info("\t\t* 1.ContentType: " +file.getContentType());
//			log.info("\t\t* 2.Name: " +file.getName());
//			log.info("\t\t* 3.OriginalFilename: " +file.getOriginalFilename());
//			log.info("\t\t* 4.Size: " +file.getSize());
//			log.info("\t\t* 5.Clazz: " +file.getClass());
//			log.info("\t\t* 6.Resource: " +file.getResource());
//			log.info("\t\t******** 7.Byte[]: " +file.getBytes().length);
//			log.info("\t\t* 8.Empty: " +file.isEmpty());
			
			// 파일을 정해진 위치에 저장하기
			String uploadTempPath = "C:\\temp\\uploadTemp\\";
			String uploadTargetPath = "C:/temp/target/";
			
			File f = new File(uploadTempPath, file.getOriginalFilename());
			f.deleteOnExit(); // *******중요*******
			
			// 지정된 경로에 수신한 파일저장
			file.transferTo(f);
			
			// (**매우 중요**)
			// 임시경로에 저장된 파일을 최종 타켓 폴더에 저장
			f.renameTo(
					new File(
							uploadTargetPath, 
							file.getOriginalFilename()
							)
					);
			
//			log.info("9. camWrite: " + f.canWrite());
//			log.info("10. exists: " + f.exists());
//			log.info("11. uploadPath: " +f.getAbsolutePath());
			
			 successFiles.add(file.getOriginalFilename()); // requestScope에 저장됨.
			
		} // enhanced for
		
		log.info("Done.");
		model.addAttribute("successFiles", successFiles);
		
	} // uploadFormAction
	
	
} // end class
