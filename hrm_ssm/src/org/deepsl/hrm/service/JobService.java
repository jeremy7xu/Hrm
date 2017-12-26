package org.deepsl.hrm.service;

import java.util.List;

import org.deepsl.hrm.domain.Job;
import org.deepsl.hrm.util.tag.PageModel;

public interface JobService {

	void addJob(Job job);

	void modifyJob(Job job);
	
	List<Job> findJob(Job job,PageModel pageModel);
	
	List<Job> findAllJob();
	
	Job findJobById(Integer id);
	
	void removeJobById(Integer id); 

}
