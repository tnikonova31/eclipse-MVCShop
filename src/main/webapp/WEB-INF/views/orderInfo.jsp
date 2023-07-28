<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
    
    <style>
	   	<%@include file="cssAll.css"%>
	</style>
   
   	<!-- Подключение JSTL -->
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
    
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	
	<script>
		document.title = "Информация о заказе";
		
		function changeStatus(orderId, status){
			let userAnswer = confirm("Вы действительно хотите изменить статус заказа?");
			if (userAnswer){
				let status2;
				if(status == 1){
					status2=1;
				}else{
					status2=2;
				}
								
				let str = "&orderId=" + orderId +"&status="+ status2;
				$.ajax({
	    			type:"GET",
	    			url:"Ordering",
	    			data: str,
	    			success: function(answer){    		    			 			
	    			window.location.href = "Main?action=orderInfo";
	    		}
				});					
			}
		}
	</script>
              
      <!-- Путь после меню -->    
      <div class="container">
        <ol class="breadcrumb">
        <c:if test="${access == 0 }">
          <li class="nav-item"><a href="Main" class="nav-link px-2">Главная  ></a></li>
          <li><a href="Main?action=cart" class="nav-link px-2">Информация о заказе</a></li>
        </c:if>     	
        
        </ol>
      </div>
      <!-- ---------------------->
    
    <!-- Информация о заказе для пользователя -->
     
    <c:if test="${access == 0 && !orderInfo.isEmpty()}">
    	<h3>Информация о заказе </h3>
   
	   <c:set var="flag" value="0" />
	   <c:set var="allPrice" value="0" />
	   <c:set var="all" value="0" />
	   
	   <table>            
	    <c:forEach var="item" items="${orderInfo}" varStatus="num">    	
    		<c:if test="${item.orderId != flag}">
    			<tr>
    				<td class="count-my"> Номер заказа: ${item.orderId}</td>
 					<td class="count-my"></td>
    				<td class="count-my"> </td>
    				<td class="count-my"> </td>
    				<td class="count-my"> </td>
    				<c:set var="flag"> ${item.orderId}</c:set>    			    	
      			</tr>
      			<tr>
    				<td class="count-my">Дата оформления: ${item.dateCreated}</td>
 					<td class="count-my"></td>
    				<td class="count-my"> </td>
    				<td class="count-my"> </td>
    				<td class="count-my"> </td>
    				<c:set var="flag"> ${item.orderId}</c:set>    			    	
      			</tr>      			      			
    		</c:if>
    		
    		<c:if test="${item.orderId == flag}">
    		<tr>
    			<td class="count-my"> </td>
 				<td class="count-my"> </td>
    			<td class="count-my"><img id="good" src="./images/goods/${item.image}" alt=""></td>
    			<td class="count-my">Количество: ${item.count}</td>
    			<td class="count-my">Цена: ${item.price}</td>    			
    			<c:set var="all"> ${item.price*item.count}</c:set>
	    		<c:set var="allPrice"> ${all+allPrice}</c:set>  	    		
    		</tr>
    		</c:if>	        
      </c:forEach>	
	     </table> 
	     
	     <div id="total_cost">
	      <div class="row mb-3 text-center">
	        <div class="col"><font style="vertical-align: inherit; padding-right: 100px; font-size: 24px;">Сумма заказа: </font></div>
	        <div class="col"><font style="vertical-align: inherit; font-size: 24px; padding-left: 80px;">${allPrice} руб.</font></div>
	      </div>
	    </div>  
	       
	   <a href="Main?action=avtorization" class="nav-link px-2">Назад в личный кабинет</a>   
	    </div>    
    </c:if>
    
    
    <!-- У пользователя пока нет заказов -->
    <c:if test="${access == 0 && orderInfo.isEmpty()}">
    	<div id="message">
        	<h3>К сожалению, у Вас пока нет оформленных заказов...</h3>        
   		</div>
    </c:if>
    
    <!-- Информация о заказах для администратора -->
    <c:if test="${access == 1 }">
    	
    	<table id="allOrders">
        <tr class="allOrders-trtd">
          <th colspan="6" class="con" style="text-align: center;">Заказы пользователей</th>
        </tr>
      <tr class="allOrders-trtd">
          <td class="allOrders-trtd"><b>Номер заказа</b></td>
          <td class="allOrders-trtd"><b>Дата оформления</b></td>
          <td class="allOrders-trtd"><b>ФИО покупателя</b></td>
          <td class="allOrders-trtd"><b>Контактный телефон</b></td>
          <td class="allOrders-trtd"><b>Статус заказа</b></td>
          <td class="allOrders-trtd"><b>Изменить статус заказа</b></td>
       </tr>
       
       <c:forEach var="item" items="${usersOrders}" varStatus="num">
       	<tr class="allOrders-trtd">
            <td class="allOrders-trtd">${item.orderId}</td>
            <td class="allOrders-trtd">${item.dateCreated}</td>		
            <td class="allOrders-trtd">${item.lastName} ${item.firstName} ${item.surName}</td>
            <td class="allOrders-trtd">${item.phoneUser}</td>
            <td class="allOrders-trtd">${item.status}</td>
            <td class="allOrders-trtd">
               
               <c:set var="flagStatus" value="0" />
               <c:if test="${item.status.equals('оформлен')}">
               		<c:set var="flagStatus" value="1" />
               </c:if>               
               <button type="button" onclick="changeStatus(${item.orderId},${flagStatus})">Изменить</button>
            
            </td>	
          </tr>
       </c:forEach>    	
    	</table>
    </c:if>
   
    
    
    