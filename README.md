# 📚 Online Library Management System

A web-based Library Management System built with Spring Boot, MySQL, HTML, CSS, and JavaScript.

---

## 🛠️ Technologies Used

- Java 17
- Spring Boot 3
- Spring Data JPA
- MySQL (via XAMPP)
- HTML, CSS, JavaScript

---

## ⚙️ Setup Instructions

### Step 1 — Install Required Tools

Make sure you have these installed on your computer:

- [Java JDK 17](https://adoptium.net) — download and install Temurin 17 LTS
- [IntelliJ IDEA Community](https://www.jetbrains.com/idea/download/) — free code editor
- [XAMPP](https://www.apachefriends.org) — provides MySQL database

---

### Step 2 — Download the Project

**Option A — Using IntelliJ (Recommended)**
1. Open IntelliJ IDEA
2. Click **File** → **New** → **Project from Version Control**
3. Paste this URL:https://github.com/Ratika1004/library-system
4. Click **Clone**
5. Wait for IntelliJ to finish loading the project

**Option B — Download as ZIP**
1. Click the green **Code** button on this page
2. Click **Download ZIP**
3. Unzip the folder somewhere on your computer
4. Open IntelliJ → **File** → **Open** → select the unzipped folder

---

### Step 3 — Set Up the Database

1. Open **XAMPP Control Panel**
2. Click **Start** next to **MySQL**
3. Click **Start** next to **Apache**
4. Open your browser and go to: `http://localhost/phpmyadmin`
5. Click **New** in the left sidebar
6. Type `library_db` as the database name → click **Create**
7. Click on `library_db` in the left sidebar
8. Click the **SQL** tab
9. Paste the following SQL and click **Go**:

```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('USER', 'ADMIN') DEFAULT 'USER'
);

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100),
    category VARCHAR(50),
    total_copies INT DEFAULT 1,
    available_copies INT DEFAULT 1
);

CREATE TABLE borrow_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE,
    status ENUM('BORROWED', 'RETURNED') DEFAULT 'BORROWED',
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);
```

10. Click the **SQL** tab again and paste this to add sample data → click **Go**:

```sql
INSERT INTO books (title, author, category, total_copies, available_copies) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', 3, 3),
('To Kill a Mockingbird', 'Harper Lee', 'Fiction', 2, 2),
('1984', 'George Orwell', 'Dystopia', 4, 4),
('Clean Code', 'Robert C. Martin', 'Technology', 2, 2);

INSERT INTO users (name, email, password, role) VALUES
('Admin User', 'admin@library.com', 'admin123', 'ADMIN');
```

---

### Step 4 — Configure the Project

Open the file:src/main/resources/application.properties
Make sure it contains:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> **Note:** The password is left blank — that is the default XAMPP MySQL setup. If you set a MySQL password in XAMPP, enter it here.

---

### Step 5 — Run the Project

1. Make sure **XAMPP MySQL is running** (green status in XAMPP Control Panel)
2. In IntelliJ, find the file:src/main/java/com/example/library/LibraryApplication.java
3. Click the green **Run** button ▶ at the top of IntelliJ
4. Wait until you see this in the console:Started LibraryApplication
5. Open your browser and go to:http://localhost:8080/login.html

---

## 📁 Project Structure
src/
└── main/
├── java/com/example/library/
│   ├── model/
│   │   ├── Book.java
│   │   ├── User.java
│   │   └── BorrowHistory.java
│   ├── repository/
│   │   ├── BookRepository.java
│   │   ├── UserRepository.java
│   │   └── BorrowHistoryRepository.java
│   ├── service/
│   │   ├── BookService.java
│   │   ├── UserService.java
│   │   └── BorrowHistoryService.java
│   ├── controller/
│   │   ├── BookController.java
│   │   ├── UserController.java
│   │   └── BorrowHistoryController.java
│   └── LibraryApplication.java
└── resources/
├── static/
│   ├── index.html
│   ├── search.html
│   ├── history.html
│   ├── admin.html
│   ├── login.html
│   ├── register.html
│   └── css/
│       └── style.css
└── application.properties

## ⚠️ Common Issues

**App crashes on startup?**
- Make sure XAMPP MySQL is running before starting the app

**CSS not loading?**
- Make sure all HTML files are inside `src/main/resources/static/`
- Make sure `style.css` is inside `src/main/resources/static/css/`

**Cannot login?**
- Check that your email and password match exactly what is in the database
- Go to `http://localhost/phpmyadmin` and check the `users` table
