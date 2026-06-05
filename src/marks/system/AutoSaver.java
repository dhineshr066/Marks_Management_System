package marks.system;

import java.sql.Connection;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * AutoSaver implements Runnable and periodically writes a simple heartbeat/log entry.
 * This demonstrates adding threading functionality without altering existing logic.
 */
public class AutoSaver implements Runnable {
    private final Connection conn;
    private volatile boolean running = true;

    public AutoSaver(Connection conn) {
        this.conn = conn;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("autosave_log.txt", true))) {
            while (running) {
                // Minimal operation: write a timestamp line to a log file.
                pw.println("Auto-save heartbeat: connection alive at " + System.currentTimeMillis());
                pw.flush();
                try {
                    Thread.sleep(10000); // sleep 10 seconds between heartbeats
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            pw.println("Auto-saver stopped.");
            pw.flush();
        } catch (Exception e) {
            // avoid throwing to caller thread - log locally
            System.err.println("AutoSaver error: " + e.getMessage());
        }
    }
}
