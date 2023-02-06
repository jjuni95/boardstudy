<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
</head>
<body>

	<form action="/board/enroll" method="post" enctype="multipart/form-data">
		
		<div>작성자
			<label>${decWriter}</label>
		</div>
		
		<div class="input_wrap">
			<label>제목</label> <input name="title" maxlength='25'>
		</div>
		<div class="input_wrap">
			<label>내용</label>
			<textarea rows="3" name="content"></textarea>
		</div>
		<div id="fileIndex" class="input_wrap">
        	<input type="file" name="file">
        	<input type="button" id="addFile" value="추가">
    	</div> 
		<button class="btn">등록</button>
		<button type="button" class="btn" onClick="location.href='http://localhost:8080/board/list'">취소</button>
	</form>
</body>
<script>

//파일추가
$("#addFile").on("click", function(){
	var fileIndex = 1;
	var html = "";
	html+="<div>";
	html+="<input type='file' style='float:left;' name='file_" + (fileIndex++) + "'>";
	html+="<input type='button' id='fileDelBtn' value='삭제'>"; 
	html+="</div>";
	
	$("#fileIndex").append(html);
	if(fileIndex != 1){
		let disabled = document.querySelector('#addFile');
		disabled.setAttribute('disabled' , true);
	}
	
	$(document).on("click","#fileDelBtn", function(){
		$(this).parent().remove();
		let abled = document.querySelector('#addFile');
		abled.disabled = false;
		
	});
});
   
 
</script>

</html>