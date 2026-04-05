/**
 * API Service for communicating with the EV Demand Prediction backend
 */

const API_BASE_URL = 'http://localhost:8080/api';

class ApiService {
    /**
     * Fetch all available locations
     */
    static async getLocations() {
        try {
            const response = await fetch(`${API_BASE_URL}/locations`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('Error fetching locations:', error);
            throw error;
        }
    }

    /**
     * Make a prediction request
     */
    static async predict(predictionData) {
        try {
            const response = await fetch(`${API_BASE_URL}/predict`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(predictionData)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error making prediction:', error);
            throw error;
        }
    }

    /**
     * Get location encoding
     */
    static async getLocationEncoding(location) {
        try {
            const response = await fetch(`${API_BASE_URL}/locations/${encodeURIComponent(location)}/encoding`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('Error fetching location encoding:', error);
            throw error;
        }
    }

    /**
     * Set API base URL
     */
    static setBaseURL(url) {
        API_BASE_URL = url;
    }
}
