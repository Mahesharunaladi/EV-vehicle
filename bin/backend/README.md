# EV Demand Prediction - Backend

This is the backend service for the EV Demand Prediction system. It provides REST APIs for predicting EV demand based on historical data using machine learning models.

## 📁 Directory Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── ev/
│   │   │           ├── prediction/
│   │   │           │   └── EVDemandPredictionApplication.java    # Main Spring Boot app
│   │   │           ├── api/
│   │   │           │   └── PredictionApiController.java          # REST API endpoints
│   │   │           ├── controller/
│   │   │           │   └── PredictionController.java             # Web UI controller
│   │   │           ├── model/
│   │   │           │   ├── PredictionInput.java                  # Input model
│   │   │           │   └── PredictionResult.java                 # Output model
│   │   │           └── service/
│   │   │               ├── PredictionService.java                # Prediction logic
│   │   │               ├── LocationService.java                  # Location management
│   │   │               └── ModelService.java                     # ML model handling
│   │   └── resources/
│   │       └── application.properties                            # Application config
│   └── test/                                                     # Test files
├── pom.xml                                                       # Maven configuration
└── README.md                                                     # This file
```

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- Git

### Installation

1. Navigate to the backend directory:
```bash
cd backend
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

## 📡 API Endpoints

### Get All Locations
```
GET /api/locations
```
Returns a list of all available locations for prediction.

**Response:**
```json
[
  "King",
  "Snohomish",
  "India - Delhi",
  ...
]
```

### Get Location Encoding
```
GET /api/locations/{location}/encoding
```
Get the numeric encoding for a specific location.

**Response:**
```json
{
  "location": "King",
  "encoding": 42
}
```

### Make Prediction
```
POST /api/predict
Content-Type: application/json
```

**Request Body:**
```json
{
  "county": "King",
  "currentDate": "2024-01-15",
  "currentEvTotal": 150000,
  "evTotalLag1": 145000,
  "evTotalLag2": 140000,
  "evTotalLag3": 135000
}
```

**Response:**
```json
{
  "prediction": 155000.50,
  "features": {
    "months_since_start": 67,
    "county_encoded": 42,
    "ev_total_lag1": 145000,
    "ev_total_lag2": 140000,
    "ev_total_lag3": 135000,
    "ev_total_roll_mean_3": 141666.67,
    "ev_total_pct_change_1": 0.0345,
    "ev_total_pct_change_3": 0.0714,
    "ev_growth_slope": 7500
  },
  "success": true,
  "error": null
}
```

### Health Check
```
GET /api/health
```
Check if the service is running.

**Response:**
```json
{
  "status": "UP",
  "message": "EV Demand Prediction Service is running"
}
```

## 📝 File Descriptions

### `EVDemandPredictionApplication.java`
Main Spring Boot application class. Initializes and configures the entire application.

### `PredictionApiController.java`
REST API controller handling all HTTP endpoints for predictions and location management.

### `PredictionService.java`
Core service containing prediction logic:
- Feature calculation from input data
- Integration with ML model
- Error handling and validation

### `LocationService.java`
Manages location data:
- Loads locations from CSV file
- Maintains location encoding mappings
- Supports 550+ global locations including US counties and Indian cities

### `ModelService.java`
Handles machine learning model operations:
- Model initialization
- Prediction computation
- Coefficient management

### `PredictionInput.java`
Data transfer object (DTO) for incoming prediction requests.

### `PredictionResult.java`
Data transfer object (DTO) for prediction responses.

### `application.properties`
Spring Boot configuration file with server settings, logging configuration, and application properties.

## 🔧 Features Calculated for Prediction

The backend calculates the following 9 features for each prediction:

1. **months_since_start** - Time since May 2018 (base date)
2. **county_encoded** - Numeric encoding of the location
3. **ev_total_lag1** - EV count from 1 month ago
4. **ev_total_lag2** - EV count from 2 months ago
5. **ev_total_lag3** - EV count from 3 months ago
6. **ev_total_roll_mean_3** - 3-month rolling average
7. **ev_total_pct_change_1** - Percentage change over 1 month
8. **ev_total_pct_change_3** - Percentage change over 3 months
9. **ev_growth_slope** - Growth trend (linear regression slope)

## 🏗️ Architecture

```
Frontend (HTML/CSS/JS)
        ↓
REST API Endpoints
        ↓
PredictionApiController
        ↓
PredictionService (Feature Calculation)
        ↓
ModelService (ML Model)
LocationService (Location Encoding)
        ↓
Prediction Output
```

## 📊 Technologies Used

- **Spring Boot 2.7.14** - Web framework
- **Java 11** - Programming language
- **Maven** - Build tool
- **Apache Commons Math3** - Mathematical computations
- **Apache Commons CSV** - CSV parsing
- **Lombok** - Boilerplate reduction
- **Jackson** - JSON processing

## 🔒 Security Considerations

- Input validation on all endpoints
- Error handling to prevent information leakage
- Type-safe feature calculations
- CSV file access is secured by file system permissions

## 🐛 Troubleshooting

### Build errors
```bash
# Clean build
mvn clean build

# Skip tests if needed
mvn clean install -DskipTests
```

### Runtime errors
1. Check `application.properties` for correct configuration
2. Verify Java 11+ is installed: `java -version`
3. Check logs in target directory for detailed error messages

### CSV file not found
Ensure `Electric_Vehicle_Population_Size_History_By_County_.csv` is in the project root directory.

## 📞 Support

For issues or questions, please check the main project README or contact the development team.

## 📜 License

This project is licensed under the MIT License - see the main project README for details.
