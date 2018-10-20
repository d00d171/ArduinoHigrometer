package pl.ciochon.arduino.higrometer.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import pl.ciochon.arduino.higrometer.connection.Connection;
import pl.ciochon.arduino.higrometer.connection.SerialListener;

/**
 * Created by Konrad Ciochoń on 2018-10-19.
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private Connection connection;

    @Autowired
    private SerialListener serialListener;

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        connection.addEventListener(serialListener);
    }

}
