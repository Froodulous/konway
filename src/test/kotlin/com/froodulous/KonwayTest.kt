package com.froodulous

import org.junit.Assert.*
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
    fun testCanGetNumNeighboursWrapped() {
        val cells = Array(2, { Array(2, { Cell() }) })
        cells[0][0] = Cell(alive = true)

        val world = World(cells)

        assertEquals(0, world.getNumNeighboursWrapped(0, 0))
        assertEquals(1, world.getNumNeighboursWrapped(0, 1))
        assertEquals(1, world.getNumNeighboursWrapped(1, 0))
        assertEquals(1, world.getNumNeighboursWrapped(1, 1))
    }

    @Test
    fun testCanGetNumNeighboursBounded() {
        val cells = Array(2, { Array(2, { Cell() }) })
        cells[0][0] = Cell(alive = true)

        val world = World(cells)

        assertEquals(0, world.getNumNeighboursBounded(0, 0))
        assertEquals(1, world.getNumNeighboursBounded(0, 1))
        assertEquals(1, world.getNumNeighboursBounded(1, 0))
        assertEquals(1, world.getNumNeighboursBounded(1, 1))
    }

    @Test
    fun testRulesAreAppliedCorrectly() {
        val cells = Array(2, { Array(2, { Cell() }) })
        cells[0][0] = Cell(alive = true)

        val initialWorld = World(cells)

        val rules = Rules(bornNumbers = setOf(1), surviveNumbers = emptySet())

        val expectedCells = arrayOf(arrayOf(Cell(), Cell(true)), arrayOf(Cell(true), Cell(true)))

        val expectedWorld = World(expectedCells)

        val evolvedWorld = initialWorld.evolve(rules)
        assertEquals(expectedWorld, evolvedWorld)
    }

    @Test
    fun testCanCreateOscillator() {
        val cells = Array(2, { Array(2, { Cell() }) })
        cells[0][0] = Cell(alive = true)

        val initialWorld = World(cells)

        val rules = Rules(bornNumbers = setOf(1, 3), surviveNumbers = emptySet())

        val evolvedWorld = initialWorld.evolve(rules).evolve(rules)

        val expectedCells = arrayOf(arrayOf(Cell(true), Cell()), arrayOf(Cell(), Cell()))
        val expectedWorld = World(expectedCells)

        print (evolvedWorld)
        assertEquals(expectedWorld, evolvedWorld)
    }
}