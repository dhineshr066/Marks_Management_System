package marks.system;

import java.sql.Connection;

/**
 * ThreadManager starts/stops the AutoSaver thread.
 */
public class ThreadManager {
    private final AutoSaver saver;
    private Thread thread;

    public ThreadManager(Connection conn) {
        this.saver = new AutoSaver(conn);
    }

    public void startBackground() {
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(saver, "AutoSaverThread");
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void stopBackground() {
        saver.stop();
        if (thread != null) {
            thread.interrupt();
        }
    }
}
