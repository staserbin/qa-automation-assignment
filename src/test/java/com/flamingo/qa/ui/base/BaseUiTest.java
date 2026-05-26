package com.flamingo.qa.ui.base;

import com.flamingo.qa.ui.core.BrowserManager;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.io.ByteArrayInputStream;

public class BaseUiTest {

    protected BrowserManager browserManager;
    protected Page page;

    @BeforeEach
    void setUpBrowser() {
        browserManager = new BrowserManager();
        browserManager.init();
        page = browserManager.getPage();
    }

    @AfterEach
    void tearDownBrowser(TestInfo testInfo) {
        if (page != null) {
            byte[] screenshot = browserManager.takeScreenshot();
            Allure.addAttachment(
                    "Screenshot - " + testInfo.getDisplayName(),
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    "png"
            );
        }
        browserManager.close();
    }
}