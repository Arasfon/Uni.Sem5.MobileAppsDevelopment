package com.arasfon.uni.sem5.drivenext.presentation.ui.screens.lessoronboarding

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickMultipleVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.arasfon.uni.sem5.drivenext.common.theme.DriveNextButton
import com.arasfon.uni.sem5.drivenext.domain.models.validation.ValidationResult
import com.arasfon.uni.sem5.drivenext.presentation.util.CommonTextField
import com.arasfon.uni.sem5.drivenext.presentation.util.ValidatableField

@Composable
fun CarAdditionScreen(
    onReturnBack: () -> Unit,
    onFinish: () -> Unit
) {
    var currentStage by rememberSaveable { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .padding(WindowInsets.statusBars.asPaddingValues())
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 24.dp)
                        .height(32.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (currentStage == 0) {
                                onReturnBack()
                            } else {
                                currentStage--
                            }
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBackIos,
                            contentDescription = "Назад"
                        )
                    }

                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Добавление автомобиля",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Column(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp)
                ) {
                    AnimatedContent(
                        targetState = currentStage,
                        label = "MainAnimatedContent"
                    ) { stage ->
                        when (stage) {
                            0 -> Stage1()
                            1 -> Stage2()
                            2 -> Stage3()
                            else -> throw Exception()
                        }
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                shadowElevation = 1.dp
            ) {
                Box(
                    modifier = Modifier
                        .padding(24.dp)
                        .padding(WindowInsets.navigationBars.asPaddingValues())
                ) {
                    DriveNextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (currentStage < 2) {
                                currentStage++
                            } else {
                                onFinish()
                            }
                        }
                    ) {
                        AnimatedContent(
                            targetState = currentStage == 2,
                            label = "ContinueButtonContentAnimation"
                        ) { isLastStage ->
                            Text(if (isLastStage) "Отправить" else "Далее")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Stage1() {
    val coroutineScope = rememberCoroutineScope()
    val carLocationField = ValidatableField<String, Unit>("", { if (it.isNotEmpty()) ValidationResult.Valid else ValidationResult.Invalid<Unit>(Unit) }, coroutineScope)

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        CommonTextField(
            field = carLocationField,
            labelText = { "Где расположен Ваш автомобиль?" },
            errorText = { "Данное поле не может быть пустым" },
        )
    }
}

@Composable
fun Stage2() {
    val coroutineScope = rememberCoroutineScope()
    val carAgeField = ValidatableField<String, Unit>("", { if (it.isNotEmpty()) ValidationResult.Valid else ValidationResult.Invalid<Unit>(Unit)  }, coroutineScope)
    val carManufacturerField = ValidatableField<String, Unit>("", { if (it.isNotEmpty()) ValidationResult.Valid else ValidationResult.Invalid<Unit>(Unit) }, coroutineScope)
    val carNameField = ValidatableField<String, Unit>("", { if (it.isNotEmpty()) ValidationResult.Valid else ValidationResult.Invalid<Unit>(Unit) }, coroutineScope)
    val carTransmissionField = ValidatableField<String, Unit>("", { if (it.isNotEmpty()) ValidationResult.Valid else ValidationResult.Invalid<Unit>(Unit) }, coroutineScope)
    val carMileageField = ValidatableField<String, Unit>("", { if (it.isNotEmpty()) ValidationResult.Valid else ValidationResult.Invalid<Unit>(Unit) }, coroutineScope)
    val carDescriptionField = ValidatableField<String, Unit>("", { ValidationResult.Valid }, coroutineScope)

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CommonTextField(
            field = carAgeField,
            labelText = { "Год выпуска" },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLength = 4,
            hideMaxLength = true,
            errorText = { "Данное поле не может быть пустым" },
        )

        CommonTextField(
            field = carManufacturerField,
            labelText = { "Марка автомобиля" },
            errorText = { "Данное поле не может быть пустым" },
        )

        CommonTextField(
            field = carNameField,
            labelText = { "Модель" },
            errorText = { "Данное поле не может быть пустым" },
        )

        CommonTextField(
            field = carTransmissionField,
            labelText = { "Трансмиссия" },
            errorText = { "Данное поле не может быть пустым" },
        )

        CommonTextField(
            field = carMileageField,
            labelText = { "Пробег (км)" },
            errorText = { "Данное поле не может быть пустым" },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        CommonTextField(
            field = carDescriptionField,
            labelText = { "Описание" },
            errorText = { "" },
            placeholder = {
                Text("Пожалуйста, добавьте дополнительную информацию о Вашем автомобиле, которая может быть интересна арендатору. Например, о внутреннем оформлении, мощности и других особенностях вашего автомобиля.")
            },
            singleLine = false,
            maxLength = 1000
        )
    }
}

@Composable
fun Stage3() {
    val coroutineScope = rememberCoroutineScope()
    val carPhotos = remember { ValidatableField<Array<Uri?>, Unit>(Array(5) { null }, { if (it.any { x -> x == null }) ValidationResult.Invalid<Unit>(Unit) else ValidationResult.Valid }, coroutineScope) }

    val carPhotosValidationResult by carPhotos.displayValidationState.collectAsStateWithLifecycle()

    var photo1 by rememberSaveable { mutableStateOf<Uri?>(null) }
    var photo2 by rememberSaveable { mutableStateOf<Uri?>(null) }
    var photo3 by rememberSaveable { mutableStateOf<Uri?>(null) }
    var photo4 by rememberSaveable { mutableStateOf<Uri?>(null) }
    var photo5 by rememberSaveable { mutableStateOf<Uri?>(null) }

    val carPhotoPickMedia = rememberLauncherForActivityResult(PickMultipleVisualMedia(5)) { uriList ->
        carPhotos.updateValue(Array(5) { index -> uriList.getOrNull(index) })
        photo1 = carPhotos.value[0]
        photo2 = carPhotos.value[1]
        photo3 = carPhotos.value[2]
        photo4 = carPhotos.value[3]
        photo5 = carPhotos.value[4]
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Добавьте фотографии", style = MaterialTheme.typography.titleMedium)

        Column(
            modifier = Modifier
                .clickable(onClick = {
                    carPhotoPickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                }),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(4f/3f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photo1)
                    .crossfade(true)
                    .build(),
                contentDescription = "Car photo 1",
                contentScale = ContentScale.Crop
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(4f/3f)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photo2)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Car photo 2",
                    contentScale = ContentScale.Crop
                )

                AsyncImage(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(4f/3f)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photo3)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Car photo 3",
                    contentScale = ContentScale.Crop
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(4f/3f)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photo4)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Car photo 4",
                    contentScale = ContentScale.Crop
                )

                AsyncImage(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(4f/3f)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photo5)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Car photo 5",
                    contentScale = ContentScale.Crop
                )
            }
        }

        AnimatedVisibility(carPhotosValidationResult is ValidationResult.Invalid<Unit>) {
            AnimatedContent(
                targetState = carPhotosValidationResult,
                label = "CarPhotosValidationErrorContentAnimation"
            ) { error ->
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Все 5 фото должны быть выбраны",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
