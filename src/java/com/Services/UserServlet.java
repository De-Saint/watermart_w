/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Services;

import com.Managers.GeneralManager;
import com.Managers.DBManager;
import com.Managers.PushManager;
import com.Managers.UserManager;
import com.Tables.Tables;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class UserServlet extends HttpServlet {

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
            StringBuilder htmlBuilder = new StringBuilder();
            String caseType = (String) jsonParameter.get("type");
            String json = "";
            String json1 = "";
            String json2 = "";
            String json3 = "";
            String result = "";
            switch (caseType) {
                case "Login": {
                    String Email_PhoneNumber = (String) jsonParameter.get("emailphone");
                    String Password = (String) jsonParameter.get("password");
                    String UserType = "";
                    HashMap<String, String> UserDetails = new HashMap<>();
                    int UserID = 0;
                    if (UserManager.checkEmailAddressOrPhoneNumberExist(Email_PhoneNumber)) {
                        UserID = UserManager.checkPasswordEmailMatch(Password, Email_PhoneNumber);
                        if (UserID != 0) {
                            UserType = UserManager.getUserType(UserID);
                            UserDetails = UserManager.GetUserDetails(UserID, UserType);
                            if (!UserDetails.isEmpty()) {
                                String code = "200";
                                json1 = new Gson().toJson(code);
                                json2 = new Gson().toJson(UserDetails);
                                json = "[" + json1 + "," + json2 + "]";
                            } else {
                                result = "Please your account has issues, please email watermart_info@baesicsolutions.com or call +234 706 748 3120 for details";
                                String code = "400";
                                json1 = new Gson().toJson(code);
                                json2 = new Gson().toJson(result);
                                json = "[" + json1 + "," + json2 + "]";
                            }

                        } else {
                            result = "Incorrect Login Parameters";
                            String code = "400";
                            json1 = new Gson().toJson(code);
                            json2 = new Gson().toJson(result);
                            json = "[" + json1 + "," + json2 + "]";
                        }
                    } else {
                        result = "Email or Phone Number Entered Doesn't Exist";
                        String code = "400";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(result);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "UserRegistration": {
                    String EmailAddress = (String) jsonParameter.get("email");
                    String PhoneNumber = (String) jsonParameter.get("phone");
                    String Password = (String) jsonParameter.get("password");

                    String FirstName = (String) jsonParameter.get("firstname");
                    String LastName = (String) jsonParameter.get("lastname");
                    String question = (String) jsonParameter.get("question");
                    String answer = (String) jsonParameter.get("answer");

                    String stateid = (String) jsonParameter.get("stateid");
                    String lgaid = (String) jsonParameter.get("lgaid");
                    String address = (String) jsonParameter.get("address");
                    String stateName = DBManager.GetString("state", "address_states", "where id = '" + stateid + "'");
                    String lgaName = DBManager.GetString("lga", "address_lga", "where id = '" + lgaid + "'");
                    String Address = address + ", " + lgaName + ", " + stateName + " State";

                    String Subclass = "Customer";
                    java.sql.Date DateCreated = UserManager.CurrentDate();
                    if (!UserManager.checkEmailAddressOrPhoneNumberExist(EmailAddress)) {
                        if (!UserManager.checkEmailAddressOrPhoneNumberExist(PhoneNumber)) {
                            int UserID = UserManager.RegisterUser(EmailAddress, PhoneNumber, Password, FirstName, LastName, Subclass, DateCreated);
                            if (UserID != 0) {
                                result = UserManager.RegisterCustomer(UserID, Address);
                                String Subject = "Customer Account Created";
                                String Content = LastName + " your WaterMart Customer Account has been created. Login Details::: " + "Email: "
                                        + EmailAddress + " Password: " + Password;
                                UserManager.CreateRecovery(UserID, question, answer);
                                UserManager.sendMemberMessage(UserID, Content, Subject, 1);
                                String cusmsg = LastName + ", your registration was successful. Thanks for joining WaterMart";
                                String adminmsg = "WaterMart Customer Account has been created for " + LastName;
                                PushManager.sendPushNotification(Subject, cusmsg, "", UserID);
                                PushManager.sendPushNotification(Subject, adminmsg, "", 1);
                            } else {
                                result = "Something went wrong while creating Account";
                                String code = "400";
                                json1 = new Gson().toJson(code);
                                json2 = new Gson().toJson(result);
                                json = "[" + json1 + "," + json2 + "]";
                            }
                        } else {
                            result = "Account with Phone Number already Exists";
                            String code = "400";
                            json1 = new Gson().toJson(code);
                            json2 = new Gson().toJson(result);
                            json = "[" + json1 + "," + json2 + "]";
                        }
                    } else {
                        result = "Account with Email Address already Exists";
                        String code = "400";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(result);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    String code = "200";
                    json1 = new Gson().toJson(code);
                    json2 = new Gson().toJson(result);
                    json = "[" + json1 + "," + json2 + "]";
                    break;
                }
                case "SupplierRegistration": {
                    String BizName = (String) jsonParameter.get("bizname");
                    String NafdacNumber = (String) jsonParameter.get("nafdacnumber");
                    String EmailAddress = (String) jsonParameter.get("email");
                    String PhoneNumber = (String) jsonParameter.get("phone");
                    String Password = (String) jsonParameter.get("password");

                    String watercatid = (String) jsonParameter.get("watertype");
                    String planid = (String) jsonParameter.get("payplan");
                    int WaterCatID = Integer.parseInt(watercatid);
                    int PlanID = Integer.parseInt(planid);
                    String FirstName = (String) jsonParameter.get("firstname");
                    String LastName = (String) jsonParameter.get("lastname");
                    String question = (String) jsonParameter.get("question");
                    String answer = (String) jsonParameter.get("answer");

                    String stateid = (String) jsonParameter.get("stateid");
                    String lgaid = (String) jsonParameter.get("lgaid");
                    String address = (String) jsonParameter.get("address");
                    String stateName = DBManager.GetString("state", "address_states", "where id = '" + stateid + "'");
                    String lgaName = DBManager.GetString("lga", "address_lga", "where id = '" + lgaid + "'");
                    String Address = address + ", " + lgaName.trim() + ", " + stateName + " State";

                    String Subclass = "Supplier";
                    java.sql.Date DateCreated = UserManager.CurrentDate();
                    if (!UserManager.checkEmailAddressOrPhoneNumberExist(EmailAddress)) {
                        if (!UserManager.checkEmailAddressOrPhoneNumberExist(PhoneNumber)) {
                            int UserID = UserManager.RegisterUser(EmailAddress, PhoneNumber, Password, FirstName, LastName, Subclass, DateCreated);
                            if (UserID != 0) {
                                String SupplierCode = UserManager.CreateRandomCode(8);
                                String SupplierStatus = "Not Activated";
                                result = UserManager.RegisterSupplier(UserID, BizName, NafdacNumber, Address, SupplierStatus, SupplierCode, WaterCatID, PlanID);
                                String Subject = "Supplier Account Created";
                                String Content = LastName + " your WaterMart Supplier Account has been created with the following details, Login Details::: Email "
                                        + EmailAddress + " Password: " + Password + " Supplier Code: " + SupplierCode;
                                UserManager.sendMemberMessage(UserID, Content, Subject, 1);
                                UserManager.CreateRecovery(UserID, question, answer);
                                String cusmsg = LastName + ", your registration was successful. Thanks for joining WaterMart";
                                String adminmsg = "WaterMart Supplier Account has been created for " + LastName;
                                PushManager.sendPushNotification(Subject, cusmsg, "", UserID);
                                PushManager.sendPushNotification(Subject, adminmsg, "", 1);
                            } else {
                                result = "Something went wrong while creating Account";
                                String code = "400";
                                json1 = new Gson().toJson(code);
                                json2 = new Gson().toJson(result);
                                json = "[" + json1 + "," + json2 + "]";
                            }
                        } else {
                            result = "Account with Email Address already Exists";
                            String code = "400";
                            json1 = new Gson().toJson(code);
                            json2 = new Gson().toJson(result);
                            json = "[" + json1 + "," + json2 + "]";
                        }
                    } else {
                        result = "Account with Email Address already Exists";
                        String code = "400";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(result);
                        json = "[" + json1 + "," + json2 + "]";
                    }

                    String code = "200";
                    json1 = new Gson().toJson(code);
                    json2 = new Gson().toJson(result);
                    json = "[" + json1 + "," + json2 + "]";
                    break;
                }

                case "getStates": {
                    ArrayList<Integer> ids = DBManager.GetIntArrayListDescending("id", "address_states", "");//and lastname != 'Admin'"
                    HashMap<String, String> states = new HashMap<>();
                    ArrayList<HashMap<String, String>> stateslist = new ArrayList<>();
                    if (!ids.isEmpty()) {
                        for (int i : ids) {
                            states = DBManager.GetTableData("address_states", "WHERE id= " + i);
                            stateslist.add(states);
                        }
                        json1 = new Gson().toJson(stateslist);
                        json = "[" + json1 + "]";
                    }
                    break;
                }
                case "getWaterCategory": {
                    ArrayList<Integer> ids = UserManager.getWaterCatIds();
                    HashMap<String, String> cat = new HashMap<>();
                    ArrayList<HashMap<String, String>> catlist = new ArrayList<>();
                    if (!ids.isEmpty()) {
                        for (int i : ids) {
                            cat = UserManager.GetWaterCategory(i);
                            catlist.add(cat);
                        }
                        json1 = new Gson().toJson(catlist);
                        json = "[" + json1 + "]";
                    }
                    break;
                }
                case "getPaymentPlan": {
                    ArrayList<Integer> ids = UserManager.getPaymentPlanIds();
                    HashMap<String, String> plan = new HashMap<>();
                    ArrayList<HashMap<String, String>> planlist = new ArrayList<>();
                    if (!ids.isEmpty()) {
                        for (int i : ids) {
                            plan = UserManager.GetPaymentPlanByID(i);
                            planlist.add(plan);
                        }
                        json1 = new Gson().toJson(planlist);
                        json = "[" + json1 + "]";
                    }
                    break;
                }
                case "getLGAs": {
                    String stateid = (String) jsonParameter.get("stateid");
                    ArrayList<Integer> ids = DBManager.GetIntArrayListDescending("id", "address_lga", "where group_id = " + stateid);//and lastname != 'Admin'"
                    HashMap<String, String> lga = new HashMap<>();
                    ArrayList<HashMap<String, String>> lgalist = new ArrayList<>();
                    if (!ids.isEmpty()) {
                        for (int i : ids) {
                            lga = DBManager.GetTableData("address_lga", "WHERE id= " + i);
                            lgalist.add(lga);
                        }
                        json1 = new Gson().toJson(lgalist);
                        json = "[" + json1 + "]";
                    }
                    break;
                }
                case "checkEmail": {
                    String typedEmail = (String) jsonParameter.get("email");
                    if (typedEmail.length() > 2) {
                        if (UserManager.checkEmailAddressOrPhoneNumberExist(typedEmail)) {
                            result = "Available";
                            json = new Gson().toJson(result);
                        } else {
                            result = "NotAvailable";
                            json = new Gson().toJson(result);
                        }
                    } else {
                        break;
                    }

                    break;
                }
                case "UpdateProfile": {
                    String userid = (String) jsonParameter.get("id");
                    String BizName = (String) jsonParameter.get("firstname");
                    String NafDacNumber = (String) jsonParameter.get("firstname");
                    String FirstName = (String) jsonParameter.get("firstname");
                    String LastName = (String) jsonParameter.get("lastname");
                    String PhoneNumber = (String) jsonParameter.get("phone");
                    String Password = (String) jsonParameter.get("password");

                    int id = Integer.parseInt(userid);
                    if (BizName != null) {
                        result = UserManager.UpdateBusinessName(id, BizName);
                    }
                    if (NafDacNumber != null) {
                        result = UserManager.UpdateNAFDACNumber(id, NafDacNumber);
                    }
                    if (FirstName != null) {
                        result = UserManager.UpdateFirstName(id, FirstName);
                    }
                    if (LastName != null) {
                        result = UserManager.UpdateLastName(id, LastName);
                    }
                    if (Password != null) {
                        result = UserManager.UpdateUserPassword(id, Password);
                    }
                    if (PhoneNumber != null) {
                        result = UserManager.UpdatePhone(id, PhoneNumber);
                    }
                    if (result.equals("success")) {
                        String code = "200";
                        String message = "Details Updated";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(message);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        String code = "400";
                        String message = "Something went wrong. Try again later";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(message);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "SearchForSupplier": {
                    String UserInput = (String) jsonParameter.get("searchvalue");
                    HashMap<String, String> res = new HashMap<>();
                    String suplastName = "none";
                    String supfirstName = "none";
                    String supCompanyName = "none";
                    if (!UserInput.equals("")) {
                        int Supplierid = DBManager.GetInt("supplierid", "suppliers", "where  suppliercode = '" + UserInput + "'");//email search
                        if (Supplierid != 0) {
                            int userid = DBManager.GetInt("userid", "suppliers", "where supplierid = '" + Supplierid + "'");//account number search
                            suplastName = DBManager.GetString("lastname", "users", "where userid = " + userid);
                            supfirstName = DBManager.GetString("firstname", "users", "where userid = " + userid);
                            supCompanyName = DBManager.GetString("company_name", "suppliers", "where supplierid = '" + Supplierid + "'");
                            res.put("Supplierid", "" + Supplierid);

                        } else {
                            String errormsg = "Something went wrong, Try again";
                            json = new Gson().toJson(errormsg);
                        }
                    }
                    res.put("SupplierCompanyName", supCompanyName);
                    res.put("SupplierFIrstName", supfirstName);
                    res.put("SupplierLastName", "" + suplastName);
                    json = new Gson().toJson(result);
                    break;
                }
                case "getRecoveryDetails": {
                    String UserInput = (String) jsonParameter.get("email");
                    int userid = UserManager.checkEmailMatch(UserInput);
                    if (userid == 0) {
                        String code = "400";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson("Invalid Email");
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        HashMap<String, String> data = UserManager.GetRecoveryDetails(userid);
                        json = new Gson().toJson(data);
                    }
                    break;
                }
                case "ResetPassword": {
                    String UserInput = (String) jsonParameter.get("userid");
                    int membid = Integer.parseInt(UserInput);
                    String newpassword = (String) jsonParameter.get("password");
                    String res = UserManager.UpdateUserPassword(membid, newpassword);
                    if (res.equals("success")) {
                        String body = "Password has been changed to " + newpassword;
                        UserManager.sendMemberMessage(membid, body, "Password Reset", 1);
                        String code = "200";
                        String message = "Successful";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(message);
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

                case "Message": {
                    String memberid = (String) jsonParameter.get("userid");
                    int userid = Integer.parseInt(memberid);
                    ArrayList<Integer> list = new ArrayList<>();
                    String option = (String) jsonParameter.get("option");
                    HashMap<String, String> msgdetails = new HashMap<>();
                    if (option.equals("inbox")) {
                        list = GeneralManager.getInboxMessageIDs(userid);
                    } else if (option.equals("sent")) {
                        list = GeneralManager.getSentMessageIDs(userid);
                    }
                    ArrayList<HashMap<String, String>> msglist = new ArrayList<>();
                    if (!list.isEmpty()) {
                        for (int id : list) {
                            msgdetails = GeneralManager.getMessageDetails(id);//
                            int senderid = Integer.parseInt(msgdetails.get(Tables.MessageTable.FromMemberID));
                            String sendername = GeneralManager.getUserName(senderid);
                            msgdetails.put("sendername", sendername);
                            int recieverid = Integer.parseInt(msgdetails.get(Tables.MessageTable.ToMemberID));
                            String recievername = GeneralManager.getUserName(recieverid);
                            msgdetails.put("recievername", recievername);
                            msglist.add(msgdetails);
                        }
                        String code = "200";
                        json1 = new Gson().toJson(code);
                        json2 = new Gson().toJson(msglist);
                        json = "[" + json1 + "," + json2 + "]";
                    } else {
                        String code = "400";
                        json1 = new Gson().toJson(code);
                        String message = "Sorry no message(s)";
                        json2 = new Gson().toJson(message);
                        json = "[" + json1 + "," + json2 + "]";
                    }
                    break;
                }
                case "MarkAsRead": {
                    String messageid = (String) jsonParameter.get("messageid");
                    int msgid = Integer.parseInt(messageid);
                    String res = GeneralManager.MarkMessageAsRead(msgid);
                    if (res.equals("failed")) {
                        String message = "Something went wrong! try again Later";
                        json = new Gson().toJson(message);
                    } else {
                        String message = "Message Read";
                        json = new Gson().toJson(message);

                    }
                    break;
                }
                case "DeleteMessage": {
                    String messageid = (String) jsonParameter.get("messageid");
                    int msgid = Integer.parseInt(messageid);
                    String res = GeneralManager.DeleteMessage(msgid);
                    if (res.equals("failed")) {
                        String message = "Something went wrong! try again Later";
                        json = new Gson().toJson(message);
                    } else {
                        String message = "Message Deleted";
                        json = new Gson().toJson(message);

                    }
                    break;
                }

                case "MessageDetails": {
                    String messageid = (String) jsonParameter.get("messageid");
                    int MessageID = Integer.parseInt(messageid);
                    HashMap<String, String> det = new HashMap<>();
                    det = GeneralManager.getMessageDetails(MessageID);
                    if (!det.isEmpty()) {
                        json = new Gson().toJson(det);
                    } else {
                        String message = "Something went wrong! try again Later";
                        json = new Gson().toJson(message);
                    }
                    break;
                }
                case "SendMessage": {
                    String fromuserid = (String) jsonParameter.get("fromuserid");
                    int FromUserID = Integer.parseInt(fromuserid);
                    String touserid = (String) jsonParameter.get("touserid");
                    int ToUserID = Integer.parseInt(touserid);
                    String title = (String) jsonParameter.get("msgTitle");
                    String bdy = (String) jsonParameter.get("msgBody");
                    if (bdy != null && bdy != "") {
                        int messageid = UserManager.sendMemberMessage(ToUserID, bdy, title, FromUserID);
                        String message = "Message Sent";
                        String NameFrom = UserManager.getUserName(FromUserID);
                        String UserTypeTo = UserManager.getUserType(ToUserID);
                        String msg = "";
                        if (UserTypeTo.equals("Admin")) {
                            msg = "Your have a new message from " + NameFrom;
                            PushManager.sendPushNotification("New Message", msg, "adminmsg-" + messageid, ToUserID);
                        } else if (UserTypeTo.equals("Supplier")) {
                            msg = "Your have a new message from " + NameFrom;
                            PushManager.sendPushNotification("New Message", msg, "suppliermsg-" + messageid, ToUserID);
                        } else if (UserTypeTo.equals("Customer")) {
                            msg = "Your have a new message from " + NameFrom;
                            PushManager.sendPushNotification("New Message", msg, "customermsg-" + messageid,  ToUserID);
                        }
                        json = new Gson().toJson(message);
                    } else {
                        String message = "Populate Empty Fields";
                        json = new Gson().toJson(message);
                    }
                    break;
                }
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
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
