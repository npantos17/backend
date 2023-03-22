package com.example.backend.resource;

import com.example.backend.entities.user.LoginRequest;
import com.example.backend.entities.user.User;
import com.example.backend.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allUsers() {
        return Response.ok(this.userService.allUsers()).build();
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findUser(@PathParam("id") Integer userId){
        return this.userService.findUser(userId);
    }

    @GET
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findUserByEmail(@PathParam("email") String email){
        return this.userService.findUserByEmail(email);
    }

    @GET
    @Path("/page/{num}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> findUserByEmail(@PathParam("num") Integer num){
        return this.userService.usersByPage(num);
    }


    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
//    public Response login(@Valid LoginRequest loginRequest) {
    public Response login(LoginRequest loginRequest) {
        Map<String, String> response = new HashMap<>();

        String jwt = this.userService.login(loginRequest.getEmail() , loginRequest.getPassword());
        if (jwt == null) {
            response.put("message", "No such email and password combination");
            return Response.status(422, "Unprocessable entity").entity(response).build();
        }
        response.put("jwt", jwt);

        return Response.ok(response).build();
    }


}
