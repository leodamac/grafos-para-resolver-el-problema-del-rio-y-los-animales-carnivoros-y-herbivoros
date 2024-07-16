package com.mycompany.decision;

import com.fxgraph.cells.HBoxCell;
import com.fxgraph.edges.Edge;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.ICell;
import com.fxgraph.graph.Model;
import com.fxgraph.layout.ForceDirectedLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
        com.mycompany.decision.Graph<Escenario> grafo = null;
        Escenario escenario = null;

	@Override
	public void start(Stage stage) throws Exception {
		Graph graph = new Graph();
                inicializarGrafo();
		// Add content to graph
		populateGraph(graph);

		// Layout nodes
		//AbegoTreeLayout layout = new AbegoTreeLayout(200, 200, Location.Top);
		//graph.layout(layout);
                //graph.layout(new RandomLayout());
                graph.layout(new ForceDirectedLayout());
		// Configure interaction buttons and behavior
		graph.getViewportGestures().setPanButton(MouseButton.SECONDARY);
		graph.getNodeGestures().setDragButton(MouseButton.PRIMARY);
                BorderPane bP = new BorderPane(graph.getCanvas());
                bP.setMinSize(800, 600);
                bP.setPrefSize(800,600);
                Scene scene = new Scene(bP);
               
		// Display the graph
		stage.setScene(scene);
		stage.show();
	}

	private void populateGraph(Graph graph) {
		final Model model = graph.getModel();
                
		graph.beginUpdate();
                Map<Node<Escenario>, ICell> nodosMap = new LinkedHashMap<>();
                for(Node<Escenario> e: grafo.nodos){
                    final ICell cell1 = new HBoxCell(dibujarEscenario(e.getValue()));
                    model.addCell(cell1);
                    nodosMap.put(e, cell1);
                }
                
                for(Node<Escenario> e: nodosMap.keySet()){
                    for(com.mycompany.decision.Edge<Escenario> ed: grafo.adjList.get(e)){
                        Edge edge = null;
                        if(ed.weight>0){
                            edge = new Edge(nodosMap.get(e), nodosMap.get(ed.getDest()), false, Color.color(0, 1, 0));
                        }else{
                            edge = new Edge(nodosMap.get(e), nodosMap.get(ed.getDest()), false);
                        }
                        edge.textProperty().set(""+ed.getWeight());
                        model.addEdge(edge);
                        //model.addEdge(nodosMap.get(e), nodosMap.get(ed.getDest()));
                    }
                }
                
		graph.endUpdate();
	}
        
    private void generarEscenariosPosibles(){
        Stack<Escenario> l = new Stack();
        this.escenario = new Escenario();
        l.add(this.escenario);
        while(!l.isEmpty()){
            Escenario temp = l.pop();
            System.out.println(temp);
            for(Escenario e: Escenario.generarEscenarios(temp)){
                if(!grafo.nodos.contains(new Node(e, e.puedeGenerar ? 1 : 0))){
                    l.add(e);
                }
                grafo.conect(temp, 1, e, e.puedeGenerar ? 1 : 0, e.isSeguro()?1:0);
            }
        }
    }
    
    private void inicializarGrafo(){
        this.grafo = new com.mycompany.decision.Graph();
        this.generarEscenariosPosibles();
    }
    
    
    public HBox dibujarEscenario(Escenario e){
        HBox hBoxEscenario = new HBox();
        HBox hBoxOrillaI = dibujarAnimales(e.getIzquierda());
        hBoxEscenario.getChildren().add(hBoxOrillaI);
        VBox vBoxBarca = new VBox(new ImageView(new Image("barca.png", 25,25, true, true)));
        vBoxBarca.setMinWidth(50);
        vBoxBarca.setAlignment(e.getBarca().getPos()=='d'?Pos.CENTER_RIGHT:Pos.CENTER_LEFT);
        hBoxEscenario.getChildren().add(vBoxBarca);
        HBox hBoxOrillaD = dibujarAnimales(e.getDerecha());
        hBoxEscenario.getChildren().add(hBoxOrillaD);
        int tipo = 0;
        if(e.puedeGenerar){
            tipo = 1;
            if(e.getDerecha().getNAnimales() == 6){
                tipo = 2;
            }
        }else{
            if(e.getIzquierda().getNAnimales() == 6){
                tipo = 2;
            }else{
                tipo =3;
                if(!e.getDerecha().isCarnivorosMayorHerviboros() && !e.getIzquierda().isCarnivorosMayorHerviboros()){
                    tipo = 4;
                }
            }
        }
        String stilo = tipoEscenario(tipo);
        hBoxEscenario.setStyle(stilo);
        return hBoxEscenario;
    }
    
    public String tipoEscenario(int tipo){
        String stilo = "-fx-border-width: 2 ; \n" +
"    -fx-border-style: segments(10, 15, 15, 15)  line-cap round ;\n";;
        switch(tipo){
            case 1:
                stilo += "-fx-border-color: yellow ;";
                break;
            case 2:
                stilo += "-fx-border-color: green ;";
                break;
            case 3:
                stilo += "-fx-border-color: red ;";
                break;    
            default:
                stilo += "-fx-border-color: blue ;";
                break;
        }
        return stilo;
    }
    
    public HBox dibujarAnimales(Orilla o){
        HBox hBoxAnimales = new HBox();
        VBox vBoxCarnivoros = dibujarCarnivoros(o);
        VBox vBoxHerbivoros = dibujarHerbivoros(o);
        hBoxAnimales.getChildren().addAll(vBoxCarnivoros, vBoxHerbivoros);
        return hBoxAnimales;
    }
    
    public VBox dibujarCarnivoros(Orilla o){
        VBox vBoxCarnivoros = new VBox();
        for (int i = 0; i<o.getNCarnivoros(); i++){
            vBoxCarnivoros.getChildren().add(new ImageView(new Image("carnivoro.png", 25,25, true, true)));
        }
        return vBoxCarnivoros;
    }
    
    public VBox dibujarHerbivoros(Orilla o){
        VBox vBoxHerbivoros = new VBox();
        for (int i = 0; i<o.getNHerviboros(); i++){
            vBoxHerbivoros.getChildren().add(new ImageView(new Image("herbivoro.png", 25,25, true, true)));
        }
        return vBoxHerbivoros;
    }

}