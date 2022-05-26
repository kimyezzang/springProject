package spring.mvc.pj_mine.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

public interface CustomerService {

	// -- [회원가입] --
	// ID중복확인 처리
	public void confirmIdAction(HttpServletRequest req, Model model);
	
	// Email중복확인 처리
	public void confirmEmailAction(HttpServletRequest req, Model model);
	
	// 회원가입 처리
	public void signInAction(HttpServletRequest req, Model model);
		
	// 이메일 인증 후 권한(enabled) update - 시큐리티
	public void emailChkAction(HttpServletRequest req, Model model);
		
	// --------- [회원 정보 수정 , 탈퇴,  주문내역] ----------
	// 회원정보 인증 및 탈퇴 처리
	public void deleteCustomerAction(HttpServletRequest req, Model model);
	
	// 회원정보 인증 및 상세페이지
	public void modifyDetailAction(HttpServletRequest req, Model model);
	
	// 회원정보 수정 처리
	public void modifyCustomerAction(HttpServletRequest req, Model model);
	
	//-----------------------------------[게시판]---------------------------
	// 게시글 목록
	public void boardList(HttpServletRequest req, Model model)
			throws ServletException, IOException;
	
	// 게시글 작성처리 페이지
	public void boardInsert(HttpServletRequest req, Model model)
			throws ServletException, IOException;
	
	// 게시글 상세페이지
	public void boardDetail(HttpServletRequest req, Model model)
			throws ServletException, IOException;
	
	//-----------------------------------[댓글]---------------------------
	// 댓글 추가처리 페이지
	public void commentAdd(HttpServletRequest req, Model model)
			throws ServletException, IOException; 
		
	// 댓글 목록처리 페이지
	public void commentList(HttpServletRequest req, Model model)
			throws ServletException, IOException;
			
			
	
}
