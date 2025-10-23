## 📦 Inventory Stock Price System: Multi-Language Messaging 🚀

A multi-module project showcasing asynchronous communication and microservices architecture by using **RabbitMQ** to integrate applications built in different languages: **Java/Spring Boot** and **Node.js**.

This system simulates an inventory and price update pipeline, highlighting resilience and cross-language interoperability.

-----

### ⚙️ Key Technologies and Configurations

| Category | Technology | Version | Notes |
| :--- | :--- | :--- | :--- |
| **Backend (Core)** | Java | 21 (LTS) | Uses **Records** for DTOs and immutability. |
| **Framework** | Spring Boot | 3.5.6 | Simplifies AMQP configuration and REST APIs. |
| **Messaging Broker** | RabbitMQ | 3-Management | Central messaging backbone. |
| **Secondary Consumer** | Node.js | Latest | Demonstrates cross-language communication. |
| **Serialization** | **Jackson (JSON)** | — | Standard used for robust, interoperable communication. |
| **Build Tool** | Maven | 3.9.11 | Manages the multi-module structure. |
| **Containerization** | Docker Compose | — | Quick RabbitMQ setup. |

-----

### 🏛️ Architecture & Resilience Highlights

The system uses a message pipeline where the Java Producer sends raw Java objects (`StockDTO`, `PriceDTO`) which are serialized to **JSON** and consumed by diverse services.

1.  **Standardized JSON Serialization:** The `RabbitTemplate` and `Jackson2JsonMessageConverter` are configured to serialize Java objects into JSON, ensuring the Node.js Consumer can deserialize the message effortlessly.
2.  **Contract Modules (`LibRabbitMQ`):** The data transfer objects (e.g., **`StockDTO`**) are centralized in a library module to eliminate code duplication between Java Producer and Consumers.
3.  **DLQ (Dead Letter Queue) Mechanism:** Queues are configured with a Dead Letter Exchange to manage permanent processing failures (e.g., rejecting negative stock updates), routing the message to a quarantine queue (`.dlq`).
4.  **Node.js Consumer:** Demonstrates the ability of RabbitMQ to connect completely different languages seamlessly.

$$\text{Producer (Java)} \xrightarrow{\text{JSON Message}} \text{RabbitMQ} \begin{cases} \longrightarrow \text{Consumer (Java)} \text{ w/ DLQ} \\ \longrightarrow \text{Consumer (Node.js)} \end{cases}$$

-----

### 🚀 Getting Started

#### Prerequisites

* Java Development Kit (JDK 21+)
* Apache Maven (3.9+)
* Docker and Docker Compose
* Node.js and npm

#### 1\. Start the RabbitMQ Broker

Starts RabbitMQ in Docker, with the management UI exposed on port `15673`.

```bash
# From the project root directory
docker-compose up -d
```

#### 2\. Build and Run the Java Applications (Producer & Consumer)

Running `mvn clean install` ensures the shared `LibRabbitMQ` module (containing the DTOs) is available to the rest of the projects.

```bash
# 1. Compile all modules
mvn clean install

# 2. Run the main application (Producer & Java Consumer logic)
mvn spring-boot:run -pl JavaStockPrice
```

#### 3\. Configure and Run the Node.js Consumer

The Node.js Consumer requires credentials setup and the ES module mode to be enabled.

```bash
# 1. Navigate and install dependencies
cd nodejs-consumer
npm install

# 2. Configure the Node.js project as an ES module (crucial for 'import')
# Ensure package.json contains: "type": "module"

# 3. Create the credentials file (replace YOUR_USER/PASSWORD)
echo 'export const RABBITMQ_ADMIN_USER = "YOUR_USER";
export const RABBITMQ_ADMIN_PASSWORD = "YOUR_PASSWORD";' > djs/inventory/rabbitmq-credentials.js

# 4. Run the Node.js Consumer
node djs/inventory/consumer.js
```

-----

### 🧱 Project Structure

| Module | Type | Description |
| :--- | :--- | :--- |
| `parent` (Root) | `pom` | Manages dependencies and versions for all sub-modules. |
| `LibRabbitMQ` | `jar` | Shared library containing **DTOs** (`StockDTO`, `PriceDTO`), **Constants**, and **AMQP Configuration** (`RabbitMQConfig`). |
| `JavaStockPrice` | `jar` | Main Spring Boot application (Message Producer and REST API). |
| `JavaConsumer` | `jar` | Spring Boot application simulating stock/price logic and housing the **DLQ mechanism**. |
| `nodejs-consumer` | Directory | Node.js application responsible for consuming JSON messages. |