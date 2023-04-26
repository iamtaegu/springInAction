package com.bccard.vcn.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.HttpWebHandlerAdapter;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer { //implements WebApplicationInitializer {

   /* @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Create the Spring MVC application context
        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
        mvcContext.register();

        // Create the Spring WebFlux application context
        AnnotationConfigWebApplicationContext webfluxContext = new AnnotationConfigWebApplicationContext();
        webfluxContext.register();

        // Create and configure the DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setApplicationContext(mvcContext);

        // Register the DispatcherServlet with the ServletContext
        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/mvc/*");

        // Create and configure the WebFluxHandler
        WebHandler webHandler = WebHttpHandlerBuilder.applicationContext(webfluxContext).build();

        // Register the WebFluxHandler with the ServletContext
        ServletRegistration.Dynamic webfluxRegistration = servletContext.addServlet("webflux", new HttpWebHandlerAdapter(webHandler));
        webfluxRegistration.setLoadOnStartup(1);
        webfluxRegistration.addMapping("/webflux/*");
    } */
}
