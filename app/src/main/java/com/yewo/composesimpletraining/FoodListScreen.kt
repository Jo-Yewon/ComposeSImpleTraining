package com.yewo.composesimpletraining

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yewo.composesimpletraining.ui.FoodListViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue

@Composable
fun FoodListScreen(viewModel: FoodListViewModel = FoodListViewModel()) {
    val newFoodStateContent = viewModel.textFieldState.observeAsState("")
    val foodsStateContent = viewModel.foods.observeAsState(listOf())

    rememberScrollState()

    Column {
        SimpleTextInput(
            buttonClick = { viewModel.addFood(newFoodStateContent.value) },
            textFieldValue = newFoodStateContent.value,
            textFieldUpdate = { viewModel.onTextChanged(it) }
        )
        SimpleListWithScrollTopButton(
            modifier = Modifier.weight(1f),
            names = foodsStateContent.value
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SimpleTextInput(
    buttonClick: () -> Unit = {},
    textFieldValue: String = "",
    textFieldUpdate: (newName: String) -> Unit = {},
    buttonText: String = "입력"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            modifier = Modifier.weight(1f),
            value = textFieldValue,
            onValueChange = textFieldUpdate,
            singleLine = true,
            maxLines = 1,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            onClick = buttonClick
        ) {
            Text(text = buttonText)
        }
    }
}

@Composable
fun SimpleListWithScrollTopButton(
    modifier: Modifier = Modifier,
    names: List<String>
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    val showScrollTop by remember { derivedStateOf {
        scrollState.firstVisibleItemIndex > 0 ||
                scrollState.firstVisibleItemScrollOffset > 0
    }}

    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            state = scrollState
        ) {
            items(items = names, key = { it }) { name ->
                SimpleTextItem(text = name)
            }
        }
        AnimatedVisibility(
            visible = showScrollTop,
            enter = slideInVertically(initialOffsetY = {it}),
            exit = slideOutVertically(targetOffsetY = {it}),
            modifier = Modifier.align(Alignment.BottomEnd),
        ) {
            IconButton(
                modifier = Modifier
                    .padding(end = 10.dp, bottom = 10.dp)
                    .size(48.dp)
                    .background(
                        color = androidx.compose.ui.graphics.Color.Green,
                        shape = CircleShape
                    ),
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "스크롤 위로 버튼"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleTextItem(text: String = "샌드위치") {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text
        )
    }
}