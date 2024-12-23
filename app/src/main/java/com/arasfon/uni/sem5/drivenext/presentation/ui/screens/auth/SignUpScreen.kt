package com.arasfon.uni.sem5.drivenext.presentation.ui.screens.auth

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.arasfon.uni.sem5.drivenext.R
import com.arasfon.uni.sem5.drivenext.common.theme.DriveNextButton
import com.arasfon.uni.sem5.drivenext.domain.models.Sex
import com.arasfon.uni.sem5.drivenext.presentation.util.CommonTextField
import com.arasfon.uni.sem5.drivenext.presentation.util.DatePickerTextField
import com.arasfon.uni.sem5.drivenext.presentation.util.PasswordTextField
import com.arasfon.uni.sem5.drivenext.presentation.util.ValidatableField
import com.arasfon.uni.sem5.drivenext.presentation.util.noIndicationClickable
import com.arasfon.uni.sem5.drivenext.presentation.viewmodels.auth.SignUpViewModel

@Composable
fun SignUpScreen(
    onReturnBack: () -> Unit,
    onSignUpSuccessful: () -> Unit
) {
    val viewModel: SignUpViewModel = hiltViewModel()

    val lifecycleOwner = LocalLifecycleOwner.current

    val currentStage by viewModel.currentStage.collectAsStateWithLifecycle()

    BackHandler {
        viewModel.returnToPreviousStage()
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { event ->
                when (event) {
                    SignUpViewModel.NavigationEvent.ReturnBack -> {
                        onReturnBack()
                    }
                    SignUpViewModel.NavigationEvent.SignUpSuccessful -> {
                        onSignUpSuccessful()
                    }
                }
            }
    }

    Scaffold (
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .height(32.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        onClick = { viewModel.returnToPreviousStage() }
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBackIos,
                            contentDescription = "Назад"
                        )
                    }

                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Создать аккаунт",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .weight(1f)
                        .fillMaxSize()
                ) {
                    AnimatedContent(
                        targetState = currentStage,
                        label = "StageContentAnimation"
                    ) { stage ->
                        when (stage) {
                            SignUpViewModel.STAGE_LOGIN_INFO -> Stage1(viewModel)
                            SignUpViewModel.STAGE_PERSONAL_DETAILS -> Stage2(viewModel)
                            SignUpViewModel.STAGE_LICENSE_AND_PHOTOS -> Stage3(viewModel)
                        }
                    }
                }

                Row(
                    modifier = Modifier.padding(vertical = 24.dp)
                ) {
                    DriveNextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.continueToNextStage() }
                    ) {
                        Text("Далее")
                    }
                }
            }
        }
    }
}

@Composable
fun Stage1(
    viewModel: SignUpViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            field = viewModel.emailField,
            labelText = { stringResource(R.string.auth_email_label) },
            errorText = { "error" },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            enabled = true
        )

        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            field = viewModel.passwordField,
            labelText = { "Придумайте пароль" },
            errorText = { "error" },
            imeAction = ImeAction.Next,
            enabled = true
        )

        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            field = viewModel.repeatPasswordField,
            labelText = { "Повторите пароль" },
            errorText = { "error" },
            enabled = true
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noIndicationClickable(
                    onClick = {
                        viewModel.acceptTermsField.updateValue(!viewModel.acceptTermsField.value)
                    }),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(viewModel.acceptTermsField.value, { viewModel.acceptTermsField.updateValue(it) })
            Text("Я согласен с условиями обслуживания и политикой конфиденциальности")
        }
    }
}

@Composable
fun Stage2(
    viewModel: SignUpViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            field = viewModel.surnameField,
            labelText = { "Фамилия" },
            errorText = { "error" },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            field = viewModel.nameField,
            labelText = { "Имя" },
            errorText = { "error" },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            field = viewModel.patronymicField,
            labelText = { "Отчество" },
            errorText = { "error" },
        )

        DatePickerTextField(
            modifier = Modifier.fillMaxWidth(),
            field = viewModel.birthdayField,
            labelText = { "Дата рождения" },
            errorText = { "error" }
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Пол", style = MaterialTheme.typography.labelLarge)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .noIndicationClickable(
                            onClick = {
                                viewModel.sexField.updateValue(Sex.MALE)
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    RadioButton(
                        viewModel.sexField.value == Sex.MALE,
                        onClick = { viewModel.sexField.updateValue(Sex.MALE) }
                    )
                    Text("Мужской")
                }
                Row(
                    modifier = Modifier
                        .noIndicationClickable(
                            onClick = {
                                viewModel.sexField.updateValue(Sex.FEMALE)
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    RadioButton(
                        viewModel.sexField.value == Sex.FEMALE,
                        onClick = { viewModel.sexField.updateValue(Sex.FEMALE) }
                    )
                    Text("Женский")
                }
            }
        }
    }
}

@Composable
fun Stage3(
    viewModel: SignUpViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val profilePhotoPickMedia = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.profilePhotoField.updateValue(uri)
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(144.dp)
                .noIndicationClickable(onClick = {
                    profilePhotoPickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                })
        ) {
            AnimatedContent(
                targetState = viewModel.profilePhotoField.value,
                label = "ProfilePhotoContentAnimation"
            ) { value ->
                if (value == null) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "Загрузить фото профиля",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                else {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(viewModel.profilePhotoField.value)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Фото профиля",
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(28.dp)
                    .align(Alignment.BottomEnd)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = viewModel.profilePhotoField.value == null,
                    label = "ProfilePhotoButtonContentAnimation"
                ) { isUrlNull ->
                    if (isUrlNull) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Загрузить фото профиля",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    else {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Изменить фото профиля",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }

        Text("Добавление фотографии поможет владельцам и арендаторам узнать друг друга, когда они будут забирать машину.")

        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            field = viewModel.driversLicenseField,
            labelText = { "Номер водительского удостоверения" },
            maxLength = 10,
            placeholder = { Text("0000000000") },
            errorText = { "error" },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        DatePickerTextField(
            modifier = Modifier.fillMaxWidth(),
            field = viewModel.driversLicenseIssueDateField,
            labelText = { "Дата выдачи" },
            errorText = { "error" }
        )

        ImagePicker(
            field = viewModel.driversLicensePhotoField,
            modifier = Modifier.fillMaxWidth(),
            labelText = { "Фото водительского удостоверения" }
        )

        ImagePicker(
            field = viewModel.passportPhotoField,
            modifier = Modifier.fillMaxWidth(),
            labelText = { "Фото паспорта" }
        )
    }
}

@Composable
fun<TError> ImagePicker(
    field: ValidatableField<Uri?, TError>,
    labelText: @Composable () -> String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(labelText())

        val pickMedia = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
            if (uri != null) {
                field.updateValue(uri)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                }),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(12.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = if (field.value == null) Icons.Outlined.Upload else Icons.Outlined.Edit,
                    contentDescription = if (field.value == null) "Загрузить фото" else "Изменить фото",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = if (field.value == null) "Загрузите фото" else "Фото загружено",
                color = if (field.value == null) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onBackground
            )
        }

        // TODO: Show error here
    }
}
