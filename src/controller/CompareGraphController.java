/**
 * Controller for the Graph in the Compare FXML page
 * 
 * @author Jaime Rabago (jRabago@asu.edu)
 * @version April 17, 2015
 */
package controller;

import static view.MainNavigator.COMPARE_FXML;

import java.net.URL;
import java.util.ResourceBundle;

import model.Compare;
import model.ProInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import service.DaoFactory;
import service.CompareDao;
import service.ProInfoDao;
import session.UserSession;
import view.MainNavigator;

public class CompareGraphController implements Initializable {
	
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    
    @FXML
    private BarChart<String,Number> barGraph =
    new BarChart<String,Number>(xAxis, yAxis);
    
    final static String ATT = "Your Attempts";
    final static String YDS = "Your Yards";
    final static String TDS = "Your Touchdowns";
    final static String PRO_ATT = "Pro Attempts";
    final static String PRO_YDS = "Pro Yards";
    final static String PRO_TDS = "Pro Touchdowns";
	
	view.MainNavigator controller;
    
	
	public CompareGraphController() {
		controller = new view.MainNavigator();
	}
    
    private UserSession session = UserSession.getInstance();
    
    DaoFactory daoFact = (DaoFactory) DaoFactory.getDaoFactory();
    CompareDao compareDao = daoFact.getCompareDao();
    ProInfoDao proInfoDao = daoFact.getProInfoDao();
	ProInfo info = new ProInfo();
    Compare comp = new Compare();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		int statsId = compareDao.findStatsId(session.getGameId());
		
		info = proInfoDao.findProInfo(statsId);
		comp = compareDao.inputStats(session.getGameId(), statsId);
        //Setup Graph
        barGraph.setTitle("Comparison Summary");
        
        xAxis.setLabel("Value");
        yAxis.setLabel("Stat");
        
        //Add data
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Attempts");
        series1.getData().add(new XYChart.Data(ATT, comp.getUserAttempts()));
        series1.getData().add(new XYChart.Data(PRO_ATT, comp.getProAttempts()));
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Yards");
        series2.getData().add(new XYChart.Data(YDS, comp.getUserYards()));
        series2.getData().add(new XYChart.Data(PRO_YDS, comp.getProYards()));
        
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Touchdowns");
        series3.getData().add(new XYChart.Data(TDS, comp.getUserTouchdowns()));
        series3.getData().add(new XYChart.Data(PRO_TDS, comp.getProTouchdowns()));
        
        barGraph.getData().addAll(series1, series2, series3);
	}
	
	/**
	 * Changes the current FXML page to the compare.fxml
	 * 
	 * @param e
	 */
	@FXML
	private void changeToCompare(ActionEvent e) {
		MainNavigator.loadScreen(COMPARE_FXML);
	}
}
