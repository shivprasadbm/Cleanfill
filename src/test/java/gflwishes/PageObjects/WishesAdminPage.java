package gflwishes.PageObjects;

import gflwishes.base.Generics;

import gflwishes.testcases.WishesAdmin;
import gflwishes.utilities.ExcelUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WishesAdminPage extends WishesAdmin {

    private WebDriver localDriver;
    private Generics generics;

    public WishesAdminPage(WebDriver baseDriver) {
        this.localDriver = baseDriver;
        PageFactory.initElements(localDriver, this);
        generics = new Generics(localDriver);
        log4j = Logger.getLogger("WishesAdmin");
    }


    @FindBy(xpath = "//button[contains(text(),'PROSPECTS')]/i")
    public WebElement btnAddCustomer;


    public boolean isCustomerPageOpen() {
        return generics.isPresent(btnAddCustomer);
    }


    @FindBy(xpath = "//form//span[text()='Add']")
    public WebElement btnAdd;

    public void clickonAddCustomerButton() {
        generics.pause(5);
        generics.scrollToElement(btnAddCustomer);
        generics.clickOn(btnAddCustomer);
        testStepsLog("Clicked ON Add Customer button");
        generics.pause(2);
    }
    @FindBy(xpath = "//div[text()='Roll Off']")
    public WebElement tabRollOff;

    public void clickOnTabRollOff()
    {
        generics.clickOn(tabRollOff);
        testStepsLog("Clicked on Tab Roll OFF");
    }
    @FindBy(xpath = "//span[text()='ACCEPTED MATERIALS']/following-sibling::button/i")
    public WebElement btnAddAcceptedMaterials;

    public void clickonAddAcceptedMaterialButton()
    {
        generics.pause(6);
        //JavascriptExecutor je = (JavascriptExecutor) localDriver;
        //je.executeScript("arguments[0].scrollIntoView(true);",btnAddAcceptedMaterials);
        generics.clickOn(btnAddAcceptedMaterials);
        testStepsLog("Clicked on Add Accepted material button");
        generics.pause(3);
    }
    @FindBy(xpath = "//mat-select[@formcontrolname='editedMaterialId']")
    public WebElement dpMaterial;

    @FindBy(xpath = "(//mat-option[@aria-disabled='false']/span)[2]")
    public WebElement firstEnabledOption;

    @FindBy(xpath = "(//mat-option[@aria-disabled='true']/span)[1]")
    public WebElement firstOption;

    @FindBy(xpath = "//i[text()='done']")
    public WebElement btnDone;

    @FindBy(xpath = "//i[text()='delete_forever']")
    public WebElement btnDelete;

    @FindBy(xpath = "//mat-option[1]")
    public WebElement nooption;




    public static String Material;
    public void selectMaterial() {
        JavascriptExecutor je = (JavascriptExecutor) localDriver;
        je.executeScript("arguments[0].scrollIntoView(true);", btnAddAcceptedMaterials);

        generics.pause(2);
        generics.clickOn(dpMaterial);
        if (generics.isPresent(firstEnabledOption)) {
            Material = generics.getText(firstEnabledOption);
            generics.clickOn(firstEnabledOption);
            testStepsLog("Clicked on First Material : " + Material);
            generics.clickOn(btnDone);
            testStepsLog("Material Added");
            generics.pause(4);
        } else{
            Material = generics.getText(firstOption);
            generics.clickOn(nooption);
            generics.clickOn(btnDelete);
            generics.pause(2);
        }

    }

    //==============================================

    @FindBy(xpath = "//span[text()='CONTAINER SIZES']/following-sibling::button/i")
    public WebElement btnAddContainerSize;
    public void clickonAddContainerSizeButton()
    {
        generics.moveTo(tabRollOff);
        generics.clickOn(btnAddContainerSize);
        testStepsLog("Clicked on Add Container size button");
    }
    @FindBy(xpath = "//mat-select[@formcontrolname='editedContainerSizeId']")
    public WebElement dpContainerSize;


    public static String ContainerSize;
    public void selectContainerSize()
    {
        generics.moveTo(btnAddHaulMultiplier);

        generics.pause(5);
        generics.clickOn(dpContainerSize);
        if (generics.isPresent(firstEnabledOption)) {

            ContainerSize = generics.getText(firstEnabledOption);
            generics.clickOn(firstEnabledOption);
            testStepsLog("Clicked on First ContainerSize : " + ContainerSize);
            generics.clickOn(btnDone);
            testStepsLog("ContainerSize Added");
            generics.pause(4);
        }
        else
        {
            ContainerSize = generics.getText(firstOption);
            generics.clickOn(nooption);
            generics.clickOn(btnDelete);
            generics.pause(2);
        }

    }

    //=======================================================


    @FindBy(xpath = "//span[text()='LOADING TIME']/following-sibling::button/i")
    public WebElement btnAddAcceptedLoadingTime;

    public void clickonAddLoaingTimeButton()
    {
        generics.moveTo(btnAddAFees);
        generics.clickOn(btnAddAcceptedLoadingTime);
        testStepsLog("Clicked on Add Loading Time");
    }
    @FindBy(xpath = "//mat-select[@formcontrolname='editedContainerTypeId']")
    public WebElement dpLoadingTime;

    @FindBy(xpath = "//input[@formcontrolname='editedMinutes']")
    public WebElement txtMinutes;

    public static String LoadingTime;
    public static int Minutes;
    public void selectLoadingTime()
    {
        generics.pause(2);
        generics.clickOn(dpLoadingTime);
        if (generics.isPresent(firstEnabledOption)) {

            LoadingTime = generics.getText(firstEnabledOption);
            generics.clickOn(firstEnabledOption);
            testStepsLog("Clicked on First Loading TIme : " + LoadingTime);
            Minutes=generics.getRandomBetween(1,5);
            generics.type(txtMinutes,String.valueOf(Minutes));
            generics.clickOn(btnDone);
            testStepsLog("Loading time Added");
            generics.pause(4);
        }
        else
        {
            LoadingTime = generics.getText(firstOption);
            generics.clickOn(nooption);
            generics.clickOn(btnDelete);
            generics.pause(2);
        }
    }


    //============================================================

    @FindBy(xpath = "//span[text()='HAUL MULTIPLIER']/following-sibling::button/i")
    public WebElement btnAddHaulMultiplier;

    public void clickonAddHaulMultiplierButton()
    {

        generics.clickOn(btnAddHaulMultiplier);
        testStepsLog("Clicked on Add Haul Multiplier");
    }
    @FindBy(xpath = "//mat-select[@formcontrolname='editedHaulTypeId']")
    public WebElement dpHaulMultipler;

    @FindBy(xpath = "//input[@formcontrolname='editedMultiplier']")
    public WebElement txtMultiper;


    public static String HaulMultiplier;
    public static int Multiper;
    public void selectHaulMultipler()
    {
        generics.clickOn(dpHaulMultipler);
        if (generics.isPresent(firstEnabledOption)) {

            HaulMultiplier = generics.getText(firstEnabledOption);
            generics.clickOn(firstEnabledOption);
            testStepsLog("Clicked on First HaulMultiplier: " + HaulMultiplier);
            Multiper=generics.getRandomBetween(1,5);
            generics.type(txtMultiper,String.valueOf(Multiper));
            generics.clickOn(btnDone);
            testStepsLog("HaulMultiplier Added");
            generics.pause(4);
        }
        else
        {
            HaulMultiplier = generics.getText(firstOption);
            generics.clickOn(nooption);
            generics.clickOn(btnDelete);
            generics.pause(2);
        }
    }


    //=====================================================================

    @FindBy(xpath = "//span[text()='Fees']/following-sibling::button/i")
    public WebElement btnAddAFees;

    public void clickonAddFees()
    {
        JavascriptExecutor je = (JavascriptExecutor) localDriver;
        je.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        generics.clickOn(btnAddAFees);
        testStepsLog("Clicked on Add Fees button");
    }
    @FindBy(xpath = "//mat-select[@formcontrolname='editedServiceFeeId']")
    public WebElement dpServiceFee;


    public static String Fees;

    public void selectFees()
    {

        generics.clickOn(dpServiceFee);
        if (generics.isPresent(firstEnabledOption)) {

            Fees = generics.getText(firstEnabledOption);
            generics.clickOn(firstEnabledOption);
            testStepsLog("Clicked on First Fees type: " + Fees);

            generics.clickOn(btnDone);
            testStepsLog("Fees Added");
            generics.pause(4);
        }
        else
        {
            LoadingTime = generics.getText(firstOption);
            generics.clickOn(nooption);
            generics.clickOn(btnDelete);
            generics.pause(2);
        }
    }

    @FindBy(xpath = "//button[contains(text(),'TEMP SERVICE')]")
    public WebElement btntempServices;
    @FindBy(xpath = "//button[contains(text(),'TEMP SERVICE')]/i")
    public WebElement btnTempService;

    public boolean isServiceOrderPageOpen() {
        return generics.isPresent(btnTempService);
    }

    public void clickonTempServicebtn() {
        generics.moveTo(btnTempService);
        JavascriptExecutor js = (JavascriptExecutor) localDriver;
        js.executeScript("window.scrollBy(0,-450)", "");
        generics.scrollToElement(btntempServices);
        generics.clickOn(btntempServices);
    }
    @FindBy(xpath = "(//table//a)[1]")
    public WebElement lnkprospect;

    public static String PN;
    public void OpenProspect()
    {
         PN=generics.getText(lnkprospect);
          testStepsLog("Prospect Name : " + PN);
          generics.clickOn(lnkprospect);
    }

    @FindBy(xpath = "//button[contains(text(),'PROSPECT')]/i")
    public WebElement btnAddProspect;

    public void clickonAddProspectButton() {
        generics.pause(5);
        generics.scrollToElement(btnAddProspect);
        generics.clickOn(btnAddProspect);
        testStepsLog("Clicked ON Add Prospect button");
        generics.pause(2);
    }
    @FindBy(xpath = "//div[text()='Search Customer / Prospect']/following-sibling::div//input")
    public WebElement txtProspectName;

    public void typeProspectname(int row) {
        // CustomerName = generics.getRandomCharacters(10);
               ;
        generics.clickOn(txtProspectName);
        generics.type(txtProspectName, PN);

        generics.pause(5);
    }



    @FindBy(xpath = "//mat-select[@formcontrolname='containerSizeId']")
    public WebElement dpContainerSize2;

    @FindBy(xpath = "//mat-select[@formcontrolname='serviceTypeMaterialId']")
    public WebElement dpserviceTypeMeterial;

    @FindBy(xpath = "//mat-select[@formcontrolname='haulTypeId']")
    public WebElement dpHaultype;

    @FindBy(xpath = "//button[contains(text(),'Calculate')]")
    public WebElement btnCalculate;

    @FindBy(xpath = "//span[contains(text(),'Select') and contains(@class,'option')]")
    public WebElement select;


    public boolean isAddedMaterialDisplayed()
    {
        generics.pause(2);
        generics.moveTo(btnCalculate);
        generics.clickOn(dpserviceTypeMeterial);

        List<WebElement> elemnent=localDriver.findElements(By.xpath("//span[@class='mat-option-text' and contains(text(),'"+Material+"')]"));
        if(elemnent.size()>0)
        {
            generics.clickOn(select);
            return true;
        }
        else
        {
            generics.clickOn(select);
            return false;
        }
    }
    public boolean isAddedContainerSizeDisplayed()
    {
        generics.pause(5);
        generics.clickOn(dpContainerSize2);

        List<WebElement> elemnent=localDriver.findElements(By.xpath("//span[@class='mat-option-text' and contains(text(),'"+ContainerSize+"')]"));
        if(elemnent.size()>0)
        {
            generics.clickOn(select);
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean isAddedHaultypeDisplayed()
    {
        generics.pause(2);
        generics.clickOn(dpHaultype);

        List<WebElement> elemnent=localDriver.findElements(By.xpath("//span[@class='mat-option-text' and contains(text(),'"+HaulMultiplier+"')]"));
        if(elemnent.size()>0)
        {
            generics.clickOn(select);
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean isAddedFeesDisplayed()
    {
        generics.pause(2);

        List<WebElement> elemnent=localDriver.findElements(By.xpath("//mat-label[contains(text(),'"+Fees+"')]"));
        if(elemnent.size()>0)
        {

            return true;
        }
        else
        {
            return false;
        }
    }

    @FindBy(xpath = "//th[text()='Material Name']/ancestor::table/tbody//tr[1]/td[2]/span")
    public WebElement firstMaterial;

    @FindBy(xpath = "//th[text()='Material Name']/ancestor::table/tbody//tr[1]/td/button[2]/i")
    public WebElement EditAndDoneButtonMaterial;

    @FindBy(xpath = "//div[contains(text(),'Material has been updated successfully')]")
    public WebElement msgMaterialEdit;

    public void clickonEditbuttonMaterial()
    {
        generics.moveTo(firstMaterial);
        generics.clickOn(EditAndDoneButtonMaterial);
        testStepsLog("Clicked on Edit Icon of first material");

    }
    public boolean isMaterialEdited()
    {
        if(generics.isPresent(msgMaterialEdit))
            return true;
        else
            return false;
    }

    @FindBy(xpath = "//th[text()='Container Size']/ancestor::table/tbody//tr[1]/td[2]/span")
    public WebElement firstContainer;

    @FindBy(xpath = "//th[text()='Container Size']/ancestor::table/tbody//tr[1]/td/button[2]/i")
    public WebElement EditAndDoneButtonContainer;

    @FindBy(xpath = "//div[contains(text(),'Container size has been updated successfully')]")
    public WebElement msgContainerEdit;

    public void clickonEditbuttonContainer()
    {
        generics.moveTo(firstContainer);
        generics.clickOn(EditAndDoneButtonContainer);
        testStepsLog("Clicked on Edit Icon of first ContainerSize");

    }
    public boolean isConainerSizeEdited()
    {
        generics.pause(6);
        if(generics.isPresent(msgContainerEdit))
            return true;
        else
            return false;
    }

    @FindBy(xpath = "//th[text()='Type Of Bin']/ancestor::table/tbody//tr[1]/td[2]/span")
    public WebElement firstBin;

    @FindBy(xpath = "//div[contains(text(),'Loading time has been updated successfully')]")
    public WebElement msgLoadingTimeEdit;

    @FindBy(xpath = "//th[text()='Type Of Bin']/ancestor::table/tbody//tr[1]/td/button[2]/i")
    public WebElement EditAndDoneButtonLoading;

    public void clickonEditbuttonLoadingTime()
    {
        generics.moveTo(firstBin);
        generics.clickOn(EditAndDoneButtonLoading);
        testStepsLog("Clicked on Edit Icon of first Loading Time");

    }
    public boolean isTimeEdited()
    {
        generics.pause(6);
        if(generics.isPresent(msgLoadingTimeEdit))
            return true;
        else
            return false;
    }

    @FindBy(xpath = "//th[text()='Type of Haul']/ancestor::table/tbody//tr[1]/td[2]/span")
    public WebElement firstHaul;

    @FindBy(xpath = "//div[contains(text(),'Haul type has been updated successfully')]")
    public WebElement msgHaultypeEdit;

    @FindBy(xpath = "//th[text()='Type of Haul']/ancestor::table/tbody//tr[1]/td/button[2]/i")
    public WebElement EditAndDoneButtonHaul;

    public void clickonEditbuttonHaulType()
    {
        generics.moveTo(firstHaul);
        generics.clickOn(EditAndDoneButtonHaul);
        testStepsLog("Clicked on Edit Icon of first Type Haul");

    }
    public boolean isHaultypeEdited()
    {
        generics.pause(6);
        if(generics.isPresent(msgHaultypeEdit))
            return true;
        else
            return false;
    }

    @FindBy(xpath = "//th[text()='Fee']/ancestor::table/tbody//tr[1]/td[2]/span")
    public WebElement firstFee;

    @FindBy(xpath = "//div[contains(text(),'Fees have been updated successfully')]")
    public WebElement msgFeeEdit;

    @FindBy(xpath = "//th[text()='Fee']/ancestor::table/tbody//tr[1]/td/button[2]/i")
    public WebElement EditAndDoneButtonFee;

    public void clickonEditbuttonFee()
    {
        generics.moveTo(firstFee);
        generics.clickOn(EditAndDoneButtonFee);
        testStepsLog("Clicked on Edit Icon of first Fee");

    }
    public boolean isFeeEdited()
    {
        generics.pause(8);
        if(generics.isPresent(msgFeeEdit))
            return true;
        else
            return false;
    }


    public boolean searching(String keywords, int column) {
        String xpath = "//table[contains(@class,'MuiTable-root')]/tr";
        localDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        int rowcount = findcount(xpath);
        boolean flag = true;
        for (int i = 1; i < rowcount + 1; i++) {
            String value = "";

            String xpathnew;
            if (column == 1)
                xpathnew = xpath + "[" + i + "]/td[" + column + "]//div";
            else
                xpathnew = xpath + "[" + i + "]/td[" + column + "]/div";

            value = localDriver.findElement(By.xpath(xpathnew)).getText();
            testStepsLog("The Keyword to search is  : " + keywords);
            testStepsLog("The value of row " + i + " and column " + column + " is " + value);
            if (value.contains(keywords)) {
                continue;
            } else {

                flag = false;
            }
        }
        return flag;
    }

    public int findcount(String xpath) {
        localDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        int count = localDriver.findElements(By.xpath(xpath)).size();
        return count;
    }


    public static int generateRandomDigits(int n) {
        int m = (int) Math.pow(10, n - 1);
        return m + new Random().nextInt(9 * m);
    }


    public String getTestData(int req_row, int col) throws IOException {

        FileInputStream file = new FileInputStream(
                System.getProperty("user.dir") + "/src/test//java//gfl//testData//Admin.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet("Sheet1"); // workbook.getSheetAt(0)

        // logger.info("getting from xl row: " + req_row); ;
        XSSFRow row = sheet.getRow(req_row); // Get the row in which data is stored

        // logger.info("getting from xl cell 1");
        try {
            String req_data = row.getCell(col).getStringCellValue(); // Value is always in Cell 1. Cell 0 is description
            // of value
            workbook.close();
            return req_data;
        } catch (Exception e) {
            DataFormatter dataformat = new DataFormatter();
            String req_data = dataformat.formatCellValue(sheet.getRow(req_row).getCell(col)); // Value is always in Cell
            // 1. Cell 0 is
            // description of value
            workbook.close();
            return req_data;
        }

        // XSSFCell cell = sheet.getRow(1).getCell(1);
        // String req_data=cell.getStringCellValue();
        // logger.info("sending back: " + req_data);

    }

    public static void SetTestData(String dta, int rowt, int colt) throws IOException {

        FileInputStream file = new FileInputStream(
                System.getProperty("user.dir") + "/src/test//java//gfl//testData//Admin.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet("Sheet1"); // workbook.getSheetAt(0)

        // logger.info("getting from xl row: " + req_row);
        XSSFRow row = sheet.getRow(rowt); // Get the row in which data is stored
        XSSFCell cell = row.createCell(colt);
        cell.setCellValue(dta);
        FileOutputStream fo = new FileOutputStream(
                System.getProperty("user.dir") + "/src/test//java//gfl//testData//Admin.xlsx");
        workbook.write(fo);
        workbook.close();
        file.close();
        fo.close();

    }

}
