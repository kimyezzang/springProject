package spring.mvc.pj_mine.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface EtcService {

	//-------[회원정보]
	// 회원정보 상세페이지
	public void userDetail(HttpServletRequest req, Model model);
	
	// 회원정보 삭제 처리
	public void userDeleteACtion(HttpServletRequest req, Model model);
	
	// 회원정보 리스트
	public void userList(HttpServletRequest req, Model model);
		
}
