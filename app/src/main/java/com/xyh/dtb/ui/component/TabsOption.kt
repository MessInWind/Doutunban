package com.xyh.dtb.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.xyh.dtb.store.Store

@Composable
fun TabsOption(
    useIcon: Boolean,
    useLabel: Boolean,
    showDivider: Boolean,
    isTabEnabled: Boolean,
    modifier: Modifier = Modifier,
    onUseIconChanged: (Boolean) -> Unit,
    onUseLabelChanged: (Boolean) -> Unit,
    onShowDividerChanged: (Boolean) -> Unit,
    onTabEnabledChanged: (Boolean) -> Unit,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = "Use icon",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Switch(
            checked = useIcon,
            onCheckedChange = onUseIconChanged
        )

        Text(
            text = "Use label",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Switch(
            checked = useLabel,
            onCheckedChange = onUseLabelChanged
        )

        Text(
            text = "Show divider",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Switch(
            checked = showDivider,
            onCheckedChange = onShowDividerChanged
        )

        Text(
            text = "Enable tab",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Switch(
            checked = isTabEnabled,
            onCheckedChange = onTabEnabledChanged
        )
    }
}