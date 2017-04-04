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
#personAttendTable{background-color: #fff;}
</style>
<title>학생 페이지</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/nav.jsp"%>
	<div class="main">
		<div class="main-div">
			<h3>학생개인별 출석</h3>
			<form:form commandName="command" id="searchFrm" class="form-inline">
				<select name="class_id" id="class_id"  class="form-control" required>
					<option value="">과정을 선택하세요</option>
					<c:forEach var="classes" items="${cList}">
					<option value="${classes.class_id }">${classes.class_name}</option>
					</c:forEach>
				</select>				
				
				<select name="m_id" id="m_id" class="form-control" required>
					<option value="">학생을 선택하세요</option>
					<c:forEach var="m" items="${authMembers}">
					<option value="${m.m_id }"> [ ${m.m_email} ] ${m.m_name}</option>
					</c:forEach>
				
				</select>	
				<br>			
					<label>
					<input type="text" name="from" id="from" class="datetimepicker form-control" placeholder="from" required readonly/>
					</label>
					~ <label> 
					<input type="text" name="to" id="to"  class="datetimepicker form-control" placeholder="to" required readonly/>
					</label>
					<input type="button" value="기간별 조회"  class="btn btn-default" id="searchBtn">
			</form:form>

			<table class="table table-striped table-bordered" cellspacing="0" width="100%" id="personTotalAttendTable">
					<tr>
						<th>출석 : <span id="attendings">${ClassAttend.attendings }</span></th>
						<th>결석 : <span id="noAttendings">${ClassAttend.noAttendings }</span></th>
						<th>외출 : <span id="outings">${ClassAttend.outings }</span></th>
						<th>지각 : <span id="lateings">${ClassAttend.lateings }</span></th>
						<th>조퇴 : <span id="outComings">${ClassAttend.outComings }</span></th>
					</tr>
					
			</table>
					<table class="table table-hover table-bordered" cellspacing="0" width="100%" id="personAttendTable">
						<thead>
							<tr>
								<th>수강날짜</th>
								<th>입실</th>
								<th>퇴실</th>
								<th>외출</th>
								<th>복귀</th>
								<th>출결</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
		</div>
	</div>
<%@ include file="/WEB-INF/views/include/foot.jsp" %>
<script>
$(function() {
	$("#m_id,#class_id").select2();
	from = new Date();
	from.setMonth(from.getMonth()-1);
	$("#from")
	.datetimepicker({
		language:  'ko',
		container: "#datepicker-div",
		format: "yyyy/mm/dd",
		todayHighlight:  true,
        autoclose: true,
        useCurrent: false,
	})
	.datetimepicker("update", from);
	$("#to")
	.datetimepicker({
		language:  'ko',
		container: "#datepicker-div",
		format: "yyyy/mm/dd",
		todayHighlight:  true,
        autoclose: true,
        useCurrent: false,
	})
	.datetimepicker("update", new Date());
	$("#class_id").change(function(){
		$.ajax({
			url:"<%=rootPath%>/attendance/selectStudentList",
			type:'post',
	        data: {class_id : $("#class_id").val()},
	        success: function(json){
	        	conTag = "<option value=\"\">학생을 선택하세요</option>";
				$(json.data).each(function(i,item){
		        	conTag += "<option value=\""+item.m_id+"\"> [ "+item.m_email+" ] "+item.m_name+"</option>";

				});
				$("#m_id").empty().append(conTag).select2("open");
				
	        		
	        },
	        error : function(request, status, error) { 
	        	//alert(okText+"내용을 확인해주세요");
	            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
	        } 
	        
	    });			
	});
	function getAttendList(){
		okCnt = 0;
		mode = $("#mode").val();
		frm = $("#searchFrm");
		$( ':required', frm ).each( function () {
		    if ( $(this).val().trim() == '' ) {
		    	if($(this).is("select")){
		    		$(this).select2("open");
		    	}else{
		    		$(this).focus();
		    	}
		        okCnt++;
		    }
		});
		if(okCnt==0){
			$.ajax({
				url:"<%=rootPath%>/attendance/person",
				type:'post',
		        data: $("#searchFrm").serialize(),
		        success: function(json){
		        	conTag = "";
		        	attendings = 0;
		        	noAttendings = 0;
		        	outings = 0;
		        	lateings = 0;
		        	outComings = 0;
					$(json.data).each(function(i,item){
						switch (item.attend_status) {
						case "출석":attendings++;break;
						case "결석":noAttendings++;break;
						case "조퇴":outings++;break;
						case "지각":lateings++;break;
						case "외출":outComings++;break;
						default:break;
						}
							conTag +="<tr>";
							conTag +="<td>"+item.attend_date+"</td>";
							conTag +="<td>"+((item.start_time==null)?"":item.start_time)+"</td>";
							conTag +="<td>"+((item.end_time==null)?"":item.end_time)+"</td>";
							conTag +="<td>"+((item.stop_time==null)?"":item.stop_time)+"</td>";
							conTag +="<td>"+((item.restart_time==null)?"":item.restart_time)+"</td>";
							conTag +="<td>"+item.attend_status+"</td>";
							conTag +="</tr>";
	
					});
					$("#attendings").text(attendings);
					$("#noAttendings").text(noAttendings);
					$("#outings").text(outings);
					$("#lateings").text(lateings);
					$("#outComings").text(outComings);
					$("#personTotalAttendTable").show();
					$("#personAttendTable>tbody").empty().append(conTag);
					
		        		
		        },
		        error : function(request, status, error) { 
		        	//alert(okText+"내용을 확인해주세요");
		            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		        
		    });
		}
	}
	$("#personTotalAttendTable").hide();
	$("#searchBtn").click(getAttendList);
	//getAttendList();
	
	
});


</script>
</body>
</html>