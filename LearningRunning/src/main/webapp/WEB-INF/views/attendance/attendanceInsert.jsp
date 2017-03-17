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
<script type="text/javascript">
$(function(){
	$(".search-table").on("change","#allCheck",function(){
		var allChecked = $(this).is(":checked");
		$(".attendanceCheck").prop("checked", allChecked);
	});
	function setNow(){
		d = new Date;
		now = d.getHours() +":"+ d.getMinutes();
		$("#start,#end,#stop,#restart").val(now);
	}
	setNow();
});
</script>
<style type="text/css">
.m_no{width:100px;}
</style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<div id="datepicker-div"></div>
<div class="main"><div class="main-div">
	<h3 class="sub-title">출결 수정</h3>
<div class="search-table">
	<form:form class="form-inline" enctype="multipart/form-data">
		<table  class="table table-striped table-bordered" cellspacing="0" width="100%">
			<tr>

				<th class="m_no">no
					<label class="switch">
					  <input type="checkbox" id="allCheck"/><div class="slider round"></div>
					</label>
				</th>
				<th>학생명</th>
				<th>입실<input type="time" id="start" class="form-control"  /></th>
				<th>조퇴,외출<input type="time" id="stop" class="form-control" /></th>
				<th>외출종료<input type="time" id="restart" class="form-control"  /></th>
				<th>퇴실<input type="time" id="end" class="form-control"  /></th>
			</tr>
			<c:forEach var="attendance" items="${aList}">
				<tr>
					<td>${attendance.m_id}
						<label class="switch">
							<input type="checkbox" class="attendanceCheck" name="attendanceCheck[]" value="${attendance.m_id}"/><div class="slider round"></div>
						</label>
					</td>
					<td>${attendance.m_name}</td>
					<td>
						${(empty attendance.start_time)?"입실전":attendance.start_time}
					</td>
					<td>
						${(empty attendance.stop_time)?"":attendance.start_time}
					</td>
					<td>
						${(empty attendance.restart_time)?"":attendance.start_time}
					</td>
					<td>
						${(empty attendance.end_time)?"":attendance.start_time}
					</td>
				</tr>
			</c:forEach>
		</table>
  	</form:form>
</div>	

</div></div>
<%@ include file="/WEB-INF/views/include/foot.jsp" %>
</body>
</html>