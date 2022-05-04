package mul.camp.a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mul.camp.a.dao.CommentDao;
import mul.camp.a.dto.CommentDto;

@Service
@Transactional
public class CommentService {
	
	@Autowired
	CommentDao dao;
	
	public List<CommentDto> getCommentList(int seq) {
		return dao.getCommentList(seq);
	}
	
	public boolean writeComment(CommentDto dto) {
		int n = dao.writeComment(dto);
		if(n > 0) {
			return true;
		}
		return false;
	}
}




