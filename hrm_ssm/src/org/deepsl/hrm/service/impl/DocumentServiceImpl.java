package org.deepsl.hrm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.Doc;

import org.deepsl.hrm.dao.DocumentDao;
import org.deepsl.hrm.domain.Document;
import org.deepsl.hrm.service.DocumentService;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	DocumentDao documentDao;
	
	@Override
	public List<Document> findDocument(Document document, PageModel pageModel) {

		
		Map<String, Object> params = new HashMap<String, Object>();
		String title = document.getTitle();
		if (title != null && !"".equals(title)) {
			title = "%" + title + "%";
		}
		params.put("title", title);
		
		int recordCount = documentDao.count(params);
		pageModel.setRecordCount(recordCount);
		
		int limit = pageModel.getPageSize();
		params.put("limit", limit);
		
		int pageIndex = pageModel.getPageIndex();
		if (pageIndex == 0) {
			pageModel.setPageIndex(1);
		}
		int offset = pageModel.getFirstLimitParam();
		params.put("offset", offset);
		
		List<Document> documents = documentDao.selectByPage(params);
		
		return documents;
	}

	@Override
	@Transactional
	public void addDocument(Document document) {
		// TODO Auto-generated method stub
		documentDao.save(document);
	}

	@Override
	public Document findDocumentById(Integer id) {
		// TODO Auto-generated method stub
		return documentDao.selectById(id);
	}

	@Override
	public void removeDocumentById(List<Integer> ids) {
		// TODO Auto-generated method stub
		documentDao.deleteById(ids);
	}

	@Override
	public void modifyDocument(Document document) {
		// TODO Auto-generated method stub
		documentDao.update(document);
	}

}
