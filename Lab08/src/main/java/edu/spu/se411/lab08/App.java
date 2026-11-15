package edu.spu.se411.lab08;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Application is starting...");

        Sensor temp = new TemperatureSensor();
        Sensor humidity = new HumiditySensor();

        Observer dashboard = new DashboardObserver();
        Observer logObserver = new LoggerObserver();

        temp.register(dashboard);
        temp.register(logObserver);
        humidity.register(dashboard);
        humidity.register(logObserver);

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.printf("%nReading %d%n", i + 1);
            temp.setReading(20 + random.nextDouble() * 15);
            humidity.setReading(40 + random.nextDouble() * 20);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Simulation interrupted", e);
                Thread.currentThread().interrupt();
                break;
            }
        }

        Sensor tempCopy = temp.clone();
        System.out.printf("%nCloned sensor observers: %d%n", tempCopy.getObserverCount());
        tempCopy.register(new DashboardObserver());
        System.out.println("Changing the clone:");
        tempCopy.setReading(35.5);
        System.out.printf("Original temperature is still %.2f%n", temp.getReading());

       
        temp.unregister(dashboard);
        System.out.println("Changing the original after removing its dashboard observer:");
        temp.setReading(36.0);

        logger.info("Application stopped.");
    }
}
