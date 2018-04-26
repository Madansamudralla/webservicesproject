package com.webservicesproject;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.NewCookie;

import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;

public class SessionManagementFilter extends ClientFilter {

    @SuppressWarnings("unchecked")
    @Override
    public ClientResponse handle(ClientRequest request) throws ClientHandlerException {

          ArrayList<NewCookie> cookie = new ArrayList<>();
          Object integrationCookie =
                    ConfigurationManager.getBundle().getObject("api.integration.cookie");

          if (integrationCookie != null) {
               if (integrationCookie instanceof NewCookie)
                    cookie.add((NewCookie) integrationCookie);

               else cookie.addAll((Collection<? extends NewCookie>) integrationCookie);
          } 

StringBuffer buffer = new StringBuffer();
          if (cookie != null) {
               for (NewCookie n : cookie) {
                    buffer.append(n.getName() + "=" + n.getValue() + ";");
               }
          }

          request.getHeaders().putSingle("Cookie", buffer.toString());

          ClientResponse response = getNext().handle(request);

          if (response.getCookies() != null) {
               cookie.addAll(response.getCookies());
               ConfigurationManager.getBundle().setProperty("api.integration.cookie",
                          cookie);
          }
          return response;
    }

} 
