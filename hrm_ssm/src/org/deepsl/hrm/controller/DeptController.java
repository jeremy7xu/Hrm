package org.deepsl.hrm.controller;

import java.util.List;

import org.deepsl.hrm.domain.Dept;
import org.deepsl.hrm.service.DeptService;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Description: 处理部门请求控制器
 * @author   
 * @version V1.0   
 */

@Controller
@RequestMapping(value="dept")
public class DeptController {
	@Autowired
	private DeptService deptService;
	
	@RequestMapping(value="selectDept")
	public ModelAndView selectDept(PageModel pageModel,Dept dept,ModelAndView mv){

		int countDept = deptService.countDept(dept);
		pageModel.setRecordCount(countDept);
		List<Dept> findAllDept = deptService.findDept(dept, pageModel);
		mv.addObject(pageModel);
		System.out.println(findAllDept);
		mv.addObject("depts", findAllDept);
		mv.setViewName("dept/dept");
		return mv;
	}

	@RequestMapping(value="updateDept")
	public ModelAndView updateDept(@RequestParam("flag")String flag,Dept dept,ModelAndView mv){
		System.out.println(flag);
		if("1".equals(flag))
			if(dept!=null){
				Dept findDeptById = deptService.findDeptById(dept.getId());
				mv.addObject("dept", findDeptById);
				mv.setViewName("dept/showUpdateDept");
			}
		if("2".equals(flag)){
			if(dept!=null){
				deptService.modifyDept(dept);
				mv.setViewName("forward:selectDept");
			}
			
		}
		return mv;
	}
	@RequestMapping(value="addDept")
	public ModelAndView addDept(@RequestParam("flag")String flag,Dept dept,ModelAndView mv){
		System.out.println(flag);
		if("1".equals(flag))
				mv.setViewName("dept/showAddDept");
		if("2".equals(flag)){
			if(dept==null||dept.getName().isEmpty()||dept.getRemark().isEmpty()){
				mv.addObject("errorMessage", "添加信息为空");
				mv.setViewName("dept/showAddDept");
				return mv;
			}			
			try {
				deptService.addDept(dept);
				mv.setViewName("forward:selectDept");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mv.addObject("errorMessage", "添加部门失败");
				mv.setViewName("dept/showAddDept");
			}
		}
		return mv;
	}
 
}
