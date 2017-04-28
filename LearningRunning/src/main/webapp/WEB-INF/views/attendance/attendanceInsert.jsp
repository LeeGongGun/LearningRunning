<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String fileDir = request.getRealPath("/resources/uploads/");
	String rootPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>출결 관리</title>
<%@ include file="/WEB-INF/views/include/html-head.jsp"%>
<style type="text/css">
.attend_status{display: none;position: absolute;left:0;top: 0; background-color: #fff;border: 1px solid #000;}
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
					출결 관리 <small>출결 입력</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li class="active">출결 관리</li>
				</ol>
			</section>
			<section class="content">
				<div class="row">
					<div class="col-xs-12">
						<div class="box box-success form-inline">
							<form id="searchFrm">
								<!--<select class="form-control " name="status" id="status">
									<option value="START">출석</option>
									<option value="END">퇴교</option>
									<option value="STOP">외출</option>
									<option value="RESTART">복귀</option>
									<option value="CHECK">중간확인</option>
								</select>-->
								 <select class="form-control " name="class_id"
									id="class_select_id">
									<option value="">반을 선택하세요.</option>
									<c:forEach var="classes" items="${classList}">
										<option value="${classes.class_id }">${classes.class_name}</option>
									</c:forEach>
								</select> <input type="text" class="form-control " name="attendDate"
									id="attendDate" readonly="readonly">
								<button type="button" class="btn btn-primary"
									id="attendInsertBtn">입력</button>
								<button type="button" class="btn btn-primary"
									id="todayInsertBtn">일일 결과 입력</button>
							</form>
						</div>
						<div class="box box-primary">
							<form:form id="attendFrm" class="form-inline"
								enctype="multipart/form-data">
								<input type="hidden" id="state" name="state">
								<input type="hidden" id="time" name="time">
								<table class="table table-bordered table-hover"
									cellspacing="0" width="100%" id="temp-attend">
									<thead>
										<tr>

											<th class="col-lg-1 col-xs-1"><a href="javascript:;"
												class="btn btn-default btn-sm" id="con-allCheck">반전하기</a></th>
											<th colspan="2"><input type="text" id="not-search"
												class="form-control" placeholder="이름,email 검색"></th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</form:form>
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
	$("#attendDate").datepicker({
		format: "yyyy/mm/dd",
		enableOnReadonly: false,
		beforeShowDay: function() {
		      return false;
		}
	}).datepicker('update',new Date());
	$("#class_select_id,#status").change(function(){
		getMembers();
	});
	var members = [];
	var attends = [];
	var $table=$("#temp-attend");
	$table.on("click","tr.list-tr.active",function(){
		trClick("#44b6d9",this);
	});
	$("#con-allCheck").click(function(){
		$table.find("tbody>tr").each(function(){
			if($(this).is(':visible')) trClick("#44b6d9",this);
		});
	});
	$("#attendInsertBtn").click(attendInsert);
	$("#todayInsertBtn").click(attendConfirm);
	
	function getMembers(){
		$class_id = $("#class_select_id");
		members = [];
			$.ajax({
		        url:"<%=rootPath%>/admin/classMembers",
		        type:'post',
		        data: {
		        	class_id : $class_id.val(),
		        	auth_ename : 'student',
		        	},
		        success: function(json){
		        	members = json.data;
		        },
		        error : function(request, status, error) { 
		        	//alert(okText+"내용을 확인해주세요");
		            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        }, 
		        complete: function(){
		        	getTempAttendList();
		        }
		        
		    });
	}	
	function getTempAttendList(){
		$class_id = $("#class_select_id");
		if($class_id.val()!=""){
			$.ajax({
		        url:"<%=rootPath%>/admin/tempAttend",
		        type:'post',
		        data: {
		        	class_id: $class_id.val(),
		        	//status: $("#status").val(),
		        	temp_date: $("#attendDate").val()
		        	},
		        success: function(json){
		        	authTag = "";
		        	attends = json.data;
					$(members).each(function(i,item){
							authTag +="<tr class='list-tr active' data-m_id='"+item.m_id+"'>";
							authTag +="<td><input type='checkbox' name='m_id' value='"+item.m_id+"' readonly/></td>";
							authTag +="<td>"+item.m_name+"</td>";
							authTag +="</tr>";
					});
					$("table#temp-attend>tbody").empty().append(authTag);
		        		
		        },
		        error : function(request, status, error) { 
		        	//alert(okText+"내용을 확인해주세요");
		            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        }, 
		        complete: function(){
		        	setMemDesabled();
		        }
		        
		    });
		}
		
	}
	function setMemDesabled(){
		$(attends).each(function(i,item){
			$tr=$table.find("tr[data-m_id='"+item.m_id+"']")
			$tr.removeClass("active");
			$tr.data("toggle","popover");
			$tr.data("placement","bottom");
			$tr.attr("title","출결:"+item.attend_status);
			$tr.data("content",
					"출석:"+item.start_time
					+"<br/>"+"퇴교:"+item.end_time
					+"<br/>"+"외출:"+item.stop_time
					+"<br/>"+"재입장:"+item.restart_time
					);
			$tr.popover({container: $table,trigger:"hover",html:true});
		});
	}
	function getTableList(sText){
		$table.find("tbody>tr").each(function(){
			tr = this;
			sum = 0;
			$("td",tr).each(function(i){
				if(i!=0) return true;
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
	function trClick(color,obj){
		chkbox = $("input[name='m_id']",obj);
		chkbox.prop("checked", !chkbox.is(":checked"));
		if(chkbox.is(":checked")){
			$(obj).css("background-color",color);
		}else{
			$(obj).css("background-color","#fff");
		}
	}
	function attendInsert(){
		obj = $table.find("tr.active").find("input[name='m_id']:checked");
		arr =[];
		obj.each(function(i){
			arr.push($(this).val());
		});
		if(arr.length>0){
			if(confirm(arr.length+"건  입력하시겠습니까?")){
			$.ajax({
		        url : "<%=rootPath %>/admin/tempAttend/insert",
				type : 'post',
				data : {
					m_id : arr,
					class_id : $class_id.val(),
					temp_date : $("#attendDate").val(),
					//status : $("#status").val(),
				},
				success : function(json) {
					if (json.data > 0){
						alert(json.data+"건 "+$("#status").val()+" 성공하였습니다.");
						getTempAttendList();
					}
				},
				error : function(request, status, error) {
					//alert(okText+"내용을 확인해주세요");
					alert("code : " + request.status
							+ "\r\nmessage : "
							+ request.reponseText);
				}

			});
			}
		}
	}
	function attendConfirm(){
		$class_id = $("#class_select_id");
		if($class_id.val()!=""){
			if(confirm("임시저장된 출결사항을 확정하시겠습니까?")){
			$.ajax({
		        url : "<%=rootPath %>/admin/tempAttend/attendConfirm",
				type : 'post',
				data : {
					class_id : $class_id.val(),
					temp_date : $("#attendDate").val(),
				},
				success : function(json) {
					if (json.data > 0){
						alert(json.data+"건 "+$("#status").val()+" 성공하였습니다.");
						getTempAttendList();
					}
				},
				error : function(request, status, error) {
					//alert(okText+"내용을 확인해주세요");
					alert("code : " + request.status
							+ "\r\nmessage : "
							+ request.reponseText);
				}

			});
			}
		}
	}
	
});
		</script>
	</div>
</body>
</html>