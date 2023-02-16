package iec61850.nodes.gui.other;

/**
 * @description Selection for plotting XY
 */

/**
 * A pair of values X and Y
 */
public record NHMIPoint<X, Y>(X value1, Y value2) {
}
