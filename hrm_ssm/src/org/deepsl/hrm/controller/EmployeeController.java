package org.deepsl.hrm.controller;


import java.util.List;

import org.deepsl.hrm.domain.Dept;
import org.deepsl.hrm.domain.Employee;
import org.deepsl.hrm.domain.Job;

import org.deepsl.hrm.service.DeptService;
import org.deepsl.hrm.service.EmployService;

import org.deepsl.hrm.service.HrmService;
import org.deepsl.hrm.service.JobService;
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
@Controller
public class EmployeeController {
	 

	
	// EmployService
	@Autowired
	EmployService employService;
	
	@Autowired
	DeptService deptService;
	
	@Autowired
	@Qualifier("jobService")
	JobService jobService;
	
	@RequestMapping("employee/addEmployee")
	public String addemployee(String flag,Employee employee, Model model,String job_id,String dept_id){
		
		if("1".equals(flag)){
			List<Job> findAllJob = jobService.findAllJob();
			List<Dept> findAllDept = deptService.findAllDept();
			model.addAttribute("depts", findAllDept);
			model.addAttribute("jobs", findAllJob);
			
			return "employee/showAddEmployee";
		}
		Job findJobById = jobService.findJobById(Integer.parseInt(job_id));
		Dept findDeptById = deptService.findDeptById(Integer.parseInt(dept_id));
		employee.setJob(findJobById);
		employee.setDept(findDeptById);
		employService.addEmployee(employee);
		
		return "redirect:/employee/selectEmployee";
		
	}
	@RequestMapping("employee/selectEmployee")
	public String selectemployee(Model model,Employee employee, PageModel pageModel,Integer dept_id,Integer job_id,String delete){
		
		System.out.println("EmployeeController.selectemployee()");
		List<Job> findAllJob = jobService.findAllJob();
		List<Dept> findAllDept = deptService.findAllDept();
		//new job 和 dept
		Job job = new Job();
		job.setId(job_id);
		Dept dept = new Dept();
		dept.setId(dept_id);
		
		employee.setJob(job);
		employee.setDept(dept);
		
		
		List<Employee> findEmployee = employService.findEmployee(employee, pageModel);
		
		model.addAttribute("depts", findAllDept);
		model.addAttribute("jobs", findAllJob);
		model.addAttribute("employees", findEmployee);
		model.addAttribute("pageModel", pageModel);
		
		
		return "employee/employee";
	}
	
	@RequestMapping("employee/removeEmployee")
	public String removeEmployee(Integer[] ids){
		
		employService.removeEmployeeById(ids[0]);
		
		
		return "forward:/employee/selectEmployee";
	}
	
	@RequestMapping("employee/updateEmployee")
	public String updateEmployee(String flag, Model model,Integer id,Employee employee,Integer dept_id,Integer job_id){
		
		if("1".equals(flag)){
			
			Employee findEmployeeById = employService.findEmployeeById(id);
			List<Job> findAllJob = jobService.findAllJob();
			List<Dept> findAllDept = deptService.findAllDept();
			
			model.addAttribute("jobs", findAllJob);
			model.addAttribute("depts", findAllDept);
			model.addAttribute("employee", findEmployeeById);
			
			return "employee/showUpdateEmployee";
		}
		
		Job job = new Job();
		job.setId(job_id);
		Dept dept = new Dept();
		dept.setId(dept_id);
		employee.setJob(job);
		employee.setDept(dept);
		
		employService.modifyEmployee(employee);
		
		return "forward:/employee/selectEmployee";
		
	}
	

}
