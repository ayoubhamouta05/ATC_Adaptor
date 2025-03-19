package com.youppix.atcadaptor.presentation.mainApp.calculation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youppix.atcadaptor.common.Constant.format
import com.youppix.atcadaptor.common.Dimens.BottomBarHeight
import com.youppix.atcadaptor.common.Dimens.ExtraSmallPadding
import com.youppix.atcadaptor.common.Dimens.LargePadding
import com.youppix.atcadaptor.common.Dimens.MediumPadding
import com.youppix.atcadaptor.common.Dimens.SmallPadding
import com.youppix.atcadaptor.presentation.mainApp.calculation.CalculationInputState
import com.youppix.atcadaptor.presentation.mainApp.calculation.CalculationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculationBottomSheet(
    modifier : Modifier = Modifier,
    inputState: CalculationInputState,
    state: CalculationState ,
    onDismissRequest : ()-> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = modifier
            .fillMaxSize()
            .padding(top = BottomBarHeight ),
        onDismissRequest = { onDismissRequest() },
        containerColor = MaterialTheme.colorScheme.background,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SmallPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Résultats",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = MediumPadding)
            )

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(ExtraSmallPadding),

                shape = RoundedCornerShape(SmallPadding),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)



            ) {
                Column(
                    modifier = Modifier.padding(MediumPadding)
                ) {
                    ResultRow(label = "Surface corporelle (SC)", value = "${inputState.sc.format(2)} m²")
                    ResultRow(label = "DFG calculé", value = "${inputState.dfgCalcule.format(2)} mL/min")
                    ResultRow(
                        label = "Dose de carboplatine",
                        value = "${inputState.doseCarboplatine.format(2)} mg"
                    )
                    ResultRow(
                        label = "Recommandation selon la ligne directrice (RCP)",
                        value = state.recommendationAndDose.first
                    )
                    ResultRow(
                        label = "Dose individuelle recommandée",
                        value = "${state.recommendationAndDose.second.format(2)} mg"
                    )
                }
            }

            Spacer(modifier = Modifier.height(MediumPadding))

            Button(
                onClick = { onDismissRequest() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MediumPadding)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Fermer", fontSize = 16.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(BottomBarHeight))
        }
    }
}

@Composable
fun ResultRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Medium, fontSize = 16.sp)
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
    }
}

