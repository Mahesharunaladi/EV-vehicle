#!/bin/bash

# GitHub Actions CI/CD Setup Verification Script
# This script helps verify that all CI/CD components are properly configured

echo "🔍 GitHub Actions CI/CD Setup Verification"
echo "=========================================="
echo ""

# Check if .github directory exists
if [ -d ".github" ]; then
    echo "✅ .github directory exists"
else
    echo "❌ .github directory not found"
    exit 1
fi

# Check workflow files
WORKFLOWS=("build-and-test.yml" "docker-build.yml" "security-scan.yml" "code-coverage.yml" "deploy.yml" "release.yml" "lint.yml" "pr-checks.yml")
echo ""
echo "📋 Checking Workflow Files:"
for workflow in "${WORKFLOWS[@]}"; do
    if [ -f ".github/workflows/$workflow" ]; then
        echo "  ✅ $workflow"
    else
        echo "  ❌ $workflow (missing)"
    fi
done

# Check documentation files
DOCS=("README.md" "SETUP.md" "CICD_GUIDE.md" "BADGES.md" "BRANCH_PROTECTION.md")
echo ""
echo "📚 Checking Documentation:"
for doc in "${DOCS[@]}"; do
    if [ -f ".github/$doc" ]; then
        echo "  ✅ $doc"
    else
        echo "  ❌ $doc (missing)"
    fi
done

# Check Docker files
echo ""
echo "🐳 Checking Docker Configuration:"
if [ -f "Dockerfile" ]; then
    echo "  ✅ Dockerfile"
else
    echo "  ❌ Dockerfile (missing)"
fi

if [ -f "docker-compose.yml" ]; then
    echo "  ✅ docker-compose.yml"
else
    echo "  ❌ docker-compose.yml (missing)"
fi

if [ -f ".dockerignore" ]; then
    echo "  ✅ .dockerignore"
else
    echo "  ❌ .dockerignore (missing)"
fi

# Check pom.xml
echo ""
echo "🔨 Checking Build Configuration:"
if [ -f "pom.xml" ]; then
    echo "  ✅ pom.xml"
    echo ""
    echo "📊 Build Details:"
    echo "  - Java Version: $(grep -A1 'maven.compiler.source' pom.xml | tail -1 | sed 's/<[^>]*>//g' | xargs)"
    echo "  - Project: $(grep '<artifactId>' pom.xml | head -1 | sed 's/<[^>]*>//g' | xargs)"
else
    echo "  ❌ pom.xml (missing)"
fi

# Git status check
echo ""
echo "🔄 Git Status:"
if git rev-parse --git-dir > /dev/null 2>&1; then
    echo "  ✅ Git repository initialized"
    BRANCH=$(git rev-parse --abbrev-ref HEAD)
    echo "  📍 Current branch: $BRANCH"
    REMOTE=$(git config --get remote.origin.url)
    echo "  🌐 Remote: $REMOTE"
else
    echo "  ⚠️ Not a git repository"
fi

# Summary
echo ""
echo "=========================================="
echo "✅ CI/CD Setup Verification Complete!"
echo ""
echo "🚀 Next Steps:"
echo "  1. Push all changes to GitHub"
echo "  2. Configure secrets in GitHub Settings"
echo "  3. Set up branch protection rules"
echo "  4. Connect external services (optional)"
echo "  5. Create your first release with: git tag -a v1.0.0"
echo ""
echo "📚 For detailed setup instructions, see:"
echo "  - .github/README.md"
echo "  - .github/SETUP.md"
echo "  - .github/CICD_GUIDE.md"
echo ""
