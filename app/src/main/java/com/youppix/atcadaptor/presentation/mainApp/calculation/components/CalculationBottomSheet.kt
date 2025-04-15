package com.youppix.atcadaptor.presentation.mainApp.calculation.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youppix.atcadaptor.R
import com.youppix.atcadaptor.common.Constant.APP_ENTRY
import com.youppix.atcadaptor.common.Constant.format
import com.youppix.atcadaptor.common.Dimens.BottomBarHeight
import com.youppix.atcadaptor.common.Dimens.ExtraSmallPadding
import com.youppix.atcadaptor.common.Dimens.LargePadding
import com.youppix.atcadaptor.common.Dimens.MediumPadding
import com.youppix.atcadaptor.common.Dimens.SmallPadding
import com.youppix.atcadaptor.domain.model.calculations.CalculationHistoryData
import com.youppix.atcadaptor.presentation.components.CustomTextField
import com.youppix.atcadaptor.presentation.mainApp.calculation.CalculationEvent
import com.youppix.atcadaptor.presentation.mainApp.calculation.CalculationInputState
import com.youppix.atcadaptor.presentation.mainApp.calculation.CalculationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculationBottomSheet(
    modifier: Modifier = Modifier,
    inputState: CalculationInputState,
    state: CalculationState,
    event: (CalculationEvent) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    val context = LocalContext.current
    val userId = context.getSharedPreferences(APP_ENTRY, 0).getString("userId", "")

    ModalBottomSheet(
        modifier = modifier
            .fillMaxSize()
            .padding(top = BottomBarHeight),
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



            AnimatedVisibility(!imeVisible) {
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(ExtraSmallPadding),

                    shape = RoundedCornerShape(SmallPadding),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)


                ) {
                    val (recommendation, recommendedDose) = state.recommendationAndDose
                    Log.d("CalcultaionBottomSheet", "recommendation :" + recommendation)
                    Column(
                        modifier = Modifier.padding(MediumPadding)
                    ) {
                        ResultRow(
                            label = "Surface corporelle (SC)",
                            value = "${inputState.sc.format(2)} m²"
                        )
                        ResultRow(
                            label = "DFG calculé",
                            value = "${inputState.dfgCalcule.format(2)} mL/min"
                        )
                        ResultRow(
                            label = "Dose de carboplatine",
                            value = "${inputState.doseCarboplatine.format(2)} mg"
                        )
                        ResultRow(
                            label = "Recommandation selon la ligne directrice (RCP) :",
                            value = recommendation
                        )
                        ResultRow(
                            label = "Dose individuelle recommandée: ",
                            value = "${recommendedDose.format(2)} mg"
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(MediumPadding))

            CustomTextField(
                modifier = Modifier.padding(SmallPadding),
                value = state.comment,
                onValueChange = {
                    event(CalculationEvent.OnCommentValueChange(it))
                },
                label = "commentaire",
                trailingIcon = {},
                placeholder = "laissez un commentaire",
                isError = false,
                errorMessage = ""
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SmallPadding)
            ) {

            }

            Spacer(modifier = Modifier.height(MediumPadding))

            Button(
                onClick = {
                    inputState.apply {
                        event(
                            CalculationEvent.SaveCalculation(
                                context,
                                CalculationHistoryData(
                                    user_id = 1,
                                    calculated_by = if (userId.isNullOrEmpty()) 0 else userId.toInt(),
                                    nom_de_medicament = nomDeMedicament,
                                    age = age.toInt(),
                                    poids = poids,
                                    taille = taille,
                                    genre = genre.name,
                                    race = race.name,
                                    creatinine = creatinine,
                                    alat = alat,
                                    asat = asat,
                                    pal = pal,
                                    tbil = tbil,
                                    dfg_type = dfgType.name,
                                    dfg = dfg,
                                    dfg_calcule = dfgCalcule,
                                    auc_cible = aucCible,
                                    dose_carboplatine = doseCarboplatine,
                                    dose_par_m2 = doseParM2,
                                    toxicite_renale = if(toxiciteRenale) 1 else 0,
                                    toxicite_hepatique = if(toxiciteHepatique) 1 else 0,
                                    necessite_dialyse = if(necessiteDialyse) 1 else 0,
                                    sc = sc,
                                    recommandation = state.recommendationAndDose.first,
                                    dose_recommandee = state.recommendationAndDose.second.toString(),
                                    comment = state.comment
                                )
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MediumPadding, vertical = SmallPadding)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = stringResource(R.string.save), fontSize = 16.sp, color = Color.White)
            }

            Button(
                onClick = { onDismissRequest() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MediumPadding)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = stringResource(R.string.close), fontSize = 16.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(BottomBarHeight))


        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResultRow(label: String, value: String) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Medium, fontSize = 16.sp)
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End
        )
    }
}

