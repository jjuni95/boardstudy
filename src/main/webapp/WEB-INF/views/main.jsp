<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/main.css">
</head>
<style type="text/css">
#main{
	/* width: 100%; */
    height : 800px;
    border: 1px solid black;
    margin-top: 5px;
    margin-right: 15px;
    margin-bottom: 5px;
    margin-left: 15px;
	}
#slideShow{
	margin: 0 auto;
	height : 50%;
}

.slides{
	margin-top: 65px;
}

#bottom{
	margin: 0 auto;
	height : 50%;
}

#board{
	    width: 50%;
	    height : 100%;
        float: left;
        box-sizing: border-box;
        border: 1px solid black;
        padding: 40px;
}

hr{
	 margin-top: 1.5em;
}

#btn{
	float: right;
    margin: -15px;
    width: 100px;
    height: 50px;
}

#gallery{
	    width: 50%;
	    height : 100%;
        float: right;
        box-sizing: border-box;
        border: 1px solid black;
        padding: 40px;
}

.titleList{
	display: inline-block;
    width: 400px;
    margin: 10px;
}

.gImg{
	width: 120px;
	height: 120px;
}

.imgLi {
	float: left;
	width: 33%;
	text-align: center;
	height: 50%;
}

.glistImg{
	width: 800px;
    height: 250px;
    margin-top: 20px;
    margin-left: 30px;
    list-style:none;
}

.imgClass{
	width: 150px;
    height: 150px;
}
</style>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
	
<div id="main">
	<%-- 메인 배너 --%>
	<div id="slideShow">
		<ul class="slides">
			<li><img src="/resources/image/flower.jpg" alt=""></li>
			<li><img src="/resources/image/fox.jpg" alt=""></li>
			<li><img src="/resources/image/lightning.jpg" alt=""></li>
			<li><img src="/resources/image/moon.jpg" alt=""></li>
		</ul>
		<p class="controller">
			<span class="prev">&lang;</span>
			<span class="next">&rang;</span>
		</p>
	</div>
	
	<%-- 게시판 --%>
	<div id=bottom>
		<div id="board">
			<div>
				<span>게시 글</span>
				<a href="http://localhost:8080/board/list">
					<button id="btn" type="button">더보기</button>
				</a>
			</div>
		<hr />
			<c:forEach items="${list }" var="list">
				<tr>
				 	<td><span class="titleList">${list.title}</span></td>
					<td><fmt:formatDate pattern="yyyy.MM.dd" value="${list.bregDate}" /></td>
					<br>
				</tr>
			</c:forEach>
		</div>
		
		<%-- 자유갤러리 --%>
		<div id="gallery">
			<div>
				<span>자유 갤러리</span>
				<a href="http://localhost:8080/gboard/glist">
					<button id="btn" type="button">더보기</button>
				</a>
			</div>
		<hr />
			<ul class="glistImg">
				<c:forEach items="${glist}" var = "glist">
					<li class="imgLi">
						<div class="imgClass">
							<img  class="gImg" src="/gfilelist/<c:out value="${glist.savedfileName}" />">
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />

<script type="text/javascript">

const slides = document.querySelector('.slides'); //전체 슬라이드 컨테이너
const slideImg = document.querySelectorAll('.slides li'); //모든 슬라이드들
let currentIdx = 0; //현재 슬라이드 index
const slideCount = slideImg.length; // 슬라이드 개수
const prev = document.querySelector('.prev'); //이전 버튼
const next = document.querySelector('.next'); //다음 버튼
const slideWidth = 300; //한개의 슬라이드 넓이
const slideMargin = 100; //슬라이드간의 margin 값

//전체 슬라이드 컨테이너 넓이 설정
slides.style.width = (slideWidth + slideMargin) * slideCount + 'px';

function moveSlide(num) {
  slides.style.left = -num * 400 + 'px';
  currentIdx = num;
}

prev.addEventListener('click', function () {
  /*첫 번째 슬라이드로 표시 됐을때는 
  이전 버튼 눌러도 아무런 반응 없게 하기 위해 
  currentIdx !==0일때만 moveSlide 함수 불러옴 */

  if (currentIdx !== 0) moveSlide(currentIdx - 1);
});

next.addEventListener('click', function () {
  /* 마지막 슬라이드로 표시 됐을때는 
  다음 버튼 눌러도 아무런 반응 없게 하기 위해
  currentIdx !==slideCount - 1 일때만 
  moveSlide 함수 불러옴 */
  if (currentIdx !== slideCount - 1) {
    moveSlide(currentIdx + 1);
  }
});

//글 제목이 20자 이상이면 ...처리
$(".titleList").each(function(){
    var length = 20; //표시할 글자수 정하기
    $(this).each(function(){
        if( $(this).text().length >= length ){
            $(this).text( $(this).text().substr(0,length)+'...') 
            //지정할 글자수 이후 표시할 텍스트
        }
    });
});
</script>
</body>
</html>