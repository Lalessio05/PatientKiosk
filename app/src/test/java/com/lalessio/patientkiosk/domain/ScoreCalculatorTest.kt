package com.lalessio.patientkiosk.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ScoreCalculatorTest {

    @Test
    fun `DLQI somma i punti senza moltiplicatore`() {
        //3+2+1+0+2+1+0+3+1+1 = 14, l'esempio delle slide
        val answers = listOf(3, 2, 1, 0, 2, 1, 0, 3, 1, 1)
            .withIndex().associate { (i, points) -> i to points }

        val result = ScoreCalculator.calculateScore(TestQuestionnaires.dlqi(), answers)

        assertEquals(1, result.blocks.size)
        assertEquals(14, result.blocks[0].score)
        assertEquals(30, result.blocks[0].maxScore)
        assertEquals("Effetto forte", result.blocks[0].band?.label)
        assertTrue(result.isComplete)
    }

    @Test
    fun `DLQI al massimo vale 30`() {
        val answers = (0 until 10).associateWith { 3 }

        val result = ScoreCalculator.calculateScore(TestQuestionnaires.dlqi(), answers)

        assertEquals(30, result.blocks[0].score)
        assertEquals("Effetto molto forte", result.blocks[0].band?.label)
    }

    @Test
    fun `WHO-5 moltiplica il punteggio grezzo per 4`() {
        //grezzo 3+3+3+3+3 = 15 -> 60
        val answers = (0 until 5).associateWith { 3 }

        val result = ScoreCalculator.calculateScore(TestQuestionnaires.who5(), answers)

        assertEquals(60, result.blocks[0].score)
        assertEquals(100, result.blocks[0].maxScore)
        assertEquals("Benessere adeguato", result.blocks[0].band?.label)
    }

    @Test
    fun `HADS produce due blocchi da 21 punti`() {
        //Ansia (indici pari) tutte a 3 = 21; Depressione (dispari) tutte a 0
        val answers = (0 until 14).associateWith { index -> if (index % 2 == 0) 3 else 0 }

        val result = ScoreCalculator.calculateScore(TestQuestionnaires.hads(), answers)

        assertEquals(2, result.blocks.size)

        val ansia = result.blocks[0]
        assertEquals("Ansia (HADS-A)", ansia.label)
        assertEquals(21, ansia.score)
        assertEquals(21, ansia.maxScore)
        assertEquals("Caso probabile", ansia.band?.label)

        val depressione = result.blocks[1]
        assertEquals(0, depressione.score)
        assertEquals("Nella norma", depressione.band?.label)
    }

    @Test
    fun `le sottoscale HADS non si sommano tra loro`() {
        //2 punti ovunque: 14 per sottoscala, 28 se si sommassero
        val answers = (0 until 14).associateWith { 2 }

        val result = ScoreCalculator.calculateScore(TestQuestionnaires.hads(), answers)

        //28 sarebbe fuori da ogni fascia, che arriva a 21
        assertEquals(14, result.blocks[0].score)
        assertEquals(14, result.blocks[1].score)
        assertEquals("Caso probabile", result.blocks[0].band?.label)
    }

    @Test
    fun `le fasce sono inclusive sugli estremi`() {
        val hads = TestQuestionnaires.hads()

        fun bandAt(score: Int): String? =
            hads.bands.firstOrNull { it.contains(score) }?.label

        assertEquals("Nella norma", bandAt(0))
        assertEquals("Nella norma", bandAt(7))
        assertEquals("Caso borderline", bandAt(8))
        assertEquals("Caso borderline", bandAt(10))
        assertEquals("Caso probabile", bandAt(11))
        assertEquals("Caso probabile", bandAt(21))
        //Fuori da ogni fascia: nessuna interpretazione, non un crash
        assertNull(bandAt(22))
    }

    @Test
    fun `una compilazione parziale produce un punteggio parziale`() {
        val answers = mapOf(0 to 3, 1 to 3)

        val result = ScoreCalculator.calculateScore(TestQuestionnaires.dlqi(), answers)

        assertEquals(6, result.blocks[0].score)
        assertEquals(2, result.answeredCount)
        assertEquals(10, result.questionCount)
        assertTrue(!result.isComplete)
    }
}