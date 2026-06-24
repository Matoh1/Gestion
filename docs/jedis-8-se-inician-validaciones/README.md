aquí iré comentando lo que se hace en cada versión

bitacora del profe: estoy subiendo todos los cambios a github, así pueden ir chequeando todo

v2: 
se añade la clase cristal en sable, de esta manera se puede ver que se pueden seguir comunicando entre clases, además de que el buscar por jedi ahora si tiene sentido
esto ya que ahora hay un método que lo hace y no es un llamado de un for a la rápida.
también se editó el service de jedi que ahora recibe un objeto, además de que se creo el archivo SableExternoDTO que precisamente trae ese
objeto nuevo, ahora pueden recibir y modificar objetos de otros microservicios en su microservicio actual
también hay una v2 de la base de datos de sables, ya que sea agrega la nueva clase y se debe actualizar todo.

v3:
Se cambia el clásico application.properties por application.yml, este sigue el estándar de yaml, además de agregar perfiles, tanto dev
(desarrollo), test y prod (producción), tiene sus variables globales y nos permite estár listos para mantener nuestros microservicios andando.

v4:
Tomando en cuenta que nadie querrá iniciar los microservicios uno a uno, hice los siguientes script:
- iniciar-todo.bat : este se ejecuta en windows, se hace doble click y con eso ya ejecuta ambos microservicios 
- iniciar-todo.sh: este es para mac/linux, antes de usarlo hay que ejecutar chmod +x iniciar-todo.sh y luego ./iniciar.sh y así en la terminal se inician ambos códigos

v5:
Se crea un nuevo microservicio, este es el apigateway, la gracia de este es que hace que los microservicios se ejecuten desde una url
http://localhost:8080/api/v1/jedis esta por ejemplo, hace que en vez de conectarnos desde el 8081, nos conectemos directo de la 8080
lo mismo pasa desde la 8082, todas se conectan desde esa, con este api gateway, ya tenemos las bases para poder usar eureka,
esta nos permitirá conectarnos sin tener que definir el puerto ni nada, eureka se encargará de encontrar los microservicios

v6:
Se añade eureka, este nos permite no inicializar los puertos a mano, por lo mismo se crea un nuevo microservicio de eureka, aunque literal 
pueden usar los mismos que están aqui y refactorizar chiquillos, lo que si, en los bloques que crearon tienen que agregar lo siguiente:
en el pom añadir esto

<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
			<version>5.0.1</version>
		</dependency>

y en el application.yml

cambiamos el server a 0 para que sea dinámico y añadimos el cliente de eureka

server:
  port: 0


eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/

tambien en las url que se conectan internamente deben cambiarlas para que sean así
"http://sables/api/v1/sables/buscar-por-jedi/" así como quedan en el apigateway y en el webclient config

añadir @LoadBalanced así como en el de jedis

v7:
ahora el swagger está unificado, http://localhost:8080/swagger-ui/index.html se inicializa el swagger de todos los microservicios, así
tienen todo unificado, lo que si, dejar la dependencia del swagger en la version 3.0.0 que si no, cuando esté el hateoas, este no funciona

v8:
Se hicieron cambios pequeños, la clase JediValidaciones contiene como dice su nombre, validaciones, además de que la conexión con el otro micro servicio se movió a esta nueva clase, con el convertir a DTO, así dejamo el service más ligero y no estamos con problemas de 
tener este archivo con tantas líneas de código y evitamos código repetitivo, se añadió el buscar por id en cristales y se deja el cambio
en el apigateway para ver que hacer cuando hay más de un controller en el microservicio