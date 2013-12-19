package fr.upem.spacekaira.shape;

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

/**
 * Performed vector transformation (local -> world -> screen) and shape drawing
 */
public class Draw {
    private OBBViewportTransform obb;
    private final int HEIGHT;
    private final int WIDTH;
    private float scale = 1;

    public Draw(int width , int height) {
        HEIGHT = height; WIDTH = width;
        obb = new OBBViewportTransform();
        obb.setCamera(0, 0, 1);
        obb.setExtents(width/2,height/2);
    }

    public void getWorldVectorToScreen(Vec2 argWorld, Vec2 argScreen) {
        obb.getWorldToScreen(argWorld, argScreen);
    }

    public boolean isInScreen(Vec2 worldVector) {
        Vec2 v = getWorldVectorToScreen(worldVector);
        return v.x >= 0 && v.x <= WIDTH && v.y >= 0 && v.y <= HEIGHT;
    }

    public Vec2 getWorldVectorToScreen(Vec2 argWorld) {
        Vec2 argScreen = new Vec2();
        obb.getWorldToScreen(argWorld, argScreen);
        return  argScreen;
    }

    public void setCamera(float x, float y, float scale) {
        this.scale = scale;
        obb.setCamera(x,y,scale);
    }

    public void setCenter(float x, float y) {
        obb.setCenter(x,y);
    }

    public float getCameraScale() {
        return scale;
    }

    public void setCenter(Vec2 vec2) {
        obb.setCenter(vec2);
    }

    private final Vec2Array vec2Array = new Vec2Array();
    private final int circlePoints = 13;

    private void generateCircle(Vec2 argCenter, float argRadius, Vec2[] argPoints, int argNumPoints) {
        float inc = MathUtils.TWOPI / argNumPoints;

        for (int i = 0; i < argNumPoints; i++) {
            argPoints[i].x = (argCenter.x + MathUtils.cos(i * inc) * argRadius);
            argPoints[i].y = (argCenter.y + MathUtils.sin(i * inc) * argRadius);
        }
    }

    public void drawCircle(Fixture fixture,Graphics2D graphics) {
        Brush brush = (Brush) fixture.getUserData(); if(brush == null) return;
        CircleShape circleShape = (CircleShape) fixture.getShape();
        Vec2 centroid = fixture.getBody().getWorldPoint(circleShape.m_p);
        float radius = circleShape.getRadius();

        Vec2[] vecs = vec2Array.get(circlePoints);
        generateCircle(centroid, radius, vecs, circlePoints);
        Arrays.asList(vecs).forEach(v -> { v.set(getWorldVectorToScreen(v)); });
        drawPolygon(vecs, circlePoints, brush, graphics);
    }

    public void drawEdge(Fixture fixture,Graphics2D graphics) {
        Brush brush = (Brush) fixture.getUserData(); if(brush == null) return;
        EdgeShape edgeShape = (EdgeShape)fixture.getShape();
        Vec2 vertex1 = getWorldVectorToScreen(fixture.getBody().getWorldPoint(edgeShape.m_vertex1));
        Vec2 vertex2 = getWorldVectorToScreen(fixture.getBody().getWorldPoint(edgeShape.m_vertex2));

        graphics.setPaint(brush.getColor());
        graphics.drawLine(
                Math.round(vertex1.x),
                Math.round(vertex1.y),
                Math.round(vertex2.x),
                Math.round(vertex2.y));

    }

    public void drawPolygon(Fixture fixture,Graphics2D graphics) {
        Brush brush = (Brush) fixture.getUserData(); if(brush == null) return;
        PolygonShape polygonShape = (PolygonShape) fixture.getShape();

        Vec2[] vecs = vec2Array.get(polygonShape.getVertexCount());
        for (int i=0;i<polygonShape.getVertexCount();i++) vecs[i].set(polygonShape.getVertex(i));
        Arrays.asList(vecs).forEach(v -> v.set(getWorldVectorToScreen(fixture.getBody().getWorldPoint(v))));
        drawPolygon(vecs, polygonShape.getVertexCount(), brush, graphics);
    }


    private void drawPolygon(Vec2[] vertices, int vertexCount, Brush brush, Graphics2D graphics) {
        int[] xPoints = new int[vertexCount];
        int[] yPoints = new int[vertexCount];

        for (int i=0,n=vertexCount; i<n;i++) {
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

    public static boolean isZero(float f) {
        return Math.abs(f) > 0.5;
    }
}
