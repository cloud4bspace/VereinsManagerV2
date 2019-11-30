package space.cloud4b.verein.view.mainframe;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import space.cloud4b.verein.model.verein.adressbuch.Mitglied;
import space.cloud4b.verein.view.browser.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import space.cloud4b.verein.MainApp;
import space.cloud4b.verein.model.verein.Verein;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class MainFrameController {

    int anzMitglieder;
    int anzTermine;
    Mitglied mitglied = null;

    // Reference to the main application
    private MainApp mainApp;
    @FXML
    private Label titleLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private MenuBar hMenuBarTop;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private MenuItem infoMenuItem;
    @FXML
    private MenuItem helpMenuItem;
    @FXML
    private MenuItem helpMenuItemII;
    @FXML
    private Label titleLeftLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private VBox vMenuBarLeftContainer;
    @FXML
    private TextArea meldungAusgabeText;
    @FXML
    private Label circleLabelI;
    @FXML
    private Label circleLabelII;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.titleLabel.setText(mainApp.getVerein().getVereinsName()); // bei Initialize geht es nicht...
        this.dateLabel.setText(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
        this.anzMitglieder = mainApp.getVerein().getAdressBuch().getMitgliederListeAsArrayList().size();
        this.anzTermine = mainApp.getVerein().getKalender().getTerminListeAsArrayList().size();
        this.mitglied = mainApp.getVerein().getAdressBuch().getMitgliederListeAsArrayList().get(0);
        circleLabelI.setText(this.anzMitglieder + " Mitglieder");
        circleLabelI.setContentDisplay(ContentDisplay.CENTER);
        circleLabelII.setText(this.anzTermine + " Termine");
        circleLabelII.setContentDisplay(ContentDisplay.CENTER);
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        this.mainApp = mainApp;
        Text iconTxt;

        iconTxt = GlyphsDude.createIcon(FontAwesomeIcon.USER, "20px");
        iconTxt.setFill(javafx.scene.paint.Color.WHITE);
        infoLabel.setGraphic(iconTxt);
        infoLabel.setText(System.getProperty("user.name"));

        iconTxt = GlyphsDude.createIcon(FontAwesomeIcon.SIGN_OUT, "10px");
        iconTxt.setFill(Color.BLACK);
        exitMenuItem.setGraphic(iconTxt);
        exitMenuItem.setText("beenden");

        iconTxt = GlyphsDude.createIcon(FontAwesomeIcon.INFO, "10px");
        iconTxt.setFill(Color.BLACK);
        infoMenuItem.setGraphic(iconTxt);

        iconTxt = GlyphsDude.createIcon(FontAwesomeIcon.QUESTION, "10px");
        iconTxt.setFill(Color.BLACK);
        helpMenuItem.setGraphic(iconTxt);

        titleLeftLabel.setText("Module");
        vMenuBarLeftContainer.setSpacing(10);

        iconTxt = GlyphsDude.createIcon(FontAwesomeIcon.CALENDAR, "20px");
        iconTxt.setFill(Color.GRAY);
        this.dateLabel.setGraphic(iconTxt);

        meldungAusgabeText.setWrapText(true);
        meldungAusgabeText.setText("Herzlich willkommen\n" + System.getProperty("user.name"));



    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleInfo() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vereins-App");
        alert.setHeaderText("über..");
        //System.getProperty("user.name");
        alert.setContentText(System.getProperty("user.name") + "\nWebsite: https://cloud4b.space");
        alert.showAndWait();
    }

    @FXML
    private void handleRefresh() {
        System.out.println( "Restarting app!" );
        mainApp.getPrimaryStage().close();;
        Platform.runLater( () -> mainApp.start( new Stage() ) );
    }

    /**
     * Oeffnet ein Browserfenster mit der Hilfe
     */
    @FXML
    private void handleHilfe() {
        Stage stage = new Stage();
        stage.setTitle("Web View");
        Scene scene = new Scene(new Browser("https://www.cloud4b.space/VereinsManager/Hilfe/help.html"), 750, 500, Color.web("#666970"));
        stage.setScene(scene);
        scene.getStylesheets().add("../css/BrowserToolbar.css");
        //TODO Path to stylesheet not correct...
        stage.show();
    }

    /**
     * Oeffnet das Fenster des Mitgliederbereichs
     */
    @FXML
    private void handleMitgliederbereich() {
        mainApp.showContactEditDialog(mitglied);
    }

    /**
     * Opens the birthday statistics.
     */
    @FXML
    private void handleShowBirthdayStatistics() {
        mainApp.showBirthdayStatistics();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleBeenden() {
        System.exit(0);
    }

    public void setInfo(String infoText, String infoTyp) {
        this.meldungAusgabeText.setText(infoText);
        switch(infoTyp) {
            case "OK":
                this.meldungAusgabeText.setStyle("-fx-text-fill: #4FA67B");
                break;
            case "NOK":
                this.meldungAusgabeText.setStyle("-fx-text-fill: #FF5F67");
                break;
            case "Info":
                this.meldungAusgabeText.setStyle("-fx-text-fill: #708ca6");
                break;
            default:
                this.meldungAusgabeText.setStyle("-fx-text-fill: #708ca6");
                break;

        }

       // this.meldungAusgabeText.appendText(infoText);

    }

}