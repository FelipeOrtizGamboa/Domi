# 🛵 Domi - Sistema de Gestión y Asignación de Domicilios

Aplicación de consola desarrollada en Java puro para optimizar la operación logística de Domi, una startup bogotana de domicilios. El sistema gestiona clientes, repartidores y pedidos en memoria, tomando decisiones automáticas sobre qué vehículo asignar según distancia, tamaño del paquete, condiciones climáticas (modo lluvia) y costos operativos.


## 🎥 Video Demo
+ Enlace al video:

## Equipo #5

+ Felipe Ortiz Gamboa

+ Jesús David López Ahumada

+ Andres Felipe Ordoñez Gonzalez

+ Alexandra Vanessa Del Valle

+ Camilo Andrés Ospina Villa

## ▶️ Cómo ejecutar el proyecto en IntelliJ

1. Clonar el repositorio desde GitHub (en IntelliJ: File → New → Project from Version Control).
2. Abrir el proyecto y verificar que tenga un JDK configurado (File → Project Structure → SDK).
3. Abrir el archivo `Main.java` del paquete `app`.
4. Clic derecho sobre `Main` → Run 'Main.main()'.
5. Usar el menú en la consola. El programa carga al iniciar los 9 barrios, 3 repartidores y 2 clientes de arranque.

## Pregunta de la sección 6.3

**1) ¿Por qué un mapa para los clientes y no una lista?**

RTA: 
Porque siempre buscamos al cliente por la cédula. Con el HashMap hacemos clientes.get(cedula) y llega directo; con una lista habría que recorrerla cliente por cliente hasta encontrarlo.

**2) ¿Qué ventaja tiene que los barrios no se repitan?** 

RTA:
El HashSet no deja meter dos veces "Chapinero", así que la lista de cobertura siempre está limpia, y preguntar si un barrio está es rápido.

**3) ¿Por qué el operador nunca escribe los km?** 

RTA:
Porque saldrían distintos según quién atienda y alguien podría cobrar mal. Los km salen del HashMap barrio → km, que siempre da lo mismo para el mismo barrio.

**4) ¿Por qué para los repartidores sí sirve una lista?**

RTA:
Porque al cotizar hay que preguntarles a todos, en orden. No los buscamos por una llave sino que los recorremos completos.

**5) ¿Cómo encontramos un pedido por su número?**

RTA:
Recorriendo la lista de pedidos hasta encontrar el que tiene ese número (`buscarPedido` en DomiService). Como el número es consecutivo, también se podría usar la posición en la lista, pero recorrerla es más seguro.

## La regla de la lluvia

Quedó repartida en dos partes:

- DomiService guarda si está lloviendo (modoLluvia) y se lo pasa a cada repartidor cuando cotiza.
- El 20 % está en un solo método de Repartidor, cotizar(km, lluvia), que llama a calcularCosto(km) de la hija y, si llueve, multiplica por 1,2.
- Que la bici y el dron no salgan con lluvia lo decide cada uno en "puedeLlevar".

No lo pusimos en Moto y Carro porque sería la misma regla escrita dos veces. Además, si mañana llega otro vehículo que sí salga con lluvia, el recargo ya le aplica sin tocar nada.

## Jefe final, el dron

Para agregar el dron tocamos 2 archivos:

- Creamos Dron.java con sus números porque en la distribucion del equipo ya habiampos incluido el dron desde el comienzo para el diseño.
- En Main.java agregamos la opción "4. Dron" al registrar un repartidor.

El método que cotiza y asigna no se tocó. DomiService les pregunta a todos puedeLlevar(...) y cotizar(...) sin saber si son bici, moto o dron, y cada objeto responde con su propio cálculo.

## Reflexión de cierre

**Cierren el programa y vuelvan a abrirlo. ¿Qué pasó con los pedidos del día?**

RTA: Se perdieron. Los datos viven solo en la memoria mientras el programa está abierto (en las colecciones del servicio), y al cerrarlo se borran. Al volver a abrirlo solo aparecen los datos de arranque: los 9 barrios, los 3 repartidores y los 2 clientes.

**Si Domi abre una segunda sede con otro operador, ¿cómo compartirían los clientes y los pedidos entre las dos?**

RTA: Con una base de datos compartida que ambas sedes consulten y actualicen. Así lo que registre un operador lo vería el otro al instante, y no se asignaría el mismo repartidor en dos sedes a la vez. Como DomiService es quien guarda y busca los datos, el cambio se concentraría ahí y el resto del código casi no cambiaría.