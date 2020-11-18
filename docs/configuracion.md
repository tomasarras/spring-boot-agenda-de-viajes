# Configuracion

## Todas las configuraciones se encuentran en el package **edu.isistan.config**

### Token
Para poder configurar el token, desde la clase **TokenConfig**, se puede cambiar el tiempo de expiracion del token o la clave secreta del token

### Metodo de encriptacion de la contraseña de los usuarios
Para cambiar la manera de encriptar las password en la clase **WebMvcConfig** modificar el metodo **passwordEncoder**

### Rutas de acceso publico
Para agregar o quitar rutas sin pasar por el filtro, en la clase **LoginConfiguration** agregar o quitar en el metodo **configure** donde estan las demas rutas

### Swagger
Las configuraciones de [swagger](https://tomasarras.github.io/arqui/) estan en **SpringFoxConfig**
