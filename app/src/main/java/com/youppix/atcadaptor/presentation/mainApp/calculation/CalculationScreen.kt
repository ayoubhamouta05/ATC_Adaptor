package com.youppix.atcadaptor.presentation.mainApp.calculation

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.youppix.atcadaptor.R
import com.youppix.atcadaptor.common.Constant
import com.youppix.atcadaptor.common.DFGType
import com.youppix.atcadaptor.common.Dimens.BottomBarHeight
import com.youppix.atcadaptor.common.Dimens.LargePadding
import com.youppix.atcadaptor.common.Dimens.MediumPadding
import com.youppix.atcadaptor.common.Dimens.SmallPadding
import com.youppix.atcadaptor.common.Genre
import com.youppix.atcadaptor.common.Race
import com.youppix.atcadaptor.presentation.components.CustomCircularProgress
import com.youppix.atcadaptor.presentation.components.CustomTextField
import com.youppix.atcadaptor.presentation.components.CustomTopAppBar
import com.youppix.atcadaptor.presentation.mainApp.calculation.components.BlockSeparator
import com.youppix.atcadaptor.presentation.mainApp.calculation.components.CalculationBottomSheet
import com.youppix.atcadaptor.presentation.mainApp.calculation.components.DropdownMenuLabel
import com.youppix.atcadaptor.presentation.mainApp.home.HomeScreen

class CalculationScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: CalculationViewModel = navigator.getNavigatorScreenModel()
        val inputState by viewModel.inputState
        val state by viewModel.state
        val context = LocalContext.current

        val userType = context.getSharedPreferences(Constant.APP_ENTRY, 0)
            .getString("userType", "0")

        LaunchedEffect(Unit) {
            viewModel.onEvent(CalculationEvent.GetMedicament)
        }

        LaunchedEffect(state.error) {
            if (state.error != null) {
                Toast.makeText(context, "error : " + state.error, Toast.LENGTH_SHORT).show()
                viewModel.onEvent(CalculationEvent.InitializeErrorMessage)
            }
        }

        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                CustomTopAppBar(
                    title = if (userType == "0") stringResource(R.string.calculationHistory) else stringResource(
                        R.string.calculate
                    ),
                    isArabic = false
                ) {
                    if (navigator.canPop) {
                        navigator.pop()
                    } else {
                        navigator.replace(HomeScreen())
                    }
                }
            }) { innerPadding ->
            if (userType == "1"){
                if (!state.isLoading) {
                    AnimatedVisibility(true) {
                        MedicationCalculationScreen(
                            modifier = Modifier
                                .padding(innerPadding)
                                .padding(top = SmallPadding),
                            state = state,
                            inputState = inputState,
                            inputEvent = viewModel::onInputEvent,
                            event = viewModel::onEvent
                        )
                    }

                } else {
                    CustomCircularProgress(state.isLoading)
                }
            }else {
                Text("History" ,modifier = Modifier.padding(innerPadding))
            }


            if (state.showBottomSheet) {
                CalculationBottomSheet(
                    modifier = Modifier,
                    inputState = inputState,
                    event = viewModel::onEvent,
                    state = state
                ) {
                    viewModel.onEvent(CalculationEvent.ToggleShowBottomSheet)
                }
            }
        }


    }
}

@Composable
fun MedicationCalculationScreen(
    modifier: Modifier = Modifier,
    state: CalculationState,
    inputState: CalculationInputState,
    inputEvent: (CalculationInputEvent) -> Unit,
    event: (CalculationEvent) -> Unit,
) {

    val showPatientData = remember { mutableStateOf(false) }
    val showLaboData = remember { mutableStateOf(false) }
    val showMedicalData = remember { mutableStateOf(false) }


    LazyColumn(
        modifier = modifier
    ) {

        item {
            Text(
                "Nom du Médicament Anticancéreux ",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .padding(horizontal = LargePadding)
                    .offset(y = 5.dp)
            )
        }
        item {
            DropdownMenuLabel(
                modifier = Modifier.padding(horizontal = SmallPadding),
                name = inputState.nomDeMedicament.ifEmpty { "Sélectionnez un médicament" },
                menu = state.medicamentsList.map {
                    it.medicaments_nom
                },
                isError = false,
                expanded = state.dropNomDeMedicamentMenu,
                selectedItem = inputState.nomDeMedicament,
                onSelectItemClick = {
                    inputEvent(CalculationInputEvent.OnNomDeMedicamentChanged((it)))
                },
                onDismissRequest = {
                    event(CalculationEvent.ToggleNomDeMedicament)
                },
                errorMessage = "",
                onClick = {
                    event(CalculationEvent.ToggleNomDeMedicament)
                }
            )
        }

        item {
            BlockSeparator(modifier = Modifier
                .padding(horizontal = SmallPadding)
                .offset(y = (-5).dp),
                showItems = showPatientData.value,
                sectionTitle = "Données du Patient",
                onClick = {
                    showPatientData.value = !showPatientData.value
                }) {
                Column(modifier = Modifier.padding(horizontal = SmallPadding)) {
                    CustomTextField(
                        modifier = Modifier.padding(bottom = SmallPadding),
                        value = inputState.age,
                        onValueChange = {
                            inputEvent(CalculationInputEvent.OnAgeChanged(it))
                        },
                        label = "Âge (années)",
                        placeholder = "",
                        trailingIcon = {},
                        isError = !inputState.ageError.isNullOrEmpty(),
                        errorMessage = inputState.ageError ?: ""
                    )

                    CustomTextField(
                        modifier = Modifier.padding(bottom = SmallPadding),
                        value = inputState.poids,
                        onValueChange = {
                            inputEvent(CalculationInputEvent.OnPoidsChanged(it))
                        },
                        label = "Poids (Kg)",
                        placeholder = "",
                        trailingIcon = {},
                        isError = !inputState.poidsError.isNullOrEmpty(),
                        errorMessage = inputState.poidsError ?: ""
                    )

                    CustomTextField(
                        modifier = Modifier.padding(bottom = SmallPadding),
                        value = inputState.taille,
                        onValueChange = {
                            inputEvent(CalculationInputEvent.OnTailleChanged(it))
                        },
                        label = "Taille (cm)",
                        placeholder = "",
                        trailingIcon = {},
                        isError = !inputState.tailleError.isNullOrEmpty(),
                        errorMessage = inputState.tailleError ?: ""
                    )



                    Text(
                        "Genre :",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier
                            .padding(horizontal = SmallPadding)
                            .offset(y = 5.dp)
                    )

                    DropdownMenuLabel(
                        name = inputState.genre.name.ifEmpty { "Genre" },
                        menu = Genre.entries,
                        isError = false,
                        expanded = state.dropGenreMenu,
                        selectedItem = inputState.genre,
                        onSelectItemClick = {
                            inputEvent(CalculationInputEvent.OnGenreChanged(if ((it).uppercase() == Genre.Homme.name.uppercase()) Genre.Homme else Genre.Femme))
                        },
                        onDismissRequest = {
                            event(CalculationEvent.ToggleGenreDropMenu)
                        },
                        errorMessage = "",
                        onClick = {
                            event(CalculationEvent.ToggleGenreDropMenu)
                        }
                    )

                    Text(
                        "Race :",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier
                            .padding(horizontal = SmallPadding)
                            .offset(y = (-7).dp)
                    )

                    DropdownMenuLabel(
                        modifier = Modifier.offset(y = (-5).dp),
                        name = inputState.race.name,
                        menu = Race.entries,
                        isError = false,
                        expanded = state.dropRaceMenu,
                        selectedItem = inputState.race,
                        onSelectItemClick = {
                            inputEvent(CalculationInputEvent.OnRaceChanged(if (it == Race.Afro_Americain.name) Race.Afro_Americain else Race.Non_Afro_Americain))
                        },
                        onDismissRequest = {
                            event(CalculationEvent.ToggleRaceDropMenu)
                        },
                        errorMessage = "",
                        onClick = {
                            event(CalculationEvent.ToggleRaceDropMenu)
                        }
                    )

                }

            }
        }


        item {
            BlockSeparator(modifier = Modifier
                .padding(horizontal = SmallPadding)
                .offset(y = (-5).dp),
                showItems = showLaboData.value,
                sectionTitle = "Données de Laboratoire",
                onClick = {
                    showLaboData.value = !showLaboData.value
                }) {
                Column(modifier = Modifier.padding(horizontal = SmallPadding)) {

                    CustomTextField(
                        modifier = Modifier.padding(bottom = SmallPadding),
                        value = inputState.creatinine,
                        onValueChange = {
                            inputEvent(CalculationInputEvent.OnCreatinineChanged(it))
                        },
                        label = "Créatinine [mg/dL]",
                        placeholder = "",
                        trailingIcon = {},
                        isError = !inputState.creatinineError.isNullOrEmpty(),
                        errorMessage = inputState.creatinineError ?: ""
                    )

                    Text(
                        "Type De Dfg :",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier
                            .padding(horizontal = SmallPadding)
                            .offset(y = 5.dp)
                    )

                    DropdownMenuLabel(
                        name = inputState.dfgType.name,
                        menu = DFGType.entries,
                        isError = false,
                        expanded = state.dropDfgTypeMenu,
                        selectedItem = inputState.dfgType,
                        onSelectItemClick = {
                            inputEvent(CalculationInputEvent.OnDfgTypeChanged(if (it == DFGType.MDRD.name) DFGType.MDRD else DFGType.CG))
                        },
                        onDismissRequest = {
                            event(CalculationEvent.ToggleDfgTypeDropMenu)
                        },
                        errorMessage = "",
                        onClick = {
                            event(CalculationEvent.ToggleDfgTypeDropMenu)
                        }
                    )


                    CustomTextField(
                        modifier = Modifier.padding(bottom = SmallPadding),
                        value = inputState.dfg,
                        onValueChange = {
                            inputEvent(CalculationInputEvent.OnDfgChanged(it))
                        },
                        label = "DFG [mL/min] (si disponible)",
                        placeholder = "",
                        trailingIcon = {},
                        isError = !inputState.dfgError.isNullOrEmpty(),
                        errorMessage = inputState.dfgError ?: ""
                    )

                    CustomTextField(
                        modifier = Modifier.padding(bottom = SmallPadding),
                        value = inputState.alat,
                        onValueChange = {
                            inputEvent(CalculationInputEvent.OnAlatChanged(it))
                        },
                        label = "ALAT [UI/L]",
                        placeholder = "",
                        trailingIcon = {},
                        isError = !inputState.alatError.isNullOrEmpty(),
                        errorMessage = inputState.alatError ?: ""
                    )
                    CustomTextField(
                        modifier = Modifier.padding(bottom = SmallPadding),
                        value = inputState.asat,
                        onValueChange = {
                            inputEvent(CalculationInputEvent.OnAsatChanged(it))
                        },
                        label = "ASAT [UI/L]",
                        placeholder = "",
                        trailingIcon = {},
                        isError = !inputState.asatError.isNullOrEmpty(),
                        errorMessage = inputState.asatError ?: ""
                    )

                    CustomTextField(
                        modifier = Modifier.padding(bottom = SmallPadding),
                        value = inputState.pal,
                        onValueChange = {
                            inputEvent(CalculationInputEvent.OnPalChanged(it))
                        },
                        label = "PAL [UI/L]",
                        placeholder = "",
                        trailingIcon = {},
                        isError = !inputState.palError.isNullOrEmpty(),
                        errorMessage = inputState.palError ?: ""
                    )

                    CustomTextField(
                        modifier = Modifier.padding(bottom = SmallPadding),
                        value = inputState.tbil,
                        onValueChange = {
                            inputEvent(CalculationInputEvent.OnTbilChanged(it))
                        },
                        label = "T.Bil [mg/L]",
                        placeholder = "",
                        trailingIcon = {},
                        isError = !inputState.tbilError.isNullOrEmpty(),
                        errorMessage = inputState.tbilError ?: ""
                    )

                }
            }
        }


        item {
            BlockSeparator(modifier = Modifier
                .padding(horizontal = SmallPadding)
                .offset(y = -(5.dp)),
                showItems = showMedicalData.value,
                sectionTitle = "Données du Médicament",
                onClick = {
                    showMedicalData.value = !showMedicalData.value
                }) {
                Column(modifier = Modifier.padding(horizontal = SmallPadding)) {

                    CustomTextField(
                        modifier = Modifier.padding(bottom = SmallPadding),
                        value = inputState.doseParM2,
                        onValueChange = {
                            inputEvent(CalculationInputEvent.OnDoseParM2Changed(it))
                        },
                        label = "Dose [mg/m²]",
                        placeholder = "",
                        trailingIcon = {},
                        isError = !inputState.doseParM2Error.isNullOrEmpty(),
                        errorMessage = inputState.doseParM2Error ?: ""
                    )


                    CustomTextField(
                        modifier = Modifier.padding(bottom = SmallPadding),
                        value = inputState.aucCible,
                        onValueChange = {
                            inputEvent(CalculationInputEvent.OnAucCibleChanged(it))
                        },
                        label = "AUC cible [mg-min/L]",
                        placeholder = "",
                        trailingIcon = {},
                        isError = !inputState.aucCibleError.isNullOrEmpty(),
                        errorMessage = inputState.aucCibleError ?: ""
                    )

                    Text(
                        "Toxicité rénale :",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier
                            .padding(horizontal = SmallPadding)
                            .offset(y = 5.dp)
                    )

                    DropdownMenuLabel(
                        name = if (inputState.toxiciteRenale) "Oui" else "Non",
                        menu = listOf("Oui", "Non"),
                        isError = false,
                        expanded = state.dropToxiciteRenaleMenu,
                        selectedItem = if (inputState.toxiciteRenale) "Oui" else "Non",
                        onSelectItemClick = {
                            inputEvent(CalculationInputEvent.OnToxiciteRenaleChanged(it == "Oui"))
                        },
                        onDismissRequest = {
                            event(CalculationEvent.ToggleToxiciteRenaleDropMenu)
                        },
                        errorMessage = "",
                        onClick = {
                            event(CalculationEvent.ToggleToxiciteRenaleDropMenu)
                        }
                    )

                    Text(
                        "Toxicité hépatique :",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier
                            .padding(horizontal = SmallPadding)
                            .offset(y = (-7).dp)
                    )
                    DropdownMenuLabel(
                        modifier = Modifier.offset(y = -(5.dp)),
                        name = if (inputState.toxiciteHepatique) "Oui" else "Non",
                        menu = listOf("Oui", "Non"),
                        isError = false,
                        expanded = state.dropToxiciteHepatiqueMenu,
                        selectedItem = if (inputState.toxiciteHepatique) "Oui" else "Non",
                        onSelectItemClick = {
                            inputEvent(CalculationInputEvent.OnToxiciteHepatiqueChanged(it == "Oui"))
                        },
                        onDismissRequest = {
                            event(CalculationEvent.ToggleToxiciteHepatiqueDropMenu)
                        },
                        errorMessage = "",
                        onClick = {
                            event(CalculationEvent.ToggleToxiciteHepatiqueDropMenu)
                        }
                    )

                    Text(
                        "Nécessité de dialyse :",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier
                            .padding(horizontal = SmallPadding)
                            .offset(y = (-15).dp)
                    )
                    DropdownMenuLabel(
                        modifier = Modifier.offset(y = -(SmallPadding)),
                        name = if (inputState.necessiteDialyse) "Oui" else "Non",
                        menu = listOf("Oui", "Non"),
                        isError = false,
                        expanded = state.dropDialyseMenu,
                        selectedItem = if (inputState.necessiteDialyse) "Oui" else "Non",
                        onSelectItemClick = {
                            inputEvent(CalculationInputEvent.OnNecessiteDialyseChanged(it == "Oui"))
                        },
                        onDismissRequest = {
                            event(CalculationEvent.ToggleDialyseDropMenu)
                        },
                        errorMessage = "",
                        onClick = {
                            event(CalculationEvent.ToggleDialyseDropMenu)
                        }
                    )

                }

            }
        }


        item {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = BottomBarHeight + MediumPadding)
            ) {
                Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {

                    event(CalculationEvent.OnCalculate)

//                    if (state.error == null)
//                        event(CalculationEvent.ToggleShowBottomSheet)

                }) {
                    Text("Calculer", fontWeight = FontWeight.Bold)
                }
            }
        }


    }

}




