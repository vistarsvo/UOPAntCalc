package antcalc.views;

import antcalc.Main;
import antcalc.controllers.MainController;
import antcalc.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainView implements Initializable {
    @FXML
    public MenuBar mainNavBar;
    public ImageView imageViewPanel;
    public ImageView errorN;
    public ImageView errorNc;
    public ImageView errorKd;
    public ImageView errorE;
    public ImageView errorM;
    public ImageView errorDeltaFiMax;
    public ImageView errorDeltaASigma;
    public ImageView errorIterations;
    public Button startCalculation;
    public Button showResults;
    public Button printTableOne;
    public Button printTableTwo;
    public TextField inputN;
    public TextField inputNc;
    public TextField inputKd;
    public TextField inputE;
    public TextField inputM;
    public TextField inputDeltaFiMax;
    public TextField inputDeltaASigma;
    public TextField inputIterations;
    public MenuItem startCalculationMI;
    public Tab main;
    public Tab results;
    public Tab graphiks;
    public Tab graphiksnorm;
    public Tab table1;
    public Tab table2;
    public TabPane mainTabPanel;
    public ComboBox comboBox;
    public ComboBox comboBoxG;
    public Label resultsLable;
    public Label randomsLable;
    public AnchorPane graphiksPane;
    public AnchorPane graphiksnormPane;
    public AnchorPane graphiksnormPaneInside;

    public ObservableList<TableResults> resultsData = FXCollections.observableArrayList();
    public TableView<TableResults> resultsTable;
    public TableColumn<TableResults, Integer> alphaColumn;
    public TableColumn<TableResults, Float> dgColumn;
    public TableColumn<TableResults, Float> raColumn;
    public TableColumn<TableResults, Float> rnaColumn;

    public ObservableList<TableRandoms> randomData = FXCollections.observableArrayList();
    public TableView<TableRandoms> randomTable;
    public TableColumn<TableRandoms, Integer> ncColumn;
    public TableColumn<TableRandoms, Float> sigmaColumn;
    public TableColumn<TableRandoms, Float> deltaColumn;

    public ObservableList<TableFirst> firstData = FXCollections.observableArrayList();
    public TableView<TableFirst> firstTable;
    public TableColumn<TableFirst, Integer> iterColumn;
    public TableColumn<TableFirst, Float> firstColumn;
    public TableColumn<TableFirst, Float> sixColumn;
    public TableColumn<TableFirst, Float> oreolColumn;
    public TableColumn<TableFirst, String> fmaxColumn;

    public ObservableList<TableSecond> secondData = FXCollections.observableArrayList();
    public TableView<TableSecond> secondTable;
    public TableColumn<TableSecond, String> descrColumn;
    public TableColumn<TableSecond, Integer> c_03_Column;
    public TableColumn<TableSecond, Integer> c_025_Column;
    public TableColumn<TableSecond, Integer> c_02_Column;
    public TableColumn<TableSecond, Integer> c_018_Column;
    public TableColumn<TableSecond, Integer> c_016_Column;
    public TableColumn<TableSecond, Integer> c_014_Column;
    public TableColumn<TableSecond, Integer> c_012_Column;
    public TableColumn<TableSecond, Integer> c_01_Column;
    public TableColumn<TableSecond, Integer> c_008_Column;
    public TableColumn<TableSecond, Integer> c_006_Column;

    public CheckBox noErrors;


    @FXML
    private void handleAuthorAction(final ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О авторе");
        alert.setHeaderText("Заказчик: Коваленко Марк\n" +
                            "Исполнитель: Влад");
        alert.setContentText("Данная программа разработана без коммерческой выгоды разработчику");
        alert.showAndWait();
    }


    @FXML
    private void handlePrintTableOneAction(final ActionEvent event)
    {
        System.out.println("You clicked on Print 1!");

       /* Printer printer = Printer.getDefaultPrinter();
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null) {
            boolean success = job.printPage(firstTable);
            if (success) {
                job.endJob();
            }
        }
        */


    }

    @FXML
    private void handlePrintTableTwoAction(final ActionEvent event)
    {
        System.out.println("You clicked on Print2!");
    }

    @FXML
    private void handleAboutAction(final ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("Расчет антенны v 2.0 alpha");
        alert.setContentText("Данная программа, как и все ее алгоритмы расчетов является собственностью предприятия \n" +
                             "и попадание ее в частные руки является нарушением закона!");
        alert.showAndWait();
    }

    @FXML
    private void handleExitAction(final ActionEvent event)
    {
        MainController.exitAction();
    }

    @FXML
    private void handleShowResultsAction(final ActionEvent event)
    {
        MainController.showResults(this, MathHelper.integerConvert(inputNc.getText()), 1 );
    }

    @FXML
    private void handleShowGraphNormAction(final ActionEvent event)
    {
        MainController.showResults(this, MathHelper.integerConvert(inputNc.getText()), 2 );
    }


    @FXML
    private void handleStartCalculationAction(final ActionEvent event)
    {
        if (
                   validatorN(inputN.getText())
                && validatorNc(inputNc.getText())
                && validatorKd(inputKd.getText())
                && validatorE(inputE.getText())
                && validatorM(inputM.getText())
                && validatorDeltaFiMax(inputDeltaFiMax.getText())
                && validatorDeltaASigma(inputDeltaASigma.getText())
                && validatorIterations(inputIterations.getText())
                ) {
                    startCalculationMI.setDisable(false);
                    results.setDisable(false);
                    graphiks.setDisable(true);
                    graphiksnorm.setDisable(true);
                    table1.setDisable(false);
                    table2.setDisable(false);

                    Map<String, Float> dataFloat = new HashMap<>();
                    Map<String, Integer> dataInteger = new HashMap<>();

                    dataInteger.put("N", MathHelper.integerConvert(inputN.getText()));
                    dataInteger.put("Nc", MathHelper.integerConvert(inputNc.getText()));
                    dataInteger.put("DFiMax", MathHelper.integerConvert(inputDeltaFiMax.getText()));
                    //dataInteger.put("DASigma", MathHelper.integerConvert(inputDeltaASigma.getText()));
                    dataInteger.put("Iterator", MathHelper.integerConvert(inputIterations.getText()));

                    dataFloat.put("DASigma", MathHelper.floatConvert(inputDeltaASigma.getText()));
                    dataFloat.put("Kd", MathHelper.floatConvert(inputKd.getText()));
                    dataFloat.put("E", MathHelper.floatConvert(inputE.getText()));
                    dataFloat.put("M", MathHelper.floatConvert(inputM.getText()));

                    MainController.startCalculate(dataFloat, dataInteger, this);
                } else {
                    ButtonType buttonOkay = new ButtonType("Хорошо, все сделаю");
                    Alert alert = new Alert(Alert.AlertType.ERROR,"...", buttonOkay);
                    alert.setTitle("Возникла ошибка!");
                    alert.setHeaderText("Ошибка заполнения полей!");
                    alert.setContentText("Похоже, что вы игнорируте предупреждения. \nПожалуйста, заполните все поля в соответсвии с требованиями.");
                    alert.showAndWait();
                }
    }

    @FXML
    private void handleKeyAction(final KeyEvent event)
    {
        TextField activeTextField = (TextField) event.getSource();
        String id = activeTextField.getId();
        String text = activeTextField.getText();

        switch (id) {
            case "inputN":  validatorN(text); break;
            case "inputNc": validatorNc(text); break;
            case "inputKd": validatorKd(text); break;
            case "inputE": validatorE(text); break;
            case "inputM": validatorM(text); break;
            case "inputDeltaFiMax": validatorDeltaFiMax(text); break;
            case "inputDeltaASigma": validatorDeltaASigma(text); break;
            case "inputIterations": validatorIterations(text); break;
        }

        startCalculationMI.setDisable(true);
        results.setDisable(true);
        graphiks.setDisable(true);
        graphiksnorm.setDisable(true);
        table1.setDisable(true);
        table2.setDisable(true);
    }

    @FXML
    private void handleResetAction(final ActionEvent event)
    {
        if (MainController.resetAction()) {
            startCalculationMI.setDisable(true);
            results.setDisable(true);
            graphiks.setDisable(true);
            graphiksnorm.setDisable(true);
            table1.setDisable(true);
            table2.setDisable(true);

            inputN.setText("24");
            inputNc.setText("8");
            inputKd.setText("25.9");
            inputE.setText("0.15");
            inputM.setText("0.05");
            inputDeltaFiMax.setText("1");
            inputDeltaASigma.setText("1");
            inputIterations.setText("10");

            validatorN(inputN.getText());
            validatorNc(inputNc.getText());
            validatorKd(inputKd.getText());
            validatorE(inputE.getText());
            validatorM(inputM.getText());
            validatorDeltaFiMax(inputDeltaFiMax.getText());
            validatorDeltaASigma(inputDeltaASigma.getText());
            validatorIterations(inputIterations.getText());

            SingleSelectionModel<Tab> selectionModel = mainTabPanel.getSelectionModel();
            selectionModel.select(0);
        }
    }


    @Override
    public void initialize(final URL url, final ResourceBundle rb)
    {
        mainNavBar.setFocusTraversable(true);

        alphaColumn.setCellValueFactory(new PropertyValueFactory<TableResults, Integer>("alpha"));
        dgColumn.setCellValueFactory(new PropertyValueFactory<TableResults, Float>("dg"));
        raColumn.setCellValueFactory(new PropertyValueFactory<TableResults, Float>("ra"));
        rnaColumn.setCellValueFactory(new PropertyValueFactory<TableResults, Float>("rna"));

        ncColumn.setCellValueFactory(new PropertyValueFactory<TableRandoms, Integer>("nc"));
        sigmaColumn.setCellValueFactory(new PropertyValueFactory<TableRandoms, Float>("sigma"));
        deltaColumn.setCellValueFactory(new PropertyValueFactory<TableRandoms, Float>("delta"));

        iterColumn.setCellValueFactory(new PropertyValueFactory<TableFirst, Integer>("iter"));
        firstColumn.setCellValueFactory(new PropertyValueFactory<TableFirst, Float>("first"));
        sixColumn.setCellValueFactory(new PropertyValueFactory<TableFirst, Float>("six"));
        oreolColumn.setCellValueFactory(new PropertyValueFactory<TableFirst, Float>("oreol"));
        fmaxColumn.setCellValueFactory(new PropertyValueFactory<TableFirst, String>("fmax"));

        descrColumn.setCellValueFactory(new PropertyValueFactory<TableSecond, String>("descr"));
        c_03_Column.setCellValueFactory(new PropertyValueFactory<TableSecond, Integer>("c03"));
        c_025_Column.setCellValueFactory(new PropertyValueFactory<TableSecond, Integer>("c025"));
        c_02_Column.setCellValueFactory(new PropertyValueFactory<TableSecond, Integer>("c02"));
        c_018_Column.setCellValueFactory(new PropertyValueFactory<TableSecond, Integer>("c018"));
        c_016_Column.setCellValueFactory(new PropertyValueFactory<TableSecond, Integer>("c016"));
        c_014_Column.setCellValueFactory(new PropertyValueFactory<TableSecond, Integer>("c014"));
        c_012_Column.setCellValueFactory(new PropertyValueFactory<TableSecond, Integer>("c012"));
        c_01_Column.setCellValueFactory(new PropertyValueFactory<TableSecond, Integer>("c01"));
        c_008_Column.setCellValueFactory(new PropertyValueFactory<TableSecond, Integer>("c008"));
        c_006_Column.setCellValueFactory(new PropertyValueFactory<TableSecond, Integer>("c006"));

        imageViewPanel.setVisible(true);
    }

    /*
       Validation correct input in fields
     */
    private boolean validatorN(String text) {
        boolean validateResult = InputValidator.integerValidator(text, 1, 100);
        errorN.setVisible(!validateResult);
        return validateResult;
    }

    private boolean validatorNc(String text) {
        boolean validateResult = InputValidator.integerValidator(text, 1, 100);
        errorNc.setVisible(!validateResult);
        return validateResult;
    }

    private boolean validatorKd(String text) {
        boolean validateResult = InputValidator.floatValidator(text, 0f, 100f);
        errorKd.setVisible(!validateResult);
        return validateResult;
    }

    private boolean validatorE(String text) {
        boolean validateResult = InputValidator.floatValidator(text, 0f, 100f);
        errorE.setVisible(!validateResult);
        return validateResult;
    }

    private boolean validatorM(String text) {
        boolean validateResult = InputValidator.floatValidator(text, 0f, 5f);
        errorM.setVisible(!validateResult);
        return validateResult;
    }

    private boolean validatorDeltaFiMax(String text) {
        boolean validateResult = InputValidator.integerValidator(text, 0, 180);
        errorDeltaFiMax.setVisible(!validateResult);
        return validateResult;
    }

    private boolean validatorDeltaASigma(String text) {
        boolean validateResult = InputValidator.floatValidator(text, 0, 1);
        errorDeltaASigma.setVisible(!validateResult);
        return validateResult;
    }

    private boolean validatorIterations(String text) {
        boolean validateResult = InputValidator.integerValidator(text, 1, 100);
        errorIterations.setVisible(!validateResult);
        return validateResult;
    }

}
