# EV Vehicle Demand Prediction - Full Stack Project

This is a complete full-stack application for predicting EV (Electric Vehicle) demand based on historical data. The project is organized into separate frontend and backend components for better maintainability and scalability.

## 📦 Project Structure

```
EV_Vehicle_Demand-prediction/
├── frontend/                          # Frontend application (HTML/CSS/JS)
│   ├── index.html                     # Main UI
│   ├── src/
│   │   ├── css/
│   │   │   └── styles.css             # All styling
│   │   ├── js/
│   │   │   ├── api.js                 # API communication
│   │   │   └── app.js                 # Application logic
│   │   └── images/                    # Image assets
│   └── README.md                      # Frontend documentation
│
├── backend/                           # Backend service (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/ev/
│   │   │   │   ├── prediction/        # Main application
│   │   │   │   ├── api/               # REST controllers
│   │   │   │   ├── controller/        # Web controllers
│   │   │   │   ├── model/             # Data models
│   │   │   │   └── service/           # Business logic
│   │   │   └── resources/             # Configuration
│   │   └── test/                      # Test files
│   ├── pom.xml                        # Maven configuration
│   └── README.md                      # Backend documentation
│
└── README.md                          # This file
```

## 🎯 Features

### Frontend
- Modern, responsive web interface
- Real-time prediction results
- Location selection from 550+ global locations
- Historical data input for accurate predictions
- Detailed feature analysis dashboard
- Mobile-friendly design

### Backend
- RESTful API with comprehensive endpoints
- Machine learning-based forecasting
- Feature engineering and calculation
- Support for multiple locations (US counties, Indian cities, international locations)
- Error handling and validation
- Health check endpoint

## 🚀 Quick Start

### Prerequisites
- **Frontend**: Modern web browser (Chrome, Firefox, Safari, Edge)
- **Backend**: Java 11+, Maven 3.6+

### Running the Backend

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

The backend will start at `http://localhost:8080`

### Running the Frontend

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Start a local server:
```bash
# Using Python 3
python3 -m http.server 8000

# Using Node.js (with http-server installed)
http-server

# Using PHP
php -S localhost:8000
```

3. Open `http://localhost:8000` in your browser

## 📡 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Endpoints

#### Get All Locations
```
GET /locations
Response: List of all available locations
```

#### Get Location Encoding
```
GET /locations/{location}/encoding
Response: Numeric encoding for the location
```

#### Make Prediction
```
POST /predict
Body: PredictionInput (JSON)
Response: PredictionResult (JSON)
```

#### Health Check
```
GET /health
Response: Service status
```

## 💡 Usage Example

### Request
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

### Response
```json
{
  "prediction": 155000.50,
  "features": {
    "months_since_start": 67,
    "county_encoded": 42,
    "ev_total_lag1": 145000,
    ...
  },
  "success": true,
  "error": null
}
```

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    FRONTEND (Browser)                       │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  index.html  │  styles.css  │  api.js  │  app.js   │   │
│  └─────────────────────────────────────────────────────┘   │
└────────────────────────┬────────────────────────────────────┘
                         │ HTTP/JSON
                         ↓
┌─────────────────────────────────────────────────────────────┐
│                  BACKEND (Spring Boot)                      │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  REST Controllers  │  Services  │  Models           │   │
│  ├─────────────────────────────────────────────────────┤   │
│  │  PredictionService │ LocationService │ ModelService │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

## 📊 Prediction Features

The system calculates 9 features for each prediction:

1. **Months Since Start** - Time elapsed since May 2018
2. **County Encoded** - Location identifier
3. **EV Total Lag 1** - EV count from 1 month ago
4. **EV Total Lag 2** - EV count from 2 months ago
5. **EV Total Lag 3** - EV count from 3 months ago
6. **Rolling Mean (3-month)** - Average of last 3 months
7. **Percentage Change (1-month)** - Monthly growth rate
8. **Percentage Change (3-month)** - 3-month growth rate
9. **Growth Slope** - Trend direction using linear regression

## 🌍 Supported Locations

### Geographic Coverage
- **311 US Counties** - All major counties with significant EV adoption
- **100+ Indian Cities** - Including Delhi, Mumbai, Bangalore, and more
- **International Locations** - China, UK, Germany, France, Japan, Canada, Australia, Norway, Netherlands, Sweden, and more
- **Total: 550+ Global Locations**

## 🔧 Technology Stack

### Frontend
- HTML5
- CSS3
- Vanilla JavaScript (ES6+)
- Fetch API for HTTP requests

### Backend
- Java 11
- Spring Boot 2.7.14
- Maven
- Apache Commons (Math3, CSV)
- Lombok
- Jackson (JSON processing)

## 📝 Development

### Frontend Development

To add new features to the frontend:

1. Edit `frontend/index.html` for structure changes
2. Update `frontend/src/css/styles.css` for styling
3. Modify `frontend/src/js/app.js` for application logic
4. Update `frontend/src/js/api.js` for API integration

### Backend Development

To extend the backend:

1. Add new services in `backend/src/main/java/com/ev/service/`
2. Create new models in `backend/src/main/java/com/ev/model/`
3. Add API endpoints in `backend/src/main/java/com/ev/api/`
4. Update `backend/pom.xml` for new dependencies

## 🐛 Troubleshooting

### Frontend Issues
- **Not connecting to backend**: Check if backend is running on port 8080
- **CORS errors**: Ensure backend allows frontend origin
- **Locations not loading**: Verify `/api/locations` endpoint is accessible

### Backend Issues
- **Build fails**: Run `mvn clean install -DskipTests`
- **Port already in use**: Change port in `application.properties`
- **CSV not found**: Ensure CSV file is in project root

## 🔒 Security

- Input validation on all endpoints
- Type-safe Java implementations
- Error handling prevents information leakage
- No sensitive data stored in frontend
- All data communication via HTTPS in production

## 📞 Support

For detailed documentation:
- See `frontend/README.md` for frontend-specific information
- See `backend/README.md` for backend-specific information

## 📜 License

This project is licensed under the MIT License.

## 👨‍💻 Contributing

1. Follow the existing code structure
2. Add tests for new features
3. Update documentation for changes
4. Maintain separation between frontend and backend code

## 🎓 Learning Resources

- Spring Boot Documentation: https://spring.io/projects/spring-boot
- JavaScript Fetch API: https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API
- REST API Best Practices: https://restfulapi.net/
- Maven Guide: https://maven.apache.org/guides/

---

**Last Updated**: April 5, 2026

For questions or issues, please refer to the specific component documentation or contact the development team.
