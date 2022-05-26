<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>accessDenied.jsp</title>
<link rel="stylesheet" href="${path}/resources/css/commonCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/commonCss/footer.css">
<%-- <link rel="stylesheet" href="${path}/resources/css/customerCss/table.css"> --%>

<!-- 1. kit.fontawesome.com에서 인증 2. 이미지 사용가능-->
<script src="https://kit.fontawesome.com/980ab78713.js" crossorigin="anonymous"></script>

<!-- 3-3. 자바스크립트 이벤트 추가 : 햄버거버튼을 클릭하면 아래쪽으로 메뉴가 나오도록 main.js에서 추가 -->
<script src="${path}/resources/js/customerJS/main.js"></script>

</head>
<body>
<div class="wrap">
   <!-- header 시작 -->
   <%@ include file="/WEB-INF/views/common/header.jsp" %>   
   <!-- header 끝 -->
   <!-- 메인 컨텐츠 시작 : 화면 크기 조정을 위한 container -->
   <div id="container">
      <!-- 메인 컨텐츠 시작 : 화면 크기 조정을 위한 contents - 화면 max-width: 1240, min-width: 1240 -->
      <div id="contents" style="text-align: center">
      <!-- 상단 중앙 시작 -->
         <h1>관리자 페이지</h1>
         <br>
         
         <!-- UserDeniedHandler에서 errMsg 전달 -->
         <p style="font-size: 18px">${errMsg}</p> <br>
         <c:if test="${sessionScope.customerID !=null}">
            <button value="이동하기" class="inputButton" style="width:200px; font-size:24px; text-align: center;"
               onclick="window.location='logout.do'">이동하기</button>
         </c:if>
         <c:if test="${sessionScope.customerID == null}">
            <button value="이동하기" class="inputButton" style="width:200px; font-size:24px; text-align: center;"
               onclick="window.location='login.do'">이동하기</button>
         </c:if>
      </div>
   </div>
</div>


   <!-- footer 시작 -->
   <%@ include file="/WEB-INF/views/common/footer.jsp" %>
   <!-- footer 끝 -->
</body>
</html>
