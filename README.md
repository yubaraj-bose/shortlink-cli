# Link Mapper CLI

A command-line based link mapping system built using Java, JDBC, and MySQL.  
This project allows users to store long URLs and retrieve them using short codes.

---

## 🚀 Features

- Generate short codes for long URLs
- Retrieve original URL using short code
- Track number of accesses (click count)
- View all stored mappings
- Fully CLI-based (no frontend)
- MySQL database running via Docker

---

## 🛠️ Tech Stack

- Java (CLI application)
- JDBC (Database connectivity)
- MySQL (Database)
- Docker (Database containerization)

---

## 📁 Project Structure
shortlink-cli/
├── lib/
│   └── mysql-connector-j-8.4.0.jar
├── config/
│   └── db.properties
├── src/
│   └── com/
│       └── urlshortener/
│           ├── Main.java
│           ├── db/
│           │   └── Db.java
│           ├── model/
│           │   └── UrlMapping.java
│           ├── repo/
│           │   └── UrlRepository.java
│           ├── service/
│           │   ├── ShortCodeGenerator.java
│           │   └── UrlService.java
│           └── util/
│               └── InputUtil.java
└── docker-compose.yml


---

## ⚙️ Setup Instructions


### 1. Clone the repository
git clone <your-repo-url>
cd shortlink-cli

# Add Mysql JDBC Driver
shortlink-cli/lib/mysql-connector-j-9.6.0.jar