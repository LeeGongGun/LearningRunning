<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
	$("#attendDate").datepicker({
		format: "yyyy/mm/dd",
		enableOnReadonly: false,
	}).datepicker('update',new Date());
	$("#class_select_id,#status").change(function(){
		getMembers();
	});
	var members = [];
	var attends = [];
	var $table=$("#temp-attend");
	$table.on("click","tr.list-tr",function(){
		trClick("#44b6d9",this);
	});
	$("#con-allCheck").click(function(){
		$table.find("tbody>tr").each(function(){
			if($(this).is(':visible')) trClick("#44b6d9",this);
		});
	});
	$("#attendInsertBtn").click(attendInsert);
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
		        	status: $("#status").val()
		        	},
		        success: function(json){
		        	authTag = "";
		        	attends = json.data;
					$(members).each(function(i,item){
							authTag +="<tr class='list-tr' data-m_id='"+item.m_id+"'>";
							authTag +="<td><input type='checkbox' name='m_id' value='"+item.m_id+"' readonly/></td>";
							authTag +="<td>"+item.m_name+"("+item.m_email+")</td>";
							authTag +="</tr>";
					});
					$("table#temp-attend>tbody").empty().append(authTag);
		        		
		        },
		        error : function(request, status, error) { 
		        	//alert(okText+"내용을 확인해주세요");
		            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        }, 
		        complete: function(){
		        	setAttend();
		        }
		        
		    });
		}
		
	}
	function setAttend(){
		$(attends).each(function(i,item){
			tr = $table.find("tr[data-m_id='"+item.m_id+"']");
			trClick("#44b6d9",tr);
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
		obj = $table.find("input[name='m_id']:checked");
		arr =[];
		obj.each(function(i){
			arr.push($(this).val());
		});
		if(arr.length>0){
			$.ajax({
		        url:"<%=rootPath%>/admin/tempAttend/insert",
		        type:'post',
		        data: {
		        		m_id : arr,
		        		class_id : $class_id.val(),
		        		status : $("#status").val(),
		        	},
		        success: function(json){
		        	if(json.data>0) getTempAttendList();
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
.m_no{width:auto;}
</style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<div class="main"><div class="main-div">
	<h3 class="sub-title">출결 수정</h3> <span id="today"></span>
	<div class="search-div form-inline">
		<form id="searchFrm">
			<select  class="form-control "  name="status" id="status">
				<option value="START">출석 </option>
				<option value="END">퇴교 </option>
				<option value="STOP">외출 </option>
				<option value="RESTART">복귀 </option>
				<option value="CHECK">중간확인 </option>
			</select>
			<select  class="form-control "  name="class_id" id="class_select_id">
				<option value="">반을 선택하세요. </option>
				<c:forEach var="classes" items="${classList}">
	  			<option value="${classes.class_id }">${classes.class_name}</option>
				</c:forEach>
			</select>
			<input type="text" class="form-control " name="attendDate" id="attendDate" readonly>
	<button type="button" class="btn btn-primary" id="attendInsertBtn">
  		입력
	</button>
	</form>
	</div>


<div class="search-table">
	<form:form id="attendFrm" class="form-inline" enctype="multipart/form-data">
		<input type="hidden" id="state" name="state">
		<input type="hidden" id="time" name="time">
		<table  class="table table-striped table-bordered" cellspacing="0" width="100%" id="temp-attend">
			<thead>
			<tr>

				<th><a href="javascript:;" class="btn btn-default btn-sm" id="con-allCheck">반전하기</a></th>
				<th colspan="2"><input type="text" id="not-search" class="form-control" placeholder="이름,email 검색"></th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
  	</form:form>
</div>	

</div></div>
<%@ include file="/WEB-INF/views/include/foot.jsp" %>
</body>
</html>