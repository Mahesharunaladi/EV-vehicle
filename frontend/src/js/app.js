/**
 * Main Application Logic for EV Demand Prediction Frontend
 */

class EVPredictionApp {
    constructor() {
        this.form = document.getElementById('predictionForm');
        this.countySelect = document.getElementById('county');
        this.resultsContainer = document.getElementById('resultsContainer');
        this.welcomeCard = document.getElementById('welcomeCard');
        this.currentDate = document.getElementById('currentDate');

        this.init();
    }

    /**
     * Initialize the application
     */
    async init() {
        // Set today's date as default
        this.setDefaultDate();

        // Load locations
        await this.loadLocations();

        // Attach event listeners
        this.attachEventListeners();
    }

    /**
     * Set default date to today
     */
    setDefaultDate() {
        const today = new Date();
        const year = today.getFullYear();
        const month = String(today.getMonth() + 1).padStart(2, '0');
        const day = String(today.getDate()).padStart(2, '0');
        this.currentDate.value = `${year}-${month}-${day}`;
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
            this.showError('Failed to load locations. Please try again later.');
        }
    }

    /**
     * Populate the location select dropdown
     */
    populateLocationSelect(locations) {
        this.countySelect.innerHTML = '<option value="">-- Select a location --</option>';
        locations.forEach(location => {
            const option = document.createElement('option');
            option.value = location;
            option.textContent = location;
            this.countySelect.appendChild(option);
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
        const county = this.countySelect.value.trim();
        const currentDate = this.currentDate.value;
        const currentEvTotal = document.getElementById('currentEvTotal').value;

        if (!county) {
            this.showError('Please select a location');
            return false;
        }

        if (!currentDate) {
            this.showError('Please select a date');
            return false;
        }

        if (!currentEvTotal || parseInt(currentEvTotal) < 0) {
            this.showError('Please enter a valid current EV count');
            return false;
        }

        return true;
    }

    /**
     * Get form data
     */
    getFormData() {
        return {
            county: document.getElementById('county').value,
            currentDate: document.getElementById('currentDate').value,
            currentEvTotal: parseInt(document.getElementById('currentEvTotal').value),
            evTotalLag1: parseInt(document.getElementById('evTotalLag1').value) || 0,
            evTotalLag2: parseInt(document.getElementById('evTotalLag2').value) || 0,
            evTotalLag3: parseInt(document.getElementById('evTotalLag3').value) || 0,
        };
    }

    /**
     * Submit prediction request
     */
    async submitPrediction(data) {
        try {
            // Show loading state
            this.setFormLoading(true);

            // Make API call
            const result = await ApiService.predict(data);

            // Hide welcome card
            this.welcomeCard.style.display = 'none';

            // Display results
            this.displayResults(result, data);
        } catch (error) {
            console.error('Prediction failed:', error);
            this.showError('Prediction failed. Please check your input and try again.');
        } finally {
            // Hide loading state
            this.setFormLoading(false);
        }
    }

    /**
     * Display prediction results
     */
    displayResults(result, inputData) {
        this.resultsContainer.innerHTML = '';
        this.resultsContainer.style.display = 'block';

        if (result.success) {
            // Show result card
            const resultCard = this.createResultCard(result, inputData);
            this.resultsContainer.appendChild(resultCard);

            // Show features analysis
            const featuresCard = this.createFeaturesCard(result);
            this.resultsContainer.appendChild(featuresCard);

            this.resultsContainer.classList.remove('full-width');
        } else {
            // Show error
            this.resultsContainer.innerHTML = `
                <div class="card">
                    <div class="error-message">
                        <strong>Error:</strong> ${result.error || 'An error occurred during prediction'}
                    </div>
                </div>
            `;
            this.resultsContainer.classList.add('full-width');
        }
    }

    /**
     * Create result card HTML
     */
    createResultCard(result, inputData) {
        const card = document.createElement('div');
        card.className = 'card result-card';

        const prediction = Math.round(result.prediction);
        const current = inputData.currentEvTotal;
        const change = prediction - current;
        const isPositive = change >= 0;

        card.innerHTML = `
            <h2>🚀 Prediction Result</h2>
            <h3>Predicted EV Total for Next Month</h3>
            <div class="prediction-value">${this.formatNumber(prediction)}</div>
            <div class="prediction-change ${isPositive ? 'positive' : 'negative'}">
                ${isPositive ? '📈 ↑' : '📉 ↓'}
                <span>${isPositive ? '+' : '-'}${this.formatNumber(Math.abs(change))}</span>
                <span>from current</span>
            </div>
        `;

        return card;
    }

    /**
     * Create features analysis card HTML
     */
    createFeaturesCard(result) {
        const card = document.createElement('div');
        card.className = 'card';

        let featuresHTML = '<h2>📊 Detailed Analysis</h2><div class="features-grid">';

        if (result.features && typeof result.features === 'object') {
            for (const [key, value] of Object.entries(result.features)) {
                const formattedKey = key.replace(/_/g, ' ').toUpperCase();
                const formattedValue = typeof value === 'number' ? value.toFixed(2) : value;

                featuresHTML += `
                    <div class="feature-card">
                        <div class="feature-card-title">${formattedKey}</div>
                        <div class="feature-card-value">${formattedValue}</div>
                    </div>
                `;
            }
        }

        featuresHTML += '</div>';
        card.innerHTML = featuresHTML;

        return card;
    }

    /**
     * Show error message
     */
    showError(message) {
        this.welcomeCard.style.display = 'block';
        this.resultsContainer.style.display = 'none';

        const errorDiv = document.createElement('div');
        errorDiv.className = 'error-message';
        errorDiv.innerHTML = `<strong>Error:</strong> ${message}`;

        const formCard = document.querySelector('.card');
        formCard.insertBefore(errorDiv, formCard.firstChild);

        // Remove error after 5 seconds
        setTimeout(() => {
            errorDiv.remove();
        }, 5000);
    }

    /**
     * Set form loading state
     */
    setFormLoading(isLoading) {
        const button = this.form.querySelector('button[type="submit"]');
        button.disabled = isLoading;

        if (isLoading) {
            button.innerHTML = '<span class="loading"></span> Predicting...';
        } else {
            button.innerHTML = '🔮 Predict Next Month\'s EV Demand';
        }
    }

    /**
     * Format number for display
     */
    formatNumber(num) {
        return num.toLocaleString('en-US');
    }
}

// Initialize app when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    window.app = new EVPredictionApp();
});
