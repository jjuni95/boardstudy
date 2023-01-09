<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/member/join.css">
</head>
<body>
	<div class="wrapper">
		<form id="join_form" action="/member/join"  method="post">
			<div class="wrap">
				<div class="subjecet">
					<span>회원 정보 입력</span>
				</div>
				<div class="id_wrap">
					<div class="id_name">아이디</div>
					<div class="id_input_box">
						<input id="memberId" class="id_input" name="memberId" placeholder="아이디를 입력하세요.">
					</div>
					<div class="join_button_wrap">
						<input type="button" onclick="fn_idChk();" class="idChk_button" id="idChk_button" value="중복확인">
					</div>
				</div>
				<div class="user_wrap">
					<div class="user_name">이름</div>
					<div class="user_input_box">
						<input class="user_input" id="memberName" name="memberName" placeholder="이름을 입력하세요.">
					</div>
				</div>
				<div class="pw_wrap">
					<div class="pw_name">비밀번호</div>
					<div class="pw_input_box">
						<input class="pw_input" id="memberPassword" name="memberPassword" placeholder="비밀번호를 입력하세요.">
					</div>
				</div>
				<div class="pwck_wrap">
					<div class="pwck_input_box">
						<input class="pwck_input" name="chkMemberPassword" placeholder="비밀번호를 한번 더 입력하세요.">
					</div>
				</div>

				<div class="mail_wrap">
					<div class="mail_name">이메일</div>
					<div class="mail_input_box">
						<input class="mail_input" id="emailId" name="emailId" placeholder="이메일 아이디"> 
						<input class="mail_input_1" id=inputEmail name="inputEmail" style="display: none">
						 <select name="selectEmail" id="selectEmail">
							<option value="1">직접입력</option>
							<option value="@radcns.com" selected>@radcns.com</option>
							<option value="@hanmail.net">@hanmail.net</option>
							<option value="@naver.com">@naver.com</option>
						</select>
					</div>

				</div>
				<div class="mail_button_wrap">
					<input type="button" class="mail_button" value="중복확인">
				</div>


				<div class="phoner_wrap">
					<div class="phone">연락처</div>
					<div class="phone_input_box">
						<select name="phone1" id="phone1">
							<option value="010" selected>010</option>
							<option value="011">011</option>
						</select>  
				- &nbsp;	<input name="phone2" class="phone_input2"> - 
						<input name="phone3" class="phone_input3">
					</div>
				</div>



				<div class="address_wrap">
					<div class="zipcode_name">주소</div>
					<div class="zipcode_input_wrap">
						<div class="zipcode_input_box">
							<input class="zipcode_input" id="zipcode" name="zipcode" placeholder="우편번호">
						</div>
						<div class="clearfix"></div>
						<div class="join_button_wrap">
							<input type="button" class="join_button" value="우편번호 찾기">
						</div>

					</div>
					<div class="streetadr_input_wrap">
						<div class="streetadr_input_box">
							<input class="streetadr_input" id="streeAdr" name="streeAdr" placeholder="기본주소">
						</div>
					</div>
					<div class="detailadr_input_wrap">
						<div class="detailadr_input_box">
							<input class="detailadr_input" id="detailAdr" name="detailAdr" placeholder="상세주소">
						</div>
					</div>
				</div>
				<div class="join_button_wrap">
					<input type="submit" class="join_button" value="가입">
				</div>
				<div class="joinCancel_button_wrap">
					<input type="button" class="joinCancel_button" value="취소">
				</div>
			</div>
		</form>
	</div>
</body>

<script type="text/javascript"
	src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">


//회원가입 버튼(회원가입 기능 작동)
$(document).ready(function(){
	$(".join_button").click(function(){
		if($("#memberId").val()==""){
			alert("아이디를 입력해주세요.");
			$("#memberId").focus();
			return false;
		}
		
		if($("#memberPassword").val()==""){
			alert("비밀번호를 입력해주세요.");
			$("#memberPassword").focus();
			return false;
		}
		
		if($("#memberName").val()==""){
			alert("성명을 입력해주세요.");
			$("#memberName").focus();
			return false;
		}
		
		if($("#emailId").val()=="" || $("#email").val()=="" || $("#selectEmail").val()==""){
			alert("이메일을 입력해주세요.");
			$("#emailId").focus();
			return false;
		}
		
		if($("#zipcode").val()=="" || $("#streeAdr").val()=="" || $("#detailAdr").val()==""){
			alert("주소를 입력해주세요.");
			$("#streeAdr").focus();
			return false;
		}
		
		//패스워드 체크
		$.ajax({
			url : "member/passChk",
			type : "POST",
			dataType : "json"
			data : $("")
		})
		
	})
		
});

//아이디 중복검사
function fn_idChk(){
	$.ajax({
		url : "/member/memberIdChk",
		type : "post",
		dataType : "json",
		data : {"memberId" : $("#memberId").val()},
		success : function(data){
				if(data == 1){
					alert("중복된 아이디입니다.");
				}else if(data == 0){
					$("#idChk_button").attr("value", "Y");
					alert("사용가능한 아이디입니다.");
				}
			}
		})
	}

//이메일 입력방식 선택
$('#selectEmail').change(function(){
   $("#selectEmail option:selected").each(function () {
		
		if($(this).val()== '1'){ //직접입력일 경우
			 $("#inputEmail").css('display', 'inline-block'); //활성화
			 $("#selectEmail").css('display', 'none'); //비활성화
			 $("#inputEmail").val('');                        //값 초기화
		}else{ //직접입력이 아닐경우
			 $("#inputEmail").val($(this).text());      //선택값 입력
		}
   });
});
</script>

</html>