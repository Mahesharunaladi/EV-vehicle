# EV Demand Prediction - Frontend

This is the frontend application for the EV Demand Prediction system. It provides a user-friendly interface for predicting EV demand based on historical data.

## 📁 Directory Structure

```
frontend/
├── index.html              # Main HTML file
├── src/
│   ├── css/
│   │   └── styles.css      # All CSS styling
│   ├── js/
│   │   ├── api.js          # API communication service
│   │   └── app.js          # Main application logic
│   └── images/             # Image assets
└── README.md               # This file
```

## 🚀 Getting Started

### Prerequisites
- A modern web browser (Chrome, Firefox, Safari, Edge)
- The backend server running on `http://localhost:8080`

### Installation

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Open `index.html` in your browser or use a local server:
```bash
# Using Python 3
python3 -m http.server 8000

# Using Node.js (if http-server is installed)
http-server

# Using PHP
php -S localhost:8000
```

3. Access the application at `http://localhost:8000`

## 📝 Features

- **Location Selection**: Choose from 550+ global locations
- **Date Picker**: Select any date for prediction
- **Historical Data Input**: Enter EV counts for the last 3 months
- **Real-time Predictions**: Get instant forecasts
- **Detailed Analysis**: View feature breakdown and insights
- **Responsive Design**: Works on desktop and mobile devices

## 🔗 API Integration

The frontend communicates with the backend API via the following endpoints:

- `GET /api/locations` - Fetch all available locations
- `POST /api/predict` - Submit prediction request
- `GET /api/locations/{location}/encoding` - Get location encoding

## 📂 File Descriptions

### `index.html`
The main HTML template. Contains the structure for:
- Header with application title
- Input form for EV data
- Results display area
- Welcome message

### `src/css/styles.css`
Complete styling for the application:
- Layout and grid system
- Form styling
- Card and result display styles
- Responsive design for mobile devices
- Animation effects

### `src/js/api.js`
`ApiService` class for backend communication:
- `getLocations()` - Fetch all available locations
- `predict(data)` - Submit prediction request
- `getLocationEncoding(location)` - Get location-specific encoding
- Error handling and logging

### `src/js/app.js`
`EVPredictionApp` class containing main application logic:
- Form validation and submission
- Location loading and dropdown population
- Result display and formatting
- Loading states and error handling
- User interaction management

## 🎨 Customization

### Change API URL
If the backend is running on a different URL, update it in `src/js/api.js`:
```javascript
const API_BASE_URL = 'http://your-backend-url:port/api';
```

### Styling
Modify `src/css/styles.css` to customize colors, fonts, and layout.

## 🐛 Troubleshooting

### Frontend not connecting to backend
1. Ensure the backend server is running
2. Check the `API_BASE_URL` in `src/js/api.js`
3. Verify CORS is enabled on the backend
4. Check browser console for errors (F12)

### Form not submitting
1. Ensure all required fields are filled
2. Check browser console for validation errors
3. Verify backend API is responding

### Locations not loading
1. Check backend server status
2. Verify the `/api/locations` endpoint is working
3. Check browser network tab in developer tools

## 📱 Browser Support

- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+

## 🔒 Security Notes

- All data is sent to the backend via HTTPS in production
- Input validation is performed on both frontend and backend
- No sensitive data is stored in local storage

## 📞 Support

For issues or questions, please contact the development team or check the main project README.
