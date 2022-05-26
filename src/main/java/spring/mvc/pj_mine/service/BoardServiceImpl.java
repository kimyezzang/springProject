package spring.mvc.pj_mine.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import page.Paging;
import spring.mvc.pj_mine.dao.BoardDAOImpl;
import spring.mvc.pj_mine.dto.BoardCommentDTO;
import spring.mvc.pj_mine.dto.BoardDTO;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	BoardDAOImpl dao;
	
	// 게시글 목록
	@Override
	public void boardList(HttpServletRequest req, Model model)
			throws ServletException, IOException {
		System.out.println("서비스 - boardList");
		
		// 페이지번호 클릭시
		String pageNum = req.getParameter("pageNum");
		
		Paging paging = new Paging(pageNum);
		
		// 전체 게시글 갯수 카운트
		int total = dao.boardCnt();
		paging.setTotalCount(total);
		
		System.out.println("total ==> " + total);
		int start = paging.getStartRow();
		int end = paging.getEndRow();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		// 게시글 목록
		List<BoardDTO> list = dao.boardList(map);
		
		// jsp로 처리결과 전달(request나 session으로 결과 전달)
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		
	}

	// 게시글 상세페이지
	@Override
	public void boardDetail(HttpServletRequest req, Model model)
			throws ServletException, IOException {
		System.out.println("서비스 - boardDetail");
		
		// 화면으로부터 입력받은 값을 받는다.
		int num = Integer.parseInt(req.getParameter("num"));
		System.out.println("게시글번호 : " + num);
		
		// 조회수 
		dao.plusReadCnt(num);
			
		BoardDTO dto = dao.getBoardDetail(num);	
		// 글 내용 줄바꿈 처리
		
		  String content= dto.getContent(); 
		  
		  if(content != null) {
			  content = content.replace("\n","<br>");
		  }
		  dto.setContent(content);
		
		System.out.println("상세페이지 dto : " + dto);
		// jsp로 처리결과 전달(request나 session으로 결과 전달)
		
		model.addAttribute("dto", dto);
	}

	// 게시글 작성처리 페이지
	@Override
	public void boardInsert(HttpServletRequest req, Model model)
			throws ServletException, IOException {
		System.out.println("서비스 - boardInsert");
		
		// 화면으로부터 입력받은 값을 받는다.	
		String writer = req.getParameter("writer");
		String password = req.getParameter("password");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		BoardDTO dto = new BoardDTO();
		
		dto.setWriter(writer);
		dto.setPassword(password);
		dto.setTitle(title);
		dto.setContent(content);
		
		// 게시글 저장
		int insertCnt = dao.boardInsert(dto);
		System.out.println("insertCnt : " + insertCnt);
		
		// jsp로 처리결과 전달(request나 session으로 결과 전달)
		model.addAttribute("insertCnt", insertCnt);
		
	}

	// 비밀번호 인증
	@Override
	public String password_chk(HttpServletRequest req,Model model) 
			throws ServletException, IOException {
		//  화면으로부터 입력받은 값(비밀번호), hidden값(num)을 받는다.
		int num = Integer.parseInt(req.getParameter("num"));
		String password = req.getParameter("password");
		
		System.out.println("글번호 : " + num + " 비밀번호 : " + password);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", num);
		map.put("password", password);
		
		// 비밀번호 인증
		String result = dao.password_chk(map);
		System.out.println("비밀번호 체크 결과 : " + result );
		
		String viewPage = "";
		// 비밀번호가 맞으면 수정화면으로 이동
		if(result != null) {
			viewPage = "/manager/csCenter/board_edit";
			BoardDTO dto = dao.getBoardDetail(num);
			model.addAttribute("dto", dto);
			
		} else { // 비밀번호가 틀리면 되돌아감
			viewPage = req.getContextPath() + "redirect:/board_detailAction.ad?num=" + num + "&message=error";
		}
		
		return viewPage;
	}

	// 게시글 수정처리 페이지
	@Override
	public void boardUpdate(HttpServletRequest req, Model model)
			throws ServletException, IOException {
		// 화면으로부터 입력받은 값(비밀번호), hidden값(num)을 받는다.
		BoardDTO dto = new BoardDTO();
		
		dto.setNum(Integer.parseInt(req.getParameter("num")));
		dto.setContent(req.getParameter("content"));
		dto.setPassword(req.getParameter("password"));
		dto.setTitle(req.getParameter("title"));
		dto.setWriter(req.getParameter("writer"));
		
		// update 후 list로 이동
		int updateCnt = dao.updateBoard(dto);
		
	}

	// 게시글 삭제처리 페이지
	@Override
	public void boardDelete(HttpServletRequest req, Model model) 
			throws ServletException, IOException {
		// 화면으로부터 입력받은 hidden값(num)를 받는다.
		int num = Integer.parseInt(req.getParameter("num"));
		System.out.println("삭제할 게시글 번호 : " + num);
		
		// delete
		int deleteCnt = dao.deleteBoard(num);
		
	}

	// 댓글 추가처리 페이지
	@Override
	public void commentAdd(HttpServletRequest req, Model model)
			throws ServletException, IOException {
		System.out.println("서비스 : commentAdd");
		
		// 화면으로부터 입력받은 hidden값(num)를 받아서 DTO에 담는다,
		BoardCommentDTO dto = new BoardCommentDTO();
		
		int board_num = Integer.parseInt(req.getParameter("board_num"));
		String writer = req.getParameter("writer");
		String content = req.getParameter("content");
		
		dto.setBoard_num(board_num);
		dto.setWriter(writer);
		dto.setContent(content);
		
		// 댓글 insert
		dao.getCommentInsert(dto);
		
	}

	// 댓글 목록처리 페이지
	@Override
	public void commentList(HttpServletRequest req, Model model) 
			throws ServletException, IOException {
		System.out.println("서비스 : commentList");
		int board_num = Integer.parseInt(req.getParameter("num"));
		
		System.out.println("board_num : " + board_num);
				
		// 댓글 list 조회
		List<BoardCommentDTO> list = dao.getCommentList(board_num);
		
		model.addAttribute("list", list);
		
	}

	// 댓글 삭제처리 
	@Override
	public void deleteComment(HttpServletRequest req, Model model)
			throws ServletException, IOException {
		System.out.println("서비스 : deleteComment");
		// 화면으로부터 입력받은 hidden값(num)를 받는다.
		int board_num = Integer.parseInt(req.getParameter("hiddenBoard_num"));
		int comment_num = Integer.parseInt(req.getParameter("hiddenComment_num"));
		
		System.out.println("삭제할 댓글 board_num : " + board_num);
		System.out.println("삭제할 댓글 comment_num : " + comment_num);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board_num", board_num);
		map.put("comment_num", comment_num);
		
		// delete
		int deleteCnt = dao.deleteComment(map);
		
		// 조회수 
		dao.plusReadCnt(board_num);
			
		BoardDTO dto = dao.getBoardDetail(board_num);	
		// 글 내용 줄바꿈 처리
		
		  String content= dto.getContent(); 
		  
		  if(content != null) {
			  content = content.replace("\n","<br>");
		  }
		  dto.setContent(content);
		
		System.out.println("상세페이지 dto : " + dto);
		
		model.addAttribute("dto", dto);
		
	}

	

}
