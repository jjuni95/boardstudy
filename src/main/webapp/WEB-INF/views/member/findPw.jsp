<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	
<form id="findPw_form" action="/member/findPw"  method="post">
	<div>
		<span>비밀번호 찾기</span>
		<input id="input_email" name="email"
			placeholder="이메일 주소를 입력해 주세요.">
	</div>
	<div class="id_email_submit">
		<input type="submit"
			id="EmailSubmit_button" value="임시 비밀번호 이메일 전송">
	</div>
</form>
</body>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	$("#EmailSubmit_button").click(function(){
		var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		
		if(!emailRule.test($("input[id='input_email']").val())) {           
			alert("이메일을 형식에 맞게 입력해주세요.");
            return false;
}
	
	});
});



</script>

</html>