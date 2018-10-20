package pl.ciochon.arduino.higrometer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.ciochon.arduino.higrometer.configuration.SpringConfiguration;

/**
 * Created by Konrad Ciochoń on 2018-10-19.
 */
public class HigrometerApp {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    }

}
