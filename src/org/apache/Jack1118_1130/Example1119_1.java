package org.apache.Jack1118_1130;

import java.util.Iterator;

import javax.swing.JFrame;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.assignment.DataShapeAction;
import prefuse.action.assignment.ShapeAction;
import prefuse.action.filter.GraphDistanceFilter;
import prefuse.action.filter.VisibilityFilter;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.expression.Predicate;
import prefuse.data.expression.parser.ExpressionParser;
import prefuse.data.io.GraphMLReader;
import prefuse.render.AxisRenderer;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.render.Renderer;
import prefuse.render.RendererFactory;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.GraphLib;
import prefuse.visual.VisualItem;
/**
 * 导入socialnet.xml数据集按照性别赋予不同的图形表示
 * @author Jack
 *
 */
public class Example1119_1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//------------   1    ------------
		Graph graph = null;
		try {
			graph = GraphLib.getGrid(4, 5);
			System.out.println(graph.removeEdge(8));
			Iterator<Node> it = graph.nodes();
			while(it.hasNext()){
				Node i = it.next();
				System.out.println("first child:"+i.getFirstChild());
				System.out.println("graph:"+i.getGraph());
				System.out.println("table:"+i.getTable());
				System.out.println("row:"+i.getRow());
				
			}
//			System.out.println("第十个点为:"+graph.getNode(10).c);
//			graph.addEdge(3, 5);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error");
			System.exit(1);
		}
		
		//------------   2    ------------
		Visualization vis = new Visualization();
		vis.add("graph", graph);

		//------------   3    ------------
//		LabelRenderer label = new LabelRenderer("name");
//		label.setRoundedCorner(10, 10);
		
		
		vis.setRendererFactory(new DefaultRendererFactory());
		
		//------------   4    ------------
		int[] palette = new int[]{ColorLib.rgb(255, 180, 180),ColorLib.rgb(190, 190, 255)};
//		DataColorAction fill = new DataColorAction("graph.nodes" , "gender" , Constants.NOMINAL, VisualItem.FILLCOLOR,palette);
		ColorAction text = new ColorAction("graph.nodes", VisualItem.TEXTCOLOR, ColorLib.gray(0));
		ColorAction edges = new ColorAction("graph.edges", VisualItem.STROKECOLOR, ColorLib.gray(200));
		
		int[] shapes = new int[]{ Constants.SHAPE_RECTANGLE, Constants.SHAPE_DIAMOND };
//		DataShapeAction shape = new DataShapeAction("graph.nodes", "gender", shapes);
		
		ActionList color = new ActionList();
//		color.add(fill);
		color.add(text);
		color.add(edges);
//		color.add(shape);
		
		
		ActionList layout = new ActionList(Activity.INFINITY);
		layout.add(new ForceDirectedLayout("graph"));
		layout.add(new RepaintAction());
		
		vis.putAction("color", color);
		vis.putAction("layout", layout);
		
		//------------   5    ------------
		Display display = new Display(vis);
		display.setSize(750, 700);
		display.pan(250, 250);
		display.addControlListener(new DragControl());
		display.addControlListener(new PanControl());
		display.addControlListener(new ZoomControl());
		display.addControlListener(new WheelZoomControl());
		display.addControlListener(new FocusControl(1));
		
		display.addControlListener(new ZoomToFitControl());
		//------------   6    ------------
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(display);
		jf.pack();
		jf.setVisible(true);
		
		vis.run("color");
		vis.run("layout");
		
	}

}
