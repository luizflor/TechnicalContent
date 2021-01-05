package com.demospring.errors

data class ErrorMessage(val errorCode: String = "", val errorDescription: String = "") {
	constructor() : this("", "")
}