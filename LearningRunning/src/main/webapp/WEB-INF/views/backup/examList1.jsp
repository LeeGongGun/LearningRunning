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
	$("#modalOn").click(function(){
		clearFrm();
		$('#myModal').modal("show");
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
			frm.attr("action","<%=rootPath%>/admin/exam/insert");
		}else if(mode=="edit"){
			frm.attr("action","<%=rootPath%>/admin/exam/edit");
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
		        		getExamList();
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
		getExamList();
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
		        		getExamList();
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
		$("#exam_id").val("");
		$("#frm_class_id").val("");
		$("#exam_title").val("");
		$("#exam_date").val("");
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
	
	
	function getExamList(){
		$.ajax({
	        url:"<%=rootPath%>/admin/exam",
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
	$("#not-surbjects").on("click","tr.list-tr",function(){
		trClick("#44b6d9",this);
	});
	$("#con-surbjects").on("click","tr.list-tr",function(){
		trClick("#ed9c2a",this);
	});
	$("#btnJoin").click(joinSubject);
	$("#btnCut").click(cutSubject);
	$("#not-allCheck").click(function(){
		$("table#not-surbjects>tbody>tr").each(function(){
			if($(this).is(':visible')) trClick("#44b6d9",this);
		});
	});
	$("#con-allCheck").click(function(){
		$("table#con-surbjects>tbody>tr").each(function(){
			if($(this).is(':visible')) trClick("#ed9c2a",this);
		});
	});
	$("#not-search").keydown(function(e){
		if(e.which == 13){
			getTable($("#not-surbjects"),e.target.value.toUpperCase());
		}
	});
	$("#con-search").keydown(function(e){
		if(e.which == 13){
			getTable($("#con-surbjects"),e.target.value.toUpperCase());
		}
	});
	function getTable(table,sText){
		$("tbody>tr",table).each(function(){
			tr = this;
			sum = 0;
			$("td",tr).each(function(i){
				if ( $(this).text().toUpperCase().indexOf(sText) > -1 ) {
					sum++;
				}
			});
			if ( sum > 0 ) {
				$(tr).show();
			}else{
				chkbox = $("input[name='subject_id']",tr);
				chkbox.prop("checked", false);
				$(tr).hide();
			}
		});
	}

	
	function joinSubject(){
		obj = $("#not-surbjects  [name='subject_id']:checked");
		arr =[];
		obj.each(function(i){
			arr.push($(this).val());
		});
		if(arr.length>0 && $("#exam_id").val()!=""){
			$.ajax({
		        url:"<%=rootPath%>/admin/examSubject/join",
		        type:'post',
		        data: {
		        	subject_id : arr,
		        	exam_id : $("#exam_id").val()
		        	},
		        success: function(json){
		        	if(json.data>0) getSubjectList();
		        },
		        error : function(request, status, error) { 
		        	//alert(okText+"내용을 확인해주세요");
		            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		        
		    });
		}		
	}
	function cutSubject(){
		obj = $("#con-surbjects  [name='subject_id']:checked");
		arr =[];
		obj.each(function(i){
			arr.push($(this).val());
		});
		if(arr.length>0 && $("#exam_id").val()!=""){
			$.ajax({
		        url:"<%=rootPath%>/admin/examSubject/cut",
		        type:'post',
		        data: {
		        	subject_id : arr,
		        	exam_id : $("#exam_id").val()
				},
		        success: function(json){
		        	if(json.data>0) getSubjectList();
		        },
		        error : function(request, status, error) { 
		        	//alert(okText+"내용을 확인해주세요");
		            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		        
		    });
		}		
		
	}
	function trClick(color,obj){
		chkbox = $("input[name='subject_id']",obj);
		chkbox.prop("checked", !chkbox.is(":checked"));
		if(chkbox.is(":checked")){
			$(obj).css("background-color",color);
		}else{
			$(obj).css("background-color","#fff");
		}
	}
	$("#class_select_id").change(getExamList);
	$("#frm_class_id").change(getSubjectList);
	getExamList();
	function getSubjectList(){
		if( $("#frm_class_id").val()!="" && $("#exam_id").val()!=""){
			$.ajax({
		        url:"<%=rootPath%>/admin/examSubject",
		        type:'post',
		        data: {
	        		class_id : $("#frm_class_id").val(),
	        		exam_id : $("#exam_id").val()
		        },
		        success: function(json){
		        	notTag = "";
		        	conTag = "";
					$(json.data).each(function(i,item){
						if (item.exam_id==0) {
							notTag +="<tr class='list-tr'>";
							notTag +="<td><input type='checkbox' name='subject_id' value='"+item.subject_id+"' readonly/></td>";
							notTag +="<td>"+item.subject_title+"</td>";
							notTag +="</tr>";						
						} else {
							conTag +="<tr class='list-tr'>";
							conTag +="<td><input type='checkbox' name='subject_id' value='"+item.subject_id+"'readonly/></td>";
							conTag +="<td>"+item.subject_title+"</td>";
							conTag +="</tr>";
						}
					});
					$("table#not-surbjects>tbody").empty().append(notTag);
					$("table#con-surbjects>tbody").empty().append(conTag);
		        		
		        },
		        error : function(request, status, error) { 
		        	//alert(okText+"내용을 확인해주세요");
		            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		        
		    });
		}
	}
	
	
});
</script>
<style type="text/css">
.hover-td{cursor: pointer;}
.search-div{ float: right;position: relative;top: -46px;}
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
        <h4 class="modal-title" id="myModalLabel">시험 입력</h4>
      </div>
      <div class="modal-body" style="min-height: 150px">
      <form id="editFrm">
      	<input  type="hidden" id="mode" value="insert">
      	<div class="form-group">
      	 	<label for="frm_select_id" class="col-sm-2 control-label">반</label>
         	<div class="col-sm-10">
				<select  class="form-control "  name="class_id" id="frm_class_id">
					<option value="">반을 선택하세요. </option>
					<c:forEach var="classes" items="${classList}">
		  			<option value="${classes.class_id }">${classes.class_name}-${classes.class_state}</option>
					</c:forEach>
				</select>
         	</div>
        </div>
      	<div class="form-group">
       	 	<label for="exam_title" class="col-sm-2 control-label">시험제목</label>
         	<div class="col-sm-10">
         		<input type="hidden" class="form-control" id="exam_id" name="exam_id" placeholder="아이디">
         		<input type="text" class="form-control" id="exam_title" name="exam_title" placeholder="시험명" required="required">
         	</div>
        </div>
      	<div class="form-group">
      	 	<label for="exam_date" class="col-sm-2 control-label">시험일자</label>
         	<div class="col-sm-10">
         		<input type="date" class="form-control" id="exam_date" name="exam_date" placeholder="exam_date" required="required">
         	</div>
        </div>
        </form>
      	<div class="form-group">
<div class="search-table row" id="modal-table">
	<div class="col-sm-5">
		<table  class="table table-striped table-bordered col-sm-12" cellspacing="0" width="100%" id="not-surbjects">
			<thead>
			<tr>

				<th><a href="javascript:;" class="btn btn-default btn-xs" id="not-allCheck">반전하기</a></th>
				<th><input type="text" id="not-search" class="form-control input-sm" placeholder="이름검색"></th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<div class="col-sm-2">
		<button class="btn btn-info btn-xs" id="btnJoin">연결  <span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span></button><br/>
		<button class="btn btn-warning btn-xs" id="btnCut"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>  삭제</button>
	</div>
	<div class="col-sm-5">
		<table  class="table table-striped table-bordered col-sm-12"  id="con-surbjects">
			<thead>
			<tr>

				<th><a href="javascript:;" class="btn btn-default btn-xs" id="con-allCheck">반전하기</a></th>
				<th><input type="text" id="con-search" class="form-control input-sm" placeholder="이름검색"></th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</div>	        </div>


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="insert">입력</button>
      </div>
    </div>
  </div>
</div>
<div class="main"><div class="main-div">
	<h3 class="sub-title">시험 관리</h3>
	<div class="search-div form-inline">
		<form id="searchFrm">
			<select  class="form-control "  name="class_id" id="class_select_id">
				<option value="">반을 선택하세요. </option>
				<c:forEach var="classes" items="${classList}">
	  			<option value="${classes.class_id }">${classes.class_name}-${classes.class_state}</option>
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
				<th colspan="2"><input type="text" class="form-control" id="searchText" name="searchText" placeholder="반명,시험명 검색"></th>
				<th>시험일</th>
				<th>삭제</th>
			</tr>
			</thead>
			<tbody></tbody>
		</table>
</div>	

</div></div>
<%@ include file="/WEB-INF/views/include/foot.jsp" %>
</body>
</html>