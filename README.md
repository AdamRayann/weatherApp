# weatherApp


![image](https://github.com/user-attachments/assets/b9148aa1-8b21-4082-a17e-9ca5a8525207)

## How to Build and Run the Project

### Prerequisites
- Java 17 or higher
- Maven 3.9+
- Internet connection (required to fetch API data)

---

###  Build Locally

```bash
# Clone the project
git clone https://github.com/AdamRayann/weatherApp.git
cd weatherApp

# Build the JAR with dependencies
mvn clean package
```

The app will:

Fetch cities from the remote API

Retrieve weather data for each city

Print results to the console

Save output to output.csv (in project root)


Architecture Notes

Rate Limiting
A custom RateLimiter ensures no more than 60 API calls per minute.

Additional random Thread.sleep(...) calls were added to spread requests more naturally.


Concurrency
Weather data is fetched in parallel using a fixed thread pool (ExecutorService).

Each request still passes through the rate limiter for safety.


Retry Logic
HTTP 429 errors (Too Many Requests) are detected and retried after a delay.

Errors are logged but don’t crash the entire app — the process is resilient and continues for other cities.


With More Time, I Would Improve:
Use a proper logging framework (e.g., SLF4J + Logback) instead of System.out

Add integration tests with mocks for external APIs

Save weather data to a proper database (like PostgreSQL or MongoDB) instead of CSV
