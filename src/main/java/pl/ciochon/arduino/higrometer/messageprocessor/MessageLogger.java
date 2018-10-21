package pl.ciochon.arduino.higrometer.messageprocessor;

import org.apache.log4j.Logger;
import pl.ciochon.arduino.higrometer.connection.MessageProcessor;

/**
 * Created by Konrad Ciochoń on 2018-10-21.
 */
public class MessageLogger implements MessageProcessor{

    private static final Logger logger = Logger.getLogger(MessageLogger.class);

    @Override
    public void process(String input) {
        logger.info(input);
    }
}
