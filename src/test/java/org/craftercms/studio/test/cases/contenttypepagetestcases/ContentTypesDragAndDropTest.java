package org.craftercms.studio.test.cases.contenttypepagetestcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.craftercms.studio.test.pages.SiteConfigPage;
import org.craftercms.studio.test.pages.HomePage;
import org.craftercms.studio.test.pages.LoginPage;
import org.craftercms.studio.test.pages.PreviewPage;
import org.craftercms.studio.test.utils.ConstantsPropertiesManager;
import org.craftercms.studio.test.utils.FilesLocations;
import org.craftercms.studio.test.utils.UIElementsPropertiesManager;
import org.craftercms.studio.test.utils.WebDriverManager;

/**
 * 
 * @author Gustavo Andrei Ortiz Alfaro
 *
 */

public class ContentTypesDragAndDropTest {

	WebDriver driver;

	private WebDriverManager driverManager;

	private LoginPage loginPage;

	private HomePage homePage;

	private PreviewPage previewPage;

	private SiteConfigPage siteConfigPage;

	private ConstantsPropertiesManager constantsPropertiesManager;


	@BeforeClass
	public void beforeTest() {
		this.driverManager = new WebDriverManager();
		UIElementsPropertiesManager uIElementsPropertiesManager = new UIElementsPropertiesManager(
				FilesLocations.UIELEMENTSPROPERTIESFILEPATH);
		this.constantsPropertiesManager = new ConstantsPropertiesManager(FilesLocations.CONSTANTSPROPERTIESFILEPATH);
		
		this.loginPage = new LoginPage(driverManager, uIElementsPropertiesManager,constantsPropertiesManager);
		this.homePage = new HomePage(driverManager, uIElementsPropertiesManager,constantsPropertiesManager);
		this.siteConfigPage = new SiteConfigPage(driverManager, uIElementsPropertiesManager,constantsPropertiesManager);
		this.previewPage = new PreviewPage(driverManager, uIElementsPropertiesManager,constantsPropertiesManager);
		
	}

	@AfterClass
	public void afterTest() {
		driverManager.closeConnection();
	}

	@Test(priority = 0)
	public void contentTypesDragAndDropTest() {

		// login to application

		loginPage.loginToCrafter("admin", "admin");

		// wait for element is clickeable

		homePage.getDriverManager().driverWait(2000);

		// go to preview page
		homePage.goToPreviewPage();

		// wait for element is clickeable

		homePage.getDriverManager().driverWait(4000);

		// homePage.getDriverManager().driverWait();

		// Show site content panel
		this.driverManager
		.driverWaitUntilElementIsPresentAndDisplayed(3, "xpath", ".//a[@id='acn-dropdown-toggler']").click();
		//driverManager.getDriver().findElement(By.xpath(".//a[@id='acn-dropdown-toggler']")).click();

		// wait for element is clickeable

		homePage.getDriverManager().driverWait(2000);

		// go to admin console page

		previewPage.goToAdminConsolePage();

		// wait for element is clickeable

		homePage.getDriverManager().driverWait(1000);

		// select content types
		siteConfigPage.selectContentTypeOption();

		// open content types

		siteConfigPage.clickExistingTypeOption();

		// wait for element is clickeable

		homePage.getDriverManager().driverWait(1000);

		// Select the Entry content type

		siteConfigPage.selectEntryContentType();

		// Confirm the content type selected

		siteConfigPage.confirmContentTypeSelected();

		// wait for element is clickeable

		homePage.getDriverManager().driverWait(2000);
		// homePage.getDriverManager().driverWait();

		// Drag and drop Form Section

		// driverManager.getDriver().manage().window().maximize();

		// driverManager.getDriver().manage().timeouts().implicitlyWait(10000,
		// TimeUnit.MILLISECONDS);

		WebElement From = this.driverManager
				.driverWaitUntilElementIsPresentAndDisplayed(3, "cssSelector", ".control-section");
				//driverManager.getDriver().findElement(By.cssSelector(".control-section"));

		WebElement To = this.driverManager
				.driverWaitUntilElementIsPresentAndDisplayed(3, "cssSelector", "#content-type-canvas .content-form-name");
				//driverManager.getDriver()
				//.findElement(By.cssSelector("#content-type-canvas .content-form-name"));

		Actions builder = new Actions(driverManager.getDriver());

		Action dragAndDrop = builder.clickAndHold(From)

				.moveToElement(To)

				.release(To)

				.build();

		dragAndDrop.perform();

		// Save the drag and drop process

		siteConfigPage.saveDragAndDropProcess();

		// Ok for the dialog window when appears

		// new WebDriverWait(driverManager.getDriver(),
		// 10).until(ExpectedConditions.alertIsPresent());
		// driverManager.getDriver().switchTo().alert().accept();

		// validate the control added

		Assert.assertTrue(driverManager.isElementPresentBycssSelector(5,"#content-type-canvas .content-section-name"));
		
			//driverManager.getDriver().findElement(By.cssSelector("#content-type-canvas .content-section-name"));

	
	}

}
