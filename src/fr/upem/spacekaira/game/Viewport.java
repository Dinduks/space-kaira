package fr.upem.spacekaira.game;

import fr.upem.spacekaira.brush.Brush;
import fr.upem.spacekaira.shape.characters.Ship;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.pooling.arrays.Vec2Array;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

/**
 * Performs vector transformation (local - world - screen) and shape drawing
 */
public class Viewport {
    private final int screenHeight;
    private final int screenWidth;
    private final Vec2Array vec2Array = new Vec2Array();
    private final int circlePoints = 13;
    private OBBViewportTransform obb;
    private float scale = 1;

    public Viewport(int screenWidth, int screenHeight) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        obb = new OBBViewportTransform();
        obb.setCamera(0, 0, 1);
        obb.setExtents(screenWidth / 2, screenHeight / 2);
    }

    public boolean isInScreen(Vec2 worldVector) {
        Vec2 v = getWorldVectorToScreen(worldVector);
        return v.x >= 0 && v.x <= screenWidth &&
               v.y >= 0 && v.y <= screenHeight;
    }

    public Vec2 getWorldVectorToScreen(Vec2 argWorld) {
        Vec2 argScreen = new Vec2();
        obb.getWorldToScreen(argWorld, argScreen);
        return argScreen;
    }

    public void setCamera(float x, float y, float scale) {
        this.scale = scale;
        obb.setCamera(x, y, scale);
    }

    public float getCameraScale() {
        return scale;
    }

    public void setCenter(Vec2 vec2) {
        obb.setCenter(vec2);
    }

    private void generateCircle(Vec2 argCenter, float argRadius,
                                Vec2[] argPoints, int argNumPoints) {
        float inc = MathUtils.TWOPI / argNumPoints;

        for (int i = 0; i < argNumPoints; i++) {
            argPoints[i].x = (argCenter.x + MathUtils.cos(i * inc) * argRadius);
            argPoints[i].y = (argCenter.y + MathUtils.sin(i * inc) * argRadius);
        }
    }

    public void drawCircle(Fixture fixture,Graphics2D graphics) {
        Brush brush = (Brush) fixture.getUserData();
        if (brush == null) return;
        CircleShape circleShape = (CircleShape) fixture.getShape();
        Vec2 centroid = fixture.getBody().getWorldPoint(circleShape.m_p);
        float radius = circleShape.getRadius();

        Vec2[] vecs = vec2Array.get(circlePoints);
        generateCircle(centroid, radius, vecs, circlePoints);
        Arrays.asList(vecs).forEach(v -> { v.set(getWorldVectorToScreen(v)); });
        drawPolygon(vecs, circlePoints, brush, graphics);
    }

    public void drawEdge(Fixture fixture, Graphics2D graphics) {
        Brush brush = (Brush) fixture.getUserData(); if(brush == null) return;
        EdgeShape edgeShape = (EdgeShape)fixture.getShape();
        Vec2 vertex1 = getWorldVectorToScreen(
                fixture.getBody().getWorldPoint(edgeShape.m_vertex1));
        Vec2 vertex2 = getWorldVectorToScreen(
                fixture.getBody().getWorldPoint(edgeShape.m_vertex2));

        graphics.setPaint(brush.getColor());
        graphics.drawLine(
                Math.round(vertex1.x),
                Math.round(vertex1.y),
                Math.round(vertex2.x),
                Math.round(vertex2.y));
    }

    public void drawPolygon(Fixture fixture, Graphics2D graphics) {
        Brush brush = (Brush) fixture.getUserData(); if(brush == null) return;
        PolygonShape polygonShape = (PolygonShape) fixture.getShape();

        Vec2[] vecs = vec2Array.get(polygonShape.getVertexCount());
        for (int i=0;i<polygonShape.getVertexCount();i++) {
            vecs[i].set(polygonShape.getVertex(i));
        }
        Arrays.asList(vecs).forEach(v -> {
            v.set(getWorldVectorToScreen(fixture.getBody().getWorldPoint(v)));
        });
        drawPolygon(vecs, polygonShape.getVertexCount(), brush, graphics);
    }

    private void drawPolygon(Vec2[] vertices, int vertexCount, Brush brush,
                             Graphics2D graphics) {
        int[] xPoints = new int[vertexCount];
        int[] yPoints = new int[vertexCount];

        for (int i=0; i < vertexCount;i++) {
            Vec2 vertex = vertices[i];
            xPoints[i] =  Math.round(vertex.x);
            yPoints[i] =  Math.round(vertex.y);
        }
        graphics.setPaint(brush.getColor());
        if(brush.isOpaque()) {
            graphics.fillPolygon(xPoints,yPoints,vertexCount);
        } else {
            graphics.drawPolygon(xPoints, yPoints,vertexCount);
        }
    }

    /**
     * Returns a random position in the current view port. The position will be
     * at least 10f away from the ship.
     * @param ship the player ship
     * @return A position on the current view port
     */
    public Vec2 getRandomPositionForBomb(Ship ship) {
        Random random = new Random();
        int halfScreenWidth = (int) ((screenWidth * 0.8) / 2 / getCameraScale());
        int halfScreenHeight = (int) ((screenHeight * 0.8) / 2 / getCameraScale());
        float randomXPosition = (float) (random.nextInt(halfScreenWidth - 10)) + 10;
        float randomYPosition = (float) (random.nextInt(halfScreenHeight) - 10) + 10;

        float negOrPos;
        negOrPos = random.nextBoolean() ? 1 : -1;
        float x = (ship.getPosition().x) + randomXPosition * negOrPos;
        negOrPos = random.nextBoolean() ? 1 : -1;
        float y = (ship.getPosition().y) + randomYPosition * negOrPos;

        return new Vec2(x, y);
    }

    public int getScreenHeight() { return screenHeight; }

    public int getScreenWidth() {
        return screenWidth;
    }
}
