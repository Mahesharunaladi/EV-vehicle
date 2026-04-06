/**
 * Main Application Logic for EV Demand Prediction Frontend
 */

class EVPredictionApp {
    constructor() {
        // Form elements
        this.form = document.getElementById('predictionForm');
        this.locationSelect = document.getElementById('location');
        this.currentDateInput = document.getElementById('currentDate');
        this.currentEvTotalInput = document.getElementById('currentEvTotal');
        this.evTotalLag1Input = document.getElementById('evTotalLag1');
        this.evTotalLag2Input = document.getElementById('evTotalLag2');
        this.evTotalLag3Input = document.getElementById('evTotalLag3');

        // Result elements
        this.resultsContainer = document.getElementById('resultsContainer');
        this.featuresContainer = document.getElementById('featuresContainer');
        this.analysisContainer = document.getElementById('analysisContainer');
        this.loadingIndicator = document.getElementById('loadingIndicator');
        this.errorMessage = document.getElementById('errorMessage');
        this.apiStatus = document.getElementById('apiStatus');

        this.init();
    }

    /**
     * Initialize the application
     */
    async init() {
        this.setDefaultDate();
        await this.checkApiStatus();
        await this.loadLocations();
        this.attachEventListeners();
    }

    /**
     * Check backend API status
     */
    async checkApiStatus() {
        try {
            const response = await fetch('http://localhost:8080/api/health');
            if (response.ok) {
                this.apiStatus.textContent = '🟢 Online';
                this.apiStatus.classList.add('online');
            } else {
                this.apiStatus.textContent = '🔴 Error';
                this.apiStatus.classList.add('offline');
            }
        } catch (error) {
            this.apiStatus.textContent = '🔴 Offline';
            this.apiStatus.classList.add('offline');
        }
    }

    /**
     * Set default date to today
     */
    setDefaultDate() {
        const today = new Date();
        const year = today.getFullYear();
        const month = String(today.getMonth() + 1).padStart(2, '0');
        const day = String(today.getDate()).padStart(2, '0');
        this.currentDateInput.value = `${year}-${month}-${day}`;
    }

    /**
     * Load locations from API
     */
    async loadLocations() {
        try {
            const locations = await ApiService.getLocations();
            this.populateLocationSelect(locations);
        } catch (error) {
            console.error('Failed to load locations:', error);
            this.showError('Failed to load locations. Please refresh the page.');
        }
    }

    /**
     * Populate the location select dropdown
     */
    populateLocationSelect(locations) {
        this.locationSelect.innerHTML = '<option value="">Select a location...</option>';
        locations.forEach(location => {
            const option = document.createElement('option');
            option.value = location;
            option.textContent = location;
            this.locationSelect.appendChild(option);
        });
    }

    /**
     * Attach event listeners
     */
    attachEventListeners() {
        this.form.addEventListener('submit', (e) => this.handleFormSubmit(e));
    }

    /**
     * Handle form submission
     */
    async handleFormSubmit(e) {
        e.preventDefault();

        if (!this.validateForm()) {
            return;
        }

        const predictionData = this.getFormData();
        await this.submitPrediction(predictionData);
    }

    /**
     * Validate form inputs
     */
    validateForm() {
        const location = this.locationSelect.value.trim();
        const currentDate = this.currentDateInput.value;
        const currentEvTotal = this.currentEvTotalInput.value;
        const evTotalLag1 = this.evTotalLag1Input.value;
        const evTotalLag2 = this.evTotalLag2Input.value;
        const evTotalLag3 = this.evTotalLag3Input.value;

        this.clearError();

        if (!location) {
            this.showError('Please select a location');
            return false;
        }

        if (!currentDate) {
            this.showError('Please select a date');
            return false;
        }

        if (!currentEvTotal || isNaN(currentEvTotal) || currentEvTotal < 0) {
            this.showError('Please enter a valid current EV total');
            return false;
        }

        if (!evTotalLag1 || isNaN(evTotalLag1) || evTotalLag1 < 0) {
            this.showError('Please enter a valid EV total for lag 1');
            return false;
        }

        if (!evTotalLag2 || isNaN(evTotalLag2) || evTotalLag2 < 0) {
            this.showError('Please enter a valid EV total for lag 2');
            return false;
        }

        if (!evTotalLag3 || isNaN(evTotalLag3) || evTotalLag3 < 0) {
            this.showError('Please enter a valid EV total for lag 3');
            return false;
        }

        return true;
    }

    /**
     * Get form data
     */
    getFormData() {
        return {
            county: this.locationSelect.value.trim(),
            currentDate: this.currentDateInput.value,
            currentEvTotal: parseInt(this.currentEvTotalInput.value),
            evTotalLag1: parseInt(this.evTotalLag1Input.value),
            evTotalLag2: parseInt(this.evTotalLag2Input.value),
            evTotalLag3: parseInt(this.evTotalLag3Input.value)
        };
    }

    /**
     * Submit prediction to API
     */
    async submitPrediction(data) {
        this.showLoading();
        this.clearError();

        try {
            const result = await ApiService.predict(data);
            this.displayResults(result, data);
        } catch (error) {
            console.error('Prediction failed:', error);
            this.showError(`Prediction failed: ${error.message}`);
        } finally {
            this.hideLoading();
        }
    }

    /**
     * Display prediction results
     */
    displayResults(result, data) {
        if (!result.success) {
            this.showError(result.error || 'Prediction failed');
            return;
        }

        const prediction = result.prediction;
        const features = result.features || {};

        // Clear previous results
        this.resultsContainer.innerHTML = '';

        // Create results card
        const resultHtml = `
            <div class="prediction-result">
                <div class="result-header">
                    <h3>📊 Prediction Result</h3>
                </div>
                <div class="prediction-value">
                    ${Math.round(prediction).toLocaleString()} EVs
                </div>
                <div class="prediction-metadata">
                    <div class="metadata-item">
                        <div class="metadata-label">Location</div>
                        <div class="metadata-value">${data.county}</div>
                    </div>
                    <div class="metadata-item">
                        <div class="metadata-label">Prediction Date</div>
                        <div class="metadata-value">${data.currentDate}</div>
                    </div>
                    <div class="metadata-item">
                        <div class="metadata-label">Current Total</div>
                        <div class="metadata-value">${data.currentEvTotal.toLocaleString()}</div>
                    </div>
                    <div class="metadata-item">
                        <div class="metadata-label">Growth</div>
                        <div class="metadata-value">${(((prediction - data.currentEvTotal) / data.currentEvTotal) * 100).toFixed(2)}%</div>
                    </div>
                </div>
            </div>
        `;

        this.resultsContainer.innerHTML = resultHtml;

        // Display feature breakdown
        if (Object.keys(features).length > 0) {
            this.displayFeatures(features);
        }

        // Display analysis
        this.displayAnalysis(prediction, data);
    }

    /**
     * Display feature breakdown
     */
    displayFeatures(features) {
        this.featuresContainer.classList.remove('hidden');
        const featuresGrid = document.getElementById('featuresGrid');
        featuresGrid.innerHTML = '';

        Object.entries(features).forEach(([name, value]) => {
            const featureHtml = `
                <div class="feature-item">
                    <div class="feature-name">${name}</div>
                    <div class="feature-value">${typeof value === 'number' ? value.toFixed(2) : value}</div>
                </div>
            `;
            featuresGrid.innerHTML += featureHtml;
        });
    }

    /**
     * Display market analysis
     */
    displayAnalysis(prediction, data) {
        this.analysisContainer.classList.remove('hidden');
        const analysisContent = document.getElementById('analysisContent');

        const growth = prediction - data.currentEvTotal;
        const growthPercent = ((growth / data.currentEvTotal) * 100).toFixed(2);
        const trend = growth > 0 ? '📈 Positive' : growth < 0 ? '📉 Negative' : '➡️ Neutral';

        let analysis = `
            <p><strong>Market Trend:</strong> ${trend}</p>
            <p><strong>Expected Growth:</strong> ${Math.round(growth).toLocaleString()} vehicles (${growthPercent}%)</p>
        `;

        if (growthPercent > 10) {
            analysis += `<p>The market shows strong growth potential in ${data.county}.</p>`;
        } else if (growthPercent > 0) {
            analysis += `<p>The market shows moderate growth in ${data.county}.</p>`;
        } else if (growthPercent === '0.00') {
            analysis += `<p>The market is expected to remain stable in ${data.county}.</p>`;
        } else {
            analysis += `<p>The market may experience a slight decline in ${data.county}.</p>`;
        }

        analysisContent.innerHTML = analysis;
    }

    /**
     * Show loading indicator
     */
    showLoading() {
        this.loadingIndicator.classList.add('show');
    }

    /**
     * Hide loading indicator
     */
    hideLoading() {
        this.loadingIndicator.classList.remove('show');
    }

    /**
     * Show error message
     */
    showError(message) {
        this.errorMessage.textContent = `❌ ${message}`;
        this.errorMessage.classList.remove('hidden');
    }

    /**
     * Clear error message
     */
    clearError() {
        this.errorMessage.textContent = '';
        this.errorMessage.classList.add('hidden');
    }
}

// Initialize the application when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    new EVPredictionApp();
});
