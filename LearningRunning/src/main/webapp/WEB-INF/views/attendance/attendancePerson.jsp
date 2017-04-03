<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<% 
String fileDir = request.getRealPath("/resources/uploads/");
String rootPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<style>
</style>
<title>학생 페이지</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/nav.jsp"%>
	<div class="main">
		<div class="main-div">
			<form:form commandName="command" id="searchFrm">
				<p>
					<label>
					<input type="text" name="from" id="from" class="datetimepicker form-control" placeholder="~부터"/>
					</label>
					~ <label> 
					<input type="text" name="to" id="to" class="datetimepicker form-control" placeholder="~까지"/>
					</label>
					<input type="button" value="기간별 조회"  class="btn btn-default" id="searchBtn">
				</p>
			</form:form>

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
<%@ include file="/WEB-INF/views/include/foot.jsp" %>
<script>
$(function() {
	from = new Date();
	from.setMonth(from.getMonth()-1);
	$("#from")
	.datetimepicker({
		language:  'ko',
		container: "#datepicker-div",
		format: "yyyy-mm-dd",
		todayHighlight:  true,
        autoclose: true,
        useCurrent: false,
	})
	.datetimepicker("update", from);
	$("#to")
	.datetimepicker({
		language:  'ko',
		container: "#datepicker-div",
		format: "yyyy-mm-dd",
		todayHighlight:  true,
        autoclose: true,
        useCurrent: false,
	})
	.datetimepicker("update", new Date());
	function getAttendList(){
		$.ajax({
	        type:'post',
	        data: $("#searchFrm").serialize(),
	        success: function(json){
	        	conTag = "";
				$(json.data).each(function(i,item){
						conTag +="<tr>";
						conTag +="<td>"+item.m_id+"</td>";
						conTag +="<td class=\"hover-td\"><a href=\"javascript:\">"+item.m_name+"</a></td>";
						conTag +="<td>"+item.m_email+"</td>";
						conTag +="<td>"+item.m_pass+"</td>";
						conTag +="<td><button class=\"delBtn\" data=\""+item.m_id+"\">삭제</button></td>";
						conTag +="</tr>";
				});
				$("table#sub-table>tbody").empty().append(conTag);
				
	        		
	        },
	        error : function(request, status, error) { 
	        	//alert(okText+"내용을 확인해주세요");
	            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
	        } 
	        
	    });
		
	}
	getAttendList();
	
	
});


</script>
</body>
</html>