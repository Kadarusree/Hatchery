package shiva.com.hatchery;

import android.app.Activity;
import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import shiva.com.hatchery.R;

public class EOD_CHK_History extends AppCompatActivity {


    WebView mWebView;
    FirebaseFirestore db;

    String htmlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_oxygen_temperature_results);
        mWebView = findViewById(R.id.mWebView);
        mWebView.addJavascriptInterface(new WebAppInterface(this), "AndroidInterface"); // To call methods in Android from using js in the html, AndroidInterface.showToast, AndroidInterface.getAndroidVersion etc
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());

        getSupportActionBar().setTitle("End of the day Checklist : Tank "+ Constants.TANK_NUMBER);


        htmlString = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "table, th, td {\n" +
                "    border: 1px solid black;\n" +
                "    border-collapse: collapse;\n" +
                "}\n" +
                "th, td {\n" +
                "    padding: 5px;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h2>End of the Day Checklist</h2>\n" +
                "\n" +
                "<table style=\"width:100%\">\n" +
                "  <tr>\n" +
                "    <th> Tank Number</th>\n" +
                "    <th> Date </th>\n" +
                "    <th> Initials </th>\n" +
                "    <th>" + getResources().getString(R.string.eod_chkl_opt1) + "</th>\n" +
                "    <th>" + getResources().getString(R.string.eod_chkl_opt2) + "</th> \n" +
                "    <th>" + getResources().getString(R.string.eod_chkl_opt3) + "</th>\n" +
                "    <th>" + getResources().getString(R.string.eod_chkl_opt4) + "</th> \n" +
                "    <th>" + getResources().getString(R.string.eod_chkl_opt5) + "</th>\n" +
                "    <th>" + getResources().getString(R.string.eod_chkl_opt6) + "</th> \n" +
                "    <th>" + getResources().getString(R.string.eod_chkl_opt7) + "</th>\n" +
                "    <th>" + getResources().getString(R.string.eod_chkl_opt8) + "</th> \n" +
                "    <th>" + getResources().getString(R.string.eod_chkl_opt9) + "</th> \n" +
                "    <th>" + getResources().getString(R.string.eod_chkl_opt10) + "</th>\n" +
                "    <th>" + getResources().getString(R.string.eod_chkl_opt11) + "</th> \n" +
                "    <th>" + getResources().getString(R.string.eod_chkl_opt12) + "</th>\n" +
                "    <th>" + getResources().getString(R.string.eod_chkl_opt13) + "</th> \n" +
                "    <th> Comments </th>\n" +

                "  </tr>\n";

        db = FirebaseFirestore.getInstance();
        db.collection("EOD_CHECKLIST").whereEqualTo("Tank_ID", Constants.TANK_NUMBER).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> mDocuments = task.getResult().getDocuments();
                String Date = mDocuments.get(0).get("Date").toString();
                System.out.println(Date);

                for (int i = 0; i < mDocuments.size(); i++) {

                    DocumentSnapshot mDocument = mDocuments.get(i);
                    htmlString = htmlString +
                            "  <tr>\n" +
                            "    <td>" + mDocument.get("Tank_ID") + "</td>\n" +
                            "    <td>" + mDocument.get("Date") + "</td>\n" +
                            "    <td>" + mDocument.get("Initials") + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.eod_chkl_opt1)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.eod_chkl_opt2)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.eod_chkl_opt3)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.eod_chkl_opt4)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.eod_chkl_opt5)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.eod_chkl_opt6)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.eod_chkl_opt7)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.eod_chkl_opt8)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.eod_chkl_opt9)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.eod_chkl_opt10)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.eod_chkl_opt11)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.eod_chkl_opt12)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.eod_chkl_opt13)) + "</td>\n" +
                            "    <td>" + mDocument.get("Comment") + "</td>\n" +
                            "  </tr>\n";
                }

                htmlString = htmlString + "</table>\n" +
                        "</body>\n" +
                        "</html>\n";

                System.out.println(htmlString);
                mWebView.loadData(htmlString, "text/html", "UTF-8");

               /* mWebView.setWebViewClient(new WebViewClient() {

                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return false;
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        createWebPrintJob(view);
                        mWebView = null;
                    }
                });*/

            }
        });


    }

    private void createWebPrintJob(WebView webView) {
        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);

        String jobName = getString(R.string.app_name) + " Document";

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());


       /* PrintJob mPrintJobs = new PrintJob();
        // Save the job object for later status checking
        mPrintJobs.add(printJob);*/
    }
    //
    public void Print(View view) {
        createWebPrintJob(mWebView);
    }

    public class WebAppInterface {
        Context mContext;

        // Instantiate the interface and set the context
        WebAppInterface(Context c) {
            mContext = c;
        }

        // Show a toast from the web page
        @JavascriptInterface
        public void print() {
            createWebPrintJob(mWebView);
        }




    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished (WebView view, String url) {
            //  view.loadUrl("javascript:alert(showVersion('called by Android'))");
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d("LogTag", message);
            result.confirm();
            createWebPrintJob(mWebView);
            return true;
        }
    }
}


