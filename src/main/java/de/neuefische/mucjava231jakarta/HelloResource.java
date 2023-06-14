package de.neuefische.mucjava231jakarta;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

// Endpunkt den wir ansprechen
@Path("/hello")
public class HelloResource {

    // GET /api/hello-world
    @GET
    // Was gibt man dem Client zurück? (Rückgabetyp) -> In dem Fall einfach einen String
    @Produces("text/plain")
    public String hello() {
        return "Hello, Jaro!";
    }

    // Was können wir noch machen?
    @GET
    @Path("/{name}/{age}")
    @Produces("text/plain")
    public String greetSpecificPerson(@PathParam("name") String name, @PathParam("age") String age) {
        return "Hello geschätzter Gitarren Fan... " + name + " .. du bischt " + age +  " Jahre alt";
    }

    @GET
    @Path("/to")
    @Produces(MediaType.APPLICATION_JSON) // <- MUSS JSON sein
    // http://localhost:8081/guitarshop/api/hello/to?name=erum&age=25
    public Person greetSpecificPersonWithQueryParams(@QueryParam("name") String name, @QueryParam("age") int age) {
        return new Person(name, age);
    }


    // POST PUT DELETE
}