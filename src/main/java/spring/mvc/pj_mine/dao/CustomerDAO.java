package spring.mvc.pj_mine.dao;

import java.util.List;
import java.util.Map;

import spring.mvc.pj_mine.dto.BoardDTO;
import spring.mvc.pj_mine.dto.CustomerDTO;
import spring.mvc.pj_mine.dto.ProductDTO;

public interface CustomerDAO {

	// ID 중복확인 처리
	public int idCheck(String strId);
	
	// email 중복확인 처리
	public int emailCheck(String strEmail);
			
	// 시큐리티 - 가입성공시 이메일인증을 위해 이메일 전송
	public void sendEmail(String email, String key);
	
	// 시큐리티
	public int selectKey(String key);
	
	// 시큐리티
	public int updateGrade(String key);
		
	// 회원가입 처리
	public int insertCustomer(CustomerDTO dto);
			
	// --------- [회원 정보 수정, 탈퇴, 주문내역] ----------
	// 회원정보 인증 및 탈퇴 처리
	public int deleteCustomer(String strId);
	
	// 회원정보 인증 및 상세페이지
	public CustomerDTO getCustomerDetail(String strId);
	
	// 회원정보 수정 처리
	public int updateCustomer(CustomerDTO dto);
	
}
