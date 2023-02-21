<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div>
		<span onClick="location.href='http://localhost:8080/main'" style="cursor : pointer;">메인으로
		</span>
	</div>
	<div onClick="location.href='http://localhost:8080/member/memberUpdateView'">
		${member.memberId}
	<c:if test="${loginSession != null}">
	<a href="/member/logout" id="logout">로그아웃</a>
	</c:if>
	
	<c:if test="${loginSession == null}">
	<a href="/member/login" id="login">로그인</a>
	</c:if>
	</div>
</body>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	$("#logout").click(function(){
		alert("로그아웃 되었습니다.")
	})
})
</script>
</html>
