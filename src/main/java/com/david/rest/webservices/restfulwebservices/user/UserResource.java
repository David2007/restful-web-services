package com.david.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

// this class is the controller
@RestController
public class UserResource {
    @Autowired
    private UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

//    @GetMapping("/users/{id}")
//    public EntityModel<User> retrieveUser(@PathVariable int id) {
//        User user = service.findOne(id);
//        if (user == null){
//            throw new UserNotFountException("id" + id);
//        }
//        // "all-users", SERVER_PATH + "/users"
//        // create hateoas on the user
//        // we want to link to the retrieveAllUsers uri: currently its /users but could change in the future.
//        // create link: so in future even if retrieveAllUsers uri change, we are fine.
//        EntityModel<User> model = new EntityModel<>(user);
//        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
//        model.add(linkTo.withRel("all-users"));
//        return model;
//    }

    @GetMapping("/users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);
        if (user == null){
            throw new UserNotFountException("id" + id);
        }

        // "all-users", SERVER_PATH + "/users"
        // retrieveAllUsers
        Resource<User> resource = new Resource<User>(user);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));
        return resource;
    }

    // input - details of user
    // output - CREATED & Return the created URI
    @PostMapping(path = "/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);

        // CREATED  status: 201 Created
        // /users/{id}   savedUser.getId()
        // ServletUriComponentsBuilder.fromCurrentRequest() will return "/users"
        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId());
        URI uri = uriComponents.toUri();
        ResponseEntity<Object> responseEntity = ResponseEntity.created(uri).build();
        return responseEntity;
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteById(@PathVariable int id){
        User user = service.deleteById(id);
        if (user == null){
            throw new UserNotFountException("id" + id);
        }

    }
}
