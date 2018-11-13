
package com.registel.rdw.controladores;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MySessionListener implements HttpSessionListener {
    
    
  private static int sessions;

  public static int getTotalNoOfActiveSession() {
    return sessions;
  }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        sessions++;
        System.out.println("Se ha creado una sesion");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        sessions--;
        System.out.println("Se ha destruido una sesion");
    }
    
}
