package com.ardakazanci.kazikazan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.ardakazanci.kazikazan.ui.theme.KaziKazanTheme

data class Properties(
    val path: Path,
    val color: Color = Color.White,
    val width: Float = 30F
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KaziKazanTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {

    val first = ImageBitmap.imageResource(id = R.drawable.first)
    val second = ImageBitmap.imageResource(id = R.drawable.second)
    val statePath = remember {
        mutableStateOf(Properties(path = Path()))
    }
    val stateMove = remember {
        mutableStateOf<Offset?>(null)
    }

    KaziKazanComp(
        firstImage = first,
        secondImage = second,
        statePath = statePath,
        stateMove = stateMove
    )


}


@Composable
fun KaziKazanComp(
    modifier: Modifier = Modifier,
    firstImage: ImageBitmap,
    secondImage: ImageBitmap,
    statePath: MutableState<Properties>,
    stateMove: MutableState<Offset?>
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        KaziKazan(
            baseFirstImage = firstImage,
            modifier = Modifier
                .size(300.dp,170.dp)
                .clip(RoundedCornerShape(size = 16.dp)),
            actionOffset = stateMove.value,
            actionMovedOffset = { x, y ->
                stateMove.value = Offset(x, y)
            },
            selectedPath = statePath.value.path,
            selectedPathTickness = statePath.value.width,
            bg = {
                Image(
                    bitmap = secondImage,
                    contentDescription = "",
                    modifier = Modifier.size(300.dp,170.dp)
                )
            }
        )
    }

}

