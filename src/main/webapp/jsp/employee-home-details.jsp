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
	
	
	<p>El número de identificación de la visita es ${visit.getId()}</p>
	<p>Fecha de la visita ${visit.getDate()}</p>
	<p>Actividades propuestas: ${visit.getActivities()}</p>
	<p>Número de identificación del pago: ${pay.getId()}</p>
	<p>Monto:$ ${pay.getAmount()}</p>
	<p>Fecha del pago ${pay.getDate()}</p>
	<p>Número de identificación del colaborador asignado: ${employee.getEmployeeId()}</p>
	<p>Nombre del colaborador ${employee.getEmployeeName()}</p>
	<p>Apellido del colaborador ${employee.getEmployeeLastName()}</p>
	<p>Email del colaborador ${employee.getEmployeeEmail()}</p>
	<p>Teléfono del colaborador ${employee.getEmployeePhoneNumber()}</p>
	<p>Número de indentificación del cliente ${customer.getId()}</p>
	<p>Nombre del cliente ${customer.getName()}</p>
	<p>Apellido del cliente ${customer.getLastName()}</p>
	<p>Email del cliente ${customer.getEmail()}</p>
	<p>Teléfono del cliente ${customer.getPhoneNumber()}</p>
	<p>Dirección de la visita ${address.getStreet()}</p>
	<p>Número ${address.getNumber()}</p>
	<p>Departamento/Bloque ${address.getBlock()}</p>
	<p>Ciudad ${address.getCity()}</p>
	<p>País ${address.getCountry()}</p>
	<p>Resumen de actividades durante la asesoría ${summary.getDescription()}</p>
	<p>Evaluación de satisfación de la visita ${summary.getRating()}</p>
	
	
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