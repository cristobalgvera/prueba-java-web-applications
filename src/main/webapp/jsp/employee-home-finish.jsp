<%@page import="control.entities.Summary"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://fonts.googleapis.com/css?family=Ubuntu:500"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<title>Finalizar</title>
</head>
<body>
<div class="logout" align="right">
   <form  action="employee-home" method="GET">
	<button id="logout" type="submit" name="submit-btn" value="logout">Cerrar sesión</button>
	<input type="hidden" name="idvisithidden" value="${visit.getId()}"></input>
	</form>
</div>
	<div class="logo">
		<img src="./resources/img/logo-A.png" alt="logo">
	</div>
	<br>
	<br>
	<br>

	
<div align="center" class="loquesea"> 

	<h4>Finalización de visita</h4>

	<p>Para finalizar una visita, primero debe completar un cuadro
		informativo sobre las actividades realizadas durante el proceso de
		asesoría.</p>
	<p>Además, deberá evaluar la satisfacción del proceso.</p>




	<form action="employee-home" method="GET">
	<textarea id="textarea" placeholder="Detalle las actividades" required><c:out value="${summary.getDescription()}"/></textarea> 
		<ul class="ratings" >
		
			<li class="star" name="default" value="10"></li>
			<li class="star" name="default" value="9"></li>
			<li class="star" name="default" value="8"></li>
			<li class="star" name="default" value="7"></li>
			<li class="star" name="default" value="6"></li>
			<li class="star" name="default" value="5"></li>
			<li class="star" name="default" value="4"></li>
			<li class="star" name="default" value="3"></li>
			<li class="star" name="default" value="2"></li>
			<li class="star" name="default" value="1"></li>
		</ul>
	<button name="submit-btn" value="save"  type="submit">Guardar</button>
	<button name="submit-btn" value="terminate" type="submit">Finalizar</button>
	<input type="hidden" name="idvisithidden" value="${visit.getId()}"/>
	<input type="hidden" name="rating" value=1 id="rating">
		
	</form>
</div>
	<footer>
		<p>
			Asesorías digitales <br> Todos los derechos reservados.
		</p> 
		
	</footer>
	<script>
		$(function() {
			var star = '.star', selected = '.selected';

			$(star).on('click', function() {
				$("#rating").val(parseInt($(this).val(), 10));
				
				$(selected).each(function() {
					$(this).removeClass('selected');
				});
				$(this).addClass('selected');
			});

		});
		<% int rating = (int) ((Summary) (request.getAttribute("summary"))).getRating();%>
	    $(document).ready(function () {
	        let star = '.star', selected = '.selected';
	        let rating = <%=rating%>
	        if ((rating >0)) {
	            $("ul li").each(function () {
	                if (rating >=parseInt($(this).val(), 10)) {
	                    $(this).addClass('selected');
	                   
	                }
	            })
	        } else {
	            $("ul li").last().addClass('selected');
	        }
	    }); 
	</script>
	<p><%=rating%></p>
</body>
</html>