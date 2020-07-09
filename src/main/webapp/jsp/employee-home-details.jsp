<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" %>
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
<title>Más detalles</title>
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
  
	
	<ol class="loquesea">
	<li>El número de identificación de la visita es ${visit.getId()}</li>
	<li>Fecha de la visita ${visit.getDate()}</li>
	<li>Actividades propuestas: ${visit.getActivities()}</li>
	<li>Número de identificación del pago: ${payment.getId()}</li>
	<li>Monto:$ ${payment.getAmount()}</li>
	<li>Fecha del pago ${payment.getDate()}</li>
	<li>Número de identificación del colaborador asignado: ${user.getId()}</li>
	<li>Nombre del colaborador ${user.getName()}</li>
	<li>Apellido del colaborador ${user.getLastName()}</li>
	<li>Email del colaborador ${user.getEmail()}</li>
	<li>Teléfono del colaborador ${user.getPhoneNumber()}</li>
	<li>Número de indentificación del cliente ${customer.getId()}</li>
	<li>Nombre del cliente ${customer.getName()}</li>
	<li>Apellido del cliente ${customer.getLastName()}</li>
	<li>Email del cliente ${customer.getEmail()}</li>
	<li>Teléfono del cliente ${customer.getPhoneNumber()}</li>
	<li>Dirección de la visita ${address.getStreet()}</li>
	<li>Número ${address.getNumber()}</li>
	<li>Departamento/Bloque ${address.getBlock()}</li>
	<li>Ciudad ${address.getCity()}</li>
	<li>País ${address.getCountry()}</li>
	<li>Resumen de actividades durante la asesoría ${summary.getDescription()}</li>
	<li>Evaluación de satisfación de la visita ${summary.getRating()}</li>
	
	</ol>
	<form class=botonesC action="employee-home" method="GET">
	<button name="submit-btn" value="go-back"  type="submit">Volver</button>
	<button name="submit-btn" value="finish" type="submit">Finalizar</button>
	<input type="hidden" name="idvisithidden" value="${visit.getId()}"/>
		</form>
		
	<footer>
		<p>
			Asesorías digitales <br> Todos los derechos reservados.
		</p>
	</footer>
</body>
</html>