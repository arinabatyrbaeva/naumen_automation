package ru.dns;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartTests {

    static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1400, 1000));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

    }

    @Test
    public void AddItemToCart() {
        //открываем карточку товара
        driver.get("https://www.dns-shop.ru/product/9c01c392b97ded20/igra-lego-harry-potter-collection-ps4/");

        //добавляем в корзину
        driver.findElement(By.cssSelector(".button-ui_brand")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'В корзине')]")));


        //переходим в корзину
        driver.get("https://www.dns-shop.ru/cart/");

        //проверим, что добавилось в корзину
        WebElement element = driver.findElement(By.cssSelector(".cart-link-counter__badge"));
        String textElement = element.getText();
        String message = String.format("В корзине неверное кол-во товаров. Ожидалось %s. Получили %s.", "1", textElement);
        Assertions.assertEquals("1", textElement, message);

        //очистим корзину
        driver.findElement(By.cssSelector(".count-buttons__button_minus")).click();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".buttons__basket-text")));
    }

    @Test
    public void DelItemFromCart() {
        //открываем карточку товара
        driver.get("https://www.dns-shop.ru/product/9c01c392b97ded20/igra-lego-harry-potter-collection-ps4/");

        //добавляем в корзину
        driver.findElement(By.cssSelector(".button-ui_brand")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'В корзине')]")));

        //переходим в корзину
        driver.get("https://www.dns-shop.ru/cart/");

        //проверим, что добавилось в корзину
        WebElement element = driver.findElement(By.cssSelector(".cart-link-counter__badge"));
        String textElement = element.getText();
        String message = String.format("В корзине неверное количество товаров. Ожидалось %s. Получили %s.", "1", textElement);
        Assertions.assertEquals("1", textElement, message);

        //очистим корзину
        driver.findElement(By.cssSelector(".count-buttons__button_minus")).click();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".buttons__basket-text")));

        //проверим, что корзина очистилась
        WebElement element1 = driver.findElement(By.xpath("//div[@id='cart-page-new']/div/div[2]/div[2]/div"));
        String textElement1 = element1.getText();
        String message1 = String.format("В корзине неверное количество товаров. Ожидалось %s. Получили %S", "Корзина пуста", textElement1);
        Assertions.assertEquals("Корзина пуста", textElement1, message1);
    }

    @AfterAll
    public static void closeBrowser(){
        driver.quit();
    }
}
