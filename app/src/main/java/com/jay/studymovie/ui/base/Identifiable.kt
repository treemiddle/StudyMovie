package com.jay.studymovie.ui.base

interface Identifiable {
    val identifier: Any

    override operator fun equals(other: Any?): Boolean
}