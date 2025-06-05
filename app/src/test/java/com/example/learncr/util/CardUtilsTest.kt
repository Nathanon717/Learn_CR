package com.example.learncr.util

import org.junit.Test
import org.junit.Assert.assertEquals

class CardUtilsTest {

    @Test
    fun testSimpleName() {
        assertEquals("hog-rider.png", formatCardNameToImageFilename("Hog Rider"))
    }

    @Test
    fun testNameWithPunctuation() {
        assertEquals("mini-p.e.k.k.a.png", formatCardNameToImageFilename("Mini P.E.K.K.A"))
    }

    @Test
    fun testNameWithLeadingTrailingSpaces() {
        assertEquals("royal-ghost.png", formatCardNameToImageFilename("  Royal Ghost  "))
    }

    @Test
    fun testSingleWordName() {
        assertEquals("golem.png", formatCardNameToImageFilename("Golem"))
    }

    @Test
    fun testNameWithMultipleInternalSpaces() {
        assertEquals("royal-recruits.png", formatCardNameToImageFilename("Royal  Recruits"))
    }

    @Test
    fun testNameWithInternalHyphenAndSpaces() {
        // Example: "Barbarian - Hut" -> "barbarian---hut.png" based on current logic (space, then hyphen, then space becomes three hyphens)
        // If consolidation of hyphens (e.g., to "barbarian-hut.png") was desired, the main function would need adjustment.
        // This test documents the current behavior.
        assertEquals("barbarian---hut.png", formatCardNameToImageFilename("Barbarian - Hut"))
    }

    @Test
    fun testEmptyString() {
        // Current behavior: "" -> trim -> "" -> lowercase -> "" -> replace -> "" -> ".png"
        assertEquals(".png", formatCardNameToImageFilename(""))
    }

    @Test
    fun testStringWithOnlySpaces() {
        // Current behavior: "   " -> trim -> "" -> ... -> ".png"
        assertEquals(".png", formatCardNameToImageFilename("   "))
    }

    @Test
    fun testNameWithNumbers() {
        assertEquals("elixir-golem-8.png", formatCardNameToImageFilename("Elixir Golem 8"))
    }

    @Test
    fun testAlreadyLowerCaseName() {
        assertEquals("cannon.png", formatCardNameToImageFilename("cannon"))
    }

    @Test
    fun testNameEndingWithSpace() {
        // "Knight " -> lc:"knight " -> repl:"knight-" -> trim_hyphen:"knight" -> "knight.png"
        assertEquals("knight.png", formatCardNameToImageFilename("Knight "))
    }

    @Test
    fun testNameStartingWithSpace() {
        // " Mage" -> lc:" mage" -> repl:"-mage" -> trim_hyphen:"mage" -> "mage.png"
        assertEquals("mage.png", formatCardNameToImageFilename(" Mage"))
    }

     @Test
    fun testNameWithMixedCase() {
        assertEquals("archeeers.png", formatCardNameToImageFilename("ArCheErs"))
    }

    @Test
    fun testNameWithOnlyHyphens() {
        // "---" -> lc:"---" -> repl:"---" -> trim_hyphen:"" -> ".png"
        assertEquals(".png", formatCardNameToImageFilename("---"))
    }

    @Test
    fun testNameWithSpacesAndHyphensMixed() {
        // " - Word - Another - " -> lc:" - word - another - "
        // repl:"---word---another---"
        // trim_hyphen:"word---another"
        // -> "word---another.png"
        assertEquals("word---another.png", formatCardNameToImageFilename(" - Word - Another - "))
    }

    @Test
    fun testNameThatBecomesEmptyAfterTrim() {
        // " - " -> lc:" - " -> repl:"---" -> trim_hyphen:"" -> ".png"
        assertEquals(".png", formatCardNameToImageFilename(" - "))
    }
}
