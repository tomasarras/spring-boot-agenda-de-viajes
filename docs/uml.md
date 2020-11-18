# Diagrama de clases
![Diagrama de clases](https://user-images.githubusercontent.com/52612929/99592592-2e7f5d00-29cf-11eb-8322-d8bf01a506f1.png)

## Configuraciones

### LoginConfiguration
Contiene las rutas publicas que se pueden acceder sin tener un token

### SpringFoxConfig
Configuracion de Swagger

### TokenConfig
Configuraciones del token como tiempo de expiracion o clave privada

### WebMvcConfig
<br>

## Controladores

### AbsController
Contiene toda la logica que tienen en comun los controladores

### PlanController
Controlador de los planes de los usuarios

### UsuarioController
Controllador de usuario para el sistema de login y otras operaciones

### ViajeController
Controlador de los viajes de los usuarios
<br>

## Filtros

### JWTAuthorizationFilter
Es el filtro que valida el token por cada request para el sistema de login
<br>

## Modelos

### Usuario
Tiene los datos de la entidad usuario

### Viaje
Tiene los datos de la entidad viaje

### Plan
Tiene los datos para la entidad plan, es un plan basico y de este plan heredan otros tipos de planes

### PlanReservaHotel
Tipo de plan que hereda de un plan basico y tiene los datos de la reserva de un hotel

### PlanViajeColectivo
Tipo de plan que hereda de un plan basico y tiene los datos de un viaje en colectivo

### PlanViajeTren
Tipo de plan que hereda de un plan basico y tiene los datos de un viaje en tren

### PlanViajeVuelo
Tipo de plan que hereda de un plan basico y tiene los datos de un viaje

<br>

## Reportes

### ReporteCiudad
Usada para obtener el reporte de las zonas geograficas mas visitadas

### ReporteUsuario
Usada para obtener el reporte de los usuarios que mas viajes realizan
<br>

## Repositorios

### PlanRepository
Consultas y operaciones sobre los planes

### UsuarioRepository
Consultas y operaciones sobre los usuarios

### ViajeRepository
Consultas y operaciones sobre los viajes


