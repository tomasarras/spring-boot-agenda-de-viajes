# Trazabilidad

**Feature:** _"La aplicación deberá mostrar tanto los viajes futuros como los ya realizados."_
<br>
Para esta funcionalidad, del lado del cliente el usuario podra ver sus viajes futuros o pendientes cuando ingrese al sistema y para poder ver sus viajes realizados puede ir a la pestaña 'Viajes realizados' y podra verlos, del lado del servidor se llama al metodo _getViajesDeUsuario(criterio)_ en el controlador _ViajeController_ donde el criterio puede ser 'realizados' o 'pendientes' o ningun criterio para traer todos,
este metodo usa los siguientes metodos de _ViajeRepository_ para traer los viajes _buscarViajesDeUsuarioRealizados(idUsuario,now)_, _buscarViajesDeUsuarioPendientes(idUsuario,now)_ y _buscarViajesDeUsuario(idUsuario)_.
<br>El test de esta funcionalidad esta implementado
<br>
<br>

**Feature:** _"Cada viaje estará identificado por un nombre, ciudad de  destino, fechas de inicio y fin, y una descripción breve."_
<br>
Esta funcionalidad corresponde a la entidad [viaje](https://github.com/tomasarras/arqui/blob/main/docs/der.md) y a la clase _Viaje_ donde estan los atributos
<br>
<br>

**Feature:** _"Cada viaje podrá estar compuesto por distintos “planes” como, por ejemplo: vuelos, reservas de hotel, viajes en colectivo, excursiones, viajes en tren y otras actividades."_
<br>
Esta funcionalidad corresponde a la clase _Plan_, que es un plan comun (para el caso de una excursion) y a las clases que heredan de esta, _PlanReservaHotel_, _PlanViajeColectivo_, _PlanViajeTren_ y a las [entidades](https://github.com/tomasarras/arqui/blob/main/docs/der.md) Plan, Plan_viaje_tren, Plan_reserva_hotel, Plan_viaje_colectivo
<br>
<br>

**Feature:** _"La aplicación deberá mostrar cada uno de los planes. Según el tipo de plan, la aplicación mostrará los datos del mismo."_
<br>
En esta funcionalidad, del lado del cliente el usuario podra seleccionar el viaje al cual quiere ver los planes y podra ver cada plan y el tipo, del lado del servidor se llama al metodo _getPlanesDeViaje(idViaje)_ en el controlador _PlanController_ que nos devuelve todos los planes que tenga un viaje y los datos de cada plan. <br> El test de esta funcionalidad esta implementado
<br>
<br>

**Feature:** _"Para dar de alta un viaje, existirán dos alternativas: i) mediante un formulario provisto en la aplicación;"_
<br>
En el lado del cliente, el usuario podra hacer click en 'crear nuevo viaje' y cargar los datos del viaje en un formulario, del lado del servidor se llama al metodo _crearViaje(viaje)_ en _ViajeController_ para crear el viaje
<br>
<br>

**Feature:** _"o ii) enviando un archivo de texto, que contenga toda la confirmación de la reserva (ej. el emitido por la aerolinea, o por una pagina de viajes) de un vuelo. En este caso, el sistema debe extraer los datos del archivo y cargarlos."_
<br>
Esta funcionalidad esta del lado del cliente y no en el servidor, el cliente extrae la informacion de el viaje y los planes, comprueba que sean validos y el metodo _crearViaje(viaje)_ en _ViajeController_ del lado del servidor lo crea. <br>Para probar esta funcionalidad, en la raiz del proyecto, en la carpeta _"archivos para importar"_, contiene el archivo: _"viaje de ejemplo.txt"_ el cual se puede importar
<br>
<br>

**Feature:** _"Para un vuelo que ha sido cargado, la aplicación deberá mostrar número de vuelo, compañía, fecha y hora de salida, fecha y hora de llegada, aeropuertos de salida y llegada, código de reserva, tiempo entre escalas (en caso de existir), e información de la aeronave."_
<br>
Esta funcionalidad corresponde a la entidad [plan_vuelo](https://github.com/tomasarras/arqui/blob/main/docs/der.md) y a la clase _PlanVuelo_ que tiene todos los datos de la entidad
<br>
<br>

**Feature:** _"Dependiendo de la opción utilizada para dar de alta un vuelo, algunos de estos datos serán extraidos automaticamente o cargados por el usuario."_
<br>
Esta funcionalidad esta del lado del cliente, donde se ingresa los datos del vuelo en un formulario y del lado del servidor el metodo _crearViaje(viaje)_ en _ViajeController_ lo crea
<br>
<br>

**Feature:** _"En este ultimo caso, la aplicación solo solicitará número de vuelo, fecha y compañía, y el resto de los campos serán rellenados automáticamente utilizando el servicio FlightStats, que provee un API REST para tal fin (https://developer.flightstats.com)."_
<br>
Esta funcionalidad no pudo ser implementada debido a que el servicio FlightStats no esta disponible
<br>
<br>

**Feature:** _"Una vez creado un viaje, el usuario podrá cargar otros planes."_
<br>
Esta funcionalidad esta del lado del cliente, cuando el usuario crea un viaje puede crearle planes a este mismo, y del lado del servidor, el metodo _crearPlan(idViaje,plan)_ crea el plan a un viaje que el usuario elige, el plan solo se puede crear si esta dentro del rango de fechas del viaje que lo contiene
<br>
<br>

**Feature:** _"En el caso de ser reservas de hoteles, el usuario contará con las mismas 2 opciones de carga manual o a través del envío de un archivo de texto."_
<br>
Para esta funcionalidad, la carga de hoteles se puede hacer de forma manual eligiendo desde el lado del cliente al viaje que se le quiere agregar o para importar un archivo con la reserva podra elegir el viaje y tendra una opcion especial por ser una reserva de hotel, donde si hace click podra importar el archivo, para poder probar esta funcionalidad, en la raiz del repositorio esta la carpeta _"archivos para importar"_ esta el archivo _"hotel ejemplo.txt"_, tener en cuenta de cambiar la fecha de inicio y fin en el archivo para que coincida con el rango del viaje. <br>Para la carga manual o por archivo en el lado del servidor se llama al metodo _crearPlan(idViaje,plan)_ en _PlanController_
<br>
<br>

**Feature:** _"En el caso de la segunda opción el sistema deberá ubicar automáticamente la reserva con un viaje existente de acuerdo con las fechas del mismo."_
<br>
Esta funcionalidad se hace en el lado del cliente cuando se importa el archivo y en el servidor llama al metodo _crearPlan(idViaje,plan)_ en _PlanController_
<br>
<br>

**Feature:** _"El resto de los tipos de planes sólo podrán se agregados de forma manual."_
<br>
Esta funcionalidad en el lado del cliente, el usuario solo puede cargar todos los planes de forma manual y para el caso de la reserva de hotel tendra la posibilidad de importar el archivo
<br>
<br>

**Feature:** _"Cada usuario puede generar un reporte de sus planes de viaje, ya sea pendientes, realizados, en algún rango de fechas predeterminadas, o filtrado por zona geográfica."_
<br>
Esta funcionalidad se encuentra del lado del cliente en la pestaña _Reporte de planes_ y el usuario debera especificar el tipo de reporte que quiere obtener, luego del lado del cliente se llama al metodo _getPlanesDelUsuario(zona, fechaInicioString, fechaFinString, estado)_ del controller _PlanController_ donde se debe especificar por que tipo de criterio buscar o si no recibe ningun criterio, buscara todos los planes del usuario. Para el criterio por zona, se buscara aquellos planes que esten dentro de un plan que tenga como ciudad de destino la zona por la cual se quiere buscar.<br>Este metodo usa los metodos _getPlanesDelUsuarioPorFecha(idUsuario,fechaInicio,fechaFin)_, _getPlanesDelUsuarioPorZona(idUsuario,zona)_, _getPlanesDelUsuarioPendientes(idUsuario,now)_, _getPlanesDelUsuarioRealizados(idUsuario,now)_,
_getPlanesDelUsuario(idUsuario)_ del repositorio _PlanRepository_
<br>
<br>

**Feature:** _"Adicionalmente, la compañía dueña de la aplicación puede solicitar un reporte de los usuarios que mas viajes realizan, o también de las zonas geográficas más visitadas."_
<br>
Para esta funcionalidad la compañia debe tener el rol admin, para eso se puede modificar desde la base de datos o si se importaron los datos de prueba, usar el usuario admin, ver en [Instalacion](https://github.com/tomasarras/arqui/blob/main/docs/instalacion.md), luego en la pestaña _admin_ se puede especificar el tipo de reporte, para el caso de los usuarios que mas viajes realizan se llama al metodo _getUsuariosPorMasViajesRealizados()_ en _UsuarioController_ que usa a la clase _ReporteUsuario_ para devolver el reporte, y para el caso de un reporte de zonas geograficas mas visitadas, se llama al metodo _getCiudadesMasVisitadas()_ en _ViajeController_
<br>
<br>

**Feature:** _"Cada usuario deberá poder registrarse en la aplicación, y luego acceder mediante un nombre y contraseña."_
<br>
Para esta funcionalidad, en la clase _UsuarioController_ esta el metodo _crearUsuario(u)_ que usa los metodos _buscarPorEmail()_ y _buscarPorUsername()_ de _UsuarioRepository_ para verificar que no exista otro usuario con esos datos, luego utiliza a _BCryptPasswordEncoder_ que es el metodo de encriptacion que se configuro en _WebMvcConfig_ y devuelve un token generado con una clave privada desde la configuracion de _TokenConfig_, para el acceder al sistema se llama al metodo _login(u)_ que verifica que el usuario sea correcto y le devuelve un token
