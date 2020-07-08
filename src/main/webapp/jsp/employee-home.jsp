<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="https://fonts.googleapis.com/css?family=Ubuntu:500"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/style.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- <link rel="stylesheet" href="/resources/demos/style.css"> -->
<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript"
	src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
	$(function() {
		$("#tabs").tabs();
	});
</script>
<script>
	$(function() {
		$("#dialog").dialog();
	});
</script>

</head>
<title>Home Employee</title>
</head>
<body>
	<div class="logo">
		<img src="<%=request.getContextPath()%>/resources/img/logo-A.png">
	</div>
	<div class="saludo">
		<h2>Bienvenido ${user.getName()} !</h2>
	</div>

	<div id="tabs">
		<!-- inicio de listas	 -->
		<ul>
			<li><a href="#tabs-1" onclick="hola()">Visitas pendientes</a></li>
			<li><a href="#tabs-2">Revisar visita</a></li>	
<!-- 			Agregar:
				 1.Finalizar visita
				 	11.ingresar resume (view)
				 	12.Resume de visita
 						121.modificar resume
				 2.Actualizar visita -->

			<li><a href="#tabs-2">Historial de visitas</a></li>
			

		</ul>
		<!-- 		1 -->
		<div id="tabs-1">
			<h3>Visitas pendientes</h3>
		</div>

		<!-- 		2 -->
		<div id="tabs-2">
			<h3>Visualización de asesoría</h3>

			<p>El ID del informe realizado es ${visit.getId()}</p>
			<br>
			<p>La fecha del pago es ${payment.getDate()}</p>
			<br>
			<p>El monto de la asesoría es ${payment.getAmount()}</p>
			<br>
			<p>El estado del pago está ${payment.getReady()}</p>
			<p>El ID del informe realizado es ${summary.getId()}</p>
			<br>
			<p>La fecha de la asesoría fue ${summary.getDate()}</p>
			<br>
			<p>Detalle: ${summary.getDescription()}</p>
			<br>
		</div>

		<!-- 		3 -->
		<div id="tabs-3">
			<h3>Realizar pagos</h3>

			<p>Indicar el ID de la asesoría a pagar</p>
			<br> <input type="number" name="id" required
				placeholder="Número de asesoría">
		</div>

<!-- 4 -->
		<div id="tabs-4">

			<h3>Visualización de pagos</h3>

			<p>El ID de pago realizado es ${payment.getId()}</p>
			<br>
			<p>La fecha del pago es ${payment.getDate()}</p>
			<br>
			<p>El monto de la asesoría es ${payment.getAmount()}</p>
			<br>
			<p>El estado del pago está ${payment.getReady()}</p>
		</div>
		
<!-- 		5 -->
		<div id="tabs-5">
			<form class="formayuda" action="actions" method="POST">
				<p>Formulario de ayuda</p>
				<input type="text" name="name" placeholder="Nombre" required>
				<input type="hidden" name="id" value="noexiste"> <input
					type="email" name="Email" placeholder="Email" required> <input
					type="text" class="text" placeholder="Escriba aquí su consulta"
					required> <input type="submit" value="Enviar"> <input
					type="button" id="hola" onclick="hola()" value="fej">
			</form>
		</div>
	</div>

	<footer>
		<p>
			Asesorías digitales <br> Todos los derechos reservados.
		</p>
	</footer>

</body>

</html>