package com.jay.studymovie.utils

import android.content.Context
import java.lang.ref.WeakReference

class ResourceProviderImpl(
    applicationContext: Context
) : ResourceProvider {
    private val applicationContextRef: WeakReference<Context> = WeakReference(applicationContext)

    override fun getString(resId: Int): String {
        return applicationContextRef.get()!!.getString(resId)
    }
}