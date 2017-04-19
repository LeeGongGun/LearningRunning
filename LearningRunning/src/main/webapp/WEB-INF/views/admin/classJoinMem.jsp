<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String fileDir = request.getRealPath("/resources/uploads/");
	String rootPath = request.getContextPath();
%><!DOCTYPE html>
<html>
<head>
<title>반-선생,학생 연결</title>
<%@ include file="/WEB-INF/views/include/html-head.jsp"%>
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
					반-선생,학생 연결 <small>반-선생,학생 연결</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li class="active">반-선생,학생 연결</li>
				</ol>
			</section>
			<section class="content">
				<div class="row">
					<div class="col-xs-12">
						<div class="box box-success form-inline">
							<select class="form-control " name="auth_ename" id="auth_ename">
								<option value="student">학생</option>
								<option value="teacher">선생님</option>
							</select> <select class="form-control " name="class_id" id="class_id">
								<c:forEach var="classes" items="${classList}">
									<option value="${classes.class_id }">${classes.class_name}-${classes.class_state}</option>
								</c:forEach>
							</select>

						</div>
					</div>
					<div class="col-sm-5">
						<div class="box box-info">
							<table class="table table-striped table-bordered"  id="not-members">
								<thead>
									<tr>

										<th><a href="javascript:;" class="btn btn-default btn-sm"
											id="not-allCheck">반전하기</a></th>
										<th><input type="text" id="not-search"
											class="form-control" placeholder="이름검색"></th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>
					<div class="col-sm-2">
						<button class="btn btn-info btn-block" id="btnInsert">
							입력 <span class="glyphicon glyphicon-arrow-right"
								aria-hidden="true"></span>
						</button>
						<br />
						<button class="btn btn-warning btn-block" id="btnDel">
							<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
							삭제
						</button>
					</div>
					<div class="col-sm-5">
						<div class="box box-warning">
							<table class="table table-striped table-bordered" id="con-members">
								<thead>
									<tr>

										<th><a href="javascript:;" class="btn btn-default btn-sm"
											id="con-allCheck">반전하기</a></th>
										<th><input type="text" id="con-search"
											class="form-control" placeholder="이름검색"></th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>

				</div>
			</section>
		</div>


		<%@ include file="/WEB-INF/views/include/html-footer.jsp"%>
		<%@ include file="/WEB-INF/views/include/html-rightAside.jsp"%>
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

	$("#not-members,#con-members").on("click","tr.list-tr input",function(e){
		e.preventdefault();
	});
	//$("#class_id").select2();
	$("#not-members").on("click","tr.list-tr",function(){
		trClick("#44b6d9",this);
	});
	$("#con-members").on("click","tr.list-tr",function(){
		trClick("#ed9c2a",this);
	});
	$("#auth_ename,#class_id").change(getMemberList);
	$("#btnInsert").click(insertAuth);
	$("#btnDel").click(delAuth);
	$("#not-allCheck").click(function(){
		$("table#not-members>tbody>tr").each(function(){
			trClick("#44b6d9",this);
		});
	});
	$("#con-allCheck").click(function(){
		$("table#con-members>tbody>tr").each(function(){
			trClick("#ed9c2a",this);
		});
	});
	$("#not-search").keydown(function(e){
		if(e.which == 13){
			getTable($("#not-members"),e.target.value.toUpperCase());
		}
	});
	$("#con-search").keydown(function(e){
		if(e.which == 13){
			getTable($("#con-members"),e.target.value.toUpperCase());
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
				chkbox = $("input[name='m_id']",tr);
				chkbox.prop("checked", false);
				$(tr).hide();
			}
		});
	}
	function insertAuth(){
		obj = $("#not-members  [name='m_id']:checked");
		arr =[];
		obj.each(function(i){
			arr.push($(this).val());
		});
		if(arr.length>0){
			$.ajax({
		        url:"<%=rootPath%>/admin/classJoinMem/insert",
		        type:'post',
		        data: {
	        		m_id : arr,
	        		class_id : $("#class_id").val(),
	        		auth_ename : $("#auth_ename").val()
		        	},
		        success: function(json){
		        	if(json.data>0) getMemberList();
		        },
		        error : function(request, status, error) { 
		        	//alert(okText+"내용을 확인해주세요");
		            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		        
		    });
		}		
	}
	function delAuth(){
		obj = $("#con-members  [name='m_id']:checked");
		arr =[];
		obj.each(function(i){
			arr.push($(this).val());
		});
		console.log($.param({m_id : arr,auth_ename : $("#auth_ename").val()}));
		if(arr.length>0){
			$.ajax({
		        url:"<%=rootPath%>/admin/classJoinMem/delete",
		        type:'post',
		        data: {
		        	m_id : arr,
	        		class_id : $("#class_id").val(),
					auth_ename : $("#auth_ename").val()
				},
		        success: function(json){
		        	if(json.data>0) getMemberList();
		        },
		        error : function(request, status, error) { 
		        	//alert(okText+"내용을 확인해주세요");
		            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		        
		    });
		}		
		
	}
	function trClick(color,obj){
		chkbox = $("input[name='m_id']",obj);
		chkbox.prop("checked", !chkbox.is(":checked"));
		if(chkbox.is(":checked")){
			$(obj).css("background-color",color);
		}else{
			$(obj).css("background-color","#fff");
		}
		console.log(chkbox.is(":checked"));
	}
	function getMemberList(){
		$.ajax({
	        url:"<%=rootPath%>/admin/classJoinMem",
								type : 'post',
								data : {
									class_id : $("#class_id").val(),
									auth_ename : $("#auth_ename").val()
								},
								success : function(json) {
									notTag = "";
									conTag = "";
									$(json.data)
											.each(
													function(i, item) {
														if (item.class_id == 0) {
															notTag += "<tr class='list-tr'>";
															notTag += "<td><input type='checkbox' name='m_id' value='"+item.m_id+"' readonly/></td>";
															notTag += "<td>"
																	+ item.m_name
																	+ "</td>";
															notTag += "</tr>";
														} else {
															conTag += "<tr class='list-tr'>";
															conTag += "<td><input type='checkbox' name='m_id' value='"+item.m_id+"'readonly/></td>";
															conTag += "<td>"
																	+ item.m_name
																	+ "</td>";
															conTag += "</tr>";
														}
													});
									$("table#not-members>tbody").empty()
											.append(notTag);
									$("table#con-members>tbody").empty()
											.append(conTag);

								},
								error : function(request, status, error) {
									//alert(okText+"내용을 확인해주세요");
									alert("code : " + request.status
											+ "\r\nmessage : "
											+ request.reponseText);
								}

							});

				}
				getMemberList();

			});
		</script>

	</div>
</body>
</html>