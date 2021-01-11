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
import java.util.Random;

/**
 *
 * @author Saint
 */
public class BookingManager {

    public static int AdminUserID = 1;

    public BookingManager() {

    }

    public static int PlaceOrder(int CustomerUserID, int SupplierUserID, String OrderDetails, String TotalAmount, String deliveryaddress, String deliveryfee) throws ClassNotFoundException, SQLException, ParseException {
        String OrderNumber = CreateOrderCode(8);
        Date CurrentDate = UserManager.CurrentDate();
        String CurrentTime = UserManager.CurrentTime();
        HashMap<String, Object> tableData = new HashMap<>();
        tableData.put(Tables.OrderTable.CustomerUserID, CustomerUserID);
        tableData.put(Tables.OrderTable.SupplierUserID, SupplierUserID);
        tableData.put(Tables.OrderTable.Amount, TotalAmount);
        tableData.put(Tables.OrderTable.OrderNumber, OrderNumber);
        tableData.put(Tables.OrderTable.ProductDetails, OrderDetails);
        tableData.put(Tables.OrderTable.Status, "Pending");
        tableData.put(Tables.OrderTable.SubStatus, "Pending Confirmation");
        tableData.put(Tables.OrderTable.BookedDate, CurrentDate);
        tableData.put(Tables.OrderTable.BookedTime, CurrentTime);
        tableData.put(Tables.OrderTable.DeliveryAddress, deliveryaddress);
        tableData.put(Tables.OrderTable.DeliveryFees, deliveryfee);
        int result = DBManager.insertTableDataReturnID(Tables.OrderTable.Table, tableData, "");
        return result;
    }

    public static String CreateOrderCode(int LengthOfCode) {
        String OrderCode = "";
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < LengthOfCode; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        OrderCode = sb.toString();
        return OrderCode;
    }

    public static ArrayList<Integer> GetCustomerOrderIDS(int UserID) throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDS = new ArrayList<>();
        IDS = DBManager.GetIntArrayList(Tables.OrderTable.ID, Tables.OrderTable.Table, "where " + Tables.OrderTable.CustomerUserID + " = " + UserID);
        return IDS;
    }

    public static ArrayList<Integer> GetSupplierOrderIDS(int UserID) throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDS = new ArrayList<>();
        IDS = DBManager.GetIntArrayList(Tables.OrderTable.ID, Tables.OrderTable.Table, "where " + Tables.OrderTable.SupplierUserID + " = " + UserID);
        return IDS;
    }

    public static ArrayList<Integer> getOrderIDS() throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDS = new ArrayList<>();
        IDS = DBManager.GetIntArrayList(Tables.OrderTable.ID, Tables.OrderTable.Table, "");
        return IDS;
    }

    public static HashMap<String, String> GetOrderDetails(int OrderID) throws ClassNotFoundException, SQLException {
        HashMap<String, String> Details = DBManager.GetTableData(Tables.OrderTable.Table, "where " + Tables.OrderTable.ID + " = " + OrderID);
        return Details;
    }

    public static String GetOrderProductDetails(int OrderID) throws ClassNotFoundException, SQLException {
        String result = DBManager.GetString(Tables.OrderTable.ProductDetails, Tables.OrderTable.Table, "where " + Tables.OrderTable.Status + " = 'Delivered' AND " + Tables.OrderTable.ID + " = " + OrderID);
        return result;
    }

    public static HashMap<String, String> GetPendingOrderDetails(int OrderID) throws ClassNotFoundException, SQLException {
        HashMap<String, String> Details = DBManager.GetTableData(Tables.OrderTable.Table, "where " + Tables.OrderTable.Status + " = 'Pending' AND " + Tables.OrderTable.ID + " = " + OrderID + " ORDER by id DESC");
        return Details;
    }

    public static HashMap<String, String> GetDeliveredOrderDetails(int OrderID) throws ClassNotFoundException, SQLException {
        HashMap<String, String> Details = DBManager.GetTableData(Tables.OrderTable.Table, "where " + Tables.OrderTable.Status + " = 'Delivered' AND " + Tables.OrderTable.ID + " = " + OrderID + " ORDER by id DESC");
        return Details;
    }

    public static HashMap<String, String> GetCancelledOrderDetails(int OrderID) throws ClassNotFoundException, SQLException {
        HashMap<String, String> Details = DBManager.GetTableData(Tables.OrderTable.Table, "where " + Tables.OrderTable.Status + " = 'Cancelled' AND " + Tables.OrderTable.ID + " = " + OrderID + " ORDER by id DESC");
        return Details;
    }

    public static ArrayList<HashMap<String, String>> GetOrderProductdetails(String productdetails) throws ClassNotFoundException, SQLException {
        ArrayList<HashMap<String, String>> ProductList = new ArrayList<>();
        String details[] = productdetails.split(";");
        for (String record : details) {
            String productid = record.split(",")[0];
            int ProductID = Integer.parseInt(productid);
            HashMap<String, String> ProductDet = UserManager.GetProduct(ProductID);
            ProductList.add(ProductDet);
        }
        return ProductList;
    }

    public static String getOrderStatus(int OrderID) throws ClassNotFoundException, SQLException {
        String result = "";
        result = DBManager.GetString(Tables.OrderTable.Status, Tables.OrderTable.Table, "where " + Tables.OrderTable.ID + " = " + OrderID);
        return result;
    }

    public static String getOrderNumber(int OrderID) throws ClassNotFoundException, SQLException {
        String result = "";
        result = DBManager.GetString(Tables.OrderTable.OrderNumber, Tables.OrderTable.Table, "where " + Tables.OrderTable.ID + " = " + OrderID);
        return result;
    }

    public static int getOrderSupplierUserID(int OrderID) throws ClassNotFoundException, SQLException {
        int result = 0;
        result = DBManager.GetInt(Tables.OrderTable.SupplierUserID, Tables.OrderTable.Table, "where " + Tables.OrderTable.ID + " = " + OrderID);
        return result;
    }

    public static int getOrderCustomerUserID(int OrderID) throws ClassNotFoundException, SQLException {
        int result = 0;
        result = DBManager.GetInt(Tables.OrderTable.CustomerUserID, Tables.OrderTable.Table, "where " + Tables.OrderTable.ID + " = " + OrderID);
        return result;
    }

    public static int getSupplierPaymentPlanID(int SupplierID) throws ClassNotFoundException, SQLException {
        int result = 0;
        result = DBManager.GetInt(Tables.SupplierTable.PaymentPlanID, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + SupplierID);
        return result;
    }

    public static String CancelOrderRequest(int OrderID, String userName) throws ClassNotFoundException, SQLException {
        String result = "";
        DBManager.UpdateStringData(Tables.OrderTable.Table, Tables.OrderTable.Status, "Cancelled", "where " + Tables.OrderTable.ID + " = " + OrderID);
        result = DBManager.UpdateStringData(Tables.OrderTable.Table, Tables.OrderTable.SubStatus, "Cancelled by " + userName, "where " + Tables.OrderTable.ID + " = " + OrderID);
        return result;
    }

    public static String ConfirmOrder(int OrderID, String Status, int UserID) throws ClassNotFoundException, SQLException, ParseException, Exception {
        String result = "";
        DBManager.UpdateStringData(Tables.OrderTable.Table, Tables.OrderTable.Status, Status, "where " + Tables.OrderTable.ID + " = " + OrderID);
        UpdateConfirmedBy(OrderID, UserID);
        String username = UserManager.getUserName(UserID);
        UpdateDeliveryDateAndTime(OrderID);
        String OrderNumber = BookingManager.getOrderNumber(OrderID);
        String Subject = "Order Confirmed";
        String Content = "An order with Order Number " + OrderNumber + " has been confirmed by " + username;
        UserManager.sendMemberMessage(UserID, Content, Subject, 1);
        UpdateOrderSubStatus(OrderID, "Confirmed by " + username);
        result = CalculateCharges(OrderID);
        int SupplierUserID = getOrderSupplierUserID(OrderID);
        int CustomerUserID = getOrderCustomerUserID(OrderID);
        String supmsg = "An order with Order Number " + OrderNumber + " has been confirmed by " + username;
        String cusmsg = "An order with Order Number " + OrderNumber + " has been confirmed by " + username;
        String adminmsg = "An order with Order Number " + OrderNumber + " has been confirmed by " + username;
        PushManager.sendPushNotification(Subject, supmsg, "supplier-" + OrderID, SupplierUserID);
        PushManager.sendPushNotification(Subject, cusmsg, "customer-" + OrderID, CustomerUserID);
        PushManager.sendPushNotification(Subject, adminmsg, ":admin-" + OrderID, 1);
        return result;
    }

    public static String CalculateCharges(int OrderID) throws ClassNotFoundException, SQLException {
        String result = "";
        String OrderAmount = getOrderAmount(OrderID);
        int Amount = Integer.parseInt(OrderAmount);
        int SupplierUserID = getOrderSupplierUserID(OrderID);
        int SupplierPaymentPlan = getSupplierPaymentPlanID(SupplierUserID);
        if (SupplierPaymentPlan == 2) {//Percentage Per Transaction
            int Rate = 10;
            float Bal = Amount / 100;
            float AdminBal = Bal * Rate;
            float SupplierBal = Amount - AdminBal;

            int SupplierCurrentBal = DBManager.GetInt(Tables.SupplierTable.AccountBalance, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + SupplierUserID);
            int SupplierNewBalance = (int) SupplierBal + SupplierCurrentBal;
            DBManager.UpdateIntData(Tables.SupplierTable.AccountBalance, SupplierNewBalance, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + SupplierUserID);

            int SupplierAdminCurrentBal = DBManager.GetInt(Tables.SupplierTable.AdminAccountBalance, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + SupplierUserID);
            int SupplierAdminNewBalance = (int) AdminBal + SupplierAdminCurrentBal;
            DBManager.UpdateIntData(Tables.SupplierTable.AdminAccountBalance, SupplierAdminNewBalance, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + SupplierUserID);

            int SupplierTransNo = DBManager.GetInt(Tables.SupplierTable.TransactionNumber, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + SupplierUserID);
            int SupplierNewTransNo = SupplierTransNo + 1;
            DBManager.UpdateIntData(Tables.SupplierTable.TransactionNumber, SupplierNewTransNo, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + SupplierUserID);

            int AdminCurrentBal = DBManager.GetInt(Tables.AdminTable.AccountBalance, Tables.AdminTable.Table, "where " + Tables.AdminTable.UserID + " = " + AdminUserID);
            int AdminNewBalance = (int) AdminBal + AdminCurrentBal;
            result = DBManager.UpdateIntData(Tables.AdminTable.AccountBalance, AdminNewBalance, Tables.AdminTable.Table, "where " + Tables.AdminTable.UserID + " = " + AdminUserID);

            int AdminTransNo = DBManager.GetInt(Tables.AdminTable.TransactionNumber, Tables.AdminTable.Table, "where " + Tables.AdminTable.UserID + " = " + AdminUserID);
            int AdminNewTransNo = AdminTransNo + 1;
            result = DBManager.UpdateIntData(Tables.AdminTable.TransactionNumber, AdminNewTransNo, Tables.AdminTable.Table, "where " + Tables.AdminTable.UserID + " = " + AdminUserID);

        } else if (SupplierPaymentPlan == 1) {
            int SupplierCurrentBal = DBManager.GetInt(Tables.SupplierTable.AccountBalance, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + SupplierUserID);
            int SupplierNewBalance = Amount + SupplierCurrentBal;
            DBManager.UpdateIntData(Tables.SupplierTable.AccountBalance, SupplierNewBalance, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + SupplierUserID);

            int SupplierTransNo = DBManager.GetInt(Tables.SupplierTable.TransactionNumber, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + SupplierUserID);
            int SupplierNewTransNo = SupplierTransNo + 1;
            result = DBManager.UpdateIntData(Tables.SupplierTable.TransactionNumber, SupplierNewTransNo, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + SupplierUserID);
        }
        return result;
    }

    public static String UpdateDeletedOrder(int OrderID) throws ClassNotFoundException, SQLException {
        String result = "";
        int AdminTransNo = DBManager.GetInt(Tables.AdminTable.TransactionNumber, Tables.AdminTable.Table, "where " + Tables.AdminTable.UserID + " = " + AdminUserID);
        int AdminNewTransNo = AdminTransNo - 1;
        DBManager.UpdateIntData(Tables.AdminTable.TransactionNumber, AdminNewTransNo, Tables.AdminTable.Table, "where " + Tables.AdminTable.UserID + " = " + AdminUserID);
        int UserID = getOrderSupplierUserID(OrderID);
        int SupplierTransNo = DBManager.GetInt(Tables.SupplierTable.TransactionNumber, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + UserID);
        int SupplierNewTransNo = SupplierTransNo - 1;
        DBManager.UpdateIntData(Tables.SupplierTable.TransactionNumber, SupplierNewTransNo, Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + UserID);
        return result;
    }

    public static String UpdateConfirmedBy(int OrderID, int UserID) throws ClassNotFoundException, SQLException {
        String result = "";
        result = DBManager.UpdateIntData(Tables.OrderTable.ConfirmedByUserID, UserID, Tables.OrderTable.Table, "where " + Tables.OrderTable.ID + " = " + OrderID);
        return result;
    }

    public static String UpdateOrderSubStatus(int OrderID, String SubStatus) throws ClassNotFoundException, SQLException {
        String result = "";
        result = DBManager.UpdateStringData(Tables.OrderTable.Table, Tables.OrderTable.SubStatus, SubStatus, "where " + Tables.OrderTable.ID + " = " + OrderID);
        return result;
    }

    public static String DeleteOrder(int OrderID) throws ClassNotFoundException, SQLException {
        String result = "";
        result = DBManager.DeleteObject(Tables.OrderTable.Table, "where " + Tables.OrderTable.ID + " = " + OrderID);
        return result;
    }

    public static String UpdateDeliveryDateAndTime(int OrderID) throws ClassNotFoundException, SQLException {
        String result = "";
        DBManager.UpdateCurrentDate(Tables.OrderTable.Table, Tables.OrderTable.DeliveryDate, "where " + Tables.OrderTable.ID + " = " + OrderID);
        result = DBManager.UpdateCurrentTime(Tables.OrderTable.Table, Tables.OrderTable.DeliveryTime, "where " + Tables.OrderTable.ID + " = " + OrderID);
        return result;
    }

    public static ArrayList<Integer> getDeliveredOrderIDS() throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDS = new ArrayList<>();
        IDS = DBManager.GetIntArrayListDescending(Tables.OrderTable.ID, Tables.OrderTable.Table, "where " + Tables.OrderTable.Status + " = 'Delivered'");
        return IDS;
    }

    public static String getOrderAmount(int OrderID) throws ClassNotFoundException, SQLException {
        String result = "";
        result = DBManager.GetString(Tables.OrderTable.Amount, Tables.OrderTable.Table, "where " + Tables.OrderTable.ID + " = " + OrderID);
        return result;
    }

}
