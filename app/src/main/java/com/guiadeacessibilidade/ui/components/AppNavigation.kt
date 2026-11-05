package com.guiadeacessibilidade.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.firebase.auth.FirebaseAuth
import com.guiadeacessibilidade.di.AppContainer
import com.guiadeacessibilidade.ui.Screen
import com.guiadeacessibilidade.ui.factory.AddCheckListViewModelFactory
import com.guiadeacessibilidade.ui.factory.CategoryViewModelFactory
import com.guiadeacessibilidade.ui.factory.CheckListViewModelFactory
import com.guiadeacessibilidade.ui.factory.LoginViewModelFactory
import com.guiadeacessibilidade.ui.factory.OrientationItemViewModelFactory
import com.guiadeacessibilidade.ui.factory.OrientationViewModelFactory
import com.guiadeacessibilidade.ui.factory.ProgressCheckListViewModelFactory
import com.guiadeacessibilidade.ui.factory.RegisterViewModelFactory
import com.guiadeacessibilidade.ui.factory.SubCategoryViewModelFactory
import com.guiadeacessibilidade.ui.factory.WelcomeViewModelFactory
import com.guiadeacessibilidade.ui.viewmodel.AddCheckListViewModel
import com.guiadeacessibilidade.ui.viewmodel.CategoryViewModel
import com.guiadeacessibilidade.ui.viewmodel.CheckListViewModel
import com.guiadeacessibilidade.ui.viewmodel.LoginViewModel
import com.guiadeacessibilidade.ui.viewmodel.OrientationItemViewModel
import com.guiadeacessibilidade.ui.viewmodel.OrientationViewModel
import com.guiadeacessibilidade.ui.viewmodel.ProgressCheckListViewModel
import com.guiadeacessibilidade.ui.viewmodel.RegisterViewModel
import com.guiadeacessibilidade.ui.viewmodel.SubCategoryViewModel
import com.guiadeacessibilidade.ui.viewmodel.WelcomeViewModel

@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    appContainer: AppContainer
){
    val navController = rememberNavController()

    val auth = FirebaseAuth.getInstance()

    val startDestination = if (auth.currentUser == null) {
        Screen.AuthGraph
    } else {
        Screen.MainAppGraph
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        navigation<Screen.AuthGraph>(
            startDestination = Screen.Login
        ){
            composable<Screen.Login>{ backstackEntry ->

                val factory = remember(backstackEntry){
                    LoginViewModelFactory(appContainer.loginUseCase)
                }

                val viewModel: LoginViewModel = viewModel(
                    viewModelStoreOwner = backstackEntry,
                    factory = factory
                )

                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.MainAppGraph){
                            popUpTo(Screen.AuthGraph) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register)
                    },
                    viewModel = viewModel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp)
                )
            }
            composable<Screen.Register> { backstackEntry ->

                val factory = remember(backstackEntry){
                    RegisterViewModelFactory(appContainer.registerUserUseCase)
                }

                val viewModel: RegisterViewModel = viewModel(
                    viewModelStoreOwner = backstackEntry,
                    factory = factory
                )

                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate(Screen.MainAppGraph){
                            popUpTo(Screen.AuthGraph) { inclusive = true }
                        }
                    },
                    viewModel = viewModel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp)
                )
            }
        }

        navigation<Screen.MainAppGraph>(
            startDestination = Screen.CategoriesRoute
        ) {

            composable<Screen.CategoriesRoute> { backStackEntry ->

//                val factory = remember(backStackEntry) {
//                    CategoryViewModelFactory(
//                                appContainer.getAllCategoryUseCase,
//                                appContainer.getCurrentUserUseCase,
//                                appContainer.logoutUserUseCase)
//                }

//                val viewModel: CategoryViewModel = viewModel(
//                    viewModelStoreOwner = backStackEntry,
//                    factory = factory
//                )

                val factory = remember(backStackEntry){
                    WelcomeViewModelFactory(
                                appContainer.getAllMenuUseCase,
                                appContainer.getCurrentUserUseCase,
                                appContainer.logoutUserUseCase
                    )
                }

                val viewModel: WelcomeViewModel = viewModel(
                    viewModelStoreOwner =  backStackEntry,
                    factory = factory

                )

                WelcomeScreen(
                    viewModel = viewModel,
                    onLogoutSuccess = {
                        navController.navigate(Screen.AuthGraph){
                            popUpTo(Screen.MainAppGraph) { inclusive = true }
                        }
                    },
                    onMenuSelected = { id, name, collection ->

                        if (collection == "subcategory")
                        {
                            navController.navigate(
                                Screen.SubCategoriesRoute(
                                    categoryId = id,
                                    categoryName = name
                                )
                            )
                        }
                        else if (collection == "checklist")
                        {
                            navController.navigate(
                                Screen.CheckListRoute
                            )
                        }
                    },
                    onCheckListFeatureSelected =  {id, name ->

                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable<Screen.SubCategoriesRoute> { backstackEntry ->

                val args: Screen.SubCategoriesRoute = backstackEntry.toRoute()

                val factory = remember(backstackEntry) {
                    SubCategoryViewModelFactory(appContainer.getSubCategoryByCategoryIdUseCase)
                }

                val viewModel: SubCategoryViewModel = viewModel(
                    viewModelStoreOwner = backstackEntry,
                    factory = factory
                )

                SubCategoryScreen(
                    categoryId = args.categoryId,
                    categoryName = args.categoryName,
                    viewModel = viewModel,
                    onSubCategorySelected = { id, name ->
                        navController.navigate(
                            Screen.OrientationsRoute(
                                subCategoryId = id,
                                subCategoryName = name
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp)
                )
            }

            composable<Screen.CheckListRoute> { backStackEntry ->

                val factory = remember(backStackEntry) {
                    CheckListViewModelFactory(appContainer.getAllCheckListsUseCase)
                }

                val viewModel: CheckListViewModel = viewModel(
                    viewModelStoreOwner = backStackEntry,
                    factory = factory
                )

                CheckListScreen(
                    onAddCheckListSelected = {
                        navController.navigate(Screen.AddCheckListRoute)
                    },
                    onCheckListSelected = { checkListId, checkListSubCategoryId ->
                        navController.navigate(

                            Screen.ProgressCheckListRoute(
                                checkListId = checkListId,
                                checkListSubCategoryId = checkListSubCategoryId
                            )
                        )
                    },
                    viewModel = viewModel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp)
                )
            }

            composable<Screen.OrientationsRoute> { backstackEntry ->

                val args: Screen.OrientationsRoute = backstackEntry.toRoute()

                val factory = remember(backstackEntry) {
                    OrientationViewModelFactory(
                        appContainer.getOrientationsBySubCategoryIdUseCase,
                        appContainer.toggleOrientationUseCase
                    )
                }

                val viewModel: OrientationViewModel = viewModel(
                    viewModelStoreOwner = backstackEntry,
                    factory = factory
                )

                OrientationScreen(
                    subCategoryId = args.subCategoryId,
                    viewModel = viewModel,
                    onOrientationSelected = { id, name ->
                        navController.navigate(
                            Screen.OrientationItemRoute(
                                orientationId = id
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp)
                )
            }

            composable<Screen.OrientationItemRoute> { backStackEntry ->

                val args: Screen.OrientationItemRoute = backStackEntry.toRoute()

                val factory = remember(backStackEntry) {
                    OrientationItemViewModelFactory(appContainer.loadOrientationByIdUseCase)
                }

                val viewModel: OrientationItemViewModel = viewModel(
                    viewModelStoreOwner = backStackEntry,
                    factory = factory
                )

                OrientationItemScreen(
                    orientationId = args.orientationId,
                    viewModel = viewModel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp)
                )
            }

            composable<Screen.AddCheckListRoute> { backStackEntry ->

                val factory = remember(backStackEntry){
                    AddCheckListViewModelFactory(appContainer.addCheckListUseCase)
                }

                val viewModel: AddCheckListViewModel = viewModel(
                    viewModelStoreOwner = backStackEntry,
                    factory = factory
                )

                AddCheckListScreen(
                    onAddCheckListSuccess = {
                        navController.popBackStack()
                    },
                    viewModel = viewModel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp)
                )
            }

            composable<Screen.ProgressCheckListRoute> { backStackEntry ->

                val args: Screen.ProgressCheckListRoute = backStackEntry.toRoute()

                val factory = remember(backStackEntry){
                    ProgressCheckListViewModelFactory(
                        appContainer.getOrientationsByUserChecklistUseCase,
                        appContainer.toggleCheckListOrientationUseCase)
                }

                val viewModel: ProgressCheckListViewModel = viewModel(
                    viewModelStoreOwner = backStackEntry,
                    factory = factory
                )

                ProgressCheckListScreen(
                    checkListId = args.checkListId,
                    subCategoryId = args.checkListSubCategoryId,
                    viewModel = viewModel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp)
                )
            }
        }
    }
}