package com.example.Challenge_4.mvc.testing;


import com.example.Challenge_4.mvc.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingRestTemplate {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    public UserService userService;

    @Test
    public void listSukses() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");


        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8081/api/merchants/list-spec?page=0&size=5&open=true", HttpMethod.GET, null, String.class);
        System.out.println("response  =" + exchange.getBody()); //JACKSON Parsing
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }

    @Test
    public void getIdSukses() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");
        UUID id = UUID.fromString("b4f2f661-60af-453d-b293-a51d9b6c0948");
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8081/api/merchants/" + id, HttpMethod.GET, null, String.class);
        System.out.println("response  =" + exchange.getBody());
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }

    @Test
    public void saveSukses() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");
        String bodyTesting = "{\n" +
                "    \"merchant_name\": \"Bca_Syariah\",\n" +
                "    \"merchant_location\": \"Bekasi\",\n" +
                "    \"open\": true \n" +
                "}";

        /*
        cara : Map
         */
        HttpEntity<String> entity = new HttpEntity<String>(bodyTesting, headers);
        System.out.println("bodyTesting  =" + bodyTesting);
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8081/api/merchants/save", HttpMethod.POST, entity, String.class);
        System.out.println("response  =" + exchange.getBody());
        assertEquals(HttpStatus.OK, exchange.getStatusCode());

        String jsonResponse = exchange.getBody();
        //Step 1
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        //Step 2 : apa objek akan kita ambil
        String message = jsonNode.get("success").asText();
        JsonNode data = jsonNode.get("data");
        System.out.println("data dto ="+message);
        System.out.println("data dto ="+data);

    }

    @Test
    public void listSpecSukses() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        String url = "http://localhost:8081/api/merchants/list-spec?page=0&size=5&open=true";
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        System.out.println("Response: " + exchange.getBody());
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }


}

