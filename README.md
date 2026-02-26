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

### Ejecutar todas las pruebas
Para ejecutar todas las pruebas unitarias del proyecto en conjunto, abre tu terminal en la raíz del proyecto y ejecuta:

```bash
mvn test
```

### Ejecutar una prueba individual
Si deseas ejecutar únicamente una prueba específica (por ejemplo, mientras desarrollas o depuras), puedes utilizar el parámetro `-Dtest` seguido de la ruta de la clase. Por ejemplo:

```bash
mvn test -Dtest=Testing.Wallet.GetWalletByUserIdTest
```

## 👥 Equipo de Trabajo
* **Docentes:** Profa. Yosly Hernández B. / Prof. Marcel Castro.
* **Desarrolladores:**
  * José Dos Reis
  * Daniel Briceño
  * Jean Cheng

---
*Facultad de Ciencias - Escuela de Computación - UCV (2025)*
