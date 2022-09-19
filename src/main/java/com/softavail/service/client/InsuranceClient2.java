package com.softavail.service.client;
import com.softavail.config.ClientConfig;
import com.softavail.dto.InsuranceReportResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.uri.UriBuilder;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Client()
@Slf4j
@RequiredArgsConstructor
public class InsuranceClient2 {
    private static final String VIN = "vin";
    private final HttpClient httpClient;
    private final ClientConfig config;

    public InsuranceReportResponse getInsuranceReport(String vinNumber) throws HttpClientResponseException {
        URI uri = UriBuilder.of(config.getInsurance() +"/accidents/reports").queryParam(VIN,vinNumber).build();
        HttpRequest<?> request = HttpRequest.GET(uri);
        try {
            return httpClient.toBlocking().retrieve(request, InsuranceReportResponse.class);
        } catch (HttpClientResponseException ex) {
            log.error("getInsuranceReport Response Error : ", ex);
            throw ex;
        }
    }
}






