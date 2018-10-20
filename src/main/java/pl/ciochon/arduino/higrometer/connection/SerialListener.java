package pl.ciochon.arduino.higrometer.connection;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;

/**
 * Created by Konrad Ciochoń on 2018-10-19.
 */
public class SerialListener implements SerialPortEventListener {

    private static final Logger logger = Logger.getLogger(SerialListener.class);

    private BufferedReader input;

    @Autowired
    private MeasurmentProcessor measurmentProcessor;

    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputValue = input.readLine();
                measurmentProcessor.process(inputValue);
                logger.trace("Read line: " + inputValue);
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