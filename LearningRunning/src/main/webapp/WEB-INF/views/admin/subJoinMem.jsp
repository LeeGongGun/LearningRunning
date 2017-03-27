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
<title>권한 관리</title>
<script type="text/javascript">
$(function(){

	$("#not-members,#con-members").on("click","tr.list-tr input",function(e){
		e.preventdefault();
	});
	$("#subject_id").select2();
	$("#not-members").on("click","tr.list-tr",function(){
		trClick("#44b6d9",this);
	});
	$("#con-members").on("click","tr.list-tr",function(){
		trClick("#ed9c2a",this);
	});
	$("#auth_ename,#subject_id").change(getMemberList);
	$("#btnInsert").click(insertAuth);
	$("#btnDel").click(delAuth);
	function insertAuth(){
		obj = $("#not-members  [name='m_id']:checked");
		arr =[];
		obj.each(function(i){
			arr.push($(this).val());
		});
		if(arr.length>0){
			$.ajax({
		        url:"<%=rootPath%>/admin/subJoinMem/insert",
		        type:'post',
		        data: {
	        		m_id : arr,
	        		subject_id : $("#subject_id").val(),
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
		        url:"<%=rootPath%>/admin/subJoinMem/delete",
		        type:'post',
		        data: {
		        	m_id : arr,
	        		subject_id : $("#subject_id").val(),
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
	        url:"<%=rootPath%>/admin/subJoinMem",
	        type:'post',
	        data: {
        		subject_id : $("#subject_id").val(),
	        	auth_ename: $("#auth_ename").val()
	        },
	        success: function(json){
	        	notTag = "";
	        	conTag = "";
				$(json.data).each(function(i,item){
					if (item.subject_id==0) {
						notTag +="<tr class='list-tr'>";
						notTag +="<td><input type='checkbox' name='m_id' value='"+item.m_id+"' readonly/></td>";
						notTag +="<td>"+item.m_name+"</td>";
						notTag +="</tr>";						
					} else {
						conTag +="<tr class='list-tr'>";
						conTag +="<td><input type='checkbox' name='m_id' value='"+item.m_id+"'readonly/></td>";
						conTag +="<td>"+item.m_name+"</td>";
						conTag +="</tr>";
					}
				});
				$("table#not-members>tbody").empty().append(notTag);
				$("table#con-members>tbody").empty().append(conTag);
	        		
	        },
	        error : function(request, status, error) { 
	        	//alert(okText+"내용을 확인해주세요");
	            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
	        } 
	        
	    });
		
	}
	getMemberList();
	
});
</script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<div class="main"><div class="main-div">
	<h3 class="sub-title">반(과정)-학생,선생 등록</h3>
	<div class="form-inline">
  		<select   class="form-control " name="auth_ename" id="auth_ename">
  			<option value="student">학생</option>
  			<option value="teacher">선생님</option>
  		</select>
  		<select   class="form-control " name="subject_id" id="subject_id">
			<c:forEach var="subject" items="${subjectList}">
  			<option value="${subject.subject_id }">${subject.subject_name}-${subject.subject_state}</option>
			</c:forEach>
  		</select>
		
	</div>
<div class="search-table row">
	<div class="col-sm-5">
		<table  class="table table-striped table-bordered" cellspacing="0" width="100%" id="not-members">
			<thead>
			<tr>

				<th>번호</th>
				<th>이름</th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<div class="col-sm-2">
		<button class="btn btn-info btn-block" id="btnInsert">입력  <span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span></button><br/>
		<button class="btn btn-warning btn-block" id="btnDel"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>  삭제</button>
	</div>
	<div class="col-sm-5">
		<table  class="table table-striped table-bordered" cellspacing="0" width="100%" id="con-members">
			<thead>
			<tr>

				<th>번호</th>
				<th>이름</th>
			</tr>
			</thead>
			<tbody>
			</tbody>
		</div>
	</div>
</div>	
	

</div></div>
<%@ include file="/WEB-INF/views/include/foot.jsp" %>
</body>
</html>