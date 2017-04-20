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
<title>member 관리</title>
<%@ include file="/WEB-INF/views/include/html-head.jsp"%>
<style type="text/css">
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
					member 관리<small>member 관리</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li class="active">member 관리</li>
				</ol>
			</section>
			<section class="content">
				<div class="row">
					<div class="col-xs-12">
						<div class="box box-success">
							<button type="button" class="btn btn-primary" id="searchBtn">
						  		모두보기
							</button>
							<button type="button" class="btn btn-primary" id="modalOn">
						  		입력
							</button>
						</div>
						<div class="box box-primary">
							<table  class="table table-bordered table-hover" id="sub-table">
								<thead>
								<tr>
									<th>번호</th>
									<th><input type="text" class="form-control" id="searchText" name="searchText" placeholder="검색"></th>
									<th>email</th>
									<th>비밀번호</th>
									<th>삭제</th>
								</tr>
								</thead>
								<tbody></tbody>
							</table>
						
						</div>
					</div>
				</div>
			</section>
		</div>


		<%@ include file="/WEB-INF/views/include/html-footer.jsp"%>
		<%@ include file="/WEB-INF/views/include/html-rightAside.jsp"%>
	</div>
	<!-- Modal -->
	<div class="modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">member 입력</h4>
	      </div>
	      <div class="modal-body" style="min-height: 150px">
	      <form:form commandName="command" id="editFrm" enctype="multipart/form-data">
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
	      	<div class="form-group">
	      	 	<label for="m_img" class="col-sm-2 control-label">image</label>
	         	<div class="col-sm-10">
	         		<input type="file" class="form-control" id="m_file" name="m_file" placeholder="img">
	         	</div>
	        </div>
	        
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary" id="insert">입력</button>
	        <button type="submit" class="btn btn-primary" id="submit">form</button>
	      </div>
	      </form:form>
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
		$table=$("#sub-table");
		$("#modalOn").click(function(){
			clearFrm();
			$('#myModal').modal("show");
		});

		$('#editFrm').ajaxForm({
            beforeSubmit: function (data,form,option) {
    			mode = $("#mode").val();
    			frm = $("#editFrm");
    			$($(':input[required]', frm ).get().reverse()).each( function () {
    			    if ( $(this).val().trim() == '' ) {
    			        $(this).focus();
    			        return false;
    			    }
    			});
    			okText = "입력";
    			if(mode=='insert'){
    				frm.attr("action","<%=rootPath%>/admin/member/insert");
    			}else if(mode=="edit"){
    				frm.attr("action","<%=rootPath%>/admin/member/edit");
    				okText = "수정";
    				
    			}else{
    				return false;
    			}
    			return true;
            },
            success: function(response,status){
                //성공후 서버에서 받은 데이터 처리
        		alert(okText+"성공하였습니다.");
        		getMemberList();
            },
            error: function(){
	        	alert(okText+"내용을 확인해주세요");
               //에러발생을 위한 code페이지
            }                              
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
				getList($("#searchText").val().toUpperCase());
			}
		});
		$("#searchBtn").click(function(){
			getList("");
		});
		
		$table.on("click",".hover-td",function(){
			if($.isEmptyObject(this)) return false;
			$("#mode").val("edit");
			$("#insert").text("수정");
			$("#m_id").val($(this).prev().text());
			$("#m_name").val($("a",this).text());
			$("#m_email").val($(this).next().text());
			$("#m_pass").val($(this).next().next().text());
			$('#myModal').modal();
	
		});
		$table.on("click",".delBtn",function(){
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
			$("#m_pass").val("1234");
			$("#m_email").val("aaa@naver.com");
		}
		function getList(sText){
			$("#sub-table tbody>tr").each(function(){
				tr = this;
				sum = 0;
				$("td",tr).each(function(i){
					if(i==0 || i==0 || i==0  ) return true;
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
							conTag +="<td>";
							conTag +="<img src=\"<%=rootPath%>/uploads/m_image/";
							
							conTag +=(item.m_image)?item.m_image:"m_image_default.png";
							conTag +="\" alt=\""+item.m_name+"\" class=\"img-circle\" style=\"width:50px;height:50px;\">";
							conTag +="</td>";
							conTag +="<td class=\"hover-td\"><a href=\"javascript:\">"+item.m_name+"</a></td>";
							conTag +="<td>"+item.m_email+"</td>";
							conTag +="<td>"+item.m_pass+"</td>";
							conTag +="<td><button class=\"btn btn-default delBtn\" data-m_id=\""+item.m_id+"\">삭제</button></td>";
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
</body>
</html>