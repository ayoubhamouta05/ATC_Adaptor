package com.youppix.atcadaptor.presentation.mainApp.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.youppix.atcadaptor.common.Dimens.MediumPadding
import com.youppix.atcadaptor.common.Dimens.SmallPadding
import com.youppix.atcadaptor.presentation.mainApp.details.DetailsState

@Composable
fun MedicationInformationCard(modifier : Modifier = Modifier, state: DetailsState){

    val details = state.detailsData

    if (details?.medicationName != null || details?.dose != null || details?.auc != null ||
        details?.toxicityRenal != null || details?.toxicityHepatic != null || details?.dialysisRequired != null) {

        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(SmallPadding),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = MediumPadding)
                    .padding(vertical = SmallPadding)
            ) {
                Text(
                    "💊 Détails du Médicament",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = SmallPadding)
                )
                details.medicationName?.let { Text("🏥 Médicament : $it") }
                details.dose?.let { Text("💉 Dose : $it mg") }
                details.auc?.let { Text("🔬 Cible AUC : $it") }
                details.toxicityRenal?.let { Text("🚨 Toxicité Rénale : $it") }
                details.toxicityHepatic?.let { Text("🚨 Toxicité Hépatique : $it") }
                details.dialysisRequired?.let {
                    Text("🩺 Dialyse Requise : ${if (it == 1) "Oui" else "Non"}")
                }
            }
        }
    }

//    Card(
//        modifier = modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(SmallPadding),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        Column(modifier = Modifier.padding(horizontal = MediumPadding).
//        padding(vertical = SmallPadding)) {
//            Text(
//                "💊 Détails du Médicament",
//                style = MaterialTheme.typography.bodyLarge.copy(
//                    fontWeight = FontWeight.Bold
//                ),
//                modifier = Modifier.padding(bottom = SmallPadding)
//            )
//            Text("🏥 Médicament : ${state.detailsData?.medicationName}")
//            Text("💉 Dose : ${state.detailsData?.dose} mg")
//            Text("🔬 Cible AUC : ${state.detailsData?.auc}")
//            Text("🚨 Toxicité Rénale : ${state.detailsData?.toxicityRenal}")
//            Text("🚨 Toxicité Hépatique : ${state.detailsData?.toxicityHepatic}")
//            Text("🩺 Dialyse Requise : ${if (state.detailsData?.dialysisRequired == 1) "Oui" else "Non"}")
//
//        }
//    }
}