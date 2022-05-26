package spring.mvc.pj_mine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import page.Paging;
import spring.mvc.pj_mine.dao.EtcDAO;
import spring.mvc.pj_mine.dto.CustomerDTO;


@Service
public class EtcServiceImpl implements EtcService{

	@Autowired
	EtcDAO dao;
	
	//-------[회원정보]
	// 회원정보 상세페이지
	@Override
	public void userDetail(HttpServletRequest req, Model model) {
		System.out.println("서비스 - 회원정보 상세페이지");
		
		// 화면으로부터 입력받은 값을 받는다.
		String id = req.getParameter("id");
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		// 상품상세 정보를 가져와서 DTO로 리턴
		CustomerDTO dto = dao.getUserDetail(id);
		System.out.println("CustomerDTO : " + dto);
		// jsp로 처리결과 전달(request나 session으로 결과 전달)
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("dto", dto);
		
	}

	// 회원정보 삭제 처리
	@Override
	public void userDeleteACtion(HttpServletRequest req, Model model) {
		System.out.println("서비스 - 회원정보 삭제 처리");
		
		// 화면으로부터 입력받은 값을 받는다.
		String id = req.getParameter("id");
		
		//  삭제
		int updateCnt = dao.userDelete(id);
		// 6단계. jsp로 처리결과 전달
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		model.addAttribute("updateCnt", updateCnt);
		model.addAttribute("pageNum", pageNum);

	}

	// 회원정보 리스트
	@Override
	public void userList(HttpServletRequest req, Model model) {
		System.out.println("서비스 - userList");
		// 3단계. 화면으로부터 입력받은 값을받는다.
		String pageNum = req.getParameter("pageNum");
		
		// 
		Paging paging = new Paging(pageNum);
		
		// product 카운트
		int total = dao.userCnt();
		paging.setTotalCount(total);
		
		System.out.println("total : " + total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		// 5-2단계.
		List<CustomerDTO> list = dao.userList(map); 
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		
	}


}
