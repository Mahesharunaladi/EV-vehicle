# Recommended Branch Protection Rule Configuration

Use this configuration to protect your main branch. Go to:
**Settings** → **Branches** → **Add rule** and configure as shown below.

## Basic Settings

- **Branch name pattern**: `main`
- **Include administrators**: ☑️ (Recommended)

## Protection Rules

### Require Status Checks
- ☑️ Require status checks to pass before merging
- ☑️ Require branches to be up to date before merging

**Select status checks to require:**
- ☑️ `build (11)` - Java 11 build
- ☑️ `build (17)` - Java 17 build
- ☑️ `code-quality` - Code quality checks
- ☑️ `docker-build` - Docker build

### Require Pull Request Reviews
- ☑️ Require a pull request before merging
- **Required number of reviewers**: 1
- ☑️ Require approval of reviews before merging
- ☑️ Dismiss stale pull request approvals when new commits are pushed
- ☑️ Require review from Code Owners

### Require Conversation Resolution
- ☑️ Require all conversations on code to be resolved before merging

### Additional Rules
- ☑️ Require signed commits (Optional, for security)
- ☑️ Allow force pushes: None (for security)
- ☑️ Allow deletions: ☐ (Disabled for safety)

## Create Matching Branch Protection for Develop

Create a similar rule for the `develop` branch with slightly relaxed requirements:

- **Branch name pattern**: `develop`
- ☑️ Require status checks to pass
- ☑️ Require 1 pull request review
- ☑️ Require branches up to date
- ☑️ Require conversation resolution

## Bypassing Protection (for Admins)

This configuration allows administrators to bypass rules if necessary. To disable this, uncheck "Include administrators".
