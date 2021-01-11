/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Services;

import com.Managers.BookingManager;
import com.Managers.GeneralManager;
import com.Managers.PushManager;
import com.Managers.UserManager;
import com.Tables.Tables;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
public class BookingServlet extends HttpServlet {

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
            String caseType = (String) jsonParameter.get("type");
            String json = "";
            String json1 = "";
            String json2 = "";
            String result = "";
            switch (caseType) {
                case "GetMartSuppliers": {
                    String categoryid = (String) jsonParameter.get("catid");
                    int CategoryID = Integer.parseInt(categoryid);
                    ArrayList<Integer> ProductIDS = GeneralManager.GetProductByCategoryID(CategoryID);
                    ArrayList<HashMap<String, String>> supplierlist = new ArrayList<>();
                    if (!ProductIDS.isEmpty()) {
                        for (int pid : ProductIDS) {
                            HashMap<String, String> productdet = UserManager.GetProduct(pid);
                            productdet.put("ProductID", "" + pid);
                            int supplierUserID = Integer.parseInt(productdet.get(Tables.ProductTable.SupplierUserID));
                            HashMap<String, String> supplierdet = UserManager.GetProductSupplier(supplierUserID);
                            if (!supplierdet.isEmpty()) {
                                productdet.putAll(supplierdet);
                                supplierlist.add(productdet);
                            }
                        }
                        String code = "200";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(supplierlist);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        HashMap<String, String> productcats = new HashMap<>();
                        String code = "400";
                        String message = "Sorry, You do not have any Product(s)";
                        productcats.put("empty", message);
                        supplierlist.add(productcats);
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(supplierlist);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "GetMartSupplierDetails": {
                    String productid = (String) jsonParameter.get("productid");
                    int ProductID = Integer.parseInt(productid);
                    HashMap<String, String> productdet = UserManager.GetProduct(ProductID);
                    productdet.put("ProductID", "" + ProductID);
                    int supplierUserID = Integer.parseInt(productdet.get(Tables.ProductTable.SupplierUserID));
                    HashMap<String, String> supplierdet = UserManager.GetSupplier(supplierUserID);
                    productdet.putAll(supplierdet);
                    ArrayList<HashMap<String, String>> productlist = new ArrayList<>();
                    ArrayList<Integer> productids = UserManager.getProductIds(supplierUserID, "Supplier");
                    if (!productids.isEmpty()) {
                        for (int i : productids) {
                            HashMap<String, String> products = new HashMap<>();
                            products = UserManager.GetProduct(i);
                            productlist.add(products);
                        }
                    }
                    ArrayList<HashMap<String, String>> proplist = new ArrayList<>();
                    String Properties = productdet.get(Tables.ProductTable.Properties);
                    String props[] = Properties.split(",");
                    for (String record : props) {
                        HashMap<String, String> PropDet = new HashMap<>();
                        String propName = record.split(":")[0];
                        String propValue = record.split(":")[1];
                        PropDet.put("PropName", propName);
                        PropDet.put("PropValue", propValue);
                        proplist.add(PropDet);
                    }
                    json1 = new Gson().toJson(productdet);
                    json2 = new Gson().toJson(productlist);
                    String json3 = new Gson().toJson(proplist);
                    json = "[" + json1 + "," + json2 + "," + json3 + "]";
                    break;
                }
                case "PlaceOrder": {
                    String userid = (String) jsonParameter.get("userid");
                    int CustomerUserID = Integer.parseInt(userid);
                    String supplieruserid = (String) jsonParameter.get("supplieruserid");
                    int SupplierUserID = Integer.parseInt(supplieruserid);
                    String orderdetails = (String) jsonParameter.get("orderdetails");
                    String totalamount = (String) jsonParameter.get("totalamount");
                    String deliveryaddress = (String) jsonParameter.get("deliveryaddress");
                    String deliveryfee = (String) jsonParameter.get("deliveryfee");
                    String CName = UserManager.getUserName(CustomerUserID);
                    String SName = UserManager.getUserName(SupplierUserID);
                    int orderid = BookingManager.PlaceOrder(CustomerUserID, SupplierUserID, orderdetails, totalamount.trim(), deliveryaddress, deliveryfee);
                    UserManager.sendMemberMessage(CustomerUserID, CName + ", your order has been recieved thank you.", "Placed Order", 1);
                    String OrderNumber = BookingManager.getOrderNumber(orderid);
                    String Subject = "Placed Order";
                    UserManager.sendMemberMessage(SupplierUserID, SName + ", an order has been placed for your delivery", Subject, 1);
                    String supmsg = "An order with Order Number " + OrderNumber + " has been placed for your delivery.";
                    String cusmsg = "Your order with Order Number " + OrderNumber + " is pending confirmation.";
                    String adminmsg = "An order with Order Number " + OrderNumber + " has been placed for delivery.";
                    PushManager.sendPushNotification(Subject, supmsg, "supplier-" + orderid, SupplierUserID);
                    PushManager.sendPushNotification(Subject, cusmsg, "customer-" + orderid, CustomerUserID);
                    PushManager.sendPushNotification(Subject, adminmsg, "admin-" + orderid, 1);
                    json = new Gson().toJson(orderid);
                    break;
                }

                case "GetlocationDetails": {
                    String locationid = (String) jsonParameter.get("locationid");
                    int LocationID = Integer.parseInt(locationid);
                    HashMap<String, String> UserDet = UserManager.GetLocationName(LocationID);
                    json = new Gson().toJson(UserDet);
                    break;
                }

                case "getPlacedOrders": {
                    String usertype = (String) jsonParameter.get("usertype");
                    String userid = (String) jsonParameter.get("userid");
                    String ordertype = (String) jsonParameter.get("ordertype");
                    int UserID = Integer.parseInt(userid);
                    ArrayList<Integer> IDS = new ArrayList<>();

                    if (usertype.equals("Admin")) {
                        IDS = BookingManager.getOrderIDS();
                    } else if (usertype.equals("Customer")) {
                        IDS = BookingManager.GetCustomerOrderIDS(UserID);
                    } else if (usertype.equals("Supplier")) {
                        IDS = BookingManager.GetSupplierOrderIDS(UserID);
                    }
                    ArrayList<HashMap<String, String>> OrderList = new ArrayList<>();
                    if (!IDS.isEmpty()) {
                        for (int Oid : IDS) {
                            HashMap<String, String> OrderDetails = new HashMap<>();
                            if (ordertype.equals("Pending")) {
                                OrderDetails = BookingManager.GetPendingOrderDetails(Oid);
                            } else if (ordertype.equals("Delivered")) {
                                OrderDetails = BookingManager.GetDeliveredOrderDetails(Oid);
                            } else if (ordertype.equals("Cancelled")) {
                                OrderDetails = BookingManager.GetCancelledOrderDetails(Oid);
                            }
                            if (!OrderDetails.isEmpty()) {
                                OrderDetails.put("count", "" + IDS.size());
                                OrderList.add(OrderDetails);
                            }
                        }
                        String code = "200";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(OrderList);
                        json = "[" + json1 + "," + json2 + "]";

                    } else {
                        HashMap<String, String> OrderDetails = new HashMap<>();
                        String code = "400";
                        String message = "Sorry, no order(s)";
                        OrderDetails.put("empty", message);
                        OrderList.add(OrderDetails);
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(OrderList);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }

                case "GetOrderDetails": {
                    String orderid = (String) jsonParameter.get("orderid");
                    int OrderID = Integer.parseInt(orderid);
                    ArrayList<HashMap<String, String>> Productlist = new ArrayList<>();
                    HashMap<String, String> OrderDetails = BookingManager.GetOrderDetails(OrderID);
                    int CustUserID = Integer.parseInt(OrderDetails.get(Tables.OrderTable.CustomerUserID));
                    String UserName = UserManager.getUserName(CustUserID);
                    OrderDetails.put("CustomerName", UserName);
                    String UserPhone = UserManager.getUserPhone(CustUserID);
                    OrderDetails.put("CustomerPhone", UserPhone);
                    OrderDetails.put("CustomerUserID", "" + CustUserID);
                    int SuppUserID = Integer.parseInt(OrderDetails.get(Tables.OrderTable.SupplierUserID));
                    String SuppUserName = UserManager.getUserName(SuppUserID);
                    OrderDetails.put("SupplierName", SuppUserName);
                    String SuppUserPhone = UserManager.getUserPhone(SuppUserID);
                    OrderDetails.put("SupplierPhone", SuppUserPhone);
                    OrderDetails.put("SupplierUserID", "" + SuppUserID);

                    String orderdetails = OrderDetails.get(Tables.OrderTable.ProductDetails);
                    if (!orderdetails.equals("")) {
                        String details[] = orderdetails.split(";");
                        for (String record : details) {
                            String productid = record.split(",")[0];
                            int ProductID = Integer.parseInt(productid);
                            HashMap<String, String> ProductDet = UserManager.GetProduct(ProductID);
                            String Quantity = record.split(",")[1];
                            ProductDet.put("Quantity", Quantity);
                            String Amount = record.split(",")[2];
                            ProductDet.put("Amount", Amount);
                            Productlist.add(ProductDet);
                        }
                        json1 = new Gson().toJson(OrderDetails);
                        json2 = new Gson().toJson(Productlist);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        break;
                    }
                    break;
                }
                case "ConfirmOrder": {
                    String OID = (String) jsonParameter.get("orderid");
                    int OrderID = Integer.parseInt(OID);
                    String userid = (String) jsonParameter.get("userid");
                    int UserID = Integer.parseInt(userid);
                    String Status = BookingManager.getOrderStatus(OrderID);
                    if (Status.equals("Pending")) {
                        String OrderStatus = "Delivered";
                        result = BookingManager.ConfirmOrder(OrderID, OrderStatus, UserID);
                        if (result.equals("success")) {
                            String code = "200";
                            json1 = new Gson().toJson(code);
                            json2 = new Gson().toJson(result);
                            json = "[" + json1 + "," + json2 + "]";
                        } else {
                            String code = "400";
                            String message = "Sorry, no history";
                            json1 = new Gson().toJson(code);
                            json2 = new Gson().toJson(message);
                            json = "[" + json1 + "," + json2 + "]";
                        }
                    } else {
                        String code = "400";
                        String message = "Sorry, Order has already been confirmed";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(message);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "AcceptOrder": {
                    String OID = (String) jsonParameter.get("orderid");
                    int OrderID = Integer.parseInt(OID);
                    String OrdersubStatus = "Pending Delivery";
                    int CustomerUserID = BookingManager.getOrderCustomerUserID(OrderID);
                    int SupplierUserID = BookingManager.getOrderSupplierUserID(OrderID);
                    String Status = BookingManager.getOrderStatus(OrderID);
                    if (!Status.equals("Delivered")) {
                        result = BookingManager.UpdateOrderSubStatus(OrderID, OrdersubStatus);
                        if (result.equals("success")) {
                            String OrderNumber = BookingManager.getOrderNumber(OrderID);
                            String CustomerName = UserManager.getUserName(CustomerUserID);
                            String CName = UserManager.getUserName(CustomerUserID);
                            String SName = UserManager.getUserName(SupplierUserID);
                            String Subject = "Order Accepted";
                            String Content = CName +", an order with Order Number " + OrderNumber + " has been accepted and its pending delivery.";
                            String Content2 = SName + ", you accepted to deliver an order with Order Number " + OrderNumber + " to " + CustomerName + " and its pending your delivery.";
                            UserManager.sendMemberMessage(CustomerUserID, Content, Subject, 1);
                            UserManager.sendMemberMessage(SupplierUserID, Content2, Subject, 1);
                            String supmsg = Content2;
                            String cusmsg = Content;
                            String adminmsg = Content ;
                            PushManager.sendPushNotification(Subject, supmsg, "supplier-" + OrderID, SupplierUserID);
                            PushManager.sendPushNotification(Subject, cusmsg, "customer-" + OrderID, CustomerUserID);
                            PushManager.sendPushNotification(Subject, adminmsg, "admin-" + OrderID,  1);
                            String code = "200";
                            json1 = new Gson().toJson(code);
                            json2 = new Gson().toJson(CustomerUserID);
                            json = "[" + json1 + "," + json2 + "]";
                        } else {
                            String code = "400";
                            String message = "Sorry, no history";
                            json1 = new Gson().toJson(code);
                            json2 = new Gson().toJson(message);
                            json = "[" + json1 + "," + json2 + "]";
                        }
                    } else {

                    }

                    break;
                }
                case "CancelOrder": {
                    String oid = (String) jsonParameter.get("orderid");
                    int OrderID = Integer.parseInt(oid);
                    String userid = (String) jsonParameter.get("userid");
                    int UserID = Integer.parseInt(userid);
                    String Status = BookingManager.getOrderStatus(OrderID);
                    if (!Status.equals("Cancelled")) {
                        String Subject = "Order Cancelled";
                        String OrderNumber = BookingManager.getOrderNumber(OrderID);
                        String Username = UserManager.getUserName(UserID);
                        String Content = "An order with Order Number " + OrderNumber + " has been cancelled by " + Username;
                        int SupUserID = BookingManager.getOrderSupplierUserID(OrderID);
                        int CusUserID = BookingManager.getOrderCustomerUserID(OrderID);
                        UserManager.sendMemberMessage(SupUserID, Content, Subject, 1);
                        UserManager.sendMemberMessage(CusUserID, Content, Subject, 1);
                        result = BookingManager.CancelOrderRequest(OrderID, Username);
                        String supmsg = Content;
                        String cusmsg = Content;
                        String adminmsg = Content;
                        PushManager.sendPushNotification(Subject, supmsg, "supplier-" + OrderID, SupUserID);
                        PushManager.sendPushNotification(Subject, cusmsg, "customer-" + OrderID, CusUserID);
                        PushManager.sendPushNotification(Subject, adminmsg, "admin-" + OrderID, 1);
                        String code = "200";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(result);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        String code = "400";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson("Order has already been cancelled");
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "DeleteOrder": {
                    String oid = (String) jsonParameter.get("orderid");
                    int OrderID = Integer.parseInt(oid);
                    String userid = (String) jsonParameter.get("userid");
                    int UserID = Integer.parseInt(userid);
                    String Status = BookingManager.getOrderStatus(OrderID);
                    if (Status.equals("Cancelled") || Status.equals("Delivered")) {
                        result = BookingManager.DeleteOrder(OrderID);
                        String Name = UserManager.getUserName(UserID);
                        String OrderNumber = BookingManager.getOrderNumber(OrderID);
                        String bdy = "Order with Order Number " + OrderNumber + " has been deleted by " + Name;
                        UserManager.sendMemberMessage(1, bdy, "Order Deleted", UserID);
                        if (Status.equals("Delivered")) {
                            BookingManager.UpdateDeletedOrder(OrderID);
                        }
                        PushManager.sendPushNotification("Deleted Order","", bdy, 1);
                        String code = "200";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(result);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        String code = "400";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson("Order has already been cancelled");
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
