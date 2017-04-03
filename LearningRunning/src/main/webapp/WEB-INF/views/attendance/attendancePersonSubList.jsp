<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String fileDir = request.getRealPath("/resources/uploads/");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<title>입력</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/nav.jsp"%>
	<div class="main">
		<div class="main-div">
			<h3 class="sub-title">출석반선택</h3>
			<div class="search-table">
				<table class="table table-striped table-bordered" cellspacing="0"
					width="100%">
					<tr>

						<th>번호</th>
						<th>과정명</th>
						<th>상태</th>
						<th>출석</th>
						<th>결석</th>
						<th>조퇴</th>
						<th>지각</th>
						<th>외출</th>
					</tr>
					<c:forEach var="ClassAttend" items="${ClassAttends}">
						<tr>
							<td>${ClassAttend.class_id}</td>
							<td><a
								href="<c:url value="/attendance/person/${ClassAttend.class_id}/${ClassAttend.m_id}"/>">${ClassAttend.class_name}</a></td>
							<td>${ClassAttend.class_state}</td>
							<td>${ClassAttend.attendings }</td>
							<td>${ClassAttend.noAttendings }</td>
							<td>${ClassAttend.outings }</td>
							<td>${ClassAttend.lateings }</td>
							<td>${ClassAttend.outComings }</td>
						</tr>
					</c:forEach>
				</table>
			</div>

		</div>
	</div>
	<%@ include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>