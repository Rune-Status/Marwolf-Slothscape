package com.loader.openrsc;

public class Constants {

    // Basic information
    public static final String GAME_NAME = "Open RSC";
    public static final String SERVER_DOMAIN = "game.openrsc.com"; // Only used for the server status display
    public static final int SERVER_PORT = 43594;

    // Cache
    public static final String BASE_URL = "https://game.openrsc.com/"; // Cache and client jar download locations depend on this
    public static final String CONF_DIR = "Cache";
    public static final String CLIENT_FILENAME = "Open_RSC_Client.jar";
    public static final String CACHE_URL = BASE_URL + "downloads/cache/";

    // Launcher version checking
    public static final Double VERSION_NUMBER = 20181101.180000; //YYYYMMDD.HHMMSS format
    public static final String VERSION_UPDATE_URL = "https://raw.githubusercontent.com/marwolf/Game/2.0.0/Launcher/src/com/loader/openrsc/Constants.java";
    public static final String UPDATE_JAR_URL = "https://game.openrsc.com/downloads/OpenRSC.jar";
    public static final String JAR_FILENAME = "OpenRSC.jar";
}
