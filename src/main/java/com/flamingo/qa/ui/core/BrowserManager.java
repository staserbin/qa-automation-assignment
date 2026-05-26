package com.flamingo.qa.ui.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import lombok.Getter;

public class BrowserManager {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    @Getter
    private Page page;

    public void init() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(PlaywrightConfig.isHeadless())
        );
        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(1920, 1080)
        );
        context.setDefaultTimeout(PlaywrightConfig.getDefaultTimeout());
        page = context.newPage();
    }

    public void close() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    public byte[] takeScreenshot() {
        return page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
    }
}