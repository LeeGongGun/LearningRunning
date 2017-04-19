<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String fileDir = request.getRealPath("/resources/uploads/");
	String rootPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>반(class)관리</title>
<%@ include file="/WEB-INF/views/include/html-head.jsp"%>
<style type="text/css">
.hover-td {
	position: relative;
}

.hover-td pre {
	position: absolute;
	left: 200px;
	z-index: 1;
	width: auto;
	height: auto;
	display: none;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@ include file="/WEB-INF/views/include/html-header.jsp"%>
		<%@ include file="/WEB-INF/views/include/html-leftMenuBar.jsp"%>
		<%@ include file="/WEB-INF/views/include/html-js.jsp"%>
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					반(class)관리 <small>반(class)관리</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li class="active">반(class)관리</li>
				</ol>
			</section>
			<section class="content">
				<div class="row">
					<div class="col-xs-12">
						<div class="box box-success form-inline">
							<button type="button" class="btn btn-primary btn-lg" id="modalOn">
								입력</button>
						</div>
					</div>
					<div class="box box-primary">
						<table class="table table-striped table-bordered" id="sub-table">
							<thead>
								<tr>

									<th>번호</th>
									<th>반명</th>
									<th>시작일</th>
									<th>종료일</th>
									<th>인원수</th>
									<th>상태</th>
									<th>삭제</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>

				</div>
			</section>
		</div>


		<%@ include file="/WEB-INF/views/include/html-footer.jsp"%>
		<%@ include file="/WEB-INF/views/include/html-rightAside.jsp"%>
		<!-- Modal -->
		<div class="modal" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">반 입력</h4>
					</div>
					<div class="modal-body" style="min-height: 350px">
						<form:form commandName="command" id="editFrm">
							<input type="hidden" id="mode" value="insert">
							<div class="form-group">
								<label for="classId" class="col-sm-2 control-label">반명</label>
								<div class="col-sm-10">
									<input type="hidden" class="form-control" id="class_id"
										name="class_id" placeholder="아이디"> <input type="text"
										class="form-control" id="class_name" name="class_name"
										placeholder="과정명" required="required">
								</div>
							</div>
							<div class="form-group">
								<label for="classStart" class="col-sm-2 control-label">시작,종료</label>
								<div class="col-sm-10 form-inline">
									<input type="text" class="form-control " id="class_start"
										name="class_start" placeholder="시작일"> - <input
										type="text" class="form-control" id="class_end"
										name="class_end" placeholder="종료일">
								</div>
							</div>
							<div class="form-group ">
								<label for="classComment" class="col-sm-2 control-label">상태</label>
								<div class="col-sm-10 form-inline">
									<select class="form-control" style="width: 100%"
										name="class_state" id="class_state">
										<option value="예정" selected>예정</option>
										<option value="진행중">진행중</option>
										<option value="종료">종료</option>
									</select>
								</div>
							</div>
							<div class="form-group ">
								<label for="classComment" class="col-sm-2 control-label">commant</label>
								<div class="col-sm-10 form-inline">
									<textarea rows="10" class="form-control" style="width: 100%"
										name="class_comment" id="class_comment"></textarea>
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
		<script type="text/javascript">
$.AdminLTE.dinamicMenu = function() {
    var url = window.location;
    // Will only work if string in href matches with location
    $('.treeview-menu li a[href="' + url.pathname + '"]').parent().addClass('active');
    // Will also work for relative and absolute hrefs
    $('.treeview-menu li a').filter(function() {
        return this.href == url;
    }).parent().parent().parent().addClass('active');
};  
$.AdminLTE.dinamicMenu();
</script>
		<script type="text/javascript">
$(function(){
	$table = $("#sub-table");
	$("#class_start,#class_end").datepicker({
		language:  'ko',
		container: "#datepicker-div",
		format: "yyyy-mm-dd",
		todayHighlight:  true,
        autoclose: true,
        useCurrent: false,
	});

	$("#modalOn").click(function(){
		clearFrm();
		$('#myModal').modal("show");
	});
	$("#insert").click(function(){ 
		okCnt = 0;
		mode = $("#mode").val();
		frm = $("#editFrm");
		$( ':input[required]', frm ).each( function () {
		    if ( $(this).val().trim() == '' ) {
		        $(this).focus();
		        okCnt++;
		    }
		});
		okText = "입력";
		if(mode=='insert'){
			frm.attr("action","<%=rootPath%>/admin/class/insert");
		}else if(mode=="edit"){
			frm.attr("action","<%=rootPath%>/admin/class/edit");
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
		        		getSubjectList();
		        	}
		        		
		        },
		        error : function(request, status, error) { 
		        	alert(okText+"내용을 확인해주세요");
		            //alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		        
		    });
		};
		
	});
	$table.on("click",".hover-td",function(){
		if($.isEmptyObject(this)) return false;
		$("#mode").val("edit");
		$("#insert").text("수정");
		$("#class_id").val($(this).prev().text());
		$("#class_name").val($("a",this).text());
		$("#class_start").val($(this).next().text());
		$("#class_end").val($(this).nextAll(":eq(1)").text());
		$("#class_state").val($(this).nextAll(":eq(3)").text());
		$("#class_comment").val($("pre",this).text());
		//clearFrm();
		$('#myModal').modal();

	});
	$table.on("click",".delBtn",function(){
		classTitle = $(this).parents("tr").find("td:eq(1)");
		pre = classTitle.find("fre").detach();
		if(confirm(classTitle.text()+"과정를 삭제하시겠습니까?")){
			$.ajax({
		        url:"<%=rootPath%>/admin/class/delete",
		        type:'post',
		        data: {class_id:sId},
		        success: function(json){
		        	if(json.data>0) {
		        		alert("삭제성공하였습니다.");
		        		getSubjectList();
		        	}
		        		
		        },
		        error : function(request, status, error) { 
		        	alert(sId+"번 삭제 실패\n문제가 있습니다.");
		            //alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        },
		        complete : function(){
		        	classTitle.append(pre);
		        }
		    });
		}
	});
	function clearFrm(){
		$("#mode").val("insert");
		$("#insert").text("입력");
		$("#class_id").val("");
		$("#class_name").val("");
		$("#class_start").val("");
		$("#class_end").val("");
		$("#class_state").val("예정");
		$("#class_comment").val("");
		
	}
	$table.on("mouseenter mouseleave",".hover-td",function(){
		$("pre",this).toggle("fast");
	});
	function getSubjectList(){
		$.ajax({
	        url:"<%=rootPath%>/admin/class",
			type : 'post',
			data : $("#editFrm").serialize(),
			success : function(json) {
				conTag = "";
				$(json.data).each(function(i, item) {
						conTag += "<tr>";
						conTag += "<td>"
								+ item.class_id
								+ "</td>";
						//conTag +="<td class=\"hover-td\"><a href=\"javascript:\">"+item.class_name+"</a><pre>"+item.class_comment+"</pre></td>";
						conTag += "<td class=\"hover-td\"><a href=\"javascript:\">"
								+ item.class_name
								+ "</a><pre>"
								+ item.class_comment
								+ "</pre></td>";
						conTag += "<td>"
								+ item.class_start
								+ "</td>";
						conTag += "<td>"
								+ item.class_end
								+ "</td>";
						conTag += "<td>"
								+ item.student_count
								+ "</td>";
						conTag += "<td>"
								+ item.class_state
								+ "</td>";
						conTag += "<td><button class=\"btn btn-default delBtn\" data=\""+item.class_id+"\">삭제</button></td>";
						conTag += "</tr>";
					});
				$("table#sub-table>tbody").empty().append(
						conTag);
				clearFrm();
				$('#myModal').modal("hide");

			},
			error : function(request, status, error) {
				//alert(okText+"내용을 확인해주세요");
				alert("code : " + request.status
						+ "\r\nmessage : "
						+ request.reponseText);
			}

		});

	}
	getSubjectList();
});
</script>
	</div>
</body>
</html>