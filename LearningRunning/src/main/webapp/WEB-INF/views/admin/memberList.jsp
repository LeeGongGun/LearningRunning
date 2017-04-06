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
			frm.attr("action","<%=rootPath%>/admin/member/insert");
		}else if(mode=="edit"){
			frm.attr("action","<%=rootPath%>/admin/member/edit");
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
		        		getMemberList();
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
			getList($("#searchText").val());
		}
	});
	
	$(".search-table").on("click",".hover-td",function(){
		if($.isEmptyObject(this)) return false;
		$("#mode").val("edit");
		$("#insert").text("수정");
		$("#m_id").val($(this).prev().text());
		$("#m_name").val($("a",this).text());
		$("#m_email").val($(this).next().text());
		$("#m_pass").val($(this).next().next().text());
		$('#myModal').modal();

	});
	$(".search-table").on("click",".delBtn",function(){
		sId = $(this).parents("tr").find("td:eq(1)").text()
			+"("
			+$(this).parents("tr").find("td:eq(2)").text()
			+")";
			del_id = $(this).data("m_id");
		if(confirm(sId+"님을 삭제하시겠습니까?")){
			$.ajax({
		        url:"<%=rootPath%>/admin/member/delete",
		        type:'post',
		        data: {m_id: del_id},
		        success: function(json){
		        	if(json.data>0) {
		        		alert("삭제성공하였습니다.");
		        		getMemberList();
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
		$("#m_id").val("");
		$("#m_name").val("");
		$("#m_pass").val("");
		$("#m_email").val("");
		
	}
	function getList(sText){
		$("#sub-table tr").each(function(){
			tr = this;
			sum = 0;
			$("td",tr).each(function(){
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
	
	
	function getMemberList(){
		$.ajax({
	        url:"<%=rootPath%>/admin/member",
	        type:'post',
	        data: $("#searchFrm").serialize(),
	        success: function(json){
	        	conTag = "";
				$(json.data).each(function(i,item){
						conTag +="<tr>";
						conTag +="<td>"+item.m_id+"</td>";
						conTag +="<td class=\"hover-td\"><a href=\"javascript:\">"+item.m_name+"</a></td>";
						conTag +="<td>"+item.m_email+"</td>";
						conTag +="<td>"+item.m_pass+"</td>";
						conTag +="<td><button class=\"delBtn\" data-m_id=\""+item.m_id+"\">삭제</button></td>";
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
	getMemberList();
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
        <h4 class="modal-title" id="myModalLabel">member 입력</h4>
      </div>
      <div class="modal-body" style="min-height: 150px">
      <form:form commandName="command" id="editFrm">
      	<input  type="hidden" id="mode" value="insert">
      	<div class="form-group">
      	 	<label for="m_id" class="col-sm-2 control-label">member</label>
         	<div class="col-sm-10">
         		<input type="hidden" class="form-control" id="m_id" name="m_id" placeholder="아이디">
         		<input type="text" class="form-control" id="m_name" name="m_name" placeholder="member명" required="required">
         	</div>
        </div>
      	<div class="form-group">
      	 	<label for="m_email" class="col-sm-2 control-label">email</label>
         	<div class="col-sm-10">
         		<input type="email" class="form-control" id="m_email" name="m_email" placeholder="email" required="required">
         	</div>
        </div>
      	<div class="form-group">
      	 	<label for="m_pass" class="col-sm-2 control-label">password</label>
         	<div class="col-sm-10">
         		<input type="text" class="form-control" id="m_pass" name="m_pass" placeholder="password" required="required">
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
	<h3 class="sub-title">member 관리</h3>
	<div class="search-div form-inline">
	<input type="text" class="form-control" id="searchText" name="searchText" placeholder="검색어">
	<button type="button" class="btn btn-primary" id="searchBtn">
  		검색
	</button>
	<button type="button" class="btn btn-primary" id="modalOn">
  		입력
	</button>
	</div>
	
<div class="search-table">
		<table  class="table table-striped table-bordered table-hover" id="sub-table" cellspacing="0" width="100%">
			<thead>
			<tr>

				<th>번호</th>
				<th>member명</th>
				<th>email</th>
				<th>비밀번호</th>
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