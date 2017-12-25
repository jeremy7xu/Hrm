package org.deepsl.hrm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.deepsl.hrm.dao.DeptDao;
import org.deepsl.hrm.dao.DocumentDao;
import org.deepsl.hrm.dao.EmployeeDao;
import org.deepsl.hrm.dao.JobDao;
import org.deepsl.hrm.dao.NoticeDao;
import org.deepsl.hrm.dao.UserDao;
import org.deepsl.hrm.domain.Dept;
import org.deepsl.hrm.domain.Document;
import org.deepsl.hrm.domain.Employee;
import org.deepsl.hrm.domain.Job;
import org.deepsl.hrm.domain.Notice;
import org.deepsl.hrm.domain.User;
import org.deepsl.hrm.service.HrmService;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**   
 * @Description: 人事管理系统服务层接口实现类 
 * @version V1.0   
 */
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service
public class HrmServiceImpl implements HrmService{

	/**
	 * 自动注入持久层Dao对象
	 * */
	@Autowired
	private UserDao userDao;
 
	
	/*****************用户服务接口实现*************************************/
	/**
	 * HrmServiceImpl接口login方法实现
	 *  @see { HrmService }
	 * */
	@Transactional(readOnly=true)
	@Override
	public User login(String loginname, String password) {
//		System.out.println("HrmServiceImpl login -- >>");
		HashMap<String, String> hashMap= new HashMap<>();
		hashMap.put("loginname",  loginname );
		hashMap.put("password",  password );

		
		return userDao.selectByLoginnameAndPassword( hashMap );
	}

	/**
	 * HrmServiceImpl接口findUser方法实现
	 * @see { HrmService }
	 * */
	@Transactional(readOnly=true)
	@Override
	public List<User> findUser(User user,PageModel pageModel) {
 
		List<User> users = null;
		 
		return users;
	}
	
	/**
	 * HrmServiceImpl接口findUserById方法实现
	 * @see { HrmService }
	 * */
	@Transactional(readOnly=true)
	@Override
	public User findUserById(Integer id) {
		return userDao.selectById(id);
	}
	
	/**
	 * HrmServiceImpl接口removeUserById方法实现
	 * @see { HrmService }
	 * */
	@Override
	public void removeUserById(Integer id) {
 		
	}
	
	/**
	 * HrmServiceImpl接口addUser方法实现
	 * @see { HrmService }
	 * */
	@Override
	public void modifyUser(User user) {
		userDao.update(user);
		
	}
	
	/**
	 * HrmServiceImpl接口modifyUser方法实现
	 * @see { HrmService }
	 * */
	@Override
	public void addUser(User user) {
 		userDao.save(user);
	}

	@Override
	public HashMap<String, Object> getPageModelAndUserList(String pageIndex, HashMap<String, Object> params) {
		
		HashMap<String, Object> map = new HashMap<>();
		
		PageModel pageModel = new PageModel();		//pageSize初始值默认为4
		
		int limit = pageModel.getPageSize();		 //限制每页记录数
		
		int recordCount = userDao.count(params);		//查询总记录数
		
		//int totalSize = recordCount % limit == 0 ? (recordCount / limit) :(recordCount / limit + 1);//总页数  
		if (null == pageIndex || "".equals(pageIndex)) {
			pageIndex = "1";
		}
		int pageIndex1 = Integer.parseInt(pageIndex); //当前页数
		
		pageModel.setRecordCount(recordCount);
		pageModel.setPageIndex(pageIndex1);
		
		map.put("pageModel", pageModel);  //把pageModel存到map里面
		 
		params.put("limit", limit);
		params.put("offset", pageModel.getFirstLimitParam());
		
		List<User> users = userDao.selectByPage(params);
		
		map.put("users", users);
		
		return map;
	}

	
	@Override
	public void deleteUsersByIds(ArrayList<Integer> idList) {
		for (Integer id : idList) {
			userDao.deleteById(id);
		}	
	}

}
