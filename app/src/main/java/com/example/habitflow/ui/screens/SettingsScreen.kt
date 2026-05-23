package com.example.habitflow.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    isArabic: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onLanguageChange: (Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isArabic) "الإعدادات" else "Settings",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = if (isArabic) Icons.Default.ArrowForward else Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SettingSwitchItem(
                title = if (isArabic) "الوضع الداكن" else "Dark Mode",
                subtitle = if (isArabic) "تفعيل الألوان الداكنة المريحة للعين" else "Enable eye-friendly dark colors",
                isChecked = isDarkTheme,
                onCheckedChange = onThemeChange
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

            SettingSwitchItem(
                title = if (isArabic) "اللغة العربية" else "Arabic Language",
                subtitle = if (isArabic) "تبديل اتجاه ونصوص التطبيق" else "Toggle app layout and texts",
                isChecked = isArabic,
                onCheckedChange = onLanguageChange
            )
        }
    }
}

@Composable
fun SettingSwitchItem(
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
            Text(text = subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
    }
}