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
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<% int activeTab = (request.getAttribute("activeTab") != null) ? (int) request.getAttribute("activeTab") : 0; %>
<script>

	$(function() {
		$("#tabs").tabs({
			active :{active:<%=activeTab%>}
	});
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
		<img src="../resources/img/logo-A.png" alt="logo">
	</div>
	<div class="saludo">
		<h2>Bienvenido ${user.getName()} !</h2>
	</div>

	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Gastionar visitas</a></li>
			<li><a href="#tabs-3">Visualizar pagos</a></li>

		</ul>
	</div>

	<div id="tabs-1">
		<h3>Gastionar visitas</h3>

		<table>
			<thead>
				<tr>
					<th>ID visitas</th>
					<th>Fecha visitas</th>
					<th>Colaborador</th>
					<th>Cliente</th>
			</thead>

			<tbody>
				<c:forEach var="visit" items="${allVisits}">

					<tr>

						<td><c:out value="${visit.getId()}" /></td>
						<td><c:out value="${visit.getDate()}" /></td>
						<td><c:out value="${visit.getEmployeeId()}" /></td>
						<td><c:out value="${visit.getCustomerId()}" /></td>
						<td>
						<form action="employee-home" method="GET">
						<button type="submit" name="details">Detalles</button>
						<button type="submit" name="finish">Finalizar</button>
						<input type="hidden" name="idvisithidden" value="${visit.getId()}"></input>
						</form>
						</td>




					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>



	
		
		








	<div id="tabs-3">

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

				<c:forEach var="pay" items="${payment}">

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

	</div>







	<footer>
		<p>
			Asesorías digitales <br> Todos los derechos reservados.
		</p>
	</footer>

	<script type="text/javascript">
		function hola() {
			$(this).html("Hola bebe");
		}
	</script>
</body>
</html>