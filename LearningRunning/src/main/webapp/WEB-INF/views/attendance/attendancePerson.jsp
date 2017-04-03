<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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

function periodSrch(){
	location.href = "<c:url value='/attendance/person/${memberId}' />";
}

function beLate(){
	location.href = "<c:url value='/attendance/beLate/${memberId}' />";
}
function goOut(){
	location.href = "<c:url value='/attendance/goOut/${memberId}' />";
}
function leaveEarly(){
	location.href = "<c:url value='/attendance/leaveEarly/${memberId}' />";
}
function absent(){
	location.href = "<c:url value='/attendance/absent/${memberId}' />";
}

</script>

<style>
.date{
	width : 100%;
	margin-left : 200px;
	margin-top : 100px;
	margin-bottom : -70px;
}

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
	<div class = "date">
	<form:form commandName="PersonSearch" id="frm">
		<p>
			<label>from:
			<input type="text" name="from" id="from" value="${from }" />
			</label>
			~ <label>to: 
			<input type="text" name="to" id="to" value="${to }" />
			</label>
			<input type="submit" value="기간별 조회" onclick = "periodSrch();">
		</p>
	</form:form>
	
	</div>
	
	<div class="main">
		<div class="main-div">
			<table class="table table-striped table-bordered" cellspacing="0" width="100%">
					<tr>
						<th>${authMember.m_name }님 환영합니다.</th>
						<th colspan="3">수강 과목 : ${ClassAttend.class_name }</th>
						<th>Email : ${authMember.m_email }</th>
					</tr>
					<tr>
						<th>출석 : ${ClassAttend.attendings }</th>
						<th>결석 : ${ClassAttend.noAttendings }</th>
						<th>외출 : ${ClassAttend.outings }</th>
						<th>지각 : ${ClassAttend.lateings }</th>
						<th>조퇴 : ${ClassAttend.outComings }</th>
					</tr>
					<table class="table table-striped table-bordered" cellspacing="0" width="100%" id="personAttendTable">
						<thaed>
						<tr>
							<th>수강날짜</th>
							<th>상세설명</th>
							<th>수강생 이름</th>
							<th>출 결</th>
						</tr>
						</thaed>
						<tbody>
						</tbody>
					<tr>
				</table>
			</table>
		</div>
	</div>
</body>
</html>