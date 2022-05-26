package spring.mvc.pj_mine.service;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import page.Paging;
import page.ProductPaging;
import spring.mvc.pj_mine.dao.BoardDAO;
import spring.mvc.pj_mine.dao.CustomerDAO;
import spring.mvc.pj_mine.dto.BoardCommentDTO;
import spring.mvc.pj_mine.dto.BoardDTO;
import spring.mvc.pj_mine.dto.CustomerDTO;
import spring.mvc.pj_mine.dto.ProductDTO;
import spring.mvc.pj_mine.util.EmailChkHandler;


@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	CustomerDAO dao;
	
	@Autowired
	BoardDAO bDao;
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;	// 비밀번호 암호화 클래스
	
	// ID중복확인 처리
	@Override
	public void confirmIdAction(HttpServletRequest req, Model model) {
		System.out.println("Customer서비스 => ID중복확인 처리");
		
		String strId = req.getParameter("id");
		
		int selectCnt = dao.idCheck(strId);
		
		model.addAttribute("id", strId);
		model.addAttribute("selectCnt", selectCnt);
	}

	// Email중복확인 처리
	@Override
	public void confirmEmailAction(HttpServletRequest req, Model model) {
		System.out.println("Customer서비스 => email 중복확인 처리");
		
		String strEmail = req.getParameter("email");
		
		int selectCnt = dao.emailCheck(strEmail);
		
		model.addAttribute("email", strEmail);
		model.addAttribute("selectCnt", selectCnt);
	}

	// 회원가입 처리
	@Override
	public void signInAction(HttpServletRequest req, Model model) {
		System.out.println("Customer서비스 - 회원가입처리" );
		
		CustomerDTO dto = new CustomerDTO();
		
		dto.setId(req.getParameter("id"));
		// 비밀번호 암호화 - 시큐리티
		String password = req.getParameter("password");
		System.out.println("암호화 전의 비밀번호 : "  + password);
		
		// BCryptPasswordEncoder.encode() : 비밀번호를 암호화
		String encryptPassword = passwordEncoder.encode(password);
		System.out.println("암호화 후의 비밀번호 : "  + encryptPassword);
		
		dto.setPassword(encryptPassword);
		dto.setPasswordHint(req.getParameter("passwordHint"));
		dto.setName(req.getParameter("name"));
		String strDate = req.getParameter("birth");
		String year = strDate.substring(0,4);
		String month = strDate.substring(4,6);
		String day = strDate.substring(6,8);
		strDate = year+"-"+month+"-"+day;
		System.out.println("strDate : " + strDate);
		Date date = Date.valueOf(strDate);
		dto.setBirth(date);
		dto.setAddress(req.getParameter("address"));
		String email = req.getParameter("email");
		dto.setEmail(email);
		dto.setHp(req.getParameter("hp"));
		
		// 시큐리티
		String key = EmailChkHandler.getKey();
		dto.setKey(key);
				
		int insertCnt = dao.insertCustomer(dto);
		System.out.println("서비스 insertCnt : " + insertCnt);
		
		// 시큐리티 - 가입성공시 이메일 검증
		if(insertCnt == 1) {
			dao.sendEmail(email,key);	// email은 반드시 가입한 구글계정이메일
		}
				
		model.addAttribute("insertCnt", insertCnt);
	}

	// 이메일 인증 후 권한(enabled) update - 시큐리티
	@Override
	public void emailChkAction(HttpServletRequest req, Model model) {
		System.out.println("Customer서비스 => emailChkAction");
		String key = req.getParameter("key");
		
		int selectCnt = dao.selectKey(key);
		if(selectCnt == 1) {
			selectCnt = dao.updateGrade(key);
			
			model.addAttribute("insertCnt",selectCnt);
		}
	
	}

	// --------- [회원 정보 수정 , 탈퇴 주문내역] ----------
	// 회원정보 인증 및 탈퇴 처리
	@Override
	public void deleteCustomerAction(HttpServletRequest req, Model model) {
		System.out.println("Customer서비스 => 회원정보 인증 및 탈퇴 처리");
		
		// 화면으로부터 입력받은 값을 받는다.
		String strId = (String)req.getSession().getAttribute("customerID");
		String strPw = req.getParameter("password");
		int deleteCnt = 0;
		
		// 회원삭제를 위한 인증처리
		int cnt = dao.idCheck(strId);
		if(cnt != 0) {
			// 암호화된 비밀번호 가져오기
			String encryptPassword = sqlSession.selectOne("spring.mvc.pj_mine.dao.CustomerDAO.pwdCheck",strId);
			System.out.println("암호화된 비밀번호 : " + encryptPassword);
			System.out.println("화면에서 입력받은 비밀번호 : " + strPw);
			
			// 입력한 비밀번호와 가입된 비밀번호 (암호화된 비밀번호)가 일치하는지 여부
			if(passwordEncoder.matches(strPw, encryptPassword)) {
				deleteCnt = dao.deleteCustomer(strId);
				
			} else {
				deleteCnt = 0;
			}
		}
		
		model.addAttribute("deleteCnt", deleteCnt);
		model.addAttribute("selectCnt", cnt);
	}

	// 회원정보 인증 및 상세페이지
	@Override
	public void modifyDetailAction(HttpServletRequest req, Model model) {
		System.out.println("Customer서비스 => 회원정보 인증 및 상세페이지");
		CustomerDTO dto = null;
		// 화면으로부터 입력받은 값을 받는다.
		String strId = (String)req.getSession().getAttribute("customerID");
		String strPw = req.getParameter("password");
		int selectCnt = 0;
		// 회원수정을 위한 인증처리
		int cnt = dao.idCheck(strId);
		if(cnt != 0) {
			// 암호화된 비밀번호 가져오기
			String encryptPassword = sqlSession.selectOne("spring.mvc.pj_mine.dao.CustomerDAO.pwdCheck",strId);
			System.out.println("암호화된 비밀번호 : " + encryptPassword);
			System.out.println("화면에서 입력받은 비밀번호 : " + strPw);
			
			// 입력한 비밀번호와 가입된 비밀번호 (암호화된 비밀번호)가 일치하는지 여부
			if(passwordEncoder.matches(strPw, encryptPassword)) {
				selectCnt = 1;
				dto = dao.getCustomerDetail(strId);
				
			} else {
				selectCnt = 0;
			}
		}
		
		model.addAttribute("dto", dto);
		model.addAttribute("selectCnt", selectCnt);
	}

	// 회원정보 수정 처리
	@Override
	public void modifyCustomerAction(HttpServletRequest req, Model model) {
		CustomerDTO dto = new CustomerDTO();
		// 3-2단계. 화면으로부터 입력받은 값을 dto에 담는다.
		dto.setId((String)req.getSession().getAttribute("customerID"));
		
		// 시큐리티 패스워드
		String password = req.getParameter("password");
		System.out.println("암호화 전의 비밀번호 : "  + password);
		
		// BCryptPasswordEncoder.encode() : 비밀번호를 암호화
		String encryptPassword = passwordEncoder.encode(password);
		System.out.println("암호화 후의 비밀번호 : "  + encryptPassword);
		
		dto.setPassword(encryptPassword);
		dto.setName(req.getParameter("name"));
				
		String strDate = req.getParameter("birth");
		String year = strDate.substring(0,4);
		String month = strDate.substring(4,6);
		String day = strDate.substring(6,8);
		strDate = year+"-"+month+"-"+day;
		System.out.println("strDate : " + strDate);
		Date date = Date.valueOf(strDate);
		dto.setBirth(date);
		
		dto.setAddress(req.getParameter("address"));
		dto.setHp(req.getParameter("hp"));
		dto.setPasswordHint(req.getParameter("passwordHint"));
		
		dto.setEmail(req.getParameter("email"));
		
		// 5단계. 회원수정처리
		int updateCnt = dao.updateCustomer(dto);
				
		System.out.println("updateCnt" + updateCnt);
		// 6단계. jsp로 처리 결과 전달(request나 session으로 처리 결과를 저장 후 전달)
		model.addAttribute("updateCnt", updateCnt);
		
	}

	//-----------------------------------[게시판]---------------------------
	// 게시글 목록
	@Override
	public void boardList(HttpServletRequest req, Model model) throws ServletException, IOException {
		System.out.println("서비스 - boardList");
		
		// 페이지번호 클릭시
		String pageNum = req.getParameter("pageNum");
		
		// 
		Paging paging = new Paging(pageNum);
		
		// 전체 게시글 갯수 카운트
		int total = bDao.boardCnt();
		paging.setTotalCount(total);
		
		System.out.println("total ==> " + total);
		int start = paging.getStartRow();
		int end = paging.getEndRow();
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		// 게시글 목록
		List<BoardDTO> list = bDao.boardList(map);
		
		// jsp로 처리결과 전달(request나 session으로 결과 전달)
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		
	}

	// 게시글 작성처리 페이지
	@Override
	public void boardInsert(HttpServletRequest req, Model model) throws ServletException, IOException {
		System.out.println("서비스 - boardInsert");
		
		// 화면으로부터 입력받은 값을 받는다.	
		String writer = (String) req.getSession().getAttribute("customerID");
		String password = "1234";
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		BoardDTO dto = new BoardDTO();
		
		dto.setWriter(writer);
		dto.setPassword(password);
		dto.setTitle(title);
		dto.setContent(content);
		
		// 게시글 저장
		int insertCnt = bDao.boardInsert(dto);
		System.out.println("insertCnt : " + insertCnt);
		
		// jsp로 처리결과 전달(request나 session으로 결과 전달)
		model.addAttribute("insertCnt", insertCnt);
		
	}

	// 게시글 상세페이지
	@Override
	public void boardDetail(HttpServletRequest req, Model model) throws ServletException, IOException {
		System.out.println("서비스 - boardDetail");
		
		// 화면으로부터 입력받은 값을 받는다.
		int num = Integer.parseInt(req.getParameter("num"));
		System.out.println("게시글번호 : " + num);
		
		// 조회수 
		bDao.plusReadCnt(num);
			
		BoardDTO dto = bDao.getBoardDetail(num);	
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

	//-----------------------------------[댓글]---------------------------
	// 댓글 추가처리 페이지
	@Override
	public void commentAdd(HttpServletRequest req, Model model)
			throws ServletException, IOException {
		System.out.println("서비스 : commentAdd");
		
		//  화면으로부터 입력받은 hidden값(num)를 받아서 DTO에 담는다,
		BoardCommentDTO dto = new BoardCommentDTO();
		
		int board_num = Integer.parseInt(req.getParameter("board_num"));
		String writer = (String) req.getSession().getAttribute("customerID");
		String content = req.getParameter("content");
		
		dto.setBoard_num(board_num);
		dto.setWriter(writer);
		dto.setContent(content);
		
		// 댓글 insert
		bDao.getCommentInsert(dto);
		
		// 실행이 끝나면 board_detail.jsp의 comment_add()콜백함수(success)로 넘어감
		
	}

	// 댓글 목록처리 페이지
	@Override
	public void commentList(HttpServletRequest req, Model model)
			throws ServletException, IOException {
		System.out.println("서비스 : commentList");
		int board_num = Integer.parseInt(req.getParameter("num"));
		
		System.out.println("board_num : " + board_num);
				
		// 댓글 list 조회
		List<BoardCommentDTO> list = bDao.getCommentList(board_num);
		
		req.setAttribute("list", list);
		
	}
	
	

}
