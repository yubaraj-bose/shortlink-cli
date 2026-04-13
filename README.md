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

```
shortlink-cli/
├── lib/
│   └── mysql-connector-j-9.6.0.jar
│
├── config/
│   └── db.properties
│
├── src/
│   └── com/
│       └── urlshortener/
│           ├── Main.java
│           │
│           ├── db/
│           │   └── Db.java
│           │
│           ├── model/
│           │   ├── UrlMapping.java
│           │   └── ShortenResult.java       
│           │
│           ├── repo/
│           │   └── UrlRepository.java
│           │
│           ├── service/
│           │   ├── ShortCodeGenerator.java
│           │   └── UrlService.java
│           │
│           └── util/
│               └── InputUtil.java
│
│  
│
├── docker-compose.yml
└── README.md
```

---

## ⚙️ Setup Instructions

### 1. Clone the repository

```bash
git clone <your-repo-url>
cd shortlink-cli
```

### 2. Add MySQL JDBC Driver

Download the MySQL Connector/J JAR and place it at:

```
shortlink-cli/lib/mysql-connector-j-9.6.0.jar
```

> You can download it from the [official MySQL downloads page](https://dev.mysql.com/downloads/connector/j/).

### 3. Configure the Database

Edit `config/db.properties` with your database connection details:

```properties
db.url=jdbc:mysql://localhost:3306/shortlink
db.username=root
db.password=yourpassword
```

### 4. Start MySQL with Docker

```bash
docker-compose up -d
```

### 5. Make sure Docker Desktop is up and Running

### 6. Compile and Run

```bash
# Compile
rm -rf out   ##use this only when previous buggy classes exist in out folder.
mkdir -p out
javac -cp "lib/mysql-connector-j-9.6.0.jar" -d out $(find src -name "*.java")

# Run
java -cp "out:lib/mysql-connector-j-9.6.0.jar" com.urlshortener.Main
```

### 7. If you want to view the mysql table within docker 
```bash
docker exec -it url-shortener-mysql mysql -u root -p
password:-root
use urldb;
select * from urls;
exit;
```
---

## 💡 Usage

Once running, the CLI will present a menu:

```
===== URL SHORTENER =====
1. Shorten URL
2. Retrieve Long URL
3. Show All URLs
4. Exit
Enter choice:
```

---

## 🗄️ Database Schema

```sql
  CREATE TABLE IF NOT EXISTS urls (
    id INT AUTO_INCREMENT PRIMARY KEY,
    short_code VARCHAR(20) NOT NULL UNIQUE,
    long_url TEXT NOT NULL,
    clicks INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
```

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
