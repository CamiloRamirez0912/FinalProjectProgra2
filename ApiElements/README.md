# CODIGO SERVIDOR
Autor = Camilo Esteban Ramirez Salas - 202214307

Este es un proyecto de Spring Boot que se puede compilar y ejecutar como un archivo JAR. A continuación se detallan los pasos necesarios para compilar y ejecutar el proyecto.

## Requisitos

- **Java 11** o superior.
- **Maven** (si usas Maven para la construcción del proyecto).

## Compilar el Proyecto

### Usando Maven

1. Abre una terminal y navega hasta la ubicación del proyecto o crea una terminal de comandos en el directorio raíz del proyecto.
2. Ejecuta el siguiente comando para compilar y empaquetar el proyecto:

   ```bash
   mvn clean package

## Ejecutar el proyecto
Una vez que el archivo JAR se ha generado, puedes ejecutarlo de la siguiente manera:
- Dirígete al directorio donde se encuentra el archivo JAR generado. en este caso por usar maven el archivo JAR estará en el directorio target/. 
- Ejecuta el archivo JAR con el siguiente comando: `java -jar nombre-del-archivo.jar`
- La aplicación Spring Boot se iniciará y estará disponible en el puerto configurado,que por defecto, el puerto es el 8080.