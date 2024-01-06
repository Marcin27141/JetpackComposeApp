package com.example.jetpackcomposeapp.list1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeapp.ui.theme.JetpackComposeAppTheme

class List1PhoneActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAppTheme {

            }
        }
    }
}

@Composable
fun ShowPhoneView() {
    val context = LocalContext.current

    val radioItems = listOf(
        "Zaraz oddzwonie",
        "Nie moge rozmawiac",
        "Jade do domu",
        "Wiadomosc ponizej"
    )
    var phone by remember {
        mutableStateOf("")
    }
    var radioOption by remember {
        mutableStateOf(-1)
    }
    var customSMS by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phone")
                }
                if (dialIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(dialIntent)
                }
            }) {
                Text("Dial", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = { text ->
                    phone = text
                },
                label = { Text("Phone number") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.weight(1f)
            )
        }
        Button(
            onClick = {
                val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                    val body = when (radioOption) {
                        0, 1, 2 -> radioItems[radioOption]
                        else -> customSMS
                    }
                    data = Uri.parse("smsto:$phone")
                    putExtra("sms_body", body)
                }
                if (smsIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(smsIntent)
                }
            },
        ) {
            Text("SMS", fontSize = 20.sp)
        }
        Column(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            for (i in 0..3) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = radioOption == i,
                        onClick = { radioOption = i },
                        enabled = true
                    )
                    Text(radioItems[i], fontSize = 24.sp)
                }
            }
        }
        OutlinedTextField(
            value = customSMS,
            onValueChange = { text ->
                customSMS = text
            },
            label = { Text("Your custom message") },
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        )
    }
}
