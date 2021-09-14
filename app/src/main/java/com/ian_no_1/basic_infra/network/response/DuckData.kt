package com.ian_no_1.basic_infra.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DuckData(
    @Json(name = "message") val message: String,
    @Json(name = "url") val imgUrl: String
)
