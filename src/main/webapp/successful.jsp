<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Ubuntu:500" rel="stylesheet" type="text/css">
    <link href="style.css" rel="stylesheet">
    <title>Logeado</title>
</head>
<body>
    <h2>Ha ingresado correctamente a su sesión!</h2>
    <p>
        Usuario: ${client.getUser()}<br>
		Password: ${client.getPass()}<br>
    </p>
    
</body>
</html>