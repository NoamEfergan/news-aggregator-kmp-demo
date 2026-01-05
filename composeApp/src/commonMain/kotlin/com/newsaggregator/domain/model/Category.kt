package com.newsaggregator.domain.model

enum class Category(
    val displayName: String,
) {
    ALL("All"),
    WORLD("World"),
    TECH("Tech"),
    SPORTS("Sports"),
    ENTERTAINMENT("Entertainment"),
    ;

    companion object {
        fun fromString(value: String): Category = entries.find { it.name.equals(value, ignoreCase = true) } ?: ALL
    }
}
