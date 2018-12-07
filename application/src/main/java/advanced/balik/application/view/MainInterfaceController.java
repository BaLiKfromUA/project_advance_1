package advanced.balik.application.view;

import advanced.balik.application.MainApp;
import advanced.balik.application.graph.HeapGraph;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.Optional;
import java.util.Random;

public class MainInterfaceController {

    /**
     * Генератор случайных чисел.
     */
    private static final Random RANDOM = new Random();

    /**
     * Верхняя граница (не включительно) генерации случайных чисел.
     */
    private static final int UPPER_BOUND_RANDOM = 1000;

    /**
     * Нижняя граница (включительно) генерации случайных чисел.
     */
    private static final int LOWER_BOUND_RANDOM = 0;

    /**
     * Визуальное представление кучи
     */
    private final HeapGraph heapGraph = new HeapGraph();

    private MainApp mainApp;

    private Integer step;

    @FXML
    private Label stepLabel;
    @FXML
    private TextField inputValue;
    @FXML
    private FlowPane board;
    @FXML
    private BorderPane workSpace;
    @FXML
    private VBox sideBar;
    @FXML
    private ToggleButton sideBarToggle;
    @FXML
    private SplitPane splitPane;
    @FXML
    private ToggleButton hideConsoleToggle;
    @FXML
    private ScrollPane consoleTab;
    @FXML
    private AnchorPane lowerTab;
    @FXML
    private AnchorPane rightControlGroup;
//todo: FIX SPACE


    /**
     * Убрать либо вернуть боковую панель управления.
     */
    @FXML
    public void hideSideBar() {
        if (!sideBarToggle.isSelected()) {
            rightControlGroup.getChildren().remove(sideBar);
        } else {
            rightControlGroup.getChildren().add(sideBar);
        }
    }

    @FXML
    public void hideConsole() {
        if (hideConsoleToggle.isSelected()) {
            splitPane.setDividerPositions(1.0);
            lowerTab.getChildren().remove(consoleTab);
        } else {
            splitPane.setDividerPositions(0.75);
            lowerTab.getChildren().add(consoleTab);
        }
    }

    @FXML
    private void insert() {
        getInput().ifPresent(heapGraph::addNode);

    }

    /**
     * Очистить дерево.
     */
    @FXML
    public void clean() {
        heapGraph.clear();
        inputValue.clear();
        //cache.drop();
        //updateTraversal();
    }


    /**
     * Добавить случайное значение как новый элемент кучи.
     */
    @FXML
    public void insertRandom() {
        RANDOM.setSeed(System.currentTimeMillis());
        int randomValue = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
        heapGraph.addNode(randomValue);
    }

    @FXML
    private void logAction() {

    }

    @FXML
    private void getMin() {

    }

    public MainInterfaceController() {
        step = 0;
    }

    @FXML
    private void initialize() {
        Group content = heapGraph.getContent();
        board.getChildren().add(content);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Общий обработчик событий клавиатуры.
     * <br>По кнопке ENTER - принимает значение из поля ввода.
     *
     * @param keyEvent событие нажатия на кнопку клавиатуры.
     */
    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        if (keyCode.equals(KeyCode.ENTER)) {
            getInput().ifPresent(heapGraph::addNode);
        }
        //navigateToSelected();
    }

    /**
     * Проверить наличие целого числа в поле ввода и вернуть его внутри контейнера Optional.
     *
     * @return Optional, возможно содержащий целое число.
     */
    private Optional<Integer> getInput() {
        String input = inputValue.getText();

        Optional<Integer> optional;
        if (input.matches("^(-?)\\d+")) {
            optional = Optional.of(Integer.parseInt(input));
        } else {
            optional = Optional.empty();
        }
        inputValue.clear();
        return optional;
    }

    /**
     * Завершить работу программы.
     */
    @FXML
    public void close() {
        Platform.exit();
    }

}