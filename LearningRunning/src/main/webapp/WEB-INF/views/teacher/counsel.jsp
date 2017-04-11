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
<title>과목 입력</title>
<script type="text/javascript">
$(function(){
	
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
        <h4 class="modal-title" id="myModalLabel">과목 입력</h4>
      </div>
      <div class="modal-body" style="min-height: 400px">
      	<div class="form-group">
         	<div class="col-sm-12">
			<select  class="form-control" style="width: 100%"  name="subject_id" id="modal_subject_id">
				
			</select>
         	</div>
        </div><br/>
      <form:form commandName="command" id="editFrm">
      	<input  type="hidden" id="mode" value="insert">
      	<div class="form-group">
      	 	<label for="class_id" class="col-sm-2 control-label">과목명</label>
         	<div class="col-sm-10">
         		<input type="hidden" class="form-control" id="frm_subject_id" name="subject_id" placeholder="아이디">
         		<input type="text" class="form-control" id="frm_subject_title" name="subject_title" placeholder="과목명" required="required">
         	</div>
        </div>
      	<div class="form-group">
      	 	<label for="class_id" class="col-sm-2 control-label">comment</label>
         	<div class="col-sm-10">
         		<textarea style="min-height: 280px" class="form-control" id="frm_subject_comment" name="subject_comment"></textarea>
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
<div id="subject_comments_div" style="display: none;"></div>
<div class="main"><div class="main-div">
	일단 보류
</div>
</div>
<%@ include file="/WEB-INF/views/include/foot.jsp" %>
</body>
</html>