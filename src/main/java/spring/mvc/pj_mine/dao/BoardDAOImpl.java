package spring.mvc.pj_mine.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.mvc.pj_mine.dto.BoardCommentDTO;
import spring.mvc.pj_mine.dto.BoardDTO;

@Repository
public class BoardDAOImpl implements BoardDAO{

	@Autowired
	SqlSession sqlSession;

	// 게시글 목록 int start, int end
	@Override
	public List<BoardDTO> boardList(Map<String, Object> map) {
		System.out.println("BoardDAO - 게시글 목록");

		List<BoardDTO> list = sqlSession.selectList("spring.mvc.pj_mine.dao.BoardDAO.boardList",map);
		System.out.println("list:" + list);
		return list;
	}

	// 게시판 갯수
	@Override
	public int boardCnt() {
		System.out.println("BoardDAO - 게시판 갯수");
	    
	    int selectCnt = sqlSession.selectOne("spring.mvc.pj_mine.dao.BoardDAO.boardCnt");
	      
	    return selectCnt;
	}

	// 게시글 작성처리
	@Override
	public int boardInsert(BoardDTO dto) {
		System.out.println("BoardDAO - 게시글 작성처리");
		int insertCnt = sqlSession.insert("spring.mvc.pj_mine.dao.BoardDAO.boardInsert", dto);
		
		return insertCnt;
	}

	// 조회수 증가
	@Override
	public void plusReadCnt(int num) {
		System.out.println("BoardDAO - 조회수 증가");
		
		int insertCnt = sqlSession.update("spring.mvc.pj_mine.dao.BoardDAO.plusReadCnt", num);
		
	}

	// 게시글 상세페이지
	@Override
	public BoardDTO getBoardDetail(int num) {
		System.out.println("BoardDAO - 게시글 상세페이지");
		BoardDTO dto = sqlSession.selectOne("spring.mvc.pj_mine.dao.BoardDAO.getBoardDetail", num);
		
		return dto;
	}

	// 비밀번호 인증 int num, String password
	@Override
	public String password_chk(Map<String, Object> map) {
		System.out.println("BoardDAO - BoardDAO");
		String result = sqlSession.selectOne("spring.mvc.pj_mine.dao.BoardDAO.password_chk", map);
		
		return result;
		
	}

	// 게시글 수정처리
	@Override
	public int updateBoard(BoardDTO dto) {
		System.out.println("BoardDAO - 게시글 수정처리");
		
		int updateCnt = sqlSession.update("spring.mvc.pj_mine.dao.BoardDAO.updateBoard", dto);
		
		return updateCnt;
	}

	// 게시글 삭제처리
	@Override
	public int deleteBoard(int num) {
		System.out.println("BoardDAO - 게시글 삭제처리");
		
		int deleteCnt = sqlSession.update("spring.mvc.pj_mine.dao.BoardDAO.deleteBoard", num);
		
		return deleteCnt;
	}

	// 댓글 추가처리
	@Override
	public void getCommentInsert(BoardCommentDTO dto) {
		System.out.println("BoardDAO - 댓글 추가처리");
		
		int insertCnt = sqlSession.insert("spring.mvc.pj_mine.dao.BoardDAO.getCommentInsert", dto);
		
	}

	// 댓글 목록
	@Override
	public List<BoardCommentDTO> getCommentList(int board_num) {
		System.out.println("BoardDAO - 댓글 목록");
		
		List<BoardCommentDTO> list = sqlSession.selectList("spring.mvc.pj_mine.dao.BoardDAO.getCommentList",board_num);
		System.out.println("list:" + list);
		return list;
	}

	// 댓글 삭제 int board_num, int comment_num
	@Override
	public int deleteComment(Map<String, Object> map) {
		System.out.println("BoardDAO - 댓글 삭제");

		int deleteCnt = sqlSession.delete("spring.mvc.pj_mine.dao.BoardDAO.deleteComment", map);
		
		return deleteCnt;
	}
	
	

}
