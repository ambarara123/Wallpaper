package com.example.wallpaper.network.model

data class User(
    val accepted_tos: Boolean,
    val bio: String,
    val first_name: String,
    val id: String,
    val instagram_username: String,
    val last_name: String,
    val links: LinksXX,
    val location: String,
    val name: String,
    val portfolio_url: Any,
    val profile_image: ProfileImageX,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val twitter_username: String,
    val updated_at: String,
    val username: String
)