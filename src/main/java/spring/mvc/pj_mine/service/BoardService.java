package spring.mvc.pj_mine.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

public interface BoardService {
	
	// 게시글 목록
	public void boardList(HttpServletRequest req, Model model)
			throws ServletException, IOException;
	
	// 게시글 상세페이지
	public void boardDetail(HttpServletRequest req, Model model)
			throws ServletException, IOException;
	
	// 게시글 작성처리 페이지
	public void boardInsert(HttpServletRequest req, Model model)
			throws ServletException, IOException;
	
	// 비밀번호 인증
	public String password_chk(HttpServletRequest req, Model model)
			throws ServletException, IOException;
	
	// 게시글 수정처리 페이지
	public void boardUpdate(HttpServletRequest req, Model model)
			throws ServletException, IOException;
	
	// 게시글 삭제처리 페이지
	public void boardDelete(HttpServletRequest req, Model model)
			throws ServletException, IOException;
	
	// 댓글 추가처리 페이지
	public void commentAdd(HttpServletRequest req, Model model)
			throws ServletException, IOException; 
		
	// 댓글 목록처리 페이지
	public void commentList(HttpServletRequest req, Model model)
			throws ServletException, IOException;

	// 댓글 삭제처리 
	public void deleteComment(HttpServletRequest req, Model model)
			throws ServletException, IOException;
	
}
