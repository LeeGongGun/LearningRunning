<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String fileDir = request.getRealPath("/resources/uploads/"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<title>입력</title>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<div class="main"><div class="main-div">
	<h3 class="sub-title">글쓰기. </h3>
	<form:form class="form-horizontal" enctype="multipart/form-data">
  	</form:form>

</div></div>
<%@ include file="/WEB-INF/views/include/foot.jsp" %>
</body>
</html>