package com.example.habitflow // تأكد من اسم الباكدج الخاص بك

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.habitflow.navigation.AppNavigation
import com.example.habitflow.ui.screens.SplashScreen
import com.example.habitflow.ui.theme.HabitFlowTheme // تأكد من مسار الثيم

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 1. تعريف متغيرات الحالة (State) الافتراضية
            // سنجعل الوضع الداكن واللغة العربية هما الافتراضي كما طلب العميل
            var isDarkTheme by remember { mutableStateOf(true) }
            var isArabic by remember { mutableStateOf(true) }

            // 2. تحديد الاتجاه بناءً على اللغة
            val layoutDirection = if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

            // 3. تطبيق الثيم (فاتح / داكن)
            HabitFlowTheme(darkTheme = isDarkTheme) {
                // 4. تطبيق الاتجاه (يمين-يسار / يسار-يمين) على كل التطبيق
                CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {

                    // تشغيل نظام التنقل وتمرير المتغيرات ودوال التعديل لشاشة الإعدادات
                    AppNavigation(
                        isDarkTheme = isDarkTheme,
                        isArabic = isArabic,
                        onThemeChange = { isDarkTheme = it },
                        onLanguageChange = { isArabic = it }
                    )

                }
            }
        }
    }
}