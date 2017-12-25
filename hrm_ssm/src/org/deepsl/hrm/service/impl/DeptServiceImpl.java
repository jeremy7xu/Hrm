package org.deepsl.hrm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.deepsl.hrm.dao.DeptDao;
import org.deepsl.hrm.domain.Dept;
import org.deepsl.hrm.service.DeptService;
import org.deepsl.hrm.util.common.HrmConstants;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptServiceImpl implements DeptService {
	/**
	 * 自动注入持久化层Dao对象
	 */
 @Autowired
	private DeptDao deptDao;
 
	/*****************部门服务接口实现*************************************/
	@Override
	public List<Dept> findDept(Dept dept, PageModel pageModel) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String,Object>();
		
		int pageSize = pageModel.getPageSize();
		int pageIndex = pageModel.getPageIndex();
		int start = (pageIndex-1)*pageSize;
		params.put("start", start);
		params.put("pageSize", pageSize);
		String name=null;
		if(dept!=null) {
			name = dept.getName();
		}
		params.put("name", name);

		List<Dept> selectByPage = deptDao.selectByPage(params);
		return selectByPage;
	}

	@Override
	public List<Dept> findAllDept() {
		// TODO Auto-generated method stub
		
		return deptDao.selectAllDept();
	}

	@Override
	public void removeDeptById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addDept(Dept dept){
		// TODO Auto-generated method stub
		deptDao.save(dept);
	}

	@Override
	public Dept findDeptById(Integer id) {
		// TODO Auto-generated method stub
		return deptDao.selectById(id);
	}

	@Override
	public void modifyDept(Dept dept) {
		// TODO Auto-generated method stub
		deptDao.update(dept);
	}

	@Override
	public int countDept(Dept dept) {
		// TODO Auto-generated method stub 
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("name", dept.getName());
		return deptDao.count(params);
	}

	@Override
	public void removeDeptByIds(List<Integer> list) {
		// TODO Auto-generated method stub
		deptDao.deleteByIds(list);
	}

}
