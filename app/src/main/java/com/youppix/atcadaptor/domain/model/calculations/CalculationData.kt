package com.youppix.atcadaptor.domain.model.calculations

import kotlinx.serialization.Serializable

@Serializable
data class CalculationData(
    val age: Int,
    val alat: String,
    val asat: String,
    val auc_cible: String,
    val calculation_id: Int,
    val created_at: String,
    val creatinine: String,
    val dfg: String,
    val dfg_calcule: String,
    val dfg_type: String,
    val dose_carboplatine: String,
    val dose_par_m2: String,
    val dose_recommandee: String,
    val genre: String,
    val necessite_dialyse: Int,
    val nom_de_medicament: String,
    val pal: String,
    val poids: String,
    val race: String,
    val recommandation: String,
    val sc: String,
    val taille: String,
    val tbil: String,
    val toxicite_hepatique: Int,
    val toxicite_renale: Int,
    val user_id: Int,
    val users_email: String,
    val users_name: String,
    val users_phone: String
)


@Serializable
data class CalculationHistoryData(
    val user_id: Int,
    val calculated_by: Int,
    val nom_de_medicament: String,
    val age: Int,
    val poids: String,
    val taille: String,
    val genre: String,
    val race: String,
    val creatinine: String,
    val alat: String,
    val asat: String,
    val pal: String,
    val tbil: String,
    val dfg_type: String,
    val dfg: String,
    val dfg_calcule: String,
    val auc_cible: String,
    val dose_carboplatine: String,
    val dose_par_m2: String,
    val toxicite_renale: Int,
    val toxicite_hepatique: Int,
    val necessite_dialyse: Int,
    val sc: String,
    val recommandation: String,
    val dose_recommandee: String ,
    val comment : String
)