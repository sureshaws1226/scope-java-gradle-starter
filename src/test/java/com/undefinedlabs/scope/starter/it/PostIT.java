package com.undefinedlabs.scope.starter.it;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostIT {

    @LocalServerPort
    private int randomServerPort;

    private final RestTemplate restTemplate = new RestTemplate();
    private String jSessionId;

    @Before
    public void login() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("username", "user");
        map.add("password", "password");

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        final ResponseEntity<String> loginResponse = this.restTemplate.postForEntity("http://localhost:" + randomServerPort + "/login", request, String.class);
        this.jSessionId = loginResponse.getHeaders().get("Set-Cookie").get(0).split(";", 2)[0];
    }

    @Test
    public void get_post() {
        //Given
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Cookie", jSessionId);
        final HttpEntity<MultiValueMap<String, String>> getPostEntity = new HttpEntity<>(null, headers);

        //When
        final ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + randomServerPort + "/post/3", HttpMethod.GET, getPostEntity, String.class);

        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void create_post() {
        //Given
        final MultiValueMap<String, String> newPostAttributes= new LinkedMultiValueMap<>();
        newPostAttributes.add("title", "sampleTitle");
        newPostAttributes.add("body", "sampleBody");
        newPostAttributes.add("user", "1");
        newPostAttributes.add("id", "");

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Cookie", jSessionId);
        final HttpEntity<MultiValueMap<String, String>> newPostEntity = new HttpEntity<>(newPostAttributes, headers);

        //When
        final ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + randomServerPort + "/newPost", newPostEntity, String.class);

        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void edit_post() {
        final MultiValueMap<String, String> newPostAttributes= new LinkedMultiValueMap<>();
        newPostAttributes.add("title", "sampleTitle");
        newPostAttributes.add("body", "sampleBody");
        newPostAttributes.add("user", "1");
        newPostAttributes.add("id", "1");

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Cookie", jSessionId);
        final HttpEntity<MultiValueMap<String, String>> editPostEntity = new HttpEntity<>(newPostAttributes, headers);

        //When
        final ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + randomServerPort + "/newPost", editPostEntity, String.class);

        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void delete_post(){
        //Given
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Cookie", jSessionId);
        final HttpEntity<MultiValueMap<String, String>> deletePostEntity = new HttpEntity<>(null, headers);

        //When
        final ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + randomServerPort + "/post/2", HttpMethod.DELETE, deletePostEntity, String.class);

        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

}
