<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
    
    <style>
	   	<%@include file="cssAll.css"%>
	</style>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    
    
    <div class="row mb-3">
      <div class="col-6 themed-grid-col">
        <div id="phone">
          <img src="./images/phone.png" alt="">
          <p>8 (800) 500 - 50 - 50</p>        
        </div>
      </div>
      <div class="col-6 themed-grid-col">
        <div id="cart">
        
        <!-- Корзина покупок, личный кабинет адинистратору отключаем -->
        <c:if test="${access != 1 }">
          <a href="Main?action=cart"><img src="./images/shopping-bag.png" alt=""></a>
              
          <span>Корзина</span>                

          <a href="Main?action=avtorization"><img src="./images/personal.png" alt=""></a>
          <span>Личный кабинет</span>          
	    </c:if>
	    
	        <div id="in">
	        	<!-- Личный кабинет администратору не нужен -->
	        	<c:if test="${access != 1 }">
	        		<p><a href="Main?action=avtorization" class="nav-link px-2">${login}</a></p>
          		</c:if>
          		
          		<c:if test="${access == 1 }">
          			<p><a href="#" class="nav-link px-2">${login}</a></p>
          		</c:if>
          		
          		<!-- Кнопка Выход при открытой сессии -->      
    			<c:if test="${userId > 0 }">
    				<p><a href="Main?action=exit" class="nav-link px-2">Выход</a></p>
				</c:if>         		 
          	
          	</div>	                                             
        </div>
      </div>
    </div>
     <!-- --------------------  -->
      
      
      <c:if test="${(userId == 0)}">
      	<!-- Меню сверху для user-->  
        <div class="container">
          <header class="py-3 my-4">
            <ul class="nav justify-content-center border-bottom pb-3 mb-3">
              <li class="nav-item"><a href="Main" class="nav-link px-2-top">Главная</a></li>
              <li class="nav-item"><a href="Main?action=catalog" class="nav-link px-2-top">Каталог</a></li>
              <li class="nav-item"><a href="Main?action=promotions" class="nav-link px-2-top">Новинки</a></li>
              <li class="nav-item"><a href="Main?action=promotions" class="nav-link px-2-top">Акции</a></li>
              <li class="nav-item"><a href="Main?action=promotions" class="nav-link px-2-top">Отзывы</a></li>
              <li class="nav-item"><a href="Main?action=promotions" class="nav-link px-2-top">Контакты</a></li>
            </ul>          
          </header>
        </div>
      </c:if>
      
            
        
      <c:if test="${(userId > 0 && access == 0)}">
      	<!-- Меню сверху для user-->  
        <div class="container">
          <header class="py-3 my-4">
            <ul class="nav justify-content-center border-bottom pb-3 mb-3">
              <li class="nav-item"><a href="Main" class="nav-link px-2-top">Главная</a></li>
              <li class="nav-item"><a href="Main?action=catalog" class="nav-link px-2-top">Каталог</a></li>
              <li class="nav-item"><a href="Main?action=promotions" class="nav-link px-2-top">Новинки</a></li>
              <li class="nav-item"><a href="Main?action=promotions" class="nav-link px-2-top">Акции</a></li>
              <li class="nav-item"><a href="Main?action=promotions" class="nav-link px-2-top">Отзывы</a></li>
              <li class="nav-item"><a href="Main?action=promotions" class="nav-link px-2-top">Контакты</a></li>
            </ul>          
          </header>
        </div>
      </c:if>
      
      
      <c:if test="${userId > 0 && access == 1 }">
      	<!-- Меню сверху для admin-->  
        <div class="container">
          <header class="py-3 my-4">
            <ul class="nav justify-content-center border-bottom pb-3 mb-3">
              <li class="nav-item"><a href="Main?action=catalog" class="nav-link px-2-top">Каталог</a></li>
              <li class="nav-item"><a href="Main?action=usersAdmin" class="nav-link px-2-top">Пользователи</a></li>
              <li class="nav-item"><a href="Main?action=orderInfo" class="nav-link px-2-top">Заказы</a></li>
            </ul>          
          </header>
        </div>
      </c:if>      
      