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
	$(function() {
		$("#btnInsert").click(insertAuth);

	});
</script>
<style>
.fromBox {
	width : 150px;
}
.toBox {
	width : 150px;
}
</style>

<title>입력</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/nav.jsp"%>
	<div class="main">
		<div class="main-div">
			<h3 class="sub-title">상담 관리</h3>
			<div class="search-table">
				<input type="date" class="fromBox"
					id="from_date" name="auth_end_date" placeholder="종료일">
				<input type="date" class="toBox"
					id="to_date" name="auth_end_date" placeholder="종료일">
				<table class="table table-striped table-bordered" cellspacing="0"
					width="100%">
					<tr>

						<th>번호</th>
						<th>과정명</th>
						<th>인원수</th>
						<th>상태</th>
					</tr>
					<c:forEach var="classes" items="${list}">
						<tr>
							<td>${classes.class_id}</td>
							<td><a
								href="<c:url value="/attendance/insert/${classes.class_id}"/>">${classes.class_name}</a></td>
							<td>${classes.class_comment}</td>
							<td>${classes.class_state}</td>
						</tr>
					</c:forEach>
				</table>
			</div>

		</div>
	</div>
	<%@ include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>