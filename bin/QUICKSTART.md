# Quick Start Guide - Java Edition

## 🚀 Get Started in 5 Minutes

### Step 1: Prerequisites Check
```bash
# Verify Java is installed (11+)
java -version

# Verify Maven is installed (3.6+)
mvn -version
```

### Step 2: Navigate to Project
```bash
cd "/Users/mahesharunaladi/Documents/EV vehicle/EV_Vehicle_Demand-prediction"
```

### Step 3: Build the Project
```bash
# Option A: Using Maven (Recommended)
mvn clean install

# Option B: Compile only
mvn clean compile
```

### Step 4: Run the Application
```bash
# Option A: Using Spring Boot Maven plugin
mvn spring-boot:run

# Option B: Build JAR and run
mvn clean package
java -jar target/ev-demand-prediction-1.0.0.jar
```

### Step 5: Access the Application
```
Open your browser and go to:
http://localhost:8080
```

---

## 📊 Common Tasks

### Test API Endpoint
```bash
curl http://localhost:8080/api/health
```

### Get All Locations
```bash
curl http://localhost:8080/api/locations | head -20
```

### Make a Prediction (cURL)
```bash
curl -X POST http://localhost:8080/api/predict \
  -H "Content-Type: application/json" \
  -d '{
    "county": "India - Delhi",
    "currentDate": "2024-04-15",
    "currentEvTotal": 5000,
    "evTotalLag1": 4500,
    "evTotalLag2": 4000,
    "evTotalLag3": 3500
  }'
```

### View Logs
```bash
# While running with mvn spring-boot:run
# Logs are displayed in console

# Or check application.properties for custom logging levels
```

### Change Port
Edit `src/main/resources/application.properties`:
```properties
server.port=9090
```

---

## 🛠️ Troubleshooting

### Maven build fails
```bash
# Clear cache and retry
mvn clean install -U

# Or try offline
rm -rf ~/.m2/repository
mvn clean install
```

### Port 8080 already in use
```bash
# Option 1: Find and kill process
lsof -i :8080
kill -9 <PID>

# Option 2: Change port in application.properties
# Set server.port=8081
```

### Java version issue
```bash
# Install Java 11+
# Then verify
java -version  # Should show 11 or higher

# Set JAVA_HOME if needed
export JAVA_HOME=$(/usr/libexec/java_home -v 11)
```

---

## 📝 Important Notes

- First build will take longer (downloading dependencies)
- Subsequent builds are faster
- JAR file is created in `target/` directory
- Application runs on http://localhost:8080 (configurable)

---

## 🎯 Next Steps

1. Explore the web interface
2. Test predictions with different locations
3. Try the REST API endpoints
4. Check the README.md for detailed documentation
5. Review MIGRATION_GUIDE.md for Python → Java changes

---

**Need Help?** See README.md Troubleshooting section
