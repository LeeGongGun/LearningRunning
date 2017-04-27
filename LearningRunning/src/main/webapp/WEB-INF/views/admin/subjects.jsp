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
<title>과목 입력</title>
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
					과목 입력<small>과목 입력</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li class="active">과목 입력</li>
				</ol>
			</section>
			<section class="content">
				<div class="row">
					<div class="col-xs-12">
						<div class="box box-success form-inline">
							<select class="form-control " name="class_select_id"
								id="class_select_id">
								<option value="">반을 선택하세요.</option>
								<c:forEach var="classes" items="${classList}">
									<option value="${classes.class_id }">${classes.class_name}-${classes.class_state}</option>
								</c:forEach>
							</select>
							<button type="button" class="btn btn-default" id="modalOn">
								과목관리</button>
						</div>
					</div>
					<div class="col-sm-5">
						<div class="box box-info">
							<table class="table  table-bordered table-hover" id="not-surbjects">
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
						<button class="btn btn-info btn-block" id="btnJoin">
							연결 <span class="glyphicon glyphicon-arrow-right"
								aria-hidden="true"></span>
						</button>
						<br />
						<button class="btn btn-warning btn-block" id="btnCut">
							<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
							삭제
						</button>
					</div>
					<div class="col-sm-5">
						<div class="box box-warning">
							<table class="table  table-bordered table-hover"
								id="con-surbjects">
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
	</div>
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
					<h4 class="modal-title" id="myModalLabel">과목 입력</h4>
				</div>
				<div class="modal-body" style="min-height: 400px">
					<div class="form-group">
						<div class="col-sm-12">
							<select class="form-control" style="width: 100%"
								name="subject_id" id="modal_subject_id">

							</select>
						</div>
					</div>
					<br />
					<form:form commandName="command" id="editFrm">
						<input type="hidden" id="mode" value="insert">
						<div class="form-group">
							<label for="class_id" class="col-sm-2 control-label">과목명</label>
							<div class="col-sm-10">
								<input type="hidden" class="form-control" id="frm_subject_id"
									name="subject_id" placeholder="아이디"> <input type="text"
									class="form-control" id="frm_subject_title"
									name="subject_title" placeholder="과목명" required="required">
							</div>
						</div>
						<div class="form-group">
							<label for="class_id" class="col-sm-2 control-label">comment</label>
							<div class="col-sm-10">
								<textarea style="min-height: 280px" class="form-control"
									id="frm_subject_comment" name="subject_comment"></textarea>
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
		$("#modalOn").click(function(){
			clearFrm();
			$('#myModal').modal("show");
			$("#frm_subject_title").focus();
		});
		$("#insert").click(function(){ //제목입력
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
				frm.attr("action","<%=rootPath%>/admin/classSubject/insert");
			}else if(mode=="edit"){
				frm.attr("action","<%=rootPath%>/admin/classSubject/edit");
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
		
		$("#delBtn").click(function(){
			console.log(0);
			if($("#modal_subject_id").val()=="" || $("#modal_subject_id").val()==null) return false;
			sText = $("#modal_subject_id  option:selected").text();
			del_id = $("#frm_subject_id").val();
			frm = $("#editFrm");
			if(confirm("과목 '"+sText+"' 을(를) 삭제하시겠습니까?")){
				$.ajax({
			        url:"<%=rootPath%>/admin/classSubject/delete",
			        type:'post',
			        data: {subject_id : del_id},
			        success: function(json){
			        	if(json.data>0) {
			        		alert("삭제성공하였습니다.");
			        		location.reload();
			        	}else if(json.data==0){
			        		alert("연관데이터가 있어서 삭제가 불가능 합니다..");
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
		$("#modal_subject_id").select2().change(function(){
			if($("#modal_subject_id").val()=="" || $("#modal_subject_id").val()==null){ 
				clearFrm();
				return false;
			}else{
				$("#mode").val("edit");
				$("#insert").text("수정");
				$("#delBtn").removeClass("disabled");
				$("#frm_subject_id").val($(this).val());
				$("#frm_subject_title").val($("option:selected",this).text());
				comment = "";
				$("#subject_comments_div>div").each(function(){
					tmp_id = $(this).data("subject_id");
					if(tmp_id==$("#modal_subject_id").val()){
						comment = $(this).text();
					}
				});
				$("#frm_subject_comment").val(comment);		
			}
		});
	
		
		
		function clearFrm(){
			$("#mode").val("insert");
			$("#insert").text("입력");
			$("#delBtn").addClass("disabled");
			$("#frm_subject_id").val("");
			$("#frm_subject_title").val("");
			$("#frm_subject_comment").val("");		
		}
		function getformSubjectList(){
			$.ajax({
		        url:"<%=rootPath%>/admin/classSubject/simple",
		        type:'post',
		        success: function(json){
		        	commentDivTag = "";
		        	optionTag = "<option value=\"\">과목을 선택하세요.</option>";
					$(json.data).each(function(i,item){
						optionTag += "<option value=\""+item.subject_id+"\"> "+item.subject_title+"</option>";
						commentDivTag += "<div data-subject_id=\""+item.subject_id+"\"> "+item.subject_comment+"</div>";
					});
					$("#modal_subject_id").empty().append(optionTag);
					$("#subject_comments_div").empty().append(commentDivTag);
		        		
		        },
		        error : function(request, status, error) { 
		        	//alert(okText+"내용을 확인해주세요");
		            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		        
		    });
		}
		getformSubjectList();
		
		
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
		$("#class_select_id").change(getSubjectList);
		$("#btnJoin").click(joinSubject);
		$("#btnCut").click(cutSubject);
		$("#not-allCheck").click(function(){
			$("table#not-surbjects>tbody>tr").each(function(){
				if($(this).is(':visible')) trClick("#44b6d9",this);
			});
		});
		$("#con-allCheck").click(function(){
			$("table#con-surbjects>tbody>tr").each(function(){
				if($(this).is(':visible')) trClick("#ed9c2a",this);
			});
		});
		$("#not-search").keydown(function(e){
			if(e.which == 13){
				getTable($("#not-surbjects"),e.target.value.toUpperCase());
			}
		});
		$("#con-search").keydown(function(e){
			if(e.which == 13){
				getTable($("#con-surbjects"),e.target.value.toUpperCase());
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
					chkbox = $("input[name='subject_id']",tr);
					chkbox.prop("checked", false);
					$(tr).hide();
				}
			});
		}
	
		
		function joinSubject(){
			obj = $("#not-surbjects  [name='subject_id']:checked");
			arr =[];
			obj.each(function(i){
				arr.push($(this).val());
			});
			if(arr.length>0){
				$.ajax({
			        url:"<%=rootPath%>/admin/classSubject/join",
			        type:'post',
			        data: {
			        	subject_id : arr,
		        		class_id : $("#class_select_id").val()
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
		function cutSubject(){
			obj = $("#con-surbjects  [name='subject_id']:checked");
			arr =[];
			obj.each(function(i){
				arr.push($(this).val());
			});
			console.log($.param({subject_id : arr,auth_ename : $("#auth_ename").val()}));
			if(arr.length>0){
				$.ajax({
			        url:"<%=rootPath%>/admin/classSubject/cut",
			        type:'post',
			        data: {
			        	subject_id : arr,
		        		class_id : $("#class_select_id").val()
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
		}
		function getSubjectList(){
			if( $("#class_select_id").val()!="" ){
				$.ajax({
			        url:"<%=rootPath%>/admin/classSubject",
					type : 'post',
					data : {
						class_id : $("#class_select_id").val()
					},
					success : function(json) {
						notTag = "";
						conTag = "";
						$(json.data).each(function(i, item) {
							if (item.class_id == 0) {
								notTag += "<tr class='list-tr'>";
								notTag += "<td><input type='checkbox' name='subject_id' value='"+item.subject_id+"' readonly/></td>";
								notTag += "<td>"
										+ item.subject_title
										+ "</td>";
								notTag += "</tr>";
							} else {
								conTag += "<tr class='list-tr'>";
								conTag += "<td><input type='checkbox' name='subject_id' value='"+item.subject_id+"'readonly/></td>";
								conTag += "<td>"
										+ item.subject_title
										+ "</td>";
								conTag += "</tr>";
							}
						});
						$("table#not-surbjects>tbody").empty()
								.append(notTag);
						$("table#con-surbjects>tbody").empty()
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
		}
	});
	</script>
</body>
</html>