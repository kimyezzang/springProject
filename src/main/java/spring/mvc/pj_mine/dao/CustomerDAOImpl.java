package spring.mvc.pj_mine.dao;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.mvc.pj_mine.dto.BoardDTO;
import spring.mvc.pj_mine.dto.CustomerDTO;
import spring.mvc.pj_mine.dto.ProductDTO;
import spring.mvc.pj_mine.util.SettingValues;

@Repository
public class CustomerDAOImpl implements CustomerDAO{

	@Autowired
	SqlSession sqlSession;
	
	// ID 중복확인 처리
	@Override
	public int idCheck(String strId) {
		System.out.println("dao - ID 중복확인 처리");
		
		int selectCnt = sqlSession.selectOne("spring.mvc.pj_mine.dao.CustomerDAO.idCheck",strId);
		
		
		return selectCnt;
	}

	// email 중복확인 처리
	@Override
	public int emailCheck(String strEmail) {
		System.out.println("dao - email 중복확인 처리");
		
		int selectCnt = sqlSession.selectOne("spring.mvc.pj_mine.dao.CustomerDAO.emailCheck",strEmail);
		
		return selectCnt;
	}

	// 시큐리티 - 가입성공시 이메일인증을 위해 이메일 전송
	@Override
	public void sendEmail(String email, String key) {
		System.out.println("dao - sendEmail");
		
		final String username = SettingValues.ADMIN;      // 본인 이메일
	      final String password = SettingValues.PW;      // 본인 비밀번호
	      final String host = "smtp.gmail.com";
	      
	      // SMTP(메일 서버) 설정
	      
	      // 아래 import는 pom.xml에 mail API를 설정해야 가능
	      // import java.util.Properties;
	      Properties props = new Properties();         
	      props.put("mail.smtp.user", username);         // SMTP에서 사용할 메일 주소
	      props.put("mail.smtp.password", password);      // 비밀번호
	      props.put("mail.smtp.host", host);            // host 서버 : gmail로 설정
	      props.put("mail.smtp.port", "25");            // 25번 포트 사용
	      props.put("mail.debug", "true");            // 디버그 설정
	      props.put("mail.smtp.auth", "true");         // 인증 : true
	      props.put("mail.smtp.starttls.enable", "true");   // tls 사용 허용
	      props.put("mail.smtp.ssl.enable", "true");      // ssl 허용
	      props.put("mail.smtp.ssl.trust", host);         // ssl 신뢰 가능으로 설정(보안레벨)
	      // props.put("mail.smtp.starttls.required", "true");	// send 오류
	      // props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	      
	      
	      // propert값 설정
	      props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
	      props.setProperty("mail.smtp.socketFactory.fallback", "false");
	      props.setProperty("mail.smtp.port", "465");
	      props.setProperty("mail.smtp.socketFactory.port", "465");
	      
	      // import javax.mail.Session;
	      // import javax.mail.Authenticator
	      // import javax.mail.PasswordAuthentication
	      Session session = Session.getInstance(props, new Authenticator() {
	         protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	         }
	      });
	      
	      // import javax.mail.Message
	      // import javax.mail.internet.MimeMessage;
	      // import javax.mail.internet.InternetAddress;
	      // import javax.mail.Transport
	      
	      try {
	         Message message = new MimeMessage(session);
	         message.setFrom(new InternetAddress("admin@CosmoJspPJ.com"));
	         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
	         
	         // localhost => 본인 IP => 원격발표시 IP 수정
	         // 링크를 클릭해서 "이메일 인증 성공" => enabled를 1로 update함
	         String content ="회원가입을 축하드립니다. 링크를 눌러 회원가입을 완료하세요."
	                     + "<a href='http://localhost/mvc/emailChk.do?key=" + key + "'>링크</a>";
	         message.setSubject("회원가입 인증 메일");
	         message.setContent(content, "text/html; charset=utf-8");
	         
	         System.out.println("send");
	         
	         // send 오류시 아래 2줄 추가
	         
	         Transport.send(message);
	         System.out.println("<<<< Email SEND >>>>");
	      } catch(Exception e) {
	         e.printStackTrace();
	      }      
	}
	
	// 시큐리티
	@Override
	public int selectKey(String key) {
		int selectCnt = sqlSession.selectOne("spring.mvc.pj_mine.dao.CustomerDAO.selectKey", key);
		return selectCnt;
	}

	// 시큐리티
	@Override
	public int updateGrade(String key) {
		int selectCnt = sqlSession.update("spring.mvc.pj_mine.dao.CustomerDAO.updateGrade", key);
		return selectCnt;
	}

	// 회원가입 처리
	@Override
	public int insertCustomer(CustomerDTO dto) {
		System.out.println("dao - 회원가입처리");
		
		int insertCnt = sqlSession.insert("spring.mvc.pj_mine.dao.CustomerDAO.insertCustomer", dto);
		return insertCnt;
	}
	
	// --------- [회원 정보 수정 , 탈퇴,  주문내역] ----------
	// 회원정보 인증 및 탈퇴 처리
	@Override
	public int deleteCustomer(String strId) {
		System.out.println("dao - 회원정보 인증 및 탈퇴 처리");
		
		int insertCnt = sqlSession.delete("spring.mvc.pj_mine.dao.CustomerDAO.deleteCustomer", strId);
		return insertCnt;
	}

	// 회원정보 인증 및 상세페이지
	@Override
	public CustomerDTO getCustomerDetail(String strId) {
		System.out.println("dao - 회원정보 인증 및 상세페이지");
		CustomerDTO dto = sqlSession.selectOne("spring.mvc.pj_mine.dao.CustomerDAO.selectCustomer", strId);
		return dto ;
	}

	// 회원정보 수정 처리
	@Override
	public int updateCustomer(CustomerDTO dto) {
		System.out.println("dao - 회원정보 수정 처리");
		
		int insertCnt = sqlSession.update("spring.mvc.pj_mine.dao.CustomerDAO.updateCustomer", dto);
		return insertCnt;
	}

}
