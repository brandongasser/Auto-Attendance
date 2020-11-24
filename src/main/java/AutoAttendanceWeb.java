import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AutoAttendanceWeb {

	// URL to classwork tab of attendance classroom page in parenthesis
	private static String CLASSROOM_URL = "https://classroom.google.com/w/MTkwNzg3MzA1NTE3/t/all";
	// your school email inside parenthesis
	private static String EMAIL = "819814@mystma.org";
	// your school password inside parenthesis
	private static String PASSWORD = "312Bg110!";
	// your first name inside parenthesis
	private static String FIRST_NAME = "Brandon";
	// your last name inside parenthesis
	private static String LAST_NAME = "Gasser";

	private static WebDriver driver;

	public static void main(String[] args) throws Exception {

		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		options.addArguments("--disable-gpu");

		driver = new ChromeDriver(options);

		runCode();

	}

	private static void runCode() throws Exception {
		System.out.println("Process Starting...");
		driver.navigate().to(CLASSROOM_URL);
		WebElement email = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='email']")));
		email.sendKeys(EMAIL);
		email.sendKeys(Keys.RETURN);
		WebElement password = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='password']")));
		password.sendKeys(PASSWORD);
		password.sendKeys(Keys.RETURN);

		try {
			new WebDriverWait(driver, 15)
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '"
							+ new SimpleDateFormat("MMMM dd").format(Calendar.getInstance().getTime()) + "')]")))
					.click();
		} catch (Exception e) {
			new WebDriverWait(driver, 15)
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '"
							+ new SimpleDateFormat("MMMM d").format(Calendar.getInstance().getTime()) + "')]")))
					.click();
		}
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='View assignment']"))).click();
		try {
			new WebDriverWait(driver, 15).until(
					ExpectedConditions.elementToBeClickable(By.xpath("/descendant::div[text()='STMA ATTENDANCE'][3]")))
					.click();
		} catch (Exception e) {
			new WebDriverWait(driver, 15).until(
					ExpectedConditions.elementToBeClickable(By.xpath("/descendant::div[text()='STMA ATTENDANCE'][3]")))
					.click();
		}
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("/descendant::input[@type='text'][1]")))
				.sendKeys(FIRST_NAME);
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//descendant::input[@type='text'][2]")))
				.sendKeys(LAST_NAME);
		new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='12']")))
				.click();
		new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='date']")))
				.sendKeys(new SimpleDateFormat("MM/dd/YYYY").format(Calendar.getInstance().getTime()));
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Present']"))).click();
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Submit']"))).click();
		driver.switchTo().window(tabs.get(0));
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Mark as done']"))).click();
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("/descendant::span[text()='Mark as done'][4]")))
				.click();

		// confirmation email
		driver.navigate().to("https://mail.google.com/mail/u/0/#inbox");
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Compose']"))).click();
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("/descendant::textarea[1]"))).sendKeys(EMAIL);
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='subjectbox']")))
				.sendKeys("Auto Attendance Confirmation "
						+ new SimpleDateFormat("MMMM dd").format(Calendar.getInstance().getTime()));
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@aria-label='Message Body']")))
				.sendKeys("Auto Attendance finished successfully at "
						+ new SimpleDateFormat("hh:mm:ss MMMM dd").format(Calendar.getInstance().getTime()));
		new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Send']")))
				.click();
		Thread.sleep(15000);

		for (String tab : tabs) {
			driver.switchTo().window(tab);
			driver.close();
		}
	}
}