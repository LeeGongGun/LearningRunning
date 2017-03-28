<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<title>Insert title here</title>
<style>
body{}
.loginForm{position:absolute;top:45%;left:50%;transform:translate(-50%, -50%);width:400px; margin: auto;padding : 20px; background: rgba( 255, 255, 255, 0.5 );border-radius: 5px;}
#result,.result{color: red;font-size: 11px;margin: 5px}
.btns{margin-top: 5px;}
#background-changer{position:absolute;top:0;width:100%;height:100%;z-index:-1;background-size: cover;
    background-repeat: no-repeat;
    background-position: 0 0;
    background-attachment: fixed;}
</style>
</head>
<body>
<div id="background-changer"></div>
	<form:form commandName="loginCommand" >
		<div class="loginForm" >
			<h3>Login</h3>
			<div><form:input path="email"  class="form-control" placeholder="아이디"/><div class="result"><form:errors path="email"/></div></div>
			<div><form:password path="password"  class="form-control" placeholder="비밀번호"/><div class="result"><form:errors path="password"/></div></div>
			<div id="result"><form:errors/></div>
			<div><label><spring:message code="rememberEmail"/><form:checkbox path="remember"/></label></div>
			<div><label><spring:message code="autoLogin"/><form:checkbox path="autoLogin"/></label></label></div>
			<div class="btns">
				<input type="submit" class="btn btn-primary btn-block" value="<spring:message code="login.btn" />" id="loginBtn">
				<input type="reset" class="btn btn-default btn-block" value="취소">
				<input type="button" class="btn btn-default btn-block" value="회원가입" id="registerBtn">
			</div>
		</div>		
	</form:form>

<%@ include file="/WEB-INF/views/include/foot.jsp" %>
<script type="text/javascript">
$(function(){
	$("#registerBtn").click(function(){
		$(location).attr("href","<%=request.getContextPath()%>/register/step1");
		
	});
    	var img_array = [1, 2, 3, 4, 5],
        interval = 7000;
    	 var i = 0;
    	 $bg=$("#background-changer");
    	 $bg.css("background-image", "url(<%=request.getContextPath()%>/images/background_img" + img_array[i] + ".jpg)");
            setInterval(function () {
                i++;
                if (i == img_array.length) {
                    i = 0;
                }
                $bg.fadeOut("slow", function () {
                    $(this).css("background-image", "url(<%=request.getContextPath()%>/images/background_img" + img_array[i] + ".jpg)");
                    $(this).fadeIn("slow"); // 1000 = 1초
                });
            }, interval);
        
        
});
</script>
</body>
</html>