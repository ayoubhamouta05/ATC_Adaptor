package com.youppix.atcadaptor.presentation.mainApp.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.youppix.atcadaptor.common.Dimens.BottomBarHeight
import com.youppix.atcadaptor.common.Dimens.MediumPadding
import com.youppix.atcadaptor.common.Dimens.SmallPadding
import com.youppix.atcadaptor.common.UserRole
import com.youppix.atcadaptor.presentation.components.CustomCircularProgress
import com.youppix.atcadaptor.presentation.components.CustomTopAppBar

class DetailsScreen(
    private val userRole: UserRole,
    private val medicationName: String,
    private val patientId: Int?,
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<DetailsViewModel>()
        val state by viewModel.state

        LaunchedEffect(Unit) {
            viewModel.onEvent(DetailsEvent.GetDetails(medicationName, patientId))
        }

        Scaffold(
            topBar = {
                CustomTopAppBar(
                    title = "Détails",
                    isArabic = false
                ) {
                    navigator.pop()
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                if (state.isLoading) {
                    CustomCircularProgress(state.isLoading)
                } else {
                    // Patient Information (Visible for Doctors Only)
                    if (userRole == UserRole.Doctor) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(SmallPadding),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(horizontal = MediumPadding)
                                .padding(vertical = SmallPadding)) {
                                Text(
                                    "👤 Informations du Patient",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(bottom = SmallPadding)

                                )
                                Text("🆔 ID : ${state.detailsData?.patientId}")
                                Text("📛 Nom : ${state.detailsData?.name}")
                                Text("🎂 Âge : ${state.detailsData?.age} ans")
                                Text("⚖️ Poids : ${state.detailsData?.poids} kg")
                                Text("📏 Taille : ${state.detailsData?.taille} cm")
                                Text("🚻 Genre : ${state.detailsData?.genre}")
                                Text("🌎 Ethnicité : ${state.detailsData?.race}")
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Medication Details (Always Visible)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(SmallPadding),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(horizontal = MediumPadding).
                        padding(vertical = SmallPadding)) {
                            Text(
                                "💊 Détails du Médicament",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(bottom = SmallPadding)
                            )
                            Text("🏥 Médicament : ${state.detailsData?.medicationName}")
                            Text("💉 Dose : ${state.detailsData?.dose} mg")
                            Text("🔬 Cible AUC : ${state.detailsData?.auc}")
                            Text("🚨 Toxicité Rénale : ${state.detailsData?.toxicityRenal}")
                            Text("🚨 Toxicité Hépatique : ${state.detailsData?.toxicityHepatic}")
                            Text("🩺 Dialyse Requise : ${if (state.detailsData?.dialysisRequired == 1) "Oui" else "Non"}")

                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Lab Results (Visible for Doctors Only)
                    if (userRole == UserRole.Doctor) {
                        Card(
                            modifier = Modifier.fillMaxWidth()
                                .padding(bottom = SmallPadding + BottomBarHeight),
                            shape = RoundedCornerShape(SmallPadding),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(horizontal = MediumPadding)
                                .padding(vertical = SmallPadding)) {
                                Text(
                                    "🧪 Résultats de Laboratoire",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(bottom = SmallPadding)
                                )
                                Text("🩸 Créatinine : ${state.detailsData?.creatinine} mg/dL")
                                Text("💧 DFG (Débit de Filtration Glomérulaire) : ${state.detailsData?.dfg} mL/min")
                                Text("🧬 ALAT (TGP) : ${state.detailsData?.alat} UI/L")
                                Text("🧬 ASAT (TGO) : ${state.detailsData?.asat} UI/L")
                                Text("🦴 PAL (Phosphatases Alcalines) : ${state.detailsData?.pal} UI/L")
                                Text("🩸 Bilirubine Totale : ${state.detailsData?.tbil} mg/L")
                            }
                        }
                    }
                }
            }
        }
    }
}