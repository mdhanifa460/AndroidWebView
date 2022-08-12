package yanbal.com.pe.webviewyanbal

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.DownloadListener
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_webview.*
import java.util.HashMap

class WebViewActivity : AppCompatActivity() {
    private val readStoragePermission = 11
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        requestPhonePermissions()
        setupWebview()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebview() {
        val webUrl = "https://dev.ivymobileapps.com/ivycpg_product_web_devit/Webview/OrderHistory#"//intent.getStringExtra("KEY_URL")
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJpYXQiOjYzNzk0NDk5NDY1NjEzODUxMCwiR3VpZCI6ImU4ZTcyZTNmLWE5ODQtNDA5Ny1iNTg0LThmNjMwYTk0NTBmYSIsIkxvZ2luSWQiOiJ0aGlsZWVwLnZhbiIsIk9yZ2FuaXNhdGlvbklkIjoyLCJEaXZpc2lvbklkIjozLCJVc2VySWQiOjU4MSwiRGlzdHJpYnV0b3JJZCI6MCwiRGlzdHJpYnV0b3JIaWVyYXJjaHlJZCI6MCwiUmV0YWlsZXJJZCI6MCwiUGFzc3dvcmQiOiIyIn0.bR1dgnd_rjGfMQ1NEDi0Cei2MKNLA7UDXF9iDAS1PGd-Ek3xhHaa1VFfkjsxugqi"
        val base64Str = "data:application/pdf;base64,PGh0bWwgeG1sbnM6bz0idXJuOnNjaGVtYXMtbWljcm9zb2Z0LWNvbTpvZmZpY2U6b2ZmaWNlIiB4bWxuczp4PSJ1cm46c2NoZW1hcy1taWNyb3NvZnQtY29tOm9mZmljZTpleGNlbCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnL1RSL1JFQy1odG1sNDAiPjxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0iYXBwbGljYXRpb24vdm5kLm1zLWV4Y2VsOyBjaGFyc2V0PVVURi04Ij48aGVhZD48IS0tW2lmIGd0ZSBtc28gOV0+PHhtbD48eDpFeGNlbFdvcmtib29rPjx4OkV4Y2VsV29ya3NoZWV0cz48eDpFeGNlbFdvcmtzaGVldD48eDpOYW1lPldvcmtzaGVldDwveDpOYW1lPjx4OldvcmtzaGVldE9wdGlvbnM+PHg6RGlzcGxheUdyaWRsaW5lcy8+PC94OldvcmtzaGVldE9wdGlvbnM+PC94OkV4Y2VsV29ya3NoZWV0PjwveDpFeGNlbFdvcmtzaGVldHM+PC94OkV4Y2VsV29ya2Jvb2s+PC94bWw+PCFbZW5kaWZdLS0+PHN0eWxlIHR5cD0idGV4dC9jc3MiPiAuZXhwb3J0LXRleHR7IG1zby1udW1iZXItZm9ybWF0OiJAIjsgfTwvaGVhZD48Ym9keT4KICAgICAgICAgICAgICAgICAgICA8dGFibGUgY2xhc3M9InRhYmxlLWJvcmRlcmVkIj4KICAgICAgICAgICAgICAgICAgICAgICAgPHRoZWFkIGlkPSJTZWxsZXJMaXN0aGVhZGVyIj48dHI+PHRoIGNsYXNzPSJ0aGNscyI+RGF0ZTwvdGg+PHRoIGNsYXNzPSJ0aGNscyI+U2VsbGVyPC90aD48dGggY2xhc3M9InRoY2xzIj5PcmRlciBDb3VudDwvdGg+PHRoIGNsYXNzPSJ0aGNscyI+T3JkZXIgVmFsdWVzPC90aD48L3RyPjwvdGhlYWQ+CiAgICAgICAgICAgICAgICAgICAgICAgIDx0Ym9keSBpZD0iU2VsbGVyTGlzdCI+PHRyIGNsYXNzPSJvZGQiPjx0ZCBjbGFzcz0idGRjbHMiPjxhIGNsYXNzPSJjb2wtZWZmZWN0IiBocmVmPSIjIiBvbmNsaWNrPSJMb2FkT3JkZXJTdW1tZXJ5RGV0YWlsKDEpIj4xNS8wNi8yMDIyPC9hPjwvdGQ+PHRkIGNsYXNzPSJ0ZGNscyI+dGhpbGVlcC52YW4gIDwvdGQ+PHRkIGNsYXNzPSJ0ZGNscyI+ODwvdGQ+PHRkIGNsYXNzPSJ0ZGNscyI+MTAzNDAuMDA8L3RkPjwvdHI+PHRyPjx0ZCBjb2xzcGFuPSIyIiBjbGFzcz0idGRjbHMgZm9vdGVyIj5HcmFuZCBUb3RhbDwvdGQ+PHRkIGNsYXNzPSJ0ZGNscyBmb290ZXIiPjg8L3RkPjx0ZCBjbGFzcz0idGRjbHMgZm9vdGVyIj4xMDM0MDwvdGQ+PC90cj48L3Rib2R5PgogICAgICAgICAgICAgICAgICAgIDwvdGFibGU+CiAgICAgICAgICAgICAgICA8L2JvZHk+PC9odG1sPg=="
        val reqHeader = HashMap<String, String>()
        reqHeader["SECURITY_TOKEN_KEY"] = token
        progressBar.visibility = View.VISIBLE
        browser.loadUrl(webUrl,reqHeader)
        browser.settings.javaScriptEnabled = true
        browser.setDownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
            JavaScriptInterface(this).convertBase64StringToPdfAndStoreIt(base64Str)
        }
        browser.settings.setSupportZoom(true)
        browser.settings.setAppCachePath(applicationContext.cacheDir.absolutePath)
        browser.settings.cacheMode = WebSettings.LOAD_DEFAULT
        browser.settings.databaseEnabled = true
        browser.settings.domStorageEnabled = true
        browser.settings.useWideViewPort = true
        browser.settings.loadWithOverviewMode = true
        browser.addJavascriptInterface(JavaScriptInterface(applicationContext), "Android")
        browser.settings.pluginState = WebSettings.PluginState.ON

        browser.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

                return if (url.startsWith("tel:") || url.startsWith("mailto:")) {
                    view.context.startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    )
                    true
                } else {
                    view.loadUrl(url)
                    true
                }
            }

            override fun onLoadResource(view: WebView, url: String) {
            }

            override fun onPageFinished(view: WebView, url: String) {
                Log.e("URL onPageFinished", url)
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun requestPhonePermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                readStoragePermission)
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                readStoragePermission)
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY),
                readStoragePermission)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && browser.canGoBack()) {
            browser.goBack()
            return true
        } else {
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }
}
