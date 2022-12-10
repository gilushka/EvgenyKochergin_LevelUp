package ru.levelp.at.homework3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class MailRuTest {

    protected static final String MAIL_RU_URL = "https://mail.ru/";
    private WebDriver driver;

    private WebDriverWait wait;
    Properties properties;

    @BeforeEach
    void setUp() throws IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
        this.wait = new WebDriverWait(driver, Duration.ofMillis(5000));

        properties = new Properties();
        properties.load(new FileReader(new File(String.format("src/test/resources/application.properties"))));

    }

    @Test
    public void firstTaskTest() {
        driver.navigate().to(MAIL_RU_URL);

        driver.findElement(By.cssSelector("div.mailbox button")).click();

        WebElement frame = driver.findElement(By.cssSelector("iframe.ag-popup__frame__layout__iframe"));
//        WebDriver frameDriver =
        driver.switchTo().frame(frame);

        driver.findElement(By.name("username")).sendKeys(properties.getProperty("email.adminLogin"));
        driver.findElement(By.cssSelector("#root [type='submit']")).click();
        driver.findElement(By.name("password")).sendKeys(properties.getProperty("email.password"));
        driver.findElement(By.cssSelector("[data-test-id='submit-button']")).click();

//        driver.switchTo().activeElement().findElement(By.cssSelector("div:contains['Сделать Mail стартовой?']"));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-level='enkochergin83@mail.ru']")));
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Сделать Mail стартовой?']")));
        WebElement element = driver.findElement(By.cssSelector("svg[class*='close-icon']"));
        element.click();

        List elementsNumber = driver.findElements(By.cssSelector("tbody [title='Входящие']"));
        Assertions.assertEquals(1, elementsNumber.size(), "Нужная страница не открылась");
    }

//    @AfterEach
    void shutDown() {
        driver.close();
    }
}
