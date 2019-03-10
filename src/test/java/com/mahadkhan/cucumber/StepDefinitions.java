package com.mahadkhan.cucumber;

/**
 * Created by Mahad on 2019-03-09.
 */


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import cucumber.annotation.en.And;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.Assert;
import java.util.Random;
import java.net.MalformedURLException;



public class StepDefinitions {

    // Variables
    private WebDriver driver;
    private final String PATH_TO_CHROME_DRIVER = "/Users/Mahad/Downloads/chromedriver";
    private final String PATH_TO_BINARY = "/Applications/Browsers/Google Chrome.app";
    //Authentication
    private final String SIGN_IN_URL = "https://accounts.google.com/signin/v2/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
    private final String SIGN_IN_EMAIL = "ecse428assnb@gmail.com";
    private final String SIGN_IN_PWD = "260678570";
    private final String LANDING_PAGE_URL = "https://mail.google.com/mail/u/0/#inbox";
    //Element Identifiers
    private final String PWD_LANDING = "jXeDnc";
    private final String COMPOSE_BTN = "T-I J-J5-Ji T-I-KE L3";
    private final String RECIPIENT_ID = ":8s";
    private final String RECIPIENT_CLASS = ":8s";
    private final String SUBJECT_CLASS = "aoT";
    private final String ATTACHMENT_BTN = "//input[@type='file']";
    private final String UPLOADED_ATTACHMENT_CLASS = "vI";
    private final String SEND_BTN = ":7u";
    private final String CONFIRMATION_POPUP_CLASS = "aT";
    private final String INSERT_PHOTO = ":gw";
    private final String URL_CLASS = "a-Cf a-Cf-V";
    private final String URL_INPUT_CLASS = "Mf-Tl-Qc";
    private final String INSERT_BTN = "picker:ap:2";
    private final String REMOVE_BTN = ":j6";
    private final String DRIVE_CLASS = "Kj-JD-K7-K0";
    //Images
    private final String IMAGE_1 = System.getProperty("user.dir") + "/images/image1.jpg";
    private final String IMAGE_2 = System.getProperty("user.dir") + "/images/image2.jpg";
    private final String IMAGE_3 = System.getProperty("user.dir") + "/images/image3.png";
    private final String IMAGE_4 = System.getProperty("user.dir") + "/images/image4.png";
    private final String IMAGE_5 = System.getProperty("user.dir") + "/images/image5.png";
    private final String OVER_25 = System.getProperty("user.dir") + "/images/over25.jpg";
    private final String[] imageArray = {IMAGE_1, IMAGE_2, IMAGE_3, IMAGE_4, IMAGE_5};
    //Image URLs for Alternate Flow
    private final String IMAGE_1_URL = "https://geology.com/google-earth/google-earth.jpg";
    private final String IMAGE_2_URL = "https://ssmu.ca/marketplace/wp-content/uploads/2016/09/190873.jpg";
    private final String IMAGE_3_URL = "https://cdn-images-1.medium.com/max/1600/1*Dt2MPKnIO_gFBxdFsO-09Q.png";
    private final String IMAGE_4_URL = "https://www.theglobeandmail.com/resources/assets/meta/facebook-1200x630.png";
    private final String IMAGE_5_URL = "https://www.gettysburgflag.com/media/catalog/product/cache/2/thumbnail/520x416/602f0fa2c1f0d1ba5e241f914e856ff9/p/a/pakistanflag.png";
    private final String[] imageURLArray = {IMAGE_1_URL, IMAGE_2_URL, IMAGE_3_URL, IMAGE_4_URL, IMAGE_5_URL};
    //Recipient email addresses
    private final String[] recipientEmails = {"ecse428test@mailinator.com", "ecse428test1@mailinator.com", "ecse428test2@mailinator.com", "ecse428test3@mailinator.com", "ecse428test4@mailinator.com"};


    //Given

    // This method takes the user to Gmail's email composer
    @Given("^I am on Gmail’s compose email page$")
    public void givenOnComposeEmail() throws Throwable {
        setupSeleniumWebDrivers();
        signIn();
        System.out.println("System is in inital state: " + initialState());
        System.out.println("Attempting to find Compose button");
        WebElement composeBtn = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.className(COMPOSE_BTN)));
        System.out.println("Found!");
        composeBtn.click();
        try {
            System.out.println("Attempting to open email composer");
            //Verifying if the composer is actually visible
            driver.findElement(By.id(":6d"));
            System.out.println("Email composer successfully opened");
        } catch (Exception e) {
            System.out.println("Did not successfully open email composer");
        }
    }

    // And

    // This method selects one of the email addresses provided in the list
    // and accordingly fills in the recipient and subject fields
    @And("^I have filled in the Recipients and Subject fields$")
    public void fillFields() {
        WebElement recipientInput = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(RECIPIENT_ID)));
        recipientInput.click();
        WebElement recipientTextInput = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(RECIPIENT_CLASS)));
        recipientTextInput.sendKeys(returnRandom(recipientEmails));
        WebElement subject = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(SUBJECT_CLASS)));
        subject.sendKeys("ECSE 428 Assignment B Automated Test");
    }

    // This method is an additional check to verify if the image has been attached
    @And("^the image is successfully uploaded from device$")
    public void uploadedFromDevice() {
        WebElement attachmentConfirmation = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(UPLOADED_ATTACHMENT_CLASS)));
        if (attachmentConfirmation.getText().equals("")) {
            Assert.fail("Image was not successfully uploaded");
        }
    }

    @And("^I have attached an image file$")
    public void imageAlreadyAttached() {
        attachFromDevice();
    }

    @And("^I can send the email with the link to the image on Google Drive$")
    public void sendEmailWithDriveLink() {
        System.out.println("Attempting to send email with drive link");
        sendEmailWithImage();
    }


    // When

    // This method attaches a random image from the images provided in the images folder.
    @When("^I attach an image file from my device$")
    public void attachFromDevice() {
        //Attempt to upload image
        try {
            System.out.println("Attempting to attach image from device");
            driver.findElement(By.xpath(ATTACHMENT_BTN)).sendKeys(returnRandom(imageArray));
            System.out.println("Image successfully uploaded");
        } catch (Exception e){
            System.out.println("Image failed to upload");
        }
    }

    // This method uses image URL to add the image as attachment to the email body
    @When("^I attach an image using its URL$")
    public void attachViaURL() {
        System.out.println("Attempting to upload image via URL");
        WebElement insertPhoto = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(INSERT_PHOTO)));
        insertPhoto.click();
        WebElement webAddress = (new WebDriverWait(driver, 25))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(URL_CLASS)));
        webAddress.click();
        WebElement urlInput = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(URL_INPUT_CLASS)));
        urlInput.sendKeys(returnRandom(imageURLArray));
        //Attempt to upload image
        try {
            System.out.println("Attempting to attach image via URL");
            WebElement insertBtn = (new WebDriverWait(driver, 100))
                    .until(ExpectedConditions.elementToBeClickable(By.id(INSERT_BTN)));
            insertBtn.click();
            System.out.println("Image successfully uploaded");
        } catch (Exception e){
            System.out.println("Image failed to upload");
        }
    }

    // This method removes the attached image for the error flow
    @When("^I press X next to the uploaded attachment$")
    public void removeAttachment(){
        //Attempt to remove image
        try {
            System.out.println("Attempting to remove attached image");
            WebElement removeBtn = (new WebDriverWait(driver, 20))
                    .until(ExpectedConditions.elementToBeClickable(By.id(REMOVE_BTN)));
            removeBtn.click();
            System.out.println("Image successfully removed");
        } catch (Exception e){
            System.out.println("Image could not be removed");
        }
    }

    // This mehtod attaches an image greater than 25MB
    @When("^I attach an image file of over 25MB from my device$")
    public void attachOver25(){
            System.out.println("Attempting to attach image larger than 25MB from device");
            driver.findElement(By.xpath(ATTACHMENT_BTN)).sendKeys(OVER_25);
            System.out.println("Uploading image");
    }

    @When("^I attach an image from my device via Import Photos$")
    public void attachViaImportPhoto() {
        System.out.println("Attempting to upload image via URL");
        WebElement insertPhoto = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(INSERT_PHOTO)));
        insertPhoto.click();
        WebElement uploadPhoto = (new WebDriverWait(driver, 25))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("a-Cf")));
        uploadPhoto.click();
        WebElement selectFile = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("a-b-c d-u d-u-F")));
        selectFile.sendKeys(returnRandom(imageArray));
        // Uploading popup
        WebElement uploadPopup = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("Mf-Gq-Wo")));
        // Confirm the image has been uploaded
        try {
            while(uploadPopup.getText().contains("Uploading"));
        } catch (Exception e) {
            System.out.println("Image successfully attached");
        }
    }

    // Then

    // This method sends the email and asserts true if email is sent successfully
    @Then("^I can send the email with the image attached$")
    public void sendEmailWithImage(){
        System.out.println("Attempting to find send button");
        WebElement sendBtn = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.id(SEND_BTN)));
        sendBtn.click();
        System.out.println("Send button clicked");
        WebElement statusPopup = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(CONFIRMATION_POPUP_CLASS)));
        try {
            while(statusPopup.getText().contains("Sending"));
        } catch (Exception e){
            statusPopup = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className(CONFIRMATION_POPUP_CLASS)));
        }
        System.out.println("Email Sent!!!");
        Assert.assertTrue(emailSentSuccessfully(statusPopup));
        //Ensure system is returned to initial state
        returnToInitialState();
        driver.quit();
    }

    // This method sends the email and asserts true if email is sent successfully
    @Then("^the email is sent without the image attachment$")
    public void sendEmailWithoutImage(){
        try{
            WebElement attachmentConfirmation = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className(UPLOADED_ATTACHMENT_CLASS)));
        } catch (Exception e){
            System.out.println("Attachment was successfully removed");
        }
        System.out.println("Sending email without attachment!");
        sendEmailWithImage();
    }

    // This method verifies the Google Drive popup appears indicating that the image is being uploaded to thr Drive
    @Then("^it is uploaded on Google Drive$")
    public void uploadedOnGoodleDrive(){
        WebElement drivePopup = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(DRIVE_CLASS)));
        // Confirm the file is being uploaded
        if (!drivePopup.getText().contains("Attaching")) {
            Assert.fail("The image was not able to upload");
        }
        // Wait for the file to be uploaded
        try {
            while(drivePopup.getText().contains("Attaching File"));
        }
        catch (Exception e){
            System.out.println("Large image successfully uploaded");
        }
    }



    // Helper functions
    private void setupSeleniumWebDrivers() throws MalformedURLException {
        /*if (driver == null) {
            System.out.println("Setting up ChromeDriver... ");
            ChromeOptions chromeOptions= new ChromeOptions();
            chromeOptions.setBinary(PATH_TO_BINARY);
            System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
            driver = new ChromeDriver(chromeOptions);
            System.out.print("Done!\n");
        }*/
        if (driver == null) {
            System.out.println("Setting up ChromeDriver... ");
            System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
            driver = new ChromeDriver();
            System.out.print("Done!\n");
        }
    }

    //This method signs a user into their Gmail account using the email address and password provided
    private void signIn(){
        goTo(SIGN_IN_URL);
        System.out.println("Landed on Gmail's login page");
        WebElement emailInput = driver.findElement(By.name("identifier"));
        emailInput.sendKeys(SIGN_IN_EMAIL);
        WebElement nextBtn = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(By.className("ZFr60d CeoRYc")));
        nextBtn.click();
        WebElement passwordLanding = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(PWD_LANDING)));
        try {
            while(!passwordLanding.getText().contains("Hi ECSE428"));
        } catch (Exception e) {
        }
        driver.findElement(By.id("password")).sendKeys(SIGN_IN_PWD);
        nextBtn = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(By.className("qhFLie")));
        nextBtn.click();
        System.out.println("Successfully logged in!");
    }

    // This method returns true if we return to initial state after sending an email
    private boolean initialState(){
        return driver.getCurrentUrl().equals(LANDING_PAGE_URL);
    }

    // This method ensures system always returns to initial state after an email is sent
    private void returnToInitialState(){
        if (initialState() != true){
            goTo(LANDING_PAGE_URL);
            System.out.println("System was forced to return to initial state");
        }
        else{
            System.out.println("System has returned to initial state");
        }
    }

    // This method returns true if the email was successfully sent
    private boolean emailSentSuccessfully(WebElement messageSentPopup){
        return messageSentPopup.getText().contains("Message sent");
    }


    //This method navigates to the URL provided
    private void goTo(String url) {
        if (driver != null) {
            System.out.println("Going to " + url);
            driver.get(url);
        }
    }

    //This method returns a random String element from an array of Strings
    private String returnRandom(String[] inputList){
        Random random = new Random();
        int index = random.nextInt(inputList.length);
        return inputList[index];
    }



}

