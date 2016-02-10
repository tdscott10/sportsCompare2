/**
 * Controller for the Graph in the Compare FXML page
 *
 * @author Taylor Scitt (tdscott2@asu.edu)
 * @version April 20, 2015
 */
package controller;

import static view.MainNavigator.LOGIN_FXML;
import static view.MainNavigator.QCOMPARESCREEN_FXML;

import java.net.URL;
import java.util.ResourceBundle;

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
import session.UserSession;
import view.MainNavigator;
import model.Compare;

public class QuickCompareGraphController implements Initializable {
    
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
    
    
    public QuickCompareGraphController() {
        controller = new view.MainNavigator();
    }
    
    private UserSession session = UserSession.getInstance();
    
    DaoFactory daoFact = (DaoFactory) DaoFactory.getDaoFactory();
    CompareDao compareDao = daoFact.getCompareDao();
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Compare proStats =
        compareDao.qcInputStats(session.getProStatsId());
        
        //Setup Graph
        barGraph.setTitle("Comparison Summary");
        
        xAxis.setLabel("Value");
        yAxis.setLabel("Stat");
        
        //Add data
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Attempts");
        series1.getData().add(new XYChart.Data(ATT, session.getUserAtt()));
        series1.getData().add(new XYChart.Data(PRO_ATT, proStats.getProAttempts()));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Yards");
        series2.getData().add(new XYChart.Data(YDS, session.getUserYds()));
        series2.getData().add(new XYChart.Data(PRO_YDS, proStats.getProYards()));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Touchdowns");
        series3.getData().add(new XYChart.Data(TDS, session.getUserTds()));
        series3.getData().add(new XYChart.Data(PRO_TDS, proStats.getProTouchdowns()));
        
         barGraph.getData().addAll(series1, series2, series3);
    }
    
    /**
     * Changes the current FXML page to the home.fxml
     *
     * @param e
     */
    @FXML
    private void changeToLogin(ActionEvent e) {
        MainNavigator.loadScreen(LOGIN_FXML);
    }
    
    /**
     * Changes the current FXML page to the home.fxml
     *
     * @param e
     */
    @FXML
    private void changeToCompare(ActionEvent e) {
        MainNavigator.loadScreen(QCOMPARESCREEN_FXML);
    }
}
