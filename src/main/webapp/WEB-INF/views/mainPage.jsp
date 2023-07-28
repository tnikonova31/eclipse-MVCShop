 <%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
    
    <style>
	   	<%@include file="cssAll.css"%>
	</style>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!-- После exit корректируем URL, чтобы убрать action=exit 
	из адресной строки получем параметр action,
	если он exit - перенаправляем на главную страницу -->

<script>
	window.onload = function(){
		let paramsString = document.location.search;  
		let searchParams = new URLSearchParams(paramsString);
				
		if ( searchParams.get("action") === "exit"){
			window.location.href = "Main";
		}		
	}
</script>

 
 <div class="row" data-masonry="{&quot;percentPosition&quot;: true }" style="position: relative; height: 773.339px;">
        <div class="col-sm-6 col-lg-4 mb-4" style="position: absolute; left: 0%; top: 0px;">
          <div class="card">
            <div class="card-body">
              <img src="./images/banner3.jpg" alt="">
              <p class="card-text">Золото — это подарок, который заставляет женщину поверить в слова любви.</p> 
              <p class="card-text">Обязательно загляните в наш <a href="Main?action=catalog" class="promo">каталог</a> товаров.</p>                           
            </div>
          </div>
        </div>

       <!--style="position: absolute; left: 33.3333%; top: 0px;" -->  
       <div class="col-sm-6 col-lg-4 mb-4" >
          <div class="card p-3">
            <figure class="p-3 mb-0">
              <blockquote class="blockquote">                
                <p>Немые бриллианты часто действуют на женский ум сильнее всякого красноречия.</p>
              </blockquote>
              <figcaption class="blockquote-footer mb-0 text-body-secondary">
                Уильям Шекспир<cite title="Source Title"></cite>
              </figcaption>
            </figure>
          </div>
        </div> 

        <div class="col-sm-6 col-lg-4 mb-4" style="position: absolute; left: 66.6667%; top: 0px;">
          <div class="card">
            <img src="./images/banner1.jpg" alt="">
            <div class="card-body">              
              <p class="card-text">Более подробную информацию можно найти в разделе <a href="Main?action=promotions" class="promo">"Акции"</a>.</p>            
            </div>
          </div>
        </div>

        <div class="col-sm-6 col-lg-4 mb-4" style="position: absolute; left: 33.3333%; top: 200.837px;">
          <div class="card text-bg-primary text-center p-3">
            <figure class="mb-0">
              <blockquote class="blockquote">
                <p>Взгляд лицемерит, улыбка лжет, но драгоценности никогда не обманывают.</p>
              </blockquote>
              <figcaption class="blockquote-footer mb-0 text-white">
                Дельфина де Жирарден<cite title="Source Title"></cite>
              </figcaption>
            </figure>
          </div>
        </div>
        
        <div class="col-sm-6 col-lg-4 mb-4" style="position: absolute; left: 33.3333%; top: 369.685px;">
          <div class="card text-center">
            <div class="card-body">
              <h5 class="card-title">Новая коллекция часов уже в продаже</h5> 
              <img src="./images/banner2.jpg" alt="">
              <p class="card-text">Время — драгоценный подарок, данный нам, чтобы в нём стать умнее, лучше, зрелее и совершеннее.</p>
              
            </div>
          </div>
        </div>
        
        <div class="col-sm-6 col-lg-4 mb-4" style="position: absolute; left: 66.6667%; top: 401.827px;">
          <div class="card">           
            <img src="./images/banner11.jpg" alt="">
            <p>Украшения — это признание в чувствах, которое останется рядом навсегда.</p>
            <p>Раздел <a href="Main?action=promotions" class="promo">"Новинки"</a> поможет Вам обратить на себя внимание</p>
          </div>
        </div>
        <div class="col-sm-6 col-lg-4 mb-4" style="position: absolute; left: 0%; top: 409.824px;">
          <div class="card p-3 text-end">
            <figure class="mb-0">
              <blockquote class="blockquote">
                <img src="./images/banner4.jpg" alt="">
                <p>Более подробную информацию можно найти в разделе <a href="Main?action=promotions" class="promo">"Акции"</a>.</p>
              </blockquote>             
            </figure>
          </div>
        </div>

      </div>