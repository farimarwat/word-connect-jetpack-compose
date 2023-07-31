package com.fungiggle.lexilink.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

const val GEMSHOP_FILENAME = "gem_shop"
const val INITIAL_GEMS = 100
const val GEMS_TO_CONSUME = 1
object GemShopManager {
    private const val KEY_GEMS_TOTAL = "gems_total"
    private const val KEY_FIRST_INSTALLATION = "first_installation"
    private lateinit var sharedPreferences: SharedPreferences

    fun initializeSharedPrefs(context: Context) {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        sharedPreferences = EncryptedSharedPreferences.create(
            GEMSHOP_FILENAME,
            mainKeyAlias,
            context.applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun isFirstInstallation(): Boolean {
        return sharedPreferences.getBoolean(KEY_FIRST_INSTALLATION, true)
    }

    fun setFirstInstallationStatus(isFirstInstallation: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_FIRST_INSTALLATION, isFirstInstallation).apply()
    }

    fun initializeGemsTotal() {
        sharedPreferences.edit().putInt(KEY_GEMS_TOTAL, INITIAL_GEMS).apply()
    }

    fun getGemsTotal(): Int {
        return sharedPreferences.getInt(KEY_GEMS_TOTAL, 0)
    }
    fun addGems(gems:Int){
        val currentTotal = getGemsTotal()
        sharedPreferences.edit().putInt(KEY_GEMS_TOTAL, currentTotal + gems).apply()
    }

    fun consumeGems(quantity: Int): Boolean {
        val currentTotal = getGemsTotal()
        if (currentTotal >= quantity) {
            sharedPreferences.edit().putInt(KEY_GEMS_TOTAL, currentTotal - quantity).apply()
            return true
        }
        return false
    }
}
