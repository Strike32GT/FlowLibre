package com.mas.flowlibre.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController

@Composable
fun CrearCuenta(
    navHostController: NavHostController
) {
    var nombre by remember {mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }


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
    ){
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
                            listOf(
                                Color(0xFFFF5F6D),
                                Color(0xFFFF8A5B)
                            )
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
                text = "Unete a FLowLibre",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Crear tu cuenta y empieza a disfrutar",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = "Nombre Completo",
                color = Color.White,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(6.dp))


            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                placeholder = {Text("tu nombre")},
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
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
                text = "Correo Electronico",
                color = Color.White,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(6.dp))


            OutlinedTextField(
                value = email,
                onValueChange = {email=it},
                placeholder = {Text("tu@email.com")},
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
                onValueChange = {password = it},
                placeholder = {Text("Minimo 6 caracteres")},
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(
                            imageVector = if (passwordVisible)
                            Icons.Default.Visibility
                            else
                            Icons.Default.VisibilityOff,
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Confirmar Password",
                color = Color.White,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(6.dp))


            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it},
                placeholder = { Text("Repite tu password")},
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = {
                        confirmVisible = !confirmVisible
                    }) {
                        Icon(
                            imageVector = if (confirmVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },


                visualTransformation = if (confirmVisible)
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
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3B82F6),
                )
            ) {
                Text(
                    text = "Crear Cuenta",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider(color = Color(0xFF2A2A2E))

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Al crear una cuenta, aceptas nuestros Términos de Servicio y Política de Privacidad",
                color = Color.DarkGray,
                fontSize = 11.sp,
                lineHeight = 14.sp
            )
        }
    }
}