<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/main.css">
</head>
<body>

<!-- 	<div class="login_area">
		<div class="login_button">
			<a href="/member/login">로그인</a>
		</div>
		<span><a href="/member/join">회원가입</a></span>
	</div> -->
	<div onClick="location.href='http://localhost:8080/member/memberUpdateView'">${member.memberId}</div>
	<a href="/member/logout">로그아웃</a>

</body>
</html>