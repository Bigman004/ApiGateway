---

## Routing

| Path | Routes To |
|---|---|
| `/mail-service/**` | Mailing Service |
| `/payment-service/**` | Payment Service |

> Route prefixes may vary — check `application.yml` for the exact configuration.

---

## API Key

The gateway injects an API key into the headers of every forwarded request. Downstream services validate this key before processing the request.

Set the key in `application.properties` or `application.yml`:

```properties
SHARED_KEY=your-secret-api-key
```

Each microservice should verify this key on every inbound request before trusting it.

---

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.x
- [ServiceDiscovery](https://github.com/Bigman004/ServiceDiscovery) running on port `8761`

### Run locally

```bash
git clone https://github.com/Bigman004/ApiGateway.git
cd ApiGateway

./mvnw spring-boot:run
```

### Run with Docker

```bash
docker build -t api-gateway .
docker run -p 8080:8080 api-gateway
```

---

## CI/CD Pipeline

This project uses **GitHub Actions** to automatically build and deploy the gateway on every push to the `master` branch.

### What the pipeline does
Push to master
│
▼
Checkout code
│
▼
Set up Java 21
│
▼
Build with Maven (./mvnw package)
│
▼
Build Docker image
│
▼
Push image to registry


### Secrets required

Add these to your repository under **Settings → Secrets and variables → Actions**:

| Secret | Description |
|---|---|
| `DOCKER_USERNAME` | Docker Hub username |
| `DOCKER_PASSWORD` | Docker Hub password or access token |
| `DEPLOY_HOST` | SSH host of the deployment server |
| `DEPLOY_USER` | SSH username |
| `DEPLOY_KEY` | Private SSH key for the deployment server |
| `API_KEY` | The gateway API key injected at runtime |

> Adjust secret names to match what is defined in your workflow `.yml` file.

### Workflow file location

## Startup Order

Services must be started in this order:
``````
ServiceDiscovery   (Eureka server)
ApiGateway         (this service)
Mailing Service
Payment Service
``````


The gateway will fail to route requests if Eureka is not running.

---

## Related Services

- [ServiceDiscovery](https://github.com/Bigman004/ServiceDiscovery) — Eureka server all services register with
- [paymentService](https://github.com/Bigman004/payment_service) - for initializing payment and communicating with paystack
- [mailingService(notification)](https://github.com/mailing-service) - for sending notification for successful transaction
