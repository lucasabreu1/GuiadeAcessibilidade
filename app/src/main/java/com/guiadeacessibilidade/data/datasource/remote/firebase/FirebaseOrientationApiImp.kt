package com.guiadeacessibilidade.data.datasource.remote.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.guiadeacessibilidade.data.datasource.remote.api.ISubCategoryApi
import com.guiadeacessibilidade.data.datasource.remote.api.IOrientationApi
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkOrientation
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSubCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseOrientationApiImp(
    private val dbFirestore: FirebaseFirestore,
    private val dispatcher: CoroutineDispatcher

) : IOrientationApi{
    override suspend fun getAll(): List<NetworkOrientation> {

        var result = dbFirestore.collection("orientation")
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
            val subCategoryList = result?.documents
                ?.mapNotNull { document ->

                    NetworkOrientation(
                        document.id,
                        document.getString("sub_category_id") ?: "",
                        document.getString("name") ?: "",
                        document.getString("short_name") ?: "",
                        document.getString("description_1") ?: "",
                        document.getString("description_2") ?: "",
                        document.getString("image_url") ?: "",
                        document.getString("svg_icon") ?: ""
                    )
                }
            subCategoryList
        }
        return list ?: emptyList()
    }

    override suspend fun getOrientationsBySubCategoryId(subCategoryId: String): List<NetworkOrientation> {

        var result = dbFirestore.collection("orientation")
            .get()
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
            .await()

        val list = withContext(dispatcher){

            val orientationList = result?.documents
                ?.filterNotNull()
                ?.filter { document ->
                    val _subCategoryId = document.getString("sub_category_id") ?: ""
                    _subCategoryId == subCategoryId
                }
                ?.map { document ->

                    NetworkOrientation(
                        document.id,
                        document.getString("sub_category_id") ?: "",
                        document.getString("name") ?: "",
                        document.getString("short_name") ?: "",
                        document.getString("description_1") ?: "",
                        document.getString("description_2") ?: "",
                        document.getString("image_url") ?: "",
                        document.getString("svg_icon") ?: ""
                    )
                }
            orientationList
        }
        return list ?: emptyList()
    }

    override suspend fun loadOrientationById(id: String): NetworkOrientation? {

        val result = dbFirestore.collection("orientation")
            .get()
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
            .await()

        val orientationDocument = withContext(dispatcher){
            result.documents.firstOrNull() { orientation ->
                orientation.id.equals(id)
            }
        }

        if (orientationDocument == null)
        {
            return null;
        }
        else
        {
            return NetworkOrientation(
                orientationDocument?.id ?: "",
                orientationDocument?.getString("sub_category_id") ?: "",
                orientationDocument?.getString("name") ?: "",
                orientationDocument?.getString("short_name") ?: "",
                orientationDocument?.getString("description_1") ?: "",
                orientationDocument?.getString("description_2") ?: "",
                orientationDocument?.getString("image_url") ?: "",
                orientationDocument?.getString("svg_icon") ?: ""
            )
        }
    }
}