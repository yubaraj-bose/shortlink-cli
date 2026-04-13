# Shortlink CLI

A command-line URL shortener built with Java, JDBC, and MySQL. Generates short codes for long URLs and supports real browser redirection via a lightweight HTTP server.

---

## Tech Stack

- Java (CLI + HTTP server)
- JDBC
- MySQL via Docker

---

## Project Structure

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
│           ├── ServerMain.java
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
│           ├── server/
│           │   └── RedirectServer.java
│           │
│           └── util/
│               └── InputUtil.java
│
├── docker-compose.yml
└── README.md
```

---

## Setup

### 1. Clone the repository

```bash
git clone <your-repo-url>
cd shortlink-cli
```

### 2. Add MySQL JDBC Driver

Place the driver at:

```
lib/mysql-connector-j-9.6.0.jar
```

### 3. Configure the database

Edit `config/db.properties`:

```properties
db.url=jdbc:mysql://localhost:3307/urldb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
db.user=root
db.password=root
```

### 4. Make sure Docker Desktop is running

### 5. Start MySQL

```bash
docker compose up -d
```

### 6. Compile

```bash
rm -rf out && mkdir -p out
javac -cp "lib/mysql-connector-j-9.6.0.jar" -d out $(find src -name "*.java")
```

---

## Running the Application

You need **two terminals** running simultaneously.

**Terminal 1 — HTTP Server:**

```bash
java -cp "out:lib/mysql-connector-j-9.6.0.jar" com.urlshortener.ServerMain
```

Keep this terminal open. The server runs at `http://localhost:8080`.

**Terminal 2 — CLI:**

```bash
java -cp "out:lib/mysql-connector-j-9.6.0.jar" com.urlshortener.Main
```

---

## Usage

1. In the CLI, select option `1` and enter a long URL:

   ```
  Example:- https://www.google.com
   ```

2. You'll receive a short code and browser URL:

   ```
   Example:-
   Short Code: E9t4SE
   Browser URL: http://localhost:8080/E9t4SE
   ```

3. Open the URL in a browser — it redirects to the original URL.

---

## Database Schema

```sql
CREATE TABLE IF NOT EXISTS urls (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    short_code VARCHAR(20)  NOT NULL UNIQUE,
    long_url   TEXT         NOT NULL,
    clicks     INT          NOT NULL DEFAULT 0,
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
```

To inspect stored mappings:

```bash
docker exec -it url-shortener-mysql mysql -u root -p
# password: root
```

```sql
USE urldb;
SELECT * FROM urls;
```

---

## Notes

- Start Docker before running the application.
- Keep the HTTP server (Terminal 1) running while testing in the browser.

---

## License

MIT
