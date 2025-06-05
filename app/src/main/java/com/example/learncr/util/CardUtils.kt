package com.example.learncr.util

import java.util.Locale

/**
 * Formats a card name into a string suitable for an image filename.
 *
 * The process is:
 * 1. Trim leading/trailing whitespace from the original input `cardName`.
 * 2. Remove all period characters ('.') from the string.
 * 3. Convert the result to lowercase.
 * 4. Replace all occurrences of one or more spaces with a single hyphen.
 * 5. Trim leading/trailing hyphens that might result from step 4.
 * 6. Append ".png" to the resulting string.
 *
 * Example transformations:
 * - "Hog Rider" -> "hog-rider.png"
 * - "Mini P.E.K.K.A" -> "mini-pekka.png" (periods are removed)
 * - ".Elite Barbarians." -> "elite-barbarians.png" (periods removed, spaces handled, hyphens trimmed)
 * - "  Royal Ghost  " -> "royal-ghost.png"
 * - "Skeleton Army" -> "skeleton-army.png"
 * - "Elixir Collector" -> "elixir-collector.png"
 * - " Zap " -> "zap.png" (leading space, word, trailing space)
 * - "---Card---Name---" (if such an input occurred) -> "card---name.png" (internal multiple hyphens preserved, only leading/trailing ones are trimmed)
 *
 * @param cardName The original name of the card.
 * @return A formatted string following the rules above.
 */
public fun formatCardNameToImageFilename(cardName: String): String {
    // 1. Trim leading/trailing whitespace
    var processedName = cardName.trim()

    // 2. Remove all period characters ('.')
    // Example: "Mini P.E.K.K.A" -> "Mini PEKKA"
    // Example: ".Elite Barbarians." -> "Elite Barbarians"
    processedName = processedName.replace(".", "")

    // 3. Convert to lowercase
    // Example: "Mini PEKKA" -> "mini pekka"
    processedName = processedName.lowercase(Locale.getDefault())

    // 4. Replace all occurrences of one or more spaces with a single hyphen
    // Example: "mini pekka" -> "mini-pekka"
    // Example: "  royal ghost  " (after steps 1-3: "royal ghost") -> "royal-ghost"
    processedName = processedName.replace(Regex("\\s+"), "-")

    // 5. Trim leading/trailing hyphens.
    // This regex removes one or more hyphens from the start (^-+) OR (|) one or more from the end (-+$).
    // Example: if steps 1-4 resulted in "--name--", this makes it "name"
    processedName = processedName.replace(Regex("^-+|-+$"), "")

    // 6. Append ".png"
    return "$processedName.png"
}
