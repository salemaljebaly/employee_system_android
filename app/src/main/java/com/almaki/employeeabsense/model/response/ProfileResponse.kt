package com.almaki.employeeabsense.model.response

data class ProfileResponse(
	val code: Int? = null,
	val employee: Employee? = null
)

data class Employee(
		val date: String? = null,
		val emp_location: String? = null,
		val createdAt: String? = null,
		val emp_birth: String? = null,
		val emp_no: String? = null,
		val emp_phone: String? = null,
		val __v: Int? = null,
		val emp_full_name: String? = null,
		val _id: String? = null,
		val emp_occupation: String? = null,
		val emp_email: String? = null,
		val updatedAt: String? = null
)

