package tests;
import java.io.FileNotFoundException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import base.Base;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pages.CarWashPage;
import pages.FreeListingPage;
import pages.JustdialGymPage;
import utils.DataGenerator;
import utils.ExcelUtil;
import utils.WebDriverFactory;

@Epic("Justdial Automation")
public class BaseTest extends Base 
{
	private static final Logger logger = LogManager.getLogger(BaseTest.class);

	WebDriver driver;
	CarWashPage carPage;
	FreeListingPage listingPage;
	JustdialGymPage gymPage;

	@BeforeClass
	public void setUp()
	{
		logger.info("Setting up WebDriver and initializing page objects...");
		driver = WebDriverFactory.getDriver();
		carPage = new CarWashPage();
		carPage.init(driver);
		listingPage = new FreeListingPage();
		listingPage.init(driver);
		gymPage = new JustdialGymPage();
		gymPage.init(driver);
	}

	@Test(priority = 1)
	@Feature("Car Wash Search")
	@Story("Fetch top 5 car wash services with phone and rating")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Fetch top 5 car wash services with phone and rating")
	public void testCarWashSearch() throws InterruptedException 
	{
		try 
		{
			carPage.openJustdial();
			carPage.setLocation("Siruseri, Chennai");
			carPage.searchService("Car Wash Services");
			carPage.sortByRating();
			carPage.printTopCarWashDetails(5);
			logger.info("Car wash search completed successfully.");
		} 
		catch (Exception e) 
		{
			logger.error("Error during car wash search", e);
			Assert.fail("Car wash search failed due to exception.");
		}
	}

	@Test(priority = 2)
	@Feature("Free Listing Validation")
	@Story("Enter invalid mobile number and capture validation message")
	@Severity(SeverityLevel.NORMAL)
	@Description("Enter invalid mobile number in Free Listing and capture validation message")
	public void testInvalidMobileListing()
	{
		try {
			listingPage.openPage();
			listingPage.clickFreeListing();
			String fakeMobile = DataGenerator.generateInvalidMobileNumber();
			listingPage.enterInvalidMobile(fakeMobile);
			listingPage.submitForm();
			String errorMsg = listingPage.getErrorMessage();
			logger.info("Invalid Mobile Entered: " + fakeMobile);
			logger.info("Error Message: " + errorMsg);
			System.out.println("Error Message: " + errorMsg);
			System.out.println("Invalid Mobile Entered: " + fakeMobile);
			System.out.println("===============================================");
			Assert.assertNotNull(errorMsg, "Error message should not be null");
			Assert.assertTrue(errorMsg.contains("Please Enter a Valid Mobile Number"), "Expected validation message not found");
		} 
		catch (Exception e) 
		{
			logger.error("Error during invalid mobile listing test", e);
			Assert.fail("Invalid mobile listing test failed.");
		}
	}

	@DataProvider(name = "locations")
	public Object[][] provideLocations()
	{
		return new Object[][] 
		{
			{"Siruseri, Chennai"}
		};
	}

	@Test(priority = 3, dataProvider = "locations")
	@Feature("Gym Search and Export")
	@Story("Search for gyms and export results to Excel")
	@Severity(SeverityLevel.MINOR)
	@Description("Search for gyms in user-provided location and export to Excel")
	public void testGymSearchAndExcelExport(String location) throws InterruptedException {
		try {
			gymPage.searchGyms(location);
			System.out.println("\n" + gymPage.getHeadingText() + "\n");
			List<String[]> data = gymPage.extractGymNamesAndLinks();
			for (String[] row : data) 
			{
				System.out.println(row[0]);
			}
			ExcelUtil.writeDataToExcel("GymsList.xlsx", data);
			logger.info("Gym data exported successfully for location: " + location);
			Assert.assertTrue(data.size() > 0, "No gym data extracted");
		} 
		catch (Exception e)
		{
			logger.error("Error during gym search and export", e);
			Assert.fail("Gym search and export failed.");
		}
	}
	@AfterClass
	public void tearDown() throws FileNotFoundException
	{
		logger.info("Tearing down WebDriver...");
		if (driver != null) driver.quit();
	}
}
