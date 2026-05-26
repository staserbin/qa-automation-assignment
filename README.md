# QA Automation Test Suite

A comprehensive QA automation framework covering REST API, GraphQL, and UI testing
for Restful Booker API and DemoQA website.

---

## Tech Stack

| Tool | Purpose |
|---|---|
| Java 21 | Primary language |
| JUnit 5 | Test framework |
| REST Assured | API testing |
| Playwright for Java | UI testing |
| AssertJ | Fluent assertions |
| Jackson | JSON serialization |
| Lombok | Boilerplate reduction |
| JavaFaker | Test data generation |
| Allure | Test reporting |

---

## Prerequisites

- Java 21+
- Maven 3.6+
- Chrome browser (installed automatically by Playwright)

---

## Setup

```bash
# Clone the repository
git clone https://github.com/staserbin/qa-automation-assignment
cd qa-automation-assignment

# Install dependencies
mvn clean install -DskipTests

# Install Playwright browsers
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chromium"
```

---

## How to Run

```bash
# Run all tests
mvn clean test

# Run only API tests
mvn test -Dgroups="api"

# Run only GraphQL tests
mvn test -Dgroups="api,graphql"

# Run only UI tests
mvn test -Dgroups="ui"

# Generate and open Allure report
mvn allure:serve
```

---

## CI/CD

Can be triggered manually via GitHub Actions UI (`workflow_dispatch`).

**Job:**
- `automation-tests` - runs all automated tests (API, UI, GraphQL)

**Allure Report** is linked directly in the GitHub Actions pipeline summary after each run.

---

## Project Structure

```
src/
├── main/java/com/flamingo/qa/
│   ├── api/
│   │   ├── builders/       # Test data factories
│   │   ├── clients/        # REST and GraphQL API clients
│   │   ├── core/           # ConfigReader, RestAssuredConfig, QueryLoader
│   │   └── models/         # Request/Response models (REST + GraphQL)
│   └── ui/
│       ├── builders/       # UI test data factories
│       ├── components/     # Reusable UI components (modals)
│       ├── core/           # BrowserManager, PlaywrightConfig, PageManager
│       ├── model/          # UI data models (RegistrationFormData, TableRecord)
│       └── pages/          # Page Object classes
└── test/
    ├── java/com/flamingo/qa/
    │   ├── api/
    │   │   ├── base/       # BaseApiTest
    │   │   ├── graphql/    # GraphQlPositiveTest, GraphQlNegativeTest
    │   │   └── rest/       # BookingCrudTest
    │   └── ui/
    │       ├── base/       # BaseUiTest
    │       └── tests/      # RegistrationFormTest, WebTablesTest
    └── resources/
        ├── config/         # config.properties
        ├── graphql/        # .graphql query files
        └── upload/         # test-file.txt for upload tests
```

---

## Test Coverage

### API - Restful Booker

| Test Class | Scenarios |
|---|---|
| `BookingCrudTest` | Auth token, Create, Read, Update, Delete booking |
| `GraphQlPositiveTest` | Paginated list, single entity by ID with variables, nested fields, skip offset |
| `GraphQlNegativeTest` | Non-existent ID, malformed query, invalid field (data-driven) |

### UI - DemoQA

| Test Class | Scenarios |
|---|---|
| `RegistrationFormTest` | Full form submission with file upload, date picker, dropdowns, modal verification |
| `WebTablesTest` | Add record, Edit record, Delete record, Search functionality |

> **Note:** Sorting validation for Web Tables was not automated. The `<th>` column
> headers are static HTML elements with no click handlers. Sorting isn't implemented
> on the page. Writing a test against non-existent functionality would produce a false
> negative. This is documented in Challenges & Solutions below.

---

## Test Strategy

**API:** REST tests follow the full CRUD lifecycle in a single ordered class,
reflecting a real booking workflow. GraphQL tests are split into positive/negative
classes. Negative scenarios use `@ParameterizedTest` with `@MethodSource`, multiple
invalid query types run from one data-driven test. The non-existent ID test is
kept separate as it produces a different response shape (`data: null`, no errors).

**UI:** Both suites use the POM pattern with a `PageManager` as a
central lazy factory, no `new PageObject(page)` in test classes. `BaseUiTest`
handles browser lifecycle and auto-attaches a full-page screenshot to Allure after
every test. Test data is generated via dedicated factory classes with JavaFaker for
dynamic fields. Playwright's built-in smart waiting (`waitForSelector`,
`waitForFunction`) is used throughout, no `Thread.sleep` or fixed timeouts.

**What was prioritized:** Framework architecture over test quantity: reusable base
classes, clients, factories, and page objects that scale easily. Allure `@Step`
annotations on clients and page objects provide full execution traceability.

---

## Challenges & Solutions

**418 on POST /booking**: Restful Booker returns `418` when the `Accept` header is
missing. Fixed by explicitly adding `Accept: application/json` as a header in
`RestAssuredConfig` alongside `.setAccept(JSON)`.

**GraphQL returns 400 for invalid queries**: `execute()` asserted `statusCode(200)`, 
but Hygraph returns `400` for syntax errors. Added `executeExpectingError()` accepting
both `200` and `400` via Hamcrest's `anyOf`.

**DemoQA gender radio not clickable**: Labels use `form-check-label`, not the assumed
`custom-control-label`. Ad banners also overlap elements. Fixed using
`label[for='gender-radio-N']` selector with `.setForce(true)` on click.

**React Select dynamic CSS classes**: State/City dropdowns generate class names
dynamically. Fixed using `[role='option']` with `.filter(hasText(...))`.

**Web Tables sorting not testable**: `<th>` headers are static with no sort handlers.
Test intentionally excluded, sorting functionality doesn't exist on the page.

---

## What I Would Add With More Time

- **Negative API tests**: invalid auth, missing fields, non-existent IDs for Booking
- **JSON Schema validation**: contract testing for API response structures
- **Cross-browser UI tests**: Firefox and WebKit via `BrowserManager` configuration
