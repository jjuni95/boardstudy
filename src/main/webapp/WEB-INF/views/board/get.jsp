<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
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
			<input disabled name="regdater" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${pageInfo.regDate}"/>' >
		</div>
		<div class="input_wrap">
			<label>마지막 수정일</label>
			<input disabled name="updateDate" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${pageInfo.updateDate}"/>' >
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
					
						<a href="#" id="fileName_${var.index}" onclick="return false;">${file.ORIGINFILE_NAME}</a>
						
						<c:if test="${var.index == 0 }">
					       	<input type="button" id="addFile" value="추가" <c:if test="${fileSize == 2}"> disabled="true"</c:if>>
					   	</c:if>
					   	<c:if test="${var.index == 1}">
							<button id="fileDelBtn" type="button">삭제</button>
					   	</c:if>
					</div>
				</c:forEach>
			</div> 
		</c:if>
		
		<c:if test="${fileSize == 0}">
			<div id="fileIndex" class="input_wrap">
		       	<input type="file" name="file">
		       	<input type="button" id="addFile" value="추가">
		   	</div> 
	   	</c:if>
	
		<c:if test="${showModifyBtn == 'Y'}">
		<button type="submit" class="modify_btn" id="modifyBtn">수정하기</button>
		<button type="button" class="delete_btn" id="deleteBtn">삭제하기</button>
		</c:if>
		<button type="button" class="cancel_btn" id="cancelBtn" onClick="location.href='http://localhost:8080/board/list'">목록</button>

</form>

 <p>댓글목록</p>
 
 	<!-- 댓글 -->
 <form name="replyUpdate" method="post">
	<div id="reply">
	  <ol class="replyList">
	    <c:forEach items="${replyList}" var="reply">
	      <li>
	        <p>
	        작성자 : ${reply.memberName}<br />
	        작성 날짜 :  ${reply.regDate} <br/>
	        </p>
	
	        <%-- <p>${reply.content} </p> --%>
	        <input name="content" id="content_${reply.commentNo}" value='<c:out value="${reply.content}"/>' <c:if test="${reply.memberNo ne memberNo}">disabled </c:if>>
	      <c:if test="${reply.memberNo eq memberNo}">
	      	<button type="button" class="replyUpdateBtn" name="commentNo" data-rno="${reply.commentNo}" onClick="fn_modifyBtn('${reply.commentNo}')">수정</button>
 			<button type="button" class="replyDeleteBtn" name="commentNo" data-rno="${reply.commentNo}" onClick="fn_deleteBtn('${reply.commentNo}')">삭제</button>
 			</c:if>
	      </li>
	      
	    </c:forEach>   
	  </ol>
	</div>
</form>
 
<!-- 댓글작성 -->
<form name="replyForm" method="post">
	<div>
	    <div>작성자
			<label>${decWriter}</label>
		</div>
			
	    <br/>
	   	 	<label for="content">댓글 내용</label>
	   	 	<input type="text" id="content" name="content" />
	   	 	<input type="hidden" name="memberNo" id="memberNo" value="${memberNo}">
	   	 	<input type="hidden" name="boardNo" value="${pageInfo.boardNo}">
	 </div>
	 <div>
	 	 <button type="button" class="replyWriteBtn">등록</button>

  	</div>
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
});

//파일삭제
$(document).on("click","#fileDelBtn", function(){
	$(this).parent().remove();
	let abled = document.querySelector('#addFile');
	abled.disabled = false;
	
});

//댓글등록
$(".replyWriteBtn").on("click", function(){
	  var formObj = $("form[name='replyForm']");
	  formObj.attr("action", "/board/replyWrite");
	  formObj.submit();
});

//댓글수정
function fn_modifyBtn(commentNo){
	//var contentID = "#content_" + commentNo;
	$.ajax({
		url : "/board/replyUpdate", 
		type : "post",
		dataType : "json",
		data : {"commentNo" : commentNo,
				"content" : $("#content_" + commentNo).val(),
				"boardNo" : $("#boardNo").val()
				},
		success : function(data){
				alert("수정되었습니다.");
				location.href = "/board/get?boardNo=" + $("#boardNo").val();
		}
	})
}

//댓글 삭제 
function fn_deleteBtn(commentNo){
	//var contentID = "#content_" + commentNo;
	$.ajax({
		url : "/board/replyDelete", 
		type : "post",
		dataType : "json",
		data : {"commentNo" : commentNo,
			"boardNo" : $("#boardNo").val()},
		success : function(data){
				alert("삭제되었습니다.");
				location.href = "/board/get?boardNo=" + $("#boardNo").val();
		}
	})
}
</script>

</html>