package ui;

import java.util.logging.Level;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Selenide.closeWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class BaseTest {
    protected static final boolean DEBUG = false;

    public void setUp() {
        WebDriverManager.chromedriver().browserVersion("133.0.6943.98").setup();
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.headless = false;
        Configuration.holdBrowserOpen = false;
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
    }

    @BeforeEach
    public void setUpDriver() {
        setUp();
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }
}