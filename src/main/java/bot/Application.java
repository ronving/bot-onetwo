package bot;

import org.omg.PortableServer.THREAD_POLICY_ID;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static bot.Keys.*;

public class Application {
    private static final String pathToDriver = "src/main/resources/chromedriver.exe";
    private static final String pathToElement = "/html/body/div/div[2]/main/div/div/div/div[3]/span/div/div[1]/div/div[2]/div[1]";
    private static WebElement playersOnServer;

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", pathToDriver);

        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get(WEBSITE);

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pathToElement)));

        do {
            if (playersOnServer != null) {
                try {
                    Thread.sleep(5*60*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            playersOnServer = webDriver.findElement(By.xpath(pathToElement));

        } while (playersOnServer.getText().charAt(0) < '3');

        MailSender.sendMail(MAIL_RECEIVER);

        webDriver.quit();
    }

}
