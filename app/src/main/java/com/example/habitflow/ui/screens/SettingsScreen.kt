package com.example.habitflow.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
                            imageVector = if (isArabic) Icons.AutoMirrored.Filled.ArrowForward else Icons.AutoMirrored.Filled.ArrowBack,
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
        ) {
            val themeTitle = if (isArabic) {
                if (isDarkTheme) "الوضع الداكن" else "الوضع الفاتح"
            } else {
                if (isDarkTheme) "Dark Mode" else "Light Mode"
            }

            val themeSubtitle = if (isArabic) {
                if (isDarkTheme) "التبديل إلى الوضع الفاتح" else "تفعيل الألوان الداكنة المريحة للعين"
            } else {
                if (isDarkTheme) "Switch to light mode" else "Enable eye-friendly dark colors"
            }

            val themeIcon = if (isDarkTheme) Icons.Outlined.LightMode else Icons.Outlined.DarkMode

            SettingSwitchItem(
                icon = themeIcon,
                title = themeTitle,
                subtitle = themeSubtitle,
                isChecked = isDarkTheme,
                onCheckedChange = onThemeChange
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            val langTitle = if (isArabic) "اللغة العربية" else "English Language"
            val langSubtitle = if (isArabic) "التبديل إلى اللغة الإنجليزية" else "Switch to Arabic language"
            val langIcon = if (isArabic) Icons.Outlined.Language else Icons.Outlined.Translate

            SettingSwitchItem(
                icon = langIcon,
                title = langTitle,
                subtitle = langSubtitle,
                isChecked = isArabic,
                onCheckedChange = onLanguageChange
            )
        }
    }
}

@Composable
fun SettingSwitchItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) }
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

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