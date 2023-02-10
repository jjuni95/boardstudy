<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">

#image_container img {
	width: 300px;
	height: 300px;
}

</style>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
</head>
<body>

	<form action="/gboard/gwrite" method="post" enctype="multipart/form-data">
		
		<div id="fileIndex" class="input_wrap">
			<input type="text" id="file1Name" readonly>
			<label for="file1">찾기</label>
        	<input type="file" name="file1" id="file1" style="display: none" onchange="setThumbnail(event);"/>
        	<input type="button" id="addFile" onClick="fn_addFile(1);" value="추가"/>
        	<input type="button" id="delFile" onClick="fn_delFile1();" value="삭제"/>
        	<div id="image_container"></div>
    	</div> 
    	
    	<!-- div 똑같은거 만들어서 히든처리 후 추가를 누르면 히든1 보여주고 히든1의 추가를 누르면 히든2 보여주고 -->
    	<div id="fileHidden1" class="input_wrap" style="display:none;">
			<input type="text" id="file1Name" readonly>
			<label for="file1">찾기</label>
        	<input type="file" name="file1" id="file1" style="display: none" onchange="setThumbnail(event);"/>
        	<input type="button" id="addFile" onClick="fn_addFile(2);" value="추가"/>
        	<input type="button" id="delFile" onClick="fn_delFile1();" value="삭제"/>
        	<div id="image_container"></div>
    	</div> 
    	
    	
    	<div id="fileHidden2" class="input_wrap" style="display:none;">
			<input type="text" id="file1Name" readonly>
			<label for="file1">찾기</label>
        	<input type="file" name="file1" id="file1" style="display: none" onchange="setThumbnail(event);"/>
        	<input type="button" id="addFile" onClick="fn_addFile(3);" value="추가"/>
        	<input type="button" id="delFile" onClick="fn_delFile1();" value="삭제"/>
        	<div id="image_container"></div>
    	</div> 
    	
    	
    	
		<button type="submit">등록</button>
		<button type="button" class="btn" onClick="location.href='http://localhost:8080/board/list'">취소</button>
	</form>
</body>
<script>

//파일추가
/* function fn_addFile1(){

	var html = "";
	html+="<input type='hidden' id='file2No' name='file2No' value=''>";
	html+="<div id='file2Div'>";
	html+="<input type='text' id='file2Name' readonly>";
	html+="<label for='file2'>찾기</label>";
	html+="<input type='file' name='file2' id='file2' style='display: none' onchange='setThumbnail(event);'>";
	html+="<input type='button' id='addFile2' onClick='fn_addFile2()' value='추가'>"; 
	html+="<input type='button' id='delFile2' onClick='fn_delFile2()' value='삭제'>"; 
	html+="<div id='image_container'>";
	html+="</div>";
	html+="</div>";
	
	$("#fileIndex").append(html);
	
	let disabled = document.querySelector('#addFile');
	disabled.setAttribute('disabled' , true);

}
 */
function fn_addFile(idx){
	 console.log(idx);
	if(idx==1){
		$("#fileHidden1").css("display", "block");
	}
	if(idx==2){
		$("#fileHidden2").css("display", "block");
	}
} 
 
function setThumbnail(event) {
    var reader = new FileReader();

    reader.onload = function(event) {
      var img = document.createElement("img");
      img.setAttribute("src", event.target.result);
      document.querySelector("div#image_container").appendChild(img);
    };

    reader.readAsDataURL(event.target.files[0]);
    
	var fileVal = $("#file1").val().split("\\");
	
	var fileName = fileVal[fileVal.length-1];
	
	$("#file1Name").val(fileName);
  }

function fn_delFile1(){
	$("#file1").val("");
	$("#file1Name").val("");
	$("#image_container img").remove();
}


function fn_addFile2(){
	var fileVal = $("#file2").val().split("\\");
	
	var fileName = fileVal[fileVal.length-1];
	
	$("#file2Name").val(fileName);
	
}
</script>

</html>