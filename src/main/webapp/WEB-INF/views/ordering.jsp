<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
    
	<style>
    	<%@include file="cssAll.css"%>
	</style>

    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    
    <script>
    	document.title = "Оформление заказа";
    	
    	<!-- удаление товара полностью из корзины -->
		function makeOrder(userId){
			console.log("id user " + userId);
			let data = "&userId="+ userId 
						+ "&firstName="+ $('#firstName').val()
						+ "&lastName="+ $('#lastName').val()
						+ "&phoneUser="+ $('#phoneUser').val()
						+ "&email="+ $('#email').val();
			$.ajax({
				type: "POST",
				url: "Ordering",
				data: data,
				success:function(answer){
					if (answer === "false"){
						alert("Введены некорректные данные!");
						window.location = 'Main?action=ordering';
					} else{
						window.location='Main?action=ordering&isOrdering=' + answer;
					}											
				}					
    		})
		}    	
    </script>
    
<!-- Форма ввода данных при оформлении заказа -->
       
       <!-- Если заказ не оформлен -->
       <c:if test="${!isOrdering}">
       
	   	<div class="container" id="order-form">
	    	<div class="col-md-7 col-lg-8">
	            <h4 class="mb-3" style="text-align: center;">Для оформления заказа введите данные</h4>
	            <hr class="my-4">
	            <br>
       	 		<form>
              
              	<div class="row g-3">
              	
	                <div class="col-sm-6">
		                <div class="form-group">
		                	<label for="firstName" class="form-label">Имя</label>
	                    	<input class="form-control item" type="text" name="firstName" id="firstName" placeholder="" required>
	                	</div>                
	                </div>
	                
	                <div class="col-sm-6">
		                <div class="form-group">
		                	<label for="lastName" class="form-label">Фамилия</label>
	                    	<input class="form-control item" type="text" name="lastName" id="lastName" placeholder="" required>
	                	</div>                
	                </div>
	                
	                <div class="col-12">
		                <div class="form-group">
		                	<label for="phoneUser" class="form-label">Телефон</label>
	                    	<input class="form-control item" type="text" name="phoneUser" id="phoneUser" placeholder="8" required>
	                	</div>                
	                </div>
	                
	                <div class="col-12">
		                <div class="form-group">
		                	<label for="email" class="form-label">Электронная почта</label>
	                    	<input class="form-control item" type="text" name="email" id="email" placeholder="you@example.com" required>
	                	</div>                
	                </div>
	                
          		</div>             
                
               <hr class="my-4"> <br>
              
              <div style="padding-left: 170px;">
                <button type="submit" class="w-30 btn btn-lg btn-outline-primary" onclick="makeOrder(${userId})">Оформить</button>
              </div>
                                
            </form>       
                   
          </div>
        </div>
       </c:if>
       
       <!-- Сообщение об успешном оформлении заказа -->
       <c:if test="${isOrdering}">
       		<div id="message">
        		<h1>Ваш заказ успешно оформлен!</h1>
        		<br><br><br>
        		<h3>Более подробная информация в <a href="Main?action=avtorization" class="nav-link px-2">личном кабинете.</a></h3>
   			</div>       		
       </c:if>
        
       