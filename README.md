# Proyecto Java

Este proyecto de java requiere ciertos requisitos para poder ser ejecutado a la perfección, a continuación las especifíco.

## Requisitos

1. **Java SE 23**: Asegúrate de tener instalada la versión 23 de Java SE. Puedes descargarla desde [aquí](https://www.oracle.com/java/technologies/javase/jdk23-archive-downloads.html).

2. **Librerías**: Necesitarás las siguientes librerías en la carpeta `/lib` del proyecto:
   - [MySQL Connector](https://dev.mysql.com/downloads/connector/j/)
   - [JBCrypt 0.4](https://www.mindrot.org/projects/jBCrypt/) ([Repositorio Maven](https://mvnrepository.com/artifact/org.mindrot/jbcrypt/0.4))

   Descarga las librerías y colócalas en la carpeta `/lib`.

3. **Base de Datos**:
   - Necesitarás importar el archivo SQL `PracticaFinalBravoMarcosAlvaro.sql` que contiene los scripts para crear las tablas en tu base de datos.
   - Asegúrate de tener un servidor MySQL en funcionamiento y crea una base de datos para el proyecto.
   - Importa el archivo SQL utilizando un cliente MySQL o la línea de comandos.

## Configuración del Entorno

1. **Archivo `.env`**:  
   El proyecto incluye un archivo llamado `.envDefault`. Debes renombrarlo a `.env` y modificarlo con la información de conexión a tu base de datos. Asegúrate de que el archivo contenga las siguientes variables:

DB_HOST=
DB_PORT=
DB_USERNAME=
DB_PASSWORD=
DB_DATABASE=

2. **Configuración en Eclipse**:  
Abre el proyecto en Eclipse y asegúrate de que las librerías en la carpeta `/lib` estén correctamente configuradas en el classpath del proyecto.

## Ejecución

Una vez que hayas configurado el entorno y la base de datos, puedes ejecutar el proyecto desde Eclipse. Asegúrate de que no haya errores de compilación y que todas las librerías estén correctamente importadas.


