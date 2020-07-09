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
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#tabs").tabs();
	});
</script>



<script>
	$(document).ready(function() {
		$("#dialogo").click(function(event) {
			$("#dialog").dialog();
		});
	});
</script>

</head>
<title>Home Cliente</title>
</head>

<body>
<!-- //////////////////////////////////////////////////////////// -->
	<div class="logout" align="right">
		<form action="employee-home" method="GET">
			<button id="logout" type="submit" name="submit-btn" value="logout">Cerrar
				sesión</button>
			<input type="hidden" name="idvisithidden" value="${visit.getId()}"></input>
		</form>
	</div>
<!-- //////////////////////////////////////////////////////////// -->
	<div class="logo">
		<img src="<%=request.getContextPath()%>/resources/img/logo-A.png">
	</div>
	<div class="saludo">
		<h2>Hola, ${user.getName()} !</h2>
	</div>

<!-- //////////////////////////////////////////////////////////// -->
	<!-- Tabs -->
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Solicitar asesoría</a></li>
			<li><a href="#tabs-2">Revisar asesoría</a></li>
			<li><a href="#tabs-3">Realizar pagos</a></li>
			<li><a href="#tabs-4">Revisar pagos</a></li>
			<li><a href="#tabs-5">Ayuda</a></li>
		</ul>
		
<!-- //////////////////////////////////////////////////////////// -->
		<!--         1 -->
		<div align="center" id="tabs-1">

			<h3>Solicitar Asesoría</h3>

			<form action="customer-home" class="clientformayuda" method="POST">
				<input type="text" id="nombre" placeholder="Nombre del solicitante"
					required> <input type="number" id="numero"
					placeholder="Número de teléfono" required> <input
					type="email" id="email" placeholder="Mail de contacto" required>
				<input type="text" id="direccion" placeholder="Dirección" required>
				<br> <input type="hidden" name="id" value="noexiste"> <input
					type="submit" id="envio" value="Enviar"">
			</form>
		</div>
		
<!-- //////////////////////////////////////////////////////////// -->
		<!--         2 -->
		<div id="tabs-2">
			<div align="center">
				<h3>Visualización de Asesoría</h3>
			</div>
			<div id="viewTableAs">
				<ol>
					<c:set var="idvisit" value="${0}" />
					<c:forEach var="visit" items="${visit}">
						<c:set var="idvisit" value="${idvisit+1}" />
						<li><c:out value="${idvisit}" />
							<div id="dialogo" title="Basic dialog">
								<p>
									Número de asesoría:
									<c:out value="${visit.getId()}" />
									<br> <br> Nº de colaborador:
									<c:out value="${employee.getName()}" />
									&nbsp;
									<c:out value="${employee.getLastName()}" />

									<br> <br> Fecha de realización:
									<c:out value="${visit.getDate()}" />
									<br> <br> Nº confirmación de pago:
									<c:out value="${visit.getPaymentId()}" />
									<br> <br> Actividades realizadas:
									<c:out value="${visit.getActivities()}" />
									<br>
								</p>
							</div></li>
					</c:forEach>
				</ol>
			</div>
		</div>
		
<!-- //////////////////////////////////////////////////////////// -->
		<!--         3 -->
		<div align="center" id="tabs-3">

			<h3>Realizar pagos</h3>

			<p>Indicar el ID de la asesoría a pagar</p>
			<input type="number" name="id3" required
				placeholder="Número de asesoría"><br> <br>
			<table>
				<thead>
					<tr>
						<th>Número de transacción</th>
						<th>Fecha</th>
						<th>Monto</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="ntransaccion" value="${0}" />
					<c:forEach var="pay" items="${payments}">
						<c:set var="ntransaccion" value="${ntransaccion+1}" />
						<tr>
							<td><c:out value="${ntransaccion}" /></td>
							<td><c:out value="${pay.getDate()}" /></td>
							<td><c:out value="${pay.getAmount()}" /></td>
							<br>
							<c:if test="${!pay.isReady()}">
								<td class="sinborde"><br>
									<form action="home" method="POST">
										<input type="submit" name="btnpagar"> <br> <input
											type="hidden" name="idpago" value="${pay.getId()}">
									</form></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
<!-- //////////////////////////////////////////////////////////// -->
		<!-- 		4 -->
		<div align="center" id="tabs-4">

			<h3>Visualización de pagos</h3>

			<table>
				<thead>
					<tr>
						<th>Nº de transación del pago</th>
						<th>Fecha del pago</th>
						<th>Valor total</th>
						<th>Estado del pago</th>
					</tr>
				</thead>

				<tbody>
					<c:set var="nasesoria" value="${0}" />
					<c:forEach var="pay" items="${payment}">
						<c:set var="nasesoria" value="${nasesoria+1}" />
						<tr>
							<td><c:out value="${pay.getId()}" /></td>
							<td><c:out value="${pay.getDate()}" /></td>
							<td><c:out value="${pay.getAmount()}" /></td>
							<td><c:out value="${pay.isReady() ? 'Pagado': 'No Pagado'}" /></td>
						</tr>

					</c:forEach>
				</tbody>

			</table>

		</div>
		
<!-- //////////////////////////////////////////////////////////// -->
		<!-- 		5 -->
		<div align="center" id="tabs-5">
			<form class="clientformayuda" action="actions" method="POST">
				<h3 align="center">Formulario de ayuda</h3>
				<input type="text" name="name" id="nombre" placeholder="Nombre"
					required> <input type="hidden" name="id" value="noexiste">
				<input type="email" name="Email" id="email" placeholder="Email"
					required>
				<textarea name="comentarios" id="comentarios"
					placeholder="Escriba aquí su consulta" required></textarea>
				<br> <input type="submit" value="Enviar" id="envio">
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
