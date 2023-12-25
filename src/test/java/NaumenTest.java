import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NaumenTest {
    static WebDriver driver;
    
    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1140, 1005));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    @Order(1)
    public void addFavorite() throws InterruptedException {
        login();

        // Добавление карточки пользователя в "Избранное"
        driver.findElement(By.id("favorite")).click();
        Thread.sleep(1500);
        driver.findElement(By.id("gwt-debug-apply")).click();
        Thread.sleep(2000);

        // Проверка вкладки "Избранное"   gwt-debug-title
        driver.findElement(By.id("gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9")).click();
        WebElement favoriteUser = driver.findElement(By.xpath("//*[@id='gwt-debug-NTreeItemContent.uuid:employee$17713']"));
        assertTrue(favoriteUser.isDisplayed(), "Карточка пользователя не была добавлена в Избранное");

        // Закрыть вкладку "Избранное"
        driver.findElement(By.id("gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9")).click();
        // выход из аккаунта
        driver.findElement(By.id("gwt-debug-logout")).click();
    }

    @Test
    @Order(2)
    public void delFavorite() throws InterruptedException {
        login();

        // Открыть вкладку "Избранное"
        driver.findElement(By.id("gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9")).click();
        Thread.sleep(1500);
        driver.findElement(By.id("gwt-debug-editFavorites")).click();
        Thread.sleep(1500);

        // Удалить карточку пользователя
        driver.findElement(By.cssSelector(".del:nth-child(1)")).click();
        Thread.sleep(1500);
        driver.findElement(By.id("gwt-debug-YES")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("gwt-debug-apply")).click();
        Thread.sleep(2000);

        // Проверка вкладки "Избранное"
        List<WebElement> favoriteUser = driver.findElements(By.xpath("//*[@id='gwt-debug-menuItem']"));
        assertFalse(favoriteUser.isEmpty(), "Карточка пользователя не была удалена в Избранное");

        // Закрыть вкладку "Избранное"
        driver.findElement(By.id("gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9")).click();
        // выход из аккаунта
        driver.findElement(By.id("gwt-debug-logout")).click();
    }

    private void login() {
        driver.get("https://test-m.sd.nau.run/sd/");

        // Ввод имя пользователя
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("s3nsible");
        // Ввод пароля
        driver.findElement(By.id("passwordLabel")).click();
        driver.findElement(By.id("password")).sendKeys("123");
        // Войти
        driver.findElement(By.id("submit-button")).click();

    }

    @AfterAll
    public static void close() {
        driver.close();
    }
}
