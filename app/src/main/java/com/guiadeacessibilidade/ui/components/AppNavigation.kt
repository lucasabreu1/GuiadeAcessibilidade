package com.guiadeacessibilidade.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.guiadeacessibilidade.di.AppContainer
import com.guiadeacessibilidade.ui.CategoriesRoute
import com.guiadeacessibilidade.ui.SubCategoriesRoute
import com.guiadeacessibilidade.ui.factory.CategoryViewModelFactory
import com.guiadeacessibilidade.ui.factory.SubCategoryViewModelFactory
import com.guiadeacessibilidade.ui.viewmodel.CategoryViewModel
import com.guiadeacessibilidade.ui.viewmodel.SubCategoryViewModel

@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    appContainer: AppContainer
){
    val navController = rememberNavController()

    Scaffold(
        topBar =  {
            TopAppBar(
                title = { Text("Meu App de TCC") }
            )
        },
        bottomBar =  {

        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = CategoriesRoute,
            modifier = Modifier.padding(paddingValues)
        ){
            composable<CategoriesRoute> { backStackEntry ->

                val factory = remember(backStackEntry) {
                    CategoryViewModelFactory(appContainer.getAllCategoryUseCase)
                }

                val viewModel: CategoryViewModel = viewModel(
                    viewModelStoreOwner = backStackEntry,
                    factory = factory
                )

                CategoryScreen(
                    viewModel = viewModel,
                    onCategorySelected = { id, name ->
                        navController.navigate(
                            SubCategoriesRoute(
                                categoryId = id,
                                categoryName = name
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp)
                )
            }
            composable<SubCategoriesRoute> { backstackEntry ->

                val args: SubCategoriesRoute = backstackEntry.toRoute()

                val factory = remember(backstackEntry){
                    SubCategoryViewModelFactory(appContainer.getSubCategoryByCategoryIdUseCase)
                }

                val viewModel: SubCategoryViewModel = viewModel(
                    viewModelStoreOwner = backstackEntry,
                    factory = factory
                )

                SubCategoryScreen(
                    categoryId = args.categoryId,
                    viewModel = viewModel,
                    onSubCategorySelected = { id, name ->
                        navController.navigate(
                            SubCategoriesRoute(
                                categoryId = id,
                                categoryName = name
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp)
                )

//                Column {
//                    Text(subCategoryRoute.categoryId)
//                    Text(subCategoryRoute.categoryName)
//                }
            }
        }

    }
}

//@Composable
//fun MyComp(callback: () -> Unit)
//{
//    Column()
//    {
//        Button(onClick = {
//            callback()
//        }) {
//            Text("Component 1")
//        }
//    }
//}

@Composable
fun Hello(){
    Text(text="Lucas")
}