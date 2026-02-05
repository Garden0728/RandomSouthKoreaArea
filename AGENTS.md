# Repository Guidelines

## Project Structure & Module Organization
- `src/main/java/Service/RandomArea/` contains application code (controllers, domain, config, API helpers).
- `src/main/resources/` holds templates, static assets, and configuration (`application.yaml`, `application-local.yaml`).
- `src/main/resources/Poly.region.json` provides GeoJSON data used by the service.
- `src/test/java/Service/RandomArea/` contains JUnit tests.
- `images/` stores architecture and deployment diagrams referenced by docs.

## Build, Test, and Development Commands
- `./gradlew bootRun` (or `gradlew.bat bootRun` on Windows) starts the Spring Boot app locally.
- `./gradlew test` runs the JUnit 5 test suite.
- `./gradlew build` builds the app and runs tests.
- `docker build -t random-area:local .` builds the Docker image using the provided `Dockerfile`.

## Coding Style & Naming Conventions
- Java 17, Spring Boot 3.5.x, Thymeleaf; follow existing package layout under `Service.RandomArea`.
- Use 4-space indentation and standard Java conventions (PascalCase classes, camelCase methods/fields).
- Prefer clear, domain-specific names; keep controller endpoints and template names aligned.

## Testing Guidelines
- Framework: JUnit 5 (Spring Boot test starter).
- Test naming follows `*Test.java` (e.g., `CoordinateServiceTest.java`).
- Add tests for geo-related logic and service-layer behavior; run `./gradlew test` before PRs.

## Commit & Pull Request Guidelines
- Commit messages follow a conventional prefix: `feat:`, `refactor:`, `test:` (see recent history).
- Keep commits small and scoped; use one feature or fix per commit when possible.
- No PR template is present; include a short description, test command(s) run, and screenshots for UI changes.

## Security & Configuration Tips
- Do not commit secrets. Production keys (Kakao, Sentry) are injected via GitHub Actions secrets.
- Use `SPRING_PROFILES_ACTIVE` and `application-local.yaml` for local configuration overrides.
