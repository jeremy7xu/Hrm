package org.deepsl.hrm.service;

import java.util.List;

import org.deepsl.hrm.domain.Employee;
import org.deepsl.hrm.util.tag.PageModel;

public interface EmployService {

	void addEmployee(Employee employee);
	/**
	 * 获得所有员工
	 * @param employee 查询条件
	 * @param pageModel 分页对象
	 * @return Dept对象的List集合
	 * */
	List<Employee> findEmployee(Employee employee,PageModel pageModel);
	/**
	 * 根据id删除员工
	 * @param id
	 * */
	void removeEmployeeById(Integer id);
	/**
	 * 根据id查询员工
	 * @param id
	 * @return 员工对象
	 * */
	Employee findEmployeeById(Integer id);
	/**
	 * 修改员工
	 * @param employee 员工对象
	 * */
	void modifyEmployee(Employee employee);
	
}
