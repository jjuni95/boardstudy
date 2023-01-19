<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
     <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	<a href="/board/enroll" class="top_btn">게시판 등록</a>
	<table>
		<thead>
			<tr>
				<th class="bno_width">번호</th>
				<th class="title_width">제목</th>
				<th class="writer_width">등록자</th>
				<th class="regdate_width">등록일</th>
			</tr>
		</thead>
		<c:forEach items="${list}" var="list">
			<tr>
				<td><c:out value="${list.boardNo}"/></td>
                <td><c:out value="${list.title}"/></td>
                <td>${decWriter}</td>
                <td><fmt:formatDate pattern="yyyy/MM/dd" value="${list.regdate}"/></td>
			</tr>
		</c:forEach>
	</table>
</div>

</body>
</html>