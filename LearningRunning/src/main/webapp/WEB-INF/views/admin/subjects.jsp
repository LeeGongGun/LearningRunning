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
<title>커리큘럼 입력</title>
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
		$($(':input[required]', frm ).get().reverse()).each( function () {
		    if ( $(this).val().trim() == '' ) {
		        $(this).focus();
		        okCnt++;
		        return;
		    }
		});
		okText = "입력";
		if(mode=='insert'){
			frm.attr("action","<%=rootPath%>/admin/curri/insert");
		}else if(mode=="edit"){
			frm.attr("action","<%=rootPath%>/admin/curri/edit");
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
		        		getCurriList();
		        	}
		        		
		        },
		        error : function(request, status, error) { 
		        	alert(okText+"내용을 확인해주세요");
		            //alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		        
		    });
		};
		
	});
	
	$("#editBtn").click(function(){
		if($.isEmptyObject($("#cur_select_id").val())) return false;
		$("#mode").val("edit");
		$("#insert").text("수정");
		$("#cur_id").val($("#cur_select_id").val());
		$("#cur_name").val($("#cur_select_id  option:selected").text());
		$('#myModal').modal();

	});
	$("#delBtn").click(function(){
		if($.isEmptyObject($("#cur_select_id").val())) return false;
		sId = $("#cur_select_id  option:selected").text();
		del_id = $("#cur_select_id").val();
		if(confirm("커리큘럼 '"+sId+"' 을(를) 삭제하시겠습니까?")){
			$.ajax({
		        url:"<%=rootPath%>/admin/curri/delete",
		        type:'post',
		        data: {cur_id : del_id},
		        success: function(json){
		        	if(json.data>0) {
		        		alert("삭제성공하였습니다.");
		        		getCurriList();
		
		        	}
		        		
		        },
		        error : function(request, status, error) { 
		        	alert(sId+"번 삭제 실패\n문제가 있습니다.");
		            //alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        },
		        complete : function(event, xhr, settings) {
		        	
		        }
		        
		    });
		}
	});
	function clearFrm(){
		$("#mode").val("insert");
		$("#insert").text("입력");
		$("#cur_id").val("");
		$("#cur_name").val("");
		$("#cur_pass").val("");
		$("#cur_email").val("");
		
	}
	
	
	function getCurriList(){
		$.ajax({
	        url:"<%=rootPath%>/admin/curri",
	        type:'post',
	        success: function(json){
	        	conTag = "<option value=\"\">커리큘럼을 선택하세요</option>";
				$(json.data).each(function(i,item){
		        	conTag += "<option value=\""+item.cur_id+"\"> "+item.cur_name+"</option>";

				});
				$("#cur_select_id").empty().append(conTag).select2();
				console.log(1);
				clearFrm();
				$('#myModal').modal("hide");
        		return true;
	        },
	        error : function(request, status, error) { 
	        	//alert(okText+"내용을 확인해주세요");
	            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
	        } 
	        
	    });
		
	}
	$("#not-surbjects,#con-surbjects").on("click","tr.list-tr input",function(e){
		e.preventdefault();
	});
	$("#editFrm").submit(function(e){
		e.preventDefault();//어떤식이든 submit 이벤트는 금지한다
	});
	$("#not-surbjects").on("click","tr.list-tr",function(){
		trClick("#44b6d9",this);
	});
	$("#con-surbjects").on("click","tr.list-tr",function(){
		trClick("#ed9c2a",this);
	});
	$("#cur_select_id").change(getSubjectList);
	$("#btnInsert").click(insertSubject);
	$("#btnDel").click(delSubject);
	function insertSubject(){
		obj = $("#not-surbjects  [name='subject_id']:checked");
		arr =[];
		obj.each(function(i){
			arr.push($(this).val());
		});
		if(arr.length>0){
			$.ajax({
		        url:"<%=rootPath%>/admin/curriSubject/insert",
		        type:'post',
		        data: {
		        	subject_id : arr,
	        		cur_id : $("#cur_select_id").val()
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
	function delSubject(){
		obj = $("#con-surbjects  [name='subject_id']:checked");
		arr =[];
		obj.each(function(i){
			arr.push($(this).val());
		});
		console.log($.param({subject_id : arr,auth_ename : $("#auth_ename").val()}));
		if(arr.length>0){
			$.ajax({
		        url:"<%=rootPath%>/admin/curriSubject/delete",
		        type:'post',
		        data: {
		        	subject_id : arr,
	        		cur_id : $("#cur_select_id").val()
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
		console.log(chkbox.is(":checked"));
	}
	function getSubjectList(){
		if( $("#cur_select_id").val()!="" ){
			$.ajax({
		        url:"<%=rootPath%>/admin/curriSubject",
		        type:'post',
		        data: {
	        		cur_id : $("#cur_select_id").val()
		        },
		        success: function(json){
		        	notTag = "";
		        	conTag = "";
					$(json.data).each(function(i,item){
						if (item.cur_id==0) {
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
	getCurriList();
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
        <h4 class="modal-title" id="myModalLabel">커리큘럼 입력</h4>
      </div>
      <div class="modal-body" style="min-height: 150px">
      <form:form commandName="command" id="editFrm">
      	<input  type="hidden" id="mode" value="insert">
      	<div class="form-group">
      	 	<label for="cur_id" class="col-sm-2 control-label">커리큘럼명</label>
         	<div class="col-sm-10">
         		<input type="hidden" class="form-control" id="cur_id" name="cur_id" placeholder="아이디">
         		<input type="text" class="form-control" id="cur_name" name="cur_name" placeholder="커리큘럼명" required="required">
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
	<div>
		<h3 class="sub-title">커리큘럼 관리</h3>
		<div class="search-div form-inline">
			<select name="cur_select_id" id="cur_select_id">
				<option value="">커리큘럼을 선택하세요.</option>
			</select>
			<button type="button" class="btn btn-primary" id="delBtn">
		  		curri삭제
			</button>
			<button type="button" class="btn btn-default" id="editBtn">
		  		curri수정
			</button>
			<button type="button" class="btn btn-default" id="modalOn">
		  		curri입력
			</button>
		</div>
	</div>
<div class="search-table row">
	<div class="col-sm-5">
		<table  class="table table-striped table-bordered col-sm-12" cellspacing="0" width="100%" id="not-surbjects">
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
		<table  class="table table-striped table-bordered col-sm-12"  id="con-surbjects">
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
</div>	
</div>
</div>
<%@ include file="/WEB-INF/views/include/foot.jsp" %>
</body>
</html>