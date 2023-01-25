<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
     <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
     <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
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
</div>

</body>
</html>