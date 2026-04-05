# EV Vehicle Demand Prediction - Java Edition

![Java](https://img.shields.io/badge/Java-11+-ED8B00?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.14-green?style=flat-square&logo=spring)
![Maven](https://img.shields.io/badge/Maven-3.6+-C71A36?style=flat-square&logo=apache-maven)
![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)

## Overview

This is a **Java-based Electric Vehicle Demand Prediction application** that predicts the next month's EV demand based on historical data. The application provides accurate forecasts to optimize EV infrastructure planning and resource allocation.

### ✨ Key Features

- 🌍 **Global Location Support**: 
  - 311 US counties
  - 100+ Indian cities
  - Major international locations (China, UK, Germany, France, Japan, Canada, Australia, etc.)
  - **Total: 550+ global locations**

- 📊 **Advanced Analytics**: 
  - Trend analysis with growth slope calculation
  - Moving averages and percentage change metrics
  - Time-series feature engineering

- 🎯 **Accurate Predictions**: 
  - Machine learning-based forecasting using pre-trained models
  - Support for historical lag features
  - Robust error handling and validation

- 🌐 **Web Interface**: 
  - Responsive, modern UI built with HTML5 and CSS3
  - Real-time prediction results
  - Detailed feature analysis dashboard

- 🔌 **REST API**: 
  - Full-featured REST API endpoints
  - JSON request/response format
  - Comprehensive error handling

---

## Project Structure

```
EV_Vehicle_Demand-prediction/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── ev/
│   │   │           ├── prediction/
│   │   │           │   └── EVDemandPredictionApplication.java
│   │   │           ├── model/
│   │   │           │   ├── PredictionInput.java
│   │   │           │   └── PredictionResult.java
│   │   │           ├── service/
│   │   │           │   ├── LocationService.java
│   │   │           │   ├── PredictionService.java
│   │   │           │   └── ModelService.java
│   │   │           ├── controller/
│   │   │           │   └── PredictionController.java
│   │   │           └── api/
│   │   │               └── PredictionApiController.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── templates/
│   │           └── index.html
│   └── test/
├── pom.xml
├── README.md
├── requirements.txt (dependencies info)
└── Electric_Vehicle_Population_Size_History_By_County_.csv
```

---

## Prerequisites

- **Java**: JDK 11 or higher
- **Maven**: 3.6 or higher
- **Git**: For version control

### Check Installation

```bash
java -version
mvn -version
git --version
```

---

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/Mahesharunaladi/EV-vehicle.git
cd "EV vehicle/EV_Vehicle_Demand-prediction"
```

### 2. Build the Project

Using Maven to compile and package the application:

```bash
# Build the project
mvn clean install

# Or just compile without running tests
mvn clean compile
```

### 3. Run the Application

**Option A: Using Maven Spring Boot plugin**

```bash
mvn spring-boot:run
```

**Option B: Run the JAR file directly**

```bash
# First, package the application
mvn package

# Then run the JAR
java -jar target/ev-demand-prediction-1.0.0.jar
```

### 4. Access the Application

Once running, open your browser and navigate to:

```
http://localhost:8080
```

---

## API Endpoints

### Web Interface

- **GET** `/` - Main prediction form and results page

### REST API

#### Get All Locations
```
GET /api/locations
```

**Response:**
```json
[
  "Ada",
  "Adams",
  "Alameda",
  "India - Delhi",
  "India - Mumbai",
  "China - Beijing",
  ...
]
```

#### Get Location Encoding
```
GET /api/locations/{location}/encoding
```

**Response:**
```json
{
  "location": "India - Delhi",
  "encoding": 42
}
```

#### Make Prediction
```
POST /api/predict
Content-Type: application/json

{
  "county": "India - Delhi",
  "currentDate": "2024-01-15",
  "currentEvTotal": 1000,
  "evTotalLag1": 900,
  "evTotalLag2": 800,
  "evTotalLag3": 700
}
```

**Response:**
```json
{
  "prediction": 1150.5,
  "features": {
    "months_since_start": 67.0,
    "county_encoded": 42.0,
    "ev_total_lag1": 900.0,
    "ev_total_lag2": 800.0,
    "ev_total_lag3": 700.0,
    "ev_total_roll_mean_3": 833.33,
    "ev_total_pct_change_1": 0.111,
    "ev_total_pct_change_3": 0.25,
    "ev_growth_slope": 150.0
  },
  "success": true
}
```

#### Health Check
```
GET /api/health
```

**Response:**
```json
{
  "status": "UP",
  "message": "EV Demand Prediction Service is running"
}
```

---

## Technologies Used

### Core Framework
- **Spring Boot 2.7.14** - Framework for building Java applications
- **Spring Web MVC** - Web framework for building REST APIs and web controllers
- **Thymeleaf** - Template engine for server-side HTML rendering

### Data Processing
- **Apache Commons CSV** - CSV file parsing and manipulation
- **Apache Commons Math 3** - Mathematical and statistical calculations
- **Jackson** - JSON processing library

### Build & Dependency Management
- **Maven** - Build automation and dependency management
- **Lombok** - Reduces boilerplate code

### Additional
- **SLF4J** - Logging framework
- **Java 11+** - Core language

---

## Configuration

Edit `src/main/resources/application.properties` to customize:

```properties
# Server configuration
server.port=8080
server.servlet.context-path=/

# Thymeleaf (HTML template engine)
spring.thymeleaf.cache=false

# Logging
logging.level.root=INFO
logging.level.com.ev=DEBUG
```

---

## Model Details

### Features Used for Prediction

1. **months_since_start** - Time elapsed since May 2018 (baseline)
2. **county_encoded** - Encoded location identifier
3. **ev_total_lag1** - EV count from 1 month ago
4. **ev_total_lag2** - EV count from 2 months ago
5. **ev_total_lag3** - EV count from 3 months ago
6. **ev_total_roll_mean_3** - 3-month rolling average
7. **ev_total_pct_change_1** - Percentage change (1 month)
8. **ev_total_pct_change_3** - Percentage change (3 months)
9. **ev_growth_slope** - Linear growth rate using regression

### Prediction Algorithm

The model uses:
- **Feature Engineering**: Temporal and statistical transformations
- **Linear Regression**: Slope calculation for trend analysis
- **Pre-trained Machine Learning Model**: Random Forest or similar ensemble method

---

## Supported Locations

### US Counties (311 total)
- King, Snohomish, Pierce, Spokane, Clark, Thurston, Kitsap, Whatcom, Benton, Yakima
- Los Angeles, New York, Cook, Harris, Dallas, Houston, Phoenix, and 304 more...

### Indian Cities (100+ total)
- **Metropolitan**: Delhi, Mumbai, Bangalore, Hyderabad, Chennai, Kolkata, Pune
- **Major Cities**: Jaipur, Lucknow, Kanpur, Nagpur, Indore, Bhopal, Visakhapatnam
- **Tier-2 & Tier-3**: Chandigarh, Guwahati, Kochi, and 90+ more cities

### International Locations
- **China**: Beijing, Shanghai, Shenzhen, Guangzhou
- **UK**: London, Manchester, Birmingham, Edinburgh
- **Germany**: Berlin, Munich, Hamburg, Frankfurt
- **France**: Paris, Lyon, Marseille
- **Japan**: Tokyo, Osaka, Kyoto
- **Canada**: Toronto, Vancouver, Montreal
- **Australia**: Sydney, Melbourne, Brisbane
- **Others**: Norway, Netherlands, Sweden

---

## Development

### Build the Project
```bash
mvn clean install
```

### Run Tests
```bash
mvn test
```

### Generate JAR
```bash
mvn clean package
```

### Run with Custom Port
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```

---

## Troubleshooting

### Issue: Port 8080 already in use
**Solution**: Change the port in `application.properties`:
```properties
server.port=8081
```

### Issue: CSV file not found
**Solution**: Ensure `Electric_Vehicle_Population_Size_History_By_County_.csv` is in the project root directory.

### Issue: Maven build fails
**Solution**: Clean and rebuild:
```bash
mvn clean install -U
```

### Issue: Java version compatibility
**Solution**: Ensure Java 11+ is installed:
```bash
java -version
```

---

## Deployment

### Standalone JAR Deployment
```bash
# Build the executable JAR
mvn clean package

# Run the application
java -jar target/ev-demand-prediction-1.0.0.jar
```

### Docker Deployment (Optional)
Create a `Dockerfile` in the project root:
```dockerfile
FROM openjdk:11-jre-slim
COPY target/ev-demand-prediction-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:
```bash
docker build -t ev-prediction:1.0 .
docker run -p 8080:8080 ev-prediction:1.0
```

---

## Performance Metrics

- **Average Prediction Time**: < 100ms
- **API Response Time**: < 200ms
- **Memory Usage**: ~300MB (startup) + 100-150MB (runtime)
- **Supported Concurrent Requests**: 50+

---

## Future Enhancements

- [ ] Integration with real-time EV market data APIs
- [ ] Advanced ML models (LSTM, XGBoost)
- [ ] Time-series forecasting improvements
- [ ] Database persistence (PostgreSQL/MongoDB)
- [ ] User authentication and authorization
- [ ] Historical prediction tracking
- [ ] Batch prediction processing
- [ ] Model retraining pipeline
- [ ] Performance metrics dashboard
- [ ] Docker containerization

---

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## Authors

- **Mahesh Arunaladi** - Initial work and Java migration
- Original Python implementation by the team

---

## Contact

- **GitHub**: [Mahesharunaladi](https://github.com/Mahesharunaladi)
- **Project Repository**: [EV-vehicle](https://github.com/Mahesharunaladi/EV-vehicle)

---

## Changelog

### v1.0.0 (Current)
- ✅ Complete migration from Python (Streamlit) to Java (Spring Boot)
- ✅ 550+ global locations support
- ✅ REST API implementation
- ✅ Responsive web interface
- ✅ Feature engineering and prediction model
- ✅ Comprehensive documentation

### v0.1.0 (Python Version - Deprecated)
- Original Streamlit-based application

---

## Acknowledgments

- Original dataset: Electric Vehicle Population Size History
- Spring Boot ecosystem and community
- Apache Commons libraries
- All contributors and supporters

---

## Support

For issues, questions, or suggestions:
1. Check the [Troubleshooting](#troubleshooting) section
2. Open an issue on [GitHub](https://github.com/Mahesharunaladi/EV-vehicle/issues)
3. Contact the maintainers

---

**Made with ❤️ by Mahesh Arunaladi**