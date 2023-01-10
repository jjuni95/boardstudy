<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
<link rel="stylesheet" href="/resources/css/member/login.css">
</head>
<body>

<div class="login_wrap"> 
	<form id="login_form" method="post">
		<div class="login">
			<span>로그인</span>
		</div>
		<div class="id_wrap">
				<div class="id_input_box">
				<input class="id_input" name="memberId" placeholder="아이디를 입력하세요.">
			</div>
		</div>
		<div class="pw_wrap">
			<div class="pw_input_box">
				<input class="pw_iput" name="memberPassword" placeholder="비밀번호를 입력하세요.">
			</div>
		</div>
		
		<c:if test = "${result == 0 }">
			<div class = "login_warn">사용자 ID 또는 비밀번호를 잘못 입력하셨습니다.</div>
		</c:if>
		
		<div class="login_button_wrap">
			<input type="button" class="login_button" value="로그인">
		</div>	
		
		<div class="findid_button_wrap">
			<input type="button" class="findid_button" value="아이디 찾기">
		</div>	
		
		<div class="findpw_button_wrap">
			<input type="button" class="findpw_button" value="비밀번호 찾기">
		</div>			
		
		<div class="join_button_wrap">
			<input type="button" class="join_button" value="회원가입">
		</div>
	</form>
</div>
		
		
 
<script>
 
    /* 로그인 버튼 클릭 메서드 */
    $(".login_button").click(function(){
        
        //alert("로그인 버튼 작동");
    	 /* 로그인 메서드 서버 요청 */
        $("#login_form").attr("action", "/member/login");
        $("#login_form").submit();
        
    });
    
    //잘못된 id또는 비밀번호를 작성했을때
  /*   $('#login_warn').change(function({
    	if
    }))
  */
</script>
</body>
</html>