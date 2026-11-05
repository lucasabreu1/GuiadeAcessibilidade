package com.guiadeacessibilidade.di

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.guiadeacessibilidade.data.datasource.remote.api.IAuthApi
import com.guiadeacessibilidade.data.datasource.remote.api.IAuthRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.api.ICategoryApi
import com.guiadeacessibilidade.data.datasource.remote.api.ICategoryRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.api.IGuidelineApi
import com.guiadeacessibilidade.data.datasource.remote.api.IGuidelineRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.api.IMenuApi
import com.guiadeacessibilidade.data.datasource.remote.api.IMenuRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.api.IOrientationApi
import com.guiadeacessibilidade.data.datasource.remote.api.IOrientationRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.api.IPrincipleApi
import com.guiadeacessibilidade.data.datasource.remote.api.IPrincipleRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.api.ISubCategoryApi
import com.guiadeacessibilidade.data.datasource.remote.api.ISubCategoryRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.api.ISuccessCriteriaApi
import com.guiadeacessibilidade.data.datasource.remote.api.ISuccessCriteriaRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.firebase.FirebaseAuthApiImp
import com.guiadeacessibilidade.data.datasource.remote.firebase.FirebaseCategoryApiImp
import com.guiadeacessibilidade.data.datasource.remote.firebase.FirebaseGuidelineApiImp
import com.guiadeacessibilidade.data.datasource.remote.firebase.FirebaseMenuApiImp
import com.guiadeacessibilidade.data.datasource.remote.firebase.FirebaseOrientationApiImp
import com.guiadeacessibilidade.data.datasource.remote.firebase.FirebasePrincipleApiImp
import com.guiadeacessibilidade.data.datasource.remote.firebase.FirebaseSubCategoryApiImp
import com.guiadeacessibilidade.data.datasource.remote.firebase.FirebaseSuccessCriteriaApiImp
import com.guiadeacessibilidade.data.datasource.remote.imp.AuthRemoteDataSourceImp
import com.guiadeacessibilidade.data.datasource.remote.imp.CategoryRemoteDataSourceImp
import com.guiadeacessibilidade.data.datasource.remote.imp.GuidelineRemoteDataSourceImp
import com.guiadeacessibilidade.data.datasource.remote.imp.MenuRemoteDataSourceImp
import com.guiadeacessibilidade.data.datasource.remote.imp.OrientationRemoteDataSourceImp
import com.guiadeacessibilidade.data.datasource.remote.imp.PrincipleRemoteDataSourceImp
import com.guiadeacessibilidade.data.datasource.remote.imp.SubCategoryRemoteDataSourceImp
import com.guiadeacessibilidade.data.datasource.remote.imp.SuccessCriteriaRemoteDataSourceImp
import com.guiadeacessibilidade.data.repository.CategoryRepositoryImp
import com.guiadeacessibilidade.data.repository.GuidelineRepositoryImp
import com.guiadeacessibilidade.data.repository.AuthRepositoryImp
import com.guiadeacessibilidade.data.repository.MenuRepositoryImp
import com.guiadeacessibilidade.data.repository.OrientationRepositoryImp
import com.guiadeacessibilidade.data.repository.PrincipleRepositoryImp
import com.guiadeacessibilidade.data.repository.SubCategoryRepositoryImp
import com.guiadeacessibilidade.data.repository.SuccessCriteriaRepositoryImp
import com.guiadeacessibilidade.domain.repository.IAuthRepository
import com.guiadeacessibilidade.domain.repository.ICategoryRepository
import com.guiadeacessibilidade.domain.repository.IGuidelineRepository
import com.guiadeacessibilidade.domain.repository.IMenuRepository
import com.guiadeacessibilidade.domain.repository.IOrientationRepository
import com.guiadeacessibilidade.domain.repository.IPrincipleRepository
import com.guiadeacessibilidade.domain.repository.ISubCategoryRepository
import com.guiadeacessibilidade.domain.repository.ISuccessCriteriaRepository
import com.guiadeacessibilidade.domain.usacase.AddCheckListUseCase
import com.guiadeacessibilidade.domain.usacase.GetAllCategoryUseCase
import com.guiadeacessibilidade.domain.usacase.GetAllCheckListsUseCase
import com.guiadeacessibilidade.domain.usacase.GetAllMenuUseCase
import com.guiadeacessibilidade.domain.usacase.GetAllSubCategoryUseCase
import com.guiadeacessibilidade.domain.usacase.GetAllSuccessCriteriaUseCase
import com.guiadeacessibilidade.domain.usacase.GetCurrentUserUseCase
import com.guiadeacessibilidade.domain.usacase.GetOrientationsBySubCategoryIdUseCase
import com.guiadeacessibilidade.domain.usacase.GetOrientationsByUserChecklistUseCase
import com.guiadeacessibilidade.domain.usacase.GetSubCategoriesByCategoryIdUseCase
import com.guiadeacessibilidade.domain.usacase.LoadOrientationByIdUseCase
import com.guiadeacessibilidade.domain.usacase.LoginUseCase
import com.guiadeacessibilidade.domain.usacase.LogoutUserUseCase
import com.guiadeacessibilidade.domain.usacase.RegisterUserUseCase
import com.guiadeacessibilidade.domain.usacase.ToggleCheckListOrientationUseCase
import com.guiadeacessibilidade.domain.usacase.ToggleOrientationUseCase
import kotlinx.coroutines.Dispatchers

class AppContainer(context: Context) {

    private val dbFirestore: FirebaseFirestore by lazy {
        Firebase.firestore
    }

    private val firebaseAuth: FirebaseAuth by lazy {
        Firebase.auth
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

    private val orientationApi: IOrientationApi by lazy {
        FirebaseOrientationApiImp(dbFirestore, ioDispatcher)
    }

    private val authApi: IAuthApi by lazy {
        FirebaseAuthApiImp(firebaseAuth, dbFirestore, ioDispatcher)
    }

    private val menuApi: IMenuApi by lazy {
        FirebaseMenuApiImp(dbFirestore, ioDispatcher)
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

    private val orientationRemoteDataSource: IOrientationRemoteDataSource by lazy {
        OrientationRemoteDataSourceImp(orientationApi, ioDispatcher)
    }

    private val authRemoteDataSource: IAuthRemoteDataSource by lazy {
        AuthRemoteDataSourceImp(authApi, ioDispatcher)
    }

    private val menuRemoteDataSource: IMenuRemoteDataSource by lazy {
        MenuRemoteDataSourceImp(menuApi, ioDispatcher)
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

    private val orientationRepository: IOrientationRepository by lazy {
        OrientationRepositoryImp(orientationRemoteDataSource, ioDispatcher)
    }

    private val authRepository: IAuthRepository by lazy {
        AuthRepositoryImp(authRemoteDataSource, ioDispatcher)
    }

    private val menuRepository: IMenuRepository by lazy {
        MenuRepositoryImp(menuRemoteDataSource, ioDispatcher)
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

    val getSubCategoryByCategoryIdUseCase: GetSubCategoriesByCategoryIdUseCase by lazy {
        GetSubCategoriesByCategoryIdUseCase(subCategoryRepository, ioDispatcher)
    }

    val loadOrientationByIdUseCade: LoadOrientationByIdUseCase by lazy {
        LoadOrientationByIdUseCase(orientationRepository)
    }

    val getOrientationsBySubCategoryIdUseCase: GetOrientationsBySubCategoryIdUseCase by lazy {
        GetOrientationsBySubCategoryIdUseCase(orientationRepository, authRepository,ioDispatcher)

    }

    val loadOrientationByIdUseCase: LoadOrientationByIdUseCase by lazy {
        LoadOrientationByIdUseCase(orientationRepository)
    }

    val loginUseCase: LoginUseCase by lazy {
        LoginUseCase(authRepository, ioDispatcher)
    }

    val registerUserUseCase: RegisterUserUseCase by lazy {
        RegisterUserUseCase(authRepository, ioDispatcher)
    }

    val toggleOrientationUseCase: ToggleOrientationUseCase by lazy {
        ToggleOrientationUseCase(authRepository, ioDispatcher)
    }

    val getCurrentUserUseCase: GetCurrentUserUseCase by lazy {
        GetCurrentUserUseCase(authRepository, ioDispatcher)
    }

    val logoutUserUseCase: LogoutUserUseCase by lazy {
        LogoutUserUseCase(authRepository, ioDispatcher)
    }

    val getAllMenuUseCase: GetAllMenuUseCase by lazy {
        GetAllMenuUseCase(menuRepository, ioDispatcher)
    }

    val getAllCheckListsUseCase: GetAllCheckListsUseCase by lazy {
        GetAllCheckListsUseCase(authRepository, ioDispatcher)
    }

    val addCheckListUseCase: AddCheckListUseCase by lazy {
        AddCheckListUseCase(authRepository, ioDispatcher)
    }

    val getOrientationsByUserChecklistUseCase: GetOrientationsByUserChecklistUseCase by lazy{
        GetOrientationsByUserChecklistUseCase(orientationRepository, authRepository, ioDispatcher)
    }

    val toggleCheckListOrientationUseCase: ToggleCheckListOrientationUseCase by lazy{
        ToggleCheckListOrientationUseCase(authRepository, ioDispatcher)
    }
}