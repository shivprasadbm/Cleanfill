package gflwishes.testcases;

import gflwishes.PageObjects.*;
import gflwishes.base.EndToEndBaseClass;
import gflwishes.base.EnhancedBaseClass;
import gflwishes.utilities.ExcelUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

public class EndToEnd extends EndToEndBaseClass {

    public EndToEnd() {
        log4j = Logger.getLogger("EndToEnd");
    }

    @Test
    public void TC501FM_Create_Vehicle_Functionality() {

        testCaseLog("TC03_Create_Vehicle_Functionality");

        LoginPage login = new LoginPage(fleetMapperDriver);
        vehiclePage vc = new vehiclePage(fleetMapperDriver);

        login.loginAs(USER_NAME, PASSWORD);

        vc.getServiceZone();
        vc.OpenRegion();
        vc.ClickonVehicleTab();
        vc.clickonAddVehiclebutton();
        vc.selectBusinessUnit();
        vc.typeVehicalname();

        vc.typeVin();
        vc.selectServiceZone();
        vc.selectVehicleType();
        vc.clickonSaveButton();
        if (vc.isvehicleCreated()) {
            success("Vehicle Created Successfully");
        } else {
            failure("Vehicle not created");
        }
        vc.copyVehicleInEndToEndExcel();
    }

    @Test
    public void TC002WS_Verify_Create_new_Customer_Functionality() {

        testCaseLog("TC001_TC002_TC003_Verify_Create_new_Customer_Functionality");

        LoginPage login = new LoginPage(wishesDriver);
        LandingPage lp = new LandingPage(wishesDriver);
        CustomerPage cp = new CustomerPage(wishesDriver);
        int rows = cp.getRowsExcel();

        login.loginAs(USER_NAME, PASSWORD);

        if (lp.isUserLoginSuccessful()) {
            success("User Login Successful");
        } else {
            failure("Failed to Login");
        }

        for (int i = 1; i < rows; i++) {

            lp.OpenCustomer();
            try {

                if (cp.isCustomerPageOpen()) {
                    success("Customers page open successfully");
                } else {
                    failure("Customers page not open");
                }
                cp.clickonAddCustomerButton();
                if (cp.isPopupdisplayed()) {
                    success("Popup displayed");
                } else {
                    failure("Popup not displayed");
                }
                cp.typeCustomername(i);
                cp.clickonCreateNewCustomerlnk();
                if (cp.isEnteredCustomerDisplayed()) {
                    success("Entered customer name displayed as companyname");
                } else {
                    failure("Entered customer name not displayed as companyname");
                }
                cp.selectBusinessUnit1(i);
                cp.selectJurisdiction();
                cp.selectcustomertype();
                cp.selectAddressline1();
                //cp.selectcountry();
                //cp.selectState();
                //cp.typeCity();

                cp.selectBillingAddAsCompanyAdd();
                cp.typeContact();
                cp.typeEmail();
                cp.typeContactPosition();
                cp.typePhoneNumber();
                cp.typeExtention();
                cp.typeSiteName(i);
                //cp.selectBusinessUnit();
                cp.selectBusinessType();
                cp.typePoNumber();
                cp.DeSelectsiteAddressesSameAsCompanyAddresses();
                cp.selectAddressline1ofSite(i);
                cp.typePostalcode(i);
                cp.SelectbillToCustomerBillingAddress();
                cp.clickonbtnSaveCustomer();
                if (cp.isSuccessMessageDisplayed()) {
                    success("Success Message displayed");
                } else {
                    failure("Success message not displayed");
                }
                if (cp.isCustomerAdded()) {
                    success("Customer Added successfully");
                } else {
                    failure("Customer not Added successfully");
                }
                cp.getCustomerID(i);

                excelUtils.UpdateExternalSiteID("EndtoEnd", i);
            } catch (Exception e) {
                testStepsLog("Customer not created for " + i);
            }

        }
        sa.assertAll();
    }

    @Test
    public void TC003WS_Verify_Create_Service_order_Functionality() {

        testCaseLog("TC0011_Verify_Create_Service_order_Functionality");

        LandingPage lp = new LandingPage(wishesDriver);
        ServiceOrderPage cp = new ServiceOrderPage(wishesDriver);
        int rows = cp.getRowsExcel();
        LoginPage login = new LoginPage(wishesDriver);
        //login.loginAs(USER_NAME, PASSWORD);
        for (int i = 1; i < rows; i++) {
            try {
                String CustID = cp.getCustomerIDStatus(i);
                if (CustID != "") {
                    lp.OpenServiceOrder();

                    if (cp.isServiceOrderPageOpen()) {
                        success("Service Order page open successfully");
                    } else {
                        failure("Service Order page not open");
                    }
                    cp.clickonTempServicebtn();
                    if (cp.isPopupdisplayed()) {
                        success("Popup displayed");
                    } else {
                        failure("Popup not displayed");
                    }
                    cp.typeCustomername(i);
                    cp.selectCustomer();
                    cp.selectSite(i);

                    if (cp.isSelectedCustomerOpen()) {
                        success("Selected Customer Opens successfully");
                    } else {
                        failure("Selected customer not opend");
                    }

                    cp.selectMaterial(i);
                    cp.selectContainerSize(i);
                    cp.selectHaulType(i);
                    if (cp.isServiceSectionDisplayed()) {
                        success("Services Details Section displayed on Haul Type selection");
                    } else {
                        failure("Services detail section not displayed on Haul type selection");
                    }
                    cp.selectContainerType();
                    //cp.selectRequiestedDate();
                    cp.InsertUploadingTime();
                    //cp.selectDesposibleSite();
                    cp.selectBillingCycle();
                    cp.typeDemurageDay();
                    cp.clickonGetPricing();
                    if (cp.isServiceChargesectionDisplayed()) {
                        success("Get Price button working and displayed service charges");
                    } else {
                        failure("Get Price button not working or service charges not displayed");
                    }
                    cp.typeDispatchnote(i);
                    cp.typeDrivernote(i);
                    cp.clickonSaveServiceButton();
                    if (cp.isServiceSaved()) {
                        success("Save Service button is working");
                    } else {
                        failure("Save Service button is not working");
                    }
                    if (cp.isApproveDisplay()) {
                        cp.clickonApprovebutton();
                        cp.typeApprovalReason();
                        cp.clickonReasonApprovebutton();


                        if (cp.isAllDetailsSavedCurreclty()) {
                            success("Services saved with all details");
                        } else {
                            failure("Services not saved with all details");
                        }
                    }


                    cp.clickonPaynow();
                    cp.SelectPaymentMethod();
                    cp.typeCVV();
                    cp.SelectAddress();
                    cp.SelectConfirmationCheckbox();
                    cp.UploadFile();
                    cp.ClickonPayAmount();
                    if (cp.isPaymentDone()) {
                        success("Paymenet Done successfully");
                        cp.UpdateStatus(i);
                    } else {
                        failure("Payment not done successfully");
                    }

                } else {
                    continue;
                }
            } catch (Exception e) {
                testStepsLog("Service order not Created : " + i);
            }
        }
        sa.assertAll();
    }

    @Test
    public void TC004FM_Verify_User_can_complete_pickup_order() {

        testCaseLog("Verify_User_can_complete_pickup_order");

        LoginPage login = new LoginPage(fleetMapperDriver);
        DispatchPO dispatchPO = new DispatchPO(fleetMapperDriver);
        //ServiceOrderPage cp = new ServiceOrderPage(wishesDriver);
        //login.loginAs(USER_NAME, PASSWORD);
        dispatchPO.openDispatcher();
        int rows = ExcelUtils.getRowsExcel(getClass().getSimpleName());
        System.out.println("Rows = " + rows);
        if (dispatchPO.verifyDispatchPage()) {
            success("User can see the dispatch page.");
        } else {
            failure("ERROR : Dispatch page is not display.");
        }

        for (int count = 1; count < rows; count++) {

            try {
                if (dispatchPO.isPaymentDone(count)) {

                    dispatchPO.getDispatcherName(count);

                    dispatchPO.searchAddress(count);
                    dispatchPO.selectOrder(true);

                    if (dispatchPO.verifyDeliveryDetails(count)) {
                        success("User can see the dispatch oder details.");
                    } else {
                        failure("ERROR : Details are not display proper.");
                    }

                    if (dispatchPO.isOrderFromPast()) {
                        dispatchPO.selectCurrentDate();
                    }

                    dispatchPO.addTruckFromMap(count);

                    dispatchPO.openOrderFromVehiclePane(true);

                    switch (dispatchPO.getOperationType().toLowerCase()) {
                        case "pick up":
                            dispatchPO.startOrder();
                            dispatchPO.enterPickUpContainerName();
                            dispatchPO.enterDriverNotes(count);
                            dispatchPO.completeOrder(count);
                            break;
                        case "delivery":
                            dispatchPO.startOrder();
                            dispatchPO.enterDropOffContainerName();
                            dispatchPO.enterDriverNotes(count);
                            dispatchPO.completeOrder(count);
                            break;
                        case "exchange":
                            if (dispatchPO.isIconUpward()) {
                                dispatchPO.startOrder();
                                dispatchPO.enterPickUpContainerName();
                                dispatchPO.enterTicketDetails(count);
                                dispatchPO.enterDropOffContainerName();
                                dispatchPO.enterDriverNotes(count);
                                dispatchPO.completeOrder(count);
                            } else {
                                dispatchPO.startOrder();
                                dispatchPO.enterDropOffContainerName();
                                dispatchPO.enterPickUpContainerName();
                                dispatchPO.enterTicketDetails(count);
                                dispatchPO.enterDriverNotes(count);
                                dispatchPO.completeOrder(count);
                            }
                            break;
                        case "empty & return":
                            dispatchPO.startOrder();
                            dispatchPO.enterPickUpContainerName();
                            dispatchPO.enterTicketDetails(count);
                            dispatchPO.enterDropOffContainerName();
                            dispatchPO.enterDriverNotes(count);
                            dispatchPO.completeOrder(count);
                            break;
                        case "move":
                            dispatchPO.startOrder();
                            dispatchPO.enterPickUpContainerName();
                            dispatchPO.enterDropOffContainerName();
                            dispatchPO.enterDriverNotes(count);
                            dispatchPO.completeOrder(count);
                            break;
                        case "pickup directive":
                            dispatchPO.startOrder();
                            dispatchPO.enterPickUpContainerName();
                            break;
                        case "drop directive":
                            dispatchPO.startOrder();
                            dispatchPO.enterDropOffContainerName();
                            break;
                    }
                    dispatchPO.setFlag(count, false);
                }
            } catch (Exception ignored) {
                dispatchPO.setFlag(count, true);
            }
        }
    }

    @Test
    public void TC005WS_Verify_All_deatails_in_wishes_if_service_order_status_Change_from_FM() {

        testCaseLog("TC005_Verify_All_deatails_in_wishes_if_service_order_status_Change_from_FM");

        LandingPage lp = new LandingPage(wishesDriver);
        ServiceOrderPage cp = new ServiceOrderPage(wishesDriver);
        int rows = cp.getRowsExcel();


        for (int i = 1; i < rows; i++) {

            try {
                if (cp.isFMCompleted(i)) {

                    lp.OpenServiceOrder();
                    cp.changepagesize();
                    cp.getCustomerName(i);
                    cp.openServiceOrder();
                    if (cp.isProperStatusDisplayed(i)) {
                        success("Proper Status of service order displayed");

                    } else {
                        failure("Proper status of service order not displayed");
                    }
                    if (cp.isProperVehicleDisplayed(i)) {
                        success("Proper assigned Vehicle of service order displayed");

                    } else {
                        failure("Proper Vehicle of service order not displayed");
                    }
                    if (cp.isProperDispatcherDisplayed(i)) {
                        success("Proper Dispatcher value of service order displayed");

                    } else {
                        failure("Proper Dispatcher of service order not displayed");
                    }

                    if (cp.isProperDispatcherNoteDisplayed(i)) {
                        success("Proper Dispatcher note value of service order displayed");

                    } else {
                        failure("Proper Dispatcher note value of service order not displayed");
                    }
                    if (cp.isProperDriverNoteDisplayed(i)) {
                        success("Proper Driver note value of service order displayed");

                    } else {
                        failure("Proper driver note value of service order not displayed");
                    }
                    if (cp.isProperDriverDisplayed(i)) {
                        success("Proper Driver value of service order displayed");

                    } else {
                        failure("Proper Driver value of service order not displayed");
                    }
                    if (cp.isProperScaleDisplayed(i)) {
                        success("Proper Driver note value of service order displayed");

                    } else {
                        failure("Proper driver note value of service order not displayed");
                    }
                    if (cp.isProperweightDisplayed(i)) {
                        success("Proper Driver value of service order displayed");

                    } else {
                        failure("Proper Driver value of service order not displayed");
                    }
                } else {
                    continue;
                }
            } catch (Exception e) {
                testStepsLog("FM to Wishes comparision fail : " + i);
            }

        }
        sa.assertAll();
    }

}