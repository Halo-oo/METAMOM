package mul.camp.a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mul.camp.a.dao.BbsDao;
import mul.camp.a.dto.BbsDto;
import mul.camp.a.dto.BbsParam;

@Service
@Transactional
public class BbsService {

	@Autowired
	BbsDao dao;
	
	public List<BbsDto> getBbsList() {
		return dao.getBbsList();
	}
	
	public boolean writeBbs(BbsDto dto) {
		int n = dao.writeBbs(dto);
		return n>0?true:false;
	}
	
	public BbsDto getBbs(int seq) {
		return dao.getBbs(seq);
	}
	public void readcount(int seq) {
		dao.readcount(seq);
	}
	
	public List<BbsDto> getBbsListSearch(BbsParam param) {
		return dao.getBbsListSearch(param);
	}
	
	public List<BbsDto> getBbsListSearchPage(BbsParam param) {
		return dao.getBbsListSearchPage(param);		
	}	
	public int getBbsCount(BbsParam param) {
		return dao.getBbsCount(param);		
	}
	
	public boolean reply(BbsDto dto) {
		dao.replyBbsUpdate(dto);
		int n2 = dao.replyBbsInsert(dto);
		System.out.println("n2:" + n2);
		if(n2 == 0) {
			return false;
		}
		return true;
	}
	
	public boolean updateBbs(BbsDto dto) {
		int n = dao.updateBbs(dto);
		if(n > 0) {
			return true;
		}
		return false;
	}
	
	public boolean deleteBbs(int seq) {
		int n = dao.deleteBbs(seq);
		if(n == 0) return false;
		
		return true;
	}
	
}













