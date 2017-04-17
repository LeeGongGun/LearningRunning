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
<title>과목 입력</title>
<script type="text/javascript">
$(function(){
	$("#modalOn").click(function(){
		clearFrm();
		$('#myModal').modal("show");
	});
	$("#counsel_date").datepicker({
		language:  'ko',
		container: "#datepicker-div",
		format: "yyyy/mm/dd",
		todayHighlight:  true,
        autoclose: true,
        useCurrent: false,
	});

	$("#insert").click(function(){ 
		okCnt = 0;
		mode = $("#mode").val();
		frm = $("#editFrm");
		console.log(frm.serialize());
		$($(':input[required]', frm ).get().reverse()).each( function () {
		    if ( $(this).val().trim() == '' ) {
		        $(this).focus();
		        okCnt++;
		        return;
		    }
		});
		okText = "입력";
		if(mode=='insert'){
			frm.attr("action","<%=rootPath%>/admin/counsel/insert");
		}else if(mode=="edit"){
			frm.attr("action","<%=rootPath%>/admin/counsel/edit");
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
		        		getCounselList();
		        	}
		        		
		        },
		        error : function(request, status, error) { 
		        	alert(okText+"내용을 확인해주세요");
		            //alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		        
		    });
		};
		
	});
	$("#searchText").keydown(function(e){
		if(e.which == 13){
			getList($("#sub-table"),$("#searchText").val().toUpperCase());
		}
	});
	$("#searchBtn").click(function(){
		$("#class_select_id").val("");
		getCounselList();
	});
	$(".search-table").on("click",".hover-td",function(){
		if($.isEmptyObject(this)) return false;
		exam_id = $(this).parents("tr").find("td:eq(0)").text()
		exam_title = $(this).parents("tr").find("td:eq(2)").text();
		exam_date = $(this).parents("tr").find("td:eq(3)").text();
		class_id = $(this).parents("tr").data("class_id");
		$("#mode").val("edit");
		$("#insert").text("수정");
		$("#exam_id").val(exam_id);
		$("#frm_class_id").val(class_id);
		$("#exam_title").val(exam_title);
		$("#exam_date").val(exam_date);
		getSubjectList();
		$('#myModal').modal();

	});
	$(".search-table").on("click",".delBtn",function(){
		sId = $(this).parents("tr").find("td:eq(1)").text()
			+"("
			+$(this).parents("tr").find("td:eq(2)").text()
			+")";
			del_id = $(this).data("exam_id");
		if(confirm(sId+"님을 삭제하시겠습니까?")){
			$.ajax({
		        url:"<%=rootPath%>/admin/exam/delete",
		        type:'post',
		        data: {exam_id: del_id},
		        success: function(json){
		        	if(json.data>0) {
		        		alert("삭제성공하였습니다.");
		        		getCounselList();
		        	}else if(json.data==0){
		        		alert("점수데이터가 있어서 삭제불가능.");
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
		$("#counselor").val("");
		$("#m_id").val("");
		$("#counsel_title").val("");
		$("#counsel_condent").val("");
		$("#counsel_date").val("");
	}
	function getList(table,sText){
		$("tbody>tr",table).each(function(){
			tr = this;
			sum = 0;
			$("td",tr).each(function(i){
				if( i!=1 && i!=2 ) return true;
				if ( $(this).text().toUpperCase().indexOf(sText) > -1 ) {
					sum++;
				}
			});
			if ( sum > 0 ) {
				$(tr).show();
			}else{
				$(tr).hide();
			}
		});
	}
	
	
	function getCounselList(){
		$.ajax({
	        url:"<%=rootPath%>/teacher/counsel",
	        type:'post',
	        data: $("#searchFrm").serialize(),
	        success: function(json){
	        	conTag = "";
				$(json.data).each(function(i,item){
						conTag +="<tr data-class_id='"+item.class_id+"'>";
						conTag +="<td>"+item.exam_id+"</td>";
						conTag +="<td>"+item.class_name+"</td>";
						conTag +="<td class=\"hover-td\"><a href=\"javascript:\" >"+item.exam_title+"</a></td>";
						conTag +="<td>"+item.exam_date+"</td>";
						conTag +="<td><button class=\"delBtn\" data-exam_id=\""+item.exam_id+"\">삭제</button></td>";
						conTag +="</tr>";
				});
				$("table#sub-table>tbody").empty().append(conTag);
				clearFrm();
				$('#myModal').modal("hide");
				
	        		
	        },
	        error : function(request, status, error) { 
	        	//alert(okText+"내용을 확인해주세요");
	            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
	        } 
	        
	    });
		
	}	
});
</script>
<style type="text/css">
.hover-td{cursor: pointer;}
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
        <h4 class="modal-title" id="myModalLabel">과목 입력</h4>
      </div>
      <div class="modal-body" style="min-height: 400px">
      <form:form commandName="command" id="editFrm">
      	<input  type="hidden" id="mode" value="insert">
      	<div class="form-group">
      	 	<label for="counselor" class="col-sm-2 control-label">상담자</label>
         	<div class="col-sm-10">
				<select  class="form-control "  name="counselor" id="counselor" required="required">
					<option value="">상담자를 선택하세요. </option>
					<c:forEach var="teacher" items="${listTeacher}">
		  			<option value="${teacher.m_id }">${teacher.m_name}</option>
					</c:forEach>
				</select>
         	</div>
        </div>
      	<div class="form-group">
      	 	<label for="m_id" class="col-sm-2 control-label">상담대상</label>
         	<div class="col-sm-10">
			<select  class="form-control "  name="m_id" id="m_id" required="required">
				<option value="">상담대상을 선택하세요. </option>
				<c:forEach var="student" items="${listStudent}">
	  			<option value="${student.m_id }">${student.m_name}</option>
				</c:forEach>
			</select>
         	</div>
        </div>
      	<div class="form-group">
      	 	<label for="counsel_date" class="col-sm-2 control-label">상담일자</label>
         	<div class="col-sm-10">
				<input type="text" class="form-control" id="counsel_date" name="counsel_date" placeholder="상담일자">
         	</div>
        </div>
      	<div class="form-group">
      	 	<label for="class_id" class="col-sm-2 control-label">상담 제목</label>
         	<div class="col-sm-10">
         		<input type="hidden" class="form-control" id="frm_counsel_id" name="counsel_id" placeholder="아이디">
         		<input type="text" class="form-control" id="frm_counsel_title" name="counsel_title" placeholder="상담제목" required="required">
         	</div>
        </div>
      	<div class="form-group">
      	 	<label for="class_id" class="col-sm-2 control-label">상담내용</label>
         	<div class="col-sm-10">
         		<textarea style="min-height: 280px" class="form-control" id="counsel_condent" name="counsel_condent"></textarea>
         	</div>
        </div>
        </form:form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-danger disabled" id="delBtn">삭제</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary " id="insert">입력</button>
      </div>
    </div>
  </div>
</div>
<div id="counsel_comments_div" style="display: none;"></div>
<div class="main"><div class="main-div">
	<h3 class="sub-title">상담 관리</h3>
	<div class="search-div form-inline">
		<form id="searchFrm">
			<select  class="form-control "  name="counselor" id="select_counselor">
				<option value="">상담자를 선택하세요. </option>
				<c:forEach var="teacher" items="${listTeacher}">
	  			<option value="${teacher.m_id }">${teacher.m_name}</option>
				</c:forEach>
			</select>
			<select  class="form-control "  name="m_id" id="m_select_id">
				<option value="">상담대상을 선택하세요. </option>
				<c:forEach var="student" items="${listStudent}">
	  			<option value="${student.m_id }">${student.m_name}</option>
				</c:forEach>
			</select>
	
	<button type="button" class="btn btn-primary" id="searchBtn">
  		모두보기
	</button>
	<button type="button" class="btn btn-primary" id="modalOn">
  		입력
	</button>
	</form>
	</div>
<div class="search-table">
		<table  class="table table-striped table-bordered table-hover" id="sub-table" cellspacing="0" width="100%">
			<thead>
			<tr>

				<th>번호</th>
				<th>상담자</th>
				<th>상담대상</th>
				<th><input type="text" class="form-control" id="searchText" name="searchText" placeholder="상담자,상담대상,상담내용 검색"></th>
				<th>상담일</th>
				<th>삭제</th>
			</tr>
			</thead>
			<tbody></tbody>
		</table>
</div>	
</div>
</div>
<%@ include file="/WEB-INF/views/include/foot.jsp" %>
</body>
</html>