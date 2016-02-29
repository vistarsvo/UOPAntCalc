package antcalc.controllers;

import antcalc.models.*;
import antcalc.views.CoolChartFrame;
import antcalc.views.MainView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.jfree.chart.fx.ChartViewer;

import java.util.*;

public class MainController {
    public static MainCalculation mainCalculation = new MainCalculation();
    public static MainView MainView;
    public static ChartViewer chartViewer;
    public static ChartViewer chartNormViewer;

    public static void exitAction() {
        ButtonType buttonYes = new ButtonType("Да");
        ButtonType buttonNo = new ButtonType("Нет");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Вы действительно хотите выйти?", buttonYes, buttonNo);
        alert.setTitle("Подтверждение выхода");
        alert.setHeaderText("Внимание! Программа будет закрыта!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
            Platform.exit();
            System.exit(0);
        } else {
             // Do nothing
        }
    }

    public static boolean resetAction() {
        ButtonType buttonYes = new ButtonType("Да, сбросить");
        ButtonType buttonNo = new ButtonType("Нет");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Вы действительно хотите сбросить расчеты?", buttonYes, buttonNo);
        alert.setTitle("Подтверждение сброса");
        alert.setHeaderText("Внимание! Данные будут утеряны!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
            return true;
        } else {
            return false;
        }
    }

    public static void showResults(MainView mainView, int Nc, int tabNum) {
        MainView = mainView;
        int numberIteration;
        if (tabNum == 1) {
            numberIteration = MathHelper.integerConvert(mainView.comboBox.getValue().toString());
            mainView.comboBoxG.setValue(mainView.comboBox.getValue().toString());
        } else {
            numberIteration = MathHelper.integerConvert(mainView.comboBoxG.getValue().toString());
            mainView.comboBox.setValue(mainView.comboBoxG.getValue().toString());
        }

        float[] dg      = ResultsCalculation.hMapDG.get(numberIteration);
        float[] ra      = ResultsCalculation.hMapRA.get(numberIteration);
        float[] rna     = ResultsCalculation.hMapRNA.get(numberIteration);
        float[] sigma   = ResultsCalculation.hMapSigmaNC.get(numberIteration);
        float[] delta   = ResultsCalculation.hMapDeltaNC.get(numberIteration);

        // Table Results clear
        mainView.resultsTable.getItems().clear();
        mainView.resultsData.clear();
        for (int alpha = 1; alpha <= 181; alpha += MainCalculation.dal) {
            mainView.resultsData.add(new TableResults(alpha, dg[alpha], ra[alpha], rna[alpha]));
        }
        mainView.resultsTable.setItems(mainView.resultsData);

        // Table randoms clear
        mainView.randomTable.getItems().clear();
        mainView.randomData.clear();
        for (int nc = 1; nc <= Nc; nc += 1) {
            mainView.randomData.add(new TableRandoms(nc, sigma[nc], delta[nc]));
        }
        mainView.randomTable.setItems(mainView.randomData);

        mainView.resultsLable.setText("Таблица результатов №" + String.valueOf(numberIteration));
        mainView.randomsLable.setText("Таблица слуцайных чисел  №" + String.valueOf(numberIteration));

        // Chart
        mainView.graphiksPane.getChildren().clear();
        chartViewer = new ChartViewer(CoolChartFrame.createChart("График по итерации №" + String.valueOf(numberIteration), ResultsCalculation.hMapDG.get(numberIteration), ResultsCalculation.hMapRA.get(numberIteration)), true);
        chartViewer.setPrefWidth(mainView.graphiksPane.getWidth());
        chartViewer.setPrefHeight(mainView.graphiksPane.getHeight());
        mainView.graphiksPane.getChildren().add(chartViewer);

        // Chart Norm
        mainView.graphiksnormPane.getChildren().clear();
        chartNormViewer = new ChartViewer(CoolChartFrame.createChart("Нормированный график по итерации №" + String.valueOf(numberIteration), ResultsCalculation.hMapDG.get(numberIteration), ResultsCalculation.hMapRNA.get(numberIteration)), true);
        chartNormViewer.setPrefWidth(mainView.graphiksnormPane.getWidth());
        chartNormViewer.setPrefHeight(mainView.graphiksnormPane.getHeight());
        mainView.graphiksnormPane.getChildren().add(chartNormViewer);

        mainView.graphiks.setDisable(true);
        mainView.graphiksnorm.setDisable(true);

        mainView.graphiks.setDisable(false);
        mainView.graphiksnorm.setDisable(false);
    }

    public static void startCalculate(Map<String, Float> dataFloat, Map<String, Integer> dataInteger, MainView mainView) {
        mainCalculation.setN(dataInteger.getOrDefault("N", 0));
        mainCalculation.setNc(dataInteger.getOrDefault("Nc", 0));
        mainCalculation.setDeltaFiMax(dataInteger.getOrDefault("DFiMax", 0));
        //mainCalculation.setDASigma(dataInteger.getOrDefault("DASigma", 0));

        mainCalculation.setDASigma(dataFloat.getOrDefault("DASigma", 0f));
        mainCalculation.setKD(dataFloat.getOrDefault("Kd", 0f));
        mainCalculation.setEps(dataFloat.getOrDefault("E", 0f));
        mainCalculation.setM(dataFloat.getOrDefault("M", 0f));

        // ComboBox defaults
        mainView.comboBox.getSelectionModel().clearSelection();
        mainView.comboBox.getItems().clear();
        mainView.comboBox.setValue("1");

        mainView.comboBoxG.getSelectionModel().clearSelection();
        mainView.comboBoxG.getItems().clear();
        mainView.comboBoxG.setValue("1");

        // Table Results clear
        mainView.resultsTable.getItems().clear();
        mainView.resultsData.clear();

        // Table Randoms clear
        mainView.randomTable.getItems().clear();
        mainView.randomData.clear();

        // Lables
        mainView.resultsLable.setText("Таблица результатов");
        mainView.randomsLable.setText("Таблица слуцайных чисел");

        mainView.firstTable.getItems().clear();
        mainView.firstData.clear();

        int sum_03 = 0, sum_025 = 0, sum_02 = 0, sum_018 =0,
            sum_016 = 0, sum_014 = 0, sum_012 = 0, sum_01 =0,
            sum_008 = 0, sum_006 = 0;

        ResultsCalculation.hMapDeltaNC.clear();
        ResultsCalculation.hMapSigmaNC.clear();
        ResultsCalculation.hMapDG.clear();
        ResultsCalculation.hMapFirstLep.clear();
        ResultsCalculation.hMapMaxOreol.clear();
        ResultsCalculation.hMapRA.clear();
        ResultsCalculation.hMapRNA.clear();
        ResultsCalculation.hMapSixLep.clear();
        ResultsCalculation.hMaprLevel.clear();

        if (mainView.noErrors.isSelected()) {
            dataInteger.put("Iterator",1);
            mainView.randomTable.setVisible(false);
            mainView.randomsLable.setVisible(false);
        } else {
            mainView.randomTable.setVisible(true);
            mainView.randomsLable.setVisible(true);
        }

        for (int i = 1; i <= dataInteger.getOrDefault("Iterator", 1); i++) {
            mainView.comboBox.getItems().addAll("" + String.valueOf(i));
            mainView.comboBoxG.getItems().addAll("" + String.valueOf(i));
            mainCalculation.calculateAll(mainView.noErrors.isSelected(), mainView.aRasp.isSelected());

            ResultsCalculation.hMapDG.put(i,mainCalculation.getDg());
            ResultsCalculation.hMapRA.put(i,mainCalculation.getRa());
            ResultsCalculation.hMapRNA.put(i,mainCalculation.getRna());

            ResultsCalculation.hMapSigmaNC.put(i,mainCalculation.getSigmaNC());
            ResultsCalculation.hMapDeltaNC.put(i,mainCalculation.getDeltaNC());

            ResultsCalculation.hMapMaxOreol.put(i,mainCalculation.getmaxOreol());
            ResultsCalculation.hMapFirstLep.put(i,mainCalculation.getfirstLep());
            ResultsCalculation.hMapSixLep.put(i,mainCalculation.getSixLep());
            ResultsCalculation.hMaprLevel.put(i, mainCalculation.getRLevel());

            String temp = "";
            if (ResultsCalculation.hMapFirstLep.getOrDefault(i, -1f) >= ResultsCalculation.hMapSixLep.getOrDefault(i, -1f)) {
                temp = "+";
            } else {
                temp = "-";
            }

            //System.out.println(ResultsCalculation.hMapFirstLep.getOrDefault(i, -1f) + ">" + ResultsCalculation.hMapSixLep.getOrDefault(i, -1f) + " == " + temp);

            mainView.firstData.add( new TableFirst(i,
                                                    ResultsCalculation.hMapFirstLep.getOrDefault(i, -1f),
                                                    ResultsCalculation.hMapSixLep.getOrDefault(i, -1f),
                                                    ResultsCalculation.hMapMaxOreol.getOrDefault(i, -1f),
                                                    temp
                    ));

            sum_03  += ResultsCalculation.hMaprLevel.get(i).get(0.3f);
            sum_025 += ResultsCalculation.hMaprLevel.get(i).get(0.25f);
            sum_02  += ResultsCalculation.hMaprLevel.get(i).get(0.2f);
            sum_018 += ResultsCalculation.hMaprLevel.get(i).get(0.18f);
            sum_016 += ResultsCalculation.hMaprLevel.get(i).get(0.16f);
            sum_014 += ResultsCalculation.hMaprLevel.get(i).get(0.14f);
            sum_012 += ResultsCalculation.hMaprLevel.get(i).get(0.12f);
            sum_01  += ResultsCalculation.hMaprLevel.get(i).get(0.1f);
            sum_008 += ResultsCalculation.hMaprLevel.get(i).get(0.08f);
            sum_006 += ResultsCalculation.hMaprLevel.get(i).get(0.06f);
        }
        mainView.firstTable.setItems(mainView.firstData);

        // Second Table
        mainView.secondTable.getItems().clear();
        mainView.secondData.clear();
        mainView.secondData.add(new TableSecond("Кол. не превыш.", sum_03, sum_025, sum_02, sum_018, sum_016, sum_014, sum_012, sum_01, sum_008, sum_006) );

        for (int i = 1; i <= dataInteger.getOrDefault("Iterator", 1); i++) {
            int t_sum_03  = ResultsCalculation.hMaprLevel.get(i).get(0.3f);
            int t_sum_025 = ResultsCalculation.hMaprLevel.get(i).get(0.25f);
            int t_sum_02  = ResultsCalculation.hMaprLevel.get(i).get(0.2f);
            int t_sum_018 = ResultsCalculation.hMaprLevel.get(i).get(0.18f);
            int t_sum_016 = ResultsCalculation.hMaprLevel.get(i).get(0.16f);
            int t_sum_014 = ResultsCalculation.hMaprLevel.get(i).get(0.14f);
            int t_sum_012 = ResultsCalculation.hMaprLevel.get(i).get(0.12f);
            int t_sum_01  = ResultsCalculation.hMaprLevel.get(i).get(0.1f);
            int t_sum_008 = ResultsCalculation.hMaprLevel.get(i).get(0.08f);
            int t_sum_006 = ResultsCalculation.hMaprLevel.get(i).get(0.06f);
            mainView.secondData.add(new TableSecond("Итерация " + String.valueOf(i), t_sum_03, t_sum_025, t_sum_02, t_sum_018, t_sum_016, t_sum_014, t_sum_012, t_sum_01, t_sum_008, t_sum_006) );
        }

        mainView.secondTable.setItems(mainView.secondData);
    }


}
