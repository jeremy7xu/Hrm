package org.deepsl.hrm.service.impl;

import java.util.HashMap;
import java.util.List;

import org.deepsl.hrm.dao.EmployeeDao;
import org.deepsl.hrm.domain.Employee;
import org.deepsl.hrm.service.EmployService;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployServiceImpl implements EmployService {
	
	@Autowired
	EmployeeDao dao;
	
	
	//@Transactional
	@Override
	public void addEmployee(Employee employee) {
		// TODO Auto-generated method stub
		
		dao.addEmployee(employee);
	}

	@Override
	public List<Employee> findEmployee(Employee employee, PageModel pageModel) {
		// TODO Auto-generated method stub
		/*if(pageModel.getPageIndex()==0){
			pageModel.setPageIndex(1);
			
		}
	*/
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("employee", employee);
		
		Integer count = dao.count(hashMap);
		//总记录数
		pageModel.setRecordCount(count);
		pageModel.getTotalSize();
		
		System.out.println("EmployServiceImpl.findEmployee()"+pageModel.getPageIndex());
		int offset = (pageModel.getPageIndex()-1)*pageModel.getPageSize();
		hashMap.put("pageModel", pageModel);
		hashMap.put("offset", offset);
		List<Employee> selectByPage = dao.selectByPage(hashMap);
		return selectByPage;
	}

	@Transactional
	@Override
	public void removeEmployeeById(Integer id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
		
	}

	@Override
	public Employee findEmployeeById(Integer id) {
		// TODO Auto-generated method stub
		Employee selectById = dao.selectById(id);
		
		return selectById;
	}

	@Transactional
	@Override
	public void modifyEmployee(Employee employee) {
		// TODO Auto-generated method stub
		dao.update(employee);
		
	}

}
