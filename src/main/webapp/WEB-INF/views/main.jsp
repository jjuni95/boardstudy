<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="resources/css/main.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	
	
	<!-- <div onClick="location.href='http://localhost:8080/member/memberUpdateView'">${member.memberId}</div> -->
	<!-- <a href="/member/logout">로그아웃</a>  -->


	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>