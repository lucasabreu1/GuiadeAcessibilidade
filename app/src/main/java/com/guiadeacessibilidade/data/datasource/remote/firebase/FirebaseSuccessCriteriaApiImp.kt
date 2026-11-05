package com.guiadeacessibilidade.data.datasource.remote.firebase

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.guiadeacessibilidade.data.datasource.remote.api.ISuccessCriteriaApi
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSubCategory
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
                    val searchTags = document.getSearchTags()
                    val subCategoryIdList = document.getSubCategoryIdList()

                    val obj = NetworkSuccessCriteria(
                        document.id,
                        document.getString("guideline_id") ?: "",
                        document.getString("principle_id") ?: "",
                        document.getString("name") ?: "",
                        document.getString("level") ?: "",
                        document.getString("link") ?: "",
                        document.getString("description") ?: "",
                        document.getString("mainTag") ?: "",
                        searchTags,
                        subCategoryIdList
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

    override suspend fun getSuccessCriteriasBySubCategoryId(subCategoryId: String): List<NetworkSuccessCriteria> {

        val result = dbFirestore.collection("success_criteria")
            .get()
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
            .await()

        val list = withContext(dispatcher){

            val successCriteriasList = result?.documents
                ?.filterNotNull()
                ?.filter { document ->
                    val _subCategoryIdList = document.getSubCategoryIdList()
                    _subCategoryIdList.contains(subCategoryId)
                }
                ?.map { document ->

                    NetworkSuccessCriteria(
                        document.id,
                        document.getString("guideline_id") ?: "",
                        document.getString("principle_id") ?: "",
                        document.getString("name") ?: "",
                        document.getString("level") ?: "",
                        document.getString("link") ?: "",
                        document.getString("description") ?: "",
                        document.getString("main_tag") ?: "",
                        document.getSearchTags(),
                        document.getSubCategoryIdList()
                    )
                }
            successCriteriasList
        }

        return list ?: emptyList()
    }

    fun DocumentSnapshot.getSearchTags(): List<String> {

        val rawTagsList = this.get("search_tags") as? List<*>
        val searchTags: List<String> = rawTagsList?.mapNotNull { it as? String } ?: emptyList()

        return searchTags
    }

    fun DocumentSnapshot.getSubCategoryIdList(): List<String> {

        val rawSubCategoryIdList = this.get("sub_category_id_list") as? List<*>
        val result: List<String> = rawSubCategoryIdList?.mapNotNull { it as? String } ?: emptyList()

        return result
    }

    override suspend fun loadSuccessCriteriaById(id: String): NetworkSuccessCriteria {
        TODO("Not yet implemented")
    }
}
