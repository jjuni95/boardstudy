<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

	<form id="modifyForm" action="/board/modify" method="post" enctype="multipart/form-data">
	<input type="hidden" name="boardNo" id="boardNo" value="${pageInfo.boardNo}">
	<input type="hidden" id="fileNoDel" name="fileNoDel[]" value=""> 
	<input type="hidden" id="fileNameDel" name="fileNameDel[]" value=""> 
		
		<div>작성자
			<label>${pageInfo.memberName}</label>
		</div>
		<div>조회수
			<label>${pageInfo.hit}</label>
		</div>
		<div class="input_wrap">
			<label>작성일</label>
			<input name="regdater" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${pageInfo.regDate}"/>' >
		</div>
		<div class="input_wrap">
			<label>마지막 수정일</label>
			<input name="updateDate" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${pageInfo.updateDate}"/>' >
		</div>	
			
	
		<div class="input_wrap">
			<label>게시판 제목</label>
			<input name="title" value='<c:out value="${pageInfo.title}"/>' <c:if test="${showModifyBtn != 'Y'}">disabled </c:if>>
		</div>
		<div class="input_wrap">
			<label>게시판 내용</label>
			<textarea name="content" <c:if test="${showModifyBtn != 'Y'}">disabled </c:if>>${pageInfo.content}</textarea>
		</div>
	
	
		<span>파일 목록</span>
	
		<c:if test="${fileSize != 0}">
			<div id="fileIndex" class="input_wrap">
				<c:forEach var="file" items="${file}" varStatus="var">
					<div>
						<input type="hidden" id="FILE_NO" name="FILE_NO_${var.index}" value="${file.FILE_NO }">
						<input type="hidden" id="FILE_NAME" name="FILE_NAME" value="FILE_NO_${var.index}">
					
						<a href="#" id="fileName" onclick="return false;">${file.ORIGINFILE_NAME}</a>
						<button id="fileDel" onclick="fn_del('${file.FILE_NO}','FILE_NO_${var.index}', '${var.index}');" type="button">삭제</button>
						<c:if test="${var.index == 0}">
						<div id="fileIndex" class="input_wrap">
					       	<input type="button" id="addFile" value="추가">
					   	</div> 
					   	</c:if>
					</div>
				</c:forEach>
			</div> 
		</c:if>
		
		<c:if test="${fileSize == 1}">
			<div id="fileIndex" class="input_wrap">
		       	<input type="file" name="file">
		       	<input type="button" id="addFile" value="추가">
		   	</div> 
	   	</c:if>
	
<%-- 		<c:if test="${fileSize == 0}">
			<div id="fileIndex" class="input_wrap">
		       	<input type="button" id="addFile" value="추가">
		   	</div> 
	   	</c:if> --%>
		
	
		<c:if test="${showModifyBtn == 'Y'}">
		<button type="submit" class="modify_btn" id="modifyBtn">수정하기</button>
		<button type="button" class="delete_btn" id="deleteBtn">삭제하기</button>
		</c:if>
		<button type="button" class="cancel_btn" id="cancelBtn" onClick="location.href='http://localhost:8080/board/list'">목록</button>

	</form>
</body>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
//삭제하기
$("#deleteBtn").on("click", function(){
	$.ajax({
		url : "/board/delete", 
		type : "post",
		dataType : "json",
		data : {"boardNo" : $("#boardNo").val()},
		success : function(data){
			if(data == 1){
			alert("삭제되었습니다.");
			window.location = '/board/list';
			}else{
				alert("삭제가 불가능합니다.");
			}
		}
	})
})



/* $("#fileDel").on("click", function(){
	var value = $("#fileNoDel").val();
	var name = $("#fileNameDel").val();
	
	fn_del(value, name)
}) */

var fileNoArray = new Array();
var fileNameArray = new Array();

function fn_del(value, name, fileIndex){

	
	var html = "";
	html+="<div>";
	html+="<input type='file' style='float:left;' name='file_" + (fileIndex--) + "'>";
	html+="<input type='button' id='addFile' value='추가'>"; 
	html+="</div>";

	/* if(fileIndex != 1){ */
	/* 	let disabled = document.querySelector('#addFile');
		disabled.setAttribute('disabled' , false); */
	/* } */
	$("#fileIndex").append(html);
	
	$("#fileName").remove();
	$("#fileDel").remove(); 
	
	fileNoArray.push(value);
	fileNameArray.push(name);
	$("#fileNoDel").attr("value", fileNoArray);
	$("#fileNameDel").attr("value", fileNameArray);
}



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