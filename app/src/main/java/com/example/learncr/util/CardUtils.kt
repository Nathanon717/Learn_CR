package com.example.learncr.util

import java.util.Locale

/**
 * Formats a card name into a string suitable for an image filename.
 *
 * The process is:
 * 1. Convert the input `cardName` to lowercase.
 * 2. Replace all occurrences of one or more spaces with a single hyphen.
 * 3. Trim leading/trailing hyphens that might result from step 2 (e.g., from leading/trailing spaces in the original name).
 * 4. Append ".png" to the resulting string.
 *
 * Example transformations:
 * - "Hog Rider" -> "hog-rider.png"
 * - "Mini P.E.K.K.A" -> "mini-p.e.k.k.a.png" (periods are preserved)
 * - "  Royal Ghost  " -> "royal-ghost.png" (leading/trailing spaces convert to hyphens, which are then trimmed)
 * - "Skeleton Army" -> "skeleton-army.png"
 * - "Elixir Collector" -> "elixir-collector.png"
 * - " Zap " -> "zap.png" (leading space, word, trailing space)
 * - "---Card---Name---" (if such an input occurred) -> "card---name.png" (internal multiple hyphens preserved, only leading/trailing ones are trimmed)
 *
 * @param cardName The original name of the card.
 * @return A formatted string following the rules above.
 */
public fun formatCardNameToImageFilename(cardName: String): String {
    // 1. Convert to lowercase
    val lowerCaseName = cardName.lowercase(Locale.getDefault())

    // 2. Replace all occurrences of one or more spaces with a single hyphen
    // Example: "  royal ghost  " -> "--royal-ghost--"
    // Example: " barbarian - hut " -> "-barbarian---hut-"
    var hyphenatedName = lowerCaseName.replace(Regex("\\s+"), "-")

    // 3. Trim leading/trailing hyphens.
    // This regex removes one or more hyphens from the start (^-+) OR (|) one or more from the end (-+$).
    // Example: "--royal-ghost--" -> "royal-ghost"
    // Example: "-barbarian---hut-" -> "barbarian---hut"
    // Example: "---onlyinternalhyphens---" -> "onlyinternalhyphens" (if it was already like that before space replacement)
    // Example: "-single-" -> "single"
    // Example: "-" (from " ") -> ""
    // Example: "" (from "") -> ""
    hyphenatedName = hyphenatedName.replace(Regex("^-+|-+$"), "")

    // 4. Append ".png"
    return "$hyphenatedName.png"
}
