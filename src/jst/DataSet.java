    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jst;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import javax.swing.table.DefaultTableModel;
import static org.apache.poi.hssf.usermodel.HeaderFooter.file;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.*;

/**
 *
 * @author Ersa
 */
public class DataSet {

    private boolean bias;
    private double MSElimit = 0.05;
    private double LearningRate = 0.3;
    private int JINeuron = 6;
    private int JHNeuron = 5;
    private int JONeuron = 4;
    private int maxIter = 500;
    private ArrayList<CarEvaluation> dataSetLearning = new ArrayList<>();
    private ArrayList<CarEvaluation> dataSetTesting = new ArrayList<>();
    private double[][] w1;
    private double[][] w2;
    private double[] b1;
    private double[] b2;
    private double[] db1;
    private double[] db2;
    private double[][] dw1;
    private double[][] dw2;

    public DataSet() {
    }

    public void setMatrix() {
        w1 = new double[JINeuron][JHNeuron];
        w2 = new double[JHNeuron][JONeuron];
        b1 = new double[JHNeuron];
        b2 = new double[JONeuron];
        db1 = new double[JHNeuron];
        db2 = new double[JONeuron];
        dw1 = new double[JINeuron][JHNeuron];
        dw2 = new double[JHNeuron][JONeuron];
    }

    public void setBias(boolean bias) {
        this.bias = bias;
    }

    public void setLearningRate(double LearningRate) {
        this.LearningRate = LearningRate;
    }

    public void setJHNeuron(int JHNeuron) {
        this.JHNeuron = JHNeuron;
    }

    public void setMaxIter(int maxIter) {
        this.maxIter = maxIter;
    }

    public boolean isBias() {
        return bias;
    }

    public double getLearningRate() {
        return LearningRate;
    }

    public int getJHNeuron() {
        return JHNeuron;
    }

    public int getMaxIter() {
        return maxIter;
    }

    public void addDataSetText(String file) throws FileNotFoundException, IOException {
        FileReader input = new FileReader(file);
        BufferedReader bufRead = new BufferedReader(input);
        String line = null;
        while ((line = bufRead.readLine()) != null) {
            String[] data = line.split(",");
            CarEvaluation ce = new CarEvaluation(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
            dataSetLearning.add(ce);
        }
        Collections.shuffle(dataSetLearning);

        for (int i = 0; i < 518; i++) {
            dataSetTesting.add(dataSetLearning.get(i));
            dataSetLearning.remove(i);
        }

    }

    public void addDataSetExcel(String fileLearning, String fileTesting) throws IOException {
        FileInputStream file = new FileInputStream(new File(fileLearning));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            String data[] = new String[7];
            int w = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        data[w] = String.valueOf(cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_STRING:
                        data[w] = cell.getStringCellValue();
                        break;
                }
                w++;
            }
            CarEvaluation ce = new CarEvaluation(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
            dataSetLearning.add(ce);
        }
        file.close();

        file = new FileInputStream(new File(fileTesting));

        workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheetAt(0);
        rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            String data[] = new String[7];
            int w = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        data[w] = String.valueOf((int) cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_STRING:
                        data[w] = cell.getStringCellValue();
                        break;
                }
                w++;
            }
            CarEvaluation ce = new CarEvaluation(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
            dataSetTesting.add(ce);
        }
    }

    public double randomizeWeight() {
        Random r = new Random();
        return (r.nextDouble() * 2 - 1);
    }

    public void initiateWeight() {
        for (int i = 0; i < JINeuron; i++) {
            for (int j = 0; j < JHNeuron; j++) {
                System.out.print(i);
                System.out.println(j);
                w1[i][j] = randomizeWeight();
            }
        }
        for (int i = 0; i < JHNeuron; i++) {
            for (int j = 0; j < JONeuron; j++) {
                w2[i][j] = randomizeWeight();
            }
        }

        for (int i = 0; i < JHNeuron; i++) {
            b1[i] = randomizeWeight();
        }

        for (int i = 0; i < JONeuron; i++) {
            b2[i] = randomizeWeight();
        }

    }

    public void updateWeight() {

        for (int i = 0; i < JHNeuron; i++) {
            for (int j = 0; j < JINeuron; j++) {
                w1[j][i] += dw1[j][i];
            }
        }

        for (int i = 0; i < JONeuron; i++) {
            for (int j = 0; j < JHNeuron; j++) {
                w2[j][i] += dw2[j][i];
            }
        }
        if (bias) {
            for (int i = 0; i < JHNeuron; i++) {
                b1[i] += db1[i];
            }

            for (int i = 0; i < JONeuron; i++) {
                b2[i] += db2[i];
            }
        }
    }

    public double countMSE(ArrayList<CarEvaluation> dataSet) {
        double totalError1 = 0;
        double totalError2 = 0;
        double totalError3 = 0;
        double totalError4 = 0;
        for (int i = 0; i < dataSet.size(); i++) {
            totalError1 += Math.pow(dataSet.get(i).getError(0), 2);
            totalError2 += Math.pow(dataSet.get(i).getError(1), 2);
            totalError3 += Math.pow(dataSet.get(i).getError(2), 2);
            totalError4 += Math.pow(dataSet.get(i).getError(3), 2);
        }
        return ((totalError1 + totalError2 + totalError3 + totalError4) / dataSet.size()) / 4;
    }

    public void getWeight() {
        for (int i = 0; i < JHNeuron; i++) {
            for (int j = 0; j < JINeuron; j++) {
                System.out.println("w1[" + j + "][" + i + "] = " + w1[j][i]);
            }
        }

        for (int i = 0; i < JONeuron; i++) {
            for (int j = 0; j < JHNeuron; j++) {
                System.out.println("w2[" + j + "][" + i + "] = " + w2[j][i]);
            }
        }
    }

    public CarEvaluation getDataLearning(int index) {
        return dataSetLearning.get(index);
    }

    public CarEvaluation getDataTesting(int index) {
        return dataSetTesting.get(index);
    }

    public void learning() {
        double MSE = 100;
        for (int i = 0; i < maxIter && MSE > MSElimit; i++) {
            for (int j = 0; j < dataSetLearning.size(); j++) {
                if (j == 0 && i == 0) {
                    initiateWeight();
                } else {
                    updateWeight();
                }
                CarEvaluation ce = getDataLearning(j);
                double input[] = ce.getDataArray();
                double u1[] = new double[JHNeuron];
                double z[] = new double[JHNeuron];
                for (int k = 0; k < JHNeuron; k++) {
                    for (int l = 0; l < JINeuron; l++) {
                        u1[k] += (input[l] * w1[l][k]);
                    }
                    if (bias) {
                        u1[k] += b1[k];
                    }
                    z[k] = 1 / (1 + Math.exp(-u1[k]));
                }
                double[] u2 = new double[JONeuron];
                double[] y = new double[JONeuron];
                for (int k = 0; k < JONeuron; k++) {
                    for (int l = 0; l < JHNeuron; l++) {
                        u2[k] += (z[l] * w2[l][k]);
                    }
                    if (bias) {
                        u2[k] += b2[k];
                    }
                    y[k] = 1 / (1 + Math.exp(-u2[k]));
                }

                double[] error = new double[4];
                for (int k = 0; k < 4; k++) {
                    error[k] = ce.getAcceptability(k) - y[k];
                }
                ce.setError(error);
                double[] d1 = new double[JONeuron];
                for (int k = 0; k < JONeuron; k++) {
                    d1[k] = error[k] * (1 - y[k]) * y[k];
                    if (bias) {
                        db2[k] = d1[k] * LearningRate;
                    }
                }
                for (int k = 0; k < JONeuron; k++) {
                    for (int l = 0; l < JHNeuron; l++) {
                        dw2[l][k] = d1[k] * z[l] * LearningRate;
                    }
                }
                double[] t = new double[JHNeuron];
                for (int k = 0; k < JHNeuron; k++) {
                    t[k] = 0;
                }
                for (int k = 0; k < JONeuron; k++) {
                    for (int l = 0; l < JHNeuron; l++) {
                        t[l] += d1[k] * w2[l][k];
                    }
                }
                double[] d2 = new double[JHNeuron];
                for (int k = 0; k < JHNeuron; k++) {
                    d2[k] = z[k] * (1 - z[k]) * t[k];
                    if (bias) {
                        db1[k] = d2[k] * LearningRate;
                    }
                }
                for (int k = 0; k < JHNeuron; k++) {
                    for (int l = 0; l < JINeuron; l++) {
                        dw1[l][k] = d2[k] * input[l] * LearningRate;
                    }
                }
            }
            MSE = countMSE(dataSetLearning);
            System.out.println(MSE);
        }
    }

    public double testing(DefaultTableModel tabel) {

        double benar = 0;
        for (int i = 0; i < dataSetTesting.size(); i++) {

            CarEvaluation ce = getDataTesting(i);
            double input[] = ce.getDataArray();
            double u1[] = new double[JHNeuron];
            double z[] = new double[JHNeuron];
            for (int k = 0; k < JHNeuron; k++) {
                for (int l = 0; l < JINeuron; l++) {
                    u1[k] += (input[l] * w1[l][k]);
                }
                if (bias) {
                    u1[k] += b1[k];
                }
                z[k] = 1 / (1 + Math.exp(-u1[k]));
            }
            double[] u2 = new double[JONeuron];
            double[] y = new double[JONeuron];
            for (int k = 0; k < JONeuron; k++) {
                for (int l = 0; l < JHNeuron; l++) {
                    u2[k] += (z[l] * w2[l][k]);
                }
                if (bias) {
                    u2[k] += b2[k];
                }
                y[k] = 1 / (1 + Math.exp(-u2[k]));
                y[k] = Math.round(y[k]); 
            }
            tabel.addRow(new Object[]{
                ce.getBuying(),
                ce.getMaint(),
                ce.getDoors(),
                ce.getPersons(),
                ce.getLug_boot(),
                ce.getSafety(),
                ce.getAcceptability(0),
                ce.getAcceptability(1),
                ce.getAcceptability(2),
                ce.getAcceptability(3),
                y[0],
                y[1],
                y[2],
                y[3],});;
            int error = 0;
            for (int k = 0; k < 4; k++) {

                if (ce.getAcceptability(k) == y[k]) {
                    error++;
                }
            }
            if (error == 4) {
                benar++;
            }

        }
        this.getWeight();
        return 100 * (benar / dataSetTesting.size());

    }
}
