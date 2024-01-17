package com.example.mydeviceapp
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mydeviceapp.ui.theme.MydeviceappTheme
import java.net.IDN
import java.text.Normalizer.Form

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val android_id =
            Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID)
        setContent {
            MydeviceappTheme {
                // A surface container using the 'background' color
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        DisplayText("Device details")
                        DisplayText("Unique id: $android_id") // Unique
                        DisplayText(
                            "OS version: " +
                                    (System.getProperty("os.version")?.toString() ?: "")
                        ) // OS version
                        DisplayText("API Level: " +
                                Build.VERSION.SDK.toString()) // API Level
                        DisplayText("Device: " +
                                Build.DEVICE.toString()) // Device
                        DisplayText("Model: " +
                                Build.MODEL.toString()) // Model
                        DisplayText("Product: " +
                                Build.PRODUCT.toString()) // Product
                        Spacer(modifier = Modifier.size(20.dp))
                        val phoneNum: MutableState<String> =
                            remember { mutableStateOf("") }
                        TextField(value = phoneNum.value,
                            onValueChange = {
                                phoneNum.value = it
                            }
                        )
                        Button(
                            onClick = {
                                sendSMS(phoneNum.value, "Hello")
                            },
                            modifier = Modifier.size(160.dp, 60.dp)) {
                            Text(text = "Send SMS")
                        }
                        Spacer(modifier = Modifier.size(20.dp))
                        Button(onClick = {
                            val myintent = Intent(this@MainActivity,
                                MainActivity::class.java)
                            startActivity(myintent)
                        }) {
                            Text(text = getString(R.string.app_name))
                        }
                    }
                }
            }
        }
    }
    private fun sendSMS(phoneNumber: String, message:
    String) {
        val myIntent = Intent(Intent.ACTION_VIEW)
        myIntent.data = Uri.parse("smsto:")
        myIntent.type = "vnd.android-dir/mms-sms"
        myIntent.putExtra("address", phoneNumber)
        myIntent.putExtra("sms_body", message)
        startActivity(myIntent)
    }
}
@Composable
fun DisplayText(text: String) {
    Text(
        text = text
    )
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MydeviceappTheme {
        DisplayText("Android")
    }
}