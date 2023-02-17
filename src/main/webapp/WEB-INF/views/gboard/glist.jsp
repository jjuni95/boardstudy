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
   
   #loading {
 width: 100%;   
 height: 100%;   
 top: 0px;
 left: 0px;
 position: fixed;   
 display: block;   
 opacity: 0.7;   
 background-color: #fff;   
 z-index: 99;   
 text-align: center; }  
 
#loading-image {   
 position: absolute;   
 top: 50%;   
 left: 50%;  
 z-index: 100; 
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
				</ul>
				<div id="loading">
					<img id="loading-image" src="${pageContext.request.contextPath}/resources/image/loding.gif" alt="Loading..." />
				</div>
		</div>
	</form>
</body>

<script type="text/javascript">
let cPage = 1;
next_load();
let isLoad = true; //로딩 가능
function next_load(){
	$.ajax({
	url : "/gboard/plusList", 
	type : "get",
	dataType : "json",
	data : {"galleryCnt" : cPage},
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
				
			})
			$(".galleryList").append(html);
			isLoad = true;
			$('#loading').hide();   
			cPage++
			
			 
	} 
	
	});
}

$(window).scroll(function(){
    if($(window).scrollTop()+50>=$(document).height() - $(window).height()){
		if(isLoad){
			//false하고 데이터 받아오고 뿌리고 true 
			isLoad = false;
			$('#loading').show();   
			next_load();
			
		}
    }
});



//마우스올리면 삭제버튼
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


</script>
</html>