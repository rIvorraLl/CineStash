/*
 * CineStash
 * Copyright (C) 2026 rIvorraLl [@github.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.cinestash.infrastructure.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Infrastructure component that launches the system's default browser on startup.
 * Automatically points to the application's local URL.
 */
@Component
public class BrowserLauncher {

    /**
     * Listens for the ApplicationReadyEvent and executes the browser launch command.
     */
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
            logger.log(Level.INFO, "Failed to auto-launch browser: {0}", e.getMessage());
        }
    }
}
