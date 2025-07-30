package pages;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import base.Base;

public class CarWashPage extends Base
{
	@FindBy(id = "city-auto-sug")
	WebElement locationInput;

	@FindBy(xpath = "//*[@id='react-autowhatever-city-auto-suggest--item-1']/a/div")
	WebElement firstSuggestion;

	@FindBy(id = "main-auto")
	WebElement searchInput;

	@FindBy(id = "srchbtn")
	WebElement searchButton;

	@FindBy(xpath = "//span[contains(text(),'Ratings')]")
	WebElement sortByRatings;

	@FindBy(xpath = "//*[@id='option-3']/label/div/span[1]")
	WebElement ratingOption;

	@FindBy(xpath="//*[@id=\"__next\"]/section/section/div/div[2]/div/div[2]/h1")
	WebElement result;

	public void openJustdial() 
	{
		driver.get("https://www.justdial.com");
	}

	public void setLocation(String location) 
	{
		wait.until(ExpectedConditions.elementToBeClickable(locationInput)).click();
		locationInput.clear();
		locationInput.sendKeys(location);
		wait.until(ExpectedConditions.elementToBeClickable(firstSuggestion)).click();
	}

	public void searchService(String query) 
	{
		wait.until(ExpectedConditions.visibilityOf(searchInput)).clear();
		searchInput.sendKeys(query);
		wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
	}

	public void sortByRating()
	{
		wait.until(ExpectedConditions.elementToBeClickable(sortByRatings)).click();
		wait.until(ExpectedConditions.elementToBeClickable(ratingOption)).click();
	}

	public void printTopCarWashDetails(int limit)
	{
		List<WebElement> names = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
				By.xpath("//h3[contains(@class,'resultbox_title_anchor')]")));

		List<WebElement> ratings = driver.findElements(By.xpath("//li[contains(@class,'resultbox_totalrate')]"));
		List<WebElement> phones = driver.findElements(By.xpath("//span[contains(@class,'callcontent')]"));
		System.out.println(result.getText());
		System.out.println("---------------------------");
		int count = 0;
		for (int i = 0; i < names.size() && count < limit; i++) 
		{
			String name = names.get(i).getText();
			String rating = i < ratings.size() ? ratings.get(i).getText() : "No rating";
			String phone = i < phones.size() ? phones.get(i).getText() : "Not available";

			if (phone == null || phone.equalsIgnoreCase("Show Number")) continue;

			System.out.println("Shop " + (count + 1) + ": " + name);
			System.out.println("Rating: " + rating);
			System.out.println("Phone: " + phone.replace("tel:", ""));
			System.out.println("---------------------------");
			count++;
		}
		System.out.println("===============================================");
	}
}
