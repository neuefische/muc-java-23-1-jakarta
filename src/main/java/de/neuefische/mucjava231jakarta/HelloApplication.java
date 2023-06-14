package de.neuefische.mucjava231jakarta;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

// Der Vorpfad für alle JAX-RS (REST) Endpunkte
@ApplicationPath("/api")
public class HelloApplication extends Application {

}