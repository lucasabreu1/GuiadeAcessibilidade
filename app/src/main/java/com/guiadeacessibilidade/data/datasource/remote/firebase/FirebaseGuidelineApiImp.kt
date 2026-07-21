package com.guiadeacessibilidade.data.datasource.remote.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.guiadeacessibilidade.data.datasource.remote.api.IGuidelineApi
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkGuideline
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSuccessCriteria
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.text.equals
import kotlin.text.get

class FirebaseGuidelineApiImp(
    private val dbFirestore: FirebaseFirestore,
    private val dispatcher: CoroutineDispatcher

) : IGuidelineApi{
    override suspend fun getAll(): List<NetworkGuideline> {

        var result = dbFirestore.collection("guideline")
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
            val guidelineList = result.documents.mapNotNull { document ->
                try
                {
                    NetworkGuideline(

                        document.id,
                        document.getString("brief_purpose") ?: "",
                        document.getString("name") ?: "",
                        document.getString("number") ?: "",
                        document.getString("principle_id") ?: ""
                    )


                } catch (e: Exception)
                {
                    null
                }
            }
            guidelineList
        }
        return list

    }

    override suspend fun loadGuidelineById(id: String): NetworkGuideline {

        val result = dbFirestore.collection("guideline")
            .get()
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
            .await()

        val guidelineDocument = withContext(dispatcher){
            result.documents.firstOrNull() { guideline ->
                guideline.id.equals(id)
            }
        }


        return NetworkGuideline(
            guidelineDocument?.id ?: "",
                    guidelineDocument?.getString("brief_purpose") ?: "",
                    guidelineDocument?.getString("name") ?: "",
                    guidelineDocument?.getString("number") ?: "",
                    guidelineDocument?.getString("principle_id") ?: ""

        )

    }
}