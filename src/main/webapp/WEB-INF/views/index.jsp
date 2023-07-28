<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
	   
	<style>
	   	<%@include file="cssAll.css"%>
	</style>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Интернет-магазин ювелирных изделий</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/masonry-layout@4.2.2/dist/masonry.pkgd.min.js" integrity="sha384-GNFwBvfVxBkLMJpYMOABq3c+d3KnQxudP/mGPkzpZSTYykLBNsZEnG2D9G/X/+7D" crossorigin="anonymous" async></script>

</head>
<body>
	<div id="container-main">
		<!-- Подключение header.jsp -->
		<jsp:include page="header.jsp" />		 	
	    <!-- -------------------- -->
		
		<!-- Подключение контента -->
		<jsp:include page="${content}" />
		
			
		<!-- Подключение footer.jsp -->
		<jsp:include page="footer.jsp" />
	
	</div>
</body>
</html>