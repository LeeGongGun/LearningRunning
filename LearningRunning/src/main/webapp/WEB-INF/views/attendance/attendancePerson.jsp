<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" media="all" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js" type="text/javascript"></script>
<script>
$(function() {
  $( "#from, #to" ).datepicker({
    dateFormat: 'yymmdd',
    prevText: '이전 달',
    nextText: '다음 달',
    monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
    monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
    dayNames: ['일','월','화','수','목','금','토'],
    dayNamesShort: ['일','월','화','수','목','금','토'],
    dayNamesMin: ['일','월','화','수','목','금','토'],
    showMonthAfterYear: true,
    yearSuffix: '년'
  });
});
function pageGo(page) {
	document.getElementById("page").value = page;
	document.getElementById("frm").submit();
}
function goBoard(){
	location.href = "<c:url value='/boardList' />";
}
</script>

<style>
.periodSerch{

margin : 200px;

}
.personDetail{
	width : 40px auto;
}
</style>
<title>학생 페이지</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/nav.jsp"%>
	<div class="btn btn-primary" id="insert">
	<form:form commandName="PersonSearch" id="frm">
		<p>
			<label>from:
			<input type="text" name="from" id="from" value="${from }" />
			</label>
			~ <label>to: 
			<input type="text" name="to" id="to" value="${to }" />
			</label>
			<input type="submit" value="기간별 조회">
			<input type="button" value="지각 조회" onclick="goBoard();">
			<input type="button" value="외출 조회" onclick="goBoard();">			
		</p>
	</form:form>
	</div>
	
	
	<div class="main">
		<div class="main-div">
			<table class="table table-striped table-bordered" cellspacing="0" width="100%">
					<tr>
						<td>${student }님 환영합니다.</td>
						<td>수강 과목 : ${stuSubject }</td>
						<td>현재까지 출석률 : ${attendRate }% </td>
						<td>Email : ${studentEmail }</td>
					</tr>
					<table class="table table-striped table-bordered" cellspacing="0" width="100%">
						<tr>
							<td>수강날짜</td>
							<td>상세설명</td>
							<td>수강생 이름</td>
							<td>출 결</td>
						</tr>
						<c:forEach var="person" items="${attendancePersonCommand}">
					
						<tr>
							<td>${person.start_time}</td>
							<td>${person.subject_comment}</td>
							<td>${person.m_name}</td>
							<td>${person.attend_status}</td>
						</tr>
					</c:forEach>
					<tr>
						<td>수강 일수 : </td>
				</table>
			</table>
		</div>
	</div>
</body>
</html>