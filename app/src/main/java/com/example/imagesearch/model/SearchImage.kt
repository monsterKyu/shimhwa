package com.example.imagesearch.model

data class SearchImage(
    var title: String?,
    var dateTime: String,
    var url: String,
    var isLike: Boolean = false
)
