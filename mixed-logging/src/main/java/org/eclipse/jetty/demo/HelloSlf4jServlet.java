package org.eclipse.jetty.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class HelloSlf4jServlet extends HttpServlet
{
    private static final Logger LOG = LoggerFactory.getLogger(HelloSlf4jServlet.class);
    private final String msg;
    
    public HelloSlf4jServlet(String msg)
    {
        this.msg  = msg;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        LOG.info("Got request from {} for {}",request.getRemoteAddr(), request.getRequestURL());
        response.setContentType("text/plain");
        response.getWriter().printf("%s%n",msg);
    }
}
