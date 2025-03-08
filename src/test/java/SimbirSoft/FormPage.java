package SimbirSoft;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FormPage {
    private WebDriver driver;

    public FormPage(WebDriver driver) {
        this.driver = driver;
    }

    // Локаторы
    private By nameLocator = By.id("name-input");
    private By passwordLocator = By.xpath("//*[@id=\"feedbackForm\"]/label[2]/input");
    private By drinkLocator = By.xpath("//*[@id=\"feedbackForm\"]/label[3]");
    private By colorLocator = By.xpath("//*[@id=\"feedbackForm\"]/label[9]");
    private By emailLocator = By.id("email");
    private By messageLocator = By.cssSelector("textarea[name=message]");
    private By submitBtnLocator = By.id("submit-btn");
    private By automationLocator = By.xpath("//*[@id=\"feedbackForm\"]/label[15]");

    // Метод для прокрутки к элементу
    public void scrollToElement(By locator) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // Методы для работы с элементами
    public void enterName(String name) {
        driver.findElement(nameLocator).sendKeys(name);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordLocator).sendKeys(password);
    }

    public void selectDrinks(int... drinkIds) {
        scrollToElement(drinkLocator);
        for (int id : drinkIds) {
            driver.findElement(By.id("drink" + id)).click();
        }
    }

    public void selectColor(int colorId){
        scrollToElement(colorLocator);
        driver.findElement(By.id("color" + colorId)).click();
    }

    public void selectAutomation(String value) {
        scrollToElement(automationLocator);
        Select likeAutomation = new Select(driver.findElement(By.id("automation")));
        likeAutomation.selectByValue(value);
    }

    public void enterEmail(String email) {
        driver.findElement(emailLocator).sendKeys(email);
    }

    public void enterMessage(String message) {

        driver.findElement(messageLocator).sendKeys(message);
    }

    public void getMessage(List<WebElement> liElements){
        List<String> liText = new ArrayList<>();
        System.out.println("Все элементы, находящиеся в Automation Tools:");
        for (WebElement li : liElements) {
            liText.add(li.getText());
            System.out.println(li.getText()); // Вывод всех элементов на консоль
        }
        String max = liText.get(0);
        int sizeOfAutomationTools = liText.size();
        for (String text : liText) {
            if (text.length() > max.length()) {
                max = text;
            }
        }
        String message = "Количество инструментов, описанных в пункте Automation tools = " + sizeOfAutomationTools
                + "\nИнструмент из списка Automation tools, содержащий наибольшее количество символов - " + max;
        enterMessage(message);
    }

    public void submitForm() {
        driver.findElement(submitBtnLocator).click();
    }


}