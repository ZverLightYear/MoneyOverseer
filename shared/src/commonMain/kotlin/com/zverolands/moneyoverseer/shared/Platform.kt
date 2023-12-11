package com.zverolands.moneyoverseer.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform