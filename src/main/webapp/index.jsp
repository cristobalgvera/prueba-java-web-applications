<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"  %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Ubuntu:500" rel="stylesheet" type="text/css">
   <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.css"> 
    <title>Login</title>
</head>
<body>

<div class="logo">
    <img src="resources/img/logo-A.png" alt="logo">
</div>
<div class="login">

    <div class="login-header">
        <h1>Login Intranet</h1>
    </div>
    <div class="login-form">
        <form action="validation" method="POST">
            <select name="loginClass">
                <option value="employee">Colaborador</option>
                <option selected value="customer">Cliente</option>
            </select>
            <label>Usuario:</label>
            <input type="text" name="user" placeholder="Usuario"/><br>
            <label>Clave:</label>
            <input type="password" name="password"  required placeholder="Clave"/>
            <br>
            <input type="submit" value="Acceso" required class="login-button"/>
        </form>
    </div>
</div>
<footer>
    <p> Asesorías digitales <br>
        Todos los derechos reservados.
    </p>
</footer>
</body>
</html>