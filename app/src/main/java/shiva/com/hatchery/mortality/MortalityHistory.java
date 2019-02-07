package shiva.com.hatchery.mortality;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import shiva.com.hatchery.Constants;
import shiva.com.hatchery.R;
import shiva.com.hatchery.oxygentemp.OxygenTemperatureResults;

public class MortalityHistory extends AppCompatActivity {

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

        getSupportActionBar().setTitle("Mortality Collection : Tank "+ Constants.TANK_NUMBER);

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
                "<h2>Mortality Collection</h2>\n" +
                "\n" +
                "<table style=\"width:100%\">\n" +
                "  <tr>\n" +
                "    <th> TANK ID</th>\n" +
                "    <th> DATE </th>\n" +
                "    <th> INITIALS </th>\n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_1) + "</th>\n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_2) + "</th> \n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_3) + "</th>\n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_4) + "</th> \n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_5) + "</th>\n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_6) + "</th> \n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_7) + "</th>\n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_8) + "</th> \n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_9) + "</th>\n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_10) + "</th> \n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_11) + "</th>\n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_12) + "</th>\n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_13) + "</th> \n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_14) + "</th>\n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_15) + "</th> \n" +
                "    <th>" + getResources().getString(R.string.mc_type_code_16) + "</th> \n" +
                "    <th>" + "OTHERS" + "</th> \n" +
                "    <th>" + "TOTAL" + "</th> \n" +
                "    <th>" + "NOTES" + "</th>\n" +

                "  </tr>\n";

        db = FirebaseFirestore.getInstance();
        db.collection("MORTALITY_COLLECTION").whereEqualTo("Tank_ID", Constants.TANK_NUMBER).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> mDocuments = task.getResult().getDocuments();

                for (int i = 0; i < mDocuments.size(); i++) {

                    DocumentSnapshot mDocument = mDocuments.get(i);
                    htmlString = htmlString +
                            "  <tr>\n" +
                            "    <td>" + mDocument.get("Tank_ID") + "</td>\n" +
                            "    <td>" + mDocument.get("Date") + "</td>\n" +
                            "    <td>" + mDocument.get("Initials") + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type1)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type2)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type3)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type4)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type5)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type6)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type7)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type8)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type9)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type10)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type11)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type12)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type13)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type14)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type15)) + "</td>\n" +
                            "    <td>" + mDocument.get(getResources().getString(R.string.mc_type16)) + "</td>\n" +
                            "    <td>" + mDocument.get("OTHERS_NAME")+" "+mDocument.get("OTHERS_COUNT") + "</td>\n" +
                            "    <td>" + mDocument.get("Total") + "</td>\n" +
                            "    <td>" + mDocument.get("Notes") + "</td>\n" +

                            "  </tr>\n";
                }

                htmlString = htmlString + "</table>\n" +
                        "<p></p>"+
                        "<table style=\"width:100%\">\n" +
                        "\n" +
                        "  <tr>\n" +
                        "    <td>PP-POOR PERFORMER</td>\n" +
                        "    <td>FU-FUNGUS</td>\n" +
                        "    <td>NC-NON CODEABLE</td>\n" +
                        "    <td>FR-FRESHMORT</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>VC-VACCINATION</td>\n" +
                        "    <td>HE-HUMAN ERROR</td>\n" +
                        "    <td>J-JUMPERS</td>\n" +
                        "    <td>DF-DEFORMITY</td>\n" +
                        "  </tr>\n" +
                        "  \n" +
                        "  <tr>\n" +
                        "    <td>LS-LEATHEL SAMPLING</td>\n" +
                        "    <td>EP-EYE PECK</td>\n" +
                        "    <td>PR-PREDATOR</td>\n" +
                        "    <td>DF-DEFORMITY</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>DI-DISEASE</td>\n" +
                        "    <td>TR-TRAN</td>\n" +
                        "    <td>H-HANDLING</td>\n" +
                        "    <td>CU-CULLED</td>\n" +
                        "  </tr>\n" +
                        " \n" +
                        "</table>"+
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
