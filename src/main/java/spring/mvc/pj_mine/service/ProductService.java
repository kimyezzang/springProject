package spring.mvc.pj_mine.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ProductService {

	// 상품추가 처리
	public void productAddACtion(MultipartHttpServletRequest req, Model model);
		
	// 상품 수정 처리
	public void productUpdateACtion(MultipartHttpServletRequest req, Model model);
	
	// 상품 삭제 처리
	public void productDeleteACtion(HttpServletRequest req, Model model);
	
	// 상품 리스트
	public void productList(HttpServletRequest req, Model model);
		
	// 상세페이지
	public void productDetail(HttpServletRequest req, Model model);
		
	//---------------[고객 상품]---------------------
	// 고객 상품 리스트
	// 상의 리스트
	public void customerProductList1(HttpServletRequest req, Model model);
	
	// 하의 리스트
	public void customerProductList2(HttpServletRequest req,  Model model);
		
	// 신발 리스트
	public void customerProductList3(HttpServletRequest req,  Model model);
	
	// 신상품 리스트
	public void customerNewProductList(HttpServletRequest req,  Model model);
			
	// ------------ [바로구매] ---------------------
	public void nowBuy(HttpServletRequest req, Model model);
		
	//---------------[장바구니]---------------------
	// 장바구니 추가 처리
	public void basketAddACtion(HttpServletRequest req, Model model);
	
	// 장바구니 상세페이지
	public void basketDetail(HttpServletRequest req, Model model);
	
	// 장바구니 상품 삭제
	public void basketDelete(HttpServletRequest req, Model model);
	
	// 장바구니에서 구매
	public void basketBuy(HttpServletRequest req, Model model);
	
	//---------------[주문목록]---------------------
	// 고객 주문 리스트
	public void customerOrderList(HttpServletRequest req, Model model);

	// 배송준비중 취소하기 
	public void customerPreparedOrederDelete(HttpServletRequest req, Model model);
	
	// 배송완료 환불하기
	public void customerOrederRefund(HttpServletRequest req, Model model);
	
	// ------- [검색 처리] ----------
	public void search(HttpServletRequest req, Model model);
	
	// ---------------- [관리자 주문관리] ---------------------
	// 주문 목록
	public void managerOrderList(HttpServletRequest req, Model model);

	// 주문 승인 목록
	public void managerOrderApprovalList(HttpServletRequest req, Model model);
	
	// 주문 취소
	public void managerOrderCancel(HttpServletRequest req, Model model);
	
	// 주문 승인
	public void managerOrderApproval(HttpServletRequest req, Model model);
	
	// 환불 요청 목록
	public void refundRequsetList(HttpServletRequest req, Model model);
		
	// 환불 승인
	public void refundApproval(HttpServletRequest req, Model model);
		
	// 환불 승인 목록
	public void refundApprovalList(HttpServletRequest req, Model model);
	
	// 환불 취소
	public void refundCancel(HttpServletRequest req, Model model);
	
	// 환불 취소 목록
	public void refundCancelList(HttpServletRequest req, Model model);
	
	// 결산
	public void totalMoney(HttpServletRequest req, Model model);

}
