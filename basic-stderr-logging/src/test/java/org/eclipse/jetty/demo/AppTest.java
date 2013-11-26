package org.eclipse.jetty.demo;

import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.IO;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AppTest
{
    private static Server server;
    private static URI serverUri;

    @BeforeClass
    public static void startServer() throws Exception
    {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(0); // let it use whatever port thats free
        server.addConnector(connector);

        // add handler
        server.setHandler(new HelloHandler("Hello Basic"));

        // Start server
        server.start();

        // Establish the Server URI
        String host = connector.getHost();
        if (host == null)
        {
            host = "localhost";
        }
        int port = connector.getLocalPort();
        serverUri = new URI(String.format("http://%s:%d/",host,port));
    }

    @AfterClass
    public static void stopServer()
    {
        try
        {
            server.stop();
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
    }

    @Test
    public void testGet() throws IOException
    {
        URI getURI = serverUri.resolve("/");

        HttpURLConnection connection = (HttpURLConnection)getURI.toURL().openConnection();
        Assert.assertThat("Connection.statusCode",connection.getResponseCode(),is(HttpURLConnection.HTTP_OK));
        try (InputStream in = connection.getInputStream())
        {
            String response = IO.toString(in);
            Assert.assertThat("response", response, containsString("Hello"));
        }
    }
}
