package com.almaki.employeeabsense.model.response

data class EmployeeDailyRecordResponse(
	val createdAt: String,
	val leave_at: String,
	val code_string: String,
	val __v: Int,
	val _id: String,
	val employee: String,
	val come_at: String,
	val updatedAt: String
)