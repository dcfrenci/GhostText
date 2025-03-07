package com.dcfrenci.ghosttext.ui.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dcfrenci.ghosttext.ui.theme.SpacerElementSmall
import com.dcfrenci.ghosttext.ui.theme.SpacerEndScreen
import com.dcfrenci.ghosttext.ui.theme.SpacerStartScreen
import com.dcfrenci.ghosttext.viewmodel.ViewModelCreate

@Composable
fun CreateScreenUI(viewModelCreate: ViewModelCreate) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SpacerItemColumn(SpacerStartScreen)
        TextTitle("Create")
        SpacerItemColumn(SpacerElementSmall)
        //Image
        Image(viewModelCreate = viewModelCreate)

        SpacerItemColumn()
        //Buttons (upload: gallery - camera - stock? - generate?)
        ElevatedButton(
            modifier = Modifier.width(IntrinsicSize.Max),
            onClick = { viewModelCreate.updateUpload() }
        ) {
            IconTextButton(
                arrangement = Arrangement.Center,
                icon = Icons.Outlined.Upload,
                iconDescription = "icon_upload",
            )
        }
        val galleryPicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri -> viewModelCreate.updateUri(uri) }
        )
        val cameraPicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = {
                results ->
                if (results) {
                    viewModelCreate.updateUri(viewModelCreate.photoUri)
                }
            }
        )
        if (viewModelCreate.upload) {
            Column(
                modifier = Modifier.width(IntrinsicSize.Max)
            ) {
                ElevatedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModelCreate.updateUpload()
                        viewModelCreate.loadGallery(galleryPicker)
                    }
                ) {
                    IconTextButton(
                        arrangement = Arrangement.Start,
                        icon = Icons.Outlined.PhotoLibrary,
                        iconDescription = "icon_gallery",
                        text = "Gallery"
                    )
                }
                ElevatedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModelCreate.updateUpload()
                        cameraPicker.launch(viewModelCreate.loadCamera())
                    }
                ) {
                    IconTextButton(
                        arrangement = Arrangement.Start,
                        icon = Icons.Outlined.CameraAlt,
                        iconDescription = "icon_camera",
                        text = "Camera"
                    )
                }
                ElevatedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModelCreate.updateUpload()
                        viewModelCreate.loadStockPhoto()
                    }
                ) {
                    IconTextButton(
                        arrangement = Arrangement.Start,
                        icon = Icons.Outlined.Storage,
                        iconDescription = "icon_stock_photo",
                        text = "Stock photo"
                    )
                }
                ElevatedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModelCreate.updateUpload()
                        viewModelCreate.loadGenerate()
                    }
                ) {
                    IconTextButton(
                        arrangement = Arrangement.Start,
                        icon = Icons.Outlined.AutoAwesome,
                        iconDescription = "icon_generate",
                        text = "Generate"
                    )
                }
            }
        }
        SpacerItemColumn()
        //Text to hide
        MessageBox(viewModelCreate = viewModelCreate)
        SpacerItemColumn()
        //Buttons (download - share)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ElevatedButton(
                onClick = { viewModelCreate.exportDownload() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Download,
                    contentDescription = "icon_download"
                )
            }
            ButtonSpacerHorizontal()
            ElevatedButton(
                onClick = { viewModelCreate.exportShare() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = "icon_share"
                )
            }
        }

        SpacerItemColumn(SpacerEndScreen)
    }

}
