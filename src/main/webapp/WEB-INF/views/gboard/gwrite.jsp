<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">

img {
   width: 300px;
   height: 300px;
}

</style>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
   <form action="/gboard/gwrite" method="post" enctype="multipart/form-data">
      
      <div id="fileIndex" class="input_wrap">
         <input type="text" id="file1Name" readonly>
         <label for="file1">찾기</label> 
           <input type="file" name="file1" id="file1" style="display: none" onchange="setThumbnail(event, 1);"/>
           <input type="button" id="addFile" onClick="fn_addFile(1);" value="추가"/>
           <input type="button" id="delFile1" onClick="fn_delFile(1);" value="삭제" style="display: none"/>
           <div id="image_container1"></div>
       </div> 
       
       <!-- div 똑같은거 만들어서 히든처리 후 추가를 누르면 히든1 보여주고 히든1의 추가를 누르면 히든2 보여주고 -->
       <div id="fileHidden1" class="input_wrap" style="display:none;">
         <input type="text" id="file2Name" readonly>
         <label for="file2">찾기</label>
           <input type="file" name="file2" id="file2" style="display: none" onchange="setThumbnail(event, 2);"/>
           <input type="button" id="addFile" onClick="fn_addFile(2);" value="추가"/>
           <input type="button" id="delFile2" onClick="fn_delFile(2);" value="삭제" style="display: none"/>
           <div id="image_container2"></div>
       </div> 
       
       <div id="fileHidden2" class="input_wrap" style="display:none;">
         <input type="text" id="file3Name" readonly>
         <label for="file3">찾기</label>
           <input type="file" name="file3" id="file3" style="display: none" onchange="setThumbnail(event, 3);"/>
           <input type="button" id="addFile" onClick="fn_addFile(3);" value="추가"/>
           <input type="button" id="delFile3" onClick="fn_delFile(3);" value="삭제" style="display: none"/>
           <div id="image_container3"></div>
       </div> 
       
      <button type="submit" class="submit">등록</button>
      <button type="button" class="btn" onClick="location.href='http://localhost:8080/gboard/glist'">취소</button>
   </form>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
<script>
var cnt = 0;
 
function fn_addFile(idx){
    console.log(idx);
   if(idx==1){
      $("#fileHidden1").css("display", "block");
   }
   if(idx==2){
      $("#fileHidden2").css("display", "block");
   }
} 

<%-- 썸네일 --%>
function setThumbnail(event, idx) {
    var reader = new FileReader();
    var fileVal = $("#file" + idx).val().split("\\");
    var fileName = "";
    
    reader.onload = function(event) {  
      var img = document.createElement("img");
      img.setAttribute("src", event.target.result);
      $("#image_container" + idx +" img").remove();
      document.querySelector("div#image_container"+ idx).appendChild(img); 
    };
    
    fileName = fileVal[fileVal.length-1];

    reader.readAsDataURL(event.target.files[0]);
   
	$("#file" + idx + "Name").val(fileName);
	$("#delFile" + idx).css("display", "inline-block");
}

function fn_delFile(idx){
    
   $("#file" + idx ).val("");
   $("#file" + idx + "Name").val("");
   $("#image_container" + idx +" img").remove();
    $("#delFile" + idx).css("display", "none");

}

</script>

</html>