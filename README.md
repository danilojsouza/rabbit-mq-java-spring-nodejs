## 📦 Inventory Stock Price System

A comprehensive multi-module project demonstrating real-time communication and microservices architecture using **RabbitMQ** to connect applications built in different languages (**Java/Spring** and **Node.js**).

This project showcases expertise in asynchronous messaging, multi-module Maven structure, Docker containerization, and the Spring Boot ecosystem.

### ⚙️ Technologies & Stack

| Category | Technology | Version | Notes |
| :--- | :--- | :--- | :--- |
| **Backend (Core)** | Java | 21 (LTS) | Modern Java features (e.g., Records) are used. |
| **Framework** | Spring Boot | 3.5.6 | Used for rapid development of Producer & Java Consumer. |
| **Messaging Broker** | RabbitMQ | 3-Management | Central messaging backbone. |
| **Consumer (Secondary)** | Node.js | Latest | Demonstrating cross-language communication. |
| **Build Tool** | Maven | 3.9.11 | Manages multi-module structure (`pom` packaging). |
| **Containerization** | Docker / Docker Compose | 28.5.1 | For easy local setup of the RabbitMQ service. |

---

### 🏛️ Architecture Overview

The system is designed around a single RabbitMQ Exchange (`amq.direct`) acting as the central hub for inventory updates.

| Component | Language/Framework | Role |
| :--- | :--- | :--- |
| **Producer** | Java / Spring Boot (`JavaStockPrice`) | Sends `StockDTO` and `PriceDTO` messages to the Exchange, indicating inventory changes. |
| **Consumer 1 (Price Update)** | Java / Spring Boot (`JavaConsumer`) | Receives messages, simulates complex pricing logic, and updates the price. |
| **Consumer 2 ()** | Node.js | To be defined. |
| **Messaging** | RabbitMQ | Connects the Producer and the two diverse Consumers. |

$$\text{Producer (Java)} \xrightarrow{\text{Message}} \text{RabbitMQ Exchange} \begin{cases} \longrightarrow \text{Consumer 1 (Java)} \\ \longrightarrow \text{Consumer 2 (Node.js)} \end{cases}$$

---

### 🚀 Getting Started

Follow these steps to get the project running locally.

#### Prerequisites

* Java Development Kit (JDK 21 or higher)
* Apache Maven (3.9.11 or higher)
* Docker and Docker Compose

#### 1. Start the RabbitMQ Broker

The project uses Docker Compose to manage the RabbitMQ service, ensuring a consistent environment and exposing the necessary ports (5673 for AMQP and 15673 for the Management UI).

```bash
# From the project root directory
docker-compose up -d
````

> **Note:** The broker is configured to run on `localhost:5673` to prevent conflicts with other services using the default 5672 port.

#### 2\. Build the Java Modules

Navigate to the project root and perform a clean installation to build the core modules (`LibRabbitMQ` and `JavaStockPrice`) and install them in your local Maven repository.

```bash
mvn clean install
```

#### 3\. Run the Spring Boot Application (Producer & Java Consumer)

The main application handles both the message production and the Java-based consumption logic. Use the `-pl` flag to execute the specific module containing the application:

```bash
mvn spring-boot:run -pl JavaStockPrice
```

#### 4\. Run the Node.js Consumer

(Assuming you have a separate directory for the Node.js consumer).

```bash
# Navigate to your Node.js consumer directory
cd path/to/nodejs-consumer

# Install dependencies and run
npm install
node index.js
```

-----

### 🧱 Project Structure (Maven Multi-Module)

The project leverages Maven's multi-module capability (`pom` packaging in the root) for clear separation of concerns:

| Module | Packaging | Description |
| :--- | :--- | :--- |
| `parent` (Root) | `pom` | Aggregator and parent POM. Manages common dependencies and versions. |
| `LibRabbitMQ` | `jar` | Shared library module. Contains core DTOs and constants. |
| `JavaStockPrice` | `jar` | The main Spring Boot application. Contains the Producer, Controllers, and the primary application entry point. (API) |

### ✅ Key Demonstrations

  * **Robust DTO Handling:** Use of Java **Records** (Java 21+) for immutable and clean data transfer, solving common serialization errors (e.g., `InvalidDefinitionException`).
  * **Asynchronous Communication:** Proper configuration and use of Spring AMQP to define Exchanges, Queues, and Bindings.
  * **Cross-Language Integration:** Successful messaging between Java (Producer) and Node.js (Consumer).
  * **Maven Efficiency:** Using commands like `mvn clean install` and `mvn spring-boot:run -pl <module>` for optimal multi-module workflow.
  * **Clean Coding:** Employing SLF4J logging instead of `System.out.println`.

<!-- end list -->
