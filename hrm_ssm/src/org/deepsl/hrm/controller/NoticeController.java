package org.deepsl.hrm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.deepsl.hrm.domain.Notice;
import org.deepsl.hrm.domain.User;
import org.deepsl.hrm.service.HrmService;
import org.deepsl.hrm.service.NoticeService;
import org.deepsl.hrm.util.common.HrmConstants;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Description: 处理公告请求控制器
 * @version V1.0   
 */
@RequestMapping("notice")
@Controller
public class NoticeController {

	@Autowired
	NoticeService noticeService;
	
	@RequestMapping("/selectNotice")
	public ModelAndView selectNotice(HttpSession session,Notice notice,PageModel pageModel){
		ModelAndView mav = new ModelAndView();
		List<Notice> notices = noticeService.findNotice(notice, pageModel);
		mav.getModel().put("notices", notices);
		mav.getModel().put("pageModel", pageModel);
		session.setAttribute("title", notice.getTitle());
		session.setAttribute("content", notice.getContent());
		mav.setViewName("/WEB-INF/jsp/notice/notice.jsp");
		return mav;
	}
	@RequestMapping("/addNotice")
	public ModelAndView addNotice(Notice notice){
		noticeService.addNotice(notice);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("selectNotice");
		return mav;
	}
	@RequestMapping("/removeNotice")
	public ModelAndView removeNotice(int ids){
		noticeService.removeNoticeById(ids);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("selectNotice");
		return mav;
	}
	@RequestMapping("/updateNotice")
	public ModelAndView updateNotice(Notice notice){
		noticeService.modifyNotice(notice);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("selectNotice");
		return mav;
	}
	@RequestMapping("/previewNotice")
	public ModelAndView previewNotice(int id){
		
		Notice notice = noticeService.findNoticeById(id);
		ModelAndView mav = new ModelAndView();
		mav.getModel().put("notice", notice);
		mav.setViewName("/WEB-INF/jsp/notice/previewNotice.jsp");
		return mav;
	}
	
}
