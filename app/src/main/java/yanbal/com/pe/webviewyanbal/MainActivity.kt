package yanbal.com.pe.webviewyanbal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Browser
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }

    private fun setupViews() {
        btnTabIntent.setOnClickListener {
            val url = etWebUrl.text.toString()
            if (validateUrl(url)) {
                val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJpYXQiOjYzNzk0NDk5NDY1NjEzODUxMCwiR3VpZCI6ImU4ZTcyZTNmLWE5ODQtNDA5Ny1iNTg0LThmNjMwYTk0NTBmYSIsIkxvZ2luSWQiOiJ0aGlsZWVwLnZhbiIsIk9yZ2FuaXNhdGlvbklkIjoyLCJEaXZpc2lvbklkIjozLCJVc2VySWQiOjU4MSwiRGlzdHJpYnV0b3JJZCI6MCwiRGlzdHJpYnV0b3JIaWVyYXJjaHlJZCI6MCwiUmV0YWlsZXJJZCI6MCwiUGFzc3dvcmQiOiIyIn0.bR1dgnd_rjGfMQ1NEDi0Cei2MKNLA7UDXF9iDAS1PGd-Ek3xhHaa1VFfkjsxugqi"
                val bundle = Bundle()
                bundle.putString("SECURITY_TOKEN_KEY", token)

                val builder = CustomTabsIntent.Builder()
                builder.setToolbarColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.colorPrimaryDark
                    )
                )

                val customTabsIntent = builder.build()
                customTabsIntent.intent.putExtra(Browser.EXTRA_HEADERS, bundle)
                customTabsIntent.launchUrl(this, Uri.parse(url))
            }
        }
        btnWebview.setOnClickListener {
            val url = etWebUrl.text.toString()
            if (validateUrl(url)) {
                val base64Str = "data:application/vnd.ms-excel;base64,PGh0bWwgeG1sbnM6bz0idXJuOnNjaGVtYXMtbWljcm9zb2Z0LWNvbTpvZmZpY2U6b2ZmaWNlIiB4bWxuczp4PSJ1cm46c2NoZW1hcy1taWNyb3NvZnQtY29tOm9mZmljZTpleGNlbCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnL1RSL1JFQy1odG1sNDAiPjxtZXRhIGh0dHAtZXF1aXY9ImNvbnRlbnQtdHlwZSIgY29udGVudD0iYXBwbGljYXRpb24vdm5kLm1zLWV4Y2VsOyBjaGFyc2V0PVVURi04Ij48aGVhZD48IS0tW2lmIGd0ZSBtc28gOV0+PHhtbD48eDpFeGNlbFdvcmtib29rPjx4OkV4Y2VsV29ya3NoZWV0cz48eDpFeGNlbFdvcmtzaGVldD48eDpOYW1lPldvcmtzaGVldDwveDpOYW1lPjx4OldvcmtzaGVldE9wdGlvbnM+PHg6RGlzcGxheUdyaWRsaW5lcy8+PC94OldvcmtzaGVldE9wdGlvbnM+PC94OkV4Y2VsV29ya3NoZWV0PjwveDpFeGNlbFdvcmtzaGVldHM+PC94OkV4Y2VsV29ya2Jvb2s+PC94bWw+PCFbZW5kaWZdLS0+PHN0eWxlIHR5cD0idGV4dC9jc3MiPiAuZXhwb3J0LXRleHR7IG1zby1udW1iZXItZm9ybWF0OiJAIjsgfTwvaGVhZD48Ym9keT4KICAgICAgICAgICAgICAgICAgICA8dGFibGUgY2xhc3M9InRhYmxlLWJvcmRlcmVkIj4KICAgICAgICAgICAgICAgICAgICAgICAgPHRoZWFkIGlkPSJTZWxsZXJMaXN0aGVhZGVyIj48dHI+PHRoIGNsYXNzPSJ0aGNscyI+RGF0ZTwvdGg+PHRoIGNsYXNzPSJ0aGNscyI+U2VsbGVyPC90aD48dGggY2xhc3M9InRoY2xzIj5PcmRlciBDb3VudDwvdGg+PHRoIGNsYXNzPSJ0aGNscyI+T3JkZXIgVmFsdWVzPC90aD48L3RyPjwvdGhlYWQ+CiAgICAgICAgICAgICAgICAgICAgICAgIDx0Ym9keSBpZD0iU2VsbGVyTGlzdCI+PHRyIGNsYXNzPSJvZGQiPjx0ZCBjbGFzcz0idGRjbHMiPjxhIGNsYXNzPSJjb2wtZWZmZWN0IiBocmVmPSIjIiBvbmNsaWNrPSJMb2FkT3JkZXJTdW1tZXJ5RGV0YWlsKDEpIj4xNS8wNi8yMDIyPC9hPjwvdGQ+PHRkIGNsYXNzPSJ0ZGNscyI+dGhpbGVlcC52YW4gIDwvdGQ+PHRkIGNsYXNzPSJ0ZGNscyI+ODwvdGQ+PHRkIGNsYXNzPSJ0ZGNscyI+MTAzNDAuMDA8L3RkPjwvdHI+PHRyPjx0ZCBjb2xzcGFuPSIyIiBjbGFzcz0idGRjbHMgZm9vdGVyIj5HcmFuZCBUb3RhbDwvdGQ+PHRkIGNsYXNzPSJ0ZGNscyBmb290ZXIiPjg8L3RkPjx0ZCBjbGFzcz0idGRjbHMgZm9vdGVyIj4xMDM0MDwvdGQ+PC90cj48L3Rib2R5PgogICAgICAgICAgICAgICAgICAgIDwvdGFibGU+CiAgICAgICAgICAgICAgICA8L2JvZHk+PC9odG1sPg=="
                JavaScriptInterface(this).convertBase64StringToPdfAndStoreIt(base64Str)
//                val intent = Intent(this, WebViewActivity::class.java).putExtra("KEY_URL", url)
//                startActivity(intent)
            }
        }
    }

    private fun validateUrl(url: String): Boolean {
        val validationResult = !url.isEmpty() && url.isValidUrl()
        if (!validationResult)
            Toast.makeText(baseContext, "Please prove a valid URL", Toast.LENGTH_SHORT).show()
        return validationResult
    }

    private fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()
}
