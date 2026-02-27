# Comedor Ciens UCV - Sistema de Gestión de Comedor Universitario (SGCU)

## 📌 Descripción del Proyecto
El **SGCU** es una solución de escritorio desarrollada en **Java** para automatizar y optimizar el servicio de alimentación de la Universidad Central de Venezuela. Ante la reducción del subsidio gubernamental, el sistema ahora integra una gestión de costos avanzada y una estructura de tarifas diferenciadas para garantizar la sostenibilidad del servicio.

## 🚀 Metodología y Marco de Trabajo
El proyecto inició con la metodología de desarrollo **Rational Unified Process (RUP)** para los artefactos iniciales: modelo de dominio, análisis de requerimientos y diseño. Posteriormente, se adoptó la metodología ágil **Extreme Programming (XP)**, priorizando la entrega continua de software funcional y la excelencia técnica.
* **Gestión:** Sprints cortos con integración continua.
* **Control de Versiones:** Git con flujo de trabajo GitHub Flow.
* **Pruebas:** Desarrollo guiado por pruebas unitarias automatizadas.

## 🛠️ Tecnologías Utilizadas
* **Lenguaje:** Java (OpenJDK v17).
* **Interfaz Gráfica:** Java Swing.
* **Gestión de Dependencias:** Apache Maven.
* **Pruebas Unitarias:** JUnit Framework.
* **Editor Sugerido:** VS Code con el *Extension Pack for Java*.

## 📦 Paquetes y Dependencias (pom.xml)
El proyecto hace uso de las siguientes librerías y plugins gestionados a través de Maven:
* **Jackson Databind (`jackson-databind`):** Librería principal utilizada para la lectura y escritura de archivos JSON. Permite mapear (serializar y deserializar) los objetos Java hacia los archivos `.json` que actúan como la base de datos del sistema.
* **JUnit Jupiter (`junit-jupiter`):** Framework de pruebas (JUnit 5) utilizado para escribir y ejecutar las pruebas unitarias del sistema, asegurando la calidad del código.
* **Maven Surefire Plugin (`maven-surefire-plugin`):** Plugin de Maven encargado de reconocer y ejecutar automáticamente las pruebas unitarias durante la fase de *test* del ciclo de vida de construcción.
* **Exec Maven Plugin (`exec-maven-plugin`):** Plugin que facilita la ejecución de la aplicación (la clase `Main`) directamente desde la línea de comandos de Maven.

---

## 📈 Nuevos Requerimientos y Reglas de Negocio
Basado en la coyuntura económica actual, el sistema implementa:

### 1. Cálculo del Costo Cubierto de la Bandeja (CCB)
Se determina el valor real de cada comida mediante la fórmula:
$CCB = [(CF + CV) / NB] * (1 + \%Merma)$
* **CF / CV:** Costos fijos y variables (mano de obra, insumos, energía, etc.).
* **NB:** Número de bandejas proyectadas o servidas.
* **Merma:** Factor de desperdicio por manipulación de alimentos.

### 2. Estructura de Tarifas Diferenciadas
* **Estudiantes:** Subvencionados (pagan entre 20% y 30% del CCB).
* **Profesores:** Pagan entre 70% y 90% del CCB.
* **Empleados:** Pagan entre 90% y 110% del CCB.

### 3. Control de Acceso y Pago (Simulaciones)
* **Reconocimiento Facial:** Validación de identidad mediante patrones biométricos en conjunto con la base de datos de Secretaría.
* **Monedero Virtual:** Subsistema de prepago mediante pago móvil exclusivo para el comedor.

---

## 📋 Historias de Usuario Prioritarias (Sprint 1)

### HU-06: Gestión de Costos y CCB
**Como** Administrador, **quiero** registrar los costos fijos, variables y el porcentaje de merma, **para** calcular automáticamente el Costo Cubierto de la Bandeja (CCB).
* **Criterio de Aceptación:** El sistema debe aplicar la fórmula estándar y permitir actualizar los valores mensualmente.

### HU-07: Monedero Virtual y Pago
**Como** Comensal, **quiero** recargar saldo en mi monedero virtual, **para** que el sistema descuente automáticamente la tarifa correspondiente al momento de consumir.
* **Criterio de Aceptación:** El sistema debe verificar el rol del usuario (estudiante/profe/empleado) para aplicar la tarifa correcta según el CCB vigente.

### HU-08: Control de Acceso Facial (Simulado)
**Como** Personal de Seguridad, **quiero** validar la identidad del usuario mediante reconocimiento facial, **para** permitir el acceso solo a individuos autorizados con saldo disponible.
* **Criterio de Aceptación:** Se debe realizar un "match" con el registro de Secretaría antes de procesar el cobro en el monedero.

---

### Levantar el proyecto con los siguientes comandos Maven:
```bash
mvn clean install
mvn exec:java
ó
mvn exec:java -Dexec.mainClass="com.edu.ucv.comedor.Main"
```

## 🧪 Pruebas Unitarias (Testing)

El proyecto utiliza **JUnit 5** y el plugin **Maven Surefire** para la creación y ejecución de pruebas unitarias. Todas las pruebas se encuentran dentro del directorio `src/Testing/`.

### Pruebas Disponibles
Actualmente, el proyecto cuenta con las siguientes pruebas:
* **`Testing.Wallet.GetWalletByUserIdTest`**: Verifica que el método `getWalletByUserId` obtenga correctamente los datos de la billetera (wallet) asociada a un ID de usuario específico (ej. usuario 11), validando su balance, estado y correspondencia de IDs.
* **`Testing.Wallet.RechargeWalletTest`**: Verifica el método `rechargeWallet`, comprobando recargas exitosas (incremento de saldo), manejo de errores al intentar recargar montos negativos y validación de usuarios inexistentes.
* **`Testing.Menu.CreateMenuTest`**: Verifica el método `create` del servicio de menús, comprobando la creación exitosa de un menú (y su posterior borrado lógico), así como el manejo de errores al intentar crear menús sin platos o con más de 3 platos.
* **`Testing.User.RegisterUserTest`**: Verifica el método `register` del servicio de autenticación, comprobando validaciones de roles (ej. un estudiante no puede registrarse como administrador), el registro exitoso de un comensal, la prevención de correos duplicados y la limpieza de la base de datos mediante el borrado lógico.
* **`Testing.User.GetUserByIdTest`**: Verifica el método `getUserById` del servicio de usuarios, comprobando que se obtengan correctamente los datos de un usuario existente y que retorne nulo al buscar un ID inexistente.
* **`Testing.Config.UpdateConfigTest`**: Verifica el método `updateConfig` del servicio de configuración, comprobando las validaciones de rangos permitidos para los porcentajes de subsidio y valores de costos fijos, además de validar la actualización exitosa y restauración de los datos.
* **`Testing.Booking.ChargeForServiceTest`**: Verifica el método `chargeForService` del servicio de reservas, comprobando el cobro exitoso de un servicio con saldo suficiente y el manejo de errores cuando el usuario no tiene saldo suficiente en su billetera.
* **`Testing.Booking.ChargeForServiceTestWithRestore`**: Variante de la prueba de cobro de reservas que incluye la restauración automática del saldo del usuario y del estado de la reserva a sus valores originales después de cada ejecución, garantizando la idempotencia de las pruebas.
* **`Testing.Booking.CreateBookingTest`**: Verifica el método `create` del servicio de reservas, comprobando la creación exitosa de una reserva válida y la validación que impide a un usuario tener más de una reserva para el mismo turno y día.

### Ejecutar todas las pruebas
Para ejecutar todas las pruebas unitarias del proyecto en conjunto, abre tu terminal en la raíz del proyecto y ejecuta:

```bash
mvn test
```

### Ejecutar una prueba individual
Si deseas ejecutar únicamente una prueba específica (por ejemplo, mientras desarrollas o depuras), puedes utilizar el parámetro `-Dtest` seguido de la ruta de la clase. Ejemplos:

```bash
mvn test -Dtest=Testing.Wallet.GetWalletByUserIdTest
mvn test -Dtest=Testing.Wallet.RechargeWalletTest
mvn test -Dtest=Testing.Menu.CreateMenuTest
mvn test -Dtest=Testing.User.RegisterUserTest
mvn test -Dtest=Testing.User.GetUserByIdTest
mvn test -Dtest=Testing.Config.UpdateConfigTest
mvn test -Dtest=Testing.Booking.ChargeForServiceTest
mvn test -Dtest=Testing.Booking.ChargeForServiceTestWithRestore
mvn test -Dtest=Testing.Booking.CreateBookingTest
```

## 👥 Equipo de Trabajo
* **Docentes:** Profa. Yosly Hernández B. / Prof. Marcel Castro.
* **Desarrolladores:**
  * José Dos Reis
  * Daniel Briceño
  * Jean Cheng

---
*Facultad de Ciencias - Escuela de Computación - UCV (2025)*
