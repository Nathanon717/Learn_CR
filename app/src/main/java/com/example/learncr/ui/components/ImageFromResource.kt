package com.example.learncr.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var imageBitmap by remember(filename) { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(filename) {
        // Construct the full path within the resources.
        // ClassLoader.getResourceAsStream expects a path relative to the root of the classpath.
        // For a typical Gradle project structure, files in src/main/java or src/main/resources
        // are added to the classpath.
        // If assets are in "app/src/main/java/com/example/learncr/ui/assets/",
        // the resource path will be "com/example/learncr/ui/assets/filename".
        val resourcePath = "com/example/learncr/ui/assets/$filename"

        withContext(Dispatchers.IO) {
            val stream: InputStream? = try {
                Thread.currentThread().contextClassLoader?.getResourceAsStream(resourcePath)
            } catch (e: Exception) {
                // Log error or handle
                println("Error getting resource stream for $resourcePath: ${e.message}")
                null
            }

            if (stream != null) {
                try {
                    stream.use { BitmapFactory.decodeStream(it)?.asImageBitmap() }
                        .also { loadedBitmap -> imageBitmap = loadedBitmap }
                } catch (e: Exception) {
                    // Log error or handle if decoding fails
                    println("Error decoding image $resourcePath: ${e.message}")
                    imageBitmap = null // Ensure bitmap is null on error
                }
            } else {
                println("Resource stream is null for $resourcePath. Check path and build configuration.")
                imageBitmap = null // Ensure bitmap is null if stream is not found
            }
        }
    }

    imageBitmap?.let { bitmap ->
        Image(
            bitmap = bitmap,
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
    // Optionally, display a placeholder if imageBitmap is null
    // else {
    //     Text("Loading $filename...")
    // }
}
