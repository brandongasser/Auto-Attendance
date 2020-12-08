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

	private static final String CLASSROOM_URL = "https://classroom.google.com/w/MTkwNzg3MzA1NTE3/t/all";
	private static final String EMAIL_URL = "https://mail.google.com/mail/u/0/#inbox";
	private static final String FORMS_URL = "https://docs.google.com/forms/d/e/1FAIpQLScsuyMMv0HruPu3HjvG5p9v_XTGPCBIxCjg2Y1UmB5extnRxA/viewform";
	private static final String ACCOUNTS_URL = "https://accounts.google.com/signin/v2/identifier?passive=1209600&continue=https%3A%2F%2Faccounts.google.com%2F&followup=https%3A%2F%2Faccounts.google.com%2F&flowName=GlifWebSignIn&flowEntry=ServiceLogin";

	private static String EMAIL;
	private static String PASSWORD;
	private static String FIRST_NAME;
	private static String LAST_NAME;
	private static String GRADE;
	private static boolean attendanceToday;

	private static WebDriver driver;

	public static void main(String[] args) throws Exception {

		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		options.addArguments("--disable-gpu");

		driver = new ChromeDriver(options);

		EMAIL = args[0];
		PASSWORD = args[1];
		FIRST_NAME = args[2];
		LAST_NAME = args[3];
		GRADE = args[4];

		runCode();

	}

	private static void runCode() throws Exception {
		System.out.println("AutoAttendance Starting...");
		logIn();
		checkForAttendance();
		if (attendanceToday) {
			System.out.println("Found Attendance Today");
			doForm();
			turnInAssignment();
			sendEmail();
			closeAllTabs();
		} else {
			System.out.println("No Attendance Today");
		}
	}

	private static void logIn() {
		driver.navigate().to(ACCOUNTS_URL);
		WebElement email = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='email']")));
		email.sendKeys(EMAIL);
		email.sendKeys(Keys.RETURN);
		WebElement password = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='password']")));
		password.sendKeys(PASSWORD);
		password.sendKeys(Keys.RETURN);
	}

	private static void checkForAttendance() {
		driver.navigate().to(CLASSROOM_URL);
		try {
			new WebDriverWait(driver, 15)
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '"
							+ new SimpleDateFormat("MMMM dd").format(Calendar.getInstance().getTime()) + "')]")));
					attendanceToday = true;
		} catch (Exception e) {
			try {
				new WebDriverWait(driver, 15)
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '"
							+ new SimpleDateFormat("MMMM d").format(Calendar.getInstance().getTime()) + "')]")));
					attendanceToday = true;
			} catch (Exception f) {
				attendanceToday = false;
			}
		}
	}

	private static void turnInAssignment() {
		driver.navigate().to(CLASSROOM_URL);
		try {
			new WebDriverWait(driver, 15)
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '"
							+ new SimpleDateFormat("EEEE, MMMM dd").format(Calendar.getInstance().getTime()) + "')]")))
					.click();
		} catch (Exception e) {
			new WebDriverWait(driver, 15)
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '"
							+ new SimpleDateFormat("EEEE, MMMM d").format(Calendar.getInstance().getTime()) + "')]")))
					.click();
		}
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='View assignment']"))).click();
				new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Mark as done']"))).click();
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("/descendant::span[text()='Mark as done'][4]")))
				.click();
	}

	private static void doForm() {
		driver.navigate().to(FORMS_URL);
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("/descendant::input[@type='text'][1]")))
				.sendKeys(FIRST_NAME);
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//descendant::input[@type='text'][2]")))
				.sendKeys(LAST_NAME);
		new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='" + GRADE + "']")))
				.click();
		new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='date']")))
				.sendKeys(new SimpleDateFormat("MM/dd/YYYY").format(Calendar.getInstance().getTime()));
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Present']"))).click();
		new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Submit']"))).click();
	}

	private static void sendEmail() throws Exception {
		driver.navigate().to(EMAIL_URL);
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
	}

	private static void closeAllTabs() {
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		for (String tab : tabs) {
			driver.switchTo().window(tab);
			driver.close();
		}
	}

}