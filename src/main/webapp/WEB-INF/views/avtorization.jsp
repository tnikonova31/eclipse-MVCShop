<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
    
	<style>
    	<%@include file="cssAll.css"%>
	</style>
       
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	
	<script>
		document.title = "Авторизация";
		
		window.onload = function(){
			document.querySelector("form").onsubmit = function(e){
				e.preventDefault();//отменяем перегрузку страницы по событию submit
				$.ajax({
					type: "POST",
					url: "Avtorization",
					data: $("form").serialize(),
					success:function(answer){
						if(answer==="Авторизация выполнена. Добро пожаловать!"){
							alert(answer);
							window.location='Main';
						}
						else{
							alert(answer);
							window.location='Main?action=avtorization';
						}						
					}					
	    		})
			}			
		}		
	</script>

        
      <!-- Путь после меню -->    
      <div class="container">
        <ol class="breadcrumb">
          <li class="nav-item"><a href="Main" class="nav-link px-2">Главная  ></a></li>
          <c:if test="${userId == 0 }">
          	<li><a href="Main?action=avtorization" class="nav-link px-2">Авторизация</a></li>
          </c:if>
          <c:if test="${userId>0 }">
          	<li><a href="Main?action=avtorization" class="nav-link px-2">Личный кабинет</a></li>
          </c:if>
          
        </ol>
      </div>
      <!-- -------------------- -->
         
    <!-- Никто не авторизовался --> 
    <c:if test="${userId == 0 }">
    	<div class="registration-cssave">
            <form>
              <div class="row mb-3 text-center">
                <div class="col-6 themed-grid-col"><a href="Main?action=avtorization" class="nav-link px-2-check">Авторизация</a></div>
                <div class="col-6 themed-grid-col"><a href="Main?action=registration" class="nav-link px-2">Регистрация</a></div>
              </div>           
                <div class="form-group">
                    <input class="form-control item" type="text" name="login" id="login" placeholder="Логин" required>
                </div>
                <div class="form-group">
                    <input class="form-control item" type="password" name="password" id="password" placeholder="Пароль" required>
                </div>               
                <div class="form-group">
                    <button class="btn btn-primary btn-block create-account" type="submit">Вход в аккаунт</button>
                </div>                
            </form>
        </div> 
	</c:if>
	
	 <!-- У нас есть открытая сессия и это не администратор -->
	 <!-- выводим заказы пользователя -->      
    <c:if test="${userId > 0 && access ==0 && !ordersInfo.isEmpty()}">
    
    	<h4 class="my-0 fw-normal" style="background: white; padding-left: 200px; padding-bottom: 0px;"> Ваши заказы: </h4> <br>
    	
    	<c:set var="flag" value="0" /> 
    	<c:set var="allPrice" value="0" />
    	<c:set var="all" value="0" />
       	
    	<c:forEach var="order" items="${ordersInfo}" varStatus="num">
    	  
    	  <c:if test="${order.orderId != flag}">
    	  	<div class="order-cart">
    	  		<div class="col">
    	  </c:if>
    	  
    	  <c:if test="${order.orderId != flag}">   	  
    	  				      		          
		         <div class="card mb-4 rounded-3 shadow-sm">
		            <div class="card-header py-3">
			            <h4 class="my"> Заказ № ${order.orderId} от ${order.dateCreated} </h4>
			            <h4 class="my"> Статус: ${order.statusOrder}.</h4>
			        </div>
			        <div class="card-body"> 
			       
			        <c:forEach var="item" items="${ordersInfo}" varStatus="num">
			        	<c:if test="${order.orderId == item.orderId}">
					      <img id="good-order" src="./images/goods/${item.image}" alt="">
				        </c:if>
		            </c:forEach>
		           </div> 
			</c:if>
		    		            
		    <c:if test="${order.orderId != flag}">
		    	<div class="card-header py-3">
		     		<c:set var="id"> ${order.orderId}</c:set>
		     		<h4 class="my"><a href="Main?action=orderInfo&orderId=${order.orderId}" class="nav-link px-2">Подробнее</a>  </h4>
		     	</div>
		     <!-- <div style="padding-left: 20px;">
		    		<button type="button" class="w-30 btn btn-lg btn-outline-primary" style="margin-bottom: 7px;">Подробнее</button>                     
		    	</div> -->
		    	
		    </c:if>
		         </div>  
		 
		 
		 <c:if test="${order.orderId != flag}">
    	  	<c:set var="flag"> ${order.orderId}</c:set>
    	  	</div>       
		  </div>
    	 </c:if>     
    		      
	    </c:forEach>  	 
	      
	   </c:if>   
	   
	   <!-- У пользователя пока нет заказов -->
    <c:if test="${userId > 0 && access ==0 && ordersInfo.isEmpty()}">
    	<div id="message">
        	<h3>К сожалению, у Вас пока нет оформленных заказов...</h3>        
   		</div>
    </c:if>
	   
	   
	    <!-- У нас есть открытая сессия и это администратор -->
	       
    <c:if test="${userId > 0 && access == 1 }">
    	
    	<h4 class="my-0 fw-normal" style="background: white; padding-left: 200px; padding-bottom: 0px;"> Личный кабинет АДМИНИСТРАТОРА </h4> <br>
    	
          
	</c:if>
	
       
  