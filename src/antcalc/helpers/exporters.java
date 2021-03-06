package antcalc.helpers;

import antcalc.models.MainCalculation;
import antcalc.models.ResultsCalculation;
import antcalc.models.TableFirst;
import antcalc.models.TableSecond;
import antcalc.views.CoolChartFrame;
import antcalc.views.MainView;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import org.jfree.chart.fx.ChartViewer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class exporters {
    public static void exportAll(String path, MainView mainView) {
        exportParamsTable(path + System.getProperty("file.separator") + "params.csv", mainView);
        exportTableOne(path + System.getProperty("file.separator") + "table_one.csv", mainView.firstTable);
        exportTableTwo(path + System.getProperty("file.separator") + "table_two.csv", mainView.secondTable);
        exportTablesResultsAndNc(path + System.getProperty("file.separator"), mainView);

    }

    public static void exportTableOne(String path, TableView firstTable) {
        File file = new File(path);
        String[] data = new String[5];
        data[0] = "Итерация";
        data[1] = "Максимум 1го лепестка";
        data[2] = "Максимум до 60 градусов";
        data[3] = "Максимум ореола";
        data[4] = "1й макс. > 2го (до 60 град)";
        try {
            csvwriter csv = new csvwriter(file, System.getProperty("file.encoding"));
            csv.writeHeader(data);
            ObservableList ol = firstTable.getItems();
            int sz = ol.size();
            for (int i = 0; i < sz; i++) {
                TableFirst tf = (TableFirst) ol.get(i);
                data[0] = String.valueOf(tf.getIter());
                data[1] = String.valueOf(tf.getFirst());
                data[2] = String.valueOf(tf.getSix());
                data[3] = String.valueOf(tf.getOreol());
                data[4] = String.valueOf(tf.getFmax());
                csv.writeData(data);
            }
            csv.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Экcпорт в файл");
            alert.setHeaderText(null);
            alert.setContentText("Возникла ошибка при экспорте таблицы 1...");
            alert.showAndWait();
        }
    }

    public static void exportTableTwo(String path, TableView secondTable) {
        File file = new File(path);
        String[] data = new String[11];
        data[0] = "Итерация";
        data[1] = "0.3";
        data[2] = "0.25";
        data[3] = "0.2";
        data[4] = "0.18";
        data[5] = "0.16";
        data[6] = "0.14";
        data[7] = "0.12";
        data[8] = "0.1";
        data[9] = "0.08";
        data[10] = "0.06";
        try {
            csvwriter csv = new csvwriter(file, System.getProperty("file.encoding"));
            csv.writeHeader(data);
            ObservableList ol = secondTable.getItems();
            int sz = ol.size();
            for (int i = 0; i < sz; i++) {
                TableSecond ts = (TableSecond) ol.get(i);
                data[0] = String.valueOf(ts.getDescr());
                data[1] = String.valueOf(ts.getC03());
                data[2] = String.valueOf(ts.getC025());
                data[3] = String.valueOf(ts.getC02());
                data[4] = String.valueOf(ts.getC018());
                data[5] = String.valueOf(ts.getC016());
                data[6] = String.valueOf(ts.getC014());
                data[7] = String.valueOf(ts.getC012());
                data[8] = String.valueOf(ts.getC01());
                data[9] = String.valueOf(ts.getC008());
                data[10] = String.valueOf(ts.getC006());
                csv.writeData(data);
            }
            csv.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Экcпорт в файл");
            alert.setHeaderText(null);
            alert.setContentText("Возникла ошибка при экспорте таблицы 2...");
            alert.showAndWait();
        }
    }

    public static void exportParamsTable(String path, MainView mainView) {
        File file = new File(path);
        String[] data = new String[2];
        data[0] = "Переменая";
        data[1] = "Значение";
        try {
            csvwriter csv = new csvwriter(file, System.getProperty("file.encoding"));
            csv.writeHeader(data);

            data[0] = "N";
            data[1] = mainView.inputN.getText();
            csv.writeData(data);

            data[0] = "Nc";
            data[1] = mainView.inputNc.getText();
            csv.writeData(data);

            data[0] = "Kd";
            data[1] = mainView.inputKd.getText();
            csv.writeData(data);

            data[0] = "E";
            data[1] = mainView.inputE.getText();
            csv.writeData(data);

            data[0] = "M";
            data[1] = mainView.inputM.getText();
            csv.writeData(data);

            data[0] = "Delta Fi max";
            data[1] = mainView.inputDeltaFiMax.getText();
            csv.writeData(data);

            data[0] = "Delta A Sigma";
            data[1] = mainView.inputDeltaASigma.getText();
            csv.writeData(data);

            data[0] = "Iterations";
            data[1] = mainView.inputIterations.getText();
            csv.writeData(data);

            data[0] = "Errors";
            data[1] = mainView.noErrors.isSelected() ? "Errors is off" : "Errors is on";
            csv.writeData(data);

            csv.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Экcпорт в файл");
            alert.setHeaderText(null);
            alert.setContentText("Возникла ошибка при экспорте таблицы нацальных параметров");
            alert.showAndWait();
        }
    }

    public static void exportTablesResultsAndNc(String path, MainView mainView) {
        int numberIteration = Integer.parseInt(mainView.inputIterations.getText());
        if (mainView.noErrors.isSelected()) numberIteration = 1;
        for (int iter = 1; iter <= numberIteration; iter++) {

            float[] dg      = ResultsCalculation.hMapDG.get(iter);
            float[] ra      = ResultsCalculation.hMapRA.get(iter);
            float[] rna     = ResultsCalculation.hMapRNA.get(iter);
            float[] sigma   = ResultsCalculation.hMapSigmaNC.get(iter);
            float[] delta   = ResultsCalculation.hMapDeltaNC.get(iter);

            File file = new File(path + "results_" + String.valueOf(iter) + ".csv");
            String[] data = new String[3];
            data[0] = "Угол";
            data[1] = "Ra";
            data[2] = "Rna";
            try {
                csvwriter csv = new csvwriter(file, System.getProperty("file.encoding"));
                csv.writeHeader(data);
                for (int alpha = 1; alpha <= 181; alpha += MainCalculation.dal) {
                    data[0] = String.valueOf(dg[alpha]);
                    data[1] = String.valueOf(ra[alpha]);
                    data[2] = String.valueOf(rna[alpha]);
                    csv.writeData(data);
                }
                csv.close();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Экcпорт в файл");
                alert.setHeaderText(null);
                alert.setContentText("Возникла ошибка при экспорте результатов");
                alert.showAndWait();
            }

            if (!mainView.noErrors.isSelected()) {
                file = new File(path + "randoms_" + String.valueOf(iter) + ".csv");
                data = new String[3];
                data[0] = "Nc";
                data[1] = "Sigma";
                data[2] = "Delta";
                try {
                    csvwriter csv = new csvwriter(file, System.getProperty("file.encoding"));
                    csv.writeHeader(data);
                    for (int nc = 1; nc <= Integer.parseInt(mainView.inputNc.getText()); nc += 1) {
                        data[0] = String.valueOf(nc);
                        data[1] = String.valueOf(sigma[nc]);
                        data[2] = String.valueOf(delta[nc]);
                        csv.writeData(data);
                    }
                    csv.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Экcпорт в файл");
                    alert.setHeaderText(null);
                    alert.setContentText("Возникла ошибка при экспорте результатов");
                    alert.showAndWait();
                }
            }

            mainView.graphiksnormPane.getChildren().clear();
            ChartViewer chartNormViewer = new ChartViewer(CoolChartFrame.createChart("Нормированный график по итерации №" + String.valueOf(iter), ResultsCalculation.hMapDG.get(iter), ResultsCalculation.hMapRNA.get(iter)), true);
            chartNormViewer.setPrefWidth(mainView.graphiksnormPane.getWidth());
            chartNormViewer.setPrefHeight(mainView.graphiksnormPane.getHeight());
            mainView.graphiksnormPane.getChildren().add(chartNormViewer);

            try {
                // retrieve image
                BufferedImage bi = chartNormViewer.getChart().createBufferedImage(800,600);
                File outputfile = new File(path + "normGraph_" + String.valueOf(iter) + ".png");
                ImageIO.write(bi, "png", outputfile);
            } catch (IOException e) {
                System.out.println("Error... Image save");
            }

            mainView.graphiksPane.getChildren().clear();
            ChartViewer chartViewer = new ChartViewer(CoolChartFrame.createChart("График по итерации №" + String.valueOf(iter), ResultsCalculation.hMapDG.get(iter), ResultsCalculation.hMapRA.get(iter)), true);
            chartViewer.setPrefWidth(mainView.graphiksPane.getWidth());
            chartViewer.setPrefHeight(mainView.graphiksPane.getHeight());
            mainView.graphiksPane.getChildren().add(chartViewer);

            try {
                // retrieve image
                BufferedImage bi = chartViewer.getChart().createBufferedImage(800,600);
                File outputfile = new File(path + "graph_" + String.valueOf(iter) + ".png");
                ImageIO.write(bi, "png", outputfile);
            } catch (IOException e) {
                System.out.println("Error... Image save");
            }

        }
    }
}


