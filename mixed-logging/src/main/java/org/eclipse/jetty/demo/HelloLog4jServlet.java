package org.eclipse.jetty.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class HelloLog4jServlet extends HttpServlet
{
    private static final Logger LOG = Logger.getLogger(HelloLog4jServlet.class);
    private final String msg;
    
    public HelloLog4jServlet(String msg)
    {
        this.msg  = msg;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        LOG.info(String.format(
                "Got request from %s for %s",
                request.getRemoteAddr(), request.getRequestURL()));
        response.setContentType("text/plain");
        response.getWriter().printf("%s%n",msg);
    }
}
