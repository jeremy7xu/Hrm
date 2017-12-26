package org.deepsl.hrm.service.impl;

import java.util.HashMap;


import java.util.List;

import org.deepsl.hrm.dao.NoticeDao;
import org.deepsl.hrm.domain.Notice;
import org.deepsl.hrm.service.NoticeService;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("NoticeService")
public class NoticeServiceImlp implements NoticeService {

	@Autowired
	private NoticeDao noticeDao;
	
	/**
	 * 获得所有公告
	 * @return Notice对象的List集合
	 * */
	@Override
	public List<Notice> findNotice(Notice notice,PageModel pageModel){
		
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("title", notice.getTitle());
		params.put("content", notice.getContent());
		Integer recordCount = noticeDao.count(params);
		pageModel.setRecordCount(recordCount);
		params.put("offset", pageModel.getFirstLimitParam());
		params.put("limit", pageModel.getPageSize());
		List<Notice> noticeList = noticeDao.selectByPage(params);
		return noticeList;
		 }
	
	/**
	 * 根据id查询公告
	 * @param id
	 * @return 公告对象
	 * */
	@Override
	public Notice findNoticeById(Integer id){
		Notice notice = noticeDao.selectById(id);
		return notice;
		}
	
	/**
	 * 根据id删除公告
	 * @param id
	 * */
	@Override
	public void removeNoticeById(Integer id){
		noticeDao.deleteById(id);
	}
	
	/**
	 * 添加公告
	 * @param Notice 公告对象
	 * */
	@Override
	public void addNotice(Notice notice){
		noticeDao.save(notice);
	}
	
	/**
	 * 修改公告
	 * @param Notice 公告对象
	 * */
	@Override
	public void modifyNotice(Notice notice){
		noticeDao.update(notice);
	}

}
