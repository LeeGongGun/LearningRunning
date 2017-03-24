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
		        url:"",
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
	$("#not-members,#auth-members").on("click","tr.list-tr input",function(e){
		e.preventdefault();
	});
	$("#not-members").on("click","tr.list-tr",function(){
		trClick("#44b6d9",this);
	});
	$("#auth-members").on("click","tr.list-tr",function(){
		trClick("#ed9c2a",this);
	});
	$("#auth_ename").change(getAuthList);
	$("#btnInsert").click(insertAuth);
	$("#btnDel").click(delAuth);
	function insertAuth(){
		arr = $("#not-members  [name='m_id']:checked");
		console.log($("#not-members  [name='m_id']:checked").val());
	}
	function delAuth(){
		obj = $("#auth-members  [name='m_id']:checked");
		arr =[];
		obj.each(function(i){
			arr.push($(this).val());
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
		console.log(chkbox.is(":checked"));
	}
	function getAuthList(){
		auth_ename = $("#auth_ename").val();
		$.ajax({
	        url:"<%=rootPath%>/auth",
	        type:'post',
	        data: {auth_ename:auth_ename},
	        success: function(json){
	        	notTag = "";
	        	authTag = "";
				$(json.data).each(function(i,item){
					if ($.isEmptyObject(item.auth_ename)) {
						notTag +="<tr class='list-tr'>";
						notTag +="<td><input type='checkbox' name='m_id' value='"+item.m_id+"' readonly/></td>";
						notTag +="<td>"+item.m_name+"</td>";
						notTag +="<td>"+item.m_email+"</td>";
						usingText = (item.m_app_u_no=="")?"미확인":"확인";
						notTag +="<td>"+usingText+"</td>";
						notTag +="</tr>";						
					} else {
						authTag +="<tr class='list-tr'>";
						authTag +="<td><input type='checkbox' name='m_id' value='"+item.m_id+"'readonly/></td>";
						authTag +="<td>"+item.m_name+"</td>";
						authTag +="<td>"+item.m_email+"</td>";
						usingText = (item.m_app_u_no=="")?"미확인":"확인";
						authTag +="<td>"+usingText+"</td>";
						authTag +="</tr>";
					}
				});
				$("table#not-members>tbody").empty().append(notTag);
				$("table#auth-members>tbody").empty().append(authTag);
	        		
	        },
	        error : function(request, status, error) { 
	        	alert(okText+"내용을 확인해주세요");
	            //alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
	        } 
	        
	    });
		
	}
	getAuthList();
	
});
</script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<div class="main"><div class="main-div">
	<h3 class="sub-title">권한 관리</h3>
	<div class="">
  		<select   class="form-control" style="width: 100%" name="auth_ename" id="auth_ename">
  			<option value="student">학생</option>
  			<option value="teacher">선생님</option>
  			<option value="admin">관리자</option>
  		</select>
		
	</div>
<div class="search-table row">
	<div class="col-sm-5">
		<table  class="table table-striped table-bordered" cellspacing="0" width="100%" id="not-members">
			<thead>
			<tr>

				<th>번호</th>
				<th>이름</th>
				<th>email</th>
				<th>인증</th>
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
		<table  class="table table-striped table-bordered" cellspacing="0" width="100%" id="auth-members">
			<thead>
			<tr>

				<th>번호</th>
				<th>이름</th>
				<th>email</th>
				<th>인증</th>
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