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
<title>성적관리</title>
<%@ include file="/WEB-INF/views/include/html-head.jsp"%>
<style type="text/css">
.hover-td{cursor: pointer;}
.changed{background-color: Yellow}
.newScore{background-color: #b0eaff;}
input.score{width: 100%;height: 40px;border: 0px;}
.search-th{width: 200px}
td.input-td{padding:0px !important;}
.search-div{ }
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
        성적관리
        <small>성적관리</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">성적관리</li>
      </ol>
    </section>
			<section class="content">
				<div class="row">
					<div class="col-xs-12">
						<div class="box box-success form-inline">
							<select class="form-control " name="class_id"
								id="class_select_id">
								<option value="">반을 선택하세요.</option>
								<c:forEach var="classes" items="${classList}">
									<option value="${classes.class_id }">${classes.class_name}-${classes.class_state}</option>
								</c:forEach>
							</select> <select class="form-control " name="exam_id"
								id="exam_select_id">
								<option value="">시험을 선택하세요.</option>
							</select>
							<button id="allInsert" class="btn  btn-primary btn-sm">신규
								입력</button>
							<button id="allEdit" class="btn  btn-warning btn-sm">수정</button>
							<button id="allDel" class="btn  btn-danger  btn-sm">모두
								삭제</button>
							<button id="allClear" class="btn  btn-default  btn-sm">
								모두<input id="setNum" type="number" min=0 max=100 step=10
									value='0' style="width: 50px; height: 16px;" />
							</button>
							<div class="box box-primary">
								<table class="table table-striped table-bordered table-hover"
									id="sub-table" cellspacing="0" width="100%">
									<thead>
										<tr>
											<th class="search-th"><input type="text"
												class="form-control" id="searchText" name="searchText"
												placeholder="검색"></th>
										</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
									</tfoot>
								</table>
							</div>
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
		bar = null;
		members = [];
			$.ajax({
		        url:"<%=rootPath%>/admin/classJoinMem",
		        type:'post',
		        data: {
		        	class_id : $("#class_select_id").val(),
		        	auth_ename : 'student',
		        	},
		        success: function(json){
		        	members = json.data;
		        },
		        error : function(request, status, error) { 
		        	//alert(okText+"내용을 확인해주세요");
		            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		        
		    });
	}
	var subjects = [];
	function getsubjects(){
		bar = null;
		subjects = [];
			$.ajax({
		        url:"<%=rootPath%>/admin/examSubject",
		        type:'post',
		        data: {
		        	class_id : $("#class_select_id").val(),
		        	exam_id : $("#exam_select_id").val(),
		        	},
		        success: function(json){
		        	subjects = json.data;
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
			if(item.exam_id>0) headTag.append("<th>"+item.subject_title+"</th>");
		});
		headTag.append($("<th/>",{"class":"total-th",text:"총합"}));
		headTag.append($("<th/>",{"class":"sum-th",text:"평균"}));
		$("#sub-table>thead").empty().append(headTag);
		
		
		footTag = $("<tr/>");
		footTag.append("<th>과목 평균</th>");
		$(subjects).each(function(i,item){
			if(item.exam_id>0) footTag.append($("<th/>",{"class":"subAvg-th"}));
		});
		footTag.append($("<th/>",{"class":"total-th",text:"총합"}));
		footTag.append($("<th/>",{"class":"sum-th",text:"평균"}));
		$("#sub-table>tfoot").empty().append(footTag);
		
		
		conTag = $("#sub-table>tbody").empty();
		$(members).each(function(i,member){
			if(member.class_id>0){
				memTag = $("<tr/>",{"class":"list-tr"});
				memTag.append("<td class='m-name'>"+member.m_name+"</td>");
				$(subjects).each(function(j,subject){
					inputTag = $("<input/>",{
						"class":"score newScore",
						"type":'number',
						"step":10,
						"min":0,
						"max":100,
						"data-class_id":$("#class_select_id").val(),
						"data-m_id":member.m_id,
						"data-exam_id":$("#exam_select_id").val(),
						"data-subject_id":subject.subject_id,
						
						//"value":tmp
					});
					if(subject.exam_id>0) {
						memTag.append($("<td/>",{"class":"input-td"}).append(inputTag));
					}
				});
				memTag.append($("<th/>",{"class":"sum-th"}));
				memTag.append($("<th/>",{"class":"avg-th"}));
				conTag.append(memTag);
			}
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
	$("#class_select_id").change(getExamList);
	$("#exam_select_id").change(getsubjects);
	
	
	
	function getExamList(){
		if($("#class_select_id").val()!=""){
			$.ajax({
		        url:"<%=rootPath%>/admin/exam",
		        type:'post',
		        data: {class_id: $("#class_select_id").val()},
		        success: function(json){
		        	conTag = "<option value=\"\">시험을 선택하세요. </option>";
					$(json.data).each(function(i,item){
							conTag +="<option value=\""+item.exam_id+"\">"+item.exam_title+"</option>";
					});
					$("#exam_select_id").empty().append(conTag);		        		
		        },
		        error : function(request, status, error) { 
		            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		    });
		}else{
			$("#exam_select_id").empty().append("<option value=\"\">시험을 선택하세요. </option>");	
		}
		getMembers();			

	}
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
	        url:"<%=rootPath%>/teacher/score",
	        type:'post',
	        data: {
        		exam_id: $("#exam_select_id").val(),
	        },
	        success: function(json){			
	        		$(json.data).each(function(i,item){
	        			$("#sub-table input[data-exam_id='"+item.exam_id+"']"
	        			+"[data-m_id='"+item.m_id+"']"
	        			+"[data-subject_id='"+item.subject_id+"']"
	        			).val(item.score).attr("data-score",item.score).removeClass("newScore changed");
	        		});
	        		setSum();
      },
	        error : function(request, status, error) { 
	            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
	        } 
	        
	    });
		
	}
	$("#allInsert").click(allInsert);
	$("#allEdit").click(allEdit);
	$("#allClear").click(allClear);
	$("#allDel").click(allDel);
	function allInsert(){
		obj = $("#sub-table input.newScore");
		exam_ids =[];
		m_ids =[];
		subject_ids =[];
		scores =[];
		obj.each(function(i,item){
			exam_ids.push($(this).data("exam_id"));
			m_ids.push($(this).data("m_id"));
			subject_ids.push($(this).data("subject_id"));
			scores.push(($(this).val()=="")?0:$(this).val());
		});
		if(exam_ids.length>0 
				&& exam_ids.length == m_ids.length 
				&& exam_ids.length == subject_ids.length  
				&& exam_ids.length == scores.length ){
			$.ajax({
		        url:"<%=rootPath%>/teacher/score/insert",
		        type:'post',
		        data: {
	        		exam_id: exam_ids,
		        	m_id: m_ids ,
		        	subject_id: subject_ids ,
		        	score: scores ,
		        	
		        },
		        success: function(json){
		        	alert(json.data+"건 입력하였습니다.");
		        	getScoreList();
		        		
		        },
		        error : function(request, status, error) { 
		            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		        
		    });
		}
	}
	function allEdit(){
		obj = $("#sub-table input.changed").not(".newScore");
		exam_ids =[];
		m_ids =[];
		subject_ids =[];
		scores =[];
		obj.each(function(i,item){
			exam_ids.push($(this).attr("data-exam_id"));
			m_ids.push($(this).attr("data-m_id"));
			subject_ids.push($(this).attr("data-subject_id"));
			scores.push(($(this).val()=="")?0:$(this).val());
		});
		console.log(exam_ids.length);
		if(exam_ids.length>0 
				&& exam_ids.length == m_ids.length 
				&& exam_ids.length == subject_ids.length  
				&& exam_ids.length == scores.length ){
			$.ajax({
		        url:"<%=rootPath%>/teacher/score/update",
		        type:'post',
		        data: {
	        		exam_id: exam_ids,
		        	m_id: m_ids ,
		        	subject_id: subject_ids ,
		        	score: scores ,
		        	
		        },
		        success: function(json){
		        	alert(json.data+"건 수정하였습니다.");
		        	getScoreList();
		        		
		        },
		        error : function(request, status, error) { 
		            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
		        } 
		        
		    });
		}
	}
	function allDel(){
		obj = $("#sub-table input.score[data-score]");
		tmp=0;
		obj.each(function(i,item){
			tmp++;
		});
		console.log(tmp);
		if(tmp>0){
			if(confirm(tmp+"건을 삭제하시겠습니까?")){
				$.ajax({
			        url:"<%=rootPath%>/teacher/score/delete",
			        type:'post',
			        data: {
		        		exam_id: $("#exam_select_id").val(),		        	
			        },
			        success: function(json){
			        	alert(json.data+"건 삭제하였습니다.");
			        	setTable();
			        		
			        },
			        error : function(request, status, error) { 
			            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
			        } 
			        
			    });
			}
		}
	}
	function allClear(){
		$("#sub-table input.score").val($("#setNum").val()).each(function(){isEqualScore(this)});
	}
	function isEqualScore(obj){
		val = $(obj).val();
		Rval = $(obj).data("score");
		if(val!=Rval) $(obj).addClass("changed");
		else $(obj).removeClass("changed");
		setSum();
	}
	function setSum(){
		subAvg = [];
		subCnt = 0;
		$("#sub-table tbody>tr").each(function(i,tr){
			
			sum=0;
			cnt=0;
			$("input.score",this).each(function(j,item){
				if(subAvg[j]==null){
					subAvg.push(Number($(item).val()));
				}else{
					subAvg[j] += Number($(item).val());
				}
				sum+=Number($(item).val());
				cnt++;
			});
			avg=(cnt>0)?sum/cnt:0;
			$(".sum-th",tr).html($("<span/>",{"text":sum}));
			$(".avg-th",tr).html(avg.toFixed(2));
			subCnt++;
		});
		if(subCnt>0){
			$(subAvg).each(function(i,item){
				$(".subAvg-th:eq("+i+")").text((subAvg[i]/subCnt).toFixed(2));
			});
		}


		}
});
</script>

</div>
</body>
</html>