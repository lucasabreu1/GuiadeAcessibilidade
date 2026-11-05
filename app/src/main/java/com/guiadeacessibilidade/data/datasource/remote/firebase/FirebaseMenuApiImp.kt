package com.guiadeacessibilidade.data.datasource.remote.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.guiadeacessibilidade.data.datasource.remote.api.IMenuApi
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkMenu
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseMenuApiImp(
    private val dbFirestore: FirebaseFirestore,
    private val dispatcher: CoroutineDispatcher
) : IMenuApi{
    override suspend fun getAll(): List<NetworkMenu> {

        val allMenuList = withContext(dispatcher){

            try
            {
                val result = dbFirestore.collection("menu")
                    .get()
                    .await()

                val menuList = result?.documents
                    ?.mapNotNull { document ->

                        NetworkMenu(
                            document.id,
                            document.getString("name") ?: "",
                            document.getString("svg_content") ?: "",
                            document.getString("collection") ?: ""
                        )
                    }

                menuList
            }
            catch (exception: Exception)
            {
                throw exception
            }

        }
        return allMenuList ?: emptyList()
    }
}