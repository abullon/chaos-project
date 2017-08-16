package com.fiveadvantages.chaos.server;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import io.advantageous.qbit.server.EndpointServerBuilder;
import io.advantageous.qbit.server.ServiceEndpointServer;
import om.fiveadvantages.chaos.service.CuponService;
import org.slf4j.LoggerFactory;

public class Launcher {

    public static void main(String... args) {
        configLogLevel();
        ServiceEndpointServer server = new EndpointServerBuilder().setPort(9091).build();
        server.initServices(new CuponService());
        server.start();
    }

    private static void configLogLevel() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        PatternLayoutEncoder ple = new PatternLayoutEncoder();
        try {

            ple.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n");
            ple.setContext(lc);
            ple.start();
            FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
            String filePath = Launcher.class.getProtectionDomain().getCodeSource().getLocation().toString() + "Monitor.log";
            System.out.println("FILEPATH :" + filePath);
            fileAppender.setFile(filePath);
            fileAppender.setEncoder(ple);
            fileAppender.setContext(lc);
            fileAppender.start();
            Logger logger = (Logger) LoggerFactory.getLogger("MonitorLOG");
            logger.addAppender(fileAppender);
            logger.setLevel(Level.INFO);
            logger.setAdditive(true);

            Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            root.setLevel(Level.INFO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
