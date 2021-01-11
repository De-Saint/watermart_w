/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Tables;

/**
 *
 * @author Saint
 */
public class Tables {
    
     public static class AddressLGA {

        public static String Table = "address_lga";
        public static String ID = "id";
        public static String LGA = "lga";
        public static String GroupID = "group_id";
    }
     
    public static class AddressStates {

        public static String Table = "address_states";
        public static String ID = "id";
        public static String State = "state";
        public static String Country = "country";
    }
    
    public static class AdminTable {

        public static String Table = "admins";
        public static String ID = "id";
        public static String UserID = "userid";
        public static String Address = "address";
        public static String AccountBalance = "account_bal";
        public static String TransactionNumber = "transaction_number";
    }

    public static class CustomerTable {

        public static String Table = "customers";
        public static String ID = "id";
        public static String UserID = "userid";
         public static String Address = "address";
         public static String Status = "status";
    }
    public static class LocationTable {

        public static String Table = "locations";
        public static String ID = "id";
        public static String SupplierUserID = "supplier_userid";
        public static String Name = "name";
        public static String Fees = "fees";
       
    }
    public static class MessageTable {

        public static String Table = "messages";
        public static String ID = "id";
        public static String Date = "date";
        public static String Time = "time";
        public static String Subject = "subject";
        public static String IsRead = "is_read";
        public static String FromMemberID = "from_member_id";
        public static String ToMemberID = "to_member_id";
        public static String Body = "body";
    }
    public static class OrderTable {

        public static String Table = "orders";
        public static String ID = "id";
        public static String CustomerUserID = "customer_userid";
        public static String SupplierUserID = "supplier_userid";
        public static String ProductDetails = "productdetails";
        public static String Status = "status";
        public static String SubStatus = "substatus";
        public static String BookedTime = "bookedtime";
        public static String BookedDate = "bookeddate";
        public static String DeliveryTime = "deliverytime";
        public static String DeliveryDate = "deliverydate";
        public static String OrderNumber = "ordernumber";
        public static String ConfirmedByUserID = "confirmedby_userid";
        public static String Amount = "amount";
        public static String DeliveryAddress = "deliveryaddress";
        public static String DeliveryFees = "deliveryfee";
        public static String PaymentPlan = "paymentplan";
    }
      public static class ProductCategoryTable {

        public static String Table = "product_categories";
        public static String ID = "id";
        public static String Name = "name";
        public static String WaterCategoryID = "water_catid";
    }
    public static class ProductTable {

        public static String Table = "products";
        public static String ID = "id";
        public static String Name = "name";
        public static String Price = "price";
        public static String Properties = "properties";
        public static String ProductCategoryID  = "product_catid";
        public static String SupplierUserID  = "supplier_userid";
    }
 
    public static class RecoveryTable {

        public static String Table = "recovery";
        public static String ID = "id";
        public static String UserID = "userid";
        public static String Question = "question";
        public static String Answer = "answer";
    }
    public static class SupplierTable {

        public static String Table = "suppliers";
        public static String ID = "id";
        public static String UserID = "userid";
        public static String BusinessName = "business_name";
        public static String NAFDACNumber = "nafdac_number";
        public static String Address = "address";
        public static String Status = "status";
        public static String SupplierCode = "supplier_code";
        public static String PaymentPlanID = "payment_planid";
        public static String WaterCategoryID = "water_catid";
        public static String RateValue = "ratevalue";
        public static String PaymentDate = "payment_date";
        public static String PaymentDueDate = "payment_duedate";
        public static String AccountBalance = "account_bal";
        public static String AdminAccountBalance = "admin_account_bal";
        public static String TransactionNumber = "transaction_number";
    }
    public static class UserTable {

        public static String Table = "users";
        public static String ID = "id";
        public static String FirstName = "firstname";
        public static String LastName = "lastname";
        public static String Email = "email";
        public static String Phone = "phone";
        public static String Password = "password";
        public static String UserType = "type";
        public static String DateCreated = "date_created";
        public static String DeviceToken = "devicetoken";
        
    }
    public static class WaterCategoryTable {

        public static String Table = "water_categories";
        public static String ID = "id";
        public static String Name = "name";
        
    }
    public static class PaymentPlanTable {

        public static String Table = "payment_plan";
        public static String ID = "id";
        public static String Name = "name";
        
    }
    public static class RatingTable {
        public static String Table = "ratings";
        public static String ID = "id";
        public static String Value = "value";
        public static String SupplierUserID = "supplier_userid";
        public static String CustomerUserID = "customer_userid";
        
    }

}
