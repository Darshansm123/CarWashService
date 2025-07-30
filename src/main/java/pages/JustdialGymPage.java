package pages;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import base.Base;

public class JustdialGymPage extends Base
{
	@FindBy(xpath = "//div[normalize-space()='Gym']")
	WebElement gymCategory;

	@FindBy(id="city-auto-sug")
	WebElement locationInput;

	@FindBy(xpath = "//*[@id=\"react-autowhatever-city-auto-suggest--item-1\"]/a/div")
	WebElement locationSuggestion;

	@FindBy(xpath = "//div[@aria-label='search']")
	WebElement searchButton;

	@FindBy(xpath = "//div/h1[contains(text(), 'Popular Gyms')]")
	WebElement heading;

	@FindBy(xpath = "//a[@class='jsx-7cbb814d75c86232 resultbox_title_anchorbox font22 fw500 color111']")
	List<WebElement> gymList;

	public void searchGyms(String location) throws InterruptedException 
	{
		driver.get("https://www.justdial.com/");
		gymCategory.click();
		//Thread.sleep(3000);
		locationInput.clear();
		locationInput.sendKeys(location);
		//Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOf(locationSuggestion)).click();
		searchButton.click();
	}

	public String getHeadingText() 
	{
		return wait.until(ExpectedConditions.visibilityOf(heading)).getText();
	}

	public List<String[]> extractGymNamesAndLinks()
	{
		wait.until(ExpectedConditions.visibilityOfAllElements(gymList));
		List<String[]> data = new ArrayList<>();
		for (WebElement el : gymList) {
			String name = el.getText();
			String link = el.getAttribute("href");
			if (!name.isEmpty() && link != null) {
				data.add(new String[]{name, link});
			}
		}
		return data;
	}
}
