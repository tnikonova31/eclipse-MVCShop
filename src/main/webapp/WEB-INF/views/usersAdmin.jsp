<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
    
    <style>
	   	<%@include file="cssAll.css"%>
	</style>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
  	
 	<table id="allOrders">
        <tr class="allOrders-trtd">
          <th colspan="6" class="con" style="text-align: center;">Данные покупателей</th>
        </tr>
      <tr class="allOrders-trtd">
      	  <td class="allOrders-trtd"><b>Номер</b></td>      	
          <td class="allOrders-trtd"><b>ФИО покупателя</b></td>
          <td class="allOrders-trtd"><b>Контактный телефон</b></td>
          <td class="allOrders-trtd"><b>Электронная почта</b></td>
          <td class="allOrders-trtd"><b>Логин</b></td>
          <td class="allOrders-trtd"><b>Пароль</b></td>
       </tr>
       
       <c:forEach var="item" items="${users}" varStatus="num">
       	<tr class="allOrders-trtd">
       	   <td class="allOrders-trtd">${num.count}</td>      	 
          <td class="allOrders-trtd">${item.lastName} ${item.firstName} ${item.surName}</td>
          <td class="allOrders-trtd">${item.phoneUser}</td>
          <td class="allOrders-trtd">${item.email}</td>
          <td class="allOrders-trtd">${item.login}</td>
          <td class="allOrders-trtd">${item.password}</td>
        </tr>
       </c:forEach>    	
    	</table>	           
   
