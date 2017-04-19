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
					개인별 성적보기 <small>개인별 성적보기</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li class="active">개인별 성적보기</li>
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

						</div>
						<div class="box box-primary">
							<table class="table table-bordered" id="sub-table">
								<thead>
									<tr>
										<th class="search-th"><input type="text" class="form-control" id="searchText" name="searchText" placeholder="검색"></th>
									</tr>
								</thead>
								<tbody>
								</tbody>
								<tfoot>
								</tfoot>
							</table>
						</div>
						<div class="box box-primary chart-responsive">
							<div class="chart" id="bar-chart" style="height: 300px;"></div>
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
	var members = [];
	var bar = null;
	function getMembers(){
		$class_id = $("#class_select_id");
		bar = null;
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
		        	getSubjects();
		        }
		        
		    });
	}
	var subjects = [];
	function getSubjects(){
		bar = null;
		subjects = [];
			$.ajax({
		        url:"<%=rootPath%>/teacher/memberExamSubject",
		        type:'post',
		        data: {
		        	class_id : $("#class_select_id").val()
		        	},
		        success: function(json){
		        	$(json.data).each(function(i,item){
		        		subjects.push(item);
		        	});
		        },
		        error : function(request, status, error) { 
		        	//alert(okText+"내용을 확인해주세요");
		            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        },
		        complete: function(){
		        	setTable();
		        }
		        
		    });
	}
	function setTable(){
		
		
		headTag = $("<tr/>");
		headTag.append("<th class='search-th'><input type=\"text\" class=\"form-control\" id=\"searchText\" name=\"searchText\" placeholder=\"검색\"></th>");
		$(subjects).each(function(i,item){
			headTag.append("<th>"+item.subject_title+"</th>");
		});
		headTag.append($("<th/>",{"class":"total-th",text:"총합"}));
		headTag.append($("<th/>",{"class":"sum-th",text:"평균"}));
		$("#sub-table>thead").empty().append(headTag);
		
		footTag = $("<tr/>");
		footTag.append("<th>과목 평균</th>");
		$(subjects).each(function(i,item){
			footTag.append($("<th/>",{"class":"subAvg-th"}));
		});
		footTag.append($("<th/>",{"class":"total-th",text:"총합"}));
		footTag.append($("<th/>",{"class":"sum-th",text:"평균"}));
		$("#sub-table>tfoot").empty().append(footTag);
		
		conTag = $("#sub-table>tbody").empty();
		
		$(members).each(function(i,member){
				memTag = $("<tr/>",{"class":"list-tr"});
				memTag.append("<td class='m-name'>"+member.m_name+"</td>");
				$(subjects).each(function(j,subject){
					inputTag = $("<span/>",{
						"class":"score",
						"data-exam_id":subject.exam_id,
						"data-m_id":member.m_id,
						"data-subject_id":subject.subject_id,
						//"value":tmp
						});
					memTag.append($("<td/>",{"class":"input-td"}).append(inputTag));
				});
				memTag.append($("<th/>",{"class":"sum-th"}));
				memTag.append($("<th/>",{"class":"avg-th"}));
				conTag.append(memTag);
		});
		
		getScoreList();
	}
	$("#sub-table").on("keydown","#searchText",function(e){
		if(e.which == 13){
			getTableList($("#searchText").val().toUpperCase());
		}
	});
	$("#searchBtn").click(function(){
		getTableList("");
	});
	$("#sub-table").on("input","input.score",function(){
		isEqualScore(this);
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
	$("#class_select_id").change(getMembers);
	
	
	
	function getTableList(sText){
		$("#sub-table tbody>tr").each(function(){
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
	
	
	function getScoreList(){
		$.ajax({
	        url:"<%=rootPath%>/teacher/memberScore",
						type : 'post',
						data : {
							class_id : $("#class_select_id").val(),
						},
						success : function(json) {
							$(json.data).each(
									function(i, item) {
										$span = $("#sub-table span"
												+ "[data-exam_id='"
												+ item.exam_id + "']"
												+ "[data-m_id='" + item.m_id
												+ "']" + "[data-subject_id='"
												+ item.subject_id + "']");
										$span.text(item.score).attr(
												"data-score", item.score);
									});
							setSum();
						},
						error : function(request, status, error) {
							alert("code : " + request.status + "\r\nmessage : "
									+ request.reponseText);
						}

					});

				}

				function setSum() {
					gData = [];
					ykeys = [];
					labels = [];
					subAvg = [];
					subCnt = 0;
					$("#sub-table tbody>tr")
							.each(
									function(i, tr) {
										dataRow = {
											y : $(".m-name", tr).text()
										};

										sum = 0;
										cnt = 0;
										$("span.score", this)
												.each(
														function(j, item) {
															var subject_id = $(
																	item)
																	.data(
																			"subject_id");
															var subject = $
																	.grep(
																			subjects,
																			function(
																					element,
																					index) {
																				return (element.subject_id === subject_id);
																			});
															subject_title = subject[0].subject_title;
															if (dataRow[j] == null)
																dataRow[j] = Number($(
																		item)
																		.text());
															if (ykeys[j] == null)
																ykeys.push(j);
															if (labels[j] == null)
																labels
																		.push(subject_title);
															if (subAvg[j] == null) {
																subAvg
																		.push(Number($(
																				item)
																				.text()));
															} else {
																subAvg[j] += Number($(
																		item)
																		.text());
															}
															sum += Number($(
																	item)
																	.text());
															cnt++;
														});

										avg = (cnt > 0) ? sum / cnt : 0;
										$(".sum-th", tr).html(sum);
										$(".avg-th", tr).html(avg.toFixed(2));
										subCnt++;
										gData.push(dataRow);
									});
					if (subCnt > 0) {
						$(subAvg).each(
								function(i, item) {
									$(".subAvg-th:eq(" + i + ")").text(
											(subAvg[i] / subCnt).toFixed(2));
								});
					}
					if (bar == null) {
						$("#bar-chart").empty();
						bar = new Morris.Bar({
							element : 'bar-chart',
							resize : true,
							data : gData,
							barColors : [ '#999999', '#245580', '#5cb85c',
									'#46b8da', '#eea236', '#d43f3a' ],
							xkey : 'y',
							ykeys : ykeys,
							labels : labels,
							hideHover : 'auto'
						});
					} else {
						bar.setData(gData);
					}

				}
			});
		</script>

	</div>
</body>
</html>