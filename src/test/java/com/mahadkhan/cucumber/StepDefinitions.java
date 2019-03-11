package com.mahadkhan.cucumber;

/**
 * ECSE 428 Assn B
 * Student Name: Mahad Khan
 * Student ID: 260678570
 */


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import cucumber.annotation.en.And;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import org.junit.Assert;
import java.util.Random;
import java.net.MalformedURLException;



public class StepDefinitions {

    // Variables
    private WebDriver driver;
    private final String PATH_TO_CHROME_DRIVER = "/Users/mahadkhan/Downloads/chromedriver";
    //Authentication
    private final String SIGN_IN_URL = "https://accounts.google.com/signin/v2/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
    private final String SIGN_IN_EMAIL = "ecse428assnb@gmail.com";
    private final String SIGN_IN_PWD = "260678570";
    private final String LANDING_PAGE_URL = "https://mail.google.com/mail/#inbox";
    //Element Identifiers
    private final String PWD_LANDING = "jXeDnc";
    private final String COMPOSE_BTN = "z0";
    private final String SUBJECT_CLASS = "aoT";
    private final String ATTACHMENT_BTN = "//input[@type='file']";
    private final String UPLOADED_ATTACHMENT_CLASS = "vI";
    private final String SEND_BTN = "gU";
    private final String CONFIRMATION_POPUP_CLASS = "aT";
    private final String REMOVE_BTN = "vq";
    //Images
    private final String IMAGE_1 = System.getProperty("user.dir") + "/images/image1.jpg";
    private final String IMAGE_2 = System.getProperty("user.dir") + "/images/image2.jpg";
    private final String IMAGE_3 = System.getProperty("user.dir") + "/images/image3.png";
    private final String IMAGE_4 = System.getProperty("user.dir") + "/images/image4.png";
    private final String IMAGE_5 = System.getProperty("user.dir") + "/images/image5.png";
    private final String[] imageArray = {IMAGE_1, IMAGE_2, IMAGE_3, IMAGE_4, IMAGE_5};
    //Recipient email addresses
    private final String[] recipientEmails = {"ecse428test@mailinator.com", "ecse428test1@mailinator.com", "ecse428test2@mailinator.com", "ecse428test3@mailinator.com", "ecse428test4@mailinator.com"};


    //Given

    // This method takes the user to Gmail's email composer
    @Given("^I am on Gmail’s compose email page$")
    public void givenOnComposeEmail() throws Throwable {
        setupSeleniumWebDrivers();
        signIn();
        System.out.println("Attempting to find Compose button");
        WebElement composeBtn = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.className(COMPOSE_BTN)));
        System.out.println("Found!");
        composeBtn.click();
        try {
            System.out.println("Attempting to open email composer");
            //Verifying if the composer is actually visible
            WebElement composer = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.id(":6g")));
            System.out.println("Email composer successfully opened");
        } catch (Exception e) {
            System.out.println("Did not successfully open email composer");
        }
    }

    // This method just signs the user in
    @Given("^I am signed in$")
    public void signedIn() throws Throwable {
        setupSeleniumWebDrivers();
        signIn();
        System.out.println("Signed in.");

    }

    // And

    // This method selects one of the email addresses provided in the list
    // and accordingly fills in the recipient and subject fields
    @And("^I have filled in the Recipients and Subject fields$")
    public void fillFields() {
        System.out.println("Attempting to fill recipient and subject fields");
        WebElement recipientTextInput = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("vO")));
        recipientTextInput.sendKeys(returnRandom(recipientEmails));
        WebElement subject = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(SUBJECT_CLASS)));
        subject.sendKeys("ECSE 428 Assignment B Automated Test");
    }

    // This method selects an email in the inbox and attempts to right click and select reply
    @And("^I have selected to reply to an email in my inbox$")
    public void reply() {
        System.out.println("Attempting to find the email I want to reply");
        WebElement selectedEmail = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("zA")));
        System.out.println("Found!");
        // Actions class is required for right click
        Actions actions = new Actions(driver);
        System.out.println("Attempting to right click");
        actions.contextClick(selectedEmail).perform();
        WebElement replyBtn = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(":7j")));
        replyBtn.click();
        System.out.println("Reply Button Clicked");
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

    // This method attempts to attach an image to the reply window that popped up
    @And("^I attach an image file$")
    public void attachToReply() {
        try {
            System.out.println("Attempting to attach image from device");
            driver.findElement(By.xpath("//input[@type='file']")).sendKeys(returnRandom(imageArray));
            System.out.println("Image successfully uploaded");
        } catch (Exception e){
            System.out.println("Image failed to upload");
            // Fail if the image does not upload
            Assert.fail("Image failed to upload");
        }
    }

    // This method fills in the recipient field, clicks the cc button and then fills the cc field
    // The subject can not yet be filled as it hides the cc button
    @And("^I have filled in the Recipient and cc field$")
    public void fillRecipientAndCC() {
        System.out.println("Attempting to fill recipient field");
        WebElement recipientTextInput = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("vO")));
        recipientTextInput.sendKeys(returnRandom(recipientEmails));
        System.out.println("Attempting to fill cc field");
        WebElement ccBtn = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(":70")));
        ccBtn.click();
        WebElement ccInput = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(":a8")));
        ccInput.sendKeys(returnRandom(recipientEmails));

    }

    // This method solely sets the recipient field and not the subject field
    @And("^I have filled in the Recipient field$")
    public void fillRecipient() {
        System.out.println("Attempting to fill recipient field");
        WebElement recipientTextInput = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("vO")));
        recipientTextInput.sendKeys(returnRandom(recipientEmails));

    }

    // This method sets the subject field only
    @And("^I have filled in the subject field")
    public void fillSubject() {
        WebElement subject = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(SUBJECT_CLASS)));
        subject.sendKeys("ECSE 428 Assignment B Automated Test");

    }

    // This method attaches an image file that will later be removed
    @And("^I have attached an image file$")
    public void imageAlreadyAttached() {
        attachFromDevice();
    }

    // This method accepts the popup warning that arises when the subject field is left empty
    @And("^I accept the warning$")
    public void acceptWarning() {
        System.out.println("Accept popup warning");
        // The following fixed time sleep is solely so that the action can be seen in the video.
        // It is NOT used to control the system under test
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        // Accept the popup warning
        driver.switchTo().alert().accept();;
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

    // This method attaches and attempts to send the email with an image attachment
    // However as the subject is missing it will not be able to get sent
    @When("^I attempt to send an image file from my device$")
    public void sendImage() {
        //Attempt to upload image
        try {
            System.out.println("Attempting to attach image from device");
            driver.findElement(By.xpath(ATTACHMENT_BTN)).sendKeys(returnRandom(imageArray));
            System.out.println("Image successfully uploaded");
        } catch (Exception e){
            System.out.println("Image failed to upload");
        }
        System.out.println("Attempting to find send button");
        WebElement sendBtn = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.className(SEND_BTN)));
        sendBtn.click();
        System.out.println("Send button clicked");
    }




    // This method removes the attached image for the error flow
    @When("^I press X next to the uploaded attachment$")
    public void removeAttachment(){
        //Attempt to remove image
        try {
            System.out.println("Attempting to remove attached image");
            WebElement removeBtn = (new WebDriverWait(driver, 5))
                    .until(ExpectedConditions.elementToBeClickable(By.className(REMOVE_BTN)));
            removeBtn.click();
            System.out.println("Image successfully removed");
        } catch (Exception e){
            System.out.println("Image could not be removed");
        }
    }



    // Then

    // This method sends the email and asserts true if email is sent successfully
    @Then("^I can send the email with the image attached$")
    public void sendEmailWithImage(){
        System.out.println("Attempting to find send button");
        WebElement sendBtn = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.className(SEND_BTN)));
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

    // This method confirms the email has been sent and returns the system to initial state
    @Then("^the email will be sent$")
    public void sendEmail(){
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

    // This method send the reply email with the image attached and asserts true if the email is sent successfully
    @Then("^I can send the reply with the image attached$")
    public void sendReply(){
        System.out.println("Attempting to find send button");
        WebElement sendBtn = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.id(":aj")));
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




    // Helper functions
    private void setupSeleniumWebDrivers() throws MalformedURLException {
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
        WebElement nextBtn = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(By.className("qhFLie")));
        nextBtn.click();
        WebElement passwordLanding = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(PWD_LANDING)));
        try {
            while(!(passwordLanding.getText().contains("Welcome")));
        } catch (Exception e) {
        }
        WebElement password = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("whsOnd")));
        password.sendKeys(SIGN_IN_PWD);
        nextBtn = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(By.className("qhFLie")));
        nextBtn.click();
        System.out.println("Successfully logged in!");
        System.out.println("System is in inital state: " + initialState());
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


    // This method navigates to the URL provided
    private void goTo(String url) {
        if (driver != null) {
            System.out.println("Going to " + url);
            driver.get(url);
        }
    }

    // This method returns a random String element from an array of Strings
    private String returnRandom(String[] inputList){
        Random random = new Random();
        int index = random.nextInt(inputList.length);
        return inputList[index];
    }



}

