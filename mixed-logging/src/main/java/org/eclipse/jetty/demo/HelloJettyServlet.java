package org.eclipse.jetty.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

@SuppressWarnings("serial")
public class HelloJettyServlet extends HttpServlet
{
    private static final Logger LOG = Log.getLogger(HelloJettyServlet.class);
    private final String msg;
    
    public HelloJettyServlet(String msg)
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
