
# Google Sheet Read Service

Create a new google service account
https://support.google.com/a/answer/7378726?hl=en

Before running the project please update the application.properties under src/main/resources with your google service account details from the downloaded secret json

#
# Google credentials
#
```
google.account.secret.type=
google.account.secret.project-id=
google.account.secret.private-key-id=
google.account.secret.private-key=
google.account.secret.client-email=
google.account.secret.client-id=
google.account.secret.auth-uri=
google.account.secret.token-uri=
google.account.secret.auth-provider-x509-cert-url=
google.account.secret.client-x509-cert-url=
```

## Software required to build and run
```
Java: JDK 1.8+
Maven: Apache Maven 3.6.3+
```
## Package Information
If you are updating the package, please change the references in
```
com.github.yshameer.google.sheet.service.SheetServiceApplication
   Line 13:  @ComponentScan(basePackages = "com.github.yshameer.google.sheet.service")

com.github.yshameer.google.sheet.service.configuration.SheetConfig
   Line 50: RequestHandlerSelectors.basePackage("com.github.yshameer.google.sheet.service.api")
   
```

## Running Locally

To build and run with maven do the following:

```
mvn clean install
mvn spring-boot:run
```

## Testing Locally
```Swagger UI: http://localhost:8080```

```
If you already have a sheet you are trying to access do step 3 else follow steps 1 to 3

1) Create a new google sheet https://docs.google.com/spreadsheets/

2) Create a worksheet with first column as column header and add values 

3) Share the sheet with secret.client-email from properties (service account) before accessing the sheet with the api

```


##APIs
```
GET /sheetInfo/{sheetId} returns the basic information about the sheet
POST /sheetData returs the data for the requested columns with row and column numbers of the requested worksheet(s)

Sheet Id can be found after https://docs.google.com/spreadsheets/d/
```
