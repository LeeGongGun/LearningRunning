<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<title>회원 가입</title>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<div class="main"><div class="main-div">
	<p>${registerRequest.name }회원 가입을 완료하였습니다.</p>
	<p><a href='<c:url value="/main"/>'>[첫화면 이동]</a></p>
</div></div>
<%@ include file="/WEB-INF/views/include/foot.jsp" %>

</body>
</html>