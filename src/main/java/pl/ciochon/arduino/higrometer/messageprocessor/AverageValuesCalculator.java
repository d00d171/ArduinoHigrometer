package pl.ciochon.arduino.higrometer.messageprocessor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import pl.ciochon.arduino.higrometer.connection.MessageProcessor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Created by Konrad Ciochoń on 2018-10-19.
 */
public class AverageValuesCalculator implements MessageProcessor {

    private static final Logger logger = Logger.getLogger(AverageValuesCalculator.class);

    private LocalTime lastSaveTime;

    private List<Double> humidities = new ArrayList<>();

    private List<Double> temps = new ArrayList<>();

    @Value("${average.interval:30}")
    private int averageInterval;

    public void process(String inputValue) {
        if(lastSaveTime == null){
            lastSaveTime = LocalTime.now();
        }
        saveValues(inputValue);
        if(halfHourPassed()){
            persistAveragesAndReset();
        }
    }

    private void persistAveragesAndReset(){
        Double avgHumidity = getAverageAndReset(humidities);
        Double avgTemp = getAverageAndReset(temps);
        logger.info(String.format("avg humidity: %f, \t avg temperature: %f", avgHumidity, avgTemp));
        lastSaveTime = LocalTime.now();
    }

    private void saveValues(String inputValue){
        String[] splitValues = inputValue.split(";");
        Double humidity = Double.parseDouble(splitValues[0]);
        Double temp = Double.parseDouble(splitValues[1]);
        humidities.add(humidity);
        temps.add(temp);
    }

    private Double getAverageAndReset(List<Double> values){
        Double result = values.stream().mapToDouble(a -> a).average().getAsDouble();
        values.clear();
        return result;
    }

    private boolean halfHourPassed(){
        return Math.abs(MINUTES.between(lastSaveTime, LocalTime.now())) >= averageInterval;
    }
}
