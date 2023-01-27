<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
</head>
<body>

	<form action="/board/enroll" method="post" enctype="multipart/form-data">
		
		<div>작성자
			<label>${decWriter}</label>
		</div>
		
		<div class="input_wrap">
			<label>제목</label> <input name="title">
		</div>
		<div class="input_wrap">
			<label>내용</label>
			<textarea rows="3" name="content"></textarea>
		</div>
		<div class="input_wrap">
        	<input type="file" name="file">
    	</div> 
		<button class="btn">등록</button>
	</form>
</body>
<script>

    $(document).ready(function(){
    
 
    });
 
</script>

</html>