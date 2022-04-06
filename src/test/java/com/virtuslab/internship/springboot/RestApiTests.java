package com.virtuslab.internship.springboot;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestApiTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void checkCartLinkWorking() {

        //        Given
        String cartLink = "http://localhost:" + port + "/cart";
        String receiptLink = "http://localhost:" + port + "/generateReceipt";
        String discountedLink = "http://localhost:" + port + "/generateReceipt/OwnerDiscount";
        String cartResponseShouldContain = "\"_links\":{\"self\":{\"href\":\"http://localhost:"+port+"/cart\"}}";
        String receiptResponseShouldContain = "\"_links\":{\"self\":{\"href\":\"http://localhost:"+port+"/generateReceipt\"}}";
        String discountedResponseShouldContain = "\"_links\":{\"self\":{\"href\":\"http://localhost:"+port+"/generateReceipt/OwnerDiscount\"}}";

        //        When
        String cartResponse = this.restTemplate.getForObject(cartLink, String.class);
        String receiptResponse = this.restTemplate.getForObject(receiptLink, String.class);
        String discountedResponse = this.restTemplate.getForObject(discountedLink, String.class);

        //        Then
        assertTrue(cartResponse.contains(cartResponseShouldContain));
        assertTrue(receiptResponse.contains(receiptResponseShouldContain));
        assertTrue(discountedResponse.contains(discountedResponseShouldContain));

    }
    @Test
    public void checkAddProduct(){
//        Given
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request =
                new HttpEntity<>("Cereals", headers);
        String url = "http://localhost:" + port + "/addProductToCart";
        String shouldContain = "\"name\":\"Cereals\"";
//        When
        this.restTemplate.postForObject(url ,request, String.class);

//        Then
        String cartAfter = this.restTemplate.getForObject("http://localhost:" + port + "/cart", String.class);
        assertTrue(cartAfter.contains(shouldContain));
    }

}



