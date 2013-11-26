package org.eclipse.jetty.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HelloHandler extends AbstractHandler
{
    private final String msg;
    
    public HelloHandler(String msg)
    {
        this.msg  = msg;
    }
    
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/plain");
        response.getWriter().printf("%s%n",msg);
        baseRequest.setHandled(true);
    }
}
