<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
     <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
     <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
 .pageInfo{
      list-style : none;
      display: inline-block;
    margin: 50px 0 0 100px;      
  }
  .pageInfo li{
      float: left;
    font-size: 20px;
    margin-left: 18px;
    padding: 7px;
    font-weight: 500;
  }
 a:link {color:black; text-decoration: none;}
 a:visited {color:black; text-decoration: none;}
 a:hover {color:black; text-decoration: underline;}
 
  .active{
      background-color: #cdd5ec;
  }
 
</style>

<meta charset="UTF-8">
<title>Insert title here</title>
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
</head>
<body>
<h1>목록페이지입니다.</h1>
<div class="table_wrap">
	<a href="/board/enroll" class="top_btn">작성하기</a>
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
						<td>${list.title}</td>
						<td>${list.memberName}</td>
						<td><fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${list.bregDate}"/></td>
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
	 <div class="pageInfo_wrap" >
	        <div class="pageInfo_area">
	        <ul id="pageInfo" class="pageInfo">
	        
	        <!-- 이전페이지 버튼 -->
                <c:if test="${pageMaker.prev}">
                    <li class="pageInfo_btn previous"><a href="${pageMaker.startPage-1}">Previous</a></li>
                </c:if>
	        
			<!-- 각 번호 페이지 버튼 -->
                <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                    <li class="pageInfo_btn ${pageMaker.cri.pageNum == num ? "active":"" }"><a href="${num}">${num}</a></li>
                </c:forEach>

				
			<!-- 다음페이지 버튼 -->
               <c:if test="${pageMaker.next}">
                   <li class="pageInfo_btn next"><a href="${pageMaker.endPage + 1 }">Next</a></li>
               </c:if> 
				
			</ul>
		</div>
    </div>
	
	<form id="moveForm" method="get">
		<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
       	<input type="hidden" name="amount" value="${pageMaker.cri.amount }"> 
	</form>
</div>

</body>

<script type="text/javascript">

$(".pageInfo a").on("click", function(e){
	 e.preventDefault();
     $("input[name='pageNum']").val($(this).attr("href"));
     $("#moveForm").attr("action", "/board/list");
     $("#moveForm").submit();
    
});



</script>
</html>