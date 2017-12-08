package org.algoritmed.mvp2;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AmSec03ApplicationTests {

	@Autowired	private TestRestTemplate restTemplate;
	@LocalServerPort private int port;

	@Test
	public void testCss() throws Exception {
		ResponseEntity<String> responseEntity = this.restTemplate
				.getForEntity("/f/css/w3.css", String.class);
		System.err.println("testCss() responseEntity");
		System.err.println(responseEntity.getBody().contains("w3-container"));
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).contains("w3-container");
	}

//	@Test
	public void testLogin() throws Exception {
		HttpHeaders headers = getHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.set("username", "user");
		form.set("password", "user");
		System.err.println("form");
		System.err.println(form);
		ResponseEntity<String> responseEntity = this.restTemplate.exchange("/login",
				HttpMethod.POST, new HttpEntity<>(form, headers), String.class);
		System.err.println("responseEntity");
		System.err.println(responseEntity);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		assertThat(responseEntity.getHeaders().getLocation().toString())
		.endsWith(this.port + "/");
		assertThat(responseEntity.getHeaders().get("Set-Cookie")).isNotNull();
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<String> page = this.restTemplate.getForEntity("/login",
				String.class);
		assertThat(page.getStatusCode()).isEqualTo(HttpStatus.OK);
		String cookie = page.getHeaders().getFirst("Set-Cookie");
		System.err.println("cookie");
		System.err.println(cookie);
		headers.set("Cookie", cookie);
		Pattern pattern = Pattern.compile("(?s).*name=\"_csrf\".*?value=\"([^\"]+).*");
		Matcher matcher = pattern.matcher(page.getBody());
		assertThat(matcher.matches()).as(page.getBody()).isTrue();
		headers.set("X-CSRF-TOKEN", matcher.group(1));
		return headers;
	}

	@Test
	public void testLoginPage() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
		ResponseEntity<String> entity = this.restTemplate.exchange("/login",
				HttpMethod.GET, new HttpEntity<Void>(headers), String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("_csrf");
	}

	@Test
	public void testHome() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
		ResponseEntity<String> entity = this.restTemplate.exchange("/", HttpMethod.GET,
				new HttpEntity<Void>(headers), String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		//		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		//		assertThat(entity.getHeaders().getLocation().toString()).endsWith(this.port + "/login");
	}

}
