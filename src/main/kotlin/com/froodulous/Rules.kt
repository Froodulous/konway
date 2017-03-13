package com.froodulous

/**
 * Rules governing the evolution of a World.
 *
 * @param bornNumbers The Set of numbers which, if a dead or living cell has that many living neighbours, causes that
 * cell to be alive in the next generation.
 * @param surviveNumbers The Set of numbers which, if a living cell has that many living neighbours, causes a cell to
 * survive.
 */
class Rules(val bornNumbers: Set<Int>, val surviveNumbers: Set<Int>)

/**
 * Check if a living cell survives to the next generation according to these rules and the cell's number of living
 * neighbours.
 */
fun Rules.survives(numNeighbours: Int) = bornNumbers.contains(numNeighbours) || surviveNumbers.contains(numNeighbours)

/**
 * Check if a dead cell is born in to the next generation according to these rules and the cell's number of living
 * neighbours.
 */
fun Rules.born(numNeighbours: Int) = bornNumbers.contains(numNeighbours)