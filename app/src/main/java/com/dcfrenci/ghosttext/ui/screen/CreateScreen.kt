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
import androidx.compose.ui.unit.dp
import com.dcfrenci.ghosttext.viewmodel.ViewModelCreate

@Composable
fun CreateScreenUI(viewModelCreate: ViewModelCreate) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SpaceItemColumn()
        //Image
        if (viewModelCreate.uri != null) {
            viewModelCreate.uri?.let {
                ImageCard(
                    uri = it,
                    contentDescription = "shared_image",
                )
            }
        } else {
            ImageCardLost(
                modifier = Modifier
            )
        }
        SpaceItemColumn()
        //Buttons (upload: gallery - camera - stock? - generate?)
        ElevatedButton(
            modifier = Modifier.width(IntrinsicSize.Max),
            onClick = { viewModelCreate.updateUpload() }
        ) {
            IconTextButton(
                imageVector = Icons.Outlined.Upload,
                contentDescription = "icon_upload",
                text = ""
            )
        }
        val galleryPicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {uri -> viewModelCreate.updateUri(uri)}
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
                        imageVector = Icons.Outlined.PhotoLibrary,
                        contentDescription = "icon_gallery",
                        text = "Gallery"
                    )
                }
                ElevatedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModelCreate.updateUpload()
                        viewModelCreate.loadCamera()
                    }
                ) {
                    IconTextButton(
                        arrangement = Arrangement.Start,
                        imageVector = Icons.Outlined.CameraAlt,
                        contentDescription = "icon_camera",
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
                        imageVector = Icons.Outlined.Storage,
                        contentDescription = "icon_stock_photo",
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
                        imageVector = Icons.Outlined.AutoAwesome,
                        contentDescription = "icon_generate",
                        text = "Generate"
                    )
                }
            }
        }
        SpaceItemColumn()
        //Text to hide
        MessageBox(input = true)
        SpaceItemColumn()
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
            ElevatedButton(
                onClick = { viewModelCreate.exportShare() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = "icon_share"
                )
            }
        }

        //TODO - Remove
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
        }
    }

}
