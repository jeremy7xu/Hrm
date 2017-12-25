package org.deepsl.hrm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.deepsl.hrm.domain.User;
import org.deepsl.hrm.service.HrmService;
import org.deepsl.hrm.util.common.HrmConstants;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 处理用户请求控制器
 * */
@Controller
public class UserController {
	
	/**
	 * 自动注入UserService
	 * */
	@Autowired
	private HrmService hrmService;
		
	/**
	 * 处理登录请求
	 * @param String loginname  登录名
	 * @param String password 密码
	 * @return 跳转的视图
	 * */
	@RequestMapping(value="/login")
	 public ModelAndView login(String loginname,
			 String password,
			 HttpSession session,
			 ModelAndView mv){
		// 调用业务逻辑组件判断用户是否可以登录
		User user = hrmService.login(loginname, password);
		if(user != null){
			// 将用户保存到HttpSession当中
			session.setAttribute(HrmConstants.USER_SESSION, user);
			
			// 客户端跳转到main页面
			mv.setViewName("main");
		}else{
			// 设置登录失败提示信息
			mv.addObject("message", "登录名或密码错误!请重新输入");
			// 服务器内部跳转到登录页面
			mv.setViewName("forward:/loginForm");
		}
		return mv;
		
	}
	
	/**
	 * 处理用户查询请求
	 * @param pageIndex 请求的是第几页
	 * @param HttpServletRequest request
	 * */
	@RequestMapping("user/selectUser")
	public String selectUser(HttpServletRequest request, String pageIndex, String username, String status) {
		try {
			if (null == pageIndex || "".equals(pageIndex)) {
				pageIndex = "1";
			}
			HashMap<String, Object> params  = new HashMap<>();
			if (null != username && !"".equals(username)) {
				username = "%" + username + "%";
				params.put("username", username);
			}
			if (status != null && !"".equals(status)) {
				params.put("status", Integer.parseInt(status));
			}
			
			HashMap<String , Object> map = hrmService.getPageModelAndUserList(pageIndex, params);
			
			List<User> users = (List<User>) map.get("users");
			PageModel pageModel = (PageModel) map.get("pageModel");
			
			request.setAttribute("users", users);
			request.setAttribute("pageModel", pageModel);
			
			
		} catch (Exception e) {

		
			request.setAttribute("error", e);
		}
		return "user/user";

	}
	
	
	
	/**
	 * 处理添加用户请求
	 * @param flag为1表示请求前往添加用户的页面， 为2表示添加
	 * @param employee 模糊查询参数
	 * @param Model model
	 * */
	@RequestMapping("user/addUser")
	public ModelAndView addUser(String flag, User user) {
		ModelAndView mv = new ModelAndView();
		
		if ("1".equals(flag)) {
			mv.setViewName("user/showAddUser");
			
		}else if ("2".equals(flag)) {
			
			user.setCreateDate(new Date());
			hrmService.addUser(user);
			
			mv.setViewName("redirect:/user/selectUser");
		}
		return mv;
	}
	
	
	/**
	 * 处理修改用户请求
	 * @param pageIndex 请求的是第几页
	 * @param employee 模糊查询参数
	 * @param Model model
	 * */
	@RequestMapping("user/updateUser")
	public ModelAndView updateUser(String flag, String id, User user) {
		
		ModelAndView mv = new ModelAndView();
		if ("1".equals(flag)) {
			User user2 = hrmService.findUserById(Integer.parseInt(id));
			mv.addObject("user", user2);
			mv.setViewName("user/showUpdateUser");
		}else if ("2".equals(flag)) {
			hrmService.modifyUser(user);
			mv.setViewName("redirect:/user/selectUser");
		}
		
		
		return mv;
	}
	
	
	
	
	
	/**
	 * 处理删除用户请求
	 * @param String ids 需要删除的id字符串
	 * */
	@RequestMapping("user/removeUser")
	public ModelAndView removeUser(String ids) {
		
		ArrayList<Integer> idList = new ArrayList<>();
		String[] idStrings  = ids.split(",");
		for (String string : idStrings) {
			idList.add(Integer.parseInt(string));
		}
		hrmService.deleteUsersByIds(idList);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/user/selectUser");
		
		return mv;
	}
	
	
 
	 
	
}
