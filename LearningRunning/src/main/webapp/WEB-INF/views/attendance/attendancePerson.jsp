<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<style>
.personDetail{
	width : 40px auto;
}
</style>
<title>학생 페이지</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/nav.jsp"%>
	<div class="main">
		<div class="main-div">
			<table class="table table-striped table-bordered" cellspacing="0" width="100%">
					<tr>
						<td>${student }님 환영합니다.</td>
						<td> 수강 과목은 입니다.</td>
						<td>현재까지 출석률은 ${attendRate }% 입니다.</td>
					</tr>
					<table class="table table-striped table-bordered" cellspacing="0" width="100%">
						<tr>
							<td>수강과목</td>
							<td>수강날짜</td>
							<td>개 강 일</td>
							<td>종 강 일</td>
							<td>상세설명</td>
							<td>수강생 E-mail</td>
							<td>수강생 이름</td>
							<td>출 결</td>
						</tr>
						<c:forEach var="person" items="${attendancePersonCommand}">
					
						<tr>
							<td>${person.subject_name}</td>
							<td>${person.start_time}</td>
							<td>${person.subject_start}</td>
							<td>${person.subject_end}</td>
							<td>${person.subject_comment}</td>
							<td>${person.m_email}</td>
							<td>${person.m_name}</td>
							<td>${person.attend_status}</td>
						</tr>
					</c:forEach>
				</table>
			</table>
		</div>
	</div>
</body>
</html>