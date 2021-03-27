package com.almaki.employeeabsense.model.request

data class CodeRequest (
    val code_string: String? = null,
    val come_at : String? = null,
    val leave_at : String? = null
        )