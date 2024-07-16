package com.fxgraph.edges;

import com.fxgraph.graph.Graph;
import com.fxgraph.graph.ICell;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Edge extends AbstractEdge {

	private transient final StringProperty textProperty;
        private Color color = Color.color(0, 0, 0);
	public Edge(ICell source, ICell target) {
		this(source, target, false);
	}

	public Edge(ICell source, ICell target, boolean isDirected) {
		super(source, target, isDirected);
		textProperty = new SimpleStringProperty();
	}
        public Edge(ICell source, ICell target, boolean isDirected, Color color) {
		super(source, target, isDirected);
		textProperty = new SimpleStringProperty();
                this.color = color;
	}

	@Override
	public EdgeGraphic getGraphic(Graph graph) {
		return new EdgeGraphic(graph, this, textProperty, color);
	}

	public StringProperty textProperty() {
		return textProperty;
	}

	public static class EdgeGraphic extends AbstractEdgeGraphic {
                private final Line line;
		public EdgeGraphic(Graph graph, Edge edge, StringProperty textProperty, Color color) {
			line = new Line();
                        line.setStroke(color);
			final DoubleBinding sourceX = edge.getSource().getXAnchor(graph);
			final DoubleBinding sourceY = edge.getSource().getYAnchor(graph);
			final DoubleBinding targetX = edge.getTarget().getXAnchor(graph);
			final DoubleBinding targetY = edge.getTarget().getYAnchor(graph);

			if (edge.isDirected()) {
				Region target = graph.getGraphic(edge.getTarget());
				setupArrowIntersect(target, sourceX, sourceY, targetX, targetY);
				group.getChildren().add(arrow);
			} else {
				line.startXProperty().bind(sourceX);
				line.startYProperty().bind(sourceY);

				line.endXProperty().bind(targetX);
				line.endYProperty().bind(targetY);
				group.getChildren().add(line);
			}


			final DoubleProperty textWidth = new SimpleDoubleProperty();
			final DoubleProperty textHeight = new SimpleDoubleProperty();
			text.textProperty().bind(textProperty);
			text.getStyleClass().add("edge-text");
			text.xProperty().bind(sourceX.add(targetX).divide(2).subtract(textWidth.divide(2)));
			text.yProperty().bind(sourceY.add(targetY).divide(2).subtract(textHeight.divide(2)));
			final Runnable recalculateWidth = () -> {
				textWidth.set(text.getLayoutBounds().getWidth());
				textHeight.set(text.getLayoutBounds().getHeight());
			};
			text.parentProperty().addListener((obs, oldVal, newVal) -> recalculateWidth.run());
			text.textProperty().addListener((obs, oldVal, newVal) -> recalculateWidth.run());
			group.getChildren().add(text);
			getChildren().add(group);
		}

		public Line getLine() {
			return line;
		}

	}

}