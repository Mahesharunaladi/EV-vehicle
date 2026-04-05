# GitHub Actions Setup Instructions

Follow these steps to fully enable CI/CD for your EV Vehicle Demand Prediction project.

## Step 1: Verify Workflow Files

All workflow files have been created in `.github/workflows/`:
- ✅ `build-and-test.yml` - Build and test on every push/PR
- ✅ `docker-build.yml` - Build and push Docker images
- ✅ `security-scan.yml` - Run security scans
- ✅ `code-coverage.yml` - Generate coverage reports
- ✅ `deploy.yml` - Deploy to AWS/ECS
- ✅ `release.yml` - Create releases from tags
- ✅ `lint.yml` - Code quality checks
- ✅ `pr-checks.yml` - PR validation

## Step 2: Add Repository Secrets

Go to **Settings** → **Secrets and variables** → **Actions** and add:

### For Deployment (Optional):
```
AWS_ACCESS_KEY_ID = your-aws-key
AWS_SECRET_ACCESS_KEY = your-aws-secret
AWS_REGION = us-east-1
AWS_ACCOUNT_ID = your-account-id
```

### For Notifications (Optional):
```
SLACK_WEBHOOK_URL = your-slack-webhook
```

### For Security Scanning (Optional):
```
SNYK_TOKEN = your-snyk-token
SONAR_TOKEN = your-sonarcloud-token
```

## Step 3: Configure Branch Protection

Go to **Settings** → **Branches** → **Branch protection rules**:

1. Click "Add rule"
2. Branch name pattern: `main`
3. Enable:
   - ✅ Require a pull request before merging
   - ✅ Require status checks to pass before merging
   - ✅ Require branches to be up to date before merging
   - ✅ Require code reviews before merging (at least 1)
   - ✅ Dismiss stale pull request approvals when new commits are pushed
   - ✅ Require conversation resolution before merging

4. Select status checks:
   - ✅ build (11)
   - ✅ build (17)
   - ✅ code-quality

## Step 4: Create Environments (Optional)

Go to **Settings** → **Environments**:

Create two environments:

### Staging
- Environment name: `staging`
- Protection rules:
  - ⚠️ Required reviewers: Select team members
  - ⚠️ Deployment branches: Only `develop` and `main`

### Production
- Environment name: `production`
- Protection rules:
  - ⚠️ Required reviewers: Senior team leads
  - ⚠️ Deployment branches: Only `main`
  - ⚠️ Wait timer: 1 hour (for safety)

## Step 5: Connect External Services

### Codecov Integration
1. Visit [codecov.io](https://codecov.io)
2. Sign up with GitHub
3. Add your repository
4. Get token and add to secrets as `CODECOV_TOKEN`

### SonarCloud Integration (Optional)
1. Visit [sonarcloud.io](https://sonarcloud.io)
2. Sign up with GitHub
3. Create project for your repository
4. Add token to secrets as `SONAR_TOKEN`

### Snyk Integration (Optional)
1. Visit [snyk.io](https://snyk.io)
2. Sign up with GitHub
3. Connect repository
4. Add token to secrets as `SNYK_TOKEN`

## Step 6: Initial Setup Commit

```bash
# Navigate to your project
cd /Users/mahesharunaladi/Documents/EV\ vehicle/EV_Vehicle_Demand-prediction

# Add all new CI/CD files
git add .github/ Dockerfile docker-compose.yml .dockerignore

# Commit
git commit -m "feat: add GitHub Actions CI/CD pipeline

- Add build and test workflow
- Add Docker build workflow
- Add security scanning workflow
- Add deployment workflow
- Add release workflow
- Add code coverage workflow
- Add linting workflow
- Add PR checks workflow
- Add Dockerfile and docker-compose.yml"

# Push to repository
git push origin main
```

## Step 7: Verify Workflows

1. Go to **Actions** tab on GitHub
2. Check that workflows appear
3. View "Build and Test" workflow
4. It should start running automatically

## Quick Command Reference

### Create a release (auto-triggers release workflow):
```bash
git tag -a v1.0.0 -m "Release 1.0.0"
git push origin v1.0.0
```

### Manual workflow dispatch:
- Go to Actions → Select workflow → Run workflow

### View logs:
- Go to Actions → Select run → View logs

### Download artifacts:
- Go to Actions → Select run → Download artifacts

## Project Structure

```
.github/
├── workflows/
│   ├── build-and-test.yml      # Build & Test (Java 11, 17)
│   ├── docker-build.yml         # Build Docker images
│   ├── security-scan.yml        # Security scans
│   ├── code-coverage.yml        # Code coverage reports
│   ├── deploy.yml               # AWS deployment
│   ├── release.yml              # Release automation
│   ├── lint.yml                 # Code quality
│   └── pr-checks.yml            # PR validation
├── CICD_GUIDE.md               # Full CI/CD documentation
├── BADGES.md                   # GitHub badges
└── workflows/

Dockerfile                       # Multi-stage Docker build
docker-compose.yml              # Local development setup
.dockerignore                   # Docker ignore file
```

## Workflow Triggers

| Workflow | Trigger | Environment |
|----------|---------|-------------|
| Build & Test | push, PR | ubuntu-latest |
| Docker Build | push (main/develop/tags) | ubuntu-latest |
| Security Scan | push, PR, weekly | ubuntu-latest |
| Code Coverage | push (main/develop), PR | ubuntu-latest |
| Deploy | push main (manual) | ubuntu-latest → AWS |
| Release | Git tag (v*.*.*) | ubuntu-latest |
| Lint | push, PR | ubuntu-latest |
| PR Checks | PR events | ubuntu-latest |

## Troubleshooting

### Workflows not running
- ✅ Check `.github/workflows/` contains YAML files
- ✅ Verify YAML syntax (check workflow logs)
- ✅ Check branch names match workflow triggers

### Build fails
- ✅ Check Java version (should be 11+)
- ✅ Verify Maven dependencies
- ✅ Check pom.xml syntax
- ✅ Review workflow logs for error messages

### Deployment fails
- ✅ Verify AWS credentials in secrets
- ✅ Check AWS region and account ID
- ✅ Ensure ECS cluster/service names match
- ✅ Check CloudWatch logs for container errors

### Docker build issues
- ✅ Check Dockerfile syntax
- ✅ Verify Docker registry credentials
- ✅ Check image name format
- ✅ Review build logs

## Best Practices

1. **Commit Messages**: Use conventional commits
   ```
   feat: add feature
   fix: fix bug
   docs: update docs
   test: add tests
   ```

2. **Branch Strategy**: Use trunk-based development
   - `main` - production-ready code
   - `develop` - development branch
   - `feature/*` - feature branches

3. **PR Reviews**: Always require code review before merge

4. **Tags**: Use semantic versioning for releases
   - `v1.0.0` - release
   - `v1.0.0-rc1` - release candidate
   - `v1.0.0-beta` - beta version

5. **Monitoring**: Check GitHub Actions dashboard regularly

## Support

For detailed information:
- Read `.github/CICD_GUIDE.md`
- Check GitHub Actions documentation
- Review workflow logs for errors
- Check external service integrations

## Security Notes

⚠️ **Important**:
- Never commit secrets to repository
- Use GitHub secrets for sensitive data
- Rotate tokens regularly
- Limit secret access to required workflows
- Review GitHub Actions logs for exposed secrets
- Enable branch protection on production branches

---

✅ **Setup Complete!** Your CI/CD pipeline is ready. Push a commit to see workflows in action.
