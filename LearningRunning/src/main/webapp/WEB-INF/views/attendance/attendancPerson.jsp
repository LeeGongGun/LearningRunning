<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/head.jsp" %>

<title>입력</title>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
	<div class = "personwelcome">
		<tr>
			<td>${attendancePersonCommand.m_id} 님 환영합니다. </td>
		</tr>
	</div>
	<div>
		<tr>
			<td>수강과목</td>
			<td>수강날짜</td>
			<td>개 강 일</td>
			<td>종 강 일</td>
			<td>상세설명</td>
			<td>수강생 E-mail</td>
			<td>수강생 명</td>
			<td>종 강 일</td>
			<td>출 석 률</td>
		</tr>
	</div>
	<div>
		<tr>
			<td>${attendancePersonCommand.subject_name}</td>
			<td>${attendancePersonCommand.ss_Date}</td>
			<td>${attendancePersonCommand.subject_start}</td>
			<td>${attendancePersonCommand.subject_end}</td>
			<td>${attendancePersonCommand.subject_comment}</td>
			<td>${attendancePersonCommand.m_email}</td>
			<td>${attendancePersonCommand.m_name}</td>
			<td>출 석 률</td>
		</tr>
	</div>
</body>
</html>