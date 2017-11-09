package org.craftercms.studio.test.cases.contextualnavigationbartestcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.craftercms.studio.test.pages.DashboardPage;
import org.craftercms.studio.test.pages.HomePage;
import org.craftercms.studio.test.pages.LoginPage;
import org.craftercms.studio.test.pages.PreviewPage;
import org.craftercms.studio.test.utils.ConstantsPropertiesManager;
import org.craftercms.studio.test.utils.FilesLocations;
import org.craftercms.studio.test.utils.UIElementsPropertiesManager;
import org.craftercms.studio.test.utils.WebDriverManager;
import org.openqa.selenium.By;

/**
 * 
 * @author Gustavo Andrei Ortiz Alfaro
 *
 */

public class PublishingSiteTest {

	private WebDriverManager driverManager;

	private LoginPage loginPage;

	private HomePage homePage;

	private PreviewPage previewPage;

	private DashboardPage dashboardPage;

	private String userName;
	private String password;
	private String createFormFrameElementCss;
	private String createFormMainTitleElementXPath;
	private String createFormSaveAndCloseElementId;
	private String studioLogo;
	private String testingContentItem;
	private String topNavDeleteOption;
	private String topNavEditOption;
	private String topNavStatusIcon;

	@BeforeClass
	public void beforeTest() {
		this.driverManager = new WebDriverManager();
		UIElementsPropertiesManager UIElementsPropertiesManager = new UIElementsPropertiesManager(
				FilesLocations.UIELEMENTSPROPERTIESFILEPATH);
		ConstantsPropertiesManager constantsPropertiesManager = new ConstantsPropertiesManager(
				FilesLocations.CONSTANTSPROPERTIESFILEPATH);
	
		this.driverManager.setConstantsPropertiesManager(constantsPropertiesManager);
		
		this.loginPage = new LoginPage(driverManager, UIElementsPropertiesManager);
		this.homePage = new HomePage(driverManager, UIElementsPropertiesManager);
		this.previewPage = new PreviewPage(driverManager, UIElementsPropertiesManager);
		this.dashboardPage = new DashboardPage(driverManager, UIElementsPropertiesManager);

		userName = constantsPropertiesManager.getSharedExecutionConstants().getProperty("crafter.username");
		password = constantsPropertiesManager.getSharedExecutionConstants().getProperty("crafter.password");
		createFormFrameElementCss = UIElementsPropertiesManager.getSharedUIElementsLocators()
				.getProperty("general.createformframe");
		createFormMainTitleElementXPath = UIElementsPropertiesManager.getSharedUIElementsLocators()
				.getProperty("general.createformMainTitle");
		createFormSaveAndCloseElementId = UIElementsPropertiesManager.getSharedUIElementsLocators()
				.getProperty("general.saveandclosebutton");
		studioLogo = UIElementsPropertiesManager.getSharedUIElementsLocators()
				.getProperty("general.studiologo");
		testingContentItem = UIElementsPropertiesManager.getSharedUIElementsLocators()
				.getProperty("general.testingcontentitem");
		topNavDeleteOption = UIElementsPropertiesManager.getSharedUIElementsLocators()
				.getProperty("general.deletetopnavoption");
		topNavEditOption= UIElementsPropertiesManager.getSharedUIElementsLocators()
				.getProperty("general.edittopnavoption");
		topNavStatusIcon = UIElementsPropertiesManager.getSharedUIElementsLocators()
				.getProperty("general.statustopbaricon");

	}

	@AfterClass
	public void afterTest() {
		driverManager.closeConnection();
	}

	public void changeBodyToNotRequiredOnEntryContent() {

		previewPage.changeBodyOfEntryContentPageToNotRequired();
	}

	public void createContent() {

		// right click to see the the menu
		dashboardPage.rightClickToSeeMenu();

		// Select Entry Content Type
		dashboardPage.clickEntryCT();

		// Confirm the Content Type selected
		dashboardPage.clickOKButton();

		// Switch to the iframe
		driverManager.getDriver().switchTo().defaultContent();
		driverManager.getDriver().switchTo().frame(this.driverManager.driverWaitUntilElementIsPresentAndDisplayed(
				"cssSelector", createFormFrameElementCss));
		this.driverManager.isElementPresentAndClickableBycssSelector(createFormFrameElementCss);

		// Set basics fields of the new content created
		dashboardPage.setBasicFieldsOfNewContent("Test1", "Testing1");
		// Set the title of main content
		this.driverManager
				.driverWaitUntilElementIsPresentAndDisplayed("cssSelector", createFormMainTitleElementXPath)
				.sendKeys("MainTitle");
		// save and close
		this.driverManager.driverWaitUntilElementIsPresentAndDisplayed("id", createFormSaveAndCloseElementId)
				.click();
		// reload page
		driverManager.getDriver().navigate().refresh();

	}

	public void approveAndPublish() {

		// approve and publish

		previewPage.clickOnApprovePublish();

		// submit
		previewPage.clickOnSubmitButtonOfApprovePublish();
	}

	public void reloadPage() {
		// reload page
		driverManager.getDriver().navigate().refresh();
		driverManager.getDriver().navigate().refresh();
	}

	@Test(priority = 0)

	public void publishingSite() {

		// login to application

		loginPage.loginToCrafter(userName, password);

		// go to preview page
		homePage.goToPreviewPage();

		changeBodyToNotRequiredOnEntryContent();

		// go to dashboard
		this.driverManager.driverWaitUntilElementIsPresentAndDisplayed( "id", studioLogo).click();

		// expand pages folder
		dashboardPage.expandPagesTree();

		// create content
		createContent();

		dashboardPage.expandHomeTree();

		// wait for element is clickeable
		driverManager.getDriver().navigate().refresh();

		this.driverManager
				.driverWaitUntilElementIsPresentAndDisplayed( "xpath", testingContentItem)
				.click();

		// approve and publish
		approveAndPublish();	
		
		this.driverManager.driverWaitUntilElementIsPresentAndDisplayedAndClickable("xpath",
				topNavDeleteOption);
		
		this.driverManager.driverWaitUntilElementIsPresentAndDisplayedAndClickable( "xpath",
				topNavEditOption);
				
		String isLifeContent="";
		
		while(!(isLifeContent.contains("undefined live")))
		{
			isLifeContent= this.driverManager.getDriver()
			.findElement(By
			.xpath(topNavStatusIcon)).getAttribute("class").toString();
			driverManager.getDriver().navigate().refresh();
			this.dashboardPage.expandHomeTree();	
		}
				
		Assert.assertTrue(this.driverManager.getDriver()
				.findElement(By
				.xpath(topNavStatusIcon)).getAttribute("class").contains("undefined live"));
		
	}

}

