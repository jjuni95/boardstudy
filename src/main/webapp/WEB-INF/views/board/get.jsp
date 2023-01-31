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

	<form id="modifyForm" action="/board/modify" method="post">
	<input type="hidden" name="boardNo" id="boardNo" value="${pageInfo.boardNo}">
		
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
	<div class="form-group">
		<c:forEach var="file" items="${file}">
			<a href="#" onclick="fn_fileDown('${file.FILE_NO}'); return false;">${file.ORG_FILE_NAME}</a>(${file.FILE_SIZE})<br>
		</c:forEach>
	</div>
	
	
		<button class="fileAdd_btn" type="button">파일추가</button>
	
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

//파일추가
function fn_addFile(){
			var fileIndex = 1;
			//$("#fileIndex").append("<div><input type='file' style='float:left;' name='file_"+(fileIndex++)+"'>"+"<button type='button' style='float:right;' id='fileAddBtn'>"+"추가"+"</button></div>");
			$(".fileAdd_btn").on("click", function(){
				$("#fileIndex").append("<div><input type='file' style='float:left;' name='file_"+(fileIndex++)+"'>"+"</button>"+"<button type='button' style='float:right;' id='fileDelBtn'>"+"삭제"+"</button></div>");
			});
			$(document).on("click","#fileDelBtn", function(){
				$(this).parent().remove();
				
			});
		}

</script>

</html>