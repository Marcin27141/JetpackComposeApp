package com.example.jetpackcomposeapp.list1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeapp.services.PreferencesManager

private class FormField(
    val title: String,
    val property: MutableState<String>,
    val keyboardType: KeyboardType? = null
)

@Composable
fun ShowFormsView(nameIn: String, nickIn: String, onBackPressed: () -> Unit) {
    val context = LocalContext.current
    val name = remember {
        mutableStateOf(nameIn)
    }
    val email = remember {
        mutableStateOf("")
    }
    val phone = remember {
        mutableStateOf("")
    }
    val nick = remember {
        mutableStateOf(nickIn)
    }

    val formFields = arrayOf(
        FormField("Name", name),
        FormField("Email", email, KeyboardType.Email),
        FormField("Phone", phone, KeyboardType.Number),
        FormField("Nick", nick)
    )

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        ShowFormFields(formFields)
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = {
            PreferencesManager.getInstance().setNameAndNick(name.value, nick.value, context)
            onBackPressed()
        }) {
            Text("Back", fontSize = 24.sp)
        }
    }
}

@Composable
private fun ShowFormFields(formFields: Array<FormField>) {
    formFields.forEach { formField ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("${formField.title}:", fontSize = 30.sp)
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = formField.property.value,
                onValueChange = { formField.property.value = it },
                label = { Text(formField.title) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = formField.keyboardType ?: KeyboardType.Text
                ),
                modifier = Modifier.weight(1f))
        }
    }
}
