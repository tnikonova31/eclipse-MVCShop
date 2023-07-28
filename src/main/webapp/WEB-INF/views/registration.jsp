<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
    
	<style>
    	<%@include file="cssAll.css"%>
	</style>

    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script>
		document.title = "Регистрация";
		window.onload = function(){
			document.querySelector("form").onsubmit = function(e){
				e.preventDefault();//отменяем перегрузку страницы по событию submit
				
				let lastName = $('#lastName').val();
				let firstName = $('#firstName').val();
				let surName = $('#surName').val();
				let phoneUser = $('#phoneUser').val();
				let email = $('#email').val();
				let login = $('#login').val();
				let password = $('#password').val();
				
				if (isCheckData(lastName, firstName, surName, phoneUser, email, login, password)){
					$.ajax({
						type: "POST",
						url: "Registration",
						data: $("form").serialize(),
						success:function(answer){
							console.log("запрос");
							alert(answer);
							window.location='Main?action=avtorization';
						}
					})
				}				
			}
		}
	
		function isCheckData(lastName, firstName, surName, phoneUser, email, login, password){
			// кириллица, латиница, составные ФИО через дефикс
			let ruleRus = /^([А-ЯЁ]{1}[а-яё]{1,23}(\-[А-ЯЁ]{1}[а-яё]{1,23})?)$/;
			let ruleEng = /^([A-Z]{1}[a-z]{1,23}(\-[A-Z]{1}[a-z]{1,23})?)$/;
			let rulePhone = /^8[0-9]{10}$/;
			let ruleEmail = /^[A-Za-z0-9!#$%&'*+\=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+\=?^_`{|}~-]+)*\@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/;
			let ruleLogin = /^[A-Za-z]+([-_]?[a-z0-9]){4,}$/;
			let rulePassword = /^[A-Za-z0-9]+([a-z0-9]){5,}$/;
			
			let flag =0;
			
			if ( (ruleRus.test(lastName) && ruleRus.test(firstName) && ruleRus.test(surName)) || 
					(ruleEng.test(lastName) && ruleEng.test(firstName) && ruleEng.test(surName))){
				flag++;
			} else{
				alert("Проверьте правильность введённых ФИО.");
				return false;
			}
			
			if (rulePhone.test(phoneUser)){
				flag++;
			} else{
				alert("Проверьте правильность введённого номера телефона.");
				return false;
			}
			
			if (ruleEmail.test(email)){
				flag++;
			} else{
				alert("Проверьте правильность введённого адреса электронной почты.");
				return false;
			}
			
			if (ruleLogin.test(login)){
				flag++;
			} else{
				alert("Данный логин не может быть использован. Введите другой вариант.");
				return false;
			}
			
			if (rulePassword.test(password)){
				flag++;
			} else{
				alert("Данный пароль не может быть использован. Введите другой вариант.");
				return false;
			}
			
			if (flag==5){
				return true;
			}			
		}					
	</script>

        
      <!-- Путь после меню -->    
      <div class="container">
        <ol class="breadcrumb">
          <li class="nav-item"><a href="Main" class="nav-link px-2">Главная  ></a></li>
          <li><a href="Main?action=avtorization" class="nav-link px-2">Регистрация</a></li>
        </ol>
      </div>
      <!-- ---------------------->
     
    <div class="registration-cssave">
            <form method="post">
              <div class="row mb-3 text-center">
                <div class="col-6 themed-grid-col"><a href="Main?action=avtorization" class="nav-link px-2">Авторизация</a></div>
                <div class="col-6 themed-grid-col"><a href="Main?action=registration" class="nav-link px-2-check">Регистрация</a></div>
              </div>
              	<div class="form-group">
              		<span class="form-group">Фамилия</span>
                    <input class="form-control item" type="text" name="lastName" id="lastName" placeholder="Фамилия" required>
                </div>               
                <div class="form-group">
                	<span class="form-group">Имя</span>
                    <input class="form-control item" type="text" name="firstName" id="firstName" placeholder="Имя" required>
                </div>
                <div class="form-group">
                	<span class="form-group">Отчество</span>
                    <input class="form-control item" type="text" name="surName" id="surName" placeholder="Отчество" required>
                </div>        
              <div class="form-group">
              	<span class="form-group">Телефон</span>
                    <input class="form-control item" type="text" name="phoneUser" id="phoneUser" value="8" required>
                </div>
                <div class="form-group">
                  <span class="form-group">Электронная почта</span>
                  <input class="form-control item" type="text" name="email" maxlength="25" id="email" placeholder="Электронная почта" required>
              	</div>
                
                <div class="form-group">
                	<span class="form-group">Логин</span>
                    <input class="form-control item" type="text" name="login" maxlength="15"  id="login" placeholder="Логин" required
                    	title="Допустима латиница, цифры. Первый символ - буква.">
                </div>                
                <div class="form-group">
                	<span class="form-group">Пароль</span>
                    <input class="form-control item" type="password" name="password" id="password" placeholder="Пароль" required 
                    	title="Допустима латиница, цифры. Первый символ - буква.">
                </div> 

                <div class="form-group">
                    <button class="btn btn-primary btn-block create-account" type="submit">Зарегистрироваться</button>
                </div>                
            </form>
        </div>