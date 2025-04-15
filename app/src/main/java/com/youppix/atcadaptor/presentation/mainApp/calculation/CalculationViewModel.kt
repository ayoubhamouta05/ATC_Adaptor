package com.youppix.atcadaptor.presentation.mainApp.calculation

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.expandVertically
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.youppix.atcadaptor.R
import com.youppix.atcadaptor.common.Constant.format
import com.youppix.atcadaptor.common.DFGType
import com.youppix.atcadaptor.common.Genre
import com.youppix.atcadaptor.common.Race
import com.youppix.atcadaptor.common.Resource
import com.youppix.atcadaptor.domain.model.calculations.CalculationHistoryData
import com.youppix.atcadaptor.domain.useCases.calculation.CalculationUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CalculationViewModel @Inject constructor(
    private val calculationUseCases : CalculationUseCases
) : ScreenModel {

    private val _inputState = mutableStateOf(CalculationInputState(
        nomDeMedicament = "CARBOPLATINE",
        nomDeMedicamentError = null,

        age = "56",
        ageError = null,

        poids = "68.5",
        poidsError = null,

        taille = "172.4",
        tailleError = null,

        genre = Genre.Homme,

        race = Race.Non_Afro_Americain,

        creatinine = "1.2",
        creatinineError = null,

        dfg = "68.7",
        dfgError = null,

        dfgType = DFGType.CG,

        alat = "45.0",
        alatError = null,

        asat = "38.0",
        asatError = null,

        pal = "120.0",
        palError = null,

        tbil = "0.9",
        tbilError = null,

        doseParM2 = "150.0",
        doseParM2Error = null,

        aucCible = "5.0",
        aucCibleError = null,

        toxiciteRenale = false,

        toxiciteHepatique = false,

        necessiteDialyse = false,

        sc = "", // will be auto-calculated
        scError = null,

        dfgCalcule = "", // will be auto-calculated
        dfgCalculeError = null,

        doseCarboplatine = "", // will be auto-calculated
        doseCarboplatineError = null
    ))
//    private val _inputState = mutableStateOf(CalculationInputState())
    val inputState: State<CalculationInputState> = _inputState

    private val _state = mutableStateOf(CalculationState())
    val state: State<CalculationState> = _state

    private fun getMedicaments(){
        calculationUseCases.getMedicaments().onEach {result->
            when(result){
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false ,
                        error = result.message?:result.data?.message ?:"erreur"
                    )
                }
                is Resource.Successful -> {
                    _state.value = state.value.copy(
                        isLoading = false ,
                        error = null ,
                        medicamentsList = result.data?.data?: emptyList()
                    )
                }
            }

        }.launchIn(screenModelScope)
    }


    fun onInputEvent(event: CalculationInputEvent) {
        when (event) {
            is CalculationInputEvent.OnAgeChanged -> {
                val error = validateAge(event.value)
                _inputState.value = if (error == null) {
                    inputState.value.copy(age = event.value)
                } else {
                    inputState.value.copy(ageError = error)
                }
            }

            is CalculationInputEvent.OnAlatChanged -> {
                val error = validateALAT(event.value)
                _inputState.value = if (error == null) {
                    inputState.value.copy(alat = event.value)
                } else {
                    inputState.value.copy(alatError = error)
                }
            }

            is CalculationInputEvent.OnAsatChanged -> {
                val error = validateASAT(event.value)
                _inputState.value = if (error == null) {
                    inputState.value.copy(asat = event.value)
                } else {
                    inputState.value.copy(asatError = error)
                }
            }

            is CalculationInputEvent.OnAucCibleChanged -> {
                val error = validateAUC(event.value)
                _inputState.value = if (error == null) {
                    inputState.value.copy(aucCible = event.value)
                } else {
                    inputState.value.copy(aucCibleError = error)
                }
            }

            is CalculationInputEvent.OnCreatinineChanged -> {
                val error = validateCreatinine(event.value)
                _inputState.value = if (error == null) {
                    inputState.value.copy(creatinine = event.value)
                } else {
                    inputState.value.copy(creatinineError = error)
                }
            }

            is CalculationInputEvent.OnDfgCalculeChanged -> {
                val error = validateDFG(event.value)
                _inputState.value = if (error == null) {
                    inputState.value.copy(dfgCalcule = event.value)
                } else {
                    inputState.value.copy(dfgCalculeError = error)
                }
            }

            is CalculationInputEvent.OnDfgChanged -> {
                val error = validateDFG(event.value)
                _inputState.value = if (error == null) {
                    inputState.value.copy(dfg = event.value)
                } else {
                    inputState.value.copy(dfgError = error)
                }
            }

            is CalculationInputEvent.OnDfgTypeChanged -> {
                _inputState.value = inputState.value.copy(
                    dfgType = event.value
                )

                onEvent(CalculationEvent.ToggleDfgTypeDropMenu)
            }

            is CalculationInputEvent.OnDoseCarboplatineChanged -> {
                val error = validateDose(event.value)
                _inputState.value = if (error == null) {
                    inputState.value.copy(doseCarboplatine = event.value)
                } else {
                    inputState.value.copy(doseCarboplatineError = error)
                }
            }

            is CalculationInputEvent.OnDoseParM2Changed -> {
                val error = validateDose(event.value)
                _inputState.value = if (error == null) {
                    inputState.value.copy(doseParM2 = event.value)
                } else {
                    inputState.value.copy(doseParM2Error = error)
                }
            }

            is CalculationInputEvent.OnGenreChanged -> {
                _inputState.value =
                    inputState.value.copy(genre = event.value)
                onEvent(CalculationEvent.ToggleGenreDropMenu)
            }

            is CalculationInputEvent.OnNecessiteDialyseChanged -> {
                _inputState.value = inputState.value.copy(necessiteDialyse = event.value)
                onEvent(CalculationEvent.ToggleDialyseDropMenu)
            }

            is CalculationInputEvent.OnNomDeMedicamentChanged -> {
                _inputState.value =
                    inputState.value.copy(nomDeMedicament = event.value)
                onEvent(CalculationEvent.ToggleNomDeMedicament)
            }

            is CalculationInputEvent.OnPalChanged -> {
                val error = validatePAL(event.value)
                _inputState.value = if (error == null) {
                    inputState.value.copy(pal = event.value)
                } else {
                    inputState.value.copy(palError = error)
                }
            }

            is CalculationInputEvent.OnPoidsChanged -> {
                val error = validatePoids(event.value)
                _inputState.value = if (error == null) {
                    inputState.value.copy(poids = event.value)
                } else {
                    inputState.value.copy(poidsError = error)
                }
            }

            is CalculationInputEvent.OnRaceChanged -> {
                _inputState.value = inputState.value.copy(race = event.value)
                onEvent(CalculationEvent.ToggleRaceDropMenu)
            }

            is CalculationInputEvent.OnScChanged -> {
                _inputState.value = inputState.value.copy(sc = event.value)
            }

            is CalculationInputEvent.OnTailleChanged -> {
                val error = validateTaille(event.value)
                _inputState.value = if (error == null) {
                    inputState.value.copy(taille = event.value)
                } else {
                    inputState.value.copy(tailleError = error)
                }
            }

            is CalculationInputEvent.OnTbilChanged -> {
                val error = validateTBil(event.value)
                _inputState.value = if (error == null) {
                    inputState.value.copy(tbil = event.value)
                } else {
                    inputState.value.copy(tbilError = error)
                }
            }

            is CalculationInputEvent.OnToxiciteHepatiqueChanged -> {
                _inputState.value = inputState.value.copy(toxiciteHepatique = event.value)
                onEvent(CalculationEvent.ToggleToxiciteHepatiqueDropMenu)
            }

            is CalculationInputEvent.OnToxiciteRenaleChanged -> {
                _inputState.value = inputState.value.copy(toxiciteRenale = event.value)
                onEvent(CalculationEvent.ToggleToxiciteRenaleDropMenu)
            }
        }
    }

    private fun validateNomMedicament(value: String): String? {
        return if (!value.matches(Regex("^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]+\$"))) "Nom invalide" else null
    }

    private fun validateAge(value: String): String? {
        return if (!value.matches(Regex("^\\d{1,3}\$")) || value.toInt() !in 0..120) "Âge invalide" else null
    }

    private fun validatePoids(value: String): String? {
        return if (!value.matches(Regex("^\\d{1,3}(\\.\\d{1,2})?\$"))) "Poids invalide" else null
    }

    private fun validateTaille(value: String): String? {
        return if (!value.matches(Regex("^\\d{2,3}(\\.\\d{1,2})?\$"))) "Taille invalide" else null
    }

    private fun validateGenre(value: String): String? {
        return if (value != "Homme" && value != "Femme") "Genre invalide (Homme/Femme)" else null
    }

    private fun validateRace(value: String): String? {
        return if (value != "Afro-Américain" && value != "Non afro-américain") "Race invalide" else null
    }

    private fun validateDose(value: String): String? {
        return if (!value.matches(Regex("^\\d+(\\.\\d{1,2})?\$"))) "Dose invalide" else null
    }

    private fun validateAUC(value: String): String? {
        return if (!value.matches(Regex("^\\d+(\\.\\d{1,2})?\$"))) "AUC invalide" else null
    }

    private fun validateCreatinine(value: String): String? {
        return if (!value.matches(Regex("^\\d+(\\.\\d{1,2})?\$"))) "Créatinine invalide" else null
    }

    private fun validateDFG(value: String): String? {
        return if (!value.matches(Regex("^\\d+(\\.\\d{1,2})?\$"))) "DFG invalide" else null
    }

    private fun validateALAT(value: String): String? {
        return if (!value.matches(Regex("^\\d+(\\.\\d{1,2})?\$"))) "ALAT invalide" else null
    }

    private fun validateASAT(value: String): String? {
        return if (!value.matches(Regex("^\\d+(\\.\\d{1,2})?\$"))) "ASAT invalide" else null
    }

    private fun validatePAL(value: String): String? {
        return if (!value.matches(Regex("^\\d+(\\.\\d{1,2})?\$"))) "PAL invalide" else null
    }

    private fun validateTBil(value: String): String? {
        return if (!value.matches(Regex("^\\d+(\\.\\d{1,2})?\$"))) "T.Bil invalide" else null
    }

    fun onEvent(event: CalculationEvent) {
        when (event) {
            is CalculationEvent.InitializeErrorMessage -> {
                _state.value = state.value.copy(error = null)
            }

            is CalculationEvent.OnCalculate -> {
                if (canCalculate()) {
                    _state.value = state.value.copy(error = null)
                    calculer()
                    onEvent(CalculationEvent.ToggleShowBottomSheet)
                } else {
                    _state.value = state.value.copy(
                        error = "Veuillez remplir tous les champs correctement avant de calculer."
                    )
                }
            }

            CalculationEvent.ToggleDfgTypeDropMenu -> _state.value = state.value.copy(dropDfgTypeMenu = !state.value.dropDfgTypeMenu)
            CalculationEvent.ToggleDialyseDropMenu -> _state.value = state.value.copy(dropDialyseMenu = !state.value.dropDialyseMenu)
            CalculationEvent.ToggleGenreDropMenu -> _state.value = state.value.copy(dropGenreMenu = !state.value.dropGenreMenu)
            CalculationEvent.ToggleRaceDropMenu -> _state.value = state.value.copy(dropRaceMenu = !state.value.dropRaceMenu)
            CalculationEvent.ToggleToxiciteHepatiqueDropMenu -> _state.value = state.value.copy(dropToxiciteHepatiqueMenu = !state.value.dropToxiciteHepatiqueMenu)
            CalculationEvent.ToggleToxiciteRenaleDropMenu -> _state.value = state.value.copy(dropToxiciteRenaleMenu = !state.value.dropToxiciteRenaleMenu)
            CalculationEvent.ToggleShowBottomSheet -> _state.value = state.value.copy(showBottomSheet = !state.value.showBottomSheet)
            CalculationEvent.ToggleNomDeMedicament -> _state.value = state.value.copy(dropNomDeMedicamentMenu = !state.value.dropNomDeMedicamentMenu)
            CalculationEvent.GetMedicament -> {
                getMedicaments()
            }

            is CalculationEvent.OnCommentValueChange -> {
                _state.value = state.value.copy(
                    comment = event.value
                )
            }

            is CalculationEvent.SaveCalculation -> {
                saveCalculationToHistory(event.context , event.calculationHistoryData)
            }

            is CalculationEvent.ShowToast -> {
                Toast.makeText(event.context, event.message, Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun saveCalculationToHistory(context : Context, calculationHistoryData: CalculationHistoryData){
        calculationUseCases.addCalculationHistoryUseCase(calculationHistoryData).onEach {result->
            when(result){
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false ,
                        error = result.message?: result.data?.message
                    )
                }

                is Resource.Successful -> {
                    _state.value = state.value.copy(
                        isLoading = false ,
                        error = null
                    )

                    onEvent(CalculationEvent.ShowToast(context = context , message = context.getString(
                        R.string.calculationSaved)))
                }
            }
        }.launchIn(screenModelScope)
    }

    private fun calculer() {
        val sc = calculerSC(inputState.value.poids.toDouble(), inputState.value.taille.toDouble())
        val dfg = calculerDFG(
            method = inputState.value.dfgType,
            race = inputState.value.race,
            creatinine = inputState.value.creatinine.toDouble(),
            age = inputState.value.age.toInt(),
            poids = inputState.value.poids.toDouble(),
            genre = inputState.value.genre
        )
        val doseCarboplatine = calculerDoseCarboplatine(inputState.value.aucCible.toDouble(), dfg)

        _inputState.value = inputState.value.copy(
            sc = sc.format(2),
            dfgCalcule = dfg.format(2),
            doseCarboplatine = doseCarboplatine.format(2)
        )

        val recommendationAndDose = getRecommendationAndDose(
            dfg = dfg,
            sc = sc,
            aucCible = inputState.value.aucCible.toDouble(),
            toxiciteRenale = inputState.value.toxiciteRenale,
            toxiciteHepatique = inputState.value.toxiciteHepatique,
            necessiteDialyse = inputState.value.necessiteDialyse
        )

        _state.value = state.value.copy(
            recommendationAndDose = recommendationAndDose
        )
    }

    private fun calculerSC(poids: Double, taille: Double): Double {
        return if (poids > 0 && taille > 0) {
            Math.sqrt((poids * taille) / 3600)
        } else 0.0
    }

    private fun calculerDFG(
        method: DFGType,
        age: Int,
        poids: Double,
        creatinine: Double,
        genre: Genre,
        race: Race,
    ): Double {
        return when (method) {
            DFGType.CG -> {
                val facteurSexe = if (genre == Genre.Femme) 0.85 else 1.0
                ((140 - age) * poids) / (72 * creatinine) * facteurSexe
            }
            DFGType.MDRD -> {
                val facteurSexe = if (genre == Genre.Femme) 0.742 else 1.0
                val facteurRace = if (race == Race.Afro_Americain) 1.212 else 1.0
                175 * Math.pow(creatinine, -1.154) * Math.pow(age.toDouble(), -0.203) * facteurSexe * facteurRace
            }
        }
    }

    private fun calculerDoseCarboplatine(auc: Double, dfg: Double): Double {
        return if (auc > 0 && dfg > 0) auc * (dfg + 25) else 0.0
    }

    private fun canCalculate(): Boolean {
        val state = _inputState.value

        val requiredFieldsFilled =
            state.age.isNotBlank() && state.age != "0" &&
                    state.poids.isNotBlank() && state.poids != "0" &&
                    state.taille.isNotBlank() && state.taille != "0" &&
                    state.creatinine.isNotBlank() && state.creatinine != "0" &&
                    state.aucCible.isNotBlank() && state.aucCible != "0" &&
                    state.nomDeMedicament.isNotBlank()

        val noErrors =
            state.ageError == null &&
                    state.alatError == null &&
                    state.asatError == null &&
                    state.aucCibleError == null &&
                    state.creatinineError == null &&
                    state.dfgCalculeError == null &&
                    state.dfgError == null &&
                    state.doseCarboplatineError == null &&
                    state.doseParM2Error == null &&
                    state.nomDeMedicamentError == null &&
                    state.palError == null &&
                    state.poidsError == null &&
                    state.tailleError == null &&
                    state.tbilError == null &&
                    state.scError == null &&
                    _state.value.error == null

        return requiredFieldsFilled && noErrors
    }


    private fun getRecommendationAndDose(
        dfg: Double,
        sc: Double,
        aucCible: Double,
        toxiciteRenale: Boolean,
        toxiciteHepatique: Boolean,
        necessiteDialyse: Boolean,
    ): Pair<String, Double> {
        val recommendation: String
        var dose: Double

        if (necessiteDialyse) {
            recommendation = "Non Adaptée"
            dose = 0.0
        } else if (dfg < 30 || toxiciteRenale) {
            recommendation = "Non Adaptée"
            dose = 0.0
        } else {
            recommendation = "Adaptée"
            dose = aucCible * (dfg + 25) * sc

            if (toxiciteHepatique) {
                dose *= 0.8
            }
        }

        return Pair(recommendation, dose)
    }

}
