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
fun PatientInformationCard(modifier : Modifier = Modifier, state: DetailsState){

    val patient = state.detailsData

    if (patient?.patientId != null || patient?.name != null || patient?.age != null ||
        patient?.poids != null || patient?.taille != null || patient?.genre != null || patient?.race != null) {

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
                    "👤 Informations du Patient",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = SmallPadding)
                )
                patient.patientId?.let { Text("🆔 ID : $it") }
                patient.name?.let { Text("📛 Nom : $it") }
                patient.age?.let { Text("🎂 Âge : $it ans") }
                patient.poids?.let { Text("⚖️ Poids : $it kg") }
                patient.taille?.let { Text("📏 Taille : $it cm") }
                patient.genre?.let { Text("🚻 Genre : $it") }
                patient.race?.let { Text("🌎 Ethnicité : $it") }
            }
        }
    }

//    Card(
//        modifier = modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(SmallPadding),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        Column(modifier = Modifier.padding(horizontal = MediumPadding)
//            .padding(vertical = SmallPadding)) {
//            Text(
//                "👤 Informations du Patient",
//                style = MaterialTheme.typography.bodyLarge.copy(
//                    fontWeight = FontWeight.Bold
//                ),
//                modifier = Modifier.padding(bottom = SmallPadding)
//
//            )
//            Text("🆔 ID : ${state.detailsData?.patientId}")
//            Text("📛 Nom : ${state.detailsData?.name}")
//            Text("🎂 Âge : ${state.detailsData?.age} ans")
//            Text("⚖️ Poids : ${state.detailsData?.poids} kg")
//            Text("📏 Taille : ${state.detailsData?.taille} cm")
//            Text("🚻 Genre : ${state.detailsData?.genre}")
//            Text("🌎 Ethnicité : ${state.detailsData?.race}")
//        }
//    }
}