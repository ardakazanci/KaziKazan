package com.ardakazanci.kazikazan

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.IntSize

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KaziKazan(
    baseFirstImage: ImageBitmap,
    modifier: Modifier = Modifier,
    actionOffset: Offset?,
    actionMovedOffset: (Float, Float) -> Unit,
    selectedPath: Path,
    selectedPathTickness: Float,
    bg: @Composable () -> Unit
) {

    Box(modifier = modifier.clipToBounds()) {
        bg()
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            selectedPath.moveTo(it.x, it.y)
                        }
                        MotionEvent.ACTION_MOVE -> {
                            actionMovedOffset(it.x, it.y)
                        }
                    }
                    true
                }
        ) {
            val canvasHeight = size.height.toInt()
            val canvasWidth = size.width.toInt()
            val imageSize = IntSize(width = canvasWidth, height = canvasHeight)

            actionOffset?.let {
                selectedPath.addOval(oval = Rect(it, selectedPathTickness))
            }

            clipPath(path = selectedPath, clipOp = ClipOp.Difference) {
                drawImage(
                    image = baseFirstImage,
                    dstSize = imageSize
                )
            }
        }
    }
}