# GitHub Actions Status Badge

Add these badges to your README.md:

## Workflow Badges

```markdown
![Build and Test](https://github.com/YOUR_USERNAME/YOUR_REPO/actions/workflows/build-and-test.yml/badge.svg)
![Docker Build](https://github.com/YOUR_USERNAME/YOUR_REPO/actions/workflows/docker-build.yml/badge.svg)
![Security Scan](https://github.com/YOUR_USERNAME/YOUR_REPO/actions/workflows/security-scan.yml/badge.svg)
![Code Coverage](https://github.com/YOUR_USERNAME/YOUR_REPO/actions/workflows/code-coverage.yml/badge.svg)
```

## Codecov Badge

```markdown
[![codecov](https://codecov.io/gh/YOUR_USERNAME/YOUR_REPO/branch/main/graph/badge.svg?token=YOUR_CODECOV_TOKEN)](https://codecov.io/gh/YOUR_USERNAME/YOUR_REPO)
```

## SonarCloud Badge (if configured)

```markdown
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=YOUR_PROJECT_KEY&metric=alert_status)](https://sonarcloud.io/dashboard?id=YOUR_PROJECT_KEY)
```

## Replace:
- `YOUR_USERNAME` - Your GitHub username
- `YOUR_REPO` - Your repository name
- `YOUR_CODECOV_TOKEN` - Your Codecov project token
- `YOUR_PROJECT_KEY` - Your SonarCloud project key

## Example in README:

```markdown
## Project Status

![Build and Test](https://github.com/Mahesharunaladi/EV-vehicle/actions/workflows/build-and-test.yml/badge.svg)
![Docker Build](https://github.com/Mahesharunaladi/EV-vehicle/actions/workflows/docker-build.yml/badge.svg)
![Security Scan](https://github.com/Mahesharunaladi/EV-vehicle/actions/workflows/security-scan.yml/badge.svg)

[![codecov](https://codecov.io/gh/Mahesharunaladi/EV-vehicle/branch/main/graph/badge.svg)](https://codecov.io/gh/Mahesharunaladi/EV-vehicle)
```
