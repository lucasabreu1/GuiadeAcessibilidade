package com.guiadeacessibilidade.data.datasource.remote.firebase


import com.google.firebase.firestore.FirebaseFirestore
import com.guiadeacessibilidade.data.datasource.remote.api.IPrincipleApi
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkPrinciple
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebasePrincipleApiImp(
    private val dbFirestore: FirebaseFirestore,
    private val dispatcher: CoroutineDispatcher

) : IPrincipleApi{

    override suspend fun loadPrincipleById(id: String): NetworkPrinciple {

        val result = dbFirestore.collection("principle")
            .get()
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
            .await()

        val principleDocument = withContext(dispatcher){
            result.documents.firstOrNull() { principle ->
                principle.id.equals(id)
            }
        }

        return NetworkPrinciple(
            principleDocument?.id ?: "",
            principleDocument?.getString("name") ?: "",
            principleDocument?.getString("number") ?: ""

        )

    }
}