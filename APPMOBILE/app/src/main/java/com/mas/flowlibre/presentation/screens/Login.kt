package com.mas.flowlibre.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Login() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}
    var passwordVisible by remember { mutableStateOf(false)}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B0B0E),
                        Color(0xFF14141A),
                        Color(0xFF0B0B0E)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .clip(RoundedCornerShape(22.dp))
                .background(Color(0xFF1A1A1E))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(Color(0xFFFF5F6D), Color(0xFFFF8A5B))
                        )
                    ),
                contentAlignment = Alignment.Center
            ){
                Text("♪", color = Color.White, fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "FlowLibre",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "Bienvenido de vuelta",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Inicia sesión para continuar",
                color = Color.White,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = "Correo Electronico",
                color = Color.White,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(6.dp))


            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                placeholder = {Text("tuemail@gmail.com")},
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2A2A2E),
                    unfocusedBorderColor = Color(0xFF2A2A2E),
                    focusedContainerColor = Color(0xFF121214),
                    unfocusedContainerColor = Color(0xFF121214),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFFFF5F6D)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "Password",
                color = Color.White,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(6.dp))


            OutlinedTextField(
                value = password,
                onValueChange = { password = it},
                placeholder = {Text("******")},
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = {passwordVisible != passwordVisible}) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE63946),
                    unfocusedBorderColor = Color(0xFF2A2A2E),
                    focusedContainerColor = Color(0xFF121214),
                    unfocusedContainerColor = Color(0xFF121214),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFFE63946)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {/**/},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE63946)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Iniciar Sesion",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            Divider(color = Color(0xFF2A2A2E))


            Spacer(modifier = Modifier.height(12.dp))


            Text(
                text = "No tienes cuenta? Crear Cuenta",
                color = Color(0xFF4EA8FF),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Al continuar, aceptas nuestros Términos de Servicio y Política de Privacidad",
                color = Color.DarkGray,
                fontSize = 11.sp,
                lineHeight = 14.sp
            )
        }
    }
}