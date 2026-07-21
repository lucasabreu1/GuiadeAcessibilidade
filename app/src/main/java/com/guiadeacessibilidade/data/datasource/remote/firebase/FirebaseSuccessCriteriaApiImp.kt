package com.guiadeacessibilidade.data.datasource.remote.firebase

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.guiadeacessibilidade.data.datasource.remote.api.ISuccessCriteriaApi
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSuccessCriteria
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseSuccessCriteriaApiImp(
    private val dbFirestore: FirebaseFirestore,
    private val dispatcher: CoroutineDispatcher

) : ISuccessCriteriaApi{
    override suspend fun getAll(): List<NetworkSuccessCriteria> {

        var result = dbFirestore.collection("success_criteria")
            .get()
            .addOnSuccessListener { result ->
                println("FIREBASE DATA -> SUCCESS !!!")
                    for (document in result){
                        Log.d("TESTING", "${document.id} => ${document.data}")
                    }
            }
            .addOnFailureListener { exception ->
                Log.w("TESTING", "Error getting document", exception)
            }.await()

        val list = withContext(dispatcher){
                val networkList = result.documents.mapNotNull { document ->
                try
                {
                    val searchTags = document.toNetworkSuccessCriteria()

                    val obj = NetworkSuccessCriteria(
                        document.id,
                        document.getString("guideline_id") ?: "",
                        document.getString("principle_id") ?: "",
                        document.getString("name") ?: "",
                        document.getString("level") ?: "",
                        document.getString("link") ?: "",
                        document.getString("description") ?: "",
                        document.getString("mainTag") ?: "",
                        searchTags
                    )

                    obj

                } catch (e: Exception)
                {
                    null
                }
            }
            networkList
        }
        return list
    }

    fun DocumentSnapshot.toNetworkSuccessCriteria(): List<String> {

        val rawTagsList = this.get("search_tags") as? List<*>
        val searchTags: List<String> = rawTagsList?.mapNotNull { it as? String } ?: emptyList()

        return searchTags
    }

    override suspend fun loadSuccessCriteriaById(id: String): NetworkSuccessCriteria {
        TODO("Not yet implemented")
    }
}
