import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marketphone.R
import com.example.marketphone.di.Injection
import com.example.marketphone.ui.common.UiState
import com.example.marketphone.ui.screen.ViewModelFactory
import com.example.marketphone.ui.screen.home.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,

    ) {
    val query by viewModel.query

    SearchBarDua(query = query, onQueryChange = viewModel::onQueryChanged)
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator()
                viewModel.onQueryChanged("")
            }

            is UiState.Success -> {
                HomeContent(
                    orderBadminton = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }

            is UiState.Error -> {}

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarDua(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(stringResource(R.string.search_Phone))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {}
}

@Composable
fun HomeContent(
    orderBadminton: List<OrderMarket>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    if (orderBadminton.isEmpty()) {
        Text(
            text = stringResource(R.string.no_data_available), // Replace with your actual string resource
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    } else {
        LazyColumn(
            contentPadding = PaddingValues(
                top = 100.dp,
                bottom = 20.dp,
                end = 20.dp,
                start = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            items(orderMarket) { data ->
                MarketItem(
                    image = data.market.image,
                    title = data.market.title,
                    price = data.market.price,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { navigateToDetail(data.market.id) }
                )
            }
        }
    }
}