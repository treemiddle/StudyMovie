package com.jay.studymovie.utils

import java.util.concurrent.atomic.AtomicBoolean

data class SingleEvent<E>(
    val event: E,
    val isConsumed: AtomicBoolean = AtomicBoolean(false)
)