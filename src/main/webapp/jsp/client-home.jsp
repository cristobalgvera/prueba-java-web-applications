<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Ubuntu:500" rel="stylesheet" type="text/css">
    <link href="css/style.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        $( function() {
          $( "#tabs" ).tabs();
        } );
        </script>
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
          <li><a href="#tabs-1">Solicitar visita</a></li>
          <li><a href="#tabs-2">Revisar informe</a></li>
          <li><a href="#tabs-3">Realizar pagos</a></li>
          <li><a href="#tabs-4">Revisar pagos</a></li>
          <li><a href="#tabs-5">Ayuda</a></li>
        </ul>
        <div id="tabs-1">
          <p>Proin elit arcu, rutrum commodo, vehicula tempus, commodo a, risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris. Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem. Mauris dapibus lacus auctor risus. Aenean tempor ullamcorper leo. Vivamus sed magna quis ligula eleifend adipiscing. Duis orci. Aliquam sodales tortor vitae ipsum. Aliquam nulla. Duis aliquam molestie erat. Ut et mauris vel pede varius sollicitudin. Sed ut dolor nec orci tincidunt interdum. Phasellus ipsum. Nunc tristique tempus lectus.</p>
        </div>
        <div id="tabs-2">
          <p>Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</p>
        </div>
        <div id="tabs-3">
          <p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
          <p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</p>
        </div>
        <div id="tabs-4">

            <h3>Visualización de pagos</h3>

            <p>El Nº de pago realizado es ${user.getId()}</p>
            <p>La fecha del pago es ${user.getDate()}</p>
            <p>El monto de la asesoría es ${user.getAmount()}</p>
            <p>El estado del pago está ${user.getReady()}</p>


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
    <script>
        var envio(){
        alert("Se ha enviado el formulario");
        }
    </script>
</body>
</html>