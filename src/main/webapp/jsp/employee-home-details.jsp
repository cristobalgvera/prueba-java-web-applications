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
<title>Más detalles</title>
</head>
<body>
	<div class="logo">
		<img src="../resources/img/logo-A.png" alt="logo">
	</div>
	<br>
	<br>
	<br>
	<ol>
	<li>El número de identificación de la visita es ${visit.getId()}</li>
	<li>Fecha de la visita ${visit.getDate()}</li>
	<li>Actividades propuestas: ${visit.getActivities()}</li>
	<li>Número de identificación del pago: ${pay.getId()}</li>
	<li>Monto:$ ${pay.getAmount()}</li>
	<li>Fecha del pago ${pay.getDate()}</li>
	<li>Número de identificación del colaborador asignado: ${employee.getEmployeeId()}</li>
	<li>Nombre del colaborador ${employee.getEmployeeName()}</li>
	<li>Apellido del colaborador ${employee.getEmployeeLastName()}</li>
	<li>Email del colaborador ${employee.getEmployeeEmail()}</li>
	<li>Teléfono del colaborador ${employee.getEmployeePhoneNumber()}</li>
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
	<form action="employee-home" method="GET">
		<button onclick="location.href='employee-home-finish.jsp'">Finalizar</button>
		<button onclick="location.href='employee-home.jsp'">Volver</button>
		</form>

	<footer>
		<p>
			Asesorías digitales <br> Todos los derechos reservados.
		</p>
	</footer>
</body>
</html>