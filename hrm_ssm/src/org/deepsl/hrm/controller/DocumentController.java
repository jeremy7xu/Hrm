package org.deepsl.hrm.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.deepsl.hrm.domain.Dept;
import org.deepsl.hrm.domain.Document;
import org.deepsl.hrm.domain.User;
import org.deepsl.hrm.service.DocumentService;
import org.deepsl.hrm.service.HrmService;
import org.deepsl.hrm.util.common.HrmConstants;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Description: 处理上传下载文件请求控制器
 * @version V1.0   
 */

@Controller
@RequestMapping("document")
public class DocumentController {

	@Autowired
	DocumentService documentService;
	
	@RequestMapping("addDocument")
	public ModelAndView addDocument(HttpServletRequest request, String flag, Document document){
		
		ModelAndView mv = new ModelAndView();
		try {
			
			if ("1".equals(flag)) {
				
				mv.setViewName("document/showAddDocument");
				
			}else if ("2".equals(flag)) {
				//设置文件上传人
				MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
				
				MultipartFile file = multipartHttpServletRequest.getFile("file");
								
				HttpSession session = request.getSession(false);
				if (null!=session && session.getAttribute(HrmConstants.USER_SESSION) != null) {
					User user = (User) session.getAttribute(HrmConstants.USER_SESSION);
					document.setUser(user);
				}
				
				document.setCreateDate(new Date());
				
				document.setFile(file);
				if (file != null) {
					//设置文件名并保存文件到WEB-INF/upload目录下
					String parent = request.getServletContext().getRealPath("/WEB-INF/upload");
					
					String fileName = file.getOriginalFilename();
					
					File newfile = new File(parent, fileName);
					
					file.transferTo(newfile);
					
					document.setFileName(fileName);
					documentService.addDocument(document);
				}
			
				mv.setViewName("redirect:/document/selectDocument");
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			
			mv.addObject("error", e.getMessage());
			mv.setViewName("document/showAddDocument");
		}
		
		
		return mv;
	}
	
	
	@RequestMapping("selectDocument")
	public ModelAndView selectDocument(String pageIndex, Document document,ModelAndView mv){
		PageModel pageModel = new PageModel();
		if (null == pageIndex || "".equals(pageIndex)) {
			pageIndex = "1";
		}
		pageModel.setPageIndex(Integer.parseInt(pageIndex));
		List<Document> documents = documentService.findDocument(document, pageModel);
		mv.addObject(pageModel);
		System.out.println(documents);
		mv.addObject("documents", documents);
		mv.setViewName("document/document");
		return mv;
		
	}
	
	@RequestMapping("removeDocument")
	public ModelAndView removeDocument(Integer[] ids, ModelAndView mv){
		
		List<Integer> list = Arrays.asList(ids);
		
		documentService.removeDocumentById(list);
		
		mv.setViewName("redirect:/document/selectDocument");
		
		return mv;
	}
	
	
	@RequestMapping("downLoad")
	public ResponseEntity<byte[]>  downLoad(Integer id , HttpServletRequest request) throws IOException{
		
		Document document = documentService.findDocumentById(id);
		
		String fileName = document.getFileName();
		String parent = request.getServletContext().getRealPath("/WEB-INF/upload");
		
		File file = new File(parent, fileName);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}
	
	@RequestMapping("updateDocument")
	public ModelAndView updateDocument(HttpServletRequest request,Document document, Integer flag, ModelAndView mv){
		
		if (flag == 1) {
			
			Document document2 = documentService.findDocumentById(document.getId());
			
			mv.addObject("document", document2);
			mv.setViewName("document/showUpdateDocument");
		}else if (flag == 2) {
			
			HttpSession session = request.getSession(false);
			if (session != null && session.getAttribute(HrmConstants.USER_SESSION) != null) {
				
				User user = (User) session.getAttribute(HrmConstants.USER_SESSION);
				document.setUser(user);
			}
			
			documentService.modifyDocument(document);
			mv.setViewName("redirect:/document/selectDocument");
		}
		
		
		return mv;
	}
	
}
