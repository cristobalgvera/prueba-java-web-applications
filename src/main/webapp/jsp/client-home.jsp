<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Ubuntu:500" rel="stylesheet" type="text/css">
    <link href="../css/style.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        $( function() {
          $( "#tabs" ).tabs();
        } );
        </script>
  		<script>
  		$( function() {
  		  $( "#dialog" ).dialog();
 		} );
  		</script>
</head>
    <title>Home Cliente</title>
</head>
<body>
    <div class="logo">
        <img src="img/logo-A.png" alt="logo">
      </div>
      <div class="saludo">
      <h2>Bienvenido ${user.getName()}</h2> 
        </div>

      <div id="tabs">
        <ul>
          <li><a href="#tabs-1">Solicitar asesoría</a></li>
          <li><a href="#tabs-2">Revisar asesoría</a></li>
          <li><a href="#tabs-3">Realizar pagos</a></li>
          <li><a href="#tabs-4">Revisar pagos</a></li>
          <li><a href="#tabs-5">Ayuda</a></li>
        </ul>
        <div id="tabs-1">
         <h3>Solicitar asesoría</h3>

            <p>El ID del informe realizado es ${visit.getId()}</p><br>
			<p>La fecha del pago es ${payment.getDate()}</p><br>
			<p>El monto de la asesoría es ${payment.getAmount()}</p><br>
          	<p>El estado del pago está ${payment.getReady()}</p>
        </div>
        <div id="tabs-2">
     <h3>Visualización de asesoría</h3>

            <p>El ID del informe realizado es ${summary.getId()}</p><br>
			<p>La fecha de la asesoría fue ${summary.getDate()}</p><br>
			<p>Detalle: ${summary.getDescription()}</p><br>
          	
        </div>
        <div id="tabs-3">
                <h3>Realizar pagos</h3>

            <p>Indicar el ID de la asesoría a pagar</p><br>
            
            <input type="number" name="id" required placeholder="Número de asesoría" >
		
        <div id="tabs-4">

            <h3>Visualización de pagos</h3>

            <p>El ID de pago realizado es ${payment.getId()}</p><br>
			<p>La fecha del pago es ${payment.getDate()}</p><br>
			<p>El monto de la asesoría es ${payment.getAmount()}</p><br>
          	<p>El estado del pago está ${payment.getReady()}</p>


          </div>
          <div id="tabs-5">
            <form class="formayuda" action="actions" method="POST">
                <p>Formulario de ayuda</p>
                <input type="text" name="name" placeholder="Nombre" required>
                <input type="hidden" name="id" value="noexiste">
                <input type="email" name="Email" placeholder="Email"  required>
                <input type="textarea" class="textarea" placeholder="Escriba aquí su consulta" required>
                <input type="submit"  value="Enviar">
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