package com.example.restfulwebservice.helloworld;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class HelloWorld {

	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path="/HelloWorld")
	public String helloworld() {
		return("Hello World");
	}
	
	@GetMapping(path="/HelloWorldBean")
	public HelloWorldBean helloworldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	@GetMapping(path="/HelloWorldBean/path-variable/{name}")
	public HelloWorldBean helloworldBean(@PathVariable String name) {
		return new HelloWorldBean("Hey "+name+"!");
	}
	
	@GetMapping(path = "/hello-world-internationalized")
	public String helloWorldInternationalized() {
		return messageSource.getMessage("good.morning.message",null,LocaleContextHolder.getLocale());
	}
}
