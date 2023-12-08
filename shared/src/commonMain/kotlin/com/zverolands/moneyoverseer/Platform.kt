package com.zverolands.moneyoverseer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform