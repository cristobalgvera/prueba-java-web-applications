# Prueba Java Web Applications

### Integrantes:
- Soledad Castro.
- Skandar Soler.
- César Bertuline.
- Cristóbal Gajardo.

#### Ubicación de diagramas: <a href="https://github.com/cristobalgvera/prueba-java-web-applications/tree/master/extras/diagramas">extras/diagramas</a>
- Diagrama lógico.
- Diagrama de clases.
- Diagrama de secuencia.

#### Entorno virtual de trabajo:
- JDK 13.0.2 / JDK 14.0.1
- Eclipse IDE 2020-06 / IntelliJ IDEA Ultimate 2020.2 EAP.
- SQL Developer / DataGrip 2020.2 EAP.
- Oracle Database 11g Express Edition.

<hr>

## Implementación de la aplicación web

### Oracle SQL DB
- Nombre: "no_mas_accidentabilidad" o "NOMASACCIDENTABILIDAD".
- Contraseña: "12345".

### Cómo implementar la base de datos
1. Crear base de datos en Oracle (11g) Apex bajo las credenciales señaladas en el punto anterior en la configuración del espacio de trabajo.

2. Dentro de la carpeta <a href="https://github.com/cristobalgvera/prueba-java-web-applications/tree/master/extras/db">extras/db</a> existen dos archivos .sql que permiten crear tablas y datos por defectos, deben ser ejecutadas en el orden brindado por el archivo. Además, se debe asegurar la correcta implementación de las secuencias y sus respectivos disparadores para cada tabla, de lo contrario el código fallará.

3. Subir archivo .war ubicado en <a href="https://github.com/cristobalgvera/prueba-java-web-applications/tree/master/extras">extras</a> a un servidor de aplicaciones Java (Tomcat, por ejemplo) y ejecutar.

4. (Opcional) En vez de realizar el paso 3, clonar repositorio y compilar proyecto Maven.
