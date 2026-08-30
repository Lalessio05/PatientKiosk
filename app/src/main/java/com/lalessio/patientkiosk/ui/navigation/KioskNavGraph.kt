package com.lalessio.patientkiosk.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lalessio.patientkiosk.ui.patientCode.PatientCodeScreen
import com.lalessio.patientkiosk.ui.patientCode.PatientCodeUiState
import com.lalessio.patientkiosk.ui.patientCode.PatientCodeViewModel
import com.lalessio.patientkiosk.ui.question.QuestionScreen
import com.lalessio.patientkiosk.ui.question.QuestionViewModel
import com.lalessio.patientkiosk.ui.questionnaireList.QuestionnaireListScreen
import com.lalessio.patientkiosk.ui.questionnaireList.QuestionnaireListUiState
import com.lalessio.patientkiosk.ui.questionnaireList.QuestionnaireListViewModel
import com.lalessio.patientkiosk.ui.sources.SourcesScreen

@Composable
fun KioskNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.PATIENT_CODE_ROUTE,
        modifier = modifier
    )
    {
        composable(Routes.PATIENT_CODE_ROUTE) {
            val viewModel: PatientCodeViewModel = hiltViewModel()
            val uiState: PatientCodeUiState by viewModel.uiState.collectAsStateWithLifecycle()

            PatientCodeScreen(
                state = uiState,
                onCodeChange = viewModel::onCodeChange,
                onRandomCode = viewModel::onRandomCode,
                onForward = {
                    val code = viewModel.onForward()
                    if (code != null)
                        navController.navigate(Routes.questionnaireList(code))
                }
            )
        }
        composable(
            Routes.QUESTIONNAIRE_LIST_ROUTE,
            arguments = listOf(navArgument("patientCode") { type = NavType.StringType }),
        ) {
            val viewModel: QuestionnaireListViewModel = hiltViewModel()
            val uiState: QuestionnaireListUiState by viewModel.uiState.collectAsStateWithLifecycle()

            val patientCode: String = it.arguments?.getString("patientCode") ?: ""

            QuestionnaireListScreen(
                state = uiState,
                onQuestionnaireSelected = { id: String->
                    navController.navigate(Routes.question(patientCode, id))
                },
                onBack = { navController.popBackStack() },
                onSources = { navController.navigate(Routes.SOURCES_ROUTE) }
            )
        }
        composable(Routes.SOURCES_ROUTE) {
            val viewModel: QuestionnaireListViewModel = hiltViewModel()
            val uiState: QuestionnaireListUiState by viewModel.uiState.collectAsStateWithLifecycle()

            SourcesScreen(
                state = uiState,
                onBack = { navController.popBackStack() },
            )
        }
        composable(
            route = Routes.QUESTION_ROUTE,
            arguments = listOf(
                navArgument("patientCode") { type = NavType.StringType },
                navArgument("questionnaireId") { type = NavType.StringType },
            ),
        ) {
            val viewModel: QuestionViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            QuestionScreen(
                state = uiState,
                onAnswerSelected = viewModel::onAnswerSelected,
                onNext = {
                    if (uiState.isLastQuestion) {
                        //Qui andrà il risultato
                    } else viewModel.onNext()
                },
                onPrevious = {
                    if (uiState.currentIndex == 0) navController.popBackStack()
                    else viewModel.onPrevious()
                },
            )
        }
    }
}