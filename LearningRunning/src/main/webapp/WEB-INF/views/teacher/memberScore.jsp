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
<link rel="stylesheet" href="<%=request.getContextPath()%>/webjars/adminlte/2.3.11/plugins/morris/morris.css">

<title>입력</title>
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
		        url:"<%=rootPath%>/admin/classSubject",
		        type:'post',
		        data: {
		        	class_id : $("#class_select_id").val()
		        	},
		        success: function(json){
		        	$(json.data).each(function(i,item){
		        		if(item.class_id>0) subjects.push(item);
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
			if(item.class_id>0) headTag.append("<th>"+item.subject_title.substr(0,6)+"</th>");
		});
		headTag.append($("<th/>",{"class":"total-th",text:"총합"}));
		headTag.append($("<th/>",{"class":"sum-th",text:"평균"}));
		$("#sub-table>thead").empty().append(headTag);
		
		footTag = $("<tr/>");
		footTag.append("<th>과목 평균</th>");
		$(subjects).each(function(i,item){
			if(item.class_id>0) footTag.append($("<th/>",{"class":"subAvg-th"}));
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
					inputTag = $("<span/>",{
						"class":"score",
						"data-m_id":member.m_id,
						"data-subject_id":subject.subject_id,
						
						//"value":tmp
					});
					if(subject.class_id>0) {
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
	        type:'post',
	        data: {
        		class_id: $("#class_select_id").val(),
	        },
	        success: function(json){			
	        		$(json.data).each(function(i,item){
	        			$span = $("#sub-table span"
	    	        			+"[data-m_id='"+item.m_id+"']"
	    	        			+"[data-subject_id='"+item.subject_id+"']"
	    	        			);
	        			console.log($span);
	        			if($span.text()==""){
	        				$span.text(item.score).attr("data-exam_id",item.exam_id).attr("data-score",item.score);
	        			}else{
	        				$newSpan = $span.clone().attr("data-exam_id",item.exam_id).attr("data-score",item.score);
	        				$span.parent("td").append($newSpan);
	        			}
	        		});
	        		setSum();
      },
	        error : function(request, status, error) { 
	            alert("code : " + request.status + "\r\nmessage : " + request.reponseText); 
	        } 
	        
	    });
		
	}

	function setSum(){
		gData = [];
		ykeys = [];
		labels = [];
		subAvg = [];
		subCnt = 0;
		$("#sub-table tbody>tr").each(function(i,tr){
			dataRow={y:$(".m-name",tr).text()};
			
			sum=0;
			cnt=0;
			$("span.score",this).each(function(j,item){
				dataRow[subjects[j].subject_id]=Number($(item).val());
				if(ykeys[j]==null)ykeys.push(subjects[j].subject_id);
				if(labels[j]==null)labels.push(subjects[j].subject_title);
				if(subAvg[j]==null){
					subAvg.push(Number($(item).val()));
				}else{
					subAvg[j] += Number($(item).val());
				}
				sum+=Number($(item).val());
				cnt++;
			});
			avg=(cnt>0)?sum/cnt:0;
			$(".sum-th",tr).html(sum);
			$(".avg-th",tr).html(avg.toFixed(2));
			subCnt++;
			gData.push(dataRow);
		});
		if(subCnt>0){
			$(subAvg).each(function(i,item){
				$(".subAvg-th:eq("+i+")").text((subAvg[i]/subCnt).toFixed(2));
			});
		}
		if(bar==null){
			$("#bar-chart").empty();
			bar = new Morris.Bar({
		        element: 'bar-chart',
		        resize: true,
		        data: gData,
		        barColors: ['#999999','#245580','#5cb85c','#46b8da','#eea236','#d43f3a'],
		        xkey: 'y',
		        ykeys: ykeys,
		        labels: labels,
		        hideHover: 'auto'
		      });
			}else{
				bar.setData(gData);
			}

		}
});
</script>
<style type="text/css">
.hover-td{cursor: pointer;}
.changed{background-color: Yellow}
.newScore{background-color: #b0eaff;}
input.score{width: 100%;height: 40px;border: 0px;}
.search-th{width: 200px}
td.input-td{padding:0px !important;}
#sub-table span{display: block;}
.search-div{ }
</style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<!-- Button trigger modal -->



<div class="main"><div class="main-div">
	<h3 class="sub-title">score 관리</h3>
	<div class="search-div">
	<select  class="form-control "  name="class_select_id" id="class_select_id">
		<option value="">반을 선택하세요. </option>
		<c:forEach var="classes" items="${classList}">
 			<option value="${classes.class_id }">${classes.class_name}-${classes.class_state}</option>
		</c:forEach>
	</select>

	</div>
	
<div class="search-table">
		<table  class="table table-striped table-bordered table-hover" id="sub-table" cellspacing="0" width="100%">
			<thead>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
			</tfoot>
		</table>
</div>	

          <div class="box box-success">
            <div class="box-header with-border">
              <h3 class="box-title">Bar Chart</h3>

              <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
              </div>
            </div>
            <div class="box-body chart-responsive">
              <div class="chart" id="bar-chart" style="height: 300px;"></div>
            </div>


</div></div>
<%@ include file="/WEB-INF/views/include/foot.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
<script src="<%=request.getContextPath()%>/webjars/adminlte/2.3.11/plugins/morris/morris.min.js"></script>

</body>
</html>