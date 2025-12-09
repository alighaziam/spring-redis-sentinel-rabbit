# Spring Boot Redis Sentinel RabbitMQ Integration

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Redis](https://img.shields.io/badge/Redis-7-red.svg)](https://redis.io/)
[![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3-blue.svg)](https://www.rabbitmq.com/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

[English](#english) | [فارسی](#فارسی)

---

<a name="english"></a>
## 🇺🇸 English

A comprehensive Spring Boot application demonstrating integration with **Redis Sentinel** (for high-availability caching) and **RabbitMQ** (for message queuing). This project showcases best practices for building scalable, resilient microservices.

### 🌟 Features

- ✅ **Redis Sentinel Integration** - High availability Redis with automatic failover
- ✅ **RabbitMQ Integration** - Reliable message queuing with dead letter queues
- ✅ **Spring Cache Abstraction** - Easy-to-use caching with Redis
- ✅ **RESTful API** - Complete REST endpoints for Redis and RabbitMQ operations
- ✅ **Docker Compose** - One-command setup for all services
- ✅ **Health Checks** - Actuator endpoints for monitoring
- ✅ **JSON Serialization** - Automatic JSON conversion for messages
- ✅ **Error Handling** - Dead letter queues for failed messages

### 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose (for local development)

### 🚀 Quick Start

#### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/spring-redis-sentinel-rabbit.git
cd spring-redis-sentinel-rabbit
```

#### 2. Start Infrastructure with Docker Compose

```bash
docker-compose up -d
```

This will start:
- Redis Master (port 6379)
- Redis Replica 1 (port 6380)
- Redis Replica 2 (port 6381)
- Redis Sentinel 1 (port 26379)
- Redis Sentinel 2 (port 26380)
- Redis Sentinel 3 (port 26381)
- RabbitMQ (ports 5672, 15672)

#### 3. Build and Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

Or run with a specific profile:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

The application will start on `http://localhost:8080`

### 📖 API Endpoints

#### Redis Operations

##### Store a value in Redis
```bash
POST /api/demo/redis/set?key=mykey&value=myvalue&ttlSeconds=3600
```

##### Get a value from Redis
```bash
GET /api/demo/redis/get/{key}
```

##### Check if key exists
```bash
GET /api/demo/redis/exists/{key}
```

##### Delete a key
```bash
DELETE /api/demo/redis/delete/{key}
```

#### RabbitMQ Operations

##### Send a message
```bash
POST /api/demo/rabbitmq/send
Content-Type: application/json

{
  "content": "Hello from RabbitMQ!",
  "sender": "API User"
}
```

#### Health Check

```bash
GET /api/demo/health
```

### 🧪 Example Usage

#### Using Redis

```bash
# Store a value
curl -X POST "http://localhost:8080/api/demo/redis/set?key=user:123&value=John Doe&ttlSeconds=3600"

# Get the value
curl http://localhost:8080/api/demo/redis/get/user:123

# Check if exists
curl http://localhost:8080/api/demo/redis/exists/user:123

# Delete
curl -X DELETE http://localhost:8080/api/demo/redis/delete/user:123
```

#### Using RabbitMQ

```bash
# Send a message
curl -X POST http://localhost:8080/api/demo/rabbitmq/send \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Hello World!",
    "sender": "Test User"
  }'
```

### 🏗️ Project Structure

```
spring-redis-sentinel-rabbit/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/redisrabbit/
│   │   │       ├── config/          # Configuration classes
│   │   │       ├── controller/      # REST controllers
│   │   │       ├── listener/        # RabbitMQ listeners
│   │   │       ├── model/           # Data models
│   │   │       └── service/         # Business logic
│   │   └── resources/
│   │       ├── application.yml      # Main configuration
│   │       ├── application-dev.yml  # Development profile
│   │       └── application-prod.yml # Production profile
│   └── test/                        # Test files
├── docker-compose.yml               # Infrastructure setup
├── pom.xml                          # Maven dependencies
└── README.md                        # This file
```

### ⚙️ Configuration

#### Redis Sentinel Configuration

The application is configured to use Redis Sentinel for high availability. Edit `application.yml`:

```yaml
spring:
  data:
    redis:
      sentinel:
        master: mymaster
        nodes:
          - localhost:26379
          - localhost:26380
          - localhost:26381
      password: ${REDIS_PASSWORD:}
```

#### RabbitMQ Configuration

```yaml
spring:
  rabbitmq:
    host: ${RABBITMQ_HOST:localhost}
    port: ${RABBITMQ_PORT:5672}
    username: ${RABBITMQ_USERNAME:guest}
    password: ${RABBITMQ_PASSWORD:guest}
```

### 🔍 Monitoring

#### RabbitMQ Management UI

Access the RabbitMQ Management UI at:
- URL: http://localhost:15672
- Username: `guest`
- Password: `guest`

#### Actuator Endpoints

- Health: http://localhost:8080/actuator/health
- Info: http://localhost:8080/actuator/info
- Metrics: http://localhost:8080/actuator/metrics

### 🧩 Key Components

#### RedisSentinelConfig
Configures Redis connection with Sentinel support, connection pooling, and JSON serialization.

#### RabbitMQConfig
Sets up RabbitMQ with exchanges, queues, dead letter queues, and message converters.

#### RedisService
Provides caching and key-value operations with Redis.

#### RabbitMQService
Handles message publishing to RabbitMQ queues.

#### RabbitMQListener
Listens to and processes messages from RabbitMQ queues.

### 🛠️ Development

#### Running Tests

```bash
mvn test
```

#### Building for Production

```bash
mvn clean package -Pprod
```

#### Running with Docker

```bash
# Build the application
mvn clean package

# Build Docker image
docker build -t spring-redis-sentinel-rabbit .

# Run with docker-compose
docker-compose up
```

### 📝 Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `REDIS_PASSWORD` | Redis password | (empty) |
| `REDIS_SENTINEL_MASTER` | Redis Sentinel master name | `mymaster` |
| `REDIS_SENTINEL_NODES` | Comma-separated Sentinel nodes | `localhost:26379,localhost:26380,localhost:26381` |
| `RABBITMQ_HOST` | RabbitMQ host | `localhost` |
| `RABBITMQ_PORT` | RabbitMQ port | `5672` |
| `RABBITMQ_USERNAME` | RabbitMQ username | `guest` |
| `RABBITMQ_PASSWORD` | RabbitMQ password | `guest` |

### 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

### 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Redis team for Redis Sentinel
- RabbitMQ team for the message broker
- All contributors and users of this project

### 📧 Contact

For questions or suggestions, please open an issue on GitHub.

---

<a name="فارسی"></a>
## 🇮🇷 فارسی

یک برنامه جامع Spring Boot که یکپارچه‌سازی **Redis Sentinel** (برای کش با قابلیت دسترسی بالا) و **RabbitMQ** (برای صف پیام) را نشان می‌دهد. این پروژه بهترین روش‌ها برای ساخت میکروسرویس‌های مقیاس‌پذیر و مقاوم را به نمایش می‌گذارد.

### 🌟 ویژگی‌ها

- ✅ **یکپارچه‌سازی Redis Sentinel** - Redis با قابلیت دسترسی بالا و failover خودکار
- ✅ **یکپارچه‌سازی RabbitMQ** - صف‌بندی پیام قابل اعتماد با صف‌های Dead Letter
- ✅ **Spring Cache Abstraction** - کش آسان با Redis
- ✅ **RESTful API** - اندپوینت‌های کامل REST برای عملیات Redis و RabbitMQ
- ✅ **Docker Compose** - راه‌اندازی یک دستوری برای تمام سرویس‌ها
- ✅ **Health Checks** - اندپوینت‌های Actuator برای مانیتورینگ
- ✅ **JSON Serialization** - تبدیل خودکار JSON برای پیام‌ها
- ✅ **Error Handling** - صف‌های Dead Letter برای پیام‌های ناموفق

### 📋 پیش‌نیازها

- Java 17 یا بالاتر
- Maven 3.6+
- Docker و Docker Compose (برای توسعه محلی)

### 🚀 شروع سریع

#### 1. کلون کردن مخزن

```bash
git clone https://github.com/yourusername/spring-redis-sentinel-rabbit.git
cd spring-redis-sentinel-rabbit
```

#### 2. راه‌اندازی زیرساخت با Docker Compose

```bash
docker-compose up -d
```

این دستور موارد زیر را راه‌اندازی می‌کند:
- Redis Master (پورت 6379)
- Redis Replica 1 (پورت 6380)
- Redis Replica 2 (پورت 6381)
- Redis Sentinel 1 (پورت 26379)
- Redis Sentinel 2 (پورت 26380)
- Redis Sentinel 3 (پورت 26381)
- RabbitMQ (پورت‌های 5672, 15672)

#### 3. ساخت و اجرای برنامه

```bash
mvn clean install
mvn spring-boot:run
```

یا با پروفایل خاص اجرا کنید:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

برنامه روی `http://localhost:8080` اجرا می‌شود

### 📖 اندپوینت‌های API

#### عملیات Redis

##### ذخیره یک مقدار در Redis
```bash
POST /api/demo/redis/set?key=mykey&value=myvalue&ttlSeconds=3600
```

##### دریافت یک مقدار از Redis
```bash
GET /api/demo/redis/get/{key}
```

##### بررسی وجود کلید
```bash
GET /api/demo/redis/exists/{key}
```

##### حذف یک کلید
```bash
DELETE /api/demo/redis/delete/{key}
```

#### عملیات RabbitMQ

##### ارسال پیام
```bash
POST /api/demo/rabbitmq/send
Content-Type: application/json

{
  "content": "سلام از RabbitMQ!",
  "sender": "کاربر API"
}
```

#### بررسی سلامت

```bash
GET /api/demo/health
```

### 🧪 مثال استفاده

#### استفاده از Redis

```bash
# ذخیره یک مقدار
curl -X POST "http://localhost:8080/api/demo/redis/set?key=user:123&value=علی احمدی&ttlSeconds=3600"

# دریافت مقدار
curl http://localhost:8080/api/demo/redis/get/user:123

# بررسی وجود
curl http://localhost:8080/api/demo/redis/exists/user:123

# حذف
curl -X DELETE http://localhost:8080/api/demo/redis/delete/user:123
```

#### استفاده از RabbitMQ

```bash
# ارسال پیام
curl -X POST http://localhost:8080/api/demo/rabbitmq/send \
  -H "Content-Type: application/json" \
  -d '{
    "content": "سلام دنیا!",
    "sender": "کاربر تست"
  }'
```

### 🏗️ ساختار پروژه

```
spring-redis-sentinel-rabbit/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/redisrabbit/
│   │   │       ├── config/          # کلاس‌های پیکربندی
│   │   │       ├── controller/      # کنترلرهای REST
│   │   │       ├── listener/        # لیسنرهای RabbitMQ
│   │   │       ├── model/           # مدل‌های داده
│   │   │       └── service/         # منطق کسب‌وکار
│   │   └── resources/
│   │       ├── application.yml      # پیکربندی اصلی
│   │       ├── application-dev.yml  # پروفایل توسعه
│   │       └── application-prod.yml # پروفایل تولید
│   └── test/                        # فایل‌های تست
├── docker-compose.yml               # راه‌اندازی زیرساخت
├── pom.xml                          # وابستگی‌های Maven
└── README.md                        # این فایل
```

### ⚙️ پیکربندی

#### پیکربندی Redis Sentinel

برنامه برای استفاده از Redis Sentinel برای قابلیت دسترسی بالا پیکربندی شده است. فایل `application.yml` را ویرایش کنید:

```yaml
spring:
  data:
    redis:
      sentinel:
        master: mymaster
        nodes:
          - localhost:26379
          - localhost:26380
          - localhost:26381
      password: ${REDIS_PASSWORD:}
```

#### پیکربندی RabbitMQ

```yaml
spring:
  rabbitmq:
    host: ${RABBITMQ_HOST:localhost}
    port: ${RABBITMQ_PORT:5672}
    username: ${RABBITMQ_USERNAME:guest}
    password: ${RABBITMQ_PASSWORD:guest}
```

### 🔍 مانیتورینگ

#### رابط مدیریت RabbitMQ

به رابط مدیریت RabbitMQ دسترسی پیدا کنید:
- URL: http://localhost:15672
- نام کاربری: `guest`
- رمز عبور: `guest`

#### اندپوینت‌های Actuator

- Health: http://localhost:8080/actuator/health
- Info: http://localhost:8080/actuator/info
- Metrics: http://localhost:8080/actuator/metrics

### 🧩 کامپوننت‌های کلیدی

#### RedisSentinelConfig
پیکربندی اتصال Redis با پشتیبانی Sentinel، connection pooling و serialization JSON.

#### RabbitMQConfig
تنظیم RabbitMQ با exchange، queue، dead letter queue و message converter.

#### RedisService
ارائه عملیات کش و key-value با Redis.

#### RabbitMQService
مدیریت انتشار پیام به صف‌های RabbitMQ.

#### RabbitMQListener
گوش دادن و پردازش پیام‌ها از صف‌های RabbitMQ.

### 🛠️ توسعه

#### اجرای تست‌ها

```bash
mvn test
```

#### ساخت برای تولید

```bash
mvn clean package -Pprod
```

#### اجرا با Docker

```bash
# ساخت برنامه
mvn clean package

# ساخت Docker image
docker build -t spring-redis-sentinel-rabbit .

# اجرا با docker-compose
docker-compose up
```

### 📝 متغیرهای محیطی

| متغیر | توضیحات | پیش‌فرض |
|-------|---------|---------|
| `REDIS_PASSWORD` | رمز عبور Redis | (خالی) |
| `REDIS_SENTINEL_MASTER` | نام master در Redis Sentinel | `mymaster` |
| `REDIS_SENTINEL_NODES` | نودهای Sentinel جدا شده با کاما | `localhost:26379,localhost:26380,localhost:26381` |
| `RABBITMQ_HOST` | هاست RabbitMQ | `localhost` |
| `RABBITMQ_PORT` | پورت RabbitMQ | `5672` |
| `RABBITMQ_USERNAME` | نام کاربری RabbitMQ | `guest` |
| `RABBITMQ_PASSWORD` | رمز عبور RabbitMQ | `guest` |

### 🤝 مشارکت

مشارکت‌ها خوش‌آمد هستند! لطفاً Pull Request ارسال کنید.

1. Fork کردن مخزن
2. ایجاد شاخه feature (`git checkout -b feature/AmazingFeature`)
3. Commit تغییرات (`git commit -m 'افزودن ویژگی جدید'`)
4. Push به شاخه (`git push origin feature/AmazingFeature`)
5. باز کردن Pull Request

### 📄 مجوز

این پروژه تحت مجوز MIT است - برای جزئیات فایل [LICENSE](LICENSE) را ببینید.

### 🙏 قدردانی

- تیم Spring Boot برای فریمورک عالی
- تیم Redis برای Redis Sentinel
- تیم RabbitMQ برای message broker
- تمام مشارکت‌کنندگان و کاربران این پروژه

### 📧 تماس

برای سوالات یا پیشنهادات، لطفاً یک issue در GitHub باز کنید.

---

⭐ اگر این پروژه برای شما مفید بود، لطفاً به آن ستاره بدهید!
