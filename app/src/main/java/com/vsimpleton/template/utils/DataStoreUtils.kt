@file:Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")

package com.vsimpleton.template.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.lang.IllegalArgumentException

/**
 * NAME: vSimpleton
 * DATE: 2021/4/13
 * DESC: DataStore工具类 提供同步or异步分别获取与存储数据
 */

object DataStoreUtils {

    private lateinit var dataStore: DataStore<Preferences>
    private const val PREFERENCE_NAME = "keyPaperDataStore"

    fun init(context: Context) {
        dataStore = context.createDataStore(PREFERENCE_NAME)
    }

    /**
     * 通过first同步获取数据
     *
     * @param key 要获取的key值
     * @param default 默认值
     */
    fun <U> getSyncData(key: String, default: U): U {
        val res = when (default) {
            is Long -> readLongData(key, default)
            is String -> readStringData(key, default)
            is Int -> readIntData(key, default)
            is Boolean -> readBooleanData(key, default)
            is Float -> readFloatData(key, default)
            is Double -> readDoubleData(key, default)
            else -> throw IllegalArgumentException("This type can be saved into DataStore")
        }
        return res as U
    }

    /**
     * 通过FLow实现异步获取数据
     *
     * @param key 要获取的key值
     * @param default 默认值
     */
    fun <U> getData(key: String, default: U): Flow<U> {
        val data = when (default) {
            is Long -> readLongFlow(key, default)
            is String -> readStringFlow(key, default)
            is Int -> readIntFlow(key, default)
            is Boolean -> readBooleanFlow(key, default)
            is Float -> readFloatFlow(key, default)
            is Double -> readDoubleFlow(key, default)
            else -> throw IllegalArgumentException("This type can be saved into DataStore")
        }
        return data as Flow<U>
    }

    /**
     * 异步存储数据
     *
     * @param key 需要存储的key
     * @param value 需要存储的value
     */
    fun <U> putData(key: String, value: U) {
        when (value) {
            is Long -> saveLongData(key, value)
            is String -> saveStringData(key, value)
            is Int -> saveIntData(key, value)
            is Boolean -> saveBooleanData(key, value)
            is Float -> saveFloatData(key, value)
            is Double -> saveDoubleData(key, value)
            else -> throw IllegalArgumentException("This type can be saved into DataStore")
        }
    }

    /**
     * 同步存储数据
     *
     * @param key 需要存储的key
     * @param value 需要存储的value
     */
    fun <U> putSyncData(key: String, value: U) {
        when (value) {
            is Long -> saveSyncLongData(key, value)
            is String -> saveSyncStringData(key, value)
            is Int -> saveSyncIntData(key, value)
            is Boolean -> saveSyncBooleanData(key, value)
            is Float -> saveSyncFloatData(key, value)
            is Double -> saveSyncDoubleData(key, value)
            else -> throw IllegalArgumentException("This type can be saved into DataStore")
        }
    }


    // =========================以下为同步获取数据===========================
    private fun readDoubleData(key: String, default: Double = 0.0): Double {
        var value = 0.0
        runBlocking {
            dataStore.data.first {
                value = it[preferencesKey<Double>(key)] ?: default
                true
            }
        }
        return value
    }

    private fun readFloatData(key: String, default: Float = 0f): Float {
        var value = 0f
        runBlocking {
            dataStore.data.first {
                value = it[preferencesKey<Float>(key)] ?: default
                true
            }
        }
        return value
    }

    private fun readBooleanData(key: String, default: Boolean = false): Boolean {
        var value = false
        runBlocking {
            dataStore.data.first {
                value = it[preferencesKey<Boolean>(key)] ?: default
                true
            }
        }
        return value
    }

    private fun readIntData(key: String, default: Int = 0): Int {
        var value = 0
        runBlocking {
            dataStore.data.first {
                value = it[preferencesKey<Int>(key)] ?: default
                true
            }
        }
        return value
    }

    private fun readStringData(key: String, default: String): String {
        var value = ""
        runBlocking {
            dataStore.data.first {
                value = it[preferencesKey<String>(key)] ?: default
                true
            }
        }
        return value
    }

    private fun readLongData(key: String, default: Long = 0L): Long {
        var value = 0L
        runBlocking {
            dataStore.data.first {
                value = it[preferencesKey<Long>(key)] ?: default
                true
            }
        }
        return value
    }


    // =========================以下为异步获取数据===========================
    private fun readBooleanFlow(key: String, default: Boolean = false): Flow<Boolean> =
        dataStore.data.catch {
            //当读取数据遇到错误时，如果是 `IOException` 异常，发送一个 emptyPreferences 来重新使用
            //但是如果是其他的异常，最好将它抛出去，不要隐藏问题
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[preferencesKey<Boolean>(key)] ?: default
        }

    private fun readIntFlow(key: String, default: Int = 0): Flow<Int> = dataStore.data.catch {
        if (it is IOException) {
            it.printStackTrace()
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[preferencesKey<Int>(key)] ?: default
    }

    private fun readLongFlow(key: String, default: Long = 0): Flow<Long> = dataStore.data.catch {
        if (it is IOException) {
            it.printStackTrace()
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[preferencesKey<Long>(key)] ?: default
    }

    private fun readDoubleFlow(key: String, default: Double = 0.0): Flow<Double> =
        dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[preferencesKey<Double>(key)] ?: default
        }

    private fun readStringFlow(key: String, default: String = ""): Flow<String> =
        dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[preferencesKey<String>(key)] ?: default
        }

    private fun readFloatFlow(key: String, default: Float = 0f): Flow<Float> =
        dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[preferencesKey<Float>(key)] ?: default
        }


    // =========================以下为异步存储数据===========================
    private fun saveBooleanData(key: String, value: Boolean) {
        GlobalScope.launch {
            dataStore.edit { mutablePreferences ->
                mutablePreferences[preferencesKey(key)] = value
            }
        }
    }

    private fun saveIntData(key: String, value: Int) {
        GlobalScope.launch {
            dataStore.edit { mutablePreferences ->
                mutablePreferences[preferencesKey(key)] = value
            }
        }
    }

    private fun saveStringData(key: String, value: String) {
        GlobalScope.launch {
            dataStore.edit { mutablePreferences ->
                mutablePreferences[preferencesKey(key)] = value
            }
        }
    }

    private fun saveFloatData(key: String, value: Float) {
        GlobalScope.launch {
            dataStore.edit { mutablePreferences ->
                mutablePreferences[preferencesKey(key)] = value
            }
        }
    }

    private fun saveDoubleData(key: String, value: Double) {
        GlobalScope.launch {
            dataStore.edit { mutablePreferences ->
                mutablePreferences[preferencesKey(key)] = value
            }
        }
    }

    private fun saveLongData(key: String, value: Long) {
        GlobalScope.launch {
            dataStore.edit { mutablePreferences ->
                mutablePreferences[preferencesKey(key)] = value
            }
        }
    }


    // =========================以下为同步存储数据===========================
    private fun saveSyncBooleanData(key: String, value: Boolean) = runBlocking {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[preferencesKey(key)] = value
        }
    }

    private fun saveSyncFloatData(key: String, value: Float) = runBlocking {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[preferencesKey(key)] = value
        }
    }

    private fun saveSyncIntData(key: String, value: Int) = runBlocking {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[preferencesKey(key)] = value
        }
    }

    private fun saveSyncLongData(key: String, value: Long) = runBlocking {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[preferencesKey(key)] = value
        }
    }

    private fun saveSyncDoubleData(key: String, value: Double) = runBlocking {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[preferencesKey(key)] = value
        }
    }

    private fun saveSyncStringData(key: String, value: String) = runBlocking {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[preferencesKey(key)] = value
        }
    }

    /**
     * 异步清除数据
     */
    fun clear() {
        GlobalScope.launch {
            dataStore.edit {
                it.clear()
            }
        }
    }

    /**
     * 同步清除数据
     */
    fun clearSync() {
        runBlocking {
            dataStore.edit {
                it.clear()
            }
        }
    }

}