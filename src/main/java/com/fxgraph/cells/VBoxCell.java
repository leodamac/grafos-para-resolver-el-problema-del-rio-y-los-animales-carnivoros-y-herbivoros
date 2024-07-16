package com.fxgraph.cells;

import com.fxgraph.graph.Graph;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class VBoxCell extends AbstractCell {
    private VBox vBox;
    public VBoxCell(VBox vBox){
        this.vBox = vBox;
    }
    
    @Override
    public Region getGraphic(Graph graph) {
            
            final Pane pane = new Pane(vBox);
            vBox.prefWidthProperty().bind(pane.prefWidthProperty());
            vBox.prefHeightProperty().bind(pane.prefHeightProperty());
            CellGestures.makeResizable(graph, pane);
            return pane;
    }

}