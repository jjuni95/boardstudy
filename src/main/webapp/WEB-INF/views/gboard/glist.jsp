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
 text-align: center;
  }  
 
#loading-image {   
 position: absolute;   
 top: 50%;   
 left: 50%;  
 z-index: 100;
 } 
 
 html, body {
	height:100%;
	margin:0; 
	padding:0;
 }
 
 .galleryList {
 	margin-top:100px;
	height:640px;
	overflow:auto; 
 }
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<a href="/gboard/gwrite" class="top_btn">등록</a>
	<form id="delete_form" method="post">
	
		<div class="galleryList">
			<ul class="ulClass">
					<c:forEach items="${list}" var = "list">
						<li class="imgLi">
							<input type="hidden" name="galleryNo" id="galleryNo" value="${list.galleryNo}">
						<div class="imgClass">
							<input type="button" value="삭제" class="delete" onclick="fn_delete(${list.galleryNo});"/>
							<%-- <img  src="${pageContext.request.contextPath}/resources/image/gallery/<c:out value="${list.savedfileName}" />"> --%>
							<img  src="/gfilelist/<c:out value="${list.savedfileName}" />">
						</div>
						</li>
					</c:forEach>
			</ul>
			<div id="loading">
				<img id="loading-image" src="${pageContext.request.contextPath}/resources/image/loding.gif" alt="Loading..." />
			</div>
		</div>
	</form>
</body>
<script type="text/javascript">
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

let cPage = 2;
//next_load();
let isLoad = false;
$('#loading').hide(); 
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
				html+="<li class='imgLi'>"
				html+="<div class='imgClass'>"
				html+="<input type='button' value='삭제' class='delete' onclick='fn_delete(" + i.galleryNo + ");'/>"
				html+="<img  src='/gfilelist/" + i.savedfileName + " '>"
				html+="</div>"
				html+="</li>"
				
			})
			html+="<script type='text/javascript'>"
			html+="$(document).ready(function(){"	
			html+="$('.imgClass').on('mouseover',function(){"
			html+="$(this).addClass('on');"
			html+="});"
			html+="$('.imgClass').on('mouseout',function(){"
			html+="$(this).removeClass('on');"
			html+="});"
			html+="});" 
			
			$(".ulClass").append(html);
			
			cPage++
			isLoad=false;
			$('#loading').hide();   
		} 
	});
	
	
}

 $('.galleryList').scroll(function(){
      //if($(window).scrollTop()+50>=$(document).height() - $(window).height()){
    	  
		if(!isLoad){
			//false하고 데이터 받아오고 뿌리고 true 
			isLoad = true;
			$('#loading').show();   
			
			next_load();
		}
    //} 

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