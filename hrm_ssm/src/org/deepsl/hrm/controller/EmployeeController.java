package org.deepsl.hrm.controller;


import java.util.List;

import org.deepsl.hrm.domain.Dept;
import org.deepsl.hrm.domain.Employee;
import org.deepsl.hrm.domain.Job;
import org.deepsl.hrm.service.HrmService;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Description: 处理员工请求控制器   
 * @version V1.0   
 */
@RequestMapping("employee")
@Controller
public class EmployeeController {
	 
	
	// EmployService
	@Autowired
	EmployService employService;
	
	
	@RequestMapping("addEmployee")
	public String addemployee(String flag,Employee employee, Model model,String job_id,String dept_id){
		
		if("1".equals(flag)){
			
			return "employee/showAddEmployee";
		}
		employService.addEmployee(employee);
		
		return "employee";
		
	}
	
}
