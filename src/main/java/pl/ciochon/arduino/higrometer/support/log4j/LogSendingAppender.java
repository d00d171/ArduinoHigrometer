package pl.ciochon.arduino.higrometer.support.log4j;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Konrad Ciochoń on 2018-10-21.
 */
@Plugin(
        name = "LogSendingAppender",
        category = Core.CATEGORY_NAME,
        elementType = Appender.ELEMENT_TYPE)
public class LogSendingAppender extends DailyRollingFileAppender {

    private static final Logger logger = Logger.getLogger(LogSendingAppender.class);

    @Override
    protected void subAppend(LoggingEvent event) {
        super.subAppend(event);

        Collection<File> files = getFiles();
        if(!files.isEmpty()) {
            try {
                MailSender.generateAndSendEmail((files).stream().map(File::getName).collect(Collectors.toList()));
                removeFiles(files);
                logger.info("Email sent");
            } catch (MessagingException | IOException e) {
                logger.error("Error sending email", e);
            }
        }
    }

    private void removeFiles(Collection<File> fileNames) throws IOException {
        for (File file : fileNames) {
            Files.delete(file.toPath());
        }
    }

    private Collection<File> getFiles(){
        File dir = new File(".");
        File [] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches("app.log\\d{4}-\\d{2}-\\d{2}.*");
            }
        });
        return Arrays.asList(files);
    }

}
