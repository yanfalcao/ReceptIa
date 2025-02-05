package com.nexusfalcao.recipecatalog.widget

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
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nexusfalcao.designsystem.preview.FontSizeAcessibilityPreview
import com.nexusfalcao.designsystem.preview.UIModeBakgroundPreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.model.state.RecipeDifficult
import com.nexusfalcao.recipecatalog.R
import com.nexusfalcao.recipecatalog.state.AmountServesFilterEnum
import com.nexusfalcao.recipecatalog.state.FilterState
import com.nexusfalcao.recipecatalog.state.TagFilterEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetFilter(
    filterUiState: FilterState,
    onDismiss: () -> Unit,
    onApplyFilter: () -> Unit,
    onResetFilter: () -> Unit,
    updateDifficultFilter: (RecipeDifficult) -> Unit,
    updateAmountServesFilter: (AmountServesFilterEnum) -> Unit,
) {
    val modalBottomSheetState: SheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        )

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = MaterialTheme.colorScheme.background,
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
    updateDifficultFilter: (RecipeDifficult) -> Unit,
    updateAmountServesFilter: (AmountServesFilterEnum) -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier =
            Modifier
                .safeDrawingPadding()
                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(id = R.string.filter),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            color = colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.difficult),
            style = MaterialTheme.typography.titleLarge,
            color = colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            for (level in RecipeDifficult.values()) {
                val levelText =
                    when (level) {
                        RecipeDifficult.Easy -> stringResource(id = R.string.easy)
                        RecipeDifficult.Medium -> stringResource(id = R.string.medium)
                        RecipeDifficult.Hard -> stringResource(id = R.string.hard)
                    }
                val tagTextContentDescription = if (filterUiState.isSelected(level)) {
                    stringResource(id = R.string.cd_tag_filter_applied, levelText)
                } else {
                    stringResource(id = R.string.cd_tag_filter_unapplied, levelText)
                }

                Tag(
                    text = levelText,
                    tagContentDescription = tagTextContentDescription,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    isSelected = filterUiState.isSelected(level),
                    updateTagFilter = { updateDifficultFilter(level) },
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = stringResource(id = R.string.amount_people_serves),
            style = MaterialTheme.typography.titleLarge,
            color = colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            for (amount in AmountServesFilterEnum.values()) {
                val levelText =
                    when (amount) {
                        AmountServesFilterEnum.ONE -> stringResource(id = R.string.one_in_number)
                        AmountServesFilterEnum.TWO -> stringResource(id = R.string.two_in_number)
                        AmountServesFilterEnum.THREE -> stringResource(id = R.string.three_in_number)
                        AmountServesFilterEnum.FOUR_OR_MORE -> stringResource(id = R.string.four_or_more)
                    }
                val tagTextContentDescription = if (filterUiState.isSelected(amount)) {
                    stringResource(id = R.string.cd_tag_filter_applied, levelText)
                } else {
                    stringResource(id = R.string.cd_tag_filter_unapplied, levelText)
                }

                Tag(
                    text = levelText,
                    tagContentDescription = tagTextContentDescription,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    isSelected = filterUiState.isSelected(amount),
                    updateTagFilter = { updateAmountServesFilter(amount) },
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(
                onClick = onResetFilter,
                colors = ButtonDefaults.buttonColors(containerColor = colorScheme.surface),
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    text = stringResource(id = R.string.reset),
                    color = colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
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
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@FontSizeAcessibilityPreview
@UIModeBakgroundPreview
@Composable
private fun BottomSheetFilter() {
    ReceptIaTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.background),
            ) {
                BottomSheetBody(
                    filterUiState =
                        FilterState(
                            tag = TagFilterEnum.FAVORITES,
                            difficult = RecipeDifficult.Easy,
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
