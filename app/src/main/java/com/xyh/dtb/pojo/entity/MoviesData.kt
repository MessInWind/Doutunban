package com.xyh.dtb.pojo.entity


data class MoviesData(
    var id: Int = -1,
    var name: String? = "",
    var year: Int? = -1,
    var rating: Double? = -1.0,
    var ratingsum: Long? = -1,
    var img: String? = "",
    var summary: String? = "",
    var tags: String? = "",
    var genre: String? = "",
    var country: String? = "",
    var director: String? = "",
    var actor: String? = "",
)