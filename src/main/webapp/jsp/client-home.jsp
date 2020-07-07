<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<<<<<<< HEAD
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
=======
    pageEncoding="UTF-8" isELIgnored="false"%>
>>>>>>> branch 'master' of https://github.com/cristobalgvera/prueba-java-web-applications
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="https://fonts.googleapis.com/css?family=Ubuntu:500"
	rel="stylesheet" type="text/css">
<link href="../css/style.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
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
<title>Home Cliente</title>
</head>
<body>
	<div class="logo">
		<img src="../resources/img/logo-A.png" alt="logo">
	</div>
	<div class="saludo">
		<h2>Bienvenido ${customer.getName()}</h2>
	</div>

	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Solicitar asesoría</a></li>
			<li><a href="#tabs-2">Revisar asesoría</a></li>
			<li><a href="#tabs-3">Realizar pagos</a></li>
			<li><a href="#tabs-4">Revisar pagos</a></li>
			<li><a href="#tabs-5">Ayuda</a></li>
		</ul>
		</div>
		<div id="tabs-1">
			<h3>Solicitar asesoría</h3>

			<form class="formayuda" action="actions" method="POST">
		
					<input type="text" name="name" placeholder="Nombre" required>
					 <input type="email" name="Email" placeholder="Email" required>
					 <input type="text" name="address" placeholder="Dirección" required>
				     <input type="date" name="date" required>
				     <input type="hidden" name="id" value="noexiste">
				     <input type="submit" value="Enviar">
				</form>
		</div>
		<div id="tabs-2">
			<h3>Visualización de asesoría</h3>

			<ol>
			<c:set var="idvisit" value="${0}" />
					<c:forEach var="visit" items="${visit}">
						<c:set var="idvisit" value="${idvisit+1}" />
			<li><c:out value="${idvisit}" />
			<div id="dialog" title="Basic dialog">
  <p>Número de asesoría: <c:out value="${idvisit}" /><br>
	Nº de colaborador: <c:out value="${visit.getEmployeeId()}" /><br>
	Fecha de realización: <c:out value="${visit.getDate()}" /><br>
	Nº confirmación de pago: <c:out value="${visit.getPaymentId()}" /><br>
	Actividades realizadas:  <c:out value="${visit.getActivities()}" /><br>
</p>
</div>
</li>
			</c:forEach>
			</ol>

		</div>
		<div id="tabs-3">
			<h3>Realizar pagos</h3>

			<p>Indicar el ID de la asesoría a pagar</p>
			<br> <input type="number" name="id" required
				placeholder="Número de asesoría">
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
							<c:if test="${!pay.isReady()}">
								<td class="sinborde">
									<form action="home" method="POST">
										<input type="submit" name="btnpagar"> <input
											type="hidden" name="idpago" value="${pay.getId()}">

									</form>
								</td>
							</c:if>
						</tr>



					</c:forEach>
				</tbody>
			</table>

			<div id="tabs-4">

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
								<td><c:out value="${pay.isReady() ? 'Pagado': 'No Pagado'}"/></td>
							</tr>

						</c:forEach>
					</tbody>

				</table>

			</div>
			<div id="tabs-5">
				<form class="formayuda" action="actions" method="POST">
					<p>Formulario de ayuda</p>
					<input type="text" name="name" placeholder="Nombre" required>
					<input type="hidden" name="id" value="noexiste"> <input
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