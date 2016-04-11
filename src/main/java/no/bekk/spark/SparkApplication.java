package no.bekk.spark;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class SparkApplication {

    private static final Logger logger = Logger.getLogger(SparkApplication.class);

    public static void main(String[] args) throws Exception {
        Server server = new Server(getPort());
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(CustomWebSocketServlet.class, "/ws");

        contextHandler.setResourceBase(SparkApplication.class.getClassLoader().getResource("src/main/webapp").toExternalForm());
        DefaultServlet staticServlet = new DefaultServlet();
        contextHandler.addServlet(staticServlet.getClass(), "/");
        server.setHandler(contextHandler);

        server.start();

        TwitterApp.run();
    }

    private static int getPort() {
            return 8888;
    }
}
