package org.cinestash.infrastructure.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class BrowserLauncher {

    @EventListener(ApplicationReadyEvent.class)
    public void launchBrowser() {
        Logger logger = Logger.getLogger(BrowserLauncher.class.getName());
        String url = "http://localhost:8080";
        String os = System.getProperty("os.name").toLowerCase();

        try {
            ProcessBuilder pb;
            if (os.contains("linux")) {
                pb = new ProcessBuilder("xdg-open", url);
            } else if (os.contains("win")) {
                pb = new ProcessBuilder("cmd", "/c", "start", url);
            } else if (os.contains("mac")) {
                pb = new ProcessBuilder("open", url);
            } else {
                logger.log(Level.INFO, "Unsupported OS for auto-launch: {0}", os);
                return;
            }

            pb.start();

        } catch (IOException e) {
            logger.log(Level.INFO, "Failed to auto-launch browser: {}", e.getMessage());
        }
    }
}