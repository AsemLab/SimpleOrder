package com.asemlab.simpleorder.ui.tables


import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil3.compose.AsyncImage
import com.asemlab.simpleorder.ui.models.ProductUI
import com.asemlab.simpleorder.ui.theme.SimpleOrderTheme
import com.asemlab.simpleorder.ui.theme.Typography


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ProductCardBox(
    product: ProductUI,
    modifier: Modifier = Modifier,
    onProductClicked: (ProductUI) -> Unit
) {

    val price = if ((product.price!! * 100).rem(100) == 0.0)
        product.price.toInt() else product.price * 100 / 100

    val countId = "count"
    val cardId = "card"

    BoxWithConstraints(
        modifier = Modifier
            .padding(16.dp)
            .width(200.dp)
            .wrapContentHeight()
    ) {

        val constraintSet = ConstraintSet {
            val count = createRefFor(countId)
            val card = createRefFor(cardId)

            constrain(count) {
                top.linkTo(card.top)
                end.linkTo(card.end, -14.dp)
                bottom.linkTo(card.top)
            }
        }
        ConstraintLayout(constraintSet) {

            Card(
                modifier = modifier.layoutId(cardId),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                onClick = {
                    onProductClicked(product)
                }
            ) {

                AsyncImage(
                    model = product.image,
                    modifier = modifier
                        .width(200.dp)
                        .height(150.dp)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )


                Column(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(16.dp)
                ) {
                    Text(
                        text = product.name!!,
                        modifier = Modifier
                            .fillMaxWidth(),
                        minLines = 1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = Typography.titleMedium
                    )
                    Text(
                        text = "$${price}", maxLines = 1, overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 4.dp),
                        style = Typography.bodyLarge
                    )
                }
            }

            if (product.count > 0) {
                Text(
                    "${product.count}", fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .background(
                            color = Color.Red,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .size(32.dp)
                        .padding(vertical = 4.dp)
                        .layoutId(countId)
                )
            }
        }
    }
}


@Composable
@Preview(
    showBackground = true, /*widthDp = 320,*/ uiMode = UI_MODE_NIGHT_NO,
    name = "Product Card Preview"
)
fun PreviewCard() {
    SimpleOrderTheme {
        ProductCardBox(
            ProductUI(
                name = "Meat asd",
                description = "Fresh Beef and very soft and red",
                image = "https://www.drinkpreneur.com/wp-content/uploads/2016/12/drinkpreneur_819_main.jpg",
                price = 25.99,
                id = "1",
                count = 5
            )
        ) {
            it.count++
        }
    }


}


