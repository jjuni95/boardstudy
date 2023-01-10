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
						<input type="text" class="pw_input" id="memberPassword" name="memberPassword" placeholder="비밀번호를 입력하세요.">
					</div>
				</div>
				<div class="pwck_wrap">
					<div class="pwck_input_box">
						<input class="pwck_input" id="chkMemberPassword" name="chkMemberPassword" placeholder="비밀번호를 한번 더 입력하세요.">
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
							<input class="zipcode_input" id="zipcode" name="zipcode" readonly="readonly" placeholder="우편번호">
						</div>
						<div class="clearfix"></div>
						<div class="address_button_wrap" onclick="daum_address()">
							<input type="button" class="address_button" value="우편번호 찾기">
						</div>

					</div>
					<div class="streetadr_input_wrap">
						<div class="streetadr_input_box">
							<input class="streetadr_input" id="streeAdr" name="streeAdr" readonly="readonly" placeholder="기본주소">
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

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

//비밀번호(특수문자 포함한 8자리 이상)



//회원가입 버튼(회원가입 기능 작동)
$(document).ready(function(){
	$(".join_button").click(function(){
		
		var pw = $('#memberPassword').val();
		var num = pw.search(/[0-9]/g); 							//숫자포함
		var eng = pw.search(/[a-z]/ig);							//영문(대소문자) 포함
		var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩';:₩/?]/gi);		//특수문자 포함
		
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
		
		if($("#memberPassword").val() != $("#chkMemberPassword").val()){
	        alert("비밀번호가 서로 다릅니다. 비밀번호를 확인해 주세요."); 
	        $("#chkMemberPassword").focus();
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
		
		//비밀번호(특수문자 포함한 8자리 이상)
		if(pw.length < 8 || pw.length > 20){
			alert(pw);
			alert(pw.length);
			alert("비밀번호를 8자리 ~ 20자리 이내로 입력해주세요.");
			return false;
		}else if(pw.search(/\s/) != -1){
	  		alert("비밀번호는 공백 없이 입력해주세요.");
	  		return false;
	 	}else if(num < 0 || eng < 0 || spe < 0 ){
	  		alert("영문,숫자, 특수문자를 혼합하여 입력해주세요.");
	  		return false;
	 	}else {
			console.log("통과"); 
	    return true;
	    }
	
		
		
		//패스워드 체크
		/* $.ajax({
			url : "member/passChk",
			type : "POST",
			dataType : "json"
			data : $("")
		}) */
		
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



/* 다음 주소 연동 */
function daum_address(){
	
	new daum.Postcode({
		oncomplete: function(data) {
	            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분
	            
			 // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 도로명 조합형 주소 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }
            // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
            if(fullRoadAddr !== ''){
                fullRoadAddr += extraRoadAddr;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            console.log(data.zonecode);
            console.log(fullRoadAddr);
            
            
            $("[name=zipcode]").val(data.zonecode);
            $("[name=streeAdr]").val(fullRoadAddr);
            
            /* document.getElementById('signUpUserPostNo').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById('signUpUserCompanyAddress').value = fullRoadAddr;
            document.getElementById('signUpUserCompanyAddressDetail').value = data.jibunAddress; */
	 }
	}).open();    
}
 
 
</script>

</html>