package com.guiadeacessibilidade.data.datasource.remote.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.guiadeacessibilidade.data.datasource.remote.api.IAuthApi
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkChecklist
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class FirebaseAuthApiImp(
    private val firebaseAuth: FirebaseAuth,
    private val dbFirestore: FirebaseFirestore,
    private val dispatcher: CoroutineDispatcher

) : IAuthApi{
    override suspend fun login(
        email: String,
        password: String
    ): NetworkUser? {

        val user = withContext(dispatcher) {

            try
            {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

                if (result?.user != null)
                {
                    val loginUser = result.user

                    NetworkUser(
                        loginUser?.uid ?: "",
                        loginUser?.displayName ?: "",
                        loginUser?.email ?: ""
                    )
                }
                else
                {
                    null
                }
            }
            catch (exception: Exception)
            {
                throw exception
            }
        }
        return user
    }

    override suspend fun register(name: String, email: String, password: String): NetworkUser? {

        val user = withContext(dispatcher){

            try
            {
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

                val currentUser = result?.user

                if (currentUser != null)
                {
                    var profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    currentUser.updateProfile(profileUpdates).await()

                    NetworkUser(
                        currentUser.uid ?: "",
                        name = currentUser.displayName ?: "",
                        email = currentUser.email ?: ""
                    )
                }
                else
                {
                    null
                }

            }catch (exception: Exception){
                throw exception
            }
        }
        return user
    }

    override fun getCurrentUser(): NetworkUser? {

        val currentUser = firebaseAuth.currentUser

        if (currentUser != null)
        {
            return NetworkUser(
                currentUser.uid ?: "",
                currentUser.displayName ?: "",
                currentUser.email ?: ""
            )
        }
        else
        {
            return null
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun getCompletedOrientationsListByUser(uid: String): Set<String> {

        val setCompletedOrientation = withContext(dispatcher){

            try
            {
                 val currentUser = getCurrentUser()

                 if (currentUser != null)
                 {

                    val completedOrientations = dbFirestore.collection("user")
                                                    .document(currentUser.uid)
                                                    .collection("completed_orientations")
                                                    .get()
                                                    .await()

                    completedOrientations.documents.map { it.id }.toSet()
                 }
                else
                {
                    null
                }

            }catch (exception: Exception){
                null
            }

        }
        return setCompletedOrientation ?: emptySet()
    }

    override suspend fun getUserCheckListCompletedOrientations(checkListId: String): Set<String> {

        val setCompletedOrientation = withContext(dispatcher){

            try
            {
                val currentUser = getCurrentUser()

                if (currentUser != null)
                {

                    val completedOrientations = dbFirestore.collection("user")
                        .document(currentUser.uid)
                        .collection("checklist")
                        .document(checkListId)
                        .collection("completed_orientation")
                        .get()
                        .await()

                    completedOrientations.documents.map { it.id }.toSet()
                }
                else
                {
                    null
                }

            }catch (exception: Exception){
                null
            }

        }
        return setCompletedOrientation ?: emptySet()
    }

    override suspend fun onToggleOrientation(
        orientationId: String,
        isCompleted: Boolean
    ): Boolean {

        val toggleOrientationResult = withContext(dispatcher){

            try
            {
                val currentUser = getCurrentUser()

                if (currentUser != null)
                {
                    val userProgressRef = dbFirestore.collection("user")
                        .document(currentUser.uid)
                        .collection("completed_orientations")
                        .document(orientationId)

                    if (!isCompleted)
                    {
                        userProgressRef.delete().await()
                    }
                    else
                    {
                        val data = mapOf(
                            "completed_at" to FieldValue.serverTimestamp()
                        )
                        userProgressRef.set(data).await()
                    }
                    true
                }
                else
                {
                    throw Exception("Usuario nao encontrado. Faça login novamente")
                }
            }
            catch (exception: Exception)
            {
                throw exception
            }
        }
        return toggleOrientationResult
    }

    override suspend fun onToggleCheckListOrientation(
        checkListId: String,
        orientationId: String,
        isCompleted: Boolean
    ): Boolean {

        val toggleOrientationResult = withContext(dispatcher){

            try
            {
                val currentUser = getCurrentUser()

                if (currentUser != null)
                {
                    val userProgressRef = dbFirestore.collection("user")
                        .document(currentUser.uid)
                        .collection("checklist")
                        .document(checkListId)
                        .collection("completed_orientation")
                        .document(orientationId)

                    if (!isCompleted)
                    {
                        userProgressRef.delete().await()
                    }
                    else
                    {
                        val data = mapOf(
                            "completed_at" to FieldValue.serverTimestamp()
                        )
                        userProgressRef.set(data).await()
                    }
                    true
                }
                else
                {
                    throw Exception("Usuario nao encontrado. Faça login novamente")
                }
            }
            catch (exception: Exception)
            {
                throw exception
            }
        }
        return toggleOrientationResult
    }

    override suspend fun addChecklist(
        checkListName: String,
        checkListDescription: String,
        checkListSubCategoryId: String
    ): Boolean {

        val addCheckListResult = withContext(dispatcher){

            try
            {
                val currentUser = getCurrentUser()

                if (currentUser != null)
                {
                    val uid = UUID.randomUUID().toString()

                    val data = mapOf(
                        "name" to checkListName,
                        "checklist_description" to checkListDescription,
                        "sub_category_id" to checkListSubCategoryId,
                        "created_at" to FieldValue.serverTimestamp()
                    )

                    dbFirestore.collection("user")
                        .document(currentUser.uid)
                        .collection("checklist")
                        .document(uid)
                        .set(data)
                        .await()

                    true
                }
                else
                {
                    throw Exception("Usuario nao encontrado. Faça login novamente")
                }
            }
            catch (exception: Exception)
            {
                throw  exception
            }
        }
        return addCheckListResult
    }

    override suspend fun getCheckLists(uuid: String): List<NetworkChecklist> {

        val checkLists = withContext(dispatcher){

            try
            {
                val result = dbFirestore.collection("user")
                    .document(uuid)
                    .collection("checklist")
                    .get()
                    .await()

                val resultList = result?.documents
                    ?.mapNotNull { document ->

                        NetworkChecklist(
                            document.id,
                            document.getString("sub_category_id") ?: "",
                            document.getString("name") ?: "",
                            document.getString("description") ?: ""
                        )
                    }

                resultList
            }
            catch (exception: Exception)
            {
                throw exception
            }

        }
        return checkLists ?: emptyList()
    }
}