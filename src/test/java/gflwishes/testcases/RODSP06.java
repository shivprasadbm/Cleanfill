package gflwishes.testcases;

import gflwishes.PageObjects.DispatchOR;
import gflwishes.PageObjects.DispatchPO_2;
import gflwishes.PageObjects.LoginPage;
import gflwishes.base.EnhancedBaseClass;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class RODSP06 extends EnhancedBaseClass {

    public RODSP06() {
        log4j = Logger.getLogger("RODSP06");
    }

    @Test
    public void TC034FM_Assign_an_order_which_requested_date_time_is_in_past_to_a_vehicle() {

        testCaseLog("TC034 : Assign an order which requested date time is in past to a vehicle");

        LoginPage login = new LoginPage(fleetMapperDriver);
        DispatchPO_2 dispatchPO_2 = new DispatchPO_2(fleetMapperDriver);

        login.loginAs(USER_NAME, PASSWORD);

        dispatchPO_2.openDispatcher();

        if (dispatchPO_2.verifyDispatchPage()) {
            success("User can see the dispatch page.");
        } else {
            failure("ERROR : Dispatch page is not display.");
        }

        dispatchPO_2.addPastOrderFromMapToTruck();

        if (dispatchPO_2.verifyPastDateTruckMove()) {
            success("Verify error message for Past Date order");
        } else {
            failure("ERROR : Verify error message for Past Date order");
        }

    }

    @Test
    public void TC035FM_Assign_an_order_which_requested_date_time_is_today() {

        testCaseLog("TC035 : Assign an order which requested date time is today");

        LoginPage login = new LoginPage(fleetMapperDriver);
        DispatchPO_2 dispatchPO_2 = new DispatchPO_2(fleetMapperDriver);

        login.loginAs(USER_NAME, PASSWORD);

        dispatchPO_2.openDispatcher();

        if (dispatchPO_2.verifyDispatchPage()) {
            success("User can see the dispatch page.");
        } else {
            failure("ERROR : Dispatch page is not display.");
        }

        if (dispatchPO_2.isTodayOrderNotDisplay()) {
            dispatchPO_2.selectPastOrder();
            dispatchPO_2.setCurrentDate();
        }

        dispatchPO_2.addTodayOrderFromMapToTruck();

        if (dispatchPO_2.verifyOrderAssigned()) {
            success("Verify order assigned successfully");
        } else {
            failure("ERROR : Verify order assigned successfully");
        }

        if (dispatchPO_2.orderStatusAssigned()) {
            success("Verify order status display assigned successfully");
        } else {
            failure("Verify order status display assigned successfully");
        }

        dispatchPO_2.filterOrder("assigned");

        dispatchPO_2.searchAddress();

        if (dispatchPO_2.verifyOrderFilteredAsAssigned()) {
            success("Verify order status display assigned successfully");
        } else {
            failure("Verify order status display assigned successfully");
        }

        sa.assertAll();

    }

    @Test
    public void TC036FM_Assign_an_order_which_requested_date_time_is_in_future() {

        testCaseLog("TC036 : Assign an order which requested date time is in future");

        LoginPage login = new LoginPage(fleetMapperDriver);
        DispatchPO_2 dispatchPO_2 = new DispatchPO_2(fleetMapperDriver);

        login.loginAs(USER_NAME, PASSWORD);

        dispatchPO_2.openDispatcher();

        if (dispatchPO_2.verifyDispatchPage()) {
            success("User can see the dispatch page.");
        } else {
            failure("ERROR : Dispatch page is not display.");
        }

        dispatchPO_2.setTomorrowDateToOrder();

        dispatchPO_2.filterTomorrowOrder();

        dispatchPO_2.addTomorrowOrderFromMapToTruck();

        if (dispatchPO_2.verifyOrderAssigned()) {
            success("Verify order assigned successfully");
        } else {
            failure("ERROR : Verify order assigned successfully");
        }

        if (dispatchPO_2.orderStatusAssigned()) {
            success("Verify order status display assigned successfully");
        } else {
            failure("Verify order status display assigned successfully");
        }

        dispatchPO_2.filterOrder("assigned");

        if (dispatchPO_2.verifyOrderFilteredAsAssigned()) {
            success("Verify order status display assigned successfully");
        } else {
            failure("Verify order status display assigned successfully");
        }

    }

    @Test
    public void TC037FM_Assign_an_order_which_status_of_the_order_is_assigned() {

        testCaseLog("TC037 : Assign an order which status of the order is assigned");

        LoginPage login = new LoginPage(fleetMapperDriver);
        DispatchPO_2 dispatchPO_2 = new DispatchPO_2(fleetMapperDriver);

        login.loginAs(USER_NAME, PASSWORD);

        dispatchPO_2.openDispatcher();

        if (dispatchPO_2.verifyDispatchPage()) {
            success("User can see the dispatch page.");
        } else {
            failure("ERROR : Dispatch page is not display.");
        }

        dispatchPO_2.filterOrder("assigned");

        dispatchPO_2.addTodayAssignedOrderFromMapToTruck();

        if (dispatchPO_2.verifyPastDateTruckMove()) {
            success("Verify error message for As Date order");
        } else {
            failure("ERROR : Verify error message for Past Date order");
        }

    }

    @Test
    public void TC038FM_Assign_an_order_to_a_single_vehicle_which_status_of_the_order_is_In_Progress() {

        testCaseLog("TC038 : Assign an order to a single vehicle which status of the order is 'In Progress'");

        LoginPage login = new LoginPage(fleetMapperDriver);
        DispatchPO_2 dispatchPO_2 = new DispatchPO_2(fleetMapperDriver);
        DispatchOR dispatchOR = new DispatchOR(fleetMapperDriver);

        login.loginAs(USER_NAME, PASSWORD);

        dispatchPO_2.openDispatcher();

        if (dispatchPO_2.verifyDispatchPage()) {
            success("User can see the dispatch page.");
        } else {
            failure("ERROR : Dispatch page is not display.");
        }

        dispatchPO_2.filterOrder("in progress");

        if (dispatchPO_2.isInProgressOrdersNotDisplay()) {
            dispatchPO_2.filterOrder("unassigned");

            Map<String, List<List<String>>> vehicles = dispatchOR.getAllVehicleDetails();

            dispatchPO_2.addTodayOrderFromMapToTruck(vehicles);
            dispatchPO_2.openOrderFromVehiclePane();
            dispatchPO_2.startOrder();

            dispatchPO_2.filterOrder("in progress");
        }

        dispatchPO_2.addTodayInProgressOrderFromMapToTruck();

        if (dispatchPO_2.verifyAssigningVehicleToInProgressOrder()) {
            success("Verify error message for assigning truck to in progress order");
        } else {
            failure("ERROR : Verify error message for assigning truck to in progress order");
        }

    }

    @Test
    public void TC037FM_Assign_an_order_which_status_of_the_order_is_on_hold() {

        testCaseLog("TC037 : Assign an order which status of the order is on hold");

        LoginPage login = new LoginPage(fleetMapperDriver);
        DispatchPO_2 dispatchPO_2 = new DispatchPO_2(fleetMapperDriver);

        login.loginAs(USER_NAME, PASSWORD);

        dispatchPO_2.openDispatcher();

        if (dispatchPO_2.verifyDispatchPage()) {
            success("User can see the dispatch page.");
        } else {
            failure("ERROR : Dispatch page is not display.");
        }

        if (dispatchPO_2.isOnHoldOrderNotDisplay()) {
            if (dispatchPO_2.isTodayOrderNotDisplay()) {
                dispatchPO_2.selectPastOrder();
                dispatchPO_2.setCurrentDate();
            } else {
                dispatchPO_2.selectTodayOrder();
            }
            dispatchPO_2.makeOrderOnHold();
        }

        dispatchPO_2.addTodayOnHoldOrderFromMapToTruck();

        if (dispatchPO_2.verifyOnHoldAssignmentMessage()) {
            success("Verify error message for assigning an on hold order");
        } else {
            failure("ERROR : Verify error message for assigning an on hold order");
        }

    }

    @Test
    public void TC039FM_Assign_an_order_to_a_vehicle_on_the_top_slot_which_already_has_an_assignment_in_progress_as_the_top() {

        testCaseLog("TC039 : Assign/Slide an order to a vehicle on the top slot which already has an assignment" +
                " in progress as the top(first assignment)");

        LoginPage login = new LoginPage(fleetMapperDriver);
        DispatchPO_2 dispatchPO_2 = new DispatchPO_2(fleetMapperDriver);
        DispatchOR dispatchOR = new DispatchOR(fleetMapperDriver);

        login.loginAs(USER_NAME, PASSWORD);

        dispatchPO_2.openDispatcher();

        if (dispatchPO_2.verifyDispatchPage()) {
            success("User can see the dispatch page.");
        } else {
            failure("ERROR : Dispatch page is not display.");
        }

        Map<String, List<List<String>>> vehicles = dispatchOR.getAllVehicleDetails();

        if (dispatchPO_2.isERExOrderNotDisplay()) {
            dispatchPO_2.selectPastERExOrder();
            dispatchPO_2.setCurrentDate();
        }

        dispatchPO_2.addTodayERExOrderFromMapToTruck(vehicles);
        dispatchPO_2.openOrderFromVehiclePane();
        dispatchPO_2.startOrder();

        if (dispatchPO_2.isERExOrderNotDisplay()) {
            dispatchPO_2.selectPastERExOrder();
            dispatchPO_2.setCurrentDate();
        }

        dispatchPO_2.addTodayERExOrderFromMapToTruckWithVehicle();

        dispatchPO_2.moveOrderToTopInVehicle();

        if (dispatchPO_2.verifyMoveOrderTopToInProgress()) {
            success("Verify error message for moving order on the top of In Progress order");
        } else {
            failure("ERROR : Verify error message for moving order on the top of In Progress order");
        }
    }

    @Test
    public void TC040FM_Assign_any_two_orders_to_a_vehicle_with_different_container_sizes() {

        testCaseLog("TC040 : Assign any two orders to a vehicle with different container sizes");

        LoginPage login = new LoginPage(fleetMapperDriver);
        DispatchPO_2 dispatchPO_2 = new DispatchPO_2(fleetMapperDriver);
        DispatchOR dispatchOR = new DispatchOR(fleetMapperDriver);

        login.loginAs(USER_NAME, PASSWORD);

        dispatchPO_2.openDispatcher();

        if (dispatchPO_2.verifyDispatchPage()) {
            success("User can see the dispatch page.");
        } else {
            failure("ERROR : Dispatch page is not display.");
        }

        Map<Integer, List<String>> orders = dispatchOR.getAllOrderDetails();
        Map<String, List<List<String>>> vehicles = dispatchOR.getAllVehicleDetails();

        dispatchPO_2.addPickUpDirectiveOrderToTruck(orders, vehicles);

        orders = dispatchOR.getAllOrderDetails();
        vehicles = dispatchOR.getAllVehicleDetails();

        dispatchPO_2.addDropDirectiveOrderToTruck(orders, vehicles);

        if (dispatchPO_2.verifyActionRequestDisplay()) {
            success("Verify Action Request happen for the two different containers in a vehicle");
        } else {
            failure("ERROR : Verify Action Request happen for the two different containers in a vehicle");
        }


    }

    @Test
    public void TC000FM_Unassign_vehicle_from_the_assigned_order() {

        testCaseLog("TC000 : Unassign vehicle from the assigned order");

        LoginPage login = new LoginPage(fleetMapperDriver);
        DispatchPO_2 dispatchPO_2 = new DispatchPO_2(fleetMapperDriver);

        login.loginAs(USER_NAME, PASSWORD);

        dispatchPO_2.openDispatcher();

        if (dispatchPO_2.verifyDispatchPage()) {
            success("User can see the dispatch page.");
        } else {
            failure("ERROR : Dispatch page is not display.");
        }

        if (dispatchPO_2.isTodayOrderNotDisplay()) {
            dispatchPO_2.selectPastOrder();
            dispatchPO_2.setCurrentDate();
        }

        dispatchPO_2.addTodayOrderFromMapToTruck();

        if (dispatchPO_2.verifyOrderAssigned()) {
            success("Verify order assigned successfully");
        } else {
            failure("ERROR : Verify order assigned successfully");
        }

        dispatchPO_2.openOrderFromVehiclePane();

        dispatchPO_2.unassignOrder();
        dispatchPO_2.filterOrder("assigned");

        if (dispatchPO_2.verifyOrderUnAssigned()) {
            success("Verify order unassigned successfully");
        } else {
            failure("ERROR : Verify order unassigned successfully");
        }

    }

    @Test
    public void TC000FM_Unassign_vehicle_from_the_in_progress_order() {

        testCaseLog("TC000 : Unassign vehicle from the in progress order");

        LoginPage login = new LoginPage(fleetMapperDriver);
        DispatchPO_2 dispatchPO_2 = new DispatchPO_2(fleetMapperDriver);

        login.loginAs(USER_NAME, PASSWORD);

        dispatchPO_2.openDispatcher();

        if (dispatchPO_2.verifyDispatchPage()) {
            success("User can see the dispatch page.");
        } else {
            failure("ERROR : Dispatch page is not display.");
        }

        if (dispatchPO_2.isTodayOrderNotDisplay()) {
            dispatchPO_2.selectPastOrder();
            dispatchPO_2.setCurrentDate();
        }

        dispatchPO_2.addTodayOrderFromMapToTruck();

        if (dispatchPO_2.verifyOrderAssigned()) {
            success("Verify order assigned successfully");
        } else {
            failure("ERROR : Verify order assigned successfully");
        }

        dispatchPO_2.openOrderFromVehiclePane();
        dispatchPO_2.startOrder();

        dispatchPO_2.openOrderFromVehiclePane();
        dispatchPO_2.unassignOrder();
        dispatchPO_2.filterOrder("in progress");

        if (dispatchPO_2.verifyOrderUnAssigned()) {
            success("Verify order unassigned successfully");
        } else {
            failure("ERROR : Verify order unassigned successfully");
        }

    }

    @Test
    public void TC000FM_Unassign_vehicle_from_the_in_progress_order_after_container_name_added() {

        testCaseLog("TC000 : Unassign vehicle from the in progress order after container name added");

        LoginPage login = new LoginPage(fleetMapperDriver);
        DispatchPO_2 dispatchPO_2 = new DispatchPO_2(fleetMapperDriver);

        login.loginAs(USER_NAME, PASSWORD);

        dispatchPO_2.openDispatcher();

        if (dispatchPO_2.verifyDispatchPage()) {
            success("User can see the dispatch page.");
        } else {
            failure("ERROR : Dispatch page is not display.");
        }

        if (dispatchPO_2.isTodayOrderNotDisplay()) {
            dispatchPO_2.selectPastOrder();
            dispatchPO_2.setCurrentDate();
        }

        dispatchPO_2.addTodayOrderFromMapToTruck();

        if (dispatchPO_2.verifyOrderAssigned()) {
            success("Verify order assigned successfully");
        } else {
            failure("ERROR : Verify order assigned successfully");
        }

        dispatchPO_2.openOrderFromVehiclePane();
        dispatchPO_2.startOrder();

        dispatchPO_2.openOrderFromVehiclePane();

        dispatchPO_2.enterPickUpContainerName();

        if (dispatchPO_2.verifyOrderCantUnassigned()) {
            success("Verify order can't be unassigned after container name added");
        } else {
            failure("ERROR : Verify order can't be unassigned after container name added");
        }

    }

}
