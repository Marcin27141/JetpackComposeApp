package com.example.jetpackcomposeapp.list1

import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ShowPhoneView() {
    val smsOptions = listOf(
        "Zaraz oddzwonie",
        "Nie moge rozmawiac",
        "Jade do domu",
        "Wiadomosc ponizej"
    )
    val phone = remember {
        mutableStateOf("")
    }
    val radioOption = remember {
        mutableIntStateOf(-1)
    }
    val customSMS = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShowDialRow(phone)
        SendSmsButton({ phone.value }, {
            when (radioOption.intValue) {
                    0, 1, 2 -> smsOptions[radioOption.intValue]
                    else -> customSMS.value
                }
        })
        SmsRadioOptions(smsOptions, radioOption)
        CustomSmsTextField(customSMS)
    }
}

@Composable
private fun ShowDialRow(phone: MutableState<String>) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DialButton(phone)
        Spacer(modifier = Modifier.width(16.dp))
        PhoneNumberInput(phone, Modifier.weight(1F))
    }
}

@Composable
private fun PhoneNumberInput(phone: MutableState<String>, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = phone.value,
        onValueChange = { phone.value = it },
        label = { Text("Phone number") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        modifier = modifier
    )
}

@Composable
private fun DialButton(phone: MutableState<String>) {
    val context = LocalContext.current

    Button(onClick = {
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${phone.value}")
        }
        context.startActivity(dialIntent)
    }) {
        Text("Dial", fontSize = 20.sp)
    }
}

@Composable
private fun SendSmsButton(getPhoneNumber: () -> String, getSmsContent: () -> String) {
    val context = LocalContext.current
    Button(
        onClick = {
            val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:${getPhoneNumber()}")
                putExtra("sms_body", getSmsContent())
            }
            context.startActivity(smsIntent)
        },
    ) {
        Text("SMS", fontSize = 20.sp)
    }
}

@Composable
private fun SmsRadioOptions(smsOptions: List<String>, currentChoice: MutableIntState) {
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        smsOptions.forEachIndexed { idx, radioItem ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentChoice.intValue == idx,
                    onClick = { currentChoice.intValue = idx },
                    enabled = true
                )
                Text(radioItem, fontSize = 24.sp)
            }
        }
    }
}

@Composable
private fun CustomSmsTextField(customSMS: MutableState<String>) {
    OutlinedTextField(
        value = customSMS.value,
        onValueChange = { text -> customSMS.value = text },
        label = { Text("Your custom message") },
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    )
}
