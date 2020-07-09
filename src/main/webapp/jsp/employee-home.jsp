<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Ubuntu:500"
          rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/resources/css/style.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <% int activeTab = (request.getAttribute("activeTab") != null) ? (int) request.getAttribute("activeTab") : 0; %>
    <script>
        $(function () {
            $("#tabs").tabs({
                active: {active:<%=activeTab%>}
            });
        });
    </script>
    <script>
        $(function () {
            $("#dialog").dialog();
        });
    </script>
</head>
<title>Home Employee</title>
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

<div class="saludo">
    <h2>Bienvenido ${user.getName()} !</h2>
</div>
<div id="tabs">
    <ul>
        <li><a href="#tabs-1">Gastionar visitas</a></li>
        <li><a href="#tabs-2">Visualizar pagos</a></li>
    </ul>
    <div id="tabs-1">
        <h3>Gastionar visitas</h3>
        <table align="center">
            <thead>
            <tr>
                <th>ID visita</th>
                <th>Fecha visita</th>
                <th>Cliente</th>
            </thead>
            <tbody>
            <c:forEach var="visit" items="${allVisits}">
                <tr>
                    <td><c:out value="${visit.getId()}"/></td>
                    <td><c:out value="${visit.getDate()}"/></td>
                    <td><c:out value="${visit.getCustomerId()}"/></td>
                    <td>
                        <form class="formbtn" action="employee-home" method="GET">
                            <button  type="submit" name="submit-btn" value="details">Detalles</button>
                            <button type="submit" name="submit-btn" value="finish">Finalizar</button>
                            <input type="hidden" name="idvisithidden" value="${visit.getId()}"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div id="tabs-2">
        <h3>Visualización de pagos</h3>
        <table align="center">
            <thead>
            <tr>
                <th>Nº de transación del pago</th>
                <th>Fecha del pago</th>
                <th>Valor total</th>
                <th>Estado del pago</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="pay" items="${payments}">
                <tr>
                    <td><c:out value="${pay.getId()}"/></td>
                    <td><c:out value="${pay.getDate()}"/></td>
                    <td><c:out value="${pay.getAmount()}"/></td>
                    <td><c:out value="${pay.isReady() ? 'Pagado': 'No Pagado'}"/></td>
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

</body>
</html>