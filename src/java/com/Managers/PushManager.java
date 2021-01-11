/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Managers;

import com.Tables.Tables;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

/**
 *
 * @author Saint
 */
public class PushManager {

    public PushManager() {

    }

    public static HttpURLConnection sendPushNotification(String title, String message, String additionalData, int userid) throws Exception {
        HttpURLConnection conn = null;
        String SERVER_KEY = "AAAAlGcNF74:APA91bE7MVWb02LRGyGF_HtXh4ANwPfCenxPqujRs6dy7HLrXq2yIFY5KEETAGfU39geLM5a55mmv_YL8JEWYzv5ocpwVpaM90vLahDnj6eljAOAemsHxm5ToMt_yrVJ-SABGQc2QZlU";
        String DEVICE_TOKEN = DBManager.GetString(Tables.UserTable.DeviceToken, Tables.UserTable.Table, "where " + Tables.UserTable.ID + " = " + userid);
        String pushMessage = "{\"data\":{\"title\":\""
                + title
                + "\",\"message\":\""
                + additionalData
                + "\",\"additionalData\":\""
                + message
                + "\"},\"to\":\""
                + DEVICE_TOKEN
                + "\"}";
        try {
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty(
                    "Authorization", "key=" + SERVER_KEY);
            conn.setRequestProperty(
                    "Content-Type", "application/json");
            conn.setRequestMethod(
                    "POST");
            conn.setDoOutput(
                    true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();

            outputStream.write(pushMessage.getBytes());

            System.out.println(conn.getResponseCode());
            System.out.println(conn.getResponseMessage());
            int code = conn.getResponseCode();
            String result = conn.getResponseMessage();
            String me = result;
        } catch (Exception e) {
            String error = e.getMessage();
            System.out.print(error);
            return conn;
        }
        // Create connection to send FCM Message request.
        return conn;
    }

    public static String UpdateDeviceToken(int UserID, String DeviceToken) throws ClassNotFoundException, SQLException {
        String result = "";
        DBManager.UpdateStringData(Tables.UserTable.Table, Tables.UserTable.DeviceToken, DeviceToken, "where " + Tables.UserTable.ID + " = " + UserID);
        return result;
    }
}
