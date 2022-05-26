package spring.mvc.pj_mine.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.mvc.pj_mine.dto.BasketDTO;
import spring.mvc.pj_mine.dto.OrderDTO;
import spring.mvc.pj_mine.dto.ProductDTO;
import spring.mvc.pj_mine.dto.TotalMoneyDTO;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	SqlSession sqlSession;
	
	// 상품추가 처리
	@Override
	public int productInsert(ProductDTO dto) {
		System.out.println("ProductDAOImpl - productInsert");
		
		int insertCnt = sqlSession.insert("spring.mvc.pj_mine.dao.ProductDAO.productInsert",dto);
	      
		return insertCnt;
	}

	// 상품 수정 처리
	@Override
	public int productUpdate(ProductDTO dto) {
		System.out.println("DAO - productUpdate");
		
		ProductDAO dao = sqlSession.getMapper(ProductDAO.class);
	    return dao.productUpdate(dto);
	}

	// 상품 삭제 처리
	@Override
	public int productDelete(int pdNo) {
		System.out.println("DAO - productDelete");
		
		int updateCnt = sqlSession.delete("spring.mvc.pj_mine.dao.ProductDAO.productDelete",pdNo);
		return updateCnt;
	}

	// 상품 갯수
	@Override
	public int productCnt() {
		System.out.println("ProductDAO - productCnt");
	     
      int selectCnt = sqlSession.selectOne("spring.mvc.pj_mine.dao.ProductDAO.mProductCnt");
      
      return selectCnt;
	      
	}

	// 상품목록 int start ,int end
	@Override
	public List<ProductDTO> productList(Map<String, Object> map) {
		System.out.println("DAO - productList");
		
		List<ProductDTO> list = sqlSession.selectList("spring.mvc.pj_mine.dao.ProductDAO.mProductList",map);
		
		return list;
	}
	
	
	// 상품 상세페이지
	@Override
	public ProductDTO getProductDetail(int pdNo) {
		System.out.println("ProductDAOImpl - getProductDetail");
		
		ProductDTO dto = sqlSession.selectOne("spring.mvc.pj_mine.dao.ProductDAO.getProductDetail",pdNo);
		return dto;
	}
	
	//----- [고객 상품] -----
	// 상의 상품 갯수
	@Override
	public int customerProductCnt1(String strCategoryCnt) {
		System.out.println("ProductDAO - productCnt");
	    
	    int selectCnt = sqlSession.selectOne("spring.mvc.pj_mine.dao.ProductDAO.productCnt",strCategoryCnt);
	      
	    return selectCnt;
	}

	// 신상품 상품 갯수
	@Override
	public int customerNewProductCnt() {
		System.out.println("ProductDAO - 신상품 상품 갯수");
	    
	    int selectCnt = sqlSession.selectOne("spring.mvc.pj_mine.dao.ProductDAO.newProductCnt");
	      
	    return selectCnt;	
	}

	// 상품 목록 
	@Override
	public List<ProductDTO> customerProductList1(Map<String,Object> map) {
		System.out.println("ProductDAO - 상의 목록");
		
		List<ProductDTO> list = sqlSession.selectList("spring.mvc.pj_mine.dao.ProductDAO.productList",map);
		System.out.println("list:" + list);
		return list;
		
	}

	// 신상품 목록
	@Override
	public List<ProductDTO> customerNewProductList(Map<String,Object> map) {
		System.out.println("ProductDAO - 신상품 목록");

		List<ProductDTO> list = sqlSession.selectList("spring.mvc.pj_mine.dao.ProductDAO.newProductList",map);
		System.out.println("list:" + list);
		return list;
	}

	
	// ----- [바로구매] --------
	@Override
	public int nowBuy(BasketDTO dto) {
		System.out.println("DAO - nowBuy");
		
		int updateCnt = sqlSession.insert("spring.mvc.pj_mine.dao.ProductDAO.nowBuy",dto);
		return updateCnt;
	}

	// -------------- [장바구니] -------------------
	// 장바구니 추가 처리
	@Override
	public int basketInsert(BasketDTO dto) {
		System.out.println("DAO - basketInsert");
		
		int updateCnt = sqlSession.insert("spring.mvc.pj_mine.dao.ProductDAO.basketInsert",dto);
		return updateCnt;
	}

	// 장바구니 목록
	@Override
	public List<BasketDTO> basketList(String id) {
		System.out.println("DAO - basketList");
		
		List<BasketDTO> list = sqlSession.selectList("spring.mvc.pj_mine.dao.ProductDAO.basketList",id);
		return list;
	}

	// 장바구니 상품 삭제
	@Override
	public int baksetDelete(int bkNo) {
		System.out.println("DAO - baksetDelete");
		
		int updateCnt = sqlSession.delete("spring.mvc.pj_mine.dao.ProductDAO.baksetDelete",bkNo);
		return updateCnt;
	}

	// 장바구니 구매 처리1 - 장바구니 체크 목록 가져오기
	@Override
	public int baksetGet(List<String> list) {
		System.out.println("DAO - baksetGet");
		
		String oldBkNo;
		int insertCnt = 0;
		int deleteCnt = 0;
		
		for(int i=0; i<list.size(); i++) {
			oldBkNo = list.get(i);
			OrderDTO dto = sqlSession.selectOne("spring.mvc.pj_mine.dao.ProductDAO.baksetGet1",oldBkNo);
			
			insertCnt = sqlSession.insert("spring.mvc.pj_mine.dao.ProductDAO.baksetGet2",dto);
			if(insertCnt == 1) {
				System.out.println("insertCnt" + insertCnt);
				int bkNo = Integer.parseInt(oldBkNo);
				System.out.println("bkNo" + bkNo);
				deleteCnt = sqlSession.delete("spring.mvc.pj_mine.dao.ProductDAO.baksetDelete",bkNo);
			}
		}
		
		return deleteCnt;
	}

	// 장바구니 회원주소 가져오기
	@Override
	public String basketAddress(String id) {
		String address = sqlSession.selectOne("spring.mvc.pj_mine.dao.ProductDAO.basketAddress",id);
		
		return address;
	}

	// ------ [주문목록] -----------
	@Override
	public List<OrderDTO> customerOrderList(Map<String,Object> map) {
		System.out.println("DAO - customerOrderList");
		
		List<OrderDTO> list = sqlSession.selectList("spring.mvc.pj_mine.dao.ProductDAO.customerOrderList",map);
		return list;
	}

	// 주문목록 상품 갯수
	@Override
	public int customerOrderCnt(String id) {
		System.out.println("ProductDAO - 주문목록 상품 갯수");
	    
	    int selectCnt = sqlSession.selectOne("spring.mvc.pj_mine.dao.ProductDAO.customerOrderCnt",id);
	      
	    return selectCnt;	
	}

	// 주문목록 배송준비중 취소하기
	@Override
	public int customerPreparedOrederDelete(String orNo) {
		System.out.println("DAO - customerPreparedOrederDelete");
		
		int updateCnt = sqlSession.delete("spring.mvc.pj_mine.dao.ProductDAO.customerPreparedOrederDelete",orNo);
		return updateCnt;
	}

	// 주문목록 배송완료 환불하기
	@Override
	public int customerOrederRefund(String orNo) {
		System.out.println("DAO - customerOrederRefund");
		
		int updateCnt = sqlSession.update("spring.mvc.pj_mine.dao.ProductDAO.customerOrederRefund",orNo);
		return updateCnt;
	}

	// ----- [검색] -----
	// 검색
	@Override
	public List<ProductDTO> search(Map<String, Object> map) {
		System.out.println("ProductDAO - 검색 상품 목록");

		List<ProductDTO> list = sqlSession.selectList("spring.mvc.pj_mine.dao.ProductDAO.search",map);
		System.out.println("list:" + list);
		return list;
	}

	// 검색 갯수
	@Override
	public int searchCnt(String content) {
		System.out.println("ProductDAO - 검색 갯수");
	    
	    int selectCnt = sqlSession.selectOne("spring.mvc.pj_mine.dao.ProductDAO.searchCnt",content);
	      
	    return selectCnt;
	}

	// -------- [관리자 주문관리] -------------
	// 주문목록 int start ,int end
	@Override
	public List<OrderDTO> managerOrderList(Map<String, Object> map) {
		System.out.println("ProductDAO - 주문목록");
		
		List<OrderDTO> list = sqlSession.selectList("spring.mvc.pj_mine.dao.ProductDAO.managerOrderList",map);
		System.out.println("list:" + list);
		return list;
	}

	// 주문목록 상품 갯수
	@Override
	public int managerOrderCnt() {
		System.out.println("ProductDAO - 주문목록 상품 갯수");
		 int selectCnt = sqlSession.selectOne("spring.mvc.pj_mine.dao.ProductDAO.managerOrderCnt");
	      
		  return selectCnt;
	}

	// 주문 취소
	@Override
	public int managerOrderCancel(String orNo) {
		System.out.println("ProductDAO - 주문 취소");
		int updateCnt = sqlSession.update("spring.mvc.pj_mine.dao.ProductDAO.managerOrderCancel",orNo);
		return updateCnt;
	}

	// 주문 승인
	@Override
	public int managerOrederApproval(String orNo) {
		System.out.println("ProductDAO - 주문 승인");
		int updateCnt = sqlSession.update("spring.mvc.pj_mine.dao.ProductDAO.managerOrederApproval1",orNo);
		int updateCnt2 = 0;
		if(updateCnt != 0) {
		updateCnt2 = sqlSession.update("spring.mvc.pj_mine.dao.ProductDAO.managerOrederApproval2",orNo);
		}
		return updateCnt2;
	}

	// 주문승인 목록 int start ,int end
	@Override
	public List<OrderDTO> managerOrderApprovalList(Map<String, Object> map) {
		System.out.println("ProductDAO - 주문승인 목록");
		List<OrderDTO> list = sqlSession.selectList("spring.mvc.pj_mine.dao.ProductDAO.managerOrderApprovalList",map);
		System.out.println("list:" + list);
		return list;
	}

	// 주문승인목록 상품 갯수
	@Override
	public int managerOrderApprovalCnt() {
		System.out.println("ProductDAO - 주문승인목록 상품 갯수");
		 int selectCnt = sqlSession.selectOne("spring.mvc.pj_mine.dao.ProductDAO.managerOrderApprovalCnt");
	      
		  return selectCnt;
	}

	// --- 환불 ---
	// 환불요청 목록 int start ,int end
	@Override
	public List<OrderDTO> refundRequestList(Map<String, Object> map) {
		System.out.println("ProductDAO - 환불요청 목록");
		List<OrderDTO> list = sqlSession.selectList("spring.mvc.pj_mine.dao.ProductDAO.refundRequestList",map);
		System.out.println("list:" + list);
		return list;
	}

	// 환불요청 갯수
	@Override
	public int refundRequestCnt() {
		System.out.println("ProductDAO - 환불요청 갯수");
		 int selectCnt = sqlSession.selectOne("spring.mvc.pj_mine.dao.ProductDAO.refundRequestCnt");
	      
		  return selectCnt;
	}

	// 환불 취소
	@Override
	public int refundCancel(String orNo) {
		System.out.println("ProductDAO - 환불 취소");
		int updateCnt = sqlSession.update("spring.mvc.pj_mine.dao.ProductDAO.refundCancel",orNo);
		return updateCnt;
	}

	// 환불 승인
	@Override
	public int refundApproval(String orNo) {
		System.out.println("ProductDAO - 환불 승인");
		int updateCnt = sqlSession.update("spring.mvc.pj_mine.dao.ProductDAO.refundApproval1",orNo);
		int updateCnt2 = 0;
		if(updateCnt != 0) {
		 updateCnt2 = sqlSession.update("spring.mvc.pj_mine.dao.ProductDAO.refundApproval2",orNo);
		}
		return updateCnt2;
	}

	// 환불승인 목록 int start ,int end
	@Override
	public List<OrderDTO> refundApprovalList(Map<String, Object> map) {
		System.out.println("ProductDAO - 환불승인 목록");
		List<OrderDTO> list = sqlSession.selectList("spring.mvc.pj_mine.dao.ProductDAO.refundApprovalList",map);
		System.out.println("list:" + list);
		return list;
	}

	// 환불 승인 목록 갯수
	@Override
	public int refundApprovalCnt() {
		System.out.println("ProductDAO - 환불 승인 목록 갯수");
		int selectCnt = sqlSession.selectOne("spring.mvc.pj_mine.dao.ProductDAO.refundApprovalCnt");
	      
		  return selectCnt;
	}

	// 환불취소 목록 int start ,int end
	@Override
	public List<OrderDTO> refundCancelList(Map<String, Object> map) {
		System.out.println("ProductDAO - 환불취소 목록");
		List<OrderDTO> list = sqlSession.selectList("spring.mvc.pj_mine.dao.ProductDAO.refundCancelList",map);
		System.out.println("list:" + list);
		return list;
	}

	// 환불 취소 목록 갯수
	@Override
	public int refundCancelCnt() {
		System.out.println("ProductDAO - 환불 취소 목록 갯수");
		int selectCnt = sqlSession.selectOne("spring.mvc.pj_mine.dao.ProductDAO.refundCancelCnt");
	      
		  return selectCnt;
	}

	// 결산
	@Override
	public List<Integer> totalMoney() {
		System.out.println("ProductDAO - 결산");
		
		List<Integer> list = sqlSession.selectList("spring.mvc.pj_mine.dao.ProductDAO.totalMoney");
		/* List<Integer> */
		return list;
	}

	

}
