<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
img {
   width: 300px;
   height: 300px;
   margin:5px;border:#FFF 2px solid;
   position : relative;
}
img:hover {
border:#F00 2px solid;
}
.on .delete{
	
    display: block;
	
}
.delete{
	display: none;
    width: 50px;
	height: 30px;
	position: absolute;
	left: 300px;
    margin-top: 15px;
    z-index : 99; 
}
.imgLi {
	float: left;
	width: 23%;
	position : relative;
	text-align: center;
}
ul{
   list-style:none;
   }
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
</head>
<body>
	<a href="/gboard/gwrite" class="top_btn">등록</a>
	<form id="delete_form" method="post">
	
		<div class="galleryList">
				<ul>
					<c:forEach items="${list}" var = "list">
						<li class="imgLi">
								<input type="hidden" name="galleryNo" id="galleryNo" value="${list.galleryNo}">
							<div class="imgClass">
								<input type="button" value="삭제" class="delete" onclick="fn_delete(${list.galleryNo});"/>
								<img  src="${pageContext.request.contextPath}/resources/image/gallery/<c:out value="${list.savedfileName}" />">
							</div>
						</li>
					</c:forEach>
				</ul>
		</div>
	</form>
</body>

<script type="text/javascript">

next_load(1);
function next_load(page){
	$.ajax({
	url : "/gboard/plusList", 
	type : "get",
	dataType : "json",
	data : {"galleryCnt" : page},
	success : function(data){
		console.log(data);
		
			var html = "";
			data.forEach(i => {
				//console.log(i.ORIGINFILE_NAME);
				//let html = $(".galleryList ul").html();
				//console.log(html);
				html+="<li class='imgLi'>"
				html+="<div class='imgClass'>"
				html+="<input type='button' value='삭제' class='delete' onclick='fn_delete(" + i.galleryNo + ");'/>"
				html+="<img  src='${pageContext.request.contextPath}/resources/image/gallery/" + i.savedfileName + " '>"
				html+="</div>"
				html+="</li>"
				
				$(".galleryList").append(html);
			})
			page++;
			 
	} 
	
	});
}

$(window).scroll(function(){
    if($(window).scrollTop()+200>=$(document).height() - $(window).height())
    {
        if(!loading)    //실행 가능 상태
        {
            loading = true; //실행 불가능 상태로 변경
            next_load(); 
        }
        else            //실행 불가능 상태
        {
            alert('다음페이지를 로딩중입니다.');  
        }
    }
});

$(document).ready(function(){	
	
	//마우스올리면 삭제버튼
	$('.imgClass').on('mouseover',function(){
		$(this).addClass('on');
	});
	$('.imgClass').on('mouseout',function(){
		$(this).removeClass('on');
	});
});

//자유갤러리 삭제 
function fn_delete(galleryNo){
	$.ajax({
		url : "/gboard/delete", 
		type : "post",
		dataType : "json",
		data : {"galleryNo" : galleryNo},
		success : function(data){
				alert("삭제되었습니다.");
				location.href = "/gboard/glist";
		}
	})
} 

var count = 8;

</script>
</html>