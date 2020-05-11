package segGroupCW;

import javafx.print.PrinterJob;
import javafx.scene.Node;

public class PrintController {
	private PrinterJob job;
	private Node node;
	public PrintController(Node node) {
		job = PrinterJob.createPrinterJob();
		this.node = node;
	}
	public void print(){
		if(job != null){
			job.showPrintDialog(App.thisStage); // Window must be your main Stage
			job.printPage(node);
			job.endJob();
		}
	}
}
