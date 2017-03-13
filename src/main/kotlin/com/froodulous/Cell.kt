package com.froodulous

/**
 * Class representing a single Cell.
 *
 * @param age The number of steps for which the cell has been alive.
 * @param alive Whether the cell is currently alive.
 */
data class Cell(val alive: Boolean = false, val age: Int = 0) {
    override fun equals(other: Any?): Boolean {
        return other is Cell && alive == other.alive
    }

    override fun hashCode(): Int {
        return alive.hashCode()
    }

    override fun toString(): String {
        return if (alive) "#" else "."
    }

    /**
     * Evolve the cell by one step.
     *
     * @param numNeighbours The number of living neighbours to use to determine the cell's new state.
     * @param rules The rules to apply to determine the cell's new state.
     * @return A new Cell representing the cell's new state according to the given Rules and number of neighbours.
     */
    fun evolve(numNeighbours: Int, rules: Rules): Cell {
        if (alive) {
            if (rules.survives(numNeighbours)) {
                return Cell(true, age + 1)
            }
        } else {
            if (rules.born(numNeighbours)) {
                return Cell(true)
            }
        }
        return Cell()
    }
}