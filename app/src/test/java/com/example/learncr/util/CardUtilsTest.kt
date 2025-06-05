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
        // "Mini P.E.K.K.A" -> trim -> "Mini P.E.K.K.A" -> no periods "Mini PEKKA" -> lc "mini pekka" -> repl "mini-pekka" -> trimH "mini-pekka"
        assertEquals("mini-pekka.png", formatCardNameToImageFilename("Mini P.E.K.K.A"))
    }

    @Test
    fun testPEKKA() {
        // "P.E.K.K.A" -> "PEKKA" -> "pekka" -> "pekka" -> "pekka"
        assertEquals("pekka.png", formatCardNameToImageFilename("P.E.K.K.A"))
    }

    @Test
    fun testLeadingTrailingPeriods() {
        // ".Elite Barbarians." -> "Elite Barbarians" -> "elite barbarians" -> "elite-barbarians" -> "elite-barbarians"
        assertEquals("elite-barbarians.png", formatCardNameToImageFilename(".Elite Barbarians."))
    }

    @Test
    fun testPeriodsNoSpaces() {
        // "Three.Musketeers" -> "ThreeMusketeers" -> "threemusketeers" -> "threemusketeers" -> "threemusketeers"
        assertEquals("threemusketeers.png", formatCardNameToImageFilename("Three.Musketeers"))
    }

    @Test
    fun testPeriodsWithSpaces() {
        // "Three. Musketeers" -> "Three Musketeers" -> "three musketeers" -> "three-musketeers" -> "three-musketeers"
        assertEquals("three-musketeers.png", formatCardNameToImageFilename("Three. Musketeers"))
    }

    @Test
    fun testShortNameWithPeriods() {
        // "R.G.G." -> "RGG" -> "rgg" -> "rgg" -> "rgg"
        assertEquals("rgg.png", formatCardNameToImageFilename("R.G.G."))
    }

    @Test
    fun testMixedPeriodsAndSpacesAndTrimming() {
        // "  Mr. T. Rouble " -> trim "Mr. T. Rouble" -> no periods "Mr T Rouble" -> lc "mr t rouble" -> repl "mr-t-rouble" -> trimH "mr-t-rouble"
        assertEquals("mr-t-rouble.png", formatCardNameToImageFilename("  Mr. T. Rouble "))
    }

    @Test
    fun testPeriodsNextToOriginalHyphens() {
        // "Card-.A.-Name" -> trim "Card-.A.-Name" -> no periods "Card-A-Name" -> lc "card-a-name" -> repl "card-a-name" -> trimH "card-a-name"
        assertEquals("card-a-name.png", formatCardNameToImageFilename("Card-.A.-Name"))
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
