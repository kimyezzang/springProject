package spring.mvc.pj_mine.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import spring.mvc.pj_mine.service.ProductServiceImpl;

@Controller
public class StockController {

	private static final Logger logger = LoggerFactory.getLogger(StockController.class);
	
	@Autowired
	ProductServiceImpl service;	

	// -------------------- product -----------------------
	// 상품목록
	@RequestMapping("productList.st")
	public String productList(HttpServletRequest req , Model model) {
		logger.info("[url ==> productList.st]");
		
		service.productList(req, model);
		
		return "manager/stock/productList";
	}
	
	// 상품등록
	@RequestMapping("productAdd.st")
	public String productAdd(HttpServletRequest req , Model model) {
		logger.info("[url ==> productAdd.st]");
		
		return "manager/stock/productAdd";
	}
	
	// 상품등록 처리
	@RequestMapping("productAddAction.st")
	public String productAddAction(MultipartHttpServletRequest req , Model model) {
		logger.info("[url ==> productAddAction.st]");
		
		service.productAddACtion(req, model);
		
		return "manager/stock/productAddAction";
	}
	
	// 상품 상세
	@RequestMapping("productDetail.st")
	public String productDetail(HttpServletRequest req , Model model) {
		logger.info("[url ==> productDetail.st]");
		
		service.productDetail(req, model);
		
		return "manager/stock/productDetail";
	}
	
	// 상품 수정
	@RequestMapping("productUpdateAction.st")
	public String productUpdateAction(MultipartHttpServletRequest req , Model model) {
		logger.info("[url ==> productUpdateAction.st]");
		
		service.productUpdateACtion(req, model);
		
		return "manager/stock/productUpdateAction";
	}
	
	// 제품삭제
	@RequestMapping("productDeleteAction.st")
	public String productDeleteAction(HttpServletRequest req , Model model) {
		logger.info("[url ==> productDeleteAction.st]");
		
		service.productDeleteACtion(req, model);
		
		return "manager/stock/productDeleteAction";
	}
	
	// -------------------- [주문관리] -----------------------			
	// 주문목록
	@RequestMapping("productOrderList.st")
	public String productOrderList(HttpServletRequest req , Model model) {
		logger.info("[url ==> productOrderList.st]");
		
		service.managerOrderList(req, model);
		
		return "manager/order/productOrderList";
	}
	
	// 주문 취소 버튼
	@RequestMapping("productOrderCancel.st")
	public String productOrderCancel(HttpServletRequest req , Model model) {
		logger.info("[url ==> productOrderCancel.st]");
		
		service.managerOrderCancel(req, model);
		
		return "redirect:/productOrderList.st";
	}
	
	// 주문 승인 버튼
	@RequestMapping("productOrderApproval.st")
	public String productOrderApproval(HttpServletRequest req , Model model) {
		logger.info("[url ==> productOrderApproval.st]");
		
		service.managerOrderApproval(req, model);
		
		return "redirect:/productOrderList.st";
	}
	
	// 주문 승인 목록
	@RequestMapping("productOrderApprovalList.st")
	public String productOrderApprovalList(HttpServletRequest req , Model model) {
		logger.info("[url ==> productOrderApprovalList.st]");
		
		service.managerOrderApprovalList(req, model);
		
		return "manager/order/productOrderApproval";
	}
	
	// ---------- [환불] --------- 
	// 환불 요청 목록
	@RequestMapping("refundRequestList.st")
	public String refundRequestList(HttpServletRequest req , Model model) {
		logger.info("[url ==> refundRequestList.st]");
		
		service.refundRequsetList(req, model);
		
		return "manager/refund/refundRequestList";
	}
	
	// 환불 승인 목록
	@RequestMapping("refundApprovalList.st")
	public String refundApprovalList(HttpServletRequest req , Model model) {
		logger.info("[url ==> refundApprovalList.st]");
		
		service.refundApprovalList(req, model);
		
		return "manager/refund/refundApprovalList";
	}
	
	// 환불 취소 목록
	@RequestMapping("refundCancelList.st")
	public String refundCancelList(HttpServletRequest req , Model model) {
		logger.info("[url ==> refundCancelList.st]");
		
		service.refundCancelList(req, model);
		
		return "manager/refund/refundCancelList";
	}
	
	// 환불 취소 버튼
	@RequestMapping("refundCancel.st")
	public String refundCancel(HttpServletRequest req , Model model) {
		logger.info("[url ==> refundCancel.st]");
		
		service.refundCancel(req, model);
		
		return "redirect:/refundRequestList.st";
	}
	
	// 환불 승인 버튼
	@RequestMapping("refundApproval.st")
	public String refundApproval(HttpServletRequest req , Model model) {
		logger.info("[url ==> refundApproval.st]");
		
		service.refundApproval(req, model);
		
		return "redirect:/refundRequestList.st";
	}
	
	// 결산
	@RequestMapping("totalMoney.st")
	public String totalMoney(HttpServletRequest req , Model model) {
		logger.info("[url ==> totalMoney.st]");
		
		service.totalMoney(req, model);
		
		return "manager/totalMoney/totalMoney";
	}
	
}
