package org.eclipse.jetty.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class HelloCommonsLoggingServlet extends HttpServlet
{
    private static final Log LOG = LogFactory.getLog(HelloCommonsLoggingServlet.class);
    private final String msg;
    
    public HelloCommonsLoggingServlet(String msg)
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
