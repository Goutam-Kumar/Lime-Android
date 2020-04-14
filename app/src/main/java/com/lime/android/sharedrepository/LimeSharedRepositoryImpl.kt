package com.lime.android.sharedrepository

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson
import com.lime.android.models.login.Userinfo
import com.lime.android.util.GLOBAL_TAG

private const val FILE_NAME = "lime_prefs"
private const val SIZE = "size"
private const val LOGGED_IN_USER = "logged_in_user"

internal class LimeSharedRepositoryImpl(context: Context): LimeSharedRepository {

    private val sharedPreferences by lazy {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    private fun jsonSerialize(intput: Any): String {
        return Gson().toJson(intput)
    }

    private fun jsonDeserealize(input: String, clazz: Class<out Any>): Class<out Any>{
        return Gson().fromJson(input,clazz::class.java)
    }

    override var loggedInUser: Userinfo
        get() = jsonDeserealize(sharedPreferences.getString(LOGGED_IN_USER, "").orEmpty(),Userinfo::class.java) as Userinfo
        set(value) {
            Log.d(GLOBAL_TAG,jsonSerialize(value))
            sharedPreferences.edit { putString(LOGGED_IN_USER, jsonSerialize(value)) }
        }

    override fun storeData(data: String): String {
        val currentSize = sharedPreferences.getInt(SIZE, 0)
        val key = "OBJ$currentSize"
        sharedPreferences.edit {
            putInt(SIZE, currentSize + 1)
            putString(key, data)
        }

        return key
    }

    override fun fetchData(key: String): String =
        sharedPreferences.getString(key, "").orEmpty()


    override fun cleanup() {
        sharedPreferences.edit { clear() }
    }
}

