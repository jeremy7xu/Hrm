package org.deepsl.hrm.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.deepsl.hrm.domain.Document;
import org.deepsl.hrm.domain.User;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Description: 处理上传下载文件请求控制器
 * @version V1.0   
 */

@Controller
@RequestMapping("document")
public class DocumentController {

	@RequestMapping("addDocument")
	public ModelAndView addDocument(HttpServletRequest request, String flag, Document document, MultipartFile file){
		
		ModelAndView mv = new ModelAndView();
		try {
			
			if ("1".equals(flag)) {
				
				mv.setViewName("document/showAddDocument");
				
			}else if ("2".equals(flag)) {
				//设置文件上传人
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
				}
			
				mv.setViewName("document/showAddDocument");
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			
			mv.addObject("error", e.getMessage());
			mv.setViewName("document/showAddDocument");
		}
		
		
		return mv;
	}
	
 
}
