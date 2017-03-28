<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
	<h2>회원 정보입력 ${errMsg }</h2>
	<form:form action="${pageContext.request.contextPath }/register/step3" commandName="registerRequest">
		<label for="email">
			email : <form:input path="email"/>
			<form:errors path="email"/>
		</label><br/>
		<label for="name">
			name : <form:input path="name"/>
			<form:errors path="name"/>
		</label><br/>
		<label for="password">
			password : <form:password path="password"/>
			<form:errors path="password"/>
		</label><br/>
		<label for="confirmPassword">
			password : 확인 <form:password path="confirmPassword"/>
			<form:errors path="confirmPassword"/>
		</label><br/>
		<input type="submit" value="가입완료">
		
	</form:form>
</div></div>
<%@ include file="/WEB-INF/views/include/foot.jsp" %>
	
</body>
</html>