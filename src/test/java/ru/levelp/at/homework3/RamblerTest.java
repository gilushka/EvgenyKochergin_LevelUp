package ru.levelp.at.homework3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class RamblerTest {

    protected static final String RAMBLER_URL = "https://mail.rambler.ru/";
    protected static final String FIRST_MAIL_SUBJECT = "Тема первого письма";
    protected static final String FIRST_MAIL_TEXT = "Текст первого письма";
    private WebDriver driver;

    private WebDriverWait wait;
    Properties properties;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() throws IOException {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
//        driver = HelperClass.getNewDriver();
//        this.wait = new WebDriverWait(driver, Duration.ofMillis(5000));

        properties = new Properties();
        properties.load(new FileReader(new File(String.format("src/test/resources/application.properties"))));

    }

    @Test
    public void firstTaskTest() {
        driver.navigate().to(RAMBLER_URL);

        WebElement iframe = driver.findElement(By.cssSelector("iframe[src*='login']"));
        driver.switchTo().frame(iframe);

        driver.findElement(By.cssSelector("[type=\"email\"]")).sendKeys(properties.getProperty("email.login"));
        driver.findElement(By.cssSelector("[type=\"password\"]")).sendKeys(properties.getProperty("email.password"));
        driver.findElement(By.cssSelector("[type=\"submit\"] span")).click();

//        driver.navigate().to("https://mail.rambler.ru/folder/INBOX/");
        driver.findElement(By.xpath("//span[text()='Входящие']"));

//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Входящие']")));
        String writeButtonPath = "div[class*=\"Header\"] button";
        List elementsCount = driver.findElements(By.cssSelector(writeButtonPath));
        Assertions.assertEquals(1, elementsCount.size(), "Нужная страница не открылась");

        driver.findElement(By.cssSelector(writeButtonPath)).click();
        driver.findElement(By.id("receivers")).sendKeys(properties.getProperty("email.login"));
        driver.findElement(By.id("subject")).sendKeys(FIRST_MAIL_SUBJECT);
        driver.findElement(By.cssSelector("#tinymce div")).sendKeys(FIRST_MAIL_TEXT);
        driver.findElement(By.xpath("//span[text()='Сохранить черновик']")).click();

//        driver.findElement(By.cssSelector("div[aria-label=\"Черновики, папка, свернуто\"]")).click();
        driver.findElement(By.cssSelector("div[aria-label=\"Черновики, папка, свернуто\"] a")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[title=\"Создать шаблон\"]")));
        elementsCount = driver.findElements(By.cssSelector("[class*=\"ns-view-messages-item-wrap\"]"));
        Assertions.assertEquals(1, elementsCount.size(), "Количество сохраненных сообщений не равно 1");

        //Тему письма проверяю тут потому что на странице письма значение в явном виде отсутствует.
        String mailSubject = "span[title=\"" + FIRST_MAIL_SUBJECT + "\"]";
        Assertions.assertTrue(driver.findElements(By.cssSelector(mailSubject)).size() == 1, "Тема письма некорректна");

        driver.findElement(By.cssSelector(mailSubject)).click();
        String to = driver.findElement(By.xpath("//div[@title='Кому']/span")).getAttribute("data-email");
        Assertions.assertEquals(properties.getProperty("email.login"), to, "Адресат в черновике не корректен");

        String text = driver.findElement(By.cssSelector("[title=\"Напишите что-нибудь\"] div")).getText();
        Assertions.assertEquals(FIRST_MAIL_TEXT, text, "Текст письма не совпадает");

//        driver.close();
//        driver = HelperClass.getNewDriver();
//        driver.navigate().to(GOOGLE_URL);
//        driver.findElement(By.cssSelector(".PSHeader-Right button")).click();
//        driver.findElement(By.name("login")).sendKeys(properties.getProperty("email.login"));
//        driver.findElement(By.id("passp:sign-in")).click();
//        driver.findElement(By.name("passwd")).sendKeys(properties.getProperty("email.password"));
//        driver.findElement(By.id("passp:sign-in")).click();
//        driver.findElement(By.cssSelector("div[aria-label=\"Черновики, папка, свернуто\"] a")).click();
//        driver.findElement(By.cssSelector(mailSubject)).click();

        driver.findElement(By.cssSelector(".ComposeSendButton-Text")).click();
        elementsCount = driver.findElements(By.cssSelector("[class*=\"ns-view-messages-item-wrap\"]"));
        Assertions.assertEquals(0, elementsCount.size(), "Количество сохраненных сообщений не равно 0");

    }

//    @AfterEach
    void shutDown() {
        driver.close();
    }
}
