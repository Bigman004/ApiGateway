package com.example.api_gateway.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class ModifiedRequest extends HttpServletRequestWrapper {
    private Map<String, String> customHeaders = new HashMap<>();
    public ModifiedRequest(HttpServletRequest request) {
        super(request);
    }
    public void addHeader(String name, String value){
        customHeaders.put(name, value);
    }
    @Override
    public String getHeader(String name) {
        if(customHeaders.containsKey(name)){
            return customHeaders.get(name);
        }
        return super.getHeader(name);
    }
    @Override
    public Enumeration<String> getHeaderNames(){
        Set<String> names = new HashSet<>();
        names.addAll(customHeaders.keySet());
        return Collections.enumeration(names);
    }
    @Override
    public Enumeration<String> getHeaders(String name){
        if(customHeaders.containsKey(name)){
            return Collections.enumeration(Arrays.asList(customHeaders.get(name)));
        }
        return super.getHeaders(name);
    }
}
