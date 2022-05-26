package spring.mvc.pj_mine.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import page.CustomerOrderPaging;
import page.Paging;
import page.ProductPaging;
import spring.mvc.pj_mine.dao.ProductDAO;
import spring.mvc.pj_mine.dto.BasketDTO;
import spring.mvc.pj_mine.dto.OrderDTO;
import spring.mvc.pj_mine.dto.ProductDTO;
import spring.mvc.pj_mine.dto.TotalMoneyDTO;


@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductDAO dao;
	
	// 상품추가 처리
	@Override
	public void productAddACtion(MultipartHttpServletRequest req, Model model) {
		System.out.println("서비스 - productAddACtion");
		
		// 추가S
		MultipartFile file = req.getFile("pdImg");
		System.out.println("file : " + file);
		
		// 저장경로
		String saveDir = req.getSession().getServletContext().getRealPath("/resources/upload/");
		System.out.println("saveDir : " + saveDir);
		
		// 저장경로(jsp의 IMG_UPLOAD_DIR)
		String realDir = "D:\\Dev105\\springWorspace\\spring_pj_marketyezzang\\src\\main\\webapp\\resources\\upload\\";
		System.out.println("realDir = " + realDir);
		
		try {
			file.transferTo(new File(saveDir + file.getOriginalFilename()));
			
			FileInputStream fis = new FileInputStream(saveDir + file.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(realDir + file.getOriginalFilename());
			
			int data = 0;
			
			while((data = fis.read()) != -1) {
				fos.write(data);
			}
			fis.close();
			fos.close();
			// 추가E --------------------------------
			//  화면으로부터 입력받은 값을 dto에 받는다.
			ProductDTO dto = new ProductDTO();
			dto.setBrand(req.getParameter("brand"));
			dto.setPdName(req.getParameter("pdName"));
			// 플젝명/upload 해당 경로
			//  ImageUploaderHandler 클래스에서 setAttribute()로 넘겼으므로
			
			// 수정S ----------------------------------
			String p_img1 = "/mvc/resources/upload/" + file.getOriginalFilename();	// 컨텍스트명/경로
			System.out.println("p_img1 : " + p_img1);
			// 수정E ----------------------------------
			
			dto.setPdImg(p_img1);
			
			dto.setCategory(req.getParameter("category"));
			dto.setContent(req.getParameter("content"));
			dto.setPrice(Integer.parseInt(req.getParameter("price")));
			dto.setQuantity(Integer.parseInt(req.getParameter("quantity")));
			dto.setStatus(req.getParameter("status"));
			
			// 상품정보 인서트
			 int updateCnt = dao.productInsert(dto);
			 System.out.println("[ProductServiceImpl 5-상품insert] updateCnt : " + updateCnt);	// 정상 : 1
			// upload폴더를 새로고침하면 등록한 이미지가 들어온다.
			
			model.addAttribute("insertCnt", updateCnt);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	// 상품 수정 처리
	@Override
	public void productUpdateACtion(MultipartHttpServletRequest req, Model model) {
		// 화면으로부터 입력받은 값(비밀번호), hidden값(num)을 받는다.
		// productDetail.jsp에서 hidden으로 넘어온 값을 받는다.
		
		String hiddenPdImg = req.getParameter("hiddenPdImg");
		int hiddenPdNo = Integer.parseInt(req.getParameter("hiddenPdNo"));	// pk
		int pageNum = Integer.parseInt(req.getParameter("hiddenPageNum"));
		
		// 추가S
		MultipartFile file = req.getFile("pdImg");
		System.out.println("file : " + file);
		
		// 저장경로
		String saveDir = req.getSession().getServletContext().getRealPath("/resources/upload/");
		System.out.println("saveDir : " + saveDir);
		
		// 아래 경로에서 파일열기 추가할 이미지 선택
		// 아래 경로 upload 폴더 우클릭 > resources/location 복사해서 가져옴 
		// 저장경로(jsp의 IMG_UPLOAD_DIR)
		String realDir = "D:\\Dev105\\springWorspace\\spring_pj_marketyezzang\\src\\main\\webapp\\resources\\upload\\";
		System.out.println("realDir = " + realDir);
		
		String p_img1 = "";
		// 이미지를 수정하고자 할경우 선택한 이미지를 p_img에 등록
		if(file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
			try {
				file.transferTo(new File(saveDir + file.getOriginalFilename()));
				
				FileInputStream fis = new FileInputStream(saveDir + file.getOriginalFilename());
				FileOutputStream fos = new FileOutputStream(realDir + file.getOriginalFilename());
				
				int data = 0;
				
				while((data = fis.read()) != -1) {
					fos.write(data);
				}
				fis.close();
				fos.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
			p_img1 = "/mvc/resources/upload/" + file.getOriginalFilename();	// 컨텍스트명/경로
			System.out.println("새 이미지 p_img1 : " + p_img1);
		} else {
			// 기존 이미지 사용(이미지 수정 안할 경우)
			p_img1 = hiddenPdImg;
			System.out.println("기존 이미지 : " + p_img1);
		}
		// 추가E --------------------------------
		
		// 화면값 받아오기
		ProductDTO dto = new ProductDTO();
		dto.setPdNo(hiddenPdNo);	// 누락주의
		dto.setPdName(req.getParameter("pdName"));
		dto.setPdImg(p_img1);
		dto.setCategory(req.getParameter("category"));
		dto.setBrand(req.getParameter("brand"));
		dto.setContent(req.getParameter("content"));
		dto.setPrice(Integer.parseInt(req.getParameter("price")));
		dto.setQuantity(Integer.parseInt(req.getParameter("quantity")));
		dto.setStatus(req.getParameter("status"));
		
		// 상품정보 수정
		int updateCnt = dao.productUpdate(dto);
		System.out.println("productUpdateACtion - updateCnt : " + updateCnt);
		
		//  jsp로 처리결과 전달
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("updateCnt", updateCnt);
		
	}

	// 상품 삭제 처리
	@Override
	public void productDeleteACtion(HttpServletRequest req, Model model) {
		// 화면으로부터 입력받은 값을 받는다.
		int pdNo = Integer.parseInt(req.getParameter("pdNo"));
		
		// 상품삭제
		int updateCnt = dao.productDelete(pdNo);
		// jsp로 처리결과 전달
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		model.addAttribute("updateCnt", updateCnt);
		model.addAttribute("pageNum", pageNum);
	}

	// 상품 리스트
	@Override
	public void productList(HttpServletRequest req, Model model) {
		System.out.println("서비스 - productList");
		// 화면으로부터 입력받은 값을받는다.
		String pageNum = req.getParameter("pageNum");
		
		Paging paging = new Paging(pageNum);
		
		// product 카운트
		int total = dao.productCnt();
		paging.setTotalCount(total);
		
		System.out.println("total : " + total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		List<ProductDTO> list = dao.productList(map); 
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		// jsp로 처리결과 전달(request나 session으로 결과 전달)
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
	}
	
	
	// 상세페이지
	@Override
	public void productDetail(HttpServletRequest req, Model model) {
		System.out.println("서비스 - productDetail");
		// 화면으로부터 입력받은 값을 받는다.
		int pdNo = Integer.parseInt(req.getParameter("pdNo"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		// 상품상세 정보를 가져와서 DTO로 리턴
		ProductDTO dto = dao.getProductDetail(pdNo);
		System.out.println("ProductDTO : " + dto);
		
		// jsp로 처리결과 전달(request나 session으로 결과 전달)
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("dto", dto);
		
	}
	
	//---------------[고객 상품]---------------------
	// 고객 상품 리스트
	// 상의 리스트
	@Override
	public void customerProductList1(HttpServletRequest req, Model model) {
		System.out.println("서비스 -  상의 리스트");
		
		String pageNum = req.getParameter("pageNum");
		
		ProductPaging paging = new ProductPaging(pageNum);
		
		String strCategoryCnt = "상의";
		int total = dao.customerProductCnt1(strCategoryCnt);
		paging.setTotalCount(total);
		
		System.out.println("total : " + total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("strCategory", "상의");
		
		List<ProductDTO> list = dao.customerProductList1(map);
		
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
	}

	// 하의 리스트
	@Override
	public void customerProductList2(HttpServletRequest req, Model model) {
		System.out.println("서비스 -  하의 리스트");
		
		String pageNum = req.getParameter("pageNum");
		
		ProductPaging paging = new ProductPaging(pageNum);
		
		String strCategory = "하의";
		int total = dao.customerProductCnt1(strCategory);
		paging.setTotalCount(total);
		
		System.out.println("total : " + total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("strCategory", "하의");
		
		List<ProductDTO> list = dao.customerProductList1(map);
		
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
	}

	// 신발 리스트
	@Override
	public void customerProductList3(HttpServletRequest req, Model model) {
		System.out.println("서비스 -  신발 리스트");
		
		String pageNum = req.getParameter("pageNum");
		
		ProductPaging paging = new ProductPaging(pageNum);
		
		String strCategory = "신발";
		int total = dao.customerProductCnt1(strCategory);
		paging.setTotalCount(total);
		
		System.out.println("total : " + total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("strCategory", "신발");
		
		List<ProductDTO> list = dao.customerProductList1(map);
		
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
	}

	// 신상품 리스트
	@Override
	public void customerNewProductList(HttpServletRequest req, Model model) {
		System.out.println("서비스 -  신상품 리스트");
		String pageNum = req.getParameter("pageNum");
		
		ProductPaging paging = new ProductPaging(pageNum);
		
		int total = dao.customerNewProductCnt();
		paging.setTotalCount(total);
		
		System.out.println("total : " + total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		List<ProductDTO> list = dao.customerNewProductList(map);
		
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
	}

	

	//---------------[장바구니]---------------------
	// 장바구니 추가 처리
	@Override
	public void basketAddACtion(HttpServletRequest req, Model model) {
		System.out.println("서비스 - basketAddACtion");
		
		// 화면으로부터 입력받은 값을 dto에 받는다.
		BasketDTO dto = new BasketDTO();
		dto.setPdNo(Integer.parseInt(req.getParameter("hiddenPdNo")));
		dto.setPdName(req.getParameter("hiddenPdName"));
		dto.setPdImg(req.getParameter("hiddenPdImg"));
		dto.setBrand(req.getParameter("hiddenPdBrand"));
		dto.setPrice(Integer.parseInt(req.getParameter("totalPrice")));
		dto.setQuantity(Integer.parseInt(req.getParameter("buttonCount")));
		dto.setId((String) req.getSession().getAttribute("customerID"));
		
		System.out.println("dto : "+ dto);
		
		// 상품정보 인서트
		int updateCnt = dao.basketInsert(dto);
		System.out.println("updateCnt : " + updateCnt);	// 정상 : 1
		// upload폴더를 새로고침하면 등록한 이미지가 들어온다.
		
		// jsp로 처리결과 전달(request나 session으로 결과 전달)
		model.addAttribute("insertCnt", updateCnt);
		
	}

	// 장바구니 상세페이지
	@Override
	public void basketDetail(HttpServletRequest req, Model model) {
		System.out.println("서비스 - basketDetail");
		String id = ((String) req.getSession().getAttribute("customerID"));
		
		// 상품정보 인서트
		List <BasketDTO> list = dao.basketList(id);
		System.out.println("list : " + list);	// 정상 : 1
		// upload폴더를 새로고침하면 등록한 이미지가 들어온다.
		
		String address = dao.basketAddress(id);
		
		// jsp로 처리결과 전달(request나 session으로 결과 전달)
		model.addAttribute("list", list);
		model.addAttribute("address", address);
		
		System.out.println("장바구니 address " + address);
		
	}

	// 장바구니 상품 삭제
	@Override
	public void basketDelete(HttpServletRequest req, Model model) {
		System.out.println("서비스 - basketDelete");
		
		int bkNo = Integer.parseInt(req.getParameter("bkNo"));
		
		int updateCnt = dao.baksetDelete(bkNo);
		System.out.println("updateCnt : " + updateCnt);	// 정상 : 1
		
		model.addAttribute("insertCnt", updateCnt);
		
	}

	// 장바구니에서 구매
	@Override
	public void basketBuy(HttpServletRequest req, Model model) {
		System.out.println("서비스 - basketBuy");
		
		String list = req.getParameter("list");
		List<String> bkNoList = new ArrayList<String>();
		String[] arrList = list.split(",");
		
		for(int i=0; i<arrList.length; i++ ) {
			bkNoList.add(arrList[i]);
		}
		
		System.out.println("bkNoList : " + bkNoList);
		
		int cnt = dao.baksetGet(bkNoList);
		System.out.println("장바구니 구매 cnt : " + cnt);
		
	}

	// ------------ [바로구매] ---------------------
	@Override
	public void nowBuy(HttpServletRequest req, Model model) {
		System.out.println("서비스 - nowBuy");
		
		// 화면으로부터 입력받은 값을 dto에 받는다.
		BasketDTO dto = new BasketDTO();
		dto.setPdNo(Integer.parseInt(req.getParameter("hiddenPdNo")));
		dto.setPdName(req.getParameter("hiddenPdName"));
		dto.setPdImg(req.getParameter("hiddenPdImg"));
		dto.setBrand(req.getParameter("hiddenPdBrand"));
		dto.setPrice(Integer.parseInt(req.getParameter("totalPrice")));
		dto.setQuantity(Integer.parseInt(req.getParameter("buttonCount")));
		dto.setId((String) req.getSession().getAttribute("customerID"));
		
		System.out.println("dto : "+ dto);
		
		// 상품정보 인서트
		int updateCnt = dao.nowBuy(dto);
		System.out.println("updateCnt : " + updateCnt);	// 정상 : 1
		// upload폴더를 새로고침하면 등록한 이미지가 들어온다.
		
		// jsp로 처리결과 전달(request나 session으로 결과 전달)
		model.addAttribute("insertCnt", updateCnt);
		
	}

	//---------------[주문목록]---------------------
	// 고객 주문 리스트
	@Override
	public void customerOrderList(HttpServletRequest req, Model model) {
		System.out.println("서비스 - customerOrderList");
		
		String id = (String) req.getSession().getAttribute("customerID");
		
		String pageNum = req.getParameter("pageNum");
		
		CustomerOrderPaging paging = new CustomerOrderPaging(pageNum);
		
		// OrderList 카운트
		int total = dao.customerOrderCnt(id);
		paging.setTotalCount(total);
		
		System.out.println("total : " + total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("id",id);
		// 
		List<OrderDTO> list = dao.customerOrderList(map);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		// jsp로 처리결과 전달(request나 session으로 결과 전달)
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		
	}

	// 배송준비중 취소하기  > 주문목록에서 삭제
	@Override
	public void customerPreparedOrederDelete(HttpServletRequest req, Model model) {
		String orNo = req.getParameter("orNo");
		
		int deleteCnt = dao.customerPreparedOrederDelete(orNo);
		
		System.out.println("주문목록 배송준비중 취소하기 : " + deleteCnt);
		
	}

	// 배송완료 환불하기
	@Override
	public void customerOrederRefund(HttpServletRequest req, Model model) {
		String orNo = req.getParameter("orNo");
		
		int updateCnt = dao.customerOrederRefund(orNo);
		
		System.out.println("주문목록 배송완료 환불요청 : " + updateCnt);
		
	}

	// ------- [검색 처리] ----------
	@Override
	public void search(HttpServletRequest req, Model model) {
		System.out.println("service - search");
		
		String content = req.getParameter("searchContent");
		String pageNum = req.getParameter("pageNum");
		ProductPaging paging = new ProductPaging(pageNum);
		
		int total = dao.searchCnt(content);
		paging.setTotalCount(total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("content",content);
		List<ProductDTO> list = dao.search(map);
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		
	}

	// ---------------- [관리자 주문관리] ---------------------
	// 주문 목록
	@Override
	public void managerOrderList(HttpServletRequest req, Model model) {
		
		String pageNum = req.getParameter("pageNum");
		Paging paging = new Paging(pageNum);
		
		// OrderList 카운트
		int total = dao.managerOrderCnt();
		paging.setTotalCount(total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		// 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		List<OrderDTO> list = dao.managerOrderList(map);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
	}

	// 주문 승인 목록
	@Override
	public void managerOrderApprovalList(HttpServletRequest req, Model model) {
		System.out.println("service - 주문승인 목록");
		
		String pageNum = req.getParameter("pageNum");
		Paging paging = new Paging(pageNum);
		
		// OrderList 카운트
		int total = dao.managerOrderApprovalCnt();
		paging.setTotalCount(total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		// 
		List<OrderDTO> list = dao.managerOrderApprovalList(map);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		
	}

	// 주문 취소
	@Override
	public void managerOrderCancel(HttpServletRequest req, Model model) {
		String orNo = req.getParameter("orNo");
		
		int deleteCnt = dao.managerOrderCancel(orNo);
		
		System.out.println("주문 승인취소 : " + deleteCnt);
		
		
	}

	// 주문 승인
	@Override
	public void managerOrderApproval(HttpServletRequest req, Model model) {
		String orNo = req.getParameter("orNo");
		
		int updateCnt = dao.managerOrederApproval(orNo);
		
		System.out.println("주문 승인 : " + updateCnt);
		
	}

	// 환불 요청 목록
	@Override
	public void refundRequsetList(HttpServletRequest req, Model model) {
		System.out.println("service - 환불 요청 목록");
		
		String pageNum = req.getParameter("pageNum");
		Paging paging = new Paging(pageNum);
		
		// OrderList 카운트
		int total = dao.refundRequestCnt();
		paging.setTotalCount(total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		// 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		List<OrderDTO> list = dao.refundRequestList(map);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		
	}

	// 환불 승인
	@Override
	public void refundApproval(HttpServletRequest req, Model model) {
		String orNo = req.getParameter("orNo");
		
		int updateCnt = dao.refundApproval(orNo);
		
		System.out.println("주문 승인 : " + updateCnt);
		
	}

	// 환불 승인 목록
	@Override
	public void refundApprovalList(HttpServletRequest req, Model model) {
		System.out.println("service - 환불 승인 목록");
		
		String pageNum = req.getParameter("pageNum");
		Paging paging = new Paging(pageNum);
		
		// OrderList 카운트
		int total = dao.refundApprovalCnt();
		paging.setTotalCount(total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		// 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		List<OrderDTO> list = dao.refundApprovalList(map);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		
	}

	// 환불 취소
	@Override
	public void refundCancel(HttpServletRequest req, Model model) {
		String orNo = req.getParameter("orNo");
		
		int updateCnt = dao.refundCancel(orNo);
		
		System.out.println("주문 승인 : " + updateCnt);
	}

	// 환불 취소 목록
	@Override
	public void refundCancelList(HttpServletRequest req, Model model) {
		System.out.println("service - 환불 취소 목록");
		
		String pageNum = req.getParameter("pageNum");
		Paging paging = new Paging(pageNum);
		
		// OrderList 카운트
		int total = dao.refundCancelCnt();
		paging.setTotalCount(total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		// 
		List<OrderDTO> list = dao.refundCancelList(map);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
	}

	// 결산
	@Override
	public void totalMoney(HttpServletRequest req, Model model) {
		System.out.println("service - 결산");
		
		List<Integer> dto = dao.totalMoney();
		TotalMoneyDTO tDto = new TotalMoneyDTO();
		
		tDto.setCategory1Money(dto.get(0));
		tDto.setCategory2Money(dto.get(1));
		tDto.setCategory3Money(dto.get(2));
		tDto.setTotalMoney(dto.get(3));
		
		model.addAttribute("dto", tDto);
		
	}

	

}
