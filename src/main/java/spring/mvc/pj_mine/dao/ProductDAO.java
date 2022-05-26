package spring.mvc.pj_mine.dao;

import java.util.List;
import java.util.Map;

import spring.mvc.pj_mine.dto.BasketDTO;
import spring.mvc.pj_mine.dto.OrderDTO;
import spring.mvc.pj_mine.dto.ProductDTO;
import spring.mvc.pj_mine.dto.TotalMoneyDTO;


public interface ProductDAO {

	// 상품추가 처리
	public int productInsert(ProductDTO dto);
			
	// 상품 수정 처리
	public int productUpdate(ProductDTO dto);
		
	// 상품 상세페이지
	public ProductDTO getProductDetail(int pdNo);
		
	// 상품 삭제 처리
	public int productDelete(int pdNo);
		
	// 상품 갯수
	public int productCnt();
	
	// 상품목록 int start ,int end
	public List<ProductDTO> productList(Map<String,Object> map);

	//----- [고객 상품] -----
	// 상품 목록 갯수
	public int customerProductCnt1(String strCategoryCnt);
	
	// 신상품 상품 갯수
	public int customerNewProductCnt();	
	
	// 상품 목록 리스트
	public List<ProductDTO> customerProductList1(Map<String,Object> map);
	
	// 신상품 목록
	public List<ProductDTO> customerNewProductList(Map<String,Object> map);

	// ----- [바로구매] --------
	public int nowBuy(BasketDTO dto);
	
	// ------ [장바구니] -----------
	// 장바구니 추가 처리
	public int basketInsert(BasketDTO dto);
	
	// 장바구니 목록
	public List<BasketDTO> basketList(String id);
	
	// 장바구니 상품 삭제
	public int baksetDelete(int bkNo);
	
	// 장바구니 구매 처리1 - 장바구니 체크 목록 가져오기
	public int baksetGet(List<String> list);
	
	// 장바구니 회원 주소 정보
	public String basketAddress(String id);
	
	// ------ [주문목록] -----------
	public List<OrderDTO> customerOrderList(Map<String,Object> map);
	
	// 주문목록 상품 갯수
	public int customerOrderCnt(String id);
	
	// 주문목록 배송준비중 취소하기
	public int customerPreparedOrederDelete(String orNo);
	
	// 주문목록 배송완료 환불하기
	public int customerOrederRefund(String orNo);
	
	// ----- [검색] -----
	// 검색
	public List<ProductDTO> search(Map<String,Object> map);
	
	// 검색 갯수
	public int searchCnt(String content);
	
	// -------- [관리자 주문관리] -------------
	// 주문목록 int start ,int end
	public List<OrderDTO> managerOrderList(Map<String,Object> map);
	
	// 주문목록 상품 갯수
	public int managerOrderCnt();
	
	// 주문 취소
	public int managerOrderCancel(String orNo);
	
	// 주문 승인
	public int managerOrederApproval(String orNo);
	
	// 주문승인 목록 int start ,int end
	public List<OrderDTO> managerOrderApprovalList(Map<String,Object> map);
	
	// 주문승인목록 상품 갯수
	public int managerOrderApprovalCnt();
	
	// --- 환불 ---
	// 환불요청 목록 int start ,int end
	public List<OrderDTO> refundRequestList(Map<String,Object> map);
	
	// 환불요청 갯수
	public int refundRequestCnt();
	
	// 환불 취소
	public int refundCancel(String orNo);
	
	// 환불 승인
	public int refundApproval(String orNo);
	
	// 환불승인 목록 int start ,int end
	public List<OrderDTO> refundApprovalList(Map<String,Object> map);
	
	// 환불 승인 목록 갯수
	public int refundApprovalCnt();
	
	// 환불취소 목록 int start ,int end
	public List<OrderDTO> refundCancelList(Map<String,Object> map);
	
	// 환불 취소 목록 갯수
	public int refundCancelCnt();
	
	// 결산
	public List<Integer> totalMoney();
		
	
}
