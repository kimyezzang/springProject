<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ include file="/WEB-INF/views/common/setting.jsp" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="${path}/resources/css/commonCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/commonCss/login.css">
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
<script src="${path}/resources/js/jsTitle.js"></script>
<script src="https://kit.fontawesome.com/d46bcbd556.js" crossorigin="anonymous"></script>
<!-- https://fontawesome.com/ -->
</head>
<body>
<div id="wrap">
<!--헤더 -->
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<!-- UserLoginFailureHandler 에서 msg 넘김 -->
   <c:if test="${errorMsg != null}">
      <script type="text/javascript">
         alert("${errorMsg}");
      </script>
   </c:if>
<!--헤더 끝-->

   <!-- 메인 컨텐츠 시작 -->
<div id="container2">
   
  <form name="loginform" action="${path}/login_check.do" method="post" onsubmit="return loginCheck();"> 
  <%-- <form action="${path}/loginAction.do" method="post" > --%>
  <!--  method="post" 반드시 추가 -->
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
   <fieldset>
   	<legend><b>로그인</b></legend>
   	<table class="login">
   		<tr>
   			<td><hr></td>
   		</tr>
   		<tr><td><input type="text" name="id"id="id" size="25" placeholder="아이디를 입력해 주세요" autofocus required></td></tr>
   		<tr><td><input type="password" name="password" id="password" size="25" placeholder="비밀번호를 입력해 주세요" required></td></tr>
   		<tr><td><a href="#"><p>비밀번호 찾기</p></a></td></tr>
   		<tr>
   			<td><hr></td>
   		</tr>
   		<tr><td><input type="submit" value="로그인"></td></tr>
   	</table>
   </fieldset>
  </form>   

</div>

   <!-- 메인 컨텐츠 끝 -->
<!-- 푸터시작 -->
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
<!-- 푸터 끝 -->
</div>
</body>
</html>