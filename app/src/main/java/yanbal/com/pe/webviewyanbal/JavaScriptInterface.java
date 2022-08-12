package yanbal.com.pe.webviewyanbal;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class JavaScriptInterface {
    private Context context;
    public JavaScriptInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void getBase64FromBlobData(String base64Data) throws IOException {
        convertBase64StringToPdfAndStoreIt(base64Data);
    }
    public static String getBase64StringFromBlobUrl(String blobUrl) {
        if(blobUrl.startsWith("blob")){
            return "javascript: var xhr = new XMLHttpRequest();" +
                    "xhr.open('GET', '"+ blobUrl +"', true);" +
                    "xhr.setRequestHeader('Content-type','application/pdf');" +
                    "xhr.responseType = 'blob';" +
                    "xhr.onload = function(e) {" +
                    "    if (this.status == 200) {" +
                    "        var blobPdf = this.response;" +
                    "        var reader = new FileReader();" +
                    "        reader.readAsDataURL(blobPdf);" +
                    "        reader.onloadend = function() {" +
                    "            base64data = reader.result;" +
                    "            Android.getBase64FromBlobData(base64data);" +
                    "        }" +
                    "    }" +
                    "};" +
                    "xhr.send();";
        }
        return "javascript: console.log('It is not a Blob URL');";
    }


    public void convertBase64StringToPdfAndStoreIt(String base64PDf) throws IOException {
        Log.e("BASE 64", base64PDf);
        final int notificationId = 1;
        String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());

       String externalFileLoc =  context.getExternalFilesDir
                (Environment.DIRECTORY_DOWNLOADS)
                + "/cpg/";
       File dwldsPath = new File(externalFileLoc);
       if(!dwldsPath.exists())
           dwldsPath.mkdir();


        final File newFileName = new File(externalFileLoc + "/ivycpgHanifa_" + currentDateTime + ".pdf");
        newFileName.createNewFile();
        byte[] pdfAsBytes = Base64.decode("JVBERi0xLjMNCiXlv4/lq4wNCg0KMSAwIG9iag0KPDwNCi9UeXBlIC9DYXRhbG9nDQovT3V0bGluZXMgMiAwIFINCi9QYWdlcyAzIDAgUg0KPj4NCmVuZG9iag0KDQoyIDAgb2JqDQo8PA0KL1R5cGUgL091dGxpbmVzDQovQ291bnQgMA0KPj4NCmVuZG9iag0KDQozIDAgb2JqDQo8PA0KL1R5cGUgL1BhZ2VzDQovQ291bnQgMg0KL0tpZHMgWyA0IDAgUiA2IDAgUiBdIA0KPj4NCmVuZG9iag0KDQo0IDAgb2JqDQo8PA0KL1R5cGUgL1BhZ2UNCi9QYXJlbnQgMyAwIFINCi9SZXNvdXJjZXMgPDwNCi9Gb250IDw8DQovRjEgOSAwIFIgDQo+Pg0KL1Byb2NTZXQgOCAwIFINCj4+DQovTWVkaWFCb3ggWzAgMCA2MTIuMDAwMCA3OTIuMDAwMF0NCi9Db250ZW50cyA1IDAgUg0KPj4NCmVuZG9iag0KDQo1IDAgb2JqDQo8PCAvTGVuZ3RoIDEwNzQgPj4NCnN0cmVhbQ0KMiBKDQpCVA0KMCAwIDAgcmcNCi9GMSAwMDI3IFRmDQo1Ny4zNzUwIDcyMi4yODAwIFRkDQooIEEgU2ltcGxlIFBERiBGaWxlICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNjg4LjYwODAgVGQNCiggVGhpcyBpcyBhIHNtYWxsIGRlbW9uc3RyYXRpb24gLnBkZiBmaWxlIC0gKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA2NjQuNzA0MCBUZA0KKCBqdXN0IGZvciB1c2UgaW4gdGhlIFZpcnR1YWwgTWVjaGFuaWNzIHR1dG9yaWFscy4gTW9yZSB0ZXh0LiBBbmQgbW9yZSApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDY1Mi43NTIwIFRkDQooIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNjI4Ljg0ODAgVGQNCiggQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA2MTYuODk2MCBUZA0KKCB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBCb3JpbmcsIHp6enp6LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA2MDQuOTQ0MCBUZA0KKCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNTkyLjk5MjAgVGQNCiggQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA1NjkuMDg4MCBUZA0KKCBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDU1Ny4xMzYwIFRkDQooIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEV2ZW4gbW9yZS4gQ29udGludWVkIG9uIHBhZ2UgMiAuLi4pIFRqDQpFVA0KZW5kc3RyZWFtDQplbmRvYmoNCg0KNiAwIG9iag0KPDwNCi9UeXBlIC9QYWdlDQovUGFyZW50IDMgMCBSDQovUmVzb3VyY2VzIDw8DQovRm9udCA8PA0KL0YxIDkgMCBSIA0KPj4NCi9Qcm9jU2V0IDggMCBSDQo+Pg0KL01lZGlhQm94IFswIDAgNjEyLjAwMDAgNzkyLjAwMDBdDQovQ29udGVudHMgNyAwIFINCj4+DQplbmRvYmoNCg0KNyAwIG9iag0KPDwgL0xlbmd0aCA2NzYgPj4NCnN0cmVhbQ0KMiBKDQpCVA0KMCAwIDAgcmcNCi9GMSAwMDI3IFRmDQo1Ny4zNzUwIDcyMi4yODAwIFRkDQooIFNpbXBsZSBQREYgRmlsZSAyICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNjg4LjYwODAgVGQNCiggLi4uY29udGludWVkIGZyb20gcGFnZSAxLiBZZXQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDY3Ni42NTYwIFRkDQooIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNjY0LjcwNDAgVGQNCiggdGV4dC4gT2gsIGhvdyBib3JpbmcgdHlwaW5nIHRoaXMgc3R1ZmYuIEJ1dCBub3QgYXMgYm9yaW5nIGFzIHdhdGNoaW5nICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNjUyLjc1MjAgVGQNCiggcGFpbnQgZHJ5LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDY0MC44MDAwIFRkDQooIEJvcmluZy4gIE1vcmUsIGEgbGl0dGxlIG1vcmUgdGV4dC4gVGhlIGVuZCwgYW5kIGp1c3QgYXMgd2VsbC4gKSBUag0KRVQNCmVuZHN0cmVhbQ0KZW5kb2JqDQoNCjggMCBvYmoNClsvUERGIC9UZXh0XQ0KZW5kb2JqDQoNCjkgMCBvYmoNCjw8DQovVHlwZSAvRm9udA0KL1N1YnR5cGUgL1R5cGUxDQovTmFtZSAvRjENCi9CYXNlRm9udCAvSGVsdmV0aWNhDQovRW5jb2RpbmcgL1dpbkFuc2lFbmNvZGluZw0KPj4NCmVuZG9iag0KDQoxMCAwIG9iag0KPDwNCi9DcmVhdG9yIChSYXZlIFwoaHR0cDovL3d3dy5uZXZyb25hLmNvbS9yYXZlXCkpDQovUHJvZHVjZXIgKE5ldnJvbmEgRGVzaWducykNCi9DcmVhdGlvbkRhdGUgKEQ6MjAwNjAzMDEwNzI4MjYpDQo+Pg0KZW5kb2JqDQoNCnhyZWYNCjAgMTENCjAwMDAwMDAwMDAgNjU1MzUgZg0KMDAwMDAwMDAxOSAwMDAwMCBuDQowMDAwMDAwMDkzIDAwMDAwIG4NCjAwMDAwMDAxNDcgMDAwMDAgbg0KMDAwMDAwMDIyMiAwMDAwMCBuDQowMDAwMDAwMzkwIDAwMDAwIG4NCjAwMDAwMDE1MjIgMDAwMDAgbg0KMDAwMDAwMTY5MCAwMDAwMCBuDQowMDAwMDAyNDIzIDAwMDAwIG4NCjAwMDAwMDI0NTYgMDAwMDAgbg0KMDAwMDAwMjU3NCAwMDAwMCBuDQoNCnRyYWlsZXINCjw8DQovU2l6ZSAxMQ0KL1Jvb3QgMSAwIFINCi9JbmZvIDEwIDAgUg0KPj4NCg0Kc3RhcnR4cmVmDQoyNzE0DQolJUVPRg0K",Base64.DEFAULT);


        FileOutputStream os;
        os = new FileOutputStream(newFileName, false);
        os.write(pdfAsBytes);
        os.flush();

        if (newFileName.exists()) {
            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            Uri apkURI = FileProvider.getUriForFile(context,context.getApplicationContext().getPackageName() + ".provider", dwldsPath);
            intent.setDataAndType(apkURI, MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf"));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            String CHANNEL_ID = "MYCHANNEL";
            final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel= new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_LOW);
                Notification notification = new Notification.Builder(context,CHANNEL_ID)
                        .setContentText("You have got something new!")
                        .setContentTitle("File downloaded")
                        .setContentIntent(pendingIntent)
                        .setChannelId(CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.sym_action_chat)
                        .build();
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(notificationChannel);
                    notificationManager.notify(notificationId, notification);
                }

            } else {
                NotificationCompat.Builder b = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(android.R.drawable.sym_action_chat)
                        //.setContentIntent(pendingIntent)
                        .setContentTitle("MY TITLE")
                        .setContentText("MY TEXT CONTENT");

                if (notificationManager != null) {
                    notificationManager.notify(notificationId, b.build());
                    Handler h = new Handler();
                    long delayInMilliseconds = 1000;
                    h.postDelayed(new Runnable() {
                        public void run() {
                            notificationManager.cancel(notificationId);
                        }
                    }, delayInMilliseconds);
                }
            }
        }
        Toast.makeText(context, "PDF FILE DOWNLOADED!", Toast.LENGTH_SHORT).show();
    }
}