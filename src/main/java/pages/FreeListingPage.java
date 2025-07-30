package pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import base.Base;

public class FreeListingPage extends Base
{
    @FindBy(xpath = "//*[@id='header_freelisting']/a/div[2]")
    WebElement freeListingButton;

    @FindBy(xpath = "//*[@id=\"1\"]")
    WebElement mobileInput;

    @FindBy(xpath = "//div[@id='listyourbusiness']//button[1]")
    WebElement startButton;

    @FindBy(className = "entermobilenumber_error__text__uPM09")
    WebElement errorText;

    public void openPage() 
    {
        driver.navigate().to("https://www.justdial.com/");
    }

    public void clickFreeListing() 
    {
        wait.until(ExpectedConditions.elementToBeClickable(freeListingButton)).click();
    }

    public void enterInvalidMobile(String mobile)
    {
        wait.until(ExpectedConditions.visibilityOf(mobileInput)).sendKeys(mobile);
    }

    public void submitForm() 
    {
        startButton.click();
    }
    
    public String getErrorMessage()
    {
        return wait.until(ExpectedConditions.visibilityOf(errorText)).getText();
    }
}
