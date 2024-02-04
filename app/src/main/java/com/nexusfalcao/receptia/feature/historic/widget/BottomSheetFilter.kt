package com.nexusfalcao.receptia.feature.historic.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.feature.historic.state.AmountServesFilterEnum
import com.nexusfalcao.receptia.feature.historic.state.FilterState
import com.nexusfalcao.receptia.feature.historic.state.TagFilterEnum
import com.nexusfalcao.receptia.persistence.utils.DifficultState
import com.nexusfalcao.receptia.ui.preview.ThemePreviewShowsBakground
import com.nexusfalcao.receptia.ui.theme.ReceptIaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetFilter(
    filterUiState: FilterState,
    onDismiss: () -> Unit,
    onApplyFilter: () -> Unit,
    onResetFilter: () -> Unit,
    updateDifficultFilter: (DifficultState) -> Unit,
    updateAmountServesFilter: (AmountServesFilterEnum) -> Unit,
) {
    val modalBottomSheetState: SheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        BottomSheetBody(
            filterUiState = filterUiState,
            onApplyFilter = onApplyFilter,
            onResetFilter = onResetFilter,
            updateDifficultFilter = updateDifficultFilter,
            updateAmountServesFilter = updateAmountServesFilter,
        )
    }
}

@Composable
private fun BottomSheetBody(
    filterUiState: FilterState,
    onApplyFilter: () -> Unit,
    onResetFilter: () -> Unit,
    updateDifficultFilter: (DifficultState) -> Unit,
    updateAmountServesFilter: (AmountServesFilterEnum) -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .safeDrawingPadding()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(id = R.string.filter),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
            color = colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.difficult),
            style = MaterialTheme.typography.titleSmall,
            color = colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            for (level in DifficultState.values()) {
                val levelText = when(level) {
                    DifficultState.Easy -> stringResource(id = R.string.easy)
                    DifficultState.Medium -> stringResource(id = R.string.medium)
                    DifficultState.Hard -> stringResource(id = R.string.hard)
                }

                Tag(
                    text = levelText,
                    textStyle = MaterialTheme.typography.labelSmall,
                    isSelected = filterUiState.isSelected(level),
                    updateTagFilter = { updateDifficultFilter(level) },
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = stringResource(id = R.string.amount_people_serves),
            style = MaterialTheme.typography.titleSmall,
            color = colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            for (amount in AmountServesFilterEnum.values()) {
                val levelText = when(amount) {
                    AmountServesFilterEnum.ONE -> stringResource(id = R.string.one_in_number)
                    AmountServesFilterEnum.TWO -> stringResource(id = R.string.two_in_number)
                    AmountServesFilterEnum.THREE -> stringResource(id = R.string.three_in_number)
                    AmountServesFilterEnum.FOUR_OR_MORE -> stringResource(id = R.string.four_or_more)
                }

                Tag(
                    text = levelText,
                    textStyle = MaterialTheme.typography.labelSmall,
                    isSelected = filterUiState.isSelected(amount),
                    updateTagFilter = { updateAmountServesFilter(amount) },
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onResetFilter,
                colors = ButtonDefaults.buttonColors(containerColor = colorScheme.surface),
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    text = stringResource(id = R.string.reset),
                    color = colorScheme.onSurface,
                    style = MaterialTheme.typography.labelMedium,
                )
            }

            Button(
                onClick = onApplyFilter,
                colors = ButtonDefaults.buttonColors(containerColor = colorScheme.primary),
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    text = stringResource(id = R.string.apply),
                    color = colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}

@ThemePreviewShowsBakground
@Composable
private fun BottomSheetFilter() {

    ReceptIaTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.background)
            ) {
                BottomSheetBody(
                    filterUiState = FilterState(
                        tag = TagFilterEnum.FAVORITES,
                        difficult = DifficultState.Easy
                    ),
                    onApplyFilter = {},
                    onResetFilter = {},
                    updateDifficultFilter = {},
                    updateAmountServesFilter = {},
                )
            }
        }
    }
}
