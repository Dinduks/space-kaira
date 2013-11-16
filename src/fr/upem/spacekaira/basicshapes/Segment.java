package fr.upem.spacekaira.basicshapes;

import fr.upem.spacekaira.position.Position;

/**
 * Represents a segment
 * Has two {@code Position}s: {@code segmentStart} and {@code segmentEnd}
 */
public class Segment extends BasicShape {
    private Position segmentEnd;

    public Segment(Position segmentStart, Position segmentEnd) {
        setPosition(segmentStart);
        this.segmentEnd = segmentEnd;
    }

    public Position getSegmentsStart() {
        return getPosition();
    }

    public Position getSegmentEnd() {
        return segmentEnd;
    }
}
