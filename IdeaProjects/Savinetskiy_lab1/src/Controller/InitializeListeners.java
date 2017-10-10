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
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
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
    private CheckMenuItem newWindow;

    private Image originalImage;
    //private Image originalImage = (new Image(Class.class.getResourceAsStream("/Lenna.jpg")));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageView.setImage(originalImage);

        open.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());
                try{
                    originalImage = new Image(file.toURI().toString());
                    imageView.setImage(originalImage);}
                    catch (NullPointerException e){ }
            }
        });

        fast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(newWindow.isSelected()) openImageInNewWindow(fast.getText(), Converting.fast(imageView.getImage()));
                else imageView.setImage(Converting.fast(imageView.getImage()));
            }
        });

        correctionForTheHumanEye.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(newWindow.isSelected())
                    openImageInNewWindow(correctionForTheHumanEye.getText(), Converting.correctionForTheHumanEye(imageView.getImage()));
                else
                    imageView.setImage(Converting.correctionForTheHumanEye(imageView.getImage()));
            }
        });

        desaturation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(newWindow.isSelected())
                    openImageInNewWindow(desaturation.getText(), Converting.desaturation(imageView.getImage()));
                else
                    imageView.setImage(Converting.desaturation(imageView.getImage()));
            }
        });

        gradationByMinimum.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(newWindow.isSelected())
                    openImageInNewWindow(gradationByMinimum.getText(), Converting.gradationByMinimum(imageView.getImage()));
                else
                    imageView.setImage(Converting.gradationByMinimum(imageView.getImage()));
            }
        });

        gradationByMaximum.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(newWindow.isSelected())
                    openImageInNewWindow(gradationByMaximum.getText(), Converting.gradationByMaximum(imageView.getImage()));
                else
                    imageView.setImage(Converting.gradationByMaximum(imageView.getImage()));
            }
        });

        histogramEqualization.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(newWindow.isSelected())
                    openImageInNewWindow(histogramEqualization.getText(), Converting.histogramEqualization(imageView.getImage()));
                else
                    imageView.setImage(Converting.histogramEqualization(imageView.getImage()));
            }
        });

        original.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                imageView.setImage(originalImage);
            }
        });

        histogram.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openHistogram(histogram.getText(), imageView.getImage());
            }
        });

        sample1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openSample(sample1);
            }
        });
        sample2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openSample(sample2);
            }
        });
        sample3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openSample(sample3);
            }
        });
        sample4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openSample(sample4);
            }
        });
        sample5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openSample(sample5);
            }
        });
    }
    private void openImageInNewWindow(String title, Image image) {
        Stage stage = new Stage();
        stage.setMinHeight(200);
        stage.setMinWidth(300);
        stage.setTitle(title);

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
        imageView.setImage(new Image(Class.class.getResourceAsStream("/" + sample.getText() + ".jpg")));
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