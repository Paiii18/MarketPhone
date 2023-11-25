import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marketphone.R
import com.example.marketphone.helper.formatAsCurrency
import com.example.marketphone.ui.theme.MarketPhoneTheme

@Composable
fun MarketItem(
    image: Int,
    title: String,
    price: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Card {
            Box(
                modifier
                    .height(150.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                )

            }
            Box(modifier = modifier.height(10.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )
            Box(modifier = modifier.height(10.dp))
            Text(
                text = "Rp ${formatAsCurrency(price)}",


                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )
            Box(modifier = modifier.height(10.dp))
        }

    }
}

@Composable
@Preview(showBackground = true)
fun RewardItemPreview() {
   MarketPhoneTheme {
        MarketItem(R.drawable.hp1, "Phone", 1000000)
    }
}