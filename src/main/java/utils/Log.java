package utils;

import org.apache.log4j.Logger;

public class Log {

    public static final Logger L = Logger.getLogger(Log.class);
    public static Log instance = null;

    private Log() {
    }

    public static Log getInstance() {
        if (instance == null)
            synchronized (Log.class) {
                instance = new Log();
            }

        return instance;
    }

    /**
     * @method info
     */
    public void info(String parameter) {
        L.info(parameter);
    }

    /**
     * @method debug
     */
    public void debug(String parameter) {
        L.debug(parameter);
    }

    /**
     * @method warn
     */
    public void warn(String parameter) {
        L.warn(parameter);
    }

    /**
     * @method err
     */
    public void err(String parameter) {
        L.error(parameter);
    }
}
