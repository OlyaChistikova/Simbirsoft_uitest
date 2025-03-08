package SimbirSoft;

import io.qameta.allure.Step;
import jdk.jfr.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;


public class Ui_Test {
    private WebDriver driver;
    private final String url = "https://practice-automation.com/form-fields";
    private FormPage formPage;
    private String name = "Olya";
    private String password = "qwerty";
    private String email = "olya.chistikova@mail.ru";

    @BeforeSuite
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver = new ChromeDriver(chromeOptions);
    }

    @BeforeClass
    public void appSetup() {
        driver.get(url);
        formPage = new FormPage(driver);
    }

    @Test(priority = 0)
    public void testName() throws InterruptedException{
        formPage.enterName(name);
//        Thread.sleep(2000);
        Assert.assertTrue(isValidName(name), "Имя не может быть пустым и должно содержать хотя бы одно слово");
//        Thread.sleep(1000);
    }

    private boolean isValidName(String name){
        return name != null && !name.trim().isEmpty();
    }

    @Test(priority = 1)
    public void testPassword() throws InterruptedException{
        formPage.enterPassword(password);
//        Thread.sleep(1000);
    }

    @Test(priority = 2)
    public void testDrinks() throws InterruptedException{
        formPage.selectDrinks(2,3);
//        Thread.sleep(1000);
    }

    @Test(priority = 3)
    public void testColor() throws InterruptedException{
        formPage.selectColor(3);
//        Thread.sleep(1000);
    }

    @Test(priority = 4)
    public void testLikeAutomation() throws InterruptedException{
        formPage.selectAutomation("yes");
//        Thread.sleep(1000);
    }

    @Test(priority = 5)
    public void testEmail() throws InterruptedException{
        formPage.enterEmail(email);
        Assert.assertTrue(isValidEmail(email), "Email не соответствует формату name@example.com");
//        Thread.sleep(1000);
    }

    private boolean isValidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9._]+.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    @Test(priority = 6)
    public void testMessage() throws InterruptedException{
        List<WebElement> liElements = driver.findElement(By.xpath("(//ul)[2]")).findElements(By.tagName("li"));
        formPage.getMessage(liElements);
//        Thread.sleep(8000);
    }
    @Test(priority = 7)
    @Description("Test to verify alert funtionality")
    @Step("Executing test alert")
    public void testAlert() throws InterruptedException{
        formPage.submitForm();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert1 = driver.switchTo().alert();
//            Thread.sleep(1000);
            alert1.accept();
            String pageSource = driver.getPageSource();
            if (pageSource == null){
                Assert.fail("Page source is null.");
            }
            Assert.assertTrue(pageSource.contains("Message received!"),
                    "Success message not found on the page.");
        } catch (TimeoutException e) {
            Assert.fail("Alert did not appear within the expected time.");
        } catch (NoAlertPresentException e) {
            Assert.fail("No alert present at the time of switch.");
        } catch (NullPointerException e){
            Assert.fail("Encountered a NullPointerException: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}