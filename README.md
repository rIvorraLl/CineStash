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

CineStash follows modern software engineering standards, including **Hexagonal Architecture (Ports & Adapters)** and **SOLID** principles. This ensures a clean separation between core business logic and infrastructure, making the application robust and maintainable without over-complicating the user experience.

---

## 🚀 Getting Started

### Prerequisites

-   **Java 21** or higher.

### Installation & Run

#### Option 1: Running with Maven
If you have Maven installed, you can build and run the application directly:
```bash
git clone https://github.com/yourusername/cinestash.git
cd cinestash
mvn clean install
mvn spring-boot:run
```

#### Option 2: Running the Standalone JAR
To run CineStash without needing Maven (useful for distribution):
1.  **Build the package:**
    ```bash
    mvn clean package
    ```
2.  **Run the JAR:**
    ```bash
    java -jar target/cinestash-1.1.0.jar
    ```

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
