<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.pageInfo {
	list-style: none;
	display: inline-block;
	margin: 50px 0 0 100px;
}

.pageInfo li {
	float: left;
	font-size: 20px;
	margin-left: 18px;
	padding: 7px;
	font-weight: 500;
}

a:link {
	color: black;
	text-decoration: none;
}

a:visited {
	color: black;
	text-decoration: none;
}

a:hover {
	color: black;
	text-decoration: underline;
}

.active {
	background-color: #cdd5ec;
}

.move {
	text-decoration : line-through;
}
</style>

<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
</head>
<body>
	<div class="table_wrap">
		
		<div class="search_wrap">
			<div class="search_area">
			<select name="type">
                <option value="" <c:out value="${pageMaker.cri.type == null?'selected':'' }"/>>--</option>
                <option value="T" <c:out value="${pageMaker.cri.type eq 'T'?'selected':'' }"/>>제목</option>
                <option value="W" <c:out value="${pageMaker.cri.type eq 'W'?'selected':'' }"/>>등록자</option>
            </select>  
				<input type="text" name="keyword" value="${pageMaker.cri.keyword }">
				<button>Search</button>
			</div>
		</div>
		<table>
			<thead>
				<tr>
					<th class="bno_width">번호</th>
					<th class="title_width">제목</th>
					<th class="writer_width">등록자</th>
					<th class="regdate_width">등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${fn:length(list) > 0}">
						<c:forEach items="${list }" var="list">
						
							<tr>
								<td>${list.boardNo}</td>
								
							 	<td class="<c:if test="${list.isEnabled eq 'Y'}">move</c:if>" >
							 		<a href='/board/get?boardNo=<c:out value="${list.boardNo}"/>' >
                       					<span class="titleList">${list.title}</span></a></td>
								<td><span class="maskingName">${list.memberName}</span></td>
								<td><fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${list.bregDate}" /></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="4">조회된 결과가 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		
		<%-- 페이징 Start --%>
		<div class="pageInfo_wrap">
			<div class="pageInfo_area">
				<ul id="pageInfo" class="pageInfo">
					<c:if test="${pageMaker.prev}">
						<li class="pageInfo_btn previous">
							<a href="${pageMaker.startPage-1}">Previous</a>
						</li>
					</c:if>
					
					<c:forEach var="num" begin="${pageMaker.startPage}"
						end="${pageMaker.endPage}">
						<li class="pageInfo_btn ${pageMaker.cri.pageNum == num ? "active":"" }">
						<a href="${num}">${num}</a></li>
					</c:forEach>
					
					<c:if test="${pageMaker.next}">
						<li class="pageInfo_btn next"><a href="${pageMaker.endPage + 1 }">Next</a></li>
					</c:if>
				</ul>
			</div>
		</div>
		<%-- 페이징 End --%>
		
		<a href="/board/enroll" class="top_btn">작성하기</a>

		<form id="moveForm" method="get">
			<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
			<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
			<input type="hidden" name="keyword" value="${pageMaker.cri.keyword }">
			<input type="hidden" name="type" value="${pageMaker.cri.type }">
		</form>
	</div>

</body>

<script type="text/javascript">

//마스킹처리
function checkNull(str){
	if(typeof str == "undefined" || str == null || str == ""){
		return true;
	}
	else{
		return false;
	}
};

//마스킹처리
$( document ).ready(function() {
 $(".maskingName").each(function(){
	 var str = $(this).text();
	
	let maskingStr  = masking(str);
	 $(this).text(maskingStr);
    });
});


$(".pageInfo a").on("click", function(e){
	 e.preventDefault();
     $("input[name='pageNum']").val($(this).attr("href"));
     $("#moveForm").attr("action", "/board/list");
     $("#moveForm").submit();
    
});

$(".search_area button").on("click", function(e){
    e.preventDefault();
    let val = $("input[name='keyword']").val();
    $("#moveForm").find("input[name='keyword']").val(val);
    $("#moveForm").find("input[name='pageNum']").val(1);
    $("#moveForm").find("input[name='type']").val();
    $("#moveForm").submit();
});


$(".search_area button").on("click", function(e){
    e.preventDefault();
    
    let type = $(".search_area select").val();
    let keyword = $(".search_area input[name='keyword']").val();
    
    if(!type){
        alert("검색 종류를 선택하세요.");
        return false;
    }
    
    if(!keyword){
        alert("키워드를 입력하세요.");
        return false;
    }        
    
    $("#moveForm").find("input[name='type']").val(type);
    $("#moveForm").find("input[name='keyword']").val(keyword);
    $("#moveForm").find("input[name='pageNum']").val(1);
    $("#moveForm").submit();
});


/* let moveForm = $("#moveForm");

$(".move").on("click", function(e){
    e.preventDefault();
    
    moveForm.append("<input type='hidden' name='boardNo' value='"+ $(this).attr("href")+ "'>");
    moveForm.attr("action", "/board/get");
    moveForm.submit();
}); */

//글 제목이 20자 이상이면 ...처리
$(".titleList").each(function(){
    var length = 10; //표시할 글자수 정하기
    $(this).each(function(){
        if( $(this).text().length >= length ){
            $(this).text( $(this).text().substr(0,length)+'...') 
            //지정할 글자수 이후 표시할 텍스트
        }
    });
});

//이름 마스킹 처리
function masking(str){
	let originStr = str;
	let maskingStr;
	let strLength;
	
	if(this.checkNull(originStr) == true){
		return originStr;
	}
	
	strLength = originStr.length;
	
	if(strLength < 4){
		maskingStr = originStr.replace(/(?<=.{1})./gi, "*");
	}else {
		maskingStr = originStr.replace(/(?<=.{2})./gi, "*");
	}
	
	return maskingStr;
}



</script>
</html>