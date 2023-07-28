<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
    
    <style>
	   	<%@include file="cssAll.css"%>
	</style>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script>
		document.title = "Каталог ювелирных изделий";
		
		function addGood(userId, goodId){
			console.log(userId);
			let str = "userId="+ userId + "&goodId="+goodId;		
			$.ajax({
    			type:"POST",
    			url:"Catalog",
    			data: str,
    			success: function(answer){
    			alert(answer);
    		}
			});
		}		
	</script>
      
     
      <!-- Каталог товаров для пользователя -->
     
    <c:if test="${userId > 0 && access == 0 }">
    	<!-- Путь после меню -->       
	      <div class="container">
	        <ol class="breadcrumb">
	          <li class="nav-item"><a href="Main" class="nav-link px-2">Главная  ></a></li>
	          <li><a href="Main?action=catalog" class="nav-link px-2">Каталог</a></li>
	        </ol>
	      </div>	  
      <!-- ---------------------->
      
	    <!-- Генерация карточек товаров на странице -->    
	     <div class="row row-cols-1 row-cols-md-3 mb-3 text-center">    
		    <c:forEach var="good" items="${goods}" varStatus="num">     	
		        <div class="col">
		          <div class="card mb-4 rounded-3 shadow-sm">
		            <div class="card-header py-3">
		              <h4 class="my-0 fw-normal">${good.title}</h4>
		            </div>
		            <div class="card-body">
		              <span class="old-price"> ${good.oldPrice} ${item.currency}</span>
		              <h1 class="card-title pricing-card-title"> ${good.price}<small class="text-body-secondary fw-light"> ${good.currency}</small></h1>
		              <img id="good" src="./images/goods/${good.image}" alt="">
		              <ul class="list-unstyled mt-3 mb-4">
		                <li class="li-col">Минимальный вес: ${good.weight}</li>
		                <li class="li-col">Размер: ${good.size}</li>
		                <li class="li-col">Металл: ${good.metall} ${good.sample}</li>
		                <li class="li-col">Вставки: ${good.insertTo}</li>                     
		              </ul>
		              <div class="row mb-2 text-center">
		                <button type="button" class="w-50 btn btn-lg btn-outline-primary" disabled="disabled">Посмотреть</button>
		                <button type="button" class="w-50 btn btn-lg btn-outline-primary" onclick="addGood(${userId}, ${good.id})" >Купить</button>
		              </div>
		            </div>
		          </div>          
		         </div>     	
		    </c:forEach>
	      </div>
	       
	    <!-- Страницы внизу сайта для каталога -->
	    <div class="control flx">
	      <div class="mse2_pagination">
	          <ul class="pagination">
	            <li class="page-item disabled" wfd-invisible="true"><a class="page-link" href="#">Первая</a></li>
	            <li class="page-item disabled" wfd-invisible="true"><a class="page-link" href="#">«</a></li>
	            <li class="page-item active"><a class="page-link" href="#">1</a></li>
	            <li class="page-item"><a class="page-link" href="#">2</a></li>
	            <li class="page-item"><a class="page-link" href="#">3</a></li>
	            <li class="page-item"><a class="page-link" href="#">4</a></li>
	            <li class="page-item"><a class="page-link" href="#">5</a></li>
	            <li class="page-item"><a class="page-link" href="#">»</a></li>
	            <li class="page-item"><a class="page-link" href="#">Последняя</a></li>
	          </ul>            
	        </div>      
	    </div>    
    </c:if>
    
     <!-- Каталог товаров для администратора -->
     
    <c:if test="${userId > 0 && access == 1 }">
      	    
		<table id="allOrders">
        <tr class="allOrders-trtd">
          <th colspan="6" class="con" style="text-align: center;">Товары каталога</th>
        </tr>
      <tr class="allOrders-trtd">
          <td class="allOrders-trtd"><b>Номер</b></td>
          <td class="allOrders-trtd"><b>Изображение</b></td>
          <td class="allOrders-trtd"><b>Наименование</b></td>
          <td class="allOrders-trtd"><b>Артикул</b></td>
          <td class="allOrders-trtd"><b>Стоимость</b></td> 
       </tr>
       
       <c:forEach var="item" items="${goods}" varStatus="num">
       	<tr class="allOrders-trtd">
            <td class="allOrders-trtd">${num.count}</td>
            <td class="allOrders-trtd"><img id="good" src="./images/goods/${item.image}" alt=""></td>		
            <td class="allOrders-trtd">${item.title}</td>
            <td class="allOrders-trtd">${item.vendorCode}</td>
            <td class="allOrders-trtd">${item.price}</td>
          </tr>
       </c:forEach>    	
    	</table>    
		        	
    </c:if>
    
    
    
    <c:if test="${userId == 0 }">
    	<!-- Путь после меню -->       
	      <div class="container">
	        <ol class="breadcrumb">
	          <li class="nav-item"><a href="Main" class="nav-link px-2">Главная  ></a></li>
	          <li><a href="Main?action=catalog" class="nav-link px-2">Каталог</a></li>
	        </ol>
	      </div>	  
      <!-- ---------------------->
      
	    <!-- Генерация карточек товаров на странице -->    
	     <div class="row row-cols-1 row-cols-md-3 mb-3 text-center">    
		    <c:forEach var="good" items="${goods}" varStatus="num">     	
		        <div class="col">
		          <div class="card mb-4 rounded-3 shadow-sm">
		            <div class="card-header py-3">
		              <h4 class="my-0 fw-normal">${good.title}</h4>
		            </div>
		            <div class="card-body">
		              <span class="old-price"> ${good.oldPrice} ${item.currency}</span>
		              <h1 class="card-title pricing-card-title"> ${good.price}<small class="text-body-secondary fw-light"> ${good.currency}</small></h1>
		              <img id="good" src="./images/goods/${good.image}" alt="">
		              <ul class="list-unstyled mt-3 mb-4">
		                <li class="li-col">Минимальный вес: ${good.weight}</li>
		                <li class="li-col">Размер: ${good.size}</li>
		                <li class="li-col">Металл: ${good.metall} ${good.sample}</li>
		                <li class="li-col">Вставки: ${good.insertTo}</li>                     
		              </ul>
		              <div class="row mb-2 text-center">
		                <button type="button" class="w-50 btn btn-lg btn-outline-primary" disabled="disabled">Посмотреть</button>
		                <button type="button" class="w-50 btn btn-lg btn-outline-primary" onclick="addGood(${userId}, ${good.id})" >Купить</button>
		              </div>
		            </div>
		          </div>          
		         </div>     	
		    </c:forEach>
	      </div>
	       
	    <!-- Страницы внизу сайта для каталога -->
	    <div class="control flx">
	      <div class="mse2_pagination">
	          <ul class="pagination">
	            <li class="page-item disabled" wfd-invisible="true"><a class="page-link" href="#">Первая</a></li>
	            <li class="page-item disabled" wfd-invisible="true"><a class="page-link" href="#">«</a></li>
	            <li class="page-item active"><a class="page-link" href="#">1</a></li>
	            <li class="page-item"><a class="page-link" href="#">2</a></li>
	            <li class="page-item"><a class="page-link" href="#">3</a></li>
	            <li class="page-item"><a class="page-link" href="#">4</a></li>
	            <li class="page-item"><a class="page-link" href="#">5</a></li>
	            <li class="page-item"><a class="page-link" href="#">»</a></li>
	            <li class="page-item"><a class="page-link" href="#">Последняя</a></li>
	          </ul>            
	        </div>      
	    </div>    
    </c:if>
    