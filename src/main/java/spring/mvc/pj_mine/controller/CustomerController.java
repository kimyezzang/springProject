package spring.mvc.pj_mine.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.mvc.pj_mine.service.CustomerServiceImpl;
import spring.mvc.pj_mine.service.ProductServiceImpl;

@Controller
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerServiceImpl service;
	
	@Autowired
	ProductServiceImpl pdService;
	
	
	// -----------[main]--------------------
	@RequestMapping("main.do")
	public String main() {
		logger.info("[url] ==> main.do");
		
		return "common/main";
	}
	
	// 로그인 화면
	@RequestMapping("login.do")
	public String login(Model model) {
		logger.info("[url ==> login.do]");
		
		model.addAttribute("selectCnt", 2);	// 환영합니다!!
		return "customer/login/login";
	}
	
	// 로그아웃 페이지
	@RequestMapping("logout.do")
	public String logout(HttpServletRequest req , Model model) {
		logger.info("[url ==> logout.do]");
		
		req.getSession().invalidate();	// 일괄 세션 삭제
		model.addAttribute("selectCnt", 2);	// 환영합니다!!
		return "common/main";
	}
	//---------------------[회원가입]----------------
	// 회원가입 화면	
	@RequestMapping("join.do")
	public String join() {
		logger.info("[url ==> join.do]");
		
		return "customer/join/join";
	}
	
	//ID 중복확인처리
	@RequestMapping("confirmIdAction.do")
	public String confirmIdAction(HttpServletRequest req , Model model) {
		logger.info("[url ==> confirmIdAction.do]");
		
		service.confirmIdAction(req, model);
		
		return "customer/join/confirmIdAction";
	}
	
	// email 중복확인 처리	
	@RequestMapping("confirmEmailAction.do")
	public String confirmEmailAction(HttpServletRequest req , Model model) {
		logger.info("[url ==> confirmEmailAction.do]");
		
		service.confirmEmailAction(req, model);
		
		return "customer/join/confirmEmailAction";
	}
	
	// 회원가입 처리	 - 시큐리티(비밀번호 암호화)
	@RequestMapping("joinAction.do")
	public String joinAction(HttpServletRequest req, Model model) {
		logger.info("[url ==> joinAction.do]");
		
		service.signInAction(req, model);
		return "customer/join/joinAction";
	}
	
	// 시큐리티 - 가입성공시 이메일인증을 위해 이메일 전송
	// DAOImpl의 sendEmail(String email, String key)에서 호출
	@RequestMapping("emailChk.do")
	public String emailChk(HttpServletRequest req, Model model) {
		logger.info("[url ==> emailChk.do]");
		
		service.emailChkAction(req, model);
		return "customer/join/emailChkAction";
	}
	
	// 회원가입 성공
	@RequestMapping("mainSuccess.do")
	public String mainSuccess(HttpServletRequest req, Model model) {
		logger.info("[url ==> mainSuccess.do]");
		
		// "mainSuccess.do?insertCnt=" + insertCnt
		int cnt = Integer.parseInt(req.getParameter("insertCnt"));
		model.addAttribute("selectCnt", cnt);
		System.out.println("cnt : " + cnt);		// 1
		
		return "customer/login/login";
	}
	// ------------- [회원수정] ------------------
	// 회원수정 - 인증화면
	@RequestMapping("modifyCustomer.do")
	public String modifyCustomer(HttpServletRequest req, Model model) {
		logger.info("[url ==> modifyCustomer.do]");
		
		return "customer/mypage/modifyCustomer";
	}
	
	// 회원수정 - 상세페이지	
	@RequestMapping("modifyDetailAction.do")
	public String modifyDetailAction(HttpServletRequest req, Model model) {
		logger.info("[url ==> modifyDetailAction.do]");
		
		service.modifyDetailAction(req, model);
		
		return "customer/mypage/modifyDetailAction";
	}
	
	// 회원수정 처리	
	@RequestMapping("modifyCustomerAction.do")
	public String modifyCustomerAction(HttpServletRequest req, Model model) {
		logger.info("[url ==> modifyCustomerAction.do]");
		
		service.modifyCustomerAction(req, model);
		
		return "customer/mypage/modifyCustomerAction";
	}
	
	// 회원탈퇴 - 인증화면
	@RequestMapping("deleteCustomer.do")
	public String deleteCustomer(HttpServletRequest req, Model model) {
		logger.info("[url ==> deleteCustomer.do]");
		
		return "customer/mypage/deleteCustomer";
	}
	
	// 회원탈퇴 처리
	@RequestMapping("deleteCustomerAction.do")
	public String deleteCustomerAction(HttpServletRequest req, Model model) {
		logger.info("[url ==> deleteCustomerAction.do]");
		
		service.deleteCustomerAction(req, model);
		
		return "customer/mypage/deleteCustomerAction";
	}
	// ------------------[ 상품 ]-----------------------
	// 상의 리스트
	@RequestMapping("productList1.do")
	public String productList1(HttpServletRequest req, Model model) {
		logger.info("[url ==> productList1.do]");
		
		pdService.customerProductList1(req, model);
		
		return "customer/product/productList1";
	}
	// 하의 리스트
	@RequestMapping("productList2.do")
	public String productList2(HttpServletRequest req, Model model) {
		logger.info("[url ==> productList2.do]");
		
		pdService.customerProductList2(req, model);
		
		return "customer/product/productList2";
	}
	
	// 신발 리스트
	@RequestMapping("productList3.do")
	public String productList3(HttpServletRequest req, Model model) {
		logger.info("[url ==> productList3.do]");
		
		pdService.customerProductList3(req, model);
		
		return "customer/product/productList3";
	}
	
	// 신상품 리스트
	@RequestMapping("newProductList.do")
	public String newProductList(HttpServletRequest req, Model model) {
		logger.info("[url ==> newProductList.do]");
		
		pdService.customerNewProductList(req, model);
		
		return "customer/product/newProductList";
	}
	
	// 상품 상세
	@RequestMapping("customerProductDetail.do")
	public String customerProductDetail(HttpServletRequest req, Model model) {
		logger.info("[url ==> customerProductDetail.do]");
		
		pdService.productDetail(req, model);
		
		return "customer/product/productDetail";
	}
	
	// ------------------ 바로구매 ------------------------
	// 바로구매	
	@RequestMapping("customerNowBuy.do")
	public void customerNowBuy(HttpServletRequest req, Model model) {
		logger.info("[url ==> customerNowBuy.do]");
		
		pdService.nowBuy(req, model);
		
	}
	
	// ------------------[ 장바구니 ]-----------------------
	// 장바구니 담기
	@RequestMapping("customerBasket.do")
	public void customerBasket(HttpServletRequest req, Model model) {
		logger.info("[url ==> customerBasket.do]");
		
		pdService.basketAddACtion(req, model);
		
	}
	
	// 장바구니 페이지
	@RequestMapping("customerBasketList.do")
	public String customerBasketList(HttpServletRequest req, Model model) {
		logger.info("[url ==> customerBasketList.do]");
		
		pdService.basketDetail(req, model);
		
		return "customer/basket/customerBasket";
	}
	
	// 장바구니 상품 삭제
	@RequestMapping("customerBasketDelete.do")
	public String customerBasketDelete(HttpServletRequest req, Model model) {
		logger.info("[url ==> customerBasketDelete.do]");
		
		pdService.basketDelete(req, model);
		
		return "redirect:/customerBasketList.do";
	}
	
	// 장바구니 회원 주소 불러오기
	// 장바구니 구매하기
	@RequestMapping("customerBasketBuy.do")
	public String customerBasketBuy(HttpServletRequest req, Model model) {
		logger.info("[url ==> customerBasketBuy.do]");
		
		pdService.basketBuy(req, model);
		
		return "redirect:/customerBasketList.do";
	}
	// ------------------[ 주문내역 ]-----------------------
	// 고객 주문리스트
	@RequestMapping("customerOrderList.do")
	public String customerOrderList(HttpServletRequest req, Model model) {
		logger.info("[url ==> customerOrderList.do]");
		
		pdService.customerOrderList(req, model);
		
		return "customer/mypage/customerOrderList";
	}
	
	// 고객 배송준비중 > 취소하기 : 주문리스트에서 삭제
	@RequestMapping("customerPreparedOrederDelete.do")
	public String customerPreparedOrederDelete(HttpServletRequest req, Model model) {
		logger.info("[url ==> customerPreparedOrederDelete.do]");
		
		pdService.customerPreparedOrederDelete(req, model);
		
		return "redirect:/customerOrderList.do";
	}
	
	// 고객 배송완료 > 환불하기 : 주문리스트 상태 환불요청으로 변경
	@RequestMapping("customerOrederRefund.do")
	public String customerOrederRefund(HttpServletRequest req, Model model) {
		logger.info("[url ==> customerOrederRefund.do]");
		
		pdService.customerOrederRefund(req, model);
		
		return "redirect:/customerOrderList.do";
	}
	
	// ----------- [검색] ------------------
	@RequestMapping("search.do")
	public String search(HttpServletRequest req, Model model) {
		logger.info("[url ==> search.do]");
		
		pdService.search(req, model);
		
		return "customer/product/searchProductList";
	}
	// ------------------[ 게시판 ]-----------------------
	// 게시판 목록
	@RequestMapping("BoardList.do")
	public String BoardList(HttpServletRequest req, Model model) 
			throws ServletException, IOException {
		logger.info("[url ==> BoardList.do]");
		
		service.boardList(req,model);
		
		return "customer/board/customerBoardList";
	}
	
	// 글쓰기
	@RequestMapping("boardInsert.do")
	public String boardInsert(HttpServletRequest req, Model model) 
			throws ServletException, IOException {
		logger.info("[url ==> boardInsert.do]");
		
		return "customer/board/board_insert";
	}
	// 글쓰기 액션
	@RequestMapping("board_insertAction.do")
	public String board_insertAction(HttpServletRequest req, Model model) 
			throws ServletException, IOException {
		logger.info("[url ==> board_insertAction.do]");
		
		service.boardInsert(req,model);
		
		return "redirect:/BoardList.do";
	}
	
	// 상세페이지
	@RequestMapping("board_detailAction.do")
	public String board_detailAction(HttpServletRequest req, Model model) 
			throws ServletException, IOException {
		logger.info("[url ==> board_detailAction.do]");
		
		service.boardDetail(req,model);
		
		return "customer/board/board_detailAction";
	}
	
	// -- 댓글 --
	// 댓글 작성
	@RequestMapping("commentAdd.do")
	public void commentAdd(HttpServletRequest req, Model model) 
			throws ServletException, IOException {
		logger.info("[url ==> commentAdd.do]");
		
		service.commentAdd(req,model);
		
	}
	// 댓글 조회
	@RequestMapping("commentList.do")
	public String commentList(HttpServletRequest req, Model model)
			throws ServletException, IOException {
		logger.info("[url ==> commentList.do]");
		
		service.commentList(req,model);
		
		return "customer/board/board_commentList";
	}
	
}
