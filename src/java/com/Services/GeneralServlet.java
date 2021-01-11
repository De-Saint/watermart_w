/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Services;

import com.Managers.GeneralManager;
import com.Managers.PushManager;
import com.Managers.UserManager;
import com.Tables.Tables;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Saint
 */
public class GeneralServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader br = request.getReader();
                String str = null;
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONParser parser = new JSONParser();
            JSONObject jsonParameter = null;
            try {
                jsonParameter = (JSONObject) parser.parse(sb.toString());
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
            ArrayList<Integer> IDS = new ArrayList<>();
            String caseType = (String) jsonParameter.get("type");
            String json = "";
            String json1 = "";
            String json2 = "";
            String result = "";
            switch (caseType) {
                case "GetProducts": {
                    String userid = (String) jsonParameter.get("userid");
                    int UserID = Integer.parseInt(userid);
                    String usertype = (String) jsonParameter.get("usertype");
                    ArrayList<Integer> productids = UserManager.getProductIds(UserID, usertype);
                    ArrayList<HashMap<String, String>> productlist = new ArrayList<>();
                    if (!productids.isEmpty()) {
                        for (int i : productids) {
                            HashMap<String, String> products = new HashMap<>();
                            products = UserManager.GetProduct(i);
                            productlist.add(products);
                        }
                        String code = "200";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(productlist);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        HashMap<String, String> productcats = new HashMap<>();
                        String code = "400";
                        String message = "Sorry, You do not have any Product(s)";
                        productcats.put("empty", message);
                        productlist.add(productcats);
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(productlist);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "getProductCategories": {
                    String userid = (String) jsonParameter.get("userid");
                    int UserID = Integer.parseInt(userid);
                    ArrayList<Integer> ids = GeneralManager.getProductCategoryIds(UserID);
                    ArrayList<HashMap<String, String>> productcatlist = new ArrayList<>();
                    if (!ids.isEmpty()) {
                        for (int i : ids) {
                            HashMap<String, String> productcats = new HashMap<>();
                            productcats = GeneralManager.GetProductCategory(i);
                            productcatlist.add(productcats);
                        }
                        String code = "200";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(productcatlist);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        String code = "400";
                        String message = "No Product";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(message);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "AddProduct": {
                    String userid = (String) jsonParameter.get("userid");
                    int UserID = Integer.parseInt(userid);
                    String catid = (String) jsonParameter.get("catid");
                    int categoryid = Integer.parseInt(catid);
                    String name = (String) jsonParameter.get("name");
                    String price = (String) jsonParameter.get("price");
                    String properties = (String) jsonParameter.get("properties");
                    int productid = GeneralManager.AddProduct(UserID, categoryid, name, price, properties);
                    json = new Gson().toJson(productid);
                    break;
                }
                case "getLocation": {
                    String usertype = (String) jsonParameter.get("usertype");
                    String userid = (String) jsonParameter.get("userid");
                    int UserID = Integer.parseInt(userid);
                    ArrayList<Integer> ids = UserManager.getLocationIds(usertype, UserID);
                    HashMap<String, String> locations = new HashMap<>();
                    ArrayList<HashMap<String, String>> locationlist = new ArrayList<>();
                    if (!ids.isEmpty()) {
                        for (int i : ids) {
                            locations = UserManager.GetLocationName(i);
                            locationlist.add(locations);
                        }
                        json1 = new Gson().toJson("200");
                        json2 = new Gson().toJson(locationlist);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        ArrayList<HashMap<String, String>> locationList = new ArrayList<>();
                        json1 = new Gson().toJson("400");
                        json2 = new Gson().toJson(locationList);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "AddLocation": {
                    String userid = (String) jsonParameter.get("userid");
                    String name = (String) jsonParameter.get("name");
                    String fees = (String) jsonParameter.get("fees");
                    int UserID = Integer.parseInt(userid);
                    result = GeneralManager.AddLocation(UserID, name, fees);
                    UserManager.sendMemberMessage(UserID, "Added New Delivery Location", "New Location", 1);
                    json = new Gson().toJson(result);
                    break;
                }
                case "UpdateLocation": {
                    String id = (String) jsonParameter.get("id");
                    int locationid = Integer.parseInt(id);
                    String name = (String) jsonParameter.get("name");
                    String fees = (String) jsonParameter.get("fees");
                    result = GeneralManager.UpdateLocation(locationid, name, fees);
                    json = new Gson().toJson(result);
                    break;
                }
                case "DeleteLocation": {
                    String locationid = (String) jsonParameter.get("locationid");
                    int LocationID = Integer.parseInt(locationid);
                    result = GeneralManager.DeleteLocation(LocationID);

                    json = new Gson().toJson(result);
                    break;
                }
                case "DeleteProduct": {
                    String productid = (String) jsonParameter.get("productid");
                    int ProductID = Integer.parseInt(productid);
                    result = GeneralManager.DeleteProduct(ProductID);
                    json = new Gson().toJson(result);
                    break;
                }
                case "getProductDetails": {
                    ArrayList<HashMap<String, String>> proplist = new ArrayList<>();
                    String productid = (String) jsonParameter.get("productid");
                    int ProductID = Integer.parseInt(productid);
                    HashMap<String, String> UserDet = UserManager.GetProduct(ProductID);

                    String Properties = UserDet.get(Tables.ProductTable.Properties);
                    String props[] = Properties.split(",");
                    for (String record : props) {
                        HashMap<String, String> PropDet = new HashMap<>();
                        String propName = record.split(":")[0];
                        String propValue = record.split(":")[1];
                        PropDet.put("PropName", propName);
                        PropDet.put("PropValue", propValue);
                        proplist.add(PropDet);
                    }
                    json1 = new Gson().toJson(UserDet);
                    json2 = new Gson().toJson(proplist);
                    json = "[" + json1 + "," + json2 + "]";
                    break;
                }
                case "UpdateProduct": {
                    String prodid = (String) jsonParameter.get("productid");
                    int ProductID = Integer.parseInt(prodid);
                    String name = (String) jsonParameter.get("name");
                    String price = (String) jsonParameter.get("price");
                    String properties = (String) jsonParameter.get("properties");
                    String productid = GeneralManager.UpdateProduct(ProductID, name, price, properties);
                    json = new Gson().toJson(productid);
                    break;
                }
                case "GetBuyDomesticWater": {
                    ArrayList<Integer> productids = GeneralManager.GetBuyDomesticWater();
                    ArrayList<HashMap<String, String>> productlist = new ArrayList<>();
                    if (!productids.isEmpty()) {
                        for (int i : productids) {
                            HashMap<String, String> products = new HashMap<>();
                            products = UserManager.GetProduct(i);
                            productlist.add(products);
                        }
                        String code = "200";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(productlist);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        HashMap<String, String> productcats = new HashMap<>();
                        String code = "400";
                        String message = "Sorry, You do not have any Product(s)";
                        productcats.put("empty", message);
                        productlist.add(productcats);
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(productlist);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "GetBuyDomesticAccessory": {
                    ArrayList<Integer> productids = GeneralManager.GetBuyDomesticAccessory();
                    ArrayList<HashMap<String, String>> productlist = new ArrayList<>();
                    if (!productids.isEmpty()) {
                        for (int i : productids) {
                            HashMap<String, String> products = new HashMap<>();
                            products = UserManager.GetProduct(i);
                            productlist.add(products);
                        }
                        String code = "200";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(productlist);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        HashMap<String, String> productcats = new HashMap<>();
                        String code = "400";
                        String message = "Sorry, You do not have any Product(s)";
                        productcats.put("empty", message);
                        productlist.add(productcats);
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(productlist);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "GetBuyIndustrialWater": {
                    ArrayList<Integer> productids = GeneralManager.GetBuyIndustrialWater();
                    ArrayList<HashMap<String, String>> productlist = new ArrayList<>();
                    if (!productids.isEmpty()) {
                        for (int i : productids) {
                            HashMap<String, String> products = new HashMap<>();
                            products = UserManager.GetProduct(i);
                            productlist.add(products);
                        }
                        String code = "200";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(productlist);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        HashMap<String, String> productcats = new HashMap<>();
                        String code = "400";
                        String message = "Sorry, You do not have any Product(s)";
                        productcats.put("empty", message);
                        productlist.add(productcats);
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(productlist);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "RateSupplier": {
                    String ratevalue = (String) jsonParameter.get("ratevalue");
                    int RateValue = Integer.parseInt(ratevalue);
                    String supplieruserid = (String) jsonParameter.get("supplieruserid");
                    int SupplierUserID = Integer.parseInt(supplieruserid);
                    String customeruserid = (String) jsonParameter.get("customeruserid");
                    int CustomerUserID = Integer.parseInt(customeruserid);
                    result = GeneralManager.RateSupplier(SupplierUserID, CustomerUserID, RateValue);
                    PushManager.sendPushNotification("Rating", "WaterMart Subscriber rates you", "",SupplierUserID);
                    json = new Gson().toJson(result);
                    break;
                }
                case "GetlocationDetails": {
                    String locationid = (String) jsonParameter.get("locationid");
                    int LocationID = Integer.parseInt(locationid);
                    HashMap<String, String> UserDet = UserManager.GetLocationName(LocationID);
                    json = new Gson().toJson(UserDet);
                    break;
                }
                case "GetBalances": {
                    String userid = (String) jsonParameter.get("userid");
                    String usertype = (String) jsonParameter.get("usertype");
                    int UserID = Integer.parseInt(userid);
                    int totalbalance = 0;
                    int transactionnumber = 0;
                    String PaymentPlan = "";
                    int PaymentAmount = 0;
                    String PaymentDate = "";
                    String PaymentDueDate = "";
                    String PaymentStatus = "";
                    int totalmonthlybal = 0;
                    if (usertype.equals("Supplier")) {
                        HashMap<String, String> SupDetails = UserManager.GetSupplier(UserID);
                        totalbalance = Integer.parseInt(SupDetails.get(Tables.SupplierTable.AccountBalance));
                        transactionnumber = Integer.parseInt(SupDetails.get(Tables.SupplierTable.TransactionNumber));
                        int pplanid = Integer.parseInt(SupDetails.get(Tables.SupplierTable.PaymentPlanID));
                        PaymentDate = SupDetails.get(Tables.SupplierTable.PaymentDate);
                        PaymentDueDate = SupDetails.get(Tables.SupplierTable.PaymentDueDate);
                        java.sql.Date CurrentDate = UserManager.CurrentDate();
                        Date PayDueDate = UserManager.getSqlDateFromString(PaymentDueDate);
                        if (PayDueDate.after(CurrentDate)) {
                            PaymentStatus = "Paid";
                        } else {
                            PaymentStatus = "Owing";
                        }
                        if (pplanid == 1) {//monthly fixed rate
                            PaymentPlan = GeneralManager.getPaymentPlanName(pplanid);
                            PaymentAmount = 10000;
                        } else {
                            PaymentPlan = GeneralManager.getPaymentPlanName(pplanid);
                            PaymentAmount = Integer.parseInt(SupDetails.get(Tables.SupplierTable.AdminAccountBalance));
                        }
                    } else if (usertype.equals("Admin")) {
                        totalbalance = GeneralManager.getAdminAccountBalance();
                        transactionnumber = GeneralManager.getAdminTransactionNumber();
                        ArrayList<Integer> MonthlySuppIDs = GeneralManager.GetMonthlySupplierIDs();
                        totalmonthlybal = MonthlySuppIDs.size() + 10000;
                    }
                    json1 = new Gson().toJson(transactionnumber);
                    json2 = new Gson().toJson(totalbalance);
                    String json3 = new Gson().toJson(PaymentPlan);
                    String json4 = new Gson().toJson(PaymentAmount);
                    String json5 = new Gson().toJson(PaymentDate);
                    String json6 = new Gson().toJson(PaymentDueDate);
                    String json7 = new Gson().toJson(PaymentStatus);
                    String json8 = new Gson().toJson(totalmonthlybal);
                    json = "[" + json1 + "," + json2 + "," + json3 + "," + json4 + "," + json5 + "," + json6 + "," + json7 + "," + json8 + "]";
                    break;
                }
                case "GetCustomers": {
                    String countstr = (String) jsonParameter.get("count").toString();
                    int count = 0;
                    if (countstr != null) {
                        count = Integer.parseInt(countstr);
                    }
                    int end = 15;
                    IDS = GeneralManager.getCustomerIds(count, end);

                    ArrayList<HashMap<String, String>> UserList = new ArrayList<>();
                    if (!IDS.isEmpty()) {
                        for (int id : IDS) {
                            HashMap<String, String> Userdetails = new HashMap<>();
                            Userdetails = UserManager.GetCustomer(id);
                            if (!Userdetails.isEmpty()) {
                                Userdetails.put("count", "" + IDS.size());
                                UserList.add(Userdetails);
                            }
                        }
                        String code = "200";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(UserList);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        HashMap<String, String> Userdetails = new HashMap<>();
                        String code = "400";
                        String message = "Sorry, You do not have any user(s)";
                        Userdetails.put("empty", message);
                        UserList.add(Userdetails);
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(UserList);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "getCustomerDetails": {
                    String userid = (String) jsonParameter.get("userid");
                    int UserID = Integer.parseInt(userid);
                    HashMap<String, String> UserDet = UserManager.GetCustomer(UserID);
                    json = new Gson().toJson(UserDet);
                    break;
                }
                case "GetSuppliers": {
                    String countstr = (String) jsonParameter.get("count").toString();
                    int count = 0;
                    if (countstr != null) {
                        count = Integer.parseInt(countstr);
                    }
                    int end = 15;
                    IDS = GeneralManager.getSupplierIds(count, end);

                    ArrayList<HashMap<String, String>> UserList = new ArrayList<>();
                    if (!IDS.isEmpty()) {
                        for (int id : IDS) {
                            HashMap<String, String> Userdetails = new HashMap<>();
                            Userdetails = UserManager.GetSupplier(id);
                            if (!Userdetails.isEmpty()) {
                                Userdetails.put("count", "" + IDS.size());
                                UserList.add(Userdetails);
                            }
                        }
                        String code = "200";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(UserList);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        HashMap<String, String> Userdetails = new HashMap<>();
                        String code = "400";
                        String message = "Sorry, You do not have any user(s)";
                        Userdetails.put("empty", message);
                        UserList.add(Userdetails);
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(UserList);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "GetPPTransactionSuppliers": {
                    IDS = GeneralManager.getPPTransactionSupplierIds();
                    ArrayList<HashMap<String, String>> UserList = new ArrayList<>();
                    if (!IDS.isEmpty()) {
                        for (int id : IDS) {
                            HashMap<String, String> Userdetails = new HashMap<>();
                            Userdetails = UserManager.GetSupplier(id);
                            if (!Userdetails.isEmpty()) {
                                Userdetails.put("count", "" + IDS.size());
                                UserList.add(Userdetails);
                            }
                        }
                        String code = "200";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(UserList);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        HashMap<String, String> Userdetails = new HashMap<>();
                        String code = "400";
                        String message = "Sorry, You do not have any user(s)";
                        Userdetails.put("empty", message);
                        UserList.add(Userdetails);
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(UserList);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "GetMFRateSuppliers": {
                    IDS = GeneralManager.getMFRateSupplierIds();
                    ArrayList<HashMap<String, String>> UserList = new ArrayList<>();
                    if (!IDS.isEmpty()) {
                        for (int id : IDS) {
                            HashMap<String, String> Userdetails = new HashMap<>();
                            Userdetails = UserManager.GetSupplier(id);
                            if (!Userdetails.isEmpty()) {
                                Userdetails.put("count", "" + IDS.size());
                                UserList.add(Userdetails);
                            }
                        }
                        String code = "200";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(UserList);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        HashMap<String, String> Userdetails = new HashMap<>();
                        String code = "400";
                        String message = "Sorry, You do not have any user(s)";
                        Userdetails.put("empty", message);
                        UserList.add(Userdetails);
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(UserList);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "getSuplierDetails": {
                    String userid = (String) jsonParameter.get("userid");
                    int UserID = Integer.parseInt(userid);
                    HashMap<String, String> UserDet = UserManager.GetSupplier(UserID);
                    json = new Gson().toJson(UserDet);
                    break;
                }
                case "DeleteCustomer": {
                    String userid = (String) jsonParameter.get("userid");
                    int UserID = Integer.parseInt(userid);
                    result = GeneralManager.DeleteCustomerAccount(UserID);
                    json = new Gson().toJson(result);
                    break;
                }
                case "DeleteSupplier": {
                    String userid = (String) jsonParameter.get("userid");
                    int UserID = Integer.parseInt(userid);
                    result = GeneralManager.DeleteSupplierAccount(UserID);
                    json = new Gson().toJson(result);
                    break;
                }
                case "ChangePaymenPlan": {
                    String userid = (String) jsonParameter.get("userid");
                    int UserID = Integer.parseInt(userid);
                    result = GeneralManager.UpdateSupplierPaymentPlan(UserID);
                    PushManager.sendPushNotification("Payment Plan", "Your payment plan has been changed by WaterMart","", UserID);
                    String SupplierName = GeneralManager.getUserName(UserID);
                    PushManager.sendPushNotification("Payment Plan", "You changed payment plan for " + SupplierName,"", 1);
                    json = new Gson().toJson(result);
                    break;
                }
                case "AccountAction": {
                    String userid = (String) jsonParameter.get("userid");
                    String action = (String) jsonParameter.get("action");
                    int UserID = Integer.parseInt(userid);
                    result = GeneralManager.UpdateUserAccount(UserID, action);
                    json = new Gson().toJson(result);
                    break;
                }
                case "GetInboxMessages": {
                    String memberid = (String) jsonParameter.get("userid");
                    int userid = Integer.parseInt(memberid);
                    ArrayList<Integer> IDs = GeneralManager.getInboxMessageIDs(userid);
                    json = new Gson().toJson(IDs.size());
                    break;
                }
                case "GetSentMessages": {
                    String memberid = (String) jsonParameter.get("userid");
                    int userid = Integer.parseInt(memberid);
                    ArrayList<Integer> IDs = GeneralManager.getSentMessageIDs(userid);
                    json = new Gson().toJson(IDs.size());
                    break;
                }
                case "GetAllSuppliers": {
                    ArrayList<Integer> TotalSuppIDS = GeneralManager.getSupplierIDS();
                    ArrayList<Integer> TotalActivatedSuppIDS = GeneralManager.getActivatedSupplierIds();
                    ArrayList<Integer> TotalNonActivatedSuppIDS = GeneralManager.getNonActivatedSupplierIds();
                    json1 = new Gson().toJson(TotalSuppIDS.size());
                    json2 = new Gson().toJson(TotalActivatedSuppIDS.size());
                    String json3 = new Gson().toJson(TotalNonActivatedSuppIDS.size());
                    json = "[" + json1 + "," + json2 + "," + json3 + "]";
                    break;
                }
                case "SaveAndUpdateDeviceToken": {
                    String UserInput = (String) jsonParameter.get("userid");
                    int UserID = Integer.parseInt(UserInput);
                    String DeviceToken = (String) jsonParameter.get("devicetoken");
                    result = PushManager.UpdateDeviceToken(UserID, DeviceToken);
                    if (result.equals("success")) {
                        String code = "200";
                        result = "Device Token Saved";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(result);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        String code = "400";
                        String message = "Error Try again";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(message);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception ex) {
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
