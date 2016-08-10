package com.thoughtworks.orderdemo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by rlxie on 8/9/16.
 */
public class APPMain {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8888);

        /**
        String proPath= System.getProperty("user.dir");
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setResourceBase(proPath + "/target/orderdemo/");
        server.setHandler(context);
        **/

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/");
        webAppContext.setResourceBase("src/main/webapp/");


        HandlerCollection handlerCollection = new HandlerCollection();
        handlerCollection.addHandler(webAppContext);
        server.setHandler(handlerCollection);

        //context.addServlet(new ServletHolder(new HelloServlet()), "/*");
        server.start();
        server.join();
    }
}
