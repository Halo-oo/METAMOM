package mul.camp.a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import mul.camp.a.dto.BbsDto;
import mul.camp.a.dto.BbsParam;

@Mapper
@Repository
public interface BbsDao {

	public List<BbsDto> getBbsList();
	
	public int writeBbs(BbsDto dto);
	
	public BbsDto getBbs(int seq);
	public void readcount(int seq);
	
	public List<BbsDto> getBbsListSearch(BbsParam param);
	
	public List<BbsDto> getBbsListSearchPage(BbsParam param);
	
	public int getBbsCount(BbsParam param);
	
	public int replyBbsUpdate(BbsDto dto);
	public int replyBbsInsert(BbsDto dto);
	
	public int updateBbs(BbsDto dto);
	
	public int deleteBbs(int seq);
}







