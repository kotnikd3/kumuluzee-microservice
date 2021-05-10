package si.kotnik.api.v1;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "Nakupovalni seznami API",
        version = "v1",
        contact = @Contact(email = "denis.kotnik@gmail.com"), license = @License(name = "dev"), description = "API za nakupovalni seznam"),
        servers = @Server(url = "http://localhost:8080/"))
@ApplicationPath("v1")
public class NakupovalniSeznamiApplication extends Application {
}
