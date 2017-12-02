package Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class InitializeListeners implements Initializable {
    @FXML
    private ImageView imageView;
    @FXML
    private MenuItem open;
    @FXML
    private MenuItem fast;
    @FXML
    private MenuItem correctionForTheHumanEye;
    @FXML
    private MenuItem desaturation;
    @FXML
    private MenuItem gradationByMinimum;
    @FXML
    private MenuItem gradationByMaximum;
    @FXML
    private MenuItem matrixFilter;
    @FXML
    private MenuItem matrixFilter1;
    @FXML
    private MenuItem matrixFilter2;
    @FXML
    private MenuItem matrixFilter3;
    @FXML
    private MenuItem matrixFilter4;
    @FXML
    private MenuItem matrixFilter5;
    @FXML
    private MenuItem matrixFilter6;
    @FXML
    private MenuItem histogramEqualization;
    @FXML
    private MenuItem histogram;
    @FXML
    private MenuItem original;
    @FXML
    private MenuItem sample1;
    @FXML
    private MenuItem sample2;
    @FXML
    private MenuItem sample3;
    @FXML
    private MenuItem sample4;
    @FXML
    private MenuItem sample5;
    @FXML
    private MenuItem sample6;
    @FXML
    private CheckMenuItem newWindow;

    private Image originalImage;
    //private Image originalImage = (new Image(Class.class.getResourceAsStream("/Lenna.jpg")));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int[][] matrix1 = new int[][] {
                {0, 0,0},
                {0, 0, 1},
                {0, 0, 0}
        };
        int[][] matrix2 = new int[][] {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
        int[][] matrix3 = new int[][] {
                {0, 1, 0},
                {1, -4, 1},
                {0, 1, 0}
        };

        int[][] matrix4 = new int[][] {
                {1, 0, 0}
        };

        int[][] matrix5 = new int[][] {
                {-1, 0, 1},
                {-2, 0, 2},
                {-1, 0, 1}
        };

        int[][] matrix6 = new int[][] {
                {-1, -2, -1},
                {0, 0, 0},
                {1, 2, 1}
        };

        open.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());
            try{
                originalImage = new Image(file.toURI().toString());
                imageView.setImage(originalImage);}
            catch (NullPointerException e){ }
        });

        fast.setOnAction(event -> {
            if(newWindow.isSelected()) openImageInNewWindow(fast.getText(), Converting.fast(imageView.getImage()));
            else imageView.setImage(Converting.fast(imageView.getImage()));
        });

        correctionForTheHumanEye.setOnAction(event -> {
            if(newWindow.isSelected())
                openImageInNewWindow(correctionForTheHumanEye.getText(), Converting.correctionForTheHumanEye(imageView.getImage()));
            else
                imageView.setImage(Converting.correctionForTheHumanEye(imageView.getImage()));
        });

        desaturation.setOnAction(event -> {
            if(newWindow.isSelected())
                openImageInNewWindow(desaturation.getText(), Converting.desaturation(imageView.getImage()));
            else
                imageView.setImage(Converting.desaturation(imageView.getImage()));
        });

        gradationByMinimum.setOnAction(event -> {
            if(newWindow.isSelected())
                openImageInNewWindow(gradationByMinimum.getText(), Converting.gradationByMinimum(imageView.getImage()));
            else
                imageView.setImage(Converting.gradationByMinimum(imageView.getImage()));
        });

        gradationByMaximum.setOnAction(event -> {
            if(newWindow.isSelected())
                openImageInNewWindow(gradationByMaximum.getText(), Converting.gradationByMaximum(imageView.getImage()));
            else
                imageView.setImage(Converting.gradationByMaximum(imageView.getImage()));
        });

        matrixFilter.setOnAction(event -> {
            TextInputDialog setSize = new TextInputDialog();
            setSize.setTitle("Размерность матрицы");
            setSize.setHeaderText("Введите размеры матрицы. Например \"3 3\"");
            Optional<String> resultString = setSize.showAndWait();

            int divider = 1;
            String size;
            if (resultString.isPresent()) {
                size = resultString.get();
                if (!Objects.equals(size, "")){
                    int i = Integer.parseInt(size.split(" ")[0]);
                    int j = Integer.parseInt(size.split(" ")[1]);
                    int[][] matrix = new int[i][j];
                    System.out.println(i + " " + j);

                    TextInputDialog setElements = new TextInputDialog();
                    setElements.setTitle("Элементы матрицы");
                    setElements.setHeaderText("Введите элементы матрицы (" + (i*j) + " элемента(ов)). Например \"1 2 3 4 5...\"");
                    Optional<String> elements = setElements.showAndWait();
                    size = elements.get();
                    int n = 0;
                    if (!size.equals("")){
                        for (int k = 0; k < i; k++) {
                            for (int l = 0; l < j; l++) {
                                int element = Integer.parseInt(size.split(" ")[n]);
                                matrix[k][l] = element;
                                n++;
                            }
                        }
                        TextInputDialog setDivider = new TextInputDialog();
                        setDivider.setTitle("Делитель");
                        setDivider.setHeaderText("1/x*matrix. Введите x");
                        Optional<String> stringDivider = setDivider.showAndWait();
                        divider = Integer.parseInt(stringDivider.get());

                        if(newWindow.isSelected())
                            openImageInNewWindow(matrixFilter.getText(), Converting.matrixFilter(imageView.getImage(), matrix, divider));
                        else
                            imageView.setImage(Converting.matrixFilter(imageView.getImage(), matrix, divider));
                    }
                }
            }
        });

        createMatrixFilter(matrixFilter1, matrix1, 1);
        createMatrixFilter(matrixFilter2, matrix2, 9);
        createMatrixFilter(matrixFilter3, matrix3, 1);
        createMatrixFilter(matrixFilter4, matrix4, 1);
        createMatrixFilter(matrixFilter5, matrix5, 1);
        createMatrixFilter(matrixFilter6, matrix6, 1);

        histogramEqualization.setOnAction(event -> {
            if(newWindow.isSelected())
                openImageInNewWindow(histogramEqualization.getText(), Converting.histogramEqualization(imageView.getImage()));
            else
                imageView.setImage(Converting.histogramEqualization(imageView.getImage()));
        });

        original.setOnAction(event -> imageView.setImage(originalImage));

        histogram.setOnAction(event -> openHistogram(histogram.getText(), imageView.getImage()));


        sample1.setOnAction(event -> openSample(sample1));
        sample2.setOnAction(event -> openSample(sample2));
        sample3.setOnAction(event -> openSample(sample3));
        sample4.setOnAction(event -> openSample(sample4));
        sample5.setOnAction(event -> openSample(sample5));
        sample6.setOnAction(event -> openSample(sample6));
    }

    private void createMatrixFilter(MenuItem menuItem, int[][] matrix, int divider){
        menuItem.setText(Arrays.deepToString(matrix));
        menuItem.setOnAction(event -> {
            if(newWindow.isSelected())
                openImageInNewWindow(matrixFilter.getText(), Converting.matrixFilter(imageView.getImage(), matrix, divider));
            else
                imageView.setImage(Converting.matrixFilter(imageView.getImage(), matrix, divider));

        });
    }

    private void openImageInNewWindow(String title, Image image) {
        Stage stage = new Stage();
        stage.setMinHeight(200);
        stage.setMinWidth(300);
        stage.setTitle(title);
        stage.setWidth(image.getWidth() + 10);
        stage.setHeight(image.getHeight() + 10);
        stage.setMaxWidth(600*image.getWidth()/image.getHeight());
        stage.setMaxHeight(600);

        AnchorPane root = new AnchorPane();

        WrappedImageView iv = new WrappedImageView();
        iv.setFitHeight(400);
        iv.setFitWidth(600);

        iv.setImage(image);
        AnchorPane.setRightAnchor(iv, 1.0);
        AnchorPane.setTopAnchor(iv, 1.0);
        AnchorPane.setLeftAnchor(iv, 1.0);
        AnchorPane.setBottomAnchor(iv, 1.0);


        Button getHistogram = new Button("Показать гистограмму");
        getHistogram.setPrefSize(150,20);
        getHistogram.setOpacity(0.4);
        getHistogram.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openHistogram(title, image);
            }
        });

        root.getChildren().add(iv);
        root.getChildren().add(getHistogram);

        stage.setScene(new Scene(root, 600, 400));

        stage.show();
    }

    private void openHistogram(String title, Image image) {
        Stage stage = new Stage();
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.setTitle(title);

        CategoryAxis xAxis    = new CategoryAxis();
        xAxis.setLabel("Уровень яркости");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Количество пикселей");

        BarChart barChart = new BarChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();

        int[] arr = Converting.histogram(image);
        for (int i = 0; i < arr.length; i++) {
            dataSeries1.getData().add(new XYChart.Data(""+ i, arr[i]));
        }
        barChart.getData().add(dataSeries1);

        VBox vbox = new VBox(barChart);
        Scene scene = new Scene(vbox, 400, 200);

        stage.setScene(scene);

        stage.show();
    }

    private void openSample(MenuItem sample){
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream( sample.getText() + ".jpg");
        imageView.setImage(new Image(is));
        originalImage = imageView.getImage();
    }


}
class WrappedImageView extends ImageView
{
    WrappedImageView()
    {
        setPreserveRatio(false);
    }

    @Override
    public double minWidth(double height)
    {
        return 40;
    }

    @Override
    public double prefWidth(double height)
    {
        Image I=getImage();
        if (I==null) return minWidth(height);
        return I.getWidth();
    }

    @Override
    public double maxWidth(double height)
    {
        return 16384;
    }

    @Override
    public double minHeight(double width)
    {
        return 40;
    }

    @Override
    public double prefHeight(double width)
    {
        Image I=getImage();
        if (I==null) return minHeight(width);
        return I.getHeight();
    }

    @Override
    public double maxHeight(double width)
    {
        return 16384;
    }

    @Override
    public boolean isResizable()
    {
        return true;
    }

    @Override
    public void resize(double width, double height)
    {
        setFitWidth(width);
        setFitHeight(height);
    }
}