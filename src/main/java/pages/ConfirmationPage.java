package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.AbstractComponents;

public class ConfirmationPage extends AbstractComponents {

		WebDriver driver;

		public ConfirmationPage(WebDriver driver) 
		{
			// TODO Auto-generated constructor stub
			super(driver);
			this.driver = driver;
			PageFactory.initElements(driver, this);
		}

		//loacting Confirmation message and creating confirmationMessage object of confirmation Message
		//of type Weblement
		@FindBy(css = ".hero-primary")
		WebElement confirmationMessage;

		//Action method to get confirmation message text and trimming spaces ar=t beginning and at end
		public String getConfirmationMessage() 
		{
			return confirmationMessage.getText().trim();
		}

}
