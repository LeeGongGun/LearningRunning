<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String fileDir = request.getRealPath("/resources/uploads/");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css"
	type="text/css" media="all" />
	
<script>
	function addBoard() {
		document.getElementById("frm").submit();
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MVC 게시판 글쓰기</title>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp"%>
	<div class="main">
		<div class="main-div">
	<form:form commandName="counselBoardCommand" enctype="multipart/form-data" id="frm">
		<table>
			<tr>
				<td colspan="2">상담 내용 입력</td>
			</tr>
			<tr>
				<td>작성자</td>
				<td><input type="text" id="name" name="name" value="" readonly/>
					
				</td>
			</tr>
			<tr>
				<td>제목</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td></td>
			</tr>
			<tr bgcolor="ccccccc">
				<td colspan=2 style="height: 1px;"></td>
			</tr>
			<tr bgcolor="ccccccc">
				<td colspan=2>&nbsp;</td>
			</tr>
			<tr align="center" align="center" valign="middle">
				<td colspan=2><a href="javascript:addBoard()">[등록]</a>&nbsp;&nbsp;
					<a href="javascript:history.go(-1)">[뒤로]</a></td>
			</tr>

		</table>
	</form:form>
	</div>
	</div>

</body>
</html>