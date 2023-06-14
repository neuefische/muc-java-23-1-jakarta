package de.neuefische.mucjava231jakarta.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import de.neuefische.mucjava231jakarta.Person;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/hello/*") // * = wildcard = alles was nach /hello/ kommt wird behandelt
public class HelloServlet extends HttpServlet {

    // Für jedes HTTP Verb - also GET POST PUT ...
    // Methode definieren, die das für uns behandelt
    // Diese Methode kommt aus der Elternklasse HttpServlet - wir müssen sie überschreiben, um eigenes Verhalten zu implementieren
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // request-Parameter = Daten die reinkommen
        // response-Parameter = Steuert die Rückgabe des Endpunktes

        // Query Parameter auslesen der "name" heißt
        String queryParamForName = request.getParameter("name");

        // Path Parameter auslesen
        // ...

        // Wir definieren unser Return via den response-Parameter
        response.getWriter().println("Die Antwort auf den QueryParam ist:");
        response.getWriter().println(queryParamForName);

        response.getWriter().println(request.getQueryString()); // age=23&name=peter
        response.getWriter().println(request.getParameter("name")); // peter -> Vorsicht! Wenn der Parameter nicht existiert, wird null zurückgegeben
        response.getWriter().println(request.getRequestURI()); // /guitarshop/hello/meinPfadParam/
        response.getWriter().println(request.getContextPath()); // /guitarshop
        response.getWriter().println(request.getServletPath()); // /hello
        response.getWriter().println(request.getRequestURL()); // http://localhost:8081/guitarshop/hello/meinPfadParam/


        String pathInfo = request.getPathInfo();
        response.getWriter().println("Path Info: " + pathInfo);

        String pathParameter = null;

        if (pathInfo != null && pathInfo.length() > 1) {
            pathParameter = pathInfo.substring(1); // Exclude the leading slash
        }

        if (pathParameter != null) {
            response.getWriter().println("\nPath Parameter: " + pathParameter);
        } else {
            response.getWriter().println("No path parameter found.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // JSON Daten (Text) in ein Objekt umwandeln
//        {
//                "name": "Gute Gerd",
//                "age": 40,
//        }

        // Den Text einlesen -> Das JSON einlesen
        // Vorher haben wir uns eine LeserKlasse beschafft
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;

        // Zeile für Zeile durchgehen und lesen solange es noch Zeilen zu lesen gibt
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        // Wenn wir zu Ende gelesen haben, schließen wir den Leser
        reader.close();

        // ObjectMapper = Klasse die aus einem Text ein passendes Java Objekt baut
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Versucht aus dem Text ein Person-Objekt zu bauen
            Person person = mapper.readValue(requestBody.toString(), Person.class);

            // Objekt in JSON umwandeln und an Client zurückschicken
            response.setContentType("application/json");
            response.getWriter().write(mapper.writeValueAsString(person));
        } catch (UnrecognizedPropertyException e) {
            response.setStatus(400);
            response.getWriter().write("Invalid request body");
        }
    }
}
