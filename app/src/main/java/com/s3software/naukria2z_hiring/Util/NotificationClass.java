package com.s3software.naukria2z_hiring.Util;

import android.app.Application;

import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

public class NotificationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }


    class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {

            String title=result.notification.payload.title;
            String desc=result.notification.payload.body;

            /*Intent intent = new Intent(getApplicationContext(), YourMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("push_title", title);
            intent.putExtra("push_message", desc);
            startActivity(intent);*/

        }
    }
}