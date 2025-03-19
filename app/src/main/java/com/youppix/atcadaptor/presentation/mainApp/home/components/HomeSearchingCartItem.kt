package com.youppix.atcadaptor.presentation.mainApp.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.youppix.atcadaptor.common.Dimens.ExtraSmallPadding
import com.youppix.atcadaptor.common.Dimens.MediumPadding
import com.youppix.atcadaptor.common.Dimens.SmallPadding
import com.youppix.atcadaptor.domain.model.home.HomeSearchItemData
import com.youppix.atcadaptor.presentation.ui.theme.ATCAdaptorTheme


@Composable
fun HomeSearchingCartItem(
    modifier: Modifier = Modifier,
    homeSearchItemData: HomeSearchItemData,
    userType: Int,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(SmallPadding)
            .clickable { onClick() },
        shape = RoundedCornerShape(SmallPadding),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = MediumPadding )
                .padding(bottom = SmallPadding)
        ) {
            if (userType == 1) {
                // Doctors can see patient info
                Text(
                    text = homeSearchItemData.patientName ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "ID: ${homeSearchItemData.patientId}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            // Medication & Dose (visible to both doctors & patients)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Medication, contentDescription = "Medication")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${homeSearchItemData.medicationName} - ${homeSearchItemData.dose}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            if (userType == 1) {
                Spacer(modifier = Modifier.height(8.dp))

                // AUC & DFG (Doctors only)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "AUC: ${homeSearchItemData.auc}", fontWeight = FontWeight.Medium)
                    Text(
                        text = "DFG: ${homeSearchItemData.dfg} mL/min",
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Toxicity Alert (Doctors only)
                if (homeSearchItemData.toxicity?.isNotEmpty() == true) {
                    Text(
                        text = "⚠️ Toxicité : ${homeSearchItemData.toxicity}",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun HomeSearchingCartItemPreview() {
    ATCAdaptorTheme {
        HomeSearchingCartItem(
            homeSearchItemData = HomeSearchItemData(

                patientName = "John Doe",
                patientId = 33,
                medicationName = "Carboplatine",
                dose = "400 mg",
                auc = "5",
                dfg = "60",
                toxicity = "None"
            ),
            userType = 1,
            onClick = {}
        )
    }

}
