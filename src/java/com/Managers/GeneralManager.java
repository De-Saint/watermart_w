/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Managers;

import com.Tables.Tables;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Saint
 */
public class GeneralManager {

    public static int AdminUserID = 1;

    public GeneralManager() {

    }

    public static int AddProduct(int UserID, int categoryid, String name, String price, String properties) throws ClassNotFoundException, SQLException {
        HashMap<String, Object> tableData = new HashMap<>();
        tableData.put(Tables.ProductTable.ProductCategoryID, categoryid);
        tableData.put(Tables.ProductTable.Name, name);
        tableData.put(Tables.ProductTable.Price, price);
        tableData.put(Tables.ProductTable.Properties, properties);
        tableData.put(Tables.ProductTable.SupplierUserID, UserID);
        int result = DBManager.insertTableDataReturnID(Tables.ProductTable.Table, tableData, "");
        return result;
    }

    public static String AddLocation(int UserID, String name, String fees) throws ClassNotFoundException, SQLException {
        HashMap<String, Object> tableData = new HashMap<>();
        tableData.put(Tables.LocationTable.Name, name);
        tableData.put(Tables.LocationTable.Fees, fees);
        tableData.put(Tables.LocationTable.SupplierUserID, UserID);
        String result = DBManager.insertTableData(Tables.LocationTable.Table, tableData, "");
        return result;
    }

    public static String RateSupplier(int SupplierUserID, int CustomerUserID, int RateValue) throws ClassNotFoundException, SQLException {
        String result = "";
        HashMap<String, Object> tableData = new HashMap<>();
        tableData.put(Tables.RatingTable.Value, RateValue);
        tableData.put(Tables.RatingTable.SupplierUserID, SupplierUserID);
        tableData.put(Tables.RatingTable.CustomerUserID, CustomerUserID);
        result = DBManager.insertTableData(Tables.RatingTable.Table, tableData, "");
        result = UpdateSupplierRating(SupplierUserID);
        return result;
    }

    public static String UpdateSupplierRating(int SupplierUserID) throws ClassNotFoundException, SQLException {
        String result = "";
        int totalRateValue = 0;
        ArrayList<Integer> RateIDs = DBManager.GetIntArrayList(Tables.RatingTable.ID, Tables.RatingTable.Table, "where " + Tables.RatingTable.SupplierUserID + " = " + SupplierUserID);
        if (!RateIDs.isEmpty()) {
            for (int ID : RateIDs) {
                int ratevalue = DBManager.GetInt(Tables.RatingTable.Value, Tables.RatingTable.Table, "where " + Tables.RatingTable.ID + " = " + ID);
                totalRateValue += ratevalue;
            }
        }
        int currentRateValue = RateIDs.size();
        try {
            int newRateValue = totalRateValue / currentRateValue;
            result = DBManager.UpdateIntData(Tables.SupplierTable.RateValue, newRateValue, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + SupplierUserID);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public static String UpdateLocation(int locationid, String name, String fees) throws ClassNotFoundException, SQLException {
        String result = DBManager.UpdateStringData(Tables.LocationTable.Table, Tables.LocationTable.Name, name, "where " + Tables.LocationTable.ID + " = " + locationid);
        DBManager.UpdateStringData(Tables.LocationTable.Table, Tables.LocationTable.Fees, fees, "where " + Tables.LocationTable.ID + " = " + locationid);
        return result;
    }

    public static String DeleteLocation(int LocationID) throws ClassNotFoundException, SQLException, ParseException {
        int UserID = DBManager.GetInt(Tables.LocationTable.SupplierUserID, Tables.LocationTable.Table, "where " + Tables.LocationTable.ID + " = " + LocationID);
        UserManager.sendMemberMessage(UserID, "Added New Delivery Location", "New Location", 1);
        String result = DBManager.DeleteObject(Tables.LocationTable.Table, "where " + Tables.LocationTable.ID + " = " + LocationID);
        return result;
    }

    public static ArrayList<Integer> getCustomerIds(int start, int limit) throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDS = DBManager.GetIntArrayListDescending(Tables.UserTable.ID, Tables.UserTable.Table, "Where " + Tables.UserTable.UserType + " = 'Customer' ORDER by lastname ASC LIMIT " + start + ", " + limit);
        return IDS;
    }

    public static ArrayList<Integer> getSupplierIds(int start, int limit) throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDS = DBManager.GetIntArrayListDescending(Tables.UserTable.ID, Tables.UserTable.Table, "Where " + Tables.UserTable.UserType + " = 'Supplier' ORDER by lastname ASC LIMIT " + start + ", " + limit);
        return IDS;
    }

    public static ArrayList<Integer> getMFRateSupplierIds() throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDS = DBManager.GetIntArrayListDescending(Tables.SupplierTable.UserID, Tables.SupplierTable.Table, "Where " + Tables.SupplierTable.PaymentPlanID + " = " + 1);
        return IDS;
    }

    public static ArrayList<Integer> getPPTransactionSupplierIds() throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDS = DBManager.GetIntArrayListDescending(Tables.SupplierTable.UserID, Tables.SupplierTable.Table, "Where " + Tables.SupplierTable.PaymentPlanID + " = " + 2);
        return IDS;
    }

    public static ArrayList<Integer> getSupplierIDS() throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDS = DBManager.GetIntArrayListDescending(Tables.UserTable.ID, Tables.UserTable.Table, "Where " + Tables.UserTable.UserType + " = 'Supplier'");
        return IDS;
    }

    public static ArrayList<Integer> getActivatedSupplierIds() throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDS = DBManager.GetIntArrayListDescending(Tables.SupplierTable.UserID, Tables.SupplierTable.Table, "Where " + Tables.SupplierTable.Status + " = 'Activated'");
        return IDS;
    }

    public static ArrayList<Integer> getNonActivatedSupplierIds() throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDS = DBManager.GetIntArrayListDescending(Tables.SupplierTable.UserID, Tables.SupplierTable.Table, "Where " + Tables.SupplierTable.Status + " != 'Activated'");
        return IDS;
    }

    public static String DeleteCustomerAccount(int UserID) throws ClassNotFoundException, SQLException {
        String result = "";
        result = DBManager.DeleteObject(Tables.UserTable.Table, "where " + Tables.UserTable.ID + " = " + UserID);
        DBManager.DeleteObject(Tables.CustomerTable.Table, "where " + Tables.CustomerTable.UserID + " = " + UserID);

        ArrayList<Integer> UserOrderIds = DBManager.GetIntArrayListDescending(Tables.OrderTable.ID, Tables.OrderTable.Table, "where " + Tables.OrderTable.SupplierUserID + " = " + UserID);
        if (!UserOrderIds.isEmpty()) {
            for (int orderid : UserOrderIds) {
                String Status = BookingManager.getOrderStatus(orderid);
                if (Status.equals("Delivered")) {
                    BookingManager.UpdateDeletedOrder(orderid);
                }
                DBManager.DeleteObject(Tables.OrderTable.Table, "where " + Tables.OrderTable.ID + " = " + orderid);
            }
        }
        ArrayList<Integer> UserMessageIds = DBManager.GetIntArrayListDescending(Tables.MessageTable.ID, Tables.MessageTable.Table, "where " + Tables.MessageTable.FromMemberID + " = " + UserID);
        if (!UserMessageIds.isEmpty()) {
            for (int msgid : UserMessageIds) {
                DBManager.DeleteObject(Tables.MessageTable.Table, "where " + Tables.MessageTable.ID + " = " + msgid);
            }
        }

        DBManager.DeleteObject(Tables.RecoveryTable.Table, "where " + Tables.RecoveryTable.UserID + " = " + UserID);
        return result;
    }

    public static String DeleteSupplierAccount(int UserID) throws ClassNotFoundException, SQLException {
        String result = "";
        result = DBManager.DeleteObject(Tables.UserTable.Table, "where " + Tables.UserTable.ID + " = " + UserID);
        DBManager.DeleteObject(Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + UserID);

        ArrayList<Integer> UserOrderIds = DBManager.GetIntArrayListDescending(Tables.OrderTable.ID, Tables.OrderTable.Table, "where " + Tables.OrderTable.SupplierUserID + " = " + UserID);
        if (!UserOrderIds.isEmpty()) {
            for (int orderid : UserOrderIds) {
                String Status = BookingManager.getOrderStatus(orderid);
                if (Status.equals("Delivered")) {
                    BookingManager.UpdateDeletedOrder(orderid);
                }
                DBManager.DeleteObject(Tables.OrderTable.Table, "where " + Tables.OrderTable.ID + " = " + orderid);
            }
        }
        ArrayList<Integer> UserMessageIds = DBManager.GetIntArrayListDescending(Tables.MessageTable.ID, Tables.MessageTable.Table, "where " + Tables.MessageTable.FromMemberID + " = " + UserID);
        if (!UserMessageIds.isEmpty()) {
            for (int msgid : UserMessageIds) {
                DBManager.DeleteObject(Tables.MessageTable.Table, "where " + Tables.MessageTable.ID + " = " + msgid);
            }
        }

        DBManager.DeleteObject(Tables.RecoveryTable.Table, "where " + Tables.RecoveryTable.UserID + " = " + UserID);
        return result;
    }

    public static ArrayList<Integer> getSentMessageIDs(int UserID) throws ClassNotFoundException, SQLException {
        ArrayList<Integer> ids = new ArrayList<>();
        ids = DBManager.GetIntArrayList(Tables.MessageTable.ID, Tables.MessageTable.Table, "where " + Tables.MessageTable.FromMemberID + " = " + UserID);//"where from_member_id = " + meid);
        return ids;
    }

    public static ArrayList<Integer> getInboxMessageIDs(int UserID) throws ClassNotFoundException, SQLException {
        ArrayList<Integer> ids = new ArrayList<>();
        ids = DBManager.GetIntArrayList(Tables.MessageTable.ID, Tables.MessageTable.Table, "where " + Tables.MessageTable.ToMemberID + " = " + UserID);
        return ids;
    }

    public static HashMap<String, String> getMessageDetails(int msgID) throws ClassNotFoundException, SQLException {
        HashMap<String, String> Details = DBManager.GetTableData(Tables.MessageTable.Table, "where " + Tables.MessageTable.ID + " = " + msgID);
        int senderid = Integer.parseInt(Details.get(Tables.MessageTable.FromMemberID));
        String sendername = getUserName(senderid);
        Details.put("sendername", sendername);
        int recieverid = Integer.parseInt(Details.get(Tables.MessageTable.ToMemberID));
        String recievername = getUserName(recieverid);
        Details.put("recievername", recievername);
        return Details;
    }

    public static String getUserName(int UserID) throws ClassNotFoundException, SQLException {
        String result = DBManager.GetString(Tables.UserTable.LastName, Tables.UserTable.Table, "where " + Tables.UserTable.ID + " = " + UserID);
        return result;
    }

    public static String getPaymentPlanName(int PlanID) throws ClassNotFoundException, SQLException {
        String result = DBManager.GetString(Tables.PaymentPlanTable.Name, Tables.PaymentPlanTable.Table, "where " + Tables.PaymentPlanTable.ID + " = " + PlanID);
        return result;
    }

    public static int getAdminAccountBalance() throws ClassNotFoundException, SQLException {
        int result = DBManager.GetInt(Tables.AdminTable.AccountBalance, Tables.AdminTable.Table, "where " + Tables.AdminTable.UserID + " = " + AdminUserID);
        return result;
    }

    public static int getAdminTransactionNumber() throws ClassNotFoundException, SQLException {
        int result = DBManager.GetInt(Tables.AdminTable.TransactionNumber, Tables.AdminTable.Table, "where " + Tables.AdminTable.UserID + " = " + AdminUserID);
        return result;
    }

    public static String MarkMessageAsRead(int MessageID) throws ClassNotFoundException, SQLException {
        String result = DBManager.UpdateIntData(Tables.MessageTable.IsRead, 1, Tables.MessageTable.Table, "where " + Tables.MessageTable.ID + " = " + MessageID);
        return result;
    }

    public static String UpdateSupplierPaymentPlan(int UserID) throws ClassNotFoundException, SQLException {
        int payplan = BookingManager.getSupplierPaymentPlanID(UserID);
        String result = "";
        if (payplan == 1) {
            result = DBManager.UpdateIntData(Tables.SupplierTable.PaymentPlanID, 2, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + UserID);
        } else if (payplan == 2) {
            result = DBManager.UpdateIntData(Tables.SupplierTable.PaymentPlanID, 1, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + UserID);
        }
        return result;
    }

    public static String UpdateUserAccount(int UserID, String Action) throws ClassNotFoundException, SQLException, Exception {
        String result = "";
        String SupplierName = "";
        if (Action.equals("Deactivate")) {
            result = DBManager.UpdateStringData(Tables.SupplierTable.Table, Tables.SupplierTable.Status, "Deactivated", "where " + Tables.SupplierTable.UserID + " = " + UserID);
            PushManager.sendPushNotification("Account Deactivated", "Your Account has been deactivated by WaterMart", "", UserID);
            SupplierName = GeneralManager.getUserName(UserID);
            PushManager.sendPushNotification("Account Deactivated", "You deactivated " + SupplierName + " Account", "", 1);
        } else if (Action.equals("Activate")) {
            DBManager.UpdateStringData(Tables.SupplierTable.Table, Tables.SupplierTable.Status, "Activated", "where " + Tables.SupplierTable.UserID + " = " + UserID);
            PushManager.sendPushNotification("Account Activated", "Your Account has been activated by WaterMart", "", UserID);
            SupplierName = GeneralManager.getUserName(UserID);
            PushManager.sendPushNotification("Account Activated", "You activated " + SupplierName + " Account",  "", 1);
            DBManager.UpdateCurrentDate(Tables.SupplierTable.Table, Tables.SupplierTable.PaymentDate, "where " + Tables.SupplierTable.UserID + " = " + UserID);
            Date DueDate = UserManager.DueDate();
            String NewDate = "" + DueDate;
            result = DBManager.UpdateStringData(Tables.SupplierTable.Table, Tables.SupplierTable.PaymentDueDate, NewDate, "where " + Tables.SupplierTable.UserID + " = " + UserID);
        } else if (Action.equals("ResetPaymentDueDate")) {
            String res = DBManager.GetString(Tables.SupplierTable.Status, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + UserID);
            if (res.equals("Activated")) {
                int payplan = BookingManager.getSupplierPaymentPlanID(UserID);
                if (payplan == 2) {
                    DBManager.UpdateIntData(Tables.SupplierTable.AdminAccountBalance, 0, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + UserID);
                    DBManager.UpdateIntData(Tables.SupplierTable.AccountBalance, 0, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + UserID);
                }
                DBManager.UpdateIntData(Tables.SupplierTable.TransactionNumber, 0, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + UserID);
                Date DueDate = UserManager.DueDate();
                String NewDate = "" + DueDate;
                result = DBManager.UpdateStringData(Tables.SupplierTable.Table, Tables.SupplierTable.PaymentDueDate, NewDate, "where " + Tables.SupplierTable.UserID + " = " + UserID);
                PushManager.sendPushNotification("New Payment Due Date", "Your New Payment Due Date is " + NewDate,  "",  UserID);
                SupplierName = GeneralManager.getUserName(UserID);
                PushManager.sendPushNotification("New Payment Due Date", SupplierName + " New Payment Due Date is " + NewDate,  "", 1);
            } else {
                result = "failed";
            }

        } else if (Action.equals("Unblock")) {
            result = DBManager.UpdateStringData(Tables.CustomerTable.Table, Tables.CustomerTable.Status, "UnBlocked", "where " + Tables.CustomerTable.UserID + " = " + UserID);
            PushManager.sendPushNotification("Account UnBlocked", "Your Account has been unblocked by WaterMart",  "", UserID);
            SupplierName = GeneralManager.getUserName(UserID);
            PushManager.sendPushNotification("Account UnBlocked", "You unblocked " + SupplierName + " account",  "", 1);
        } else if (Action.equals("Block")) {
            result = DBManager.UpdateStringData(Tables.CustomerTable.Table, Tables.CustomerTable.Status, "Blocked", "where " + Tables.CustomerTable.UserID + " = " + UserID);
            PushManager.sendPushNotification("Account Blocked", "Your Account has been blocked by WaterMart",  "",  UserID);
            SupplierName = GeneralManager.getUserName(UserID);
            PushManager.sendPushNotification("Account Blocked", "You blocked " + SupplierName + " account",  "", 1);
        }
        return result;
    }

    public static String DeleteMessage(int MessageID) throws ClassNotFoundException, SQLException {
        String result = DBManager.DeleteObject(Tables.MessageTable.Table, "where " + Tables.MessageTable.ID + " = " + MessageID);
        return result;
    }

    public static String DeleteProduct(int ProductID) throws ClassNotFoundException, SQLException {
        String result = DBManager.DeleteObject(Tables.ProductTable.Table, "where " + Tables.ProductTable.ID + " = " + ProductID);
        return result;
    }

    public static ArrayList<Integer> getProductCategoryIds(int UserID) throws ClassNotFoundException, SQLException {
        ArrayList<Integer> stateIDs = new ArrayList<>();
        int watercatid = DBManager.GetInt(Tables.SupplierTable.WaterCategoryID, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + UserID);
        stateIDs = DBManager.GetIntArrayListDescending(Tables.ProductCategoryTable.ID, Tables.ProductCategoryTable.Table, "where " + Tables.ProductCategoryTable.WaterCategoryID + " = " + watercatid);
        return stateIDs;
    }

    public static HashMap<String, String> GetProductCategory(int ID) throws ClassNotFoundException, SQLException {
        HashMap<String, String> Details = DBManager.GetTableData(Tables.ProductCategoryTable.Table, "where " + Tables.ProductCategoryTable.ID + " = " + ID);
        return Details;
    }

    public static String UpdateProduct(int productid, String name, String price, String properties) throws ClassNotFoundException, SQLException {
        String result = "";
        result = DBManager.UpdateStringData(Tables.ProductTable.Table, Tables.ProductTable.Name, name, "where " + Tables.ProductTable.ID + " = " + productid);
        DBManager.UpdateStringData(Tables.ProductTable.Table, Tables.ProductTable.Price, price, "where " + Tables.ProductTable.ID + " = " + productid);
        DBManager.UpdateStringData(Tables.ProductTable.Table, Tables.ProductTable.Properties, properties, "where " + Tables.ProductTable.ID + " = " + productid);
        return result;
    }

    public static ArrayList<Integer> GetBuyDomesticWater() throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDs = new ArrayList<>();
        ArrayList<Integer> IDs2 = new ArrayList<>();
        ArrayList<Integer> IDs3 = new ArrayList<>();
        IDs = DBManager.GetIntArrayListDescending(Tables.ProductTable.ID, Tables.ProductTable.Table, "where " + Tables.ProductTable.ProductCategoryID + " = " + 1);
        IDs2 = DBManager.GetIntArrayListDescending(Tables.ProductTable.ID, Tables.ProductTable.Table, "where " + Tables.ProductTable.ProductCategoryID + " = " + 2);
        IDs3 = DBManager.GetIntArrayListDescending(Tables.ProductTable.ID, Tables.ProductTable.Table, "where " + Tables.ProductTable.ProductCategoryID + " = " + 3);
        IDs.addAll(IDs2);
        IDs.addAll(IDs3);
        return IDs;
    }

    public static ArrayList<Integer> GetBuyDomesticAccessory() throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDs = new ArrayList<>();
        ArrayList<Integer> IDs2 = new ArrayList<>();
        ArrayList<Integer> IDs3 = new ArrayList<>();
        ArrayList<Integer> IDs4 = new ArrayList<>();
        IDs = DBManager.GetIntArrayListDescending(Tables.ProductTable.ID, Tables.ProductTable.Table, "where " + Tables.ProductTable.ProductCategoryID + " = " + 5);
        IDs2 = DBManager.GetIntArrayListDescending(Tables.ProductTable.ID, Tables.ProductTable.Table, "where " + Tables.ProductTable.ProductCategoryID + " = " + 6);
        IDs3 = DBManager.GetIntArrayListDescending(Tables.ProductTable.ID, Tables.ProductTable.Table, "where " + Tables.ProductTable.ProductCategoryID + " = " + 7);
        IDs4 = DBManager.GetIntArrayListDescending(Tables.ProductTable.ID, Tables.ProductTable.Table, "where " + Tables.ProductTable.ProductCategoryID + " = " + 8);
        IDs.addAll(IDs2);
        IDs.addAll(IDs3);
        IDs.addAll(IDs4);
        return IDs;
    }

    public static ArrayList<Integer> GetBuyIndustrialWater() throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDs = new ArrayList<>();
        IDs = DBManager.GetIntArrayListDescending(Tables.ProductTable.ID, Tables.ProductTable.Table, "where " + Tables.ProductTable.ProductCategoryID + " = " + 4);
        return IDs;
    }

    public static ArrayList<Integer> GetProductByCategoryID(int CategoryID) throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDs = new ArrayList<>();
        IDs = DBManager.GetIntArrayListDescending(Tables.ProductTable.ID, Tables.ProductTable.Table, "where " + Tables.ProductTable.ProductCategoryID + " = " + CategoryID);
        return IDs;
    }

    public static ArrayList<Integer> GetMonthlySupplierIDs() throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDs = new ArrayList<>();
        IDs = DBManager.GetIntArrayListDescending(Tables.SupplierTable.ID, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.PaymentPlanID + " = " + 1);
        return IDs;
    }
}
