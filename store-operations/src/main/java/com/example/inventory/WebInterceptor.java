package com.example.inventory;

import java.net.InetAddress;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class WebInterceptor implements HandlerInterceptor
{
	/*
	 * TO-DO Need a way to determine how the host target should discerned.
	 * It might be a hostname, a cluster name, a foundation name, a region, etc
	 */
	
    private static final String HOSTNAME_HEADER = "request-target";

    protected String hostName;

    public WebInterceptor()
    {
        hostName = SystemUtils.getHostName();

        if (!StringUtils.hasText(hostName))
        try{
            hostName = InetAddress.getLocalHost().getHostName();
        }
        catch (Exception e) {}
            
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
    {
        response.addHeader(HOSTNAME_HEADER, hostName);

        return true;
    }    
}
