<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<span>아이디 찾기</span>

	<div>
		<input id="input_email" name="input_email"
			placeholder="이메일 주소를 입력해 주세요.">
	</div>
	<div class="id_email_submit">
		<input type="button" onclick="fn_idEmailSubmit();" 
			id="idEmailSubmit_button" value="아이디 이메일 전송">
	</div>
</body>
</html>