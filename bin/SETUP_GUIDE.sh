#!/bin/bash

# EV Vehicle Demand Prediction - Setup Guide
# This script helps you set up and run both frontend and backend

echo "╔════════════════════════════════════════════════════════════╗"
echo "║  EV Vehicle Demand Prediction - Setup Guide              ║"
echo "║  Frontend & Backend Separation                            ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Color codes
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${BLUE}Choose an option:${NC}"
echo "1. Build and run backend"
echo "2. Run frontend with Python server"
echo "3. Run frontend with Node.js"
echo "4. Run frontend with PHP"
echo "5. View documentation"
echo "6. Exit"
echo ""

read -p "Enter your choice (1-6): " choice

case $choice in
    1)
        echo -e "${YELLOW}Building and running backend...${NC}"
        cd backend
        echo -e "${GREEN}✓ Building project${NC}"
        mvn clean install
        echo -e "${GREEN}✓ Starting Spring Boot application${NC}"
        mvn spring-boot:run
        ;;
    2)
        echo -e "${YELLOW}Running frontend with Python server...${NC}"
        cd frontend
        echo -e "${GREEN}✓ Starting Python server on http://localhost:8000${NC}"
        python3 -m http.server 8000
        ;;
    3)
        echo -e "${YELLOW}Running frontend with Node.js...${NC}"
        cd frontend
        echo -e "${GREEN}✓ Starting Node.js server on http://localhost:8000${NC}"
        http-server
        ;;
    4)
        echo -e "${YELLOW}Running frontend with PHP...${NC}"
        cd frontend
        echo -e "${GREEN}✓ Starting PHP server on http://localhost:8000${NC}"
        php -S localhost:8000
        ;;
    5)
        echo -e "${BLUE}Documentation Files:${NC}"
        echo "  • ARCHITECTURE.md - Complete project overview"
        echo "  • FRONTEND_BACKEND_SEPARATION.md - Separation benefits"
        echo "  • PROJECT_STRUCTURE.md - Visual structure"
        echo "  • frontend/README.md - Frontend documentation"
        echo "  • backend/README.md - Backend documentation"
        ;;
    6)
        echo -e "${YELLOW}Exiting...${NC}"
        exit 0
        ;;
    *)
        echo -e "${YELLOW}Invalid choice. Please try again.${NC}"
        ;;
esac

echo ""
echo -e "${GREEN}Setup guide completed!${NC}"
