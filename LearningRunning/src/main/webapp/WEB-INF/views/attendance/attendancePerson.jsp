<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String fileDir = request.getRealPath("/resources/uploads/");
	String rootPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
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
					학생개인별 출석 <small>학생개인별 출석</small>
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
							<form:form commandName="command" id="searchFrm"
								class="form-inline">
								<select name="class_id" id="class_id" class="form-control"
									required>
									<option value="">과정을 선택하세요</option>
									<c:forEach var="classes" items="${cList}">
										<option value="${classes.class_id }">${classes.class_name}</option>
									</c:forEach>
								</select>

								<select name="m_id" id="m_id" class="form-control" required>
									<option value="">학생을 선택하세요</option>
									<c:forEach var="m" items="${authMembers}">
										<option value="${m.m_id }">[ ${m.m_email} ]
											${m.m_name}</option>
									</c:forEach>

								</select>
								<input type="text" name="from" id="from" class="form-control"
									placeholder="from" required readonly />
					~
					<input type="text" name="to" id="to" class="form-control"
									placeholder="to" required readonly />
								</label>
								<input type="button" value="조회" class="btn btn-default"
									id="searchBtn">
							</form:form>
							<table class="table table-striped table-bordered" cellspacing="0"
								width="100%" id="personTotalAttendTable">
								<tr>
									<th><span class="badge bg-green">출석</span> : <span id="attendings">${ClassAttend.attendings }</span></th>
									<th><span class="badge bg-red">결석</span> : <span id="noAttendings">${ClassAttend.noAttendings }</span></th>
									<th><span class="badge bg-yellow">외출</span> : <span id="outings">${ClassAttend.outings }</span></th>
									<th><span class="badge bg-yellow">지각</span> : <span id="lateings">${ClassAttend.lateings }</span></th>
									<th><span class="badge bg-yellow">조퇴</span> : <span id="outComings">${ClassAttend.outComings }</span></th>
								</tr>

							</table>
							<table class="table table-hover table-bordered" cellspacing="0"
								width="100%" id="personAttendTable">
								<thead>
									<tr>
										<th>수강날짜</th>
										<th>입실</th>
										<th>퇴실</th>
										<th>외출</th>
										<th>복귀</th>
										<th>출결</th>
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
				$('.treeview-menu li a[href="' + url.pathname + '"]').parent().addClass(
						'active');
				// Will also work for relative and absolute hrefs
				$('.treeview-menu li a').filter(function() {
					return this.href == url;
				}).parent().parent().parent().addClass('active');
			};
			$.AdminLTE.dinamicMenu();
		</script>
		<script>
$(function() {
	$("#m_id,#class_id").select2();
	$("#m_id").change(getAttendList);
	from = new Date();
	from.setMonth(from.getMonth()-1);
	$("#from")
	.datepicker({
		language:  'ko',
		container: "#datepicker-div",
		format: "yyyy/mm/dd",
		todayHighlight:  true,
        autoclose: true,
        useCurrent: false,
	})
	.datepicker("update", from);
	$("#to")
	.datepicker({
		language:  'ko',
		container: "#datepicker-div",
		format: "yyyy/mm/dd",
		todayHighlight:  true,
        autoclose: true,
        useCurrent: false,
	})
	.datepicker("update", new Date());
	$("#class_id").change(function(){
		$.ajax({
			url: "<%=rootPath%>/attendance/selectStudentList",
			type:'post',
	        data: {class_id : $("#class_id").val()},
	        success: function(json){
	        	conTag = "<option value=\"\">학생을 선택하세요</option>";
				$(json.data).each(function(i,item){
		        	conTag += "<option value=\""+item.m_id+"\"> [ "+item.m_email+" ] "+item.m_name+"</option>";

				});
				$("#m_id").empty().append(conTag).select2("open");
				
	        		
	        },
	        error : function(request, status, error) { 
	        	//alert(okText+"내용을 확인해주세요");
	            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
	        } 
	        
	    });			
	});
	function getAttendList(){
		okCnt = 0;
		mode = $("#mode").val();
		frm = $("#searchFrm");
		$( ':required', frm ).each( function () {
		    if ( $(this).val().trim() == '' ) {
		    	if($(this).is("select")){
		    		$(this).select2("open");
		    	}else{
		    		$(this).focus();
		    	}
		        okCnt++;
		    }
		});
		if(okCnt==0){
			$.ajax({
				url: "<%=rootPath%>/attendance/person",
									type : 'post',
									data : $("#searchFrm").serialize(),
									success : function(json) {
										conTag = "";
										attendings = 0;
										noAttendings = 0;
										outings = 0;
										lateings = 0;
										outComings = 0;
										$(json.data)
												.each(
														function(i, item) {
															switch (item.attend_status) {
															case "출석":badgeClass="bg-green";attendings++;break;
															case "결석":badgeClass="bg-red";noAttendings++;break;
															case "조퇴":badgeClass="bg-yellow";outings++;break;
															case "지각":badgeClass="bg-yellow";lateings++;break;
															case "외출":badgeClass="bg-yellow";outComings++;break;
															default:
																break;
															}
															conTag += "<tr>";
															conTag += "<td>"
																	+ item.attend_date
																	+ "</td>";
															conTag += "<td>"
																	+ ((item.start_time == null) ? ""
																			: item.start_time)
																	+ "</td>";
															conTag += "<td>"
																	+ ((item.end_time == null) ? ""
																			: item.end_time)
																	+ "</td>";
															conTag += "<td>"
																	+ ((item.stop_time == null) ? ""
																			: item.stop_time)
																	+ "</td>";
															conTag += "<td>"
																	+ ((item.restart_time == null) ? ""
																			: item.restart_time)
																	+ "</td>";
																	conTag +="<td><span class='badge "+badgeClass+"'>"+item.attend_status+"</span></td>";
															conTag += "</tr>";

														});
										$("#attendings").text(attendings);
										$("#noAttendings").text(noAttendings);
										$("#outings").text(outings);
										$("#lateings").text(lateings);
										$("#outComings").text(outComings);
										$("#personTotalAttendTable").show();
										$("#personAttendTable>tbody").empty()
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
				$("#personTotalAttendTable").hide();
				$("#searchBtn").click(getAttendList);
				//getAttendList();

			});
		</script>
	</div>
</body>
</html>