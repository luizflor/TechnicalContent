package com.demospring.errors

enum class ErrorCode(val code: String, val description: String) {
	INVALID_REQUEST("100", "Invalid Request"),
	DATA_NOT_FOUND("110", "Data not found")
}