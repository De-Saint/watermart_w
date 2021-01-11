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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Saint
 */
public class UserManager {

    public UserManager() {

    }

    public static java.sql.Date CurrentDate() throws ParseException {
        Calendar currentdate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");
        String Placeholder = formatter.format(currentdate.getTime());
        java.util.Date datenow = formatter.parse(Placeholder);
        java.sql.Date CurrentDate = new Date(datenow.getTime());
        return CurrentDate;
    }

    public static java.sql.Date DueDate() throws ParseException {
        Calendar currentdate = Calendar.getInstance();
        currentdate.add(Calendar.DATE, 28);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");
        String Placeholder = formatter.format(currentdate.getTime());
        java.util.Date datenow = formatter.parse(Placeholder);
        java.sql.Date CurrentDate = new Date(datenow.getTime());
        return CurrentDate;
    }

    public static String CurrentTime() throws ParseException {
        Calendar currentdate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String CurrentTime = formatter.format(currentdate.getTime());
        return CurrentTime;
    }

    public static String CreateRandomCode(int LengthOfCode) {
        String SupplierCode = "";
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < LengthOfCode; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        SupplierCode = sb.toString();
        return SupplierCode;
    }

    public static java.sql.Date getSqlDateFromString(String StringDate) {
        Date date;
        try {
            date = Date.valueOf(StringDate);
        } catch (Exception e) {
            date = null;
        }
        return date;
    }

    public static boolean checkEmailAddressOrPhoneNumberExist(String EmailAddress) throws ClassNotFoundException, SQLException {
        boolean result = false;
        int usid = DBManager.GetInt(Tables.UserTable.ID, Tables.UserTable.Table, "where " + Tables.UserTable.Email + " = '" + EmailAddress + "' or " + Tables.UserTable.Phone + " = '" + EmailAddress + "'");
        if (usid != 0) {
            result = true;
        }
        return result;
    }

    public static int checkPasswordEmailMatch(String Password, String Email_PhoneNum) throws ClassNotFoundException, SQLException {
        int result = 0;
        String memPassword = "";
        String email = Email_PhoneNum;
        memPassword = DBManager.GetString(Tables.UserTable.Password, Tables.UserTable.Table, "where " + Tables.UserTable.Email + " = '" + Email_PhoneNum + "'");
        if (memPassword.equals("none")) {
            memPassword = DBManager.GetString(Tables.UserTable.Password, Tables.UserTable.Table, "where " + Tables.UserTable.Phone + " = '" + Email_PhoneNum + "'");
            email = DBManager.GetString(Tables.UserTable.Email, Tables.UserTable.Table, "where " + Tables.UserTable.Phone + " = '" + Email_PhoneNum + "'");
        }
        if (memPassword.equals(Password)) {
            result = DBManager.GetInt(Tables.UserTable.ID, Tables.UserTable.Table, "where " + Tables.UserTable.Email + " = '" + email + "'");
        }
        return result;
    }

    public static int checkEmailMatch(String EmailAddress) throws ClassNotFoundException, SQLException {
        int result = 0;
        result = DBManager.GetInt(Tables.UserTable.ID, Tables.UserTable.Table, "where " + Tables.UserTable.Email + " = '" + EmailAddress + "'");
        return result;
    }

    public static String getUserType(int UserID) throws ClassNotFoundException, SQLException {
        String result = DBManager.GetString(Tables.UserTable.UserType, Tables.UserTable.Table, "where " + Tables.UserTable.ID + " = " + UserID);
        return result;
    }

    public static HashMap<String, String> GetUserDetails(int UserID, String UserType) throws ClassNotFoundException, SQLException {
        HashMap<String, String> UserDetails = new HashMap<>();
        HashMap<String, String> TableDetails = new HashMap<>();
        switch (UserType) {
            case "Customer":
                TableDetails = DBManager.GetTableData(Tables.CustomerTable.Table, "where status = 'UnBlocked' AND " + Tables.CustomerTable.UserID + " = " + UserID);
                if (!TableDetails.isEmpty()) {
                    UserDetails = DBManager.GetTableData(Tables.UserTable.Table, "where " + Tables.UserTable.ID + " = " + UserID);
                }
                break;
            case "Supplier":
                TableDetails = DBManager.GetTableData(Tables.SupplierTable.Table, "where status = 'Activated' AND " + Tables.SupplierTable.UserID + " = " + UserID);
                if (!TableDetails.isEmpty()) {
                    UserDetails = DBManager.GetTableData(Tables.UserTable.Table, "where " + Tables.UserTable.ID + " = " + UserID);
                }
                break;
            case "Admin":
                UserDetails = DBManager.GetTableData(Tables.UserTable.Table, "where " + Tables.UserTable.ID + " = " + UserID);
                TableDetails = DBManager.GetTableData(Tables.AdminTable.Table, "where " + Tables.AdminTable.UserID + " = " + UserID);
                break;
        }
        UserDetails.putAll(TableDetails);
        return UserDetails;
    }

    public static ArrayList<Integer> getLocationIds(String usertype, int userid) throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDs = new ArrayList<>();
        if (usertype.equals("Admin")) {
            IDs = DBManager.GetIntArrayList(Tables.LocationTable.ID, Tables.LocationTable.Table, "ORDER by name DESC");
        } else if (usertype.equals("Supplier")) {
            IDs = DBManager.GetIntArrayList(Tables.LocationTable.ID, Tables.LocationTable.Table, "where " + Tables.LocationTable.SupplierUserID + " = " + userid);//"ORDER by name DESC");
        }
        return IDs;
    }

    public static ArrayList<Integer> getProductIds(int UserID, String Usertype) throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDs = new ArrayList<>();
        if (Usertype.equals("Admin")) {
            IDs = DBManager.GetIntArrayListDescending(Tables.ProductTable.ID, Tables.ProductTable.Table, "ORDER by name DESC");
        } else if (Usertype.equals("Supplier")) {
            IDs = DBManager.GetIntArrayList(Tables.ProductTable.ID, Tables.ProductTable.Table, "where " + Tables.ProductTable.SupplierUserID + " = " + UserID);//"ORDER by name DESC");
        }
        return IDs;
    }

    public static ArrayList<Integer> getPaymentPlanIds() throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDs = new ArrayList<>();
        IDs = DBManager.GetIntArrayListDescending(Tables.PaymentPlanTable.ID, Tables.PaymentPlanTable.Table, "");
        return IDs;
    }

    public static ArrayList<Integer> getWaterCatIds() throws ClassNotFoundException, SQLException {
        ArrayList<Integer> IDs = new ArrayList<>();
        IDs = DBManager.GetIntArrayListDescending(Tables.WaterCategoryTable.ID, Tables.WaterCategoryTable.Table, "");
        return IDs;
    }

    public static HashMap<String, String> GetPaymentPlanByID(int ID) throws ClassNotFoundException, SQLException {
        HashMap<String, String> Details = DBManager.GetTableData(Tables.PaymentPlanTable.Table, "where " + Tables.PaymentPlanTable.ID + " = " + ID);
        return Details;
    }

    public static HashMap<String, String> GetRecoveryDetails(int ID) throws ClassNotFoundException, SQLException {
        HashMap<String, String> Details = DBManager.GetTableData(Tables.RecoveryTable.Table, "where " + Tables.RecoveryTable.UserID + " = " + ID);
        return Details;
    }

    public static HashMap<String, String> GetWaterCategory(int ID) throws ClassNotFoundException, SQLException {
        HashMap<String, String> Details = DBManager.GetTableData(Tables.WaterCategoryTable.Table, "where " + Tables.WaterCategoryTable.ID + " = " + ID);
        return Details;
    }

    public static HashMap<String, String> GetLocationName(int ID) throws ClassNotFoundException, SQLException {
        HashMap<String, String> Details = DBManager.GetTableData(Tables.LocationTable.Table, "where " + Tables.LocationTable.ID + " = " + ID);
        return Details;
    }

    public static HashMap<String, String> GetProduct(int ID) throws ClassNotFoundException, SQLException {
        HashMap<String, String> Details = DBManager.GetTableData(Tables.ProductTable.Table, "where " + Tables.ProductTable.ID + " = " + ID);
        return Details;
    }

    public static HashMap<String, String> GetSupplier(int SupplierID) throws ClassNotFoundException, SQLException, ParseException {
        HashMap<String, String> Details = DBManager.GetTableData(Tables.SupplierTable.Table, "where " + Tables.SupplierTable.UserID + " = " + SupplierID);
        HashMap<String, String> UserDetails = DBManager.GetTableData(Tables.UserTable.Table, "where " + Tables.UserTable.ID + " = " + SupplierID);
        int pplanid = Integer.parseInt(Details.get(Tables.SupplierTable.PaymentPlanID));
        String PaymentDate = Details.get(Tables.SupplierTable.PaymentDate);
        Details.put("PaymentDate", PaymentDate);
        String PaymentStatus = "";
        String PaymentPlan = "";
        int PaymentAmount = 0;
        String PaymentDueDate = Details.get(Tables.SupplierTable.PaymentDueDate);
        java.sql.Date CurrentDate = UserManager.CurrentDate();
        Date PayDueDate = UserManager.getSqlDateFromString(PaymentDueDate);
        if (PayDueDate.after(CurrentDate)) {
            PaymentStatus = "Paid";
        } else {
            PaymentStatus = "Owing";
        }
        Details.put("PaymentStatus", PaymentStatus);
        if (pplanid == 1) {//monthly fixed rate
            PaymentPlan = GeneralManager.getPaymentPlanName(pplanid);
            PaymentAmount = 10000;
        } else {
            PaymentPlan = GeneralManager.getPaymentPlanName(pplanid);
            PaymentAmount = Integer.parseInt(Details.get(Tables.SupplierTable.AdminAccountBalance));
        }
        Details.put("PaymentPlan", PaymentPlan);
        Details.put("PaymentDueDate", PaymentDueDate);
        Details.put("PaymentAmount", "" + PaymentAmount);
        Details.put("PaymentStatus", PaymentStatus);
        Details.putAll(UserDetails);
        return Details;
    }

    public static HashMap<String, String> GetCustomer(int CustomerID) throws ClassNotFoundException, SQLException {
        HashMap<String, String> Details = DBManager.GetTableData(Tables.CustomerTable.Table, "where " + Tables.CustomerTable.UserID + " = " + CustomerID);
        HashMap<String, String> UserDetails = DBManager.GetTableData(Tables.UserTable.Table, "where " + Tables.UserTable.ID + " = " + CustomerID);
        Details.putAll(UserDetails);
        return Details;
    }

    public static HashMap<String, String> GetProductSupplier(int SupplierID) throws ClassNotFoundException, SQLException {
        HashMap<String, String> Details = DBManager.GetTableData(Tables.SupplierTable.Table, "where " + Tables.SupplierTable.Status + " = 'Activated' AND " + Tables.SupplierTable.UserID + " = " + SupplierID);
        return Details;
    }

    public static String GetProductProperties(int ProductID) throws ClassNotFoundException, SQLException {
        String Details = DBManager.GetString(Tables.ProductTable.Properties, Tables.ProductTable.Table, "where " + Tables.ProductTable.ProductCategoryID + " = " + 2 + " AND " + Tables.ProductTable.ID + " = " + ProductID);
        return Details;
    }

    public static HashMap<String, String> GetUserRecoveryDetails(int ID) throws ClassNotFoundException, SQLException {
        HashMap<String, String> Details = DBManager.GetTableData(Tables.RecoveryTable.Table, "where " + Tables.RecoveryTable.UserID + " = " + ID);
        return Details;
    }

    public static int RegisterUser(String EmailAddress, String PhoneNumber, String password, String Firstname, String Lastname, String Type, Date DateCreated) throws ClassNotFoundException, SQLException {
        int userId = 0;
        HashMap<String, Object> tableData = new HashMap<>();
        tableData.put(Tables.UserTable.Email, EmailAddress);
        tableData.put(Tables.UserTable.Phone, PhoneNumber);
        tableData.put(Tables.UserTable.Password, password);
        tableData.put(Tables.UserTable.FirstName, Firstname);
        tableData.put(Tables.UserTable.LastName, Lastname);
        tableData.put(Tables.UserTable.UserType, Type);
        tableData.put(Tables.UserTable.DateCreated, DateCreated);
        userId = DBManager.insertTableDataReturnID(Tables.UserTable.Table, tableData, "");
        return userId;
    }

    public static String RegisterCustomer(int UserID, String Address) throws ClassNotFoundException, SQLException {
        HashMap<String, Object> tableData = new HashMap<>();
        tableData.put(Tables.CustomerTable.UserID, UserID);
        tableData.put(Tables.CustomerTable.Address, Address);
        tableData.put(Tables.CustomerTable.Status, "UnBlocked");
        String result = DBManager.insertTableData(Tables.CustomerTable.Table, tableData, "");
        return result;
    }

    public static String RegisterSupplier(int UserID, String BusinessName, String NafDacNumber, String Address, String Status, String SupplierCode, int WaterCatID, int PlanID) throws ClassNotFoundException, SQLException {
        HashMap<String, Object> tableData = new HashMap<>();
        tableData.put(Tables.SupplierTable.UserID, UserID);
        tableData.put(Tables.SupplierTable.BusinessName, BusinessName);
        tableData.put(Tables.SupplierTable.NAFDACNumber, NafDacNumber);
        tableData.put(Tables.SupplierTable.Address, Address);
        tableData.put(Tables.SupplierTable.Status, Status);
        tableData.put(Tables.SupplierTable.SupplierCode, SupplierCode);
        tableData.put(Tables.SupplierTable.PaymentPlanID, PlanID);
        tableData.put(Tables.SupplierTable.WaterCategoryID, WaterCatID);
        tableData.put(Tables.SupplierTable.RateValue, 0);
        tableData.put(Tables.SupplierTable.AccountBalance, 0);
        tableData.put(Tables.SupplierTable.TransactionNumber, 0);
        tableData.put(Tables.SupplierTable.AdminAccountBalance, 0);
        String result = DBManager.insertTableData(Tables.SupplierTable.Table, tableData, "");
        return result;
    }

    public static int sendMemberMessage(int ToMemberId, String bdy, String sub, int FromMemberId) throws ClassNotFoundException, SQLException, ParseException {
        int messageid = 0;
        String Time = CurrentTime();
        Date date = CurrentDate();
        HashMap<String, Object> data = new HashMap<>();
        data.put(Tables.MessageTable.Date, date);
        data.put(Tables.MessageTable.Time, Time);
        data.put(Tables.MessageTable.Subject, sub);
        data.put(Tables.MessageTable.IsRead, 0);
        data.put(Tables.MessageTable.FromMemberID, FromMemberId);
        data.put(Tables.MessageTable.ToMemberID, ToMemberId);
        data.put(Tables.MessageTable.Body, bdy);
        messageid = DBManager.insertTableDataReturnID(Tables.MessageTable.Table, data, "");
        return messageid;
    }

    public static String CreateRecovery(int userID, String question, String answer) throws ClassNotFoundException, SQLException {
        HashMap<String, Object> data = new HashMap<>();
        data.put(Tables.RecoveryTable.UserID, userID);
        data.put(Tables.RecoveryTable.Question, question);
        data.put(Tables.RecoveryTable.Answer, answer);
        String result = DBManager.insertTableData(Tables.RecoveryTable.Table, data, "");
        return result;
    }

    public static String UpdateUserPassword(int UserID, String Password) throws ClassNotFoundException, SQLException {
        String result = DBManager.UpdateStringData(Tables.UserTable.Table, Tables.UserTable.Password, Password, "where " + Tables.UserTable.ID + " = " + UserID);
        return result;
    }

    public static String UpdateFirstName(int UserID, String FirstName) throws ClassNotFoundException, SQLException {
        String result = DBManager.UpdateStringData(Tables.UserTable.Table, Tables.UserTable.FirstName, FirstName, "where " + Tables.UserTable.ID + " = " + UserID);
        return result;
    }

    public static String UpdateBusinessName(int UserID, String BizName) throws ClassNotFoundException, SQLException {
        String result = DBManager.UpdateStringData(Tables.SupplierTable.Table, Tables.SupplierTable.BusinessName, BizName, "where " + Tables.SupplierTable.UserID + " = " + UserID);
        return result;
    }

    public static String UpdateNAFDACNumber(int UserID, String NafdacNumber) throws ClassNotFoundException, SQLException {
        String result = DBManager.UpdateStringData(Tables.SupplierTable.Table, Tables.SupplierTable.NAFDACNumber, NafdacNumber, "where " + Tables.SupplierTable.UserID + " = " + UserID);
        return result;
    }

    public static String UpdateLastName(int UserID, String LastName) throws ClassNotFoundException, SQLException {
        String result = DBManager.UpdateStringData(Tables.UserTable.Table, Tables.UserTable.LastName, LastName, "where " + Tables.UserTable.ID + " = " + UserID);
        return result;
    }

    public static String UpdateEmail(int UserID, String Email) throws ClassNotFoundException, SQLException {
        String result = DBManager.UpdateStringData(Tables.UserTable.Table, Tables.UserTable.Email, Email, "where " + Tables.UserTable.ID + " = " + UserID);
        return result;
    }

    public static String UpdatePhone(int UserID, String Phone) throws ClassNotFoundException, SQLException {
        String result = DBManager.UpdateStringData(Tables.UserTable.Table, Tables.UserTable.Phone, Phone, "where " + Tables.UserTable.ID + " = " + UserID);
        return result;
    }

    public static String getUserAddress(int UserID) throws ClassNotFoundException, SQLException {
        String result = DBManager.GetString(Tables.CustomerTable.Address, Tables.CustomerTable.Table, "where " + Tables.CustomerTable.UserID + " = " + UserID);
        return result;
    }

    public static String getUserName(int UserID) throws ClassNotFoundException, SQLException {
        String result = "";
        String lname = DBManager.GetString(Tables.UserTable.LastName, Tables.UserTable.Table, "where " + Tables.UserTable.ID + " = " + UserID);
        String fname = DBManager.GetString(Tables.UserTable.FirstName, Tables.UserTable.Table, "where " + Tables.UserTable.ID + " = " + UserID);
        result = lname + " " + fname;
        return result;
    }
//    public static String getAdminName(int UserID) throws ClassNotFoundException, SQLException {
//        String result = "";
//        String lname = DBManager.GetString(Tables.UserTable.LastName, Tables.UserTable.Table, "where " + Tables.UserTable.ID + " = " + UserID);
//        String fname = DBManager.GetString(Tables.UserTable.FirstName, Tables.UserTable.Table, "where " + Tables.UserTable.ID + " = " + UserID);
//        result = lname + " " + fname;
//        return result;
//    }

    public static String getUserPhone(int UserID) throws ClassNotFoundException, SQLException {
        String result = DBManager.GetString(Tables.UserTable.Phone, Tables.UserTable.Table, "where " + Tables.UserTable.ID + " = " + UserID);
        return result;
    }

}
