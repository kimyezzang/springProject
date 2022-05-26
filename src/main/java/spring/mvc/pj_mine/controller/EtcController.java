package spring.mvc.pj_mine.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.mvc.pj_mine.service.EtcServiceImpl;

@Controller
public class EtcController {

	private static final Logger logger = LoggerFactory.getLogger(EtcController.class);

	@Autowired
	EtcServiceImpl service;
	
	// ------- [회원정보] ------
	@RequestMapping("userList.etc")
	public String userList(HttpServletRequest req , Model model) {
		logger.info("[url ==> userList.etc]");
		
		service.userList(req, model);
		
		return "manager/etc/userList";
	}
	
	// 회원정보 상세
	@RequestMapping("userDetail.etc")
	public String userDetail(HttpServletRequest req , Model model) {
		logger.info("[url ==> userDetail.etc]");
		
		service.userDetail(req, model);
		
		return "manager/etc/userDetail";
	}
	
	// 회원 정보삭제
	@RequestMapping("userDeleteAction.etc")
	public String userDeleteAction(HttpServletRequest req , Model model) {
		logger.info("[url ==> userDeleteAction.etc]");
		
		service.userDeleteACtion(req, model);
		
		return "manager/etc/userDeleteAction";
	}
}
