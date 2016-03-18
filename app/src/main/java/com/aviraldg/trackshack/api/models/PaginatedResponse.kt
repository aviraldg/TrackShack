package com.aviraldg.trackshack.api.models


class PaginatedResponse<T> {
    val next: String = ""
    val previous: String = ""
    val results: List<T> = arrayListOf()
}