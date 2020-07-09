<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
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
    <% int activeTab = (request.getAttribute("activeTab") != null) ? (int) request.getAttribute("activeTab") : 0; %>
    <script>
        $(function () {
            $("#tabs").tabs({active:<%=activeTab%>});
        });
    </script>
    <script>
        $(document).ready(function () {
            $("#dialogo").click(function (event) {
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
    <form action="customer-home" method="GET">
        <button id="logout" type="submit" name="submit-btn" value="logout">Cerrar
            sesión
        </button>
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
        <li><a href="#tabs-4">Historial financiero</a></li>
        <li><a href="#tabs-5">Ayuda</a></li>
    </ul>

    <!-- //////////////////////////////////////////////////////////// -->
    <!--         1 -->
    <div align="center" id="tabs-1">

        <h3>Solicitar Asesoría</h3>
        <p>Ya tenemos tus datos, somos la legión.</p>
        <form action="customer-home" class="clientformayuda" method="POST">
            <button type="submit" name="submit-btn" id="envio"
                    value="request-visit">Solicitar visita
            </button>
        </form>
    </div>

    <!-- //////////////////////////////////////////////////////////// -->
    <!--         2 -->
    <div id="tabs-2">
        <div align="center">
            <h3>Visualización de Asesoría</h3>
        </div>
        <c:choose>
            <c:when test="${visits.isEmpty() || visits==null}">
                <p align="center">Usted no tiene aseorías agendadas.</p>
            </c:when>

            <c:otherwise>

                <div id="viewTableAs">
                    <ol>
                        <c:set var="visitsIndex" value="${0}"/>
                        <c:forEach var="visit" items="${visits}">
                            <li>
                                <div id="dialogo" title="Basic dialog">
                                    <p>
                                        ID de asesoría:
                                            <c:out value="${visit.getId()}"/>
                                        <br> <br> Nombre del colaborador:
                                            <c:out value="${employees.get(visitsIndex).getName()}"/>
                                        &nbsp;
                                            <c:out value="${employees.get(visitsIndex).getLastName()}"/>
                                        <br> <br> Fecha de realización:
                                            <c:out value="${visit.getDate()}"/>
                                        <br> <br> Nº confirmación de pago:
                                            <c:out value="${visit.getPaymentId()}"/>
                                        <br> <br> Actividades realizadas:
                                            <c:out value="${visit.getActivities()}"/>
                                        <br> <br>
                                    <td class="sinborde"><c:out
                                            value="${visit.isReady() ? 'Realizada': 'Por realizar'}"/></td>
                                    </p>
<%--                                    <c:out value="${visitsIndex}"></c:out>--%>
<%--                                    <c:out value="<%=activeTab%>"></c:out>--%>
                                </div>
                            </li>
                            <c:set var="visitsIndex" value="${visitsIndex+1}"/>
                            <hr>
                        </c:forEach>
                    </ol>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- //////////////////////////////////////////////////////////// -->
    <!--         3 -->
    <div align="center" id="tabs-3">

        <h3>Realizar pagos</h3>

        <c:choose>
            <c:when test="${debts.isEmpty() || debts==null}">
                <p align="center">Usted no cuenta con deudas pendientes.</p>
            </c:when>

            <c:otherwise>
                <!-- <p>Indicar el ID de la asesoría a pagar</p> -->
                <!-- <input type="number" name="id3" required -->
                <!-- placeholder="Número de asesoría"><br> <br> -->
                <table class="sinborde">
                    <thead>
                    <tr>
                        <th>N°</th>
                        <th>Número de transacción</th>
                        <th>Fecha</th>
                        <th>Monto</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:set var="ntransaccion" value="${0}"/>
                    <c:forEach var="pay" items="${payments}">
                        <c:if test="${!pay.isReady()}">
                            <c:set var="ntransaccion" value="${ntransaccion+1}"/>
                            <tr>
                                <td class="sinborde"><c:out value="${ntransaccion}"/></td>
                                <td class="sinborde"><c:out value="${pay.getId()}"/></td>
                                <td class="sinborde"><c:out value="${pay.getDate()}"/></td>
                                <td class="sinborde"><c:out value="${pay.getAmount()}"/></td>
                                <td class="sinborde">
                                    <form action="customer-home" method="POST">
                                        <button type="submit" name="submit-btn" value="pay">Pagar</button>
                                        <br> <input type="hidden" name="hidden-id" value="${pay.getId()}">
                                    </form>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- //////////////////////////////////////////////////////////// -->
    <!-- 		4 -->
    <div align="center" id="tabs-4">

        <h3>Historial financiero</h3>
        <br>
        <c:choose>
            <c:when test="${payments.isEmpty() || payments==null}">
                <p align="center">Usted no tiene pagos registrados.</p>
            </c:when>

            <c:otherwise>
                <table class="sinborde">
                    <thead>
                    <tr>
                        <th>N°</th>
                        <th>Número de transacción</th>
                        <th>Fecha del pago</th>
                        <th>Valor total</th>
                        <th>Estado del pago</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:set var="nasesoria" value="${0}"/>
                    <c:forEach var="pay" items="${payments}">
                        <c:set var="nasesoria" value="${nasesoria+1}"/>
                        <tr>
                            <td class="sinborde"><c:out value="${nasesoria}"/></td>
                            <td class="sinborde"><c:out value="${pay.getId()}"/></td>
                            <td class="sinborde"><c:out value="${pay.getDate()}"/></td>
                            <td class="sinborde"><c:out value="${pay.getAmount()}"/></td>
                            <td class="sinborde"><c:out
                                    value="${pay.isReady() ? 'Pagado': 'No Pagado'}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- //////////////////////////////////////////////////////////// -->
    <!-- 		5 -->
    <div align="center" id="tabs-5">
        <form class="clientformayuda" action="" method="POST">
            <h3 align="center">Formulario de ayuda</h3>
            <input type="text" name="name" id="nombre" placeholder="Nombre"
                   required> <input type="hidden" name="id" value="noexiste">
            <input type="email" name="Email" id="email" placeholder="Email"
                   required>
            <textarea name="comentarios" id="comentarios"
                      placeholder="Escriba aquí su consulta" required></textarea>
            <br> <input type="submit" value="Enviar" id="envio"
                        onclick="consultaEnviada()">
        </form>

    </div>
</div>

<footer>
    <p>
        Asesorías digitales <br> Todos los derechos reservados.
    </p>
</footer>

<script type="text/javascript">
    function consultaEnviada() {
        alert("Sus consultas son muy importantes para nosotros. Le responderemos a la brevedad.");
    }
</script>
</body>
</html>
	