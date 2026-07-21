package com.guiadeacessibilidade.di

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.guiadeacessibilidade.data.datasource.remote.api.ICategoryApi
import com.guiadeacessibilidade.data.datasource.remote.api.ICategoryRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.api.IGuidelineApi
import com.guiadeacessibilidade.data.datasource.remote.api.IGuidelineRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.api.IPrincipleApi
import com.guiadeacessibilidade.data.datasource.remote.api.IPrincipleRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.api.ISubCategoryApi
import com.guiadeacessibilidade.data.datasource.remote.api.ISubCategoryRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.api.ISuccessCriteriaApi
import com.guiadeacessibilidade.data.datasource.remote.api.ISuccessCriteriaRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.firebase.FirebaseCategoryApiImp
import com.guiadeacessibilidade.data.datasource.remote.firebase.FirebaseGuidelineApiImp
import com.guiadeacessibilidade.data.datasource.remote.firebase.FirebasePrincipleApiImp
import com.guiadeacessibilidade.data.datasource.remote.firebase.FirebaseSubCategoryApiImp
import com.guiadeacessibilidade.data.datasource.remote.firebase.FirebaseSuccessCriteriaApiImp
import com.guiadeacessibilidade.data.datasource.remote.imp.CategoryRemoteDataSourceImp
import com.guiadeacessibilidade.data.datasource.remote.imp.GuidelineRemoteDataSourceImp
import com.guiadeacessibilidade.data.datasource.remote.imp.PrincipleRemoteDataSourceImp
import com.guiadeacessibilidade.data.datasource.remote.imp.SubCategoryRemoteDataSourceImp
import com.guiadeacessibilidade.data.datasource.remote.imp.SuccessCriteriaRemoteDataSourceImp
import com.guiadeacessibilidade.data.repository.CategoryRepositoryImp
import com.guiadeacessibilidade.data.repository.GuidelineRepositoryImp
import com.guiadeacessibilidade.data.repository.PrincipleRepositoryImp
import com.guiadeacessibilidade.data.repository.SubCategoryRepositoryImp
import com.guiadeacessibilidade.data.repository.SuccessCriteriaRepositoryImp
import com.guiadeacessibilidade.domain.repository.ICategoryRepository
import com.guiadeacessibilidade.domain.repository.IGuidelineRepository
import com.guiadeacessibilidade.domain.repository.IPrincipleRepository
import com.guiadeacessibilidade.domain.repository.ISubCategoryRepository
import com.guiadeacessibilidade.domain.repository.ISuccessCriteriaRepository
import com.guiadeacessibilidade.domain.usacase.GetAllCategoryUseCase
import com.guiadeacessibilidade.domain.usacase.GetAllSubCategoryUseCase
import com.guiadeacessibilidade.domain.usacase.GetAllSuccessCriteriaUseCase
import com.guiadeacessibilidade.domain.usacase.GetSubCategoryByCategoryIdUseCase
import kotlinx.coroutines.Dispatchers

class AppContainer(context: Context) {

    private val dbFirestore: FirebaseFirestore by lazy {
        Firebase.firestore
    }

    private val ioDispatcher = Dispatchers.IO

    private val successCriteriaApi: ISuccessCriteriaApi by lazy {
        FirebaseSuccessCriteriaApiImp(dbFirestore, ioDispatcher)
    }

    private val guidelineApi: IGuidelineApi by lazy {
        FirebaseGuidelineApiImp(dbFirestore, ioDispatcher)
    }

    private val principleApi: IPrincipleApi by lazy {
        FirebasePrincipleApiImp(dbFirestore, ioDispatcher)
    }

    private val categoryApi: ICategoryApi by lazy {
        FirebaseCategoryApiImp(dbFirestore, ioDispatcher)
    }

    private val subCategoryApi: ISubCategoryApi by lazy {
        FirebaseSubCategoryApiImp(dbFirestore, ioDispatcher)
    }

    private val successCriteriaRemoteDataSource: ISuccessCriteriaRemoteDataSource by lazy {
        SuccessCriteriaRemoteDataSourceImp(successCriteriaApi, ioDispatcher)
    }

    private val guidelineRemoteDataSource: IGuidelineRemoteDataSource by lazy {
        GuidelineRemoteDataSourceImp(guidelineApi, ioDispatcher)
    }

    private val principleRemoteDataSource: IPrincipleRemoteDataSource by lazy {
        PrincipleRemoteDataSourceImp(principleApi, ioDispatcher)
    }

    private val categoryRemoteDataSource: ICategoryRemoteDataSource by lazy {
        CategoryRemoteDataSourceImp(categoryApi, ioDispatcher)
    }

    private val subCategoryRemoteDataSource: ISubCategoryRemoteDataSource by lazy {
        SubCategoryRemoteDataSourceImp(subCategoryApi, ioDispatcher)
    }

    private val successCriteriaRepository: ISuccessCriteriaRepository by lazy {
        SuccessCriteriaRepositoryImp(successCriteriaRemoteDataSource, ioDispatcher)
    }

    private val categoryRepository: ICategoryRepository by lazy {
        CategoryRepositoryImp(categoryRemoteDataSource, ioDispatcher)
    }

    private val subCategoryRepository: ISubCategoryRepository by lazy {
        SubCategoryRepositoryImp(subCategoryRemoteDataSource, ioDispatcher)
    }

    private val guidelineRepository: IGuidelineRepository by lazy {
        GuidelineRepositoryImp(guidelineRemoteDataSource, ioDispatcher)
    }

    private val principleRepository: IPrincipleRepository by lazy {
        PrincipleRepositoryImp(principleRemoteDataSource, ioDispatcher)
    }

    val getAllSuccessCriteriaUseCase: GetAllSuccessCriteriaUseCase by lazy {
        GetAllSuccessCriteriaUseCase(successCriteriaRepository,
            guidelineRepository,
            principleRepository,
            ioDispatcher)
    }

    val getAllCategoryUseCase: GetAllCategoryUseCase by lazy {
        GetAllCategoryUseCase(categoryRepository, ioDispatcher)
    }

    val getAllSubCategoryUseCase: GetAllSubCategoryUseCase by lazy {
        GetAllSubCategoryUseCase(subCategoryRepository, ioDispatcher)
    }

    val getSubCategoryByCategoryIdUseCase: GetSubCategoryByCategoryIdUseCase by lazy {
        GetSubCategoryByCategoryIdUseCase(subCategoryRepository, ioDispatcher)
    }

}