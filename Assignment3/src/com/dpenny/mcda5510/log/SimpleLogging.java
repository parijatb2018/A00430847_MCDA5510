package com.dpenny.mcda5510.log;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class SimpleLogging {
    private static Logger LOGGER;

    public SimpleLogging() {
        LOGGER = Logger.getLogger(SimpleLogging.class.getName());
        Handler fileHandler = null;
        try {
            fileHandler = new java.util.logging.FileHandler("/Users/parijatbandyopadhyay/Documents/GitHub/A00430847_MCDA5510/Assignment3/logs/Test.log");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.addHandler(fileHandler);
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        fileHandler.setFormatter(simpleFormatter);
    }


    public void writetoLogFile(String logMessage) {
        LOGGER.log(java.util.logging.Level.INFO, logMessage);
    }
}
