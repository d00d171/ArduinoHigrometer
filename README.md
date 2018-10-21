# ArduinoHigrometer

1. Prerequisites:
RXTXcomm.jar from here http://rxtx.qbang.org/wiki/index.php/Download and follow instructions.

2. Building:
mvn clean package "-Drxtxcomm.path=Path to RXTXcomm.jar"

3. Running
java -Dport.name=PORT_NAME [-Dgnu.io.rxtx.SerialPorts=PORT_NAME] -jar arduino-higrometer-jar-with-dependencies.jar -Dmail.password=PASSWORD_TO_MAIL -Dmail.user=MAIL_USER -Dmail.address=MAIL_ADDRESS