<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/html-head.jsp" %>
<title>Insert title here</title>
<style>
body{}
.btn-naver {
    color: #fff;
    background-color: #23b300;
    border-color: rgba(0,0,0,0.2);
}
.btn-naver:hover {
    color: #fff;
    background-color: #1b8c00;
    border-color: rgba(0,0,0,0.2);
}
#result,.result{color: red;font-size: 11px;margin: 5px}
.btns{margin-top: 5px;}
#background-changer{position:absolute;top:0;width:100%;height:100%;z-index:-1;background-size: cover;
    background-repeat: no-repeat;
    background-position: 0 0;
    background-attachment: fixed;}
.register-logo a{
    color: #fff;
	text-shadow: 2px 2px 2px gray;
}
</style>
</head>

<body class="hold-transition register-page">
<script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '284758311950258',
      xfbml      : true,
      version    : 'v2.9'
    });
    FB.AppEvents.logPageView();
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0]
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));
  
</script>
    <div id="background-changer"></div>
	<div class="register-box">
		<div class="register-logo">
			<a href="<%=request.getContextPath()%>/"><b>출석</b>해쓰~</a>
		</div>
		<form:form commandName="loginCommand" >
			<div class="register-box-body">
			    <p class="login-box-msg">회원 가입</p>
				<div class="form-group has-feedback">
					<form:input path="email"  class="form-control" placeholder="아이디"/>
					<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
					<div class="result"><form:errors path="email"/></div>
				</div>
				<div class="form-group has-feedback">
					<form:password path="password"  class="form-control" placeholder="비밀번호"/>
					<span class="glyphicon glyphicon-lock form-control-feedback"></span>
					<div class="result"><form:errors path="password"/></div>
				</div>
				<div class="form-group has-feedback" id="result"><form:errors/></div>
				<div class="form-group has-feedback">
					<p>
						<label><spring:message code="rememberEmail"/><form:checkbox path="remember"/></label>
						<br/><label><spring:message code="autoLogin"/><form:checkbox path="autoLogin"/></label>
					</p>
				</div>
				<div class="form-group has-feedback" class="btns">
					<input type="submit" class="btn btn-primary btn-block" value="<spring:message code="login.btn" />" id="loginBtn">
					<input type="button" class="btn btn-default btn-block" value="회원가입" id="registerBtn">
				</div>
			    <div class="social-auth-links text-center">
			      <p>- OR -</p>
			      <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> 페이스북 ID로 로그인 / 가입</a>
			      <a href="#" class="btn btn-block btn-social btn-naver btn-flat"><i class="fa fa-google-plus"></i> 네이버 ID로 로그인 / 가입</a>
			    </div>
			</div>		
		</form:form>
	</div>
<%@ include file="/WEB-INF/views/include/html-js.jsp" %>
<script type="text/javascript">
$(function(){
	$("#registerBtn").click(function(){
		$(location).attr("href","<%=request.getContextPath()%>/register");
		
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