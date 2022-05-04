package mul.camp.a.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mul.camp.a.dto.CommentDto;
import mul.camp.a.service.CommentService;

@RestController
public class CommentController {

	@Autowired
	CommentService service;
	
	@RequestMapping(value = "getCommentList")
	public List<CommentDto> getCommentList(int seq){
		System.out.println("CommentController getCommentList");
		
		return service.getCommentList(seq);
	}
	
	@RequestMapping(value = "commentWrite")
	public String commentWrite(CommentDto dto) {
		System.out.println("CommentController commentWrite");
		System.out.println(dto.toString());
		
		boolean b = service.writeComment(dto);
		if(b) {
			return "OK";
		}
		
		return "NO";
	}
}





