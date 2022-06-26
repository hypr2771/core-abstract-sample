package com.example.demo.controller;

import com.example.demo.dto.in.ShoeFilter;
import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Shoes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@DisplayName("Test legacy vs new core")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoreLegacyVsNewTest {


        private RestTemplate restTemplate;

        private  Shoes newCoreDefaultShoes = Shoes.builder()
                .shoes(List.of(Shoe.builder()
                        .name("New shoe")
                        .color(ShoeFilter.Color.BLUE)
                        .size(BigInteger.ONE)
                        .build()))
                .build();

        @LocalServerPort
        int randomServerPort;


        @Before
        public void initLog(){

            ClientHttpRequestFactory factory =
                    new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
            this.restTemplate = new RestTemplate(factory);

            List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
            if (CollectionUtils.isEmpty(interceptors)) {
                interceptors = new ArrayList<>();
            }
            interceptors.add(new LoggingInterceptor());
            restTemplate.setInterceptors(interceptors);
        }

        @DisplayName("Test: legacy on '/shoes/search'")
        @Test
        public void testLegacyCore()
        {
            final String url = "http://localhost:"+randomServerPort+"/shoes/search";
            Shoes expectedShoes = Shoes.builder()
                    .shoes(List.of(Shoe.builder()
                            .name("Legacy shoe")
                            .color(ShoeFilter.Color.BLUE)
                            .size(BigInteger.ONE)
                            .build()))
                    .build();


            HttpHeaders headers = new HttpHeaders();
            headers.set("version", "1");

            HttpEntity<Shoes> request = new HttpEntity<>(headers);

            // make an HTTP GET request with headers
            ResponseEntity<Shoes> result = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    Shoes.class
            );

            //Verify http code
            Assert.assertEquals("Http code",200,result.getStatusCode().value());
            //Verify shoes
            Assert.assertEquals("Legacy shoes",expectedShoes, result.getBody());
        }

    @DisplayName("Test: get on new core the default shoe")
    @Test
    public void testNewCoreDefault()
    {
        final String url = "http://localhost:"+randomServerPort+"/shoes/search";
        Shoes expectedShoes = Shoes.builder()
                .shoes(List.of(Shoe.builder()
                    .name("New shoe")
                    .color(ShoeFilter.Color.BLACK)
                    .size(BigInteger.TWO)
                    .build()))
               .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("version", "2");

        HttpEntity<Shoes> request = new HttpEntity<>(headers);

        // make an HTTP GET request with headers
        ResponseEntity<Shoes> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                Shoes.class
        );

        //Verify http code
        Assert.assertEquals("Http code",200,result.getStatusCode().value());
        //Verify shoes
        Assert.assertEquals("Legacy shoes",expectedShoes, result.getBody());
    }

    @DisplayName("Test: get on new core the filtered shoe")
    @Test
    public void testNewCoreWithFilter() {
        final String url = "http://localhost:" + randomServerPort + "/shoes/search";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("size", BigInteger.ONE)
                .queryParam("color", ShoeFilter.Color.BLUE);
        String uriBuilder = builder.build().encode().toUriString();


        Shoes expectedShoes = Shoes.builder()
                .shoes(List.of(Shoe.builder()
                        .name("New shoe")
                        .color(ShoeFilter.Color.BLUE)
                        .size(BigInteger.ONE)
                        .build()))
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("version", "2");

        HttpEntity<Shoes> request = new HttpEntity<>(headers);

        // make an HTTP GET request with headers
        ResponseEntity<Shoes> result = restTemplate.exchange(
                uriBuilder,
                HttpMethod.GET,
                request,
                Shoes.class
        );

        //Verify http code
        Assert.assertEquals("Http code",200,result.getStatusCode().value());
        //Verify shoes
        Assert.assertEquals("Legacy shoes",expectedShoes, result.getBody());
    }

    @DisplayName("Test: get on new core the filtered shoe with a bad argument")
    @Test
    public void testNewCoreWithDabFilter()
    {
        final String url = "http://localhost:"+randomServerPort+"/shoes/search";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("size", BigInteger.ONE)
                .queryParam("BadColor", ShoeFilter.Color.BLUE);
        String uriBuilder = builder.build().encode().toUriString();

        // with bad parameters expected shoes will be the default
        Shoes expectedShoes = Shoes.builder()
                .shoes(List.of(Shoe.builder()
                        .name("New shoe")
                        // Due to bad parameter the color stays in default color (black)
                        .color(ShoeFilter.Color.BLACK)
                        .size(BigInteger.ONE)
                        .build()))
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("version", "2");

        HttpEntity<Shoes> request = new HttpEntity<>(headers);

        // make an HTTP GET request with headers
        ResponseEntity<Shoes> result = restTemplate.exchange(
                uriBuilder,
                HttpMethod.GET,
                request,
                Shoes.class
        );

        //Verify http code
        Assert.assertEquals("Http code",200,result.getStatusCode().value());
        //Verify shoes
        Assert.assertEquals("Legacy shoes",expectedShoes, result.getBody());
    }
}
