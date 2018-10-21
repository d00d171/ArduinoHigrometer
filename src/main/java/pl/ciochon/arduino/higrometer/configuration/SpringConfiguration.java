package pl.ciochon.arduino.higrometer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.ciochon.arduino.higrometer.connection.Connection;
import pl.ciochon.arduino.higrometer.messageprocessor.AverageValuesCalculator;
import pl.ciochon.arduino.higrometer.connection.SerialListener;
import pl.ciochon.arduino.higrometer.messageprocessor.MessageLogger;
import pl.ciochon.arduino.higrometer.support.ContextRefreshedEventListener;

/**
 * Created by Konrad Ciochoń on 2018-10-19.
 */
@Configuration
public class SpringConfiguration {

    @Bean
    public ContextRefreshedEventListener contextRefreshedEventListener(){
        return new ContextRefreshedEventListener();
    }

    @Bean
    public Connection connection(){
        return new Connection();
    }

    @Bean
    public SerialListener serialListener(){
        return new SerialListener();
    }

    @Bean
    @Profile("AVERAGE")
    public AverageValuesCalculator averageValuesCalculator(){
        return new AverageValuesCalculator();
    }

    @Bean
    @Profile("LOGGER")
    public MessageLogger messageLogger(){
        return new MessageLogger();
    }

}

