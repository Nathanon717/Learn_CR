package com.example.learncr.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Text // Added for error/loading messages
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

/**
 * A composable function that loads an image from the Java resources directory
 * (specifically targeting "com/example/learncr/ui/assets/") and displays it.
 *
 * @param filename The name of the image file (e.g., "archer-queen.png") located in
 *                 the "com/example/learncr/ui/assets/" resource path.
 * @param contentDescription Text used by accessibility services to describe what this image displays.
 * @param modifier Modifier to be applied to the Image.
 */
@Composable
fun ResourceImage(
    filename: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    var imageBitmapState by remember(filename) { mutableStateOf<ImageBitmap?>(null) }
    var isLoading by remember(filename) { mutableStateOf(true) }

    LaunchedEffect(filename) {
        isLoading = true // Reset loading state when filename changes
        imageBitmapState = null // Reset bitmap when filename changes

        // Construct the full path within the resources.
        // ClassLoader.getResourceAsStream expects a path starting with '/' for absolute path from root of classpath.
        // If assets are in "app/src/main/java/com/example/learncr/ui/assets/",
        // the resource path will be "/com/example/learncr/ui/assets/filename".
        val resourcePath = "/com/example/learncr/ui/assets/$filename"

        withContext(Dispatchers.IO) {
            try {
                val stream: InputStream? = Thread.currentThread().contextClassLoader?.getResourceAsStream(resourcePath)

                if (stream != null) {
                    stream.use { imageBitmapState = BitmapFactory.decodeStream(it)?.asImageBitmap() }
                } else {
                    println("Resource stream is null for $resourcePath. Check path and build configuration.")
                    imageBitmapState = null // Ensure bitmap is null if stream is not found
                }
            } catch (e: Exception) {
                // Log error or handle
                println("Error loading image $resourcePath: ${e.message}")
                imageBitmapState = null // Ensure bitmap is null on error
            } finally {
                isLoading = false
            }
        }
    }

    if (isLoading) {
        Text("Loading $filename...")
    } else if (imageBitmapState != null) {
        Image(
            bitmap = imageBitmapState!!, // imageBitmapState is checked for nullability above
            contentDescription = contentDescription,
            modifier = modifier
        )
    } else {
        Text("Error loading: $filename")
    }
}
