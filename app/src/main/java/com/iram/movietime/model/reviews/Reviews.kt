package com.iram.movietime.model.reviews

class Reviews(
    val id: String,
    val page: String,
    val total_pages: String,
    val results: Array<ReviewResult>,
    val total_results: String
)