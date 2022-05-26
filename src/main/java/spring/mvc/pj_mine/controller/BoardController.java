package spring.mvc.pj_mine.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.mvc.pj_mine.service.BoardServiceImpl;


@Controller
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	BoardServiceImpl service;
	
	// 게시판 리스트
	@RequestMapping("boardList.ad")
	public String boardList(HttpServletRequest req, Model model) 
			throws ServletException, IOException {
		logger.info("[url ==> boardList.ad]");
		
		service.boardList(req, model);
		return "manager/csCenter/boardList";
	}
	// 글쓰기
	@RequestMapping("board_insert.ad")
	public String board_insert(HttpServletRequest req, Model model) 
			throws ServletException, IOException {
		logger.info("[url ==> board_insert.ad]");
		
		return "manager/csCenter/board_insert";
	}
	
	
	// 글쓰기 액션
	@RequestMapping("board_insertAction.ad")
	public String board_insertAction(HttpServletRequest req, Model model)
			throws ServletException, IOException {
		logger.info("[url ==> board_insertAction.ad]");
		
		service.boardInsert(req, model);
		return "redirect:/boardList.ad";
	}
	
	// 상세페이지
	@RequestMapping("board_detailAction.ad")
	public String board_detailAction(HttpServletRequest req, Model model) 
			throws ServletException, IOException {
		logger.info("[url ==> board_detailAction.ad]");
		
		service.boardDetail(req, model);
		return "manager/csCenter/board_detailAction";
	}
	
	// 수정 삭제시 비밀번호 인증
	@RequestMapping("password_chk.ad")
	public String password_chk(HttpServletRequest req, Model model) 
			throws ServletException, IOException {
		logger.info("[url ==> password_chk.ad]");
		
		String viewPage = service.password_chk(req, model);
		
		return viewPage;
	}
	
	// 게시글 수정
	@RequestMapping("board_updateAction.ad")
	public String board_updateAction(HttpServletRequest req, Model model)
			throws ServletException, IOException {
		logger.info("[url ==> board_updateAction.ad]");
		
		service.boardUpdate(req, model);
		return "redirect:/boardList.ad";
	}
	
	// 게시글 삭제
	@RequestMapping("board_deleteAction.ad")
	public String board_deleteAction(HttpServletRequest req, Model model) 
			throws ServletException, IOException {
		logger.info("[url ==> board_deleteAction.ad]");
		
		service.boardDelete(req, model);
		return "redirect:/boardList.ad";
	}
	
	// -- 댓글 --
	// 댓글 작성
	@RequestMapping("commentAdd.ad")
	public void commentAdd(HttpServletRequest req, Model model) 
			throws ServletException, IOException {
		logger.info("[url ==> commentAdd.ad]");
		
		service.commentAdd(req, model);
	}
	
	// 댓글 조회
	@RequestMapping("commentList.ad")
	public String commentList(HttpServletRequest req, Model model)
			throws ServletException, IOException {
		logger.info("[url ==> commentList.ad]");
		
		service.commentList(req, model);
		return "manager/csCenter/board_commentList";
	}
	
	// 댓글 삭제
	@RequestMapping("deleteComment.ad")
	public String deleteComment(HttpServletRequest req, Model model)
			throws ServletException, IOException {
		logger.info("[url ==> deleteComment.ad]");
		
		service.deleteComment(req, model);
		return "manager/csCenter/board_detailAction";
	}
	
}
