package bot;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static bot.Keys.*;

public class Application {
    private static final String pathToDriver = "src/main/resources/chromedriver.exe";
    private static final String pathToElement = "/html/body/div/div[2]/main/div/div/div/div[3]/span/div/div[1]/div/div[2]/div[1]";
    private static WebElement playersOnServer;


    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", pathToDriver);
        WebDriver webDriver = new ChromeDriver();

        webDriver.manage().window().maximize();
        webDriver.get(WEBSITE);

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 15);

        while (true) {
            if (playersOnServer != null) {
                webDriver.navigate().refresh();
                Thread.sleep(15 * 1000);
            }

            try {
                webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pathToElement)));
                playersOnServer = webDriver.findElement(By.xpath(pathToElement));
            } catch (Exception e) {
                webDriver.navigate().refresh();
                continue;
            }


            if (playersOnServer.isDisplayed()) {
                Character players = playersOnServer.getText().charAt(0);
                System.out.println(players);
                if (players > '2') {
                    break;
                }
            }
        }

        MailSender.sendMail(MAIL_RECEIVER, playersOnServer.getText().charAt(0));

        webDriver.quit();
    }

}
