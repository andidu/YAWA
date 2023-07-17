package com.adorastudios.yawa.presentation.location_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adorastudios.yawa.R

@Composable
fun LocationTile(
    modifier: Modifier = Modifier,
    locationName: String,
    selected: Boolean,
    onSelect: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = locationName,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.width(8.dp))

        if (selected) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.selected),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
            )
        } else {
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clickable {
                        onSelect()
                    }
                    .padding(8.dp),
                text = stringResource(id = R.string.select),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
} 
