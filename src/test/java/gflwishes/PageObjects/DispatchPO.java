package gflwishes.PageObjects;

import gflwishes.base.Generics;
import gflwishes.testcases.Dispatch;
import gflwishes.utilities.ExcelColumns;
import gflwishes.utilities.ExcelUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DispatchPO extends Dispatch implements ExcelColumns {

    private WebDriver driver;
    private Generics generics;

    private String orderTitle = "";

    public static String SiteAddress;
    ExcelUtils excelUtils = new ExcelUtils();

    public static String lblPickUpOrderName;

    public DispatchPO(WebDriver baseDriver) {
        this.driver = baseDriver;
        PageFactory.initElements(driver, this);
        generics = new Generics(driver);
        log4j = Logger.getLogger("DispatchPO");
    }

    @FindBy(xpath = "//span[@class='date_string']")
    WebElement lblToday;

    @FindBy(xpath = "//mat-icon[text()='filter_list ']")
    WebElement btnFilter;

    @FindAll(value = {@FindBy(xpath = "//map-common-list-header[contains(text(),'PICKUP')]//" +
            "ancestor::mat-expansion-panel-header//following-sibling::div[@role='region']//mat-list-item//div[text()!='ON HOLD']")})
    List<WebElement> lstPickUpNewOrder;

    @FindAll(value = {@FindBy(xpath = "//map-common-list-header[contains(text(),'PICKUP')]//ancestor::mat-expansion-panel" +
            "-header//following-sibling::div[@role='region']//mat-list-item//p")})
    List<WebElement> lstPickUpOrders;

    @FindBy(xpath = "//map-common-order-item-details//div[contains(text(),'ORDER NUMBER')]//span")
    WebElement lblOrderNumber;

    @FindAll(value = {@FindBy(xpath = "//map-common-vehicle-header-map-icon//span[@class='vehicle_id_font']")})
    List<WebElement> lstVehicleName;

    @FindAll(value = {@FindBy(xpath = "//map-common-vehicle-item-header//span[contains(@class,'vehicle_id')]")})
    List<WebElement> lstVehicle;

    @FindBy(xpath = "//map-common-dispatch//img[contains(@class,'vehicle-img')]")
    WebElement lblVehicleOnMap;

    @FindBy(xpath = "//map-common-white-container//div[text()='DRAG & DROP HERE']")
    WebElement truckDragAndDrop;

    @FindAll(value = {@FindBy(xpath = "//div[contains(@class,'pastDate')]")})
    List<WebElement> lstPastVehicle;

    @FindBy(xpath = "(//map-common-vehicle-list//mat-list-item//span[text()='This vehicle has no assignments'])[1]" +
            "//ancestor::map-common-vehicle-item//map-common-vehicle-item-header//span[contains(text(),'')]")
    WebElement firstempyTruck;

    @FindBy(xpath = "(//span[contains(text(),'ORDERS')]//..//..//following-sibling::div//span)[2]")
    WebElement btnFilterOpen;

    @FindAll(value = {@FindBy(xpath = "//button[@role='menuitem']")})
    List<WebElement> lstMenuItem;

    @FindBy(xpath = "//button//mat-icon[text()='play_circle_outline']")
    WebElement btnStart;

    @FindBy(xpath = "//button[@title='']//mat-icon[text()='check']")
    WebElement btnComplete;

    @FindBy(xpath = "//div[@class='mat-form-field-infix']")
    WebElement btnDriver;

    @FindAll(value = {@FindBy(xpath = "//mat-option")})
    List<WebElement> lstOption;

    @FindBy(xpath = "//mat-sidenav//mat-card-title")
    WebElement lblDispatcher;

    public void openDispatcher() {
        testStepsLog("Open Dispatcher");
        driver.navigate().to(FM_URL + File.separator + "dispatch");
    }

    public void getDispatcherName(int count) {
        excelUtils.setTestData(END_TO_END, count, DISPATCHER, driver.findElement(By.xpath("//mat-sidenav//mat-card-title")).
                getAttribute("innerHTML"));
    }

    public boolean verifyDispatchPage() {
        new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[text()='Loading...']")));
        return generics.isDisplay(lblToday) && generics.isDisplay(btnFilter);
    }

    public void addTruckFromMap(int count) {
        generics.pause(4);
        Actions act = new Actions(driver);
        generics.scrollToElement(truckDragAndDrop);
        testStepsLog("Drag and Drop Truck from the map to order.");
        act.dragAndDrop(driver.findElement(By.xpath("//map-common-vehicle-item//span[contains(text(),'" +
                excelUtils.getTestData("EndToEnd", count, VEHICLE_NAME) + "')]")), truckDragAndDrop).build().perform();
        generics.pause(3);
    }

    public void addTruckFromMapForPast() {
        generics.pause(4);
        Actions act = new Actions(driver);
        testStepsLog("Drag and Drop Truck from the map to order.");
        act.dragAndDrop(driver.findElement(By.xpath("//map-common-vehicle-item-header//" +
                "span[contains(@class,'vehicle_id_font')]")), lstPastVehicle.get(0)).build().perform();
        generics.pause(3);
    }

    public void addTruckFromMapForToday() {
        generics.pause(4);
        Actions act = new Actions(driver);
        testStepsLog("Drag and Drop Truck from the map to order.");
        generics.scrollToElement(driver.findElement(By.xpath("//span[text()='This vehicle has no assignments']//" +
                "ancestor::map-common-vehicle-item//span[contains(@class,'vehicle_id_font')]")));
        generics.scrollToElement(lstTodayVehicle.get(0));
        act.dragAndDrop(driver.findElement(By.xpath("//span[text()='This vehicle has no assignments']//" +
                        "ancestor::map-common-vehicle-item//span[contains(@class,'vehicle_id_font')]")),
                lstTodayVehicle.get(0)).build().perform();
        orderTitle = lstTodayVehicleTitle.get(0).getText();
        new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfAllElements(
                driver.findElements(By.xpath("//div[contains(@class,'cdk-overlay-backdrop-showing')]"))));
    }

    public void filterOrder(String status) {
        clickOnOrderStatusFilter();
        testStepsLog("Select Status : " + status);
        switch (status.toLowerCase()) {
            case "all":
                generics.clickOn(lstMenuItem.get(0));
                break;
            case "unassigned":
                generics.clickOn(lstMenuItem.get(1));
                break;
            case "assigned":
                generics.clickOn(lstMenuItem.get(2));
                break;
            case "in progress":
                generics.clickOn(lstMenuItem.get(3));
                break;
            case "on hold":
                generics.clickOn(lstMenuItem.get(4));
                break;
        }

    }

    private void clickOnOrderStatusFilter() {
        testStepsLog("Click on ORDER Status");
        generics.clickOnJS(btnFilterOpen);
    }

    public void clickOnFilter() {
        testStepsLog("Click on Order Filter.");
        generics.pause(2);
        generics.scrollToElement(btnFilter);
        generics.clickOnJS(btnFilter);
    }

    public void selectPickUp() {
        System.out.println("Click on oder to open.");
        generics.scrollToElement(driver.findElements(By.xpath("//map-common-list-header[contains(text(),'PICKUP')]" +
                "//ancestor::map-common-order-list//p[text()=' " + lblPickUpOrderName + " ']")).get(1));
        generics.clickOn(driver.findElements(By.xpath("//map-common-list-header[contains(text(),'PICKUP')]/" +
                "/ancestor::map-common-order-list//p[text()=' " + lblPickUpOrderName + " ']")).get(1));
        generics.pause(10);
    }

    public void selectDeliver() {
        System.out.println("Click on oder to open.");
        generics.scrollToElement(driver.findElements(By.xpath("//map-common-list-header[contains(text(),'DELIVER')]" +
                "//ancestor::map-common-order-list//p[text()=' " + lblPickUpOrderName + " ']")).get(1));
        generics.clickOn(driver.findElements(By.xpath("//map-common-list-header[contains(text(),'DELIVER')]/" +
                "/ancestor::map-common-order-list//p[text()=' " + lblPickUpOrderName + " ']")).get(1));
        generics.pause(10);
    }

    public void startOrder() {
        generics.pause(3);
        testStepsLog("Click on Start button to start order.");
        generics.clickOnJS(btnStart);
        generics.pause(2);
    }

    @FindAll(value = {@FindBy(xpath = "//span[@class='mat-option-text']")})
    List<WebElement> lstDrivers;

    public void completeOrder(int count) {
        new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(btnComplete));
        generics.scrollToElement(btnComplete);
        generics.clickOnJS(btnComplete);
        generics.pause(2);
        ((JavascriptExecutor) driver).executeScript("document.evaluate('//div[@class=\"mat-form-field-infix\"]'," +
                "   document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click();");
        excelUtils.setTestData(END_TO_END, count, STATUS, "COMPLETED");
        String driverName = excelUtils.getTestData(END_TO_END, count, DRIVER);
        generics.clickOnJS(driver.findElement(By.xpath("//span[@class='mat-option-text' and text()='" + driverName + "']")));
        generics.clickOnJS(btnProceed);
        generics.pause(5);
    }

    @FindBy(xpath = "//i[text()='date_range']//following-sibling::span")
    WebElement btnDatePicker;

    @FindBy(xpath = "//dispatch-order-aside-header//mat-icon[text()='search']")
    WebElement btnSearchOrder;

    @FindBy(xpath = "//button[@id='orderSearchButton']//following-sibling::input")
    WebElement txtSearch;

    public void searchAddress(int count) {
        SiteAddress = excelUtils.getTestData(END_TO_END, count, 0);
        new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[text()='Loading...']")));
        generics.clickOnJS(btnSearchOrder);
        generics.type(txtSearch, SiteAddress.split(" ")[0]);
    }

    public void searchAddress() {
        new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[text()='Loading...']")));
        generics.clickOnJS(btnSearchOrder);
        generics.type(txtSearch, orderTitle.split(" ")[0]);
    }

    public void selectOrder(boolean isFromData) {
        if (isFromData) {
            generics.clickOn(driver.findElement(By.xpath("(//div[@class='card']//p[contains(text()," +
                    "' " + SiteAddress.split(",")[0].toUpperCase() + " ')])[last()]")));
        } else {
            generics.clickOn(lstPastVehicle.get(0));
        }
    }

    public void selectOrder() {
        generics.clickOn(lstTodayVehicle.get(0));
    }

    @FindBy(xpath = "//map-common-order-item-details//span[@class='badge_text']")
    WebElement lblOrderType;

    @FindBy(xpath = "//div[text()='SITE CONTACT']//following-sibling::div[1]")
    WebElement lblSiteContact;

    @FindBy(xpath = "//div[text()='SITE CONTACT']//following-sibling::div//div")
    WebElement lblCustomer;


    public static String SiteCustomer;

    public boolean verifyDeliveryDetails(int count) {
        SiteCustomer = excelUtils.getTestData(END_TO_END, count, CUSTOMER_NAME);
        System.out.println(lblOrderType.getText());
        System.out.println(driver.findElement(By.xpath("//span[contains(text(),'" +
                SiteAddress.split(",")[0] + "')]")).getText().
                replace("\n", " ").replaceAll("\\s", ""));
        System.out.println(SiteAddress.replaceAll("\\s", ""));
        return lblOrderType.getText().equalsIgnoreCase("UNASSIGNED") &&
                driver.findElement(By.xpath("//span[contains(text(),'" +
                        SiteAddress.split(",")[0] + "')]")).
                        getText().replace("\n", "").trim().replaceAll("\\s", "").
                        equalsIgnoreCase(SiteAddress.replaceAll("\\s", ""))
                && lblSiteContact.getText().equalsIgnoreCase(excelUtils.getTestData(END_TO_END,
                count, SITE_NAME)) && lblCustomer.getText().equalsIgnoreCase(SiteCustomer);
    }

    @FindBy(xpath = "//div[contains(text(),'EXPECTED TIME ON SITE')]//following-sibling::div//map-common-date-time-view//div")
    WebElement lblOrderDate;

    @FindBy(xpath = "//span[contains(@class,'selected')]")
    WebElement btnCurrentDate;

    @FindBy(xpath = "//button//div[contains(text(),'PROCEED')]")
    WebElement btnProceed;

    public boolean isOrderFromPast() {
        System.out.println(lblOrderDate.getText());
        return lblOrderDate.getText().contains(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy")));
    }

    public void selectCurrentDate() {
        generics.clickOn(lblOrderDate);
        generics.clickOn(btnCurrentDate);
        generics.clickOnJS(btnProceed);
    }

    @FindAll(value = {@FindBy(xpath = "//div[contains(@class,'_inprogress') or contains(@class,'_assigned')]//p")})
    List<WebElement> lstAssignedInProgressOrders;

    public void openOrderFromVehiclePane(boolean isFromData) {
        if (isFromData) {
            for (WebElement e : lstAssignedInProgressOrders) {
                System.out.println(e.getText());
                if (e.getText().contains(SiteAddress.split(",")[0])) {
                    generics.clickOnJS(e);
                    generics.pause(2);
                    break;
                }
            }
        } else {
            System.out.println(lstAssignedInProgressOrders);
            for (WebElement e : lstAssignedInProgressOrders) {
                System.out.println(e.getText());
                if (e.getText().contains(orderTitle)) {
                    generics.clickOnJS(e);
                    generics.pause(2);
                    break;
                }
            }
        }
    }

    @FindBy(xpath = "//map-common-vehicle-order-detail//div[contains(text(),'PICK UP')]")
    WebElement lblOrder;

    public boolean isPickupProject() {
        return lblOrder.getText().equalsIgnoreCase("PICK UP");
    }

    @FindBy(xpath = "//mat-icon[text()='filter_list']")
    WebElement btnMapFilter;

    public void addDisposalSite() {
        generics.clickOnJS(btnMapFilter);
    }

    @FindBy(xpath = "//mat-icon[contains(@class,'container_buttons')]")
    List<WebElement> btnEditContainer;

    @FindBy(xpath = "//input[@formcontrolname='dropContainerName']")
    WebElement txtContainerName;

    @FindAll(value = {@FindBy(xpath = "//mat-radio-button")})
    List<WebElement> lstRadios;

    @FindBy(xpath = "//form//mat-icon[text()='check']")
    WebElement btnSaveContainerName;

    public void enterPickUpContainerName() {
        generics.pause(1);
        generics.scrollToElement(btnEditContainer.get(0));
        generics.clickOnJS(btnEditContainer.get(0));
        generics.pause(2);
        if (!lstRadios.get(1).getAttribute("class").contains("mat-radio-disabled"))
            generics.type(txtContainerName, "Test_" + generics.getRandomBetween(100, 999));
        generics.clickOnJS(btnSaveContainerName);
        generics.pause(2);
    }

    public void enterDropOffContainerName() {
        generics.pause(2);
        generics.scrollToElement(btnEditContainer.get(1));
        generics.clickOnJS(btnEditContainer.get(1));
        if (!lstRadios.get(1).getAttribute("class").contains("mat-radio-disabled"))
            generics.type(txtContainerName, "Test_" + generics.getRandomBetween(100, 999));
        generics.clickOnJS(btnSaveContainerName);
        generics.pause(3);
    }

    @FindBy(xpath = "//span[text()='SCALE TICKET # ']")
    WebElement btnScaleTicket;

    @FindBy(xpath = "//span[text()='WEIGHT ']")
    WebElement btnWeight;

    @FindBy(xpath = "//div[contains(text(),'YES')]")
    WebElement btnYes;

    @FindBy(xpath = "//input[@formcontrolname='scaleTicket']")
    WebElement txtScaleTicket;

    @FindBy(xpath = "//input[@formcontrolname='weight']")
    WebElement txtWeight;

    @FindBy(xpath = "//map-common-disposal-site-info//mat-icon[text()='check']")
    WebElement btnAcceptTickerDetails;

    public void enterTicketDetails(int count) {
        int scaleTicket = generics.getRandomBetween(100, 999);
        int weight = generics.getRandomBetween(100, 999);
        generics.pause(2);
        generics.clickOnJS(btnScaleTicket);
        generics.type(txtScaleTicket, String.valueOf(scaleTicket));
        generics.type(txtWeight, String.valueOf(weight));
        excelUtils.setTestData(END_TO_END, count, SCALE_TICKET, String.valueOf(scaleTicket));
        excelUtils.setTestData(END_TO_END, count, WEIGHT, String.valueOf(weight));
        generics.clickOnJS(btnAcceptTickerDetails);
        generics.pause(3);
    }

    @FindBy(xpath = "//map-common-vehicle-order-detail//div[contains(@class,'_dispatch_order_bold')]")
    WebElement lblOperationType;

    public String getOperationType() {
        System.out.println(generics.getText(lblOperationType));
        return generics.getText(lblOperationType);
    }

    @FindBy(xpath = "(//map-common-vehicle-order-detail//mat-icon)[2]")
    WebElement lblMatIcon;

    public boolean isIconUpward() {
        return generics.getText(lblMatIcon).equalsIgnoreCase("arrow_upward");
    }

    @FindBy(xpath = "//mat-tab-header//div[text()='FOR DRIVER']")
    WebElement btnDriverNotes;

    @FindBy(xpath = "//mat-tab-body//mat-icon[text()='add ']")
    WebElement btnAddDriverNote;

    @FindBy(xpath = "//textarea[@formcontrolname='note']")
    WebElement txtDriverNotes;

    @FindBy(xpath = "//form//mat-icon[text()='check']")
    WebElement btnAddNote;

    public void enterDriverNotes(int count) {
        String driverNote = generics.getRandomCharacters(10);
        generics.clickOn(btnDriverNotes);
        generics.pause(1);
        generics.clickOn(btnAddDriverNote);
        generics.pause(1);
        generics.type(txtDriverNotes, driverNote);
        driverNote = excelUtils.getTestData(END_TO_END, count, DRIVER_NOTES) + "," + driverNote;
        excelUtils.setTestData(END_TO_END, count, DRIVER_NOTES, driverNote);
        generics.clickOn(btnAddNote);
        generics.pause(5);
    }

    public boolean isPaymentDone(int count) {
        return !excelUtils.getTestData(END_TO_END, count, PAYMENT).isEmpty();
    }

    public void setFlag(int count, boolean flag) {
        if (flag) excelUtils.setTestData(END_TO_END, count, STATUS, "INCOMPLETE");
    }

    @FindBy(xpath = "//h3[contains(@class,'error_text')]")
    private WebElement lblError;

    public boolean verifyPastDateTruckMove() {
        return lblError.getText().equalsIgnoreCase("This operation cannot be performed.");
    }

    public boolean verifyAssigningVehicleToInProgressOrder() {
        return lblError.getText().equalsIgnoreCase("This operation cannot be performed.");
    }

    @FindAll(value = {@FindBy(xpath = "//map-common-order-item/div[not(@class)]//div[text()!='ON HOLD']")})
    List<WebElement> lstTodayVehicle;

    @FindAll(value = {@FindBy(xpath = "//map-common-order-item/div[not(@class)]//p")})
    List<WebElement> lstTodayVehicleTitle;

    public boolean isTodayOrderNotDisplay() {
        return lstTodayVehicle.isEmpty();
    }

    @FindAll(value = {@FindBy(xpath = "//map-common-vehicle-list//div[@id='order-item']//p")})
    List<WebElement> lstAssignedVehicle;

    @FindBy(xpath = "//span[text()='Order has been successfully Assigned to the driver']")
    private WebElement lblSuccessOrderAssign;

    public boolean verifyOrderAssigned() {
        // boolean bool = lblSuccessOrderAssign.isDisplayed();
        boolean bool = false;
        for (WebElement webElement : lstAssignedVehicle) {
            if (webElement.getText().equalsIgnoreCase(orderTitle)) {
                bool = webElement.getText().equalsIgnoreCase(orderTitle);
            }
        }
        return bool;
    }

    public boolean orderStatusAssigned() {
        return driver.findElement(By.xpath("//p[text()=' " + orderTitle + " ']//ancestor::map-common-order-item" +
                "//div[contains(@class,'_assigned')]")).isDisplayed();
    }

    public boolean verifyOrderFilteredAsAssigned() {
        return driver.findElement(By.xpath("//p[contains(text(),'found for \"" + orderTitle.split(" ")[0] + "\"')]//" +
                "following-sibling::map-common-order-list//p")).isDisplayed();
    }

    public void setTomorrowDateToOrder() {
        selectOrder();
        generics.clickOn(lblOrderDate);
        generics.clickOn(driver.findElement(By.xpath("//span[@bsdatepickerdaydecorator and text()='" +
                LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd")) + "']")));
        generics.clickOnJS(btnProceed);
    }

    public void filterTomorrowOrder() {
        new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfAllElements(
                driver.findElements(By.xpath("//div[contains(@class,'cdk-overlay-backdrop-showing')]"))));
        btnDatePicker.click();
        generics.clickOn(driver.findElement(By.xpath("//span[contains(@aria-label,'" +
                LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")) + "')]")));
    }

    @FindAll(value = {@FindBy(xpath = "//mat-expansion-panel-header//following-sibling::div[@role='region']" +
            "//mat-list-item//div[text()!='ON HOLD']")})
    List<WebElement> lstUnAssignedOrders;

    @FindBy(xpath = "//button//span[@dataname='PUT ON HOLD']")
    WebElement btnOnHold;

    public void makeOrderOnHold() {
        generics.clickOn(lstUnAssignedOrders.get(0));
        generics.clickOn(btnOnHold);
        generics.clickOn(btnProceed);
    }


}
