package com.example.restfulwebservice.user;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;


@RestController
public class UserJPAResource {

	
	@Autowired
	private UserDAOService service;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/jpa/users")
	protected List<User> retrieveUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) throws Exception {
		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent())
			throw new Exception("id-" + id);

		// "all-users", SERVER_PATH + "/users"
		// retrieveAllUsers
		EntityModel<User> resource = EntityModel.of(user.get());//new EntityModel<User>(user.get());

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveUsers());

		resource.add(linkTo.withRel("all-users"));

		// HATEOAS

		return resource;
	}
	
	@PostMapping("/jpa/users")
	private ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		
		return ResponseEntity.created(ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedUser.getId())
					.toUri()).build(); // to send created 201 status code

	}
	
	@DeleteMapping("/jpa/users/{id}")
	private void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	protected List<Post> retrievePosts(@PathVariable int id) throws Exception {
		Optional<User> userOptional = userRepository.findById(id);
		
		if (!userOptional.isPresent())
			throw new Exception("id-" + id);
		return userOptional.get().getPost();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	private ResponseEntity<Object> createPost(@PathVariable int id,@RequestBody Post post) throws Exception {
		
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent())
			throw new Exception("id-" + id);
		
		User user = userOptional.get();
		post.setUser(user);
		postRepository.save(post); 
		
		return ResponseEntity.created(ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(post.getId())
					.toUri()).build(); // to send created 201 status code

	}
	
	
}
