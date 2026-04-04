# CI/CD Configuration Guide

This project uses GitHub Actions for continuous integration and continuous deployment.

## 📋 Workflows

### 1. **Build and Test** (`build-and-test.yml`)
- **Trigger**: Push to main/develop/feature branches, PRs
- **Actions**:
  - Builds project with Maven
  - Runs on Java 11 and 17
  - Executes unit tests
  - Generates test reports
  - Uploads coverage to Codecov
  - Uploads build artifacts

### 2. **Docker Build** (`docker-build.yml`)
- **Trigger**: Push to main/develop, tags, or manual
- **Actions**:
  - Builds Docker image
  - Pushes to GitHub Container Registry
  - Caches layers for faster builds
  - Multi-platform support

### 3. **Security Scan** (`security-scan.yml`)
- **Trigger**: Push, PRs, weekly schedule
- **Actions**:
  - OWASP Dependency-Check
  - Trivy vulnerability scanning
  - Snyk security analysis
  - CodeQL analysis

### 4. **Code Coverage** (`code-coverage.yml`)
- **Trigger**: Push to main/develop, PRs
- **Actions**:
  - JaCoCo coverage report
  - Uploads to Codecov
  - Comments on PRs

### 5. **Deploy** (`deploy.yml`)
- **Trigger**: Push to main or manual workflow dispatch
- **Actions**:
  - Builds application
  - Pushes to AWS ECR
  - Updates ECS service
  - Notifies Slack on success/failure

### 6. **Release** (`release.yml`)
- **Trigger**: Git tags (v*.*.*) or manual
- **Actions**:
  - Creates GitHub Release
  - Uploads JAR artifacts
  - Generates release notes
  - Notifies Slack

### 7. **Lint** (`lint.yml`)
- **Trigger**: Push to main/develop/feature branches
- **Actions**:
  - Checkstyle validation
  - SpotBugs analysis
  - PMD checking
  - Generates quality reports

## 🔑 Required Secrets

Add these secrets to your GitHub repository settings (`Settings` → `Secrets and variables` → `Actions`):

### For Deployment:
- `AWS_ACCESS_KEY_ID` - AWS access key
- `AWS_SECRET_ACCESS_KEY` - AWS secret key
- `AWS_REGION` - AWS region (e.g., us-east-1)
- `AWS_ACCOUNT_ID` - AWS account ID

### For Notifications:
- `SLACK_WEBHOOK_URL` - Slack webhook for notifications

### For Security:
- `SNYK_TOKEN` - Snyk API token
- `SONAR_TOKEN` - SonarCloud token (optional)
- `CODECOV_TOKEN` - Codecov token (optional)

### For Publishing:
- `OSSRH_USERNAME` - Maven Central username (optional)
- `OSSRH_TOKEN` - Maven Central token (optional)

## 📦 Environment Configuration

Create environments for different deployment stages:

1. **Settings** → **Environments**
2. Add environments:
   - `staging`
   - `production`

## 🚀 Quick Start

### Triggering Workflows

1. **Automatic on Push**: Workflows run automatically on push to main/develop
2. **On Pull Request**: All checks run on PR
3. **Manual Dispatch**: Click "Run workflow" on the workflow page

### Creating a Release

```bash
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
```

### Manual Deployment

1. Go to Actions → Deploy
2. Click "Run workflow"
3. Select environment (staging/production)
4. Click "Run workflow"

## 📊 Monitoring

### GitHub Actions Tab
- View all workflow runs
- Check logs for each step
- Download artifacts

### External Services Integration
- **Codecov**: Code coverage reports
- **SonarCloud**: Code quality metrics
- **Snyk**: Security vulnerabilities
- **Slack**: Real-time notifications

## 🛠️ Local Setup

### Prerequisites
- JDK 11+
- Maven 3.6+
- Docker (optional, for Docker builds)

### Build Locally
```bash
mvn clean package
```

### Run Tests
```bash
mvn test
```

### Generate Coverage Report
```bash
mvn clean test jacoco:report
```

## 📝 Best Practices

1. **Branch Protection**: Enable branch protection rules on main
   - Require status checks to pass
   - Require pull request reviews
   - Require branches to be up to date

2. **Commit Messages**: Follow conventional commits
   ```
   feat: add new feature
   fix: fix a bug
   docs: update documentation
   test: add tests
   ```

3. **Version Tags**: Use semantic versioning
   - `v1.0.0` for releases
   - `v1.0.0-rc1` for release candidates
   - `v1.0.0-beta` for beta versions

4. **PR Reviews**: Ensure workflows pass before merging

## 🔍 Troubleshooting

### Build Failures
1. Check the "Build and Test" workflow logs
2. Review the error messages
3. Check Java version compatibility (11+)
4. Verify Maven dependencies

### Deployment Failures
1. Verify AWS credentials are correct
2. Check ECS cluster and service names
3. Review CloudWatch logs for container issues
4. Check Slack notifications for details

### Security Scan Issues
1. Review dependency versions
2. Update vulnerable packages
3. Check security reports in GitHub Security tab

## 📚 Resources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Docker Documentation](https://docs.docker.com/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
