<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
String fileDir = request.getRealPath("/resources/uploads/");
String rootPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<title>입력</title>
<script type="text/javascript">
$(function(){
	$('a[data-toggle="tooltip"]').tooltip();
	$("#insert").click(function(){ 
		okCnt = 0;
		mode = $("#mode").val();
		frm = $("#editFrm");
		console.log(frm.serialize());
		$( ':input[required]', frm ).each( function () {
		    if ( $(this).val().trim() == '' ) {
		        $(this).focus();
		        okCnt++;
		    }
		});
		okText = "입력";
		if(mode=='insert'){
			frm.attr("action","<%=rootPath%>/course/insert");
		}else if(mode=="edit"){
			frm.attr("action","<%=rootPath%>/course/edit");
			okText = "수정";
			
		}else{
			return;
		}
		if(okCnt==0){
			$.ajax({
		        url:frm.attr("action"),
		        type:'post',
		        data: frm.serialize(),
		        success: function(json){
		        	if(json.data>0) {
		        		alert(okText+"성공하였습니다.");
		        		location.reload();
		        	}
		        		
		        },
		        error : function(request, status, error) { 
		        	alert(okText+"내용을 확인해주세요");
		            //alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		        
		    });
		};
		
	});
	$(".search-table").on("click",".hover-td",function(){
		if($.isEmptyObject(this)) return false;
		$("#mode").val("edit");
		$("#insert").text("수정");
		$("#subject_id").val($(this).prev().text());
		$("#subject_name").val($("a",this).text());
		$("#subject_start").val($(this).next().text());
		$("#subject_end").val($(this).nextAll(":eq(1)").text());
		$("#subject_state").val($(this).nextAll(":eq(3)").text());
		$("#subject_comment").val($("pre",this).text());
		//clearFrm();
		$('#myModal').modal();

	});
	$(".search-table").on("click",".delBtn",function(){
			sId = $(this).attr("data");
		if(confirm(sId+"번 과정을 삭제하시겠습니까?")){
			$.ajax({
		        url:"<%=rootPath%>/course/delete",
		        type:'post',
		        data: {subject_id:sId},
		        success: function(json){
		        	if(json.data>0) {
		        		alert("삭제성공하였습니다.");
		        		location.reload();
		        	}
		        		
		        },
		        error : function(request, status, error) { 
		        	alert(sId+"번 삭제 실패\n문제가 있습니다.");
		            //alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		    });
		}
	});
	function clearFrm(){
		$("#mode").val("insert");
		$("#insert").text("입력");
		$("#subject_id").val("");
		$("#subject_name").val("");
		$("#subject_start").val("");
		$("#subject_end").val("");
		$("#subject_state").val("");
		$("#subject_comment").val("");
		
	}
	$(".search-table").on("mouseenter mouseleave",".hover-td",function(){
		$("pre",this).toggle("fast");
	});
});
</script>
<style type="text/css">
.hover-td{
	position : relative;
}
.hover-td pre{
	position : absolute;
	left:200px;
	z-index:1;
	width:auto;
	height:auto;
	display: none;
}
</style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<!-- Button trigger modal -->


<!-- Modal -->
<div class="modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">반(과정) 입력</h4>
      </div>
      <div class="modal-body" style="min-height: 350px">
      <form:form commandName="command" id="editFrm">
      	<input  type="hidden" id="mode" value="insert">
      	<div class="form-group">
      	 	<label for="subjectId" class="col-sm-2 control-label">과정명</label>
         	<div class="col-sm-10">
         		<input type="hidden" class="form-control" id="subject_id" name="subject_id" placeholder="아이디">
         		<input type="text" class="form-control" id="subject_name" name="subject_name" placeholder="과정명" required="required">
         	</div>
        </div>
      	<div class="form-group">
      	 <label for="subjectStart" class="col-sm-2 control-label">시작,종료</label>
         	<div class="col-sm-10 form-inline">
         		<input type="date" class="form-control " id="subject_start" name="subject_start" placeholder="시작일">
         		-
         		<input type="date" class="form-control" id="subject_end" name="subject_end" placeholder="종료일">
         	</div>
        </div>
      	<div class="form-group ">
      	 <label for="subjectComment" class="col-sm-2 control-label">commant</label>
         	<div class="col-sm-10 form-inline">
         		<select   class="form-control" style="width: 100%" name="subject_state" id="subject_state">
         			<option value="예정">예정</option>
         			<option value="진행중">진행중</option>
         			<option value="종료">종료</option>
         		</select>
         	</div>
        </div>
      	<div class="form-group ">
      	 <label for="subjectComment" class="col-sm-2 control-label">commant</label>
         	<div class="col-sm-10 form-inline">
         		<textarea rows="10"  class="form-control" style="width: 100%" name="subject_comment" id="subject_comment"></textarea>
         	</div>
        </div>
        </form:form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="insert">입력</button>
      </div>
    </div>
  </div>
</div>
<div class="main"><div class="main-div">
	<h3 class="sub-title">반(과정)관리</h3>
	<div class="">
	<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
  		입력
	</button>
	</div>
	
<div class="search-table">
		<table  class="table table-striped table-bordered" cellspacing="0" width="100%">
			<tr>

				<th>번호</th>
				<th>과정명</th>
				<th>시작일</th>
				<th>종료일</th>
				<th>인원수</th>
				<th>상태</th>
				<th>삭제</th>
			</tr>
			<c:forEach var="subject" items="${subjectList}">
				<tr>
					<td>${subject.subject_id}</td>
					<td class="hover-td"><a href="javascript:">${subject.subject_name}</a><pre readonly="readonly">${subject.subject_comment}</pre></td>
					<td>${subject.subject_start}</td>
					<td>${subject.subject_end}</td>
					<td>${subject.student_count}</td>
					<td>${subject.subject_state}</td>
					<td><button class="delBtn" data="${subject.subject_id}">삭제</button></td>
				</tr>
			</c:forEach>
		</table>
</div>	

</div></div>
<%@ include file="/WEB-INF/views/include/foot.jsp" %>
</body>
</html>