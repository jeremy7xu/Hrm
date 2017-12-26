package org.deepsl.hrm.service.impl;

import java.util.HashMap;
import java.util.List;

import org.deepsl.hrm.dao.JobDao;
import org.deepsl.hrm.domain.Job;
import org.deepsl.hrm.service.JobService;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jobService")
public class JobServiceImpl implements JobService {

	@Autowired
	private JobDao jobDao;
	
	@Override
	public void addJob(Job job) {

		jobDao.save(job); 
	}

	@Override
	public void modifyJob(Job job) {

		jobDao.update(job);
	}
	
	@Override
	@Transactional
	public List<Job> findJob(Job job, PageModel pageModel) {
		
		HashMap<String,Object> hashMap = new HashMap<>();
		String name = job.getName();
		if(name==null){
			hashMap.put("name", "%");
		}else{
			hashMap.put("name", "%"+job.getName()+"%");
		}
		
		//得出当前查询条件下的结果总数
		Integer recordCount = jobDao.count(hashMap);
		pageModel.setRecordCount(recordCount);
		
		//得到当前查询条件下的当前页的记录
		int offset = (pageModel.getPageIndex()-1)*pageModel.getPageSize();
		hashMap.put("offset", offset);
		hashMap.put("limit", pageModel.getPageSize());
		return jobDao.selectByPage(hashMap);
	}
	
	@Override
	public List<Job> findAllJob() {

		return jobDao.selectAllJob();
	}
	
	@Override
	public Job findJobById(Integer id) {
		return jobDao.selectById(id);
	}
	
	@Override
	public void removeJobById(Integer id) {

		jobDao.deleteById(id);
	}
}
