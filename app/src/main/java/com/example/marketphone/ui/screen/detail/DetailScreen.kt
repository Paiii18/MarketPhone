import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marketphone.R
import com.example.marketphone.di.Injection
import com.example.marketphone.helper.formatAsCurrency
import com.example.marketphone.ui.common.UiState
import com.example.marketphone.ui.screen.ViewModelFactory
import com.example.marketphone.ui.screen.detail.DetailViewModel
import com.example.marketphone.ui.theme.MarketPhoneTheme

@Composable
fun DetailScreen(
    marketId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getMarketById(marketId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.market.image,
                    data.market.title,
                    data.market.desc,
                    data.market.price,
                    data.count,
                    onBackClick = navigateBack,
                    onAddToCart = { count ->
                        viewModel.addToCart(data.market, count)
                        navigateToCart()
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title: String,
    desc: String,
    basePoint: Int,
    count: Int,
    onBackClick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    var totalPoint by rememberSaveable { mutableStateOf(0) }
    var orderCount by rememberSaveable { mutableStateOf(count) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Rp ${formatAsCurrency(basePoint)}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Spacer(modifier = modifier.height(10.dp))
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Spacer(modifier = modifier.height(20.dp))
                Text(
                    text = "Deskripsi",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Spacer(modifier = modifier.height(20.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(LightGray)
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ProductCounter(
                1,
                orderCount,
                onProductIncreased = { orderCount++ },
                onProductDecreased = { if (orderCount > 0) orderCount-- },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
            totalPoint = basePoint * orderCount
            OrderButton(
                text = "Tambah ke Keranjang : Rp ${formatAsCurrency(totalPoint)}",
                enabled = orderCount > 0,
                onClick = {
                    onAddToCart(orderCount)
                }
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    MarketPhoneTheme {
        DetailContent(
            R.drawable.hp1,
           "Asus ROG Phone 7 Ultimate",
            "Asus ROG Phone 7 Ultimate\n" +
                    "\n" +
                    "Jaringan GSM / HSPA / LTE / 5G\n" +
                    "\n" +
                    "Sistem Operasi Android 13\n" +
                    "\n" +
                    "Prosesor Qualcomm SM8550-AB Snapdragon 8 Gen 2 (4 nm), " +
                    "Octa-core (1x3.2 GHz Cortex-X3 & 2x2.8 GHz Cortex-A715 & 2x2.8 GHz " +
                    "Cortex-A710 & 3x2.0 GHz Cortex-A510)\n" +
                    "\n" +
                    "GPU Adreno 740\n" +
                    "\n" +
                    "RAM 16 GB Kapasitas 512 GB\n" +
                    "\n" +
                    "Ukuran Layar 6.78 inch" +
                    " Tipe Layar AMOLED, 1B colors, 165Hz, HDR10+, 1000 nits (HBM), 1500 nits (peak)" +
                    " Resolusi Layar 1080 x 2448 pixels (~395 ppi density)\n" +
                    "\n" +
                    "Sensor Fingerprint (under display, optical), accelerometer, gyro, proximity, compass\n" +
                    "\n" +
                    "Baterai 6000 mAh\n" +
                    "\n" +
                    "Lainnya\n" +
                    "\n" +
                    "NFC Yes",
            18999000,
            1,
            onBackClick = {},
            onAddToCart = {}
        )
    }
}