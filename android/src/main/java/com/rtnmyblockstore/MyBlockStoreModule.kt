package com.rtnmyblockstore

import android.content.ContentValues
import android.util.Log
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.google.android.gms.auth.blockstore.Blockstore
import com.google.android.gms.auth.blockstore.BlockstoreClient
import com.google.android.gms.auth.blockstore.DeleteBytesRequest
import com.google.android.gms.auth.blockstore.RetrieveBytesRequest
import com.google.android.gms.auth.blockstore.RetrieveBytesResponse
import com.google.android.gms.auth.blockstore.StoreBytesData
import com.rtnmyblockstore.NativeMyBlockStoreSpec

class MyBlockStoreModule(context: ReactApplicationContext): NativeMyBlockStoreSpec(context) {

    private var client: BlockstoreClient

    init {
        client = Blockstore.getClient(context)
    }

    override fun getName(): String {
        return NAME
    }

    override fun saveUnEncryptedData(data: String?, promise: Promise?) {
        val key1 = "com.rnapp.key1"
        val storeRequest1 = StoreBytesData.Builder()
            .setBytes(data!!.toByteArray())
            .setKey(key1)
            .build()
        client.storeBytes(storeRequest1)
            .addOnSuccessListener { result: Int ->
                Log.d("Here123",
                    "Stored $result bytes")
            }
            .addOnFailureListener { e ->
                Log.e("Here123", "Failed to store bytes", e)
            }
    }

    override fun saveEncryptedData(data: String?, promise: Promise?) {
        val key2 = "com.rnapp.key2"
        val storeRequest1 = StoreBytesData.Builder()
            .setBytes(data!!.toByteArray())
            .setKey(key2)



        client.isEndToEndEncryptionAvailable.addOnSuccessListener {
            storeRequest1.setShouldBackupToCloud(true)
            client.storeBytes(storeRequest1.build())
                .addOnSuccessListener { result: Int ->
                    Log.d("Here123",
                        "Stored $result bytes")
                }
                .addOnFailureListener { e ->
                    Log.e("Here123", "Failed to store bytes", e)
                }
        }.addOnFailureListener {
            Log.d("Here123", "E2EE is not available, only store bytes for D2D restore.")

        }

    }

    override fun readUnEncryptedData(promise: Promise?) {
        val key1 = "com.rnapp.key1"

        val requestedKeys = listOf(key1) // Add keys to array

        val retrieveRequest = RetrieveBytesRequest.Builder()
            .setKeys(requestedKeys)
            .build()

        client.retrieveBytes(retrieveRequest)
            .addOnSuccessListener { result: RetrieveBytesResponse ->
                val blockstoreDataMap =
                    result.blockstoreDataMap
                for ((key, value) in blockstoreDataMap) {
                    Log.d(
                        ContentValues.TAG, String.format(
                        "Retrieved bytes %s associated with key %s.",
                        String(value.bytes), key))
                    promise?.resolve(String(value.bytes))
                }
            }
            .addOnFailureListener { e: Exception? ->
                Log.e(
                    ContentValues.TAG,
                    "Failed to store bytes",
                    e)
            }
    }

    override fun readEncryptedData(promise: Promise?) {
        val key2 = "com.rnapp.key2"
        val requestedKeys = listOf(key2) // Add keys to array
        val retrieveRequest = RetrieveBytesRequest.Builder()
            .setKeys(requestedKeys)
            .build()

        client.retrieveBytes(retrieveRequest)
            .addOnSuccessListener { result: RetrieveBytesResponse ->
                val blockstoreDataMap =
                    result.blockstoreDataMap
                for ((key, value) in blockstoreDataMap) {
                    Log.d(ContentValues.TAG, String.format(
                        "Retrieved bytes %s associated with key %s.",
                        String(value.bytes), key))
                    promise?.resolve(String(value.bytes))
                }
            }
            .addOnFailureListener { e: Exception? ->
                Log.e(ContentValues.TAG,
                    "Failed to store bytes",
                    e)
            }
    }

    override fun deleteData(promise: Promise?) {
        val key1 = "com.rnapp.key1"

        val requestedKeys = listOf(key1) // Add keys to array

        val retrieveRequest = DeleteBytesRequest.Builder()
            .setKeys(requestedKeys)
            .build()

        client.deleteBytes(retrieveRequest)
    }

    override fun deleteAllData(promise: Promise?) {
        val deleteAllRequest = DeleteBytesRequest.Builder()
            .setDeleteAll(true)
            .build()
        client.deleteBytes(deleteAllRequest)
            .addOnSuccessListener { result: Boolean ->
                Log.d("here123",
                    "Any data found and deleted? $result")
            }
    }

    companion object {
        const val NAME = "RTNMyBlockStore"
    }

}