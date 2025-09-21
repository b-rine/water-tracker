# Water Tracker

A full-stack web application for tracking daily water intake with goal management and progress visualization.

## Features

- **Visual Progress**: Interactive water droplet fills as you reach your daily goal
- **Quick Logging**: Preset buttons (8, 12, 16, 20 oz) or custom amounts
- **Goal Management**: Set and adjust daily water intake targets
- **Date Navigation**: View historical logs and progress
- **Smooth Animations**: Real-time visual feedback on water consumption

## Quick Start

### Prerequisites
- Java 21 or higher
- Node.js 22 or higher  
- Maven

### Setup & Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/b-rine/water-tracker.git
   cd water-tracker
   ```

2. **Start the backend** (Terminal 1)
   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```

3. **Start the frontend** (Terminal 2)
   ```bash
   cd frontend
   npm install
   ng serve
   ```

4. **Access the application**
   - **Frontend**: http://localhost:4200
   - **Backend API**: http://localhost:8080
   - **Database Console**: http://localhost:8080/h2-console

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/tracker/summary` | Daily summary with progress |
| POST | `/tracker` | Add water log |
| DELETE | `/tracker/{id}` | Delete water log |
| POST | `/tracker/goal` | Update daily goal |

## Database

H2 database with auto-generated tables:
- **water_db**: `id`, `ounces`, `logged_at`, `log_date`
- **daily_goals**: `id`, `goal_date`, `goal_ounces`, `created_at`

**Console:** `http://localhost:8080/h2-console` (JDBC: `jdbc:h2:file:./data/water_tracker`, User: `sa`)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
