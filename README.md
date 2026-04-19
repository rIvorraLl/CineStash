# CineStash 🎬

**CineStash** is a lightweight, local-first cinema diary for movie enthusiasts. It allows you to track your viewing history, manage your collection, and gain insights into your movie-watching habits—all while maintaining complete control over your data.

---

## 🔒 Privacy & Freedom

CineStash is built on the principles of **user independence and privacy**:

-   **100% Local-First:** All data is stored locally on your machine in a SQLite database. No data is ever sent to a cloud server or a third-party service.
-   **No External Connections:** The application does not rely on external APIs to fetch movie data, posters, or metadata. Every piece of information in your diary is what *you* choose to put there. Nothing leaks to the internet.
-   **Data Sovereignty:** Your data is yours. Built-in **Import** and **Export** features allow you to move your entire collection as a standard JSON file at any time.
-   **Full Transparency:** Open-source software that respects your freedom. No tracking, no analytics, no hidden "home-calling" features.

---

## 🏗️ Technical Foundation

CineStash tries to follow modern software engineering standards, including **Hexagonal Architecture (Ports & Adapters)** and **SOLID** principles, and it has been developed as an exercise to put those into practice.

---

## 🚀 Getting Started

### Prerequisites

-   **Java 21** or higher.

### Installation & Run

#### Option 1: Development Mode (Requires Maven)
If you have **Maven** installed, you can build and run the application directly from the source:
```bash
git clone https://github.com/rIvorraLl/cinestash.git
cd cinestash
mvn clean install
mvn spring-boot:run
```

#### Option 2: Production/Standalone Mode (Using the JAR)
If you have been provided with the jar, or have built the project already, you can run CineStash on any system with Java 21.
```bash
# To run the pre-built JAR (adjust your actual jar version)
java -jar target/cinestash-*.jar
```
*(Note: If you are building it yourself from source, you will need Maven once to run `mvn clean package` to generate this JAR file in the `target/` directory.)*

### Accessing the App

Once started, the application will:
1.  **Automatically try to launch** your default web browser.
2.  If the browser does not open automatically, you can access the interface manually at:
    **[http://localhost:8080](http://localhost:8080)**

---

## 📡 API Overview

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/movies` | Add a new movie entry |
| `GET` | `/api/movies` | Search and paginate movies |
| `DELETE` | `/api/movies/{id}` | Remove a movie entry |
| `GET` | `/api/movies/stats` | Retrieve cinema statistics |
| `GET` | `/api/movies/export` | Export all data as JSON |
| `POST` | `/api/movies/import` | Bulk import movies from JSON |

---

## 🤝 Contributing & Support

We welcome contributions to make CineStash even better! You can help by:

-   **Reporting Bugs:** Open an issue to let us know about any unexpected behavior.
-   **Suggesting Features:** Have an idea for a new statistic or improvement? We'd love to hear it!
-   **Code Contributions:** Feel free to fork the repository and submit pull requests.

---

## 📄 License

This project is open-source and available under the **GNU General Public License (GPL)**. See the [LICENSE](LICENSE) file for details.
