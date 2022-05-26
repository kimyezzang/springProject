package spring.mvc.pj_mine.dao;

import java.util.List;
import java.util.Map;

import spring.mvc.pj_mine.dto.CustomerDTO;


public interface EtcDAO {

		// 회원정보 상세페이지
		public CustomerDTO getUserDetail(String id);
			
		// 회원정보 삭제 처리
		public int userDelete(String id);
			
		// 회원정보 갯수
		public int userCnt();
		
		// 회원정보 목록 int start ,int end
		public List<CustomerDTO> userList(Map<String,Object> map);
}
