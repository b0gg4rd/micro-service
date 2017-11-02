
package net.coatli.java;

import com.networknt.config.Config;
import com.networknt.server.HandlerProvider;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Methods;
import com.networknt.info.ServerInfoGetHandler;
import com.networknt.health.HealthGetHandler;
import net.coatli.java.handler.*;

public class PathHandlerProvider implements HandlerProvider {
    @Override
    public HttpHandler getHandler() {
        return Handlers.routing()
        
            .add(Methods.GET, "/api/v1/persons/", new PersonsGetHandler())
        
            .add(Methods.GET, "/api/v1/health", new HealthGetHandler())
        
            .add(Methods.GET, "/api/v1/server/info", new ServerInfoGetHandler())
        
        ;
    }
}
