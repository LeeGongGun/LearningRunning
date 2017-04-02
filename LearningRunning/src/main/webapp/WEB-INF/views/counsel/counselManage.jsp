<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String fileDir = request.getRealPath("/resources/uploads/");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css"
	type="text/css" media="all" />
	
<script>	
	function goWrite() {
		location.href ="${pageContext.request.contextPath}/teacher/counsel/counselWrite";
	}
</script>

<style>
.fromBox {
	width: 200px;
}

.toBox {
	width: 200px;
}

.teacher {
	width: 200px;
}

.student {
	width: 200px;
}

.counselDate {
	width: 200px;
}
.counselInputBox {
	width:200px;
}
</style>

<title>입력</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/nav.jsp"%>
	<div class="main">
		<div class="main-div">
			<h3 class="sub-title">상담 내역</h3>
			<div class="search-table">
				<input type="date" class="fromBox" id="from_date"
					name="auth_end_date" placeholder="종료일"> 
				<input type="date"
					class="toBox" id="to_date" name="auth_end_date" placeholder="종료일">
					
				<button type="button" class="btn btn-primary" id="insert" 
				onclick="goWrite();">검색</button>
				<button type="button" class="btn btn-primary" id="insert" 
				onclick="goWrite();">입력</button>
				
				<table class="table table-striped table-bordered" cellspacing="0"
					width="100%">
					<tr>
						<th>과정명</th>
						<th>과정 유형</th>
						<th>상담 내역</th>
						<th class="teacher">상담 선생님</th>
						<th class="student">상담 학생</th>
						<th class="counselDate">상담일</th>
					</tr>
					<c:forEach var="classes" items="${counselManager}">
						<tr>
							<td>${classes.CLASS_NAME}</td>
							<td></td>
							<td></td>
							<td></td>
							<td>${classes.manager}</td>
							<td></td>
						</tr>
					</c:forEach>
				</table>
			</div>

		</div>
	</div>
	<%@ include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>