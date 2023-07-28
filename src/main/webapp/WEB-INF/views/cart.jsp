<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
    
    <style>
	   	<%@include file="cssAll.css"%>
	</style>
   
   	<!-- Подключение JSTL -->
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
    
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	
	<script>
	
		document.title = "Корзина товаров";
	<!-- удаление товара полностью из корзины -->
		function deleteGood(userId, goodId){
			let userAnswer = confirm("Вы действительно хотите удалить товар?");
			if (userAnswer){
				let str ="&act=delete" +"&userId="+ userId + "&goodId="+ goodId;		
				$.ajax({
	    			type:"POST",
	    			url:"Cart",
	    			data: str,
	    			success: function(answer){    			
	    			alert(answer);    			
	    			window.location.href = "Main?action=cart";
	    		}
				});
			}			
		}		
	
	<!-- изменение количества товара в корзине -->
		function updateCountGood(action, userId, goodId,count){
			let str ="&act=" + action +"&userId="+ userId + "&goodId="+ goodId + "&count="+count;		
			$.ajax({
    			type:"POST",
    			url:"Cart",
    			data: str,
    			success: function(){    			
    			window.location.href = "Main?action=cart";
    		}
			});
		}	
		
		<!-- по клику на кнопку уходим на страницу оформления товара -->
		function ordering(userId){  
			window.location.href = "Main?action=ordering";		
		}
		
	</script>
              
      <!-- Путь после меню -->    
      <div class="container">
        <ol class="breadcrumb">
          <li class="nav-item"><a href="Main" class="nav-link px-2">Главная  ></a></li>
          <li><a href="Main?action=cart" class="nav-link px-2">Корзина</a></li>
        </ol>
      </div>
      <!-- ---------------------->
    
    <!-- если в корзине есть товары -->
   <c:if test="${!cart.isEmpty()}"> 
    <!-- Генерация карточек товаров на странице -->
        
    <c:set var="allPrice" value="0" />
    <c:set var="all" value="0" />
    
   <h4 class="my-0 fw-normal" style="background: white; padding-left: 200px; padding-bottom: 0px;"> Ваш заказ: </h4>
    <table>            
    <c:forEach var="good" items="${cart}" varStatus="num">
    	<tr>
    		<td class="count-my">${num.count}</td>
    		<td><img id="good" src="./images/goods/${good.image}" alt=""></td>
    		<td><a class="title-cart-my" href="#">${good.title}</a></td>
    		<td>
    			<p id="count-cart">Количество</p>                   
		        <span class="count-my"><a href="#" class="count-round-my" onclick="updateCountGood('decline',${userId},${good.id},${good.count})">➖</a></span>
		        
		        <span class="count-my" id="span-count-my">${good.count}</span>
		        
		        <span><a href="#" class="count-round-my" onclick="updateCountGood('add', ${userId},${good.id},${good.count})">➕</a></span>   		             
	        </td>
	        <td>
	        	<a href="#"> <img src="./images/delete.png" alt="" onclick="deleteGood(${userId},${good.id})"></a>
	        	<span><a href="#" class="title-cart-my" onclick="deleteGood(${userId}, ${good.id})"> Удалить</a></span>
	        	<p class="price-cart-my">${good.price} ${good.currency}</p>
	        </td>
      	</tr>
      	<c:set var="all"> ${good.price*good.count}</c:set>
	    <c:set var="allPrice"> ${all+allPrice}</c:set>
    </c:forEach>
    </table>
    
	    
	 <div id="total_cost">
      <div class="row mb-3 text-center">
        <div class="col"><font style="vertical-align: inherit; padding-right: 450px; font-size: 24px;">ИТОГО</font></div>
        <div class="col"><font style="vertical-align: inherit; font-size: 24px; padding-left: 80px;">${allPrice} руб.</font></div>
      </div>
    </div>

    <!-- Кнопка оформления заказа -->
    <div style="padding-left: 850px;">
    	
    	<!-- Кнопка оформления заказа при открытой сессии -->      
    	<c:if test="${userId > 0 }">
    		<button type="button" class="w-30 btn btn-lg btn-outline-primary" onclick="ordering(${userId})">Оформить заказ</button>
    	</c:if>
    	
    	<c:if test="${userId == 0 }">
	   		<button type="button" class="w-30 btn btn-lg btn-outline-primary" disabled="disabled">Оформить заказ</button>
	   		<p>*Для оформления заказа необходимо авторизоваться.</p>
    	</c:if>      
    </div>
    </c:if>
    
    <!--если в корзине нет товаров -->
     <c:if test="${cart.isEmpty()}">
     	<div id="message">
        	<h3>К сожалению, в Вашей корзине пока нет товаров...</h3>        
   		</div>
     </c:if>