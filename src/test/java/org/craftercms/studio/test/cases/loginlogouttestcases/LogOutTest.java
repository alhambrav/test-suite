package org.craftercms.studio.test.cases.loginlogouttestcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.craftercms.studio.test.pages.HomePage;
import org.craftercms.studio.test.pages.LoginPage;
import org.craftercms.studio.test.utils.ConstantsPropertiesManager;
import org.craftercms.studio.test.utils.FilesLocations;
import org.craftercms.studio.test.utils.UIElementsPropertiesManager;
import org.craftercms.studio.test.utils.WebDriverManager;

/**
 * 
 * @author Gustavo Andrei Ortiz Alfaro 
 *
 */

public class LogOutTest {

	WebDriver driver;

	LoginPage objLogin;

	HomePage objHomePage;

	private WebDriverManager driverManager;

	private LoginPage loginPage;

	private HomePage homePage;

	private String userName;
	private String password;

	private String loginButtonLocator;

	private String createSiteButtonXpath;
	

	@BeforeClass
	public void beforeTest() {
		this.driverManager = new WebDriverManager();
		
		UIElementsPropertiesManager UIElementsPropertiesManager = new UIElementsPropertiesManager(
				FilesLocations.UIELEMENTSPROPERTIESFILEPATH);
		ConstantsPropertiesManager constantsPropertiesManager = new ConstantsPropertiesManager(FilesLocations.CONSTANTSPROPERTIESFILEPATH);
		
		this.driverManager.setConstantsPropertiesManager(constantsPropertiesManager);
		
		this.loginPage = new LoginPage(driverManager, UIElementsPropertiesManager);
		this.homePage = new HomePage(driverManager, UIElementsPropertiesManager);
		
		userName = constantsPropertiesManager.getSharedExecutionConstants().getProperty("crafter.username");
		password = constantsPropertiesManager.getSharedExecutionConstants().getProperty("crafter.password");
		loginButtonLocator = UIElementsPropertiesManager.getSharedUIElementsLocators().getProperty("login.btn_Login");
		createSiteButtonXpath = UIElementsPropertiesManager.getSharedUIElementsLocators().getProperty("general.sites.createsitebutton");
		
	}

	@AfterClass
	public void afterTest() {
		driverManager.closeConnection();
	}

	@Test(priority = 0)

	public void logoutFromStudioTest() {

		// login to application

		loginPage.loginToCrafter(userName, password);

		this.driverManager.isElementPresentAndClickableByXpath(createSiteButtonXpath);
		//this.driverManager.isElementPresentAndClickableByXpath(createSiteButtonXpath);
		
		// LogOut
		homePage.clickLogoutOutCrafter();
		
		// Verify login is fine
	    WebElement signInButtom = this.driverManager.driverWaitUntilElementIsPresentAndDisplayed("xpath",
	    		loginButtonLocator);
	 
	    Assert.assertTrue(signInButtom.isDisplayed());
	
	}

}
