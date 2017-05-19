package com.froodulous

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for the Game of Life.
 * Created by luke on 04/12/2016.
 */
class KonwayTest {

    @Test
    fun testCanEvolveWorld() {
        val cells = Array(2, { Array(2, { Cell() }) })
        val initialWorld = World(cells)
        val rules = Rules(emptySet(), emptySet())

        val expectedWorld = World(cells)
        val evolvedWorld = initialWorld.evolve(rules)
        assertEquals(expectedWorld, evolvedWorld)
    }

    @Test
    fun testRulesAreAppliedCorrectly() {
        val cells = Array(2, { Array(2, { Cell() }) })
        cells[0][0] = Cell(alive = true)

        val initialWorld = World(cells)

        val rules = Rules(bornNumbers = setOf(1), surviveNumbers = setOf(1))

        val expectedCells = arrayOf(arrayOf(Cell(), Cell(true)), arrayOf(Cell(true), Cell(true)))

        val expectedWorld = World(expectedCells)

        val evolvedWorld = initialWorld.evolve(rules)
        assertEquals(expectedWorld, evolvedWorld)
    }

    @Test
    fun testCanCreateWorldFromString() {
        val worldFromString = worldFromString("#.\n..")

        val expectedCells = arrayOf(arrayOf(Cell(true), Cell()), arrayOf(Cell(), Cell()))
        val expectedWorld = World(expectedCells)
        assertEquals(expectedWorld, worldFromString)
    }

    @Test
    fun testWorldIsWrapped() {

        val world = worldFromString(".###.\n" +
                ".....\n" +
                ".....\n" +
                ".....\n" +
                ".....")
        val rules = Rules(bornNumbers = setOf(3), surviveNumbers = setOf(2, 3))

        val expectedWorld = worldFromString("..#..\n" +
                "..#..\n" +
                ".....\n" +
                ".....\n" +
                "..#..")

        assertEquals(expectedWorld, world.evolve(rules))
    }

    @Test
    fun testWorldIsNotWrapped() {
        val world = worldFromString(".###.\n" +
                ".....\n" +
                ".....\n" +
                ".....\n" +
                ".....", wrapWorld = false)
        val rules = Rules(bornNumbers = setOf(3), surviveNumbers = setOf(2, 3))

        val expectedWorld = worldFromString("..#..\n" +
                "..#..\n" +
                ".....\n" +
                ".....\n" +
                ".....", wrapWorld = false)

        assertEquals(expectedWorld, world.evolve(rules))
    }

    @Test
    fun testCanCreateOscillator() {
        val cells = Array(2, { Array(2, { Cell() }) })
        cells[0][0] = Cell(alive = true)

        val initialWorld = World(cells)

        val rules = Rules(bornNumbers = setOf(1, 3), surviveNumbers = setOf(1, 3))

        val evolvedWorld = initialWorld.evolve(rules).evolve(rules)

        val expectedCells = arrayOf(arrayOf(Cell(true), Cell()), arrayOf(Cell(), Cell()))
        val expectedWorld = World(expectedCells)

        print(evolvedWorld)
        assertEquals(expectedWorld, evolvedWorld)
    }
}
