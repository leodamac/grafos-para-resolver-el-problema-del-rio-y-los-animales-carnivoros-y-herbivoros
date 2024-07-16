package com.fxgraph.cells;

import com.fxgraph.graph.Graph;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class PaneCell extends AbstractCell {
    private Pane p;
    public PaneCell(Pane np){
        this.p = new Pane(np);
    }
    
    @Override
    public Region getGraphic(Graph graph) {
            final Pane pane = new Pane(p);
            p.prefWidthProperty().bind(pane.prefWidthProperty());
            p.prefHeightProperty().bind(pane.prefHeightProperty());
            CellGestures.makeResizable(graph, pane);
            return pane;
    }

}