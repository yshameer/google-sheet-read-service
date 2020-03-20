package com.github.yshameer.google.sheet.service.configuration;

import com.github.yshameer.google.sheet.service.helper.SheetsHelper;
import com.github.yshameer.google.sheet.service.model.GoogleAccountSecret;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

import static java.util.Collections.singletonList;

@Configuration
public class SheetConfig {

    private static final String APPLICATION_NAME = "SHEET_SERVICE";

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Google Sheet Service API")
                .description("Google Sheet Service provides the resources to read google sheet")
                .license("")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("", "", ""))
                .build();
    }

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.yshameer.google.sheet.service.api"))
                .build()
                .apiInfo(apiInfo());
    }


    @Bean
    public GoogleCredential googleCredential(GoogleAccountConfiguration googleAccountConfig,
                                             Gson gson,
                                             NetHttpTransport httpTransport,
                                             JsonFactory jsonFactory) throws IOException {
        GoogleAccountSecret googleAccountSecret = toGoogleAccountSecret(googleAccountConfig);
        String secretJson = gson.toJson(googleAccountSecret);
        InputStream gAccountStream = new ByteArrayInputStream(secretJson.getBytes());
        return GoogleCredential.fromStream(gAccountStream, httpTransport, jsonFactory)
                .createScoped(singletonList(SheetsScopes.SPREADSHEETS_READONLY));
    }

    private GoogleAccountSecret toGoogleAccountSecret(GoogleAccountConfiguration googleAccountConfiguration) {
        GoogleAccountSecret googleAccountSecret = GoogleAccountSecret.builder().build();
        BeanUtils.copyProperties(googleAccountConfiguration, googleAccountSecret);
        return googleAccountSecret;
    }

    @Bean
    public Sheets sheets(GoogleCredential credential,
                         JsonFactory jsonFactory,
                         NetHttpTransport httpTransport) {
        return new Sheets.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @Bean
    public NetHttpTransport netHttpTransport() throws GeneralSecurityException, IOException {
        return GoogleNetHttpTransport.newTrustedTransport();
    }

    @Bean
    public JsonFactory jsonFactory() {
        return GsonFactory.getDefaultInstance();
    }

    @Bean
    public SheetsHelper sheetsHelper(Sheets sheets) {
        return new SheetsHelper(sheets);
    }

}
