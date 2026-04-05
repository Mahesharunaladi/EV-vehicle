# Project Structure Visualization

## Complete Directory Tree

```
EV_Vehicle_Demand-prediction/
│
├── 📁 frontend/                        [NEW - Frontend Application]
│   ├── 📄 index.html                  Main UI template
│   ├── 📄 README.md                   Frontend documentation
│   │
│   └── 📁 src/
│       ├── 📁 css/
│       │   └── 📄 styles.css          All styling (responsive design)
│       │
│       ├── 📁 js/
│       │   ├── 📄 api.js              API service layer
│       │   │   - ApiService class
│       │   │   - getLocations()
│       │   │   - predict()
│       │   │   - getLocationEncoding()
│       │   │
│       │   └── 📄 app.js              Main application logic
│       │       - EVPredictionApp class
│       │       - Form handling
│       │       - Results display
│       │       - Validation
│       │
│       └── 📁 images/                 Image assets directory
│
├── 📁 backend/                         [NEW - Backend Service]
│   ├── 📄 pom.xml                     Maven configuration
│   ├── 📄 README.md                   Backend documentation
│   │
│   ├── 📁 src/
│   │   ├── 📁 main/
│   │   │   ├── 📁 java/com/ev/
│   │   │   │   ├── 📁 prediction/
│   │   │   │   │   └── 📄 EVDemandPredictionApplication.java
│   │   │   │   │       Main Spring Boot application
│   │   │   │   │
│   │   │   │   ├── 📁 api/
│   │   │   │   │   └── 📄 PredictionApiController.java
│   │   │   │   │       REST API endpoints
│   │   │   │   │       - GET /api/locations
│   │   │   │   │       - GET /api/locations/{location}/encoding
│   │   │   │   │       - POST /api/predict
│   │   │   │   │       - GET /api/health
│   │   │   │   │
│   │   │   │   ├── 📁 controller/     (For future MVC views)
│   │   │   │   │
│   │   │   │   ├── 📁 model/
│   │   │   │   │   ├── 📄 PredictionInput.java
│   │   │   │   │   │   Input data model (DTO)
│   │   │   │   │   │
│   │   │   │   │   └── 📄 PredictionResult.java
│   │   │   │   │       Output data model (DTO)
│   │   │   │   │
│   │   │   │   └── 📁 service/
│   │   │   │       ├── 📄 PredictionService.java
│   │   │   │       │   Feature calculation
│   │   │   │       │   ML prediction logic
│   │   │   │       │
│   │   │   │       ├── 📄 LocationService.java
│   │   │   │       │   Location management
│   │   │   │       │   550+ global locations
│   │   │   │       │   Location encoding
│   │   │   │       │
│   │   │   │       └── 📄 ModelService.java
│   │   │   │           ML model handling
│   │   │   │           Feature vector creation
│   │   │   │           Prediction computation
│   │   │   │
│   │   │   └── 📁 resources/
│   │   │       └── 📄 application.properties
│   │   │           Spring Boot config
│   │   │
│   │   └── 📁 test/                   (Test directory for JUnit)
│   │
│   └── 📄 pom.xml                     Copied Maven config
│
├── 📄 ARCHITECTURE.md                 [NEW] Complete project overview
├── 📄 FRONTEND_BACKEND_SEPARATION.md [NEW] Separation summary
├── 📄 PROJECT_STRUCTURE.md            [NEW] This file
│
└── [Original files remain...]
    ├── pom.xml (original in root - optional to keep)
    ├── src/ (original - can be deprecated)
    ├── README.md (original)
    └── Other files...

```

## Component Relationships

```
┌────────────────────────────────────────────────────────────────┐
│                          USER BROWSER                          │
│  Loads: frontend/index.html                                    │
│  Styles: frontend/src/css/styles.css                           │
│  Scripts: frontend/src/js/app.js + frontend/src/js/api.js     │
└────────────────────┬─────────────────────────────────────────────┘
                     │
                     │ HTTP Requests/Responses (JSON)
                     │
                     ↓
┌────────────────────────────────────────────────────────────────┐
│                    SPRING BOOT BACKEND                         │
│                  (localhost:8080)                              │
│                                                                │
│  ┌──────────────────────────────────────────────────────────┐ │
│  │  PredictionApiController                                │ │
│  │  - Routes HTTP requests                                │ │
│  │  - Validates input                                      │ │
│  │  - Returns JSON responses                               │ │
│  └──────────────────┬───────────────────────────────────────┘ │
│                     │                                         │
│  ┌────────────────┬─┴──────────────────┬─────────────────┐   │
│  │                │                    │                 │   │
│  ↓                ↓                    ↓                 ↓   │
│  ┌──────────────┐ ┌──────────────┐ ┌──────────────┐ ┌──────┐ │
│  │ Location     │ │ Prediction   │ │ Model        │ │ Data │ │
│  │ Service      │ │ Service      │ │ Service      │ │ Models
│  │              │ │              │ │              │ │      │ │
│  │ - Load CSV   │ │ - Calculate  │ │ - Predict    │ │ Input │ │
│  │ - Encode     │ │   features   │ │ - Handle     │ │Result │ │
│  │ - Map        │ │ - Validate   │ │   model      │ │      │ │
│  │   locations  │ │ - Call model │ │ - Coeffs    │ │      │ │
│  └──────────────┘ └──────────────┘ └──────────────┘ └──────┘ │
└────────────────────────────────────────────────────────────────┘
```

## Data Flow

```
1. USER ENTERS DATA
   ├─ Select Location
   ├─ Enter Current Date
   ├─ Enter Current EV Total
   └─ Enter Historical Data (Lag 1, 2, 3)

2. FRONTEND VALIDATION
   ├─ Check required fields
   ├─ Validate number ranges
   └─ Show loading state

3. HTTP REQUEST
   POST /api/predict
   Content-Type: application/json
   {
     "county": "King",
     "currentDate": "2024-01-15",
     "currentEvTotal": 150000,
     ...
   }

4. BACKEND PROCESSING
   ├─ Parse JSON input
   ├─ Calculate features:
   │  ├─ months_since_start
   │  ├─ county_encoded
   │  ├─ ev_total_lag1
   │  ├─ ev_total_lag2
   │  ├─ ev_total_lag3
   │  ├─ ev_total_roll_mean_3
   │  ├─ ev_total_pct_change_1
   │  ├─ ev_total_pct_change_3
   │  └─ ev_growth_slope
   │
   ├─ Create feature vector
   ├─ Load ML model
   └─ Make prediction

5. HTTP RESPONSE
   {
     "prediction": 155000.50,
     "features": { ... },
     "success": true,
     "error": null
   }

6. FRONTEND DISPLAY
   ├─ Hide loading state
   ├─ Display prediction value
   ├─ Show trend (↑ or ↓)
   ├─ Display feature breakdown
   └─ Show change percentage
```

## File Purposes Quick Reference

### Frontend Files

| File | Lines | Purpose |
|------|-------|---------|
| `index.html` | ~200 | UI structure and form layout |
| `styles.css` | ~300 | Complete responsive styling |
| `api.js` | ~50 | API client service |
| `app.js` | ~250 | Application logic and event handling |

### Backend Files

| File | Purpose |
|------|---------|
| `EVDemandPredictionApplication.java` | App entry point |
| `PredictionApiController.java` | HTTP endpoint routing |
| `PredictionService.java` | Feature calculation & prediction |
| `LocationService.java` | Location data management |
| `ModelService.java` | ML model operations |
| `PredictionInput.java` | Input DTO |
| `PredictionResult.java` | Output DTO |
| `application.properties` | Spring Boot configuration |
| `pom.xml` | Maven dependencies |

## Technology Stack

```
FRONTEND                          BACKEND
─────────────────────────────────────────────
HTML5                             Java 11
CSS3                              Spring Boot 2.7.14
Vanilla JavaScript (ES6+)         Maven 3.6+
Fetch API                         Apache Commons Math3
                                  Apache Commons CSV
                                  Lombok
                                  Jackson
```

## Deployment Scenario

```
Production Environment:

┌────────────────────────┐         ┌────────────────────────┐
│  Frontend CDN/Hosting  │         │  Backend (Docker/VM)   │
│                        │         │                        │
│  - index.html ──────────────────→ /api/predict           │
│  - styles.css                    - Spring Boot App       │
│  - app.js                        - Java 11               │
│  - api.js                        - Port 8080             │
│                        │         │                        │
│  Domain:               │         │ API Domain:            │
│  example.com           │         │ api.example.com        │
└────────────────────────┘         └────────────────────────┘
```

## Version Control Structure

Recommended `.gitignore` entries for this structure:

```
# Backend
backend/target/
backend/.classpath
backend/.project
backend/.settings/
backend/dependency-reduced-pom.xml
*.jar

# Frontend
frontend/node_modules/
frontend/.vscode/
frontend/.DS_Store

# IDE
.idea/
*.swp
*.swo
*~

# OS
.DS_Store
Thumbs.db
```

## Continuous Integration Setup

```yaml
# Example CI/CD Pipeline
stages:
  - build-backend
  - build-frontend
  - test-backend
  - test-frontend
  - deploy

build-backend:
  script:
    - cd backend
    - mvn clean install

build-frontend:
  script:
    - cd frontend
    - npm install  (if using npm)

test-backend:
  script:
    - cd backend
    - mvn test

deploy-backend:
  script:
    - docker build -f backend/Dockerfile -t ev-backend .
    - docker push ev-backend

deploy-frontend:
  script:
    - deploy frontend/ to CDN
```

---

This structure provides a clean, professional, and scalable organization for your EV Demand Prediction application!
