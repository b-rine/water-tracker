# 💧 Water Tracker Application

A full-stack web application for tracking daily water intake with goal management and progress visualization.

## 🚀 Features

### Backend (Spring Boot)
- **H2 Database**: Lightweight in-memory database for easy development
- **REST API**: Complete CRUD operations for water logs and daily goals
- **Timestamp Tracking**: Automatic logging of when water was consumed
- **Daily Goal Management**: Set and update daily water intake goals
- **Data Validation**: Input validation with proper error handling
- **File Logging**: Application logs saved to `./logs/water-tracker.log`
- **Unit Tests**: Comprehensive test coverage for services

### Frontend (Angular 19)
- **Modern UI**: Clean, responsive design with Tailwind CSS
- **Progress Visualization**: Interactive progress bars and water level visual
- **Date Navigation**: View water logs for any date
- **Quick Add Buttons**: Common amounts (8, 12, 16, 20 oz) for fast entry
- **Goal Settings**: Easily update daily water goals
- **Real-time Updates**: Instant feedback on progress and achievements
- **Timestamps**: See when each water log was added

## 📋 API Endpoints

### Water Logs
- `GET /tracker` - Get all water logs
- `GET /tracker/today` - Get today's water logs
- `GET /tracker/date/{date}` - Get logs for specific date
- `GET /tracker/summary` - Get daily summary with goal progress
- `POST /tracker` - Add new water log
- `DELETE /tracker/{id}` - Delete water log

### Daily Goals
- `GET /tracker/goal` - Get today's goal
- `POST /tracker/goal` - Update today's goal

## 🗄️ Database Schema

### water_db
- `id` - Primary key
- `ounces` - Amount in ounces (DECIMAL)
- `logged_at` - Timestamp when logged
- `log_date` - Date of the log

### daily_goals
- `id` - Primary key
- `goal_date` - Date for the goal
- `goal_ounces` - Target ounces for the day
- `created_at` - When the goal was created

## 🛠️ Technology Stack

**Backend:**
- Java 23
- Spring Boot 3.3.6
- Spring Data JDBC
- H2 Database
- Maven
- JUnit 5 & Mockito

**Frontend:**
- Angular 19
- TypeScript
- Tailwind CSS
- RxJS

## 🚀 Getting Started

### Prerequisites
- Java 23+
- Node.js 18+
- Maven

### Backend Setup
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Frontend Setup
```bash
cd frontend
npm install
ng serve
```

The frontend will start on `http://localhost:4200`

### H2 Database Console
Access the H2 console at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/water_tracker`
- Username: `sa`
- Password: (leave empty)

## 📊 Usage

1. **Set Your Goal**: Click the ⚙️ settings button to set your daily water goal
2. **Log Water**: Use quick buttons (8, 12, 16, 20 oz) or enter custom amounts
3. **Track Progress**: Watch your progress bar fill up and see percentage completed
4. **View History**: Use the date picker to view logs from previous days
5. **Visual Feedback**: Toggle between list view and water level visualization

## 🧪 Testing

Run backend tests:
```bash
cd backend
mvn test
```

## 📝 Logging

Application logs are saved to `./logs/water-tracker.log` with the following information:
- Water log additions and deletions
- Validation errors
- Application startup and operations

## 🎯 Key Improvements Made

1. **Database Migration**: Switched from MySQL to H2 for simplified setup
2. **Enhanced Data Model**: Added timestamps and daily goal tracking
3. **Better API Design**: RESTful endpoints with proper error handling
4. **Modern Frontend**: Complete UI overhaul with better UX
5. **Progress Tracking**: Real-time progress visualization
6. **Date Navigation**: Historical data viewing
7. **Quick Actions**: Fast water logging with preset amounts
8. **Comprehensive Testing**: Unit tests for core business logic
9. **File Logging**: Persistent logging for debugging and monitoring

## 🔧 Configuration

### Application Properties
The application uses H2 database by default. Key configurations:
- Database file: `./data/water_tracker`
- Logging: `./logs/water-tracker.log`
- Server port: 8080
- CORS enabled for `http://localhost:4200`

### Default Settings
- Daily goal: 64 ounces
- Database auto-creates tables on startup
- Logs rotate daily with 30-day retention
