package com.youppix.atcadaptor.presentation.mainApp.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.youppix.atcadaptor.R
import com.youppix.atcadaptor.common.Dimens.BottomBarHeight
import com.youppix.atcadaptor.common.Dimens.ExtraSmallPadding
import com.youppix.atcadaptor.common.Dimens.ExtraSmallPadding2
import com.youppix.atcadaptor.common.Dimens.MediumPadding
import com.youppix.atcadaptor.common.Dimens.SmallPadding


@Composable
fun WelcomeScreen(modifier: Modifier = Modifier) {


    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MediumPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .offset(x = (-15).dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.atc_logo),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                )
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.padding(top = SmallPadding)
                ) {
                    Text(
                        text = "ATC-Adaptor",

                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,

                            )
                    )
                    Text(
                        text = "Votre guide pour un dosage personnalisé des médicaments",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.offset(y = -ExtraSmallPadding)
                    )
                }
            }
        }
        item {
            FeatureItem(
                icon = R.drawable.ic_patient_data, // Remplacer par vos icônes
                title = "Infos Patient",
                description = "Saisissez et gérez les données médicales du patient."
            )
        }

        item {
            FeatureItem(
                modifier = Modifier.padding(vertical = SmallPadding),
                icon = R.drawable.ic_dosage, // Remplacer par vos icônes
                title = "Données de dose",
                description = "Consultez les médicaments et leurs dosages."
            )
        }
        item {
            FeatureItem(
                modifier = Modifier.padding(bottom = BottomBarHeight + MediumPadding),
                icon = R.drawable.ic_dosage_calc, // Remplacer par vos icônes
                title = "Calcul de Dose",
                description = "Calculez facilement la dose selon les données du patient."
            )
        }
    }


}

@Composable
fun FeatureItem(modifier: Modifier = Modifier, icon: Int, title: String, description: String) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SmallPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .size(60.dp)
                .border(1.dp, MaterialTheme.colorScheme.primary, shape = CircleShape),
            color = Color.Transparent,
            contentColor = Color(0xFF4CAF50)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(12.dp)
            )
        }
        Text(
            modifier = Modifier.padding(top = ExtraSmallPadding),
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        )
        Text(
            text = description,
            modifier = Modifier.offset(y = -ExtraSmallPadding2),
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.Gray,
                textAlign = TextAlign.Center,
            )
        )
    }
}



