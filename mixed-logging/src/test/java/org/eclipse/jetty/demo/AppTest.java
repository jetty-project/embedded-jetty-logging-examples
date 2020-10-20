//
//  ========================================================================
//  Copyright (c) Mort Bay Consulting Pty Ltd and others.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.eclipse.jetty.demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.IO;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

public class AppTest
{
    private static Server server;
    private static URI serverUri;

    @BeforeClass
    public static void startServer() throws Exception
    {
        LoggingUtil.config();

        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(0); // let it use whatever port thats free
        server.addConnector(connector);

        // add handlers
        ServletContextHandler contexts = new ServletContextHandler();
        contexts.setContextPath("/");
        server.setHandler(contexts);

        contexts.addServlet(new ServletHolder(new HelloCommonsLoggingServlet("Hello commons-logging")),"/clogging/*");
        contexts.addServlet(new ServletHolder(new HelloJettyServlet("Hello Jetty")),"/jetty/*");
        contexts.addServlet(new ServletHolder(new HelloJULServlet("Hello JUL")),"/jul/*");
        contexts.addServlet(new ServletHolder(new HelloLog4jServlet("Hello Log4j")),"/log4j/*");
        contexts.addServlet(new ServletHolder(new HelloSlf4jServlet("Hello Slf4j")),"/slf4j/*");

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
    public void testGetAll() throws IOException
    {
        String loggername = "org.eclipse.jetty.demo";
        CaptureHandler capture = CaptureHandler.attach(loggername);

        List<String> paths = new ArrayList<>();
        paths.add("/clogging/");
        paths.add("/jetty/");
        paths.add("/jul/");
        paths.add("/log4j/");
        paths.add("/slf4j/");

        try
        {
            // Request each path
            for (String path : paths)
            {
                URI getURI = serverUri.resolve(path);

                HttpURLConnection connection = (HttpURLConnection)getURI.toURL().openConnection();
                Assert.assertThat("Connection.statusCode",connection.getResponseCode(),is(HttpURLConnection.HTTP_OK));
                try (InputStream in = connection.getInputStream())
                {
                    String response = IO.toString(in);
                    Assert.assertThat("response",response,containsString("Hello"));
                }
            }

            // Validate log entries
            for (String path : paths)
            {
                capture.assertContainsRecord("org.eclipse.jetty.demo.",path);
            }
        }
        finally
        {
            capture.detach(loggername);
        }
    }
}
