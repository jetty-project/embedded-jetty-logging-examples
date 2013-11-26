package org.eclipse.jetty.demo;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class HelloJULServlet extends HttpServlet
{
    private static final Logger LOG = Logger.getLogger(HelloJULServlet.class.getName());
    private final String msg;
    
    public HelloJULServlet(String msg)
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
