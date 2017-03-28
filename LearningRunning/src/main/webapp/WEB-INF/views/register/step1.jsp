<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<h2>약관</h2>
	<p>약관 내용</p>
	<form action="step2" method="post">
		<label>
			<input type="checkbox" name="agree" value="true">약관 동의
		</label>
		<input type="submit" value="다음 단계">
	</form>
</div></div>
<%@ include file="/WEB-INF/views/include/foot.jsp" %>
</body>
</html>