package com.flamingo.qa.ui.core;

import com.flamingo.qa.api.core.config.ConfigReader;

public class PlaywrightConfig {

    private PlaywrightConfig() {}

    public static String getBaseUrl() {
        return ConfigReader.get("ui.base.url");
    }

    public static boolean isHeadless() {
        String ciEnv = System.getenv("CI");
        if (ciEnv != null && ciEnv.equals("true")) {
            return true;
        }
        String headless = ConfigReader.get("ui.headless");
        return headless == null || Boolean.parseBoolean(headless);
    }

    public static int getDefaultTimeout() {
        return 30_000;
    }
}