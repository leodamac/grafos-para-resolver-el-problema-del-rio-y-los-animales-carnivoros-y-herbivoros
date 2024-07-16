package com.fxgraph.layout;

import com.fxgraph.graph.Graph;
import com.fxgraph.graph.ICell;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ForceDirectedLayout implements Layout {

    private final double width;
    private final double height;
    private final double attractionFactor;
    private final double repulsionFactor;
    private final int iterations;
    
    public ForceDirectedLayout() {
        this(800, 600, 0.0001, 100000, 1000); // Adjusted width, height, and forces
    }

    public ForceDirectedLayout(double width, double height, double attractionFactor, double repulsionFactor, int iterations) {
        this.width = width;
        this.height = height;
        this.attractionFactor = attractionFactor;
        this.repulsionFactor = repulsionFactor;
        this.iterations = iterations;
    }

    @Override
    public void execute(Graph graph) {
        Map<ICell, Point> positions = new HashMap<>();
        Random random = new Random(8);

        // Initialize positions randomly
        System.out.println(random.nextDouble());
        for (ICell cell : graph.getModel().getAllCells()) {
            positions.put(cell, new Point(random.nextDouble() * width, random.nextDouble() * height));
            //positions.put(cell, new Point((k/100), (k/100)));

        }

        // Iterate to adjust positions
        for (int i = 0; i < iterations; i++) {
            Map<ICell, Point> forces = new HashMap<>();

            // Calculate repulsive forces
            for (ICell v : positions.keySet()) {
                forces.put(v, new Point(0, 0));
                for (ICell u : positions.keySet()) {
                    if (v != u) {
                        Point delta = positions.get(v).subtract(positions.get(u));
                        double distance = Math.max(delta.magnitude(), 0.01);
                        Point repulsion = delta.normalize().multiply(repulsionFactor / (distance * distance));
                        forces.get(v).add(repulsion);
                    }
                }
            }
                
            // Calculate attractive forces
            for (ICell v : positions.keySet()) {
                for (ICell u : v.getCellChildren()) {
                    Point delta = positions.get(v).subtract(positions.get(u));
                    double distance = Math.max(delta.magnitude(), 0.01);
                    Point attraction = delta.normalize().multiply(-attractionFactor * (distance * distance));
                    forces.get(v).add(attraction);
                    forces.get(u).add(attraction.multiply(-1));
                }
            }

            // Update positions
            for (ICell v : positions.keySet()) {
                Point pos = positions.get(v).add(forces.get(v));
                positions.put(v, pos);
            }
        }

        // Apply positions to the graph
        for (ICell cell : positions.keySet()) {
            graph.getGraphic(cell).setLayoutX(positions.get(cell).x);
            graph.getGraphic(cell).setLayoutY(positions.get(cell).y);
        }
    }
    
    private Point ensureBounds(Point pos) {
            double x = Math.max(0, Math.min(width, pos.x));
            double y = Math.max(0, Math.min(height, pos.y));
            return new Point(x, y);
    }

    private static class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        

        Point subtract(Point other) {
            return new Point(this.x - other.x, this.y - other.y);
        }

        Point add(Point other) {
            this.x += other.x;
            this.y += other.y;
            return this;
        }

        Point multiply(double factor) {
            return new Point(this.x * factor, this.y * factor);
        }

        Point normalize() {
            double magnitude = magnitude();
            return new Point(this.x / magnitude, this.y / magnitude);
        }

        double magnitude() {
            return Math.sqrt(x * x + y * y);
        }
    }
}
