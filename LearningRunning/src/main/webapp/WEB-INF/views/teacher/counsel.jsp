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
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/html-head.jsp"%>
<style type="text/css">
#searchText {
	min-width: 300px;
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
					상담 관리 <small>상담 입력,수정,삭제</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li class="active">Dashboard</li>
				</ol>
			</section>
			<section class="content">
				<div class="row">
					<div class="col-xs-12">
						<div class="box box-success form-inline">
							<form id="searchFrm">
								<select class="form-control " name="counselor"
									id="select_counselor">
									<option value="">상담자를 선택하세요.</option>
									<c:forEach var="teacher" items="${listTeacher}">
										<option value="${teacher.m_id }">${teacher.m_name}</option>
									</c:forEach>
								</select> <select class="form-control " name="m_id" id="m_select_id">
									<option value="">상담대상을 선택하세요.</option>
									<c:forEach var="student" items="${listStudent}">
										<option value="${student.m_id }">${student.m_name}</option>
									</c:forEach>
								</select>

								<button type="button" class="btn btn-primary" id="searchBtn">
									검색</button>
								<button type="button" class="btn btn-primary" id="modalOn">
									입력</button>
							</form>
						</div>
						<div class="search-table box box-info">
								<table class="table table-striped table-bordered table-hover"
									id="sub-table" >
									<thead>
										<tr>

											<th>번호</th>
											<th>상담자</th>
											<th>상담대상</th>
											<th><input type="text" class="form-control"
												id="searchText" name="searchText"
												placeholder="상담자,상담대상,상담내용 검색"></th>
											<th>상담일</th>
											<th>삭제</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
						</div>

						</div>
					</div>
				</div>
			</section>
		</div>


		<%@ include file="/WEB-INF/views/include/html-footer.jsp"%>
		<%@ include file="/WEB-INF/views/include/html-rightAside.jsp"%>
		<div class="modal" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">과목 입력</h4>
					</div>
					<div class="modal-body" style="min-height: 400px">
						<form:form commandName="command" id="editFrm">
							<input type="hidden" id="mode" value="insert">
							<div class="form-group">
								<label for="counselor" class="col-sm-2 control-label">상담자</label>
								<div class="col-sm-10">
									<select class="form-control " name="counselor" id="counselor"
										required="required">
										<option value="">상담자를 선택하세요.</option>
										<c:forEach var="teacher" items="${listTeacher}">
											<option value="${teacher.m_id }">${teacher.m_name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="m_id" class="col-sm-2 control-label">상담대상</label>
								<div class="col-sm-10">
									<select class="form-control " name="m_id" id="m_id"
										required="required">
										<option value="">상담대상을 선택하세요.</option>
										<c:forEach var="student" items="${listStudent}">
											<option value="${student.m_id }">${student.m_name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="counsel_date" class="col-sm-2 control-label">상담일자</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="counsel_date"
										name="counsel_date" placeholder="상담일자">
								</div>
							</div>
							<div class="form-group">
								<label for="class_id" class="col-sm-2 control-label">상담
									제목</label>
								<div class="col-sm-10">
									<input type="hidden" class="form-control" id="frm_counsel_id"
										name="counsel_id" placeholder="아이디"> <input
										type="text" class="form-control" id="frm_counsel_title"
										name="counsel_title" placeholder="상담제목" required="required">
								</div>
							</div>
							<div class="form-group">
								<label for="class_id" class="col-sm-2 control-label">상담내용</label>
								<div class="col-sm-10">
									<textarea style="min-height: 280px" class="form-control"
										id="counsel_condent" name="counsel_condent"></textarea>
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
	$table=$("#sub-table");
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
			frm.attr("action","<%=rootPath%>/teacher/counsel/insert");
		}else if(mode=="edit"){
			frm.attr("action","<%=rootPath%>/teacher/counsel/edit");
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
		getCounselList();
	});
	$table.on("click",".hover-td",function(){
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
		getCounselList();
		$('#myModal').modal("show");

	});
	$table.on("click",".delBtn",function(){
		sId = $(this).parents("tr").find("td:eq(2)").text()
			+"("
			+$(this).parents("tr").find("td:eq(0)").text()
			+")";
			del_id = $(this).data("counsel_id");
		if(confirm(sId+"님 상담내역을 삭제하시겠습니까?")){
			$.ajax({
		        url:"<%=rootPath%>/teacher/counsel/delete",
		        type:'post',
		        data: {counsel_id: del_id},
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
				if( i!=1 && i!=2 && i!=3 ) return true;
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
		$counselor = $("#select_counselor");
		$student = $("#m_select_id");
		$.ajax({
	        url:"<%=rootPath%>/teacher/counsel",
								type : 'post',
								data : $("#searchFrm").serialize(),
								success : function(json) {
									conTag = "";
									$(json.data)
											.each(
													function(i, item) {
														conTag += "<tr data-counsel_id='"+item.counsel_id+"'>";
														conTag += "<td>"
																+ item.counsel_id
																+ "</td>";
														counselor = $counselor
																.find(
																		"[value='"
																				+ item.counselor
																				+ "']")
																.text();
														conTag += "<td>"
																+ counselor
																+ "</td>";
														student = $student
																.find(
																		"[value='"
																				+ item.m_id
																				+ "']")
																.text();
														conTag += "<td>"
																+ student
																+ "</td>";
														conTag += "<td class=\"hover-td\"><a href=\"javascript:\" >"
																+ item.counsel_title
																+ "</a></td>";
														conTag += "<td>"
																+ item.counsel_date
																+ "</td>";
														conTag += "<td><button class=\"btn btn-default delBtn\" data-counsel_id=\""+item.counsel_id+"\">삭제</button></td>";
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
				getCounselList();
			});
		</script>

	</div>
</body>
</html>