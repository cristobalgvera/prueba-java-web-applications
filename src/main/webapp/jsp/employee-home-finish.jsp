<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<div class="logo">
		<img src="../resources/img/logo-A.png" alt="logo">
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
		<textarea id="textarea" placeholder="Detalle las actividades" required></textarea>
		<ul class="ratings">
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
		<button type="submit" name="finish">Finalizar</button>
		<button onclick="location.href='jsp/employee-home.jsp'">Volver</button>
		 <input type="hidden" name="idvisithidden"
                                   value="${visit.getId()}"/>
		<input type="hidden" name="rating" value="1" id="pii" >
		
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
				$("#pii").val($(this).val());
				
				$(selected).each(function() {
					$(this).removeClass('selected');
				});
				$(this).addClass('selected');
			});

		});
	</script>
</body>
</html>