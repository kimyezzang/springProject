package spring.mvc.pj_mine.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.mvc.pj_mine.dto.CustomerDTO;

@Repository
public class EtcDAOImpl implements EtcDAO{

	@Autowired
	SqlSession sqlSession;
	
	// 회원정보 상세페이지
	@Override
	public CustomerDTO getUserDetail(String id) {
		System.out.println("EtcDAO - getUserDetail");
		
		CustomerDTO dto = sqlSession.selectOne("spring.mvc.pj_mine.dao.EtcDAO.getUserDetail",id);
		return dto;
	}

	// 회원정보 삭제 처리
	@Override
	public int userDelete(String id) {
		System.out.println("EtcDAO - userDelete");
		
		int deleteCnt = sqlSession.delete("spring.mvc.pj_mine.dao.EtcDAO.userDelete",id);
		return deleteCnt;
	}

	// 회원정보 갯수
	@Override
	public int userCnt() {
		System.out.println("EtcDAO - userCnt");
	     
	      int selectCnt = sqlSession.selectOne("spring.mvc.pj_mine.dao.EtcDAO.userCnt");
	      
	      return selectCnt;
	}

	// 회원정보 목록 int start ,int end
	@Override
	public List<CustomerDTO> userList(Map<String, Object> map) {
		System.out.println("DAO - userList");
		
		List<CustomerDTO> list = sqlSession.selectList("spring.mvc.pj_mine.dao.EtcDAO.userList",map);
		
		return list;
	}

	

}
