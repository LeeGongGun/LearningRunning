<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>회원가입</title>
<%@ include file="/WEB-INF/views/include/html-head.jsp"%>
<style type="text/css">
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
<div id="background-changer"></div>
<%@ include file="/WEB-INF/views/include/html-js.jsp"%>
<div class="register-box">
  <div class="register-logo">
    <a href="<%=request.getContextPath()%>/"><b>출석</b>해쓰~</a>
  </div>

  <div class="register-box-body">
    <p class="login-box-msg">회원 가입</p>

    <form id="registerFrm" method="post">
      <div class="form-group has-feedback">
        <input type="text" name="m_name" id="m_name" class="form-control" placeholder="이름" required="required">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="email" name="m_email" id="m_email" class="form-control" placeholder="Email(아이디용)" required="required">
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" name="m_pass" id="m_pass" class="form-control" placeholder="Pass" required="required">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" name="m_repass" id="m_repass" class="form-control" placeholder="Re pass" required="required">
        <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">회원가입</button>
        </div>
        <!-- /.col -->
      </div>
    </form>

    <div class="social-auth-links text-center">&f02c;
      <p>- OR -</p>
      <a href="#" id="googleLogin" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> 페이스북 ID로 로그인 / 가입</a>
      <a href="#" id="naverLogin" class="btn btn-block btn-social btn-naver btn-flat"><i class="fa fa-naver"></i> 네이버 ID로 로그인 / 가입</a>
    </div>
  </div>
  <!-- /.form-box -->
</div>
<!-- /.register-box -->

<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
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
		$("#registerFrm").ajaxForm({
	        beforeSubmit: function (data,form,option) {
	        	if($("#m_pass").val().length <= 4){
					alert("비밀번호가 너무 짧습니다.");
					$("#m_pass").focus();
					return false;
	        	}
				if($("#m_pass").val()!=$("#m_repass").val()) {
					alert("비밀번호 불일치");
					$("#m_repass").focus();
					return false;
				}
	        	return true;
	        },
	        success: function(response,status,xhr){
	        	 var ct = xhr.getResponseHeader("content-type") || "";
	        	    if (ct.indexOf('html') > -1) {}
	        	    if (ct.indexOf('json') > -1) {
						if(response.data==-1){
							alert($("#m_email").val()+"은 이미 가입된 이메일입니다.");
							$("#m_email").focus();
						}else if(response.data==1){
							alert($("#m_name").val()+"님 회원가입이 성공하였습니다 로그인 페이지로 이동합니다.");
							location.href='<%=request.getContextPath() %>/login';
						}
	        	    } 
	        },
	        error: function(request, status, error){
	        		alert("문제가 있습니다.");
	        }                              
	    });
		$("#googleLogin,#naverLogin").click(function(){
			alert("준비중입니다.");
		});
  
  });
  
  
</script>


</body>
</html>