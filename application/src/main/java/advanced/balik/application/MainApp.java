package advanced.balik.application;

import advanced.balik.application.view.MainInterfaceController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MainApp extends Application {

    private static final Logger log = Logger.getLogger(MainApp.class);

    private Stage primaryStage;
    private Parent mainWindow;

    /**
     * Возвращает главную сцену.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private final static String MAIN_PATH = "/fxml/MainWindow.fxml";

    private final static String ICON_PATH = "/img/icon.png";

    /**
     * Минимальная высота окна в пикселях.
     */
    private static final int MIN_HEIGHT = 695;

    /**
     * Минимальная ширина окна в пикселях.
     */
    private static final int MIN_WIDTH = 700;

    /**
     * Значок приложения.
     */
    private static final Image ICON = new Image(MainApp.class.getResourceAsStream(ICON_PATH));

    /**
     * Заголовок окна.
     */
    private static final String TITLE = "Leftist Heap";


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(TITLE);
        this.primaryStage.getIcons().add(ICON);
        this.primaryStage.setMinHeight(MIN_HEIGHT);
        this.primaryStage.setMinWidth(MIN_WIDTH);

        showMainWindow();
        this.primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void showMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_PATH));
            mainWindow = loader.load();
            MainInterfaceController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(mainWindow);
            primaryStage.setScene(scene);

            this.primaryStage.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth)
                    -> {
                primaryStage.setWidth((Double) newSceneWidth);
                ((AnchorPane) mainWindow).setMinWidth((Double) newSceneWidth);
                log.info("Width: " + newSceneWidth);
            });
            this.primaryStage.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight)
                    -> {
                primaryStage.setHeight((Double) newSceneHeight);
                ((AnchorPane) mainWindow).setMinHeight((Double) newSceneHeight);
               log.info("Height: " + newSceneHeight);
            });

        } catch (IOException e) {
            log.error("show MainWindow error\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
