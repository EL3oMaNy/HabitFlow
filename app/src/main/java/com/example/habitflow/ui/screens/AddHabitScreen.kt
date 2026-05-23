package com.example.habitflow.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitScreen(
    isArabic: Boolean,
    onHabitCreated: (HabitModel) -> Unit,
    onBackClick: () -> Unit
) {
    var habitName by remember { mutableStateOf("") }
    var reminderTime by remember { mutableStateOf("") }
    var showTimePicker by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState()

    val categories = if (isArabic) listOf("صحة", "تعليم", "عمل", "رياضة") else listOf("Health", "Learn", "Work", "Fitness")
    var selectedCategory by remember { mutableStateOf(categories.first()) }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val hour = timePickerState.hour
                        val minute = timePickerState.minute
                        val formattedMinute = if (minute < 10) "0$minute" else minute.toString()
                        val formattedHour = if (hour < 10) "0$hour" else hour.toString()
                        reminderTime = "$formattedHour:$formattedMinute"
                        showTimePicker = false
                    }
                ) {
                    Text(if (isArabic) "تأكيد" else "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text(if (isArabic) "إلغاء" else "Cancel")
                }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isArabic) "إضافة عادة جديدة" else "Add New Habit",
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = if (isArabic) "قم بإعداد عادتك الجديدة لتتبعها ضمن مسارك اليومي." else "Set up your new habit to track it in your daily flow.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = habitName,
                onValueChange = { habitName = it },
                label = { Text(if (isArabic) "اسم العادة" else "Habit Name") },
                placeholder = { Text(if (isArabic) "مثال: قراءة كتاب، المشي..." else "e.g., Read a book, Walking...") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                singleLine = true
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = reminderTime,
                    onValueChange = { },
                    label = { Text(if (isArabic) "وقت التذكير" else "Reminder Time") },
                    placeholder = { Text("--:--") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    shape = MaterialTheme.shapes.medium,
                    trailingIcon = {
                        IconButton(onClick = { showTimePicker = true }) {
                            Icon(
                                imageVector = Icons.Outlined.Schedule,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { showTimePicker = true }
                        )
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = if (isArabic) "التصنيف (اختياري)" else "Category (Optional)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category },
                            label = { Text(category) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val (catAr, catEn) = when (selectedCategory) {
                        "صحة", "Health" -> Pair("صحة", "Health")
                        "تعليم", "Learn" -> Pair("تعليم", "Learn")
                        "عمل", "Work" -> Pair("عمل", "Work")
                        "رياضة", "Fitness" -> Pair("رياضة", "Fitness")
                        else -> Pair(selectedCategory, selectedCategory)
                    }

                    val subAr = if (reminderTime.isNotEmpty()) reminderTime else "0 دقيقة"
                    val subEn = if (reminderTime.isNotEmpty()) reminderTime else "0 mins"

                    val newHabit = HabitModel(
                        titleAr = if (habitName.isNotEmpty()) habitName else "عادة جديدة",
                        titleEn = if (habitName.isNotEmpty()) habitName else "New Habit",
                        subtitleAr = subAr,
                        subtitleEn = subEn,
                        categoryAr = catAr,
                        categoryEn = catEn,
                        icon = Icons.Outlined.CheckCircle,
                        progress = 0f,
                        isCompleted = false
                    )

                    onHabitCreated(newHabit)
                    onBackClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = if (isArabic) "حفظ العادة" else "Save Habit",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}