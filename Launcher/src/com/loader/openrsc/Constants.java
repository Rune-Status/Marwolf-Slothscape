package com.loader.openrsc;

public class Constants {

    // Basic information
    public static final String GAME_NAME = "Slothscape";
    public static final String SERVER_DOMAIN = "game.slothscape.com"; // Only used for the server status display
    public static final int SERVER_PORT = 43594;

    // Cache
    public static final String BASE_URL = "https://game.slothscape.com/"; // Cache and client jar download locations depend on this
    public static final String CONF_DIR = "Cache";
    public static final String CLIENT_FILENAME = "Slothscape_Client.jar";
    public static final String CACHE_URL = BASE_URL + "downloads/cache/";

    // Launcher version checking
    public static final Double VERSION_NUMBER = 20181109.230000; //YYYYMMDD.HHMMSS format
    public static final String VERSION_UPDATE_URL = "https://raw.githubusercontent.com/open-rsc/Slothscape/master/Launcher/src/com/loader/openrsc/Constants.java";
    public static final String UPDATE_JAR_URL = "https://game.slothscape.com/downloads/Slothscape.jar";
    public static final String JAR_FILENAME = "Slothscape.jar";
}
