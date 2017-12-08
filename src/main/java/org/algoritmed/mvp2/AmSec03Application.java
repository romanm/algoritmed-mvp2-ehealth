package org.algoritmed.mvp2;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.algoritmed.mvp2.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Controller
@ImportResource("classpath:config-app-spring.xml")
public class AmSec03Application  implements WebMvcConfigurer{

	@Autowired	CommonUtil commonUtil;
	
	@GetMapping("/r/test")
	public @ResponseBody Map<String, Object>  test() {
		Map<String, Object> model = commonUtil.test();
		return model;
	}

	@PostMapping("/r/posttest2")
	public @ResponseBody Map<String, Object> posttest2(
			@RequestBody Map<String, Object> data
			,HttpServletRequest request
			) {
		System.err.println("/r/posttest2");
		System.err.println("data");
		System.err.println(data);
		System.err.println(request.getParameterMap().keySet());
		return data;
	}

	
	@PostMapping(value="/r/posttest",  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String posttest(
			HttpServletRequest request
			) {
		System.err.println("data");
		System.err.println(request.getParameterMap().keySet());
		System.err.println(request.getParameter("_csrf"));
		System.err.println(request.getParameter("name"));
		return "redirect:/";
	}

	@GetMapping("/")
	public String home(Map<String, Object> model) {
		Map<String, Object> test = test();
		model.putAll(test);
		return "home";
	}

	@RequestMapping("/foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/v/h1").setViewName("home1");
		registry.addViewController("/v/h2").setViewName("home2");
	}

	@Configuration
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
			http.authorizeRequests()
			.antMatchers(
					"/"
					,"/f/**"
					,"/v/**"
					,"/r/**"
					,"/webjars/**"
					).permitAll().anyRequest()
			.fullyAuthenticated().and().formLogin().loginPage("/login")
			.failureUrl("/login?error").permitAll().and().logout().permitAll();
		}

		@Bean
		public InMemoryUserDetailsManager InMemoryUserDetailsManager() {
			return new InMemoryUserDetailsManager(User.withDefaultPasswordEncoder()
					.username("user").password("user").roles("USER").build());
		}

	}

	public static void main1(String[] args) {
		SpringApplication.run(AmSec03Application.class, args);
	}


	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(AmSec03Application.class).run(args);
	}

}
