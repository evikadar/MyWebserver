import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.HashMap;

public class Webserver {
    static HashMap<String, Method> routes = new HashMap<String, Method>();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        for (Method method: Routes.class.getMethods()) {
            if (method.isAnnotationPresent(WebServer.class)) {
                WebServer webServer = method.getAnnotation(WebServer.class);
                System.out.printf("%s is annotated with %s%n", method.getName(), webServer.url());
                routes.put(webServer.url(), method);
                server.createContext(webServer.url(), new AnnotationHandler(webServer.url()));
            }
        }
        server.setExecutor(null);
        server.start();
    }


    private static class AnnotationHandler implements HttpHandler {
        private String url;

        public AnnotationHandler(String url) {
            this.url = url;
        }

        public void handle(HttpExchange httpExchange) throws IOException {
            Method theMethod = routes.get(url);
            String response = null;
            try {
                response = (String) theMethod.invoke(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();

        }
    }
}
