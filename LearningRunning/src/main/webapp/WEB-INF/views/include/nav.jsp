<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <script type="text/javascript">
$(function(){
	$(window).scroll(function(e){
		if($(window).scrollTop()>40){
			$(".title-div").addClass("div-fixed");
			$(".VideoPane-bg").height($("body").height()+200);
		}else{
			$(".title-div").removeClass("div-fixed");

		}
	});
	/*
	var img_array = [1, 2, 3, 4, 5],
    interval = 7000;
	 var i = 0;
	 $bg=$(".VideoPane-bg");
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
	*/	
});
</script>
<div id="datepicker-div"></div>	
	<div class="title-div">
		<ul class="nav nav-pills ">
            <li role="presentation" class="dropdown"><a href="#"  data-toggle="dropdown" role="button" aria-expanded="false">츨결관리<span class="caret"></span></a>
	            <ul class="dropdown-menu" role="menu">
		          <li><a href="<%=request.getContextPath()%>/teacher/attendance/classList">츨결입력</a></li>
		          <li><a href="<%=request.getContextPath()%>/attendance/dashboard">반별 출결보기</a></li>
		          <li><a href="<%=request.getContextPath()%>/attendance/person">개인별 출결보기</a></li>
		        </ul>
            </li>
            <li role="presentation" class="dropdown"><a href="#"  data-toggle="dropdown" role="button" aria-expanded="false">성적관리<span class="caret"></span></a>
	            <ul class="dropdown-menu" role="menu">
		          <li><a href="<%=request.getContextPath()%>/teacher/counsel">상담관리</a></li>
		          <li><a href="<%=request.getContextPath()%>/teacher/score">성적관리</a></li>
		          <li><a href="<%=request.getContextPath()%>/teacher/memberScore">개인별성적보기</a></li>
		        </ul>
            </li>
            <li role="presentation" class="dropdown"><a href="#"  data-toggle="dropdown" role="button" aria-expanded="false">관리자<span class="caret"></span></a>
	            <ul class="dropdown-menu" role="menu">
		          <li><a href="<%=request.getContextPath()%>/admin/member">member 관리</a></li>
		          <li><a href="<%=request.getContextPath()%>/admin/auth">선생,학생권한</a></li>
		          <li><a href="<%=request.getContextPath()%>/admin/curri">과정 관리</a></li>
		          <li><a href="<%=request.getContextPath()%>/admin/class">반(class)관리</a></li>
		          <li><a href="<%=request.getContextPath()%>/admin/classSubject">과목관리</a></li>
		          <li><a href="<%=request.getContextPath()%>/admin/classJoinMem">반-학생,선생 등록</a></li>
		          <li><a href="<%=request.getContextPath()%>/admin/exam">시험 관리</a></li>
		        </ul>
            </li>
		    <li><a href="<%=request.getContextPath()%>/logout">logout</a></li>
          </ul>
          		
	</div>

	
	<div class="VideoPane-bg">
	<!-- <video data-src="https://bnetcmsus-a.akamaihd.net/cms/template_resource/VUYXVZQH388N1484354660050.mp4" loop="loop" muted="muted"  src="https://bnetcmsus-a.akamaihd.net/cms/template_resource/VUYXVZQH388N1484354660050.mp4" autoplay></video> -->
	<!--<video data-src="https://bnetcmsus-a.akamaihd.net/cms/template_resource/pb/PBDOJHUUIDIL1459385840550.mp4" loop="loop" muted="muted"  src="https://bnetcmsus-a.akamaihd.net/cms/template_resource/pb/PBDOJHUUIDIL1459385840550.mp4" autoplay></video>-->
	<!-- https://bnetcmsus-a.akamaihd.net/cms/template_resource/pb/PBDOJHUUIDIL1459385840550.mp4 -->
	<!--</div>-->
	<!--<div class="footer">-->
	<!--      <div class="footer-div">-->
	<!--        <p class="text-muted">COPYRIGHT© 2017 BY Lee Gong Gun. ALL RIGHTS RESERVED.</p>-->
	<!--      </div>-->
	</div>
