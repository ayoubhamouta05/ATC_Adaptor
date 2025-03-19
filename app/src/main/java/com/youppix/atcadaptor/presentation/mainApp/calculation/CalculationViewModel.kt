package com.youppix.atcadaptor.presentation.mainApp.calculation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import com.youppix.atcadaptor.common.Constant.format
import com.youppix.atcadaptor.common.DFGType
import com.youppix.atcadaptor.common.Genre
import com.youppix.atcadaptor.common.Race
import javax.inject.Inject

class CalculationViewModel @Inject constructor() : ScreenModel {

    private val _inputState = mutableStateOf(CalculationInputState())
    val inputState: State<CalculationInputState> = _inputState

    private val _state = mutableStateOf(CalculationState())
    val state: State<CalculationState> = _state

    fun onEvent(event: CalculationEvent) {
        when (event) {

            is CalculationEvent.InitializeErrorMessage ->{
                _state.value = state.value.copy(
                    error = null
                )
            }

            is CalculationEvent.OnCalculate -> {

                if (canCalculate()) {
                    _state.value = state.value.copy(
                        error = null
                    )
                    calculer()
                } else {
                    _state.value = state.value.copy(
                        error = "Veuillez remplir tous les champs correctement avant de calculer."
                    )
                }

            }

            CalculationEvent.ToggleDfgTypeDropMenu -> {
                _state.value = state.value.copy(
                    dropDfgTypeMenu = !state.value.dropDfgTypeMenu
                )
            }

            CalculationEvent.ToggleDialyseDropMenu -> {
                _state.value = state.value.copy(
                    dropDialyseMenu = !state.value.dropDialyseMenu
                )
            }

            CalculationEvent.ToggleGenreDropMenu -> {
                _state.value = state.value.copy(
                    dropGenreMenu = !state.value.dropGenreMenu
                )
            }

            CalculationEvent.ToggleRaceDropMenu -> {
                _state.value = state.value.copy(
                    dropRaceMenu = !state.value.dropRaceMenu
                )
            }

            CalculationEvent.ToggleToxiciteHepatiqueDropMenu -> {
                _state.value = state.value.copy(
                    dropToxiciteHepatiqueMenu = !state.value.dropToxiciteHepatiqueMenu
                )
            }

            CalculationEvent.ToggleToxiciteRenaleDropMenu -> {
                _state.value = state.value.copy(
                    dropToxiciteRenaleMenu = !state.value.dropToxiciteRenaleMenu
                )
            }

            CalculationEvent.ToggleShowBottomSheet -> {
                _state.value = state.value.copy(
                    showBottomSheet = !state.value.showBottomSheet
                )
            }

            CalculationEvent.ToggleNomDeMedicament -> {
                _state.value = state.value.copy(
                    dropNomDeMedicamentMenu = !state.value.dropNomDeMedicamentMenu
                )
            }
        }
    }

    fun onInputEvent(event: CalculationInputEvent) {
        when (event) {
            is CalculationInputEvent.OnDfgTypeChanged -> {
                _inputState.value = inputState.value.copy(
                    dfgType = event.value
                )
                _state.value = state.value.copy(
                    dropDfgTypeMenu = false
                )
            }

            is CalculationInputEvent.OnGenreChanged -> {
                _inputState.value =
                    inputState.value.copy(genre = event.value)

                _state.value = state.value.copy(
                    dropGenreMenu = false
                )

            }

            is CalculationInputEvent.OnNecessiteDialyseChanged -> {
                _inputState.value = inputState.value.copy(necessiteDialyse = event.value)
                _state.value = state.value.copy(
                    dropDialyseMenu = false
                )
            }

            is CalculationInputEvent.OnNomDeMedicamentChanged -> {
                _inputState.value = inputState.value.copy(nomDeMedicament = event.value)
                _state.value = state.value.copy(
                    dropNomDeMedicamentMenu = false
                )
            }

            is CalculationInputEvent.OnRaceChanged -> {
                _inputState.value = inputState.value.copy(race = event.value)
                _state.value = state.value.copy(
                    dropRaceMenu = false
                )
            }

            is CalculationInputEvent.OnScChanged -> {
                _inputState.value = inputState.value.copy(sc = event.value)
            }

            is CalculationInputEvent.OnTailleChanged -> {
                val filteredValue = event.value.filterIndexed { index, char ->
                    char.isDigit() || (char == '.' && index > 0 && event.value.count { it == '.' } <= 1)
                }

                _inputState.value = inputState.value.copy(
                    taille = filteredValue,
                    tailleError = if (filteredValue.isEmpty() || validateTaille(filteredValue) != null) "Taille invalide" else null
                )
            }

            is CalculationInputEvent.OnToxiciteHepatiqueChanged -> {
                _inputState.value = inputState.value.copy(toxiciteHepatique = event.value)
                _state.value = state.value.copy(
                    dropToxiciteHepatiqueMenu = false
                )
            }

            is CalculationInputEvent.OnToxiciteRenaleChanged -> {
                _inputState.value = inputState.value.copy(toxiciteRenale = event.value)
                _state.value = state.value.copy(
                    dropToxiciteRenaleMenu = false
                )
            }

            is CalculationInputEvent.OnAgeChanged -> {

                val filteredValue = event.value.filter { it.isDigit() } // Only allow numbers

                _inputState.value = inputState.value.copy(
                    age = filteredValue,
                    ageError = if (filteredValue.isEmpty() || validateAge(filteredValue) != null) "Age invalide" else null
                )

            }

            is CalculationInputEvent.OnPoidsChanged -> {
                val filteredValue = event.value.filterIndexed { index, char ->
                    char.isDigit() || (char == '.' && index > 0 && event.value.count { it == '.' } <= 1)
                }

                _inputState.value = inputState.value.copy(
                    poids = filteredValue,
                    poidsError = if (filteredValue.isEmpty() || validatePoids(filteredValue) != null) "Poids invalide" else null
                )
            }

            is CalculationInputEvent.OnDoseCarboplatineChanged -> {
                val filteredValue = event.value.filterIndexed { index, char ->
                    char.isDigit() || (char == '.' && index > 0 && event.value.count { it == '.' } <= 1)
                }

                _inputState.value = inputState.value.copy(
                    doseCarboplatine = filteredValue,
                    doseCarboplatineError = if (filteredValue.isEmpty() || validateDose(
                            filteredValue
                        ) != null
                    ) "Dose invalide" else null
                )
            }

            is CalculationInputEvent.OnDoseParM2Changed -> {
                val filteredValue = event.value.filterIndexed { index, char ->
                    char.isDigit() || (char == '.' && index > 0 && event.value.count { it == '.' } <= 1)
                }

                _inputState.value = inputState.value.copy(
                    doseParM2 = filteredValue,
                    doseParM2Error = if (filteredValue.isEmpty() || validateDose(filteredValue) != null) "Dose invalide" else null
                )
            }

            is CalculationInputEvent.OnAucCibleChanged -> {
                val filteredValue = event.value.filterIndexed { index, char ->
                    char.isDigit() || (char == '.' && index > 0 && event.value.count { it == '.' } <= 1)
                }

                _inputState.value = inputState.value.copy(
                    aucCible = filteredValue,
                    aucCibleError = if (filteredValue.isEmpty() || validateAUC(filteredValue) != null) "AUC invalide" else null
                )
            }

            is CalculationInputEvent.OnCreatinineChanged -> {
                val filteredValue = event.value.filterIndexed { index, char ->
                    char.isDigit() || (char == '.' && index > 0 && event.value.count { it == '.' } <= 1)
                }

                _inputState.value = inputState.value.copy(
                    creatinine = filteredValue,
                    creatinineError = if (filteredValue.isEmpty() || validateCreatinine(
                            filteredValue
                        ) != null
                    ) "Créatinine invalide" else null
                )
            }

            is CalculationInputEvent.OnDfgChanged -> {
                val filteredValue = event.value.filterIndexed { index, char ->
                    char.isDigit() || (char == '.' && index > 0 && event.value.count { it == '.' } <= 1)
                }

                _inputState.value = inputState.value.copy(
                    dfg = filteredValue,
                    dfgError = if (filteredValue.isEmpty() || validateDFG(filteredValue) != null) "DFG invalide" else null
                )
            }

            is CalculationInputEvent.OnDfgCalculeChanged -> {
                val filteredValue = event.value.filterIndexed { index, char ->
                    char.isDigit() || (char == '.' && index > 0 && event.value.count { it == '.' } <= 1)
                }

                _inputState.value = inputState.value.copy(
                    dfgCalcule = filteredValue,
                    dfgCalculeError = if (filteredValue.isEmpty() || validateDFG(filteredValue) != null) "DFG invalide" else null
                )
            }

            is CalculationInputEvent.OnTbilChanged -> {
                val filteredValue = event.value.filterIndexed { index, char ->
                    char.isDigit() || (char == '.' && index > 0 && event.value.count { it == '.' } <= 1)
                }

                _inputState.value = inputState.value.copy(
                    tbil = filteredValue,
                    tbilError = if (filteredValue.isEmpty() || validateTBil(filteredValue) != null) "T.Bil invalide" else null
                )
            }

            is CalculationInputEvent.OnAlatChanged -> {
                val filteredValue = event.value.filterIndexed { index, char ->
                    char.isDigit() || (char == '.' && index > 0 && event.value.count { it == '.' } <= 1)
                }

                _inputState.value = inputState.value.copy(
                    alat = filteredValue,
                    alatError = if (filteredValue.isEmpty() || validateALAT(filteredValue) != null) "ALAT invalide" else null
                )
            }

            is CalculationInputEvent.OnAsatChanged -> {
                val filteredValue = event.value.filterIndexed { index, char ->
                    char.isDigit() || (char == '.' && index > 0 && event.value.count { it == '.' } <= 1)
                }

                _inputState.value = inputState.value.copy(
                    asat = filteredValue,
                    asatError = if (filteredValue.isEmpty() || validateASAT(filteredValue) != null) "ASAT invalide" else null
                )
            }

            is CalculationInputEvent.OnPalChanged -> {
                val filteredValue = event.value.filterIndexed { index, char ->
                    char.isDigit() || (char == '.' && index > 0 && event.value.count { it == '.' } <= 1)
                }

                _inputState.value = inputState.value.copy(
                    pal = filteredValue,
                    palError = if (filteredValue.isEmpty() || validatePAL(filteredValue) != null) "PAL invalide" else null
                )
            }

        }

    }

    private fun validateAge(value: String): String? {
        if (value.isBlank()) return "Champ requis"
        return if (!value.matches(Regex("^\\d{1,3}\$")) || value.toInt() !in 0..120) "Âge invalide" else null
    }

    private fun validatePoids(value: String): String? {
        if (value.isEmpty()) return null // Permet d'effacer complètement
        return if (!value.matches(Regex("^\\d{1,3}(\\.\\d{0,2})?$"))) "Poids invalide" else null
    }

    private fun validateTaille(value: String): String? {
        if (value.isEmpty()) return null
        return if (!value.matches(Regex("^\\d{1,3}(\\.\\d{0,2})?$"))) "Taille invalide" else null
    }

    private fun validateDose(value: String): String? {
        if (value.isEmpty()) return null
        return if (!value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) "Dose invalide" else null
    }

    private fun validateAUC(value: String): String? {
        if (value.isEmpty()) return null
        return if (!value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) "AUC invalide" else null
    }

    private fun validateCreatinine(value: String): String? {
        if (value.isEmpty()) return null
        return if (!value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) "Créatinine invalide" else null
    }

    private fun validateDFG(value: String): String? {
        if (value.isEmpty()) return null
        return if (!value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) "DFG invalide" else null
    }

    private fun validateALAT(value: String): String? {
        if (value.isEmpty()) return null
        return if (!value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) "ALAT invalide" else null
    }

    private fun validateASAT(value: String): String? {
        if (value.isEmpty()) return null
        return if (!value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) "ASAT invalide" else null
    }

    private fun validatePAL(value: String): String? {
        if (value.isEmpty()) return null
        return if (!value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) "PAL invalide" else null
    }

    private fun validateTBil(value: String): String? {
        if (value.isEmpty()) return null
        return if (!value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) "T.Bil invalide" else null
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
                175 * Math.pow(creatinine, -1.154) * Math.pow(
                    age.toDouble(),
                    -0.203
                ) * facteurSexe * facteurRace
            }

        }
    }

    private fun calculerDoseCarboplatine(auc: Double, dfg: Double): Double {
        return if (auc > 0 && dfg > 0) (auc * (dfg + 25))  else 0.0
    }


    private fun canCalculate(): Boolean {
        val state = _inputState.value
        return state.ageError == null &&
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
                _state.value.error == null
    }

    private fun getRecommendationAndDose(
        dfg: Double, // Débit de Filtration Glomérulaire (mL/min)
        sc: Double, // Surface Corporelle (m²)
        aucCible: Double, // AUC cible
        toxiciteRenale: Boolean,
        toxiciteHepatique: Boolean,
        necessiteDialyse: Boolean,
    ): Pair<String, Double> {

        val recommendation: String
        var dose: Double

        if (necessiteDialyse) {
            recommendation = "Non Adaptée"
            dose =
                0.0 // Si dialyse, éviter le carboplatine ou ajuster selon un protocole spécifique
        } else if (dfg < 30 || toxiciteRenale) {
            recommendation = "Non Adaptée"
            dose = 0.0 // Contre-indiqué si DFG trop faible
        } else {
            recommendation = "Adaptée"

            // Formule de dose ajustée par SC : Dose = AUC cible * (DFG + 25) * SC
            dose = aucCible * (dfg + 25) * sc

            // Ajustement selon toxicité hépatique
            if (toxiciteHepatique) {
                dose *= 0.8 // Réduction de 20% en cas d'insuffisance hépatique
            }
        }

        return Pair(recommendation, dose)
    }


}