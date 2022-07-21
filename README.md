# Homework assignment
## About
This is a Spring Boot backend homework assignment.

Complementary API-consuming frontend Vue.js project:
https://github.com/romansj/BackendHwkVue


### API specs
Generated with Swagger, available:
https://romansj.github.io/open_api_hwk/open_api/index.html

### Test & coverage report
Generated from full test suite run:
Test report: https://romansj.github.io/open_api_hwk/test/index.html
Coverage: https://romansj.github.io/open_api_hwk/coverage_report/index.html


<br>

## How to

### Launch application

#### 1. Install pgAdmin 4

https://www.pgadmin.org/download/

Use following settings for ease of configuration (can be modified in `application.properties`)
* port = `5432`
* username = `postgres`
* password = `password`

After installing, under Servers -> "PostgreSQL 14" create a database named "backend_hwk".  
Tables will be created automatically when you launch the app.

> *Can be replaced with in-memory config like test, see* `..src\test\resources\application.properties`

<br>

#### 2. Configure parameters (optional)
*Optional - will throw error instead of sending email - if you don't configure, but server will continue running*

**Email**  
`..src\main\resources\email-config.properties`

* Set an email address you would like to send out email from
* Generate an app password https://support.google.com/accounts/answer/185833?hl=en
* Set password variable to generated password

**User**  
`..src\main\resources\email-config.properties`

* Set your e-mail address for receiving e-mails
* Set your name if you wish

<br>

#### 3. Launch!

Select the application Gradle configuration and click Run (Shift-F10)
<br>

### Run tests

Right click on `test` module, select   
`Run tests...` or   
`Run tests with coverage` to get coverage results.