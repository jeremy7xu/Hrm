package org.deepsl.hrm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.deepsl.hrm.domain.Job;
import org.deepsl.hrm.service.JobService;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 处理职位请求控制器
 * @version V1.0
 */

@RequestMapping("job")
@Controller
public class JobController {

	@Autowired
	private JobService jobService;


	@RequestMapping("toAddJob")
	public String toAddJob() {

		return "job/showAddJob";
	}

	@RequestMapping("addJob")
	public String addJob(Job job) {

		jobService.addJob(job);

		return "forward:toSelectJob";
	}

	@RequestMapping("toSelectJob")
	public String toSelectJob(HttpServletRequest request) {

		// 默认跳转到分页的首页
		PageModel pageModel = new PageModel();
		pageModel.setPageIndex(1);

		// 查询条件为空
		List<Job> jobs = jobService.findJob(new Job(), pageModel);

		request.setAttribute("pageModel", pageModel);
		request.setAttribute("jobs", jobs);
		return "job/job";
	}

	@RequestMapping("selectJob")
	public String selectJob(Job job, HttpServletRequest request,
			Integer pageIndex) {

		pageIndex = pageIndex == null ? 1 : pageIndex;
		PageModel pageModel = new PageModel();
		pageModel.setPageIndex(pageIndex);
		// 获得当前查询条件下的当前页的记录列表
		List<Job> jobs = jobService.findJob(job, pageModel);

		request.setAttribute("jobs", jobs);
		request.setAttribute("pageModel", pageModel);
		// 得出查询结果后默认跳转到首页
		return "job/job";
	}

	@RequestMapping("toUpdateJob")
	public ModelAndView toUpdateJob(Integer id) {

		Job job = jobService.findJobById(id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("job", job);
		mv.setViewName("job/showUpdateJob");
        
		return mv;
	}

	@RequestMapping("updateJob")
	public String updateJob(Job job) {

		jobService.modifyJob(job);
		return "forward:toSelectJob";
	}

	@RequestMapping("removeJob")
	public String removeJob(int[] ids) {

		for(int id : ids){
			jobService.removeJobById(id);
		}
		return "forward:toSelectJob";
	}   
}
