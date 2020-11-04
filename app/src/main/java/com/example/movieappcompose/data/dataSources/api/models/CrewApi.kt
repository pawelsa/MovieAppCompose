package com.example.movieappcompose.data.dataSources.api.models

data class CrewApi(
	val credit_id: String,
	val department: String,
	val gender: Int,
	val id: Int,
	val job: String,
	val name: String,
	val profile_path: String?
)