package pl.ciochon.arduino.higrometer.connection;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.util.List;

/**
 * Created by Konrad Ciochoń on 2018-10-19.
 */
public class SerialListener implements SerialPortEventListener {

    private static final Logger logger = Logger.getLogger(SerialListener.class);

    private BufferedReader input;

    @Autowired
    private List<MessageProcessor> messageProcessors;

    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputValue = input.readLine();
                logger.trace("Read line: " + inputValue);
                messageProcessors.stream().forEach(messageProcessor -> {
                    try {
                        messageProcessor.process(inputValue);
                    } catch (Exception e){
                        logger.error("Error processing message by processor: " + messageProcessor.getClass());
                    }
                });
            } catch (Exception e) {
                logger.error("Error reading serial", e);
            }
        }
    }

    @Autowired
    public void setConnection(Connection connection) {
        this.input = connection.getInput();
    }
}