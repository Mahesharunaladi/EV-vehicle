import streamlit as st
import pandas as pd
import numpy as np
from joblib import load
from datetime import datetime
import base64
from PIL import Image

st.set_page_config(
    page_title="EV Demand Prediction",
    page_icon="⚡",
    layout="wide",
    initial_sidebar_state="expanded"
)

def local_css(file_name):
    with open(file_name) as f:
        st.markdown(f'<style>{f.read()}</style>', unsafe_allow_html=True)


st.markdown("""
    <style>
    /* Base styles */
    body {
        color: #333333 !important;
    }
    
    /* Override Streamlit's default text color */
    .stApp {
        color: #333333;
        background: linear-gradient(135deg, #f5f7fa 0%, #e4e8f0 100%);
    }
    
    /* Ensure all text is visible */
    .stMarkdown, .stMarkdown p, .stMarkdown li {
        color: #1f2937 !important;
    }
    
    /* Header styles */
    .stMarkdown h1, .stMarkdown h2, .stMarkdown h3, 
    .stMarkdown h4, .stMarkdown h5, .stMarkdown h6 {
        color: #1f2937 !important;
    }
    
    /* Form labels */
    label, .stTextInput > label, .stNumberInput > label, 
    .stSelectbox > label, .stDateInput > label, .stSlider > label {
        color: #1f2937 !important;
        font-weight: 600 !important;
        margin-bottom: 0.25rem !important;
        display: block !important;
    }
    
    /* Ensure form controls have good contrast */
    .stTextInput>div>div>input, 
    .stNumberInput>div>div>input,
    .stSelectbox>div>div>select,
    .stDateInput>div>input {
        background-color: white !important;
        color: #1f2937 !important;
        border: 1px solid #d1d5db !important;
        border-radius: 8px !important;
        padding: 0.5rem 0.75rem !important;
    }
    
    /* Card styles */
    .prediction-card {
        background: white;
        border-radius: 15px;
        padding: 2rem;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        margin: 1rem 0;
        color: #333333 !important;
    }
    
    .feature-card {
        background: white;
        border-radius: 10px;
        padding: 1.2rem;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        margin: 0.5rem 0;
        color: #333333 !important;
    }
    
    /* Input fields */
    .stTextInput>div>div>input, 
    .stNumberInput>div>div>input,
    .stSelectbox>div>div>select,
    .stDateInput>div>input {
        background-color: white !important;
        color: #333333 !important;
        border: 1px solid #dee2e6 !important;
        border-radius: 8px !important;
    }
    
    /* Button styles */
    .stButton>button {
        background: linear-gradient(45deg, #00b4d8, #0077b6) !important;
        color: white !important;
        border: none !important;
        padding: 0.8rem 2rem !important;
        border-radius: 30px !important;
        font-weight: 600 !important;
        transition: all 0.3s ease !important;
    }
    
    .stButton>button:hover {
        transform: translateY(-2px) !important;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15) !important;
    }
    
    /* Headers */
    h1, h2, h3, h4, h5, h6 {
        color: #1e3a8a !important;
    }
    
    /* Expandable sections */
    .streamlit-expanderHeader {
        font-size: 1.1rem !important;
        font-weight: 600 !important;
        color: #1e40af !important;
    }
    
    /* Ensure text in expanders is visible */
    .streamlit-expanderContent {
        color: #333333 !important;
    }
    </style>
""", unsafe_allow_html=True)
@st.cache_resource
def load_model():
    MODEL_PATH = 'forecasting_ev_model.pkl'
    return load(MODEL_PATH)

model = load_model()

COUNTY_ENCODING = {
    'King': 0,
    'Snohomish': 1,
    'Pierce': 2,
    'Spokane': 3,
    'Clark': 4,
    'Thurston': 5,
    'Kitsap': 6,
    'Whatcom': 7,
    'Benton': 8,
    'Yakima': 9
}

FEATURE_NAMES = [
    'months_since_start',
    'county_encoded',
    'ev_total_lag1',
    'ev_total_lag2',
    'ev_total_lag3',
    'ev_total_roll_mean_3',
    'ev_total_pct_change_1',
    'ev_total_pct_change_3',
    'ev_growth_slope'
]

def calculate_growth_slope(ev_totals):
    if len(ev_totals) < 2:
        return 0.0
    x = np.arange(len(ev_totals))
    slope = np.polyfit(x, ev_totals, 1)[0]
    return slope

def predict_ev_demand(input_data):
    try:
        county = input_data['county']
        current_ev_total = float(input_data['current_ev_total'])
        ev_total_lag1 = float(input_data.get('ev_total_lag1', current_ev_total))
        ev_total_lag2 = float(input_data.get('ev_total_lag2', ev_total_lag1))
        current_date = input_data.get('current_date', datetime.now().strftime('%Y-%m-%d'))
        
        date_obj = datetime.strptime(current_date, '%Y-%m-%d')
        months_since_start = (date_obj.year - 2018) * 12 + date_obj.month - 5
        
        recent_ev_totals = [ev_total_lag2, ev_total_lag1, current_ev_total]
        ev_total_roll_mean_3 = np.mean(recent_ev_totals)
        
        if ev_total_lag1 > 0:
            ev_total_pct_change_1 = (current_ev_total - ev_total_lag1) / ev_total_lag1
            ev_total_pct_change_3 = (current_ev_total - ev_total_lag2) / ev_total_lag2 if ev_total_lag2 > 0 else 0
        else:
            ev_total_pct_change_1 = 0
            ev_total_pct_change_3 = 0
        
        ev_growth_slope = calculate_growth_slope(recent_ev_totals)
        county_encoded = COUNTY_ENCODING.get(county, 0)
        
        features = [
            months_since_start,
            county_encoded,
            ev_total_lag1,
            ev_total_lag2,
            float(input_data.get('ev_total_lag3', ev_total_lag2)),
            ev_total_roll_mean_3,
            ev_total_pct_change_1,
            ev_total_pct_change_3,
            ev_growth_slope
        ]
        
        prediction = model.predict([features])
        
        return {
            'prediction': float(prediction[0]),
            'features': dict(zip(FEATURE_NAMES, features))
        }
    
    except Exception as e:
        return {'error': str(e)}

def main():

    st.markdown(
        """
        <div style='background: linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%); 
                    padding: 2rem; 
                    border-radius: 15px; 
                    color: #1e293b;
                    margin-bottom: 2rem;
                    border: 1px solid #e2e8f0;
                    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);'>
            <div style='text-align: center; padding: 1.5rem 0;'>
                <h1 style='margin: 0; color: #1e293b; font-size: 2.5rem; font-weight: 700; letter-spacing: -0.5px;'>
                    EV Demand Prediction
                </h1>
                <div style='max-width: 700px; margin: 1rem auto 0;'>
                    <p style='font-size: 1.2rem; color: #475569; margin: 1rem 0 0 0; line-height: 1.6;'>
                        Predict the next month's EV demand based on historical data. Get accurate forecasts to optimize your EV infrastructure planning and resource allocation.
                    </p>
                </div>
            </div>
        </div>
        """, 
        unsafe_allow_html=True
    )

    col1, col2 = st.columns(2)
    
    with col1:
        st.markdown("### 🗺️ Location & Date")
        county = st.selectbox('Select County', options=list(COUNTY_ENCODING.keys()))
        current_date = st.date_input('Current Date', value=datetime.now())
    
    with col2:
        st.markdown("### 📊 Current EV Data")
        current_ev_total = st.number_input(
            'Current Number of EVs',
            min_value=0,
            step=1,
            value=1000,
            help="Current total number of electric vehicles"
        )
    
    st.markdown("### 📈 Historical EV Data")
    hist_col1, hist_col2, hist_col3 = st.columns(3)
    
    with hist_col1:
        ev_total_lag1 = st.number_input(
            'EVs 1 Month Ago',
            min_value=0,
            step=1,
            value=900
        )
    
    with hist_col2:
        ev_total_lag2 = st.number_input(
            'EVs 2 Months Ago',
            min_value=0,
            step=1,
            value=800
        )
    
    with hist_col3:
        ev_total_lag3 = st.number_input(
            'EVs 3 Months Ago',
            min_value=0,
            step=1,
            value=700
        )
    
    # Prediction button
    if st.button('🔮 Predict Next Month\'s EV Demand', use_container_width=True):
        with st.spinner('Crunching the numbers...'):
            input_data = {
                'county': county,
                'current_date': current_date.strftime('%Y-%m-%d'),
                'current_ev_total': current_ev_total,
                'ev_total_lag1': ev_total_lag1,
                'ev_total_lag2': ev_total_lag2,
                'ev_total_lag3': ev_total_lag3
            }
            
            result = predict_ev_demand(input_data)
            
            if 'error' in result:
                st.error(f"❌ Error making prediction: {result['error']}")
            else:
                st.balloons()
                
                # Display prediction in a nice card
                st.markdown("""
                <div class="prediction-card">
                    <h2>🚀 Prediction Result</h2>
                    <div style='display: flex; align-items: center; margin: 1.5rem 0;'>
                        <div style='flex: 1;'>
                            <h3 style='color: #6b7280; margin-bottom: 0.5rem;'>Predicted EV Total for Next Month</h3>
                            <div style='font-size: 2.5rem; font-weight: bold; color: #1e40af;'>
                                {:,}
                            </div>
                            <div style='margin-top: 0.5rem; font-size: 1.1rem; color: {};'>
                                {}{:,} from current
                            </div>
                        </div>
                        <div style='margin-left: 2rem; font-size: 4rem;'>
                            {}
                        </div>
                    </div>
                </div>
                """.format(
                    int(round(result['prediction'])),
                    '#10b981' if result['prediction'] > current_ev_total else '#ef4444',
                    '↑ +' if result['prediction'] > current_ev_total else '↓ ',
                    int(round(abs(result['prediction'] - current_ev_total))),
                    '📈' if result['prediction'] > current_ev_total else '📉'
                ), unsafe_allow_html=True)
                
                # Show features in an expander
                with st.expander("📊 View Detailed Analysis", expanded=True):
                    st.markdown("### 📋 Features Used for Prediction")
                    features = result['features']
                    
                    # Create a 3-column layout for features
                    col1, col2, col3 = st.columns(3)
                    
                    # Group features into 3 columns
                    features_list = list(features.items())
                    chunk_size = (len(features_list) + 2) // 3  # Distribute features across 3 columns
                    
                    for i, chunk in enumerate([features_list[i:i + chunk_size] for i in range(0, len(features_list), chunk_size)]):
                        with [col1, col2, col3][i]:
                            for key, value in chunk:
                                # Format the value based on its type
                                formatted_value = f"{value:,.2f}" if isinstance(value, (float, int)) and not isinstance(value, bool) else str(value)
                                st.markdown(f"""
                                <div class="feature-card">
                                    <div style='color: #4b5563; font-weight: 500;'>{key.replace('_', ' ').title()}</div>
                                    <div style='font-size: 1.2rem; font-weight: 600; color: #1e40af;'>{formatted_value}</div>
                                </div>
                                """, unsafe_allow_html=True)
                
                st.markdown("### 📈 EV Adoption Trend")
                months = ['3 Months Ago', '2 Months Ago', 'Last Month', 'Current', 'Next Month (Predicted)']
                values = [
                    ev_total_lag3,
                    ev_total_lag2,
                    ev_total_lag1,
                    current_ev_total,
                    result['prediction']
                ]
                
                chart_data = pd.DataFrame({
                    'Month': months,
                    'EV Count': values
                })
                
                st.line_chart(
                    chart_data.set_index('Month'),
                    use_container_width=True,
                    height=400
                )

if __name__ == '__main__':
    main()