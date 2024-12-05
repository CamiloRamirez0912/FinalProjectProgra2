# CODIGO CLIENTE
Autor = Camilo Esteban Ramirez Salas

Este es un proyecto de con una interfaz desarrollada en swing, sigue el modelo arquitectónico MVP.

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
- La aplicación se iniciará y con ello mostrará una la interfaz de usuario, para que funcione correctamente se debe estar corriendo el servidor spring. 