package advanced.balik.application.view;

import advanced.balik.application.MainApp;
import advanced.balik.application.graph.HeapGraph;
import advanced.balik.application.graph.Style;
import advanced.balik.application.graph.ViewMode;
import advanced.balik.application.graph.Turn;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainInterfaceController {

    private static final Logger log = Logger.getLogger(MainInterfaceController.class);

    /**
     * Длительность задержки при автоматических действиях
     */
    private static final Duration DURATION = Duration.millis(1000);

    private volatile int AnimationDuration = 1000;
    @FXML
    private Slider animationSlider;

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
    private static final int LOWER_BOUND_RANDOM = -999;

    /**
     * Визуальное представление кучи
     */
    private final HeapGraph heapGraph = new HeapGraph();

    private MainApp mainApp;

    @FXML
    private Label stepLabel;
    @FXML
    private Label logLabel;

    /* Animation controls */
    @FXML
    private TitledPane animationPane;
    @FXML
    private TextField inputValue;
    @FXML
    private TextField turnValue;
    /* Workspace */

    @FXML
    private FlowPane board;

    @FXML
    private ScrollPane viewArea;

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
    @FXML
    private Menu viewMenu;

    private Integer step;

    private List<Integer> turns;

    private final Animation animation = new Timeline(new KeyFrame(
            DURATION,
            actionEvent -> {
                if (!turns.isEmpty()) {
                    ++step;
                    if (turns.get(0) == 1) {
                        insertWithoutAnimation();
                    } else {
                        getMinWithoutAnimation();
                    }
                    turns.remove(0);
                } else {
                    stop();
                }
            }));

    public MainInterfaceController() {
        step = 0;
        this.turns = new ArrayList<>();
    }

    @FXML
    private void initialize() {
        Group content = heapGraph.getContent();
        board.getChildren().add(content);
        animation.setCycleCount(Animation.INDEFINITE);
        mode = new ArrayList<>(Arrays.asList(ViewMode.values()));
        currMode = ViewMode.STANDART;
        logAction(Action.EMPTY.getAction());
        // Adding Listener to value property.
        animationSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                AnimationDuration = newValue.intValue());
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * HEAP OPERATIONS
     **/
    @FXML
    private void insert() {
        getInput(inputValue).ifPresent(value -> {
            if (!heapGraph.checkValue(value)) {
                if (Math.abs(value) < 1000) {
                    ++step;
                    if (isAnimation) {
                        insertInOtherThread(value);
                    } else {
                        heapGraph.addNode(value);
                        heapGraph.draw();
                        heapGraph.findNode(value);
                        logAction(String.format(Action.INSERT.getAction(), value));
                    }
                } else {
                    log.error(Error.TOO_BIG.getHeader());
                    showError(Error.TOO_BIG);
                }
            } else {
                ++step;
                heapGraph.unselect();
                heapGraph.findNode(value);
                navigateToSelected();
                logAction(String.format(Action.ALREADY_IN_THE_HEAP.getAction(), value));
            }
        });
    }

    /**
     * Добавить случайное значение как новый элемент кучи.
     */
    @FXML
    public void insertRandom() {
        ++step;
        RANDOM.setSeed(System.currentTimeMillis());
        int randomValue = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
        if (!heapGraph.checkValue(randomValue)) {
            if (isAnimation) {
                insertInOtherThread(randomValue);
            } else {
                insertWithoutAnimation();
            }
        } else {
            insertRandom();
        }
    }

    private void insertWithoutAnimation() {
        RANDOM.setSeed(System.currentTimeMillis());
        int randomValue = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
        if (!heapGraph.checkValue(randomValue)) {
            heapGraph.addNode(randomValue);
            heapGraph.draw();
            heapGraph.unselect();
            heapGraph.findNode(randomValue);
            logAction(String.format(Action.INSERT.getAction(), randomValue));
            navigateToSelected();
        } else {
            insertWithoutAnimation();
        }
    }

    private void insertInOtherThread(int value) {
        Thread insertThread = new Thread(() -> {
            Platform.runLater(() -> this.disableAll(true));
            heapGraph.addNode(value);
            ArrayList<Turn> mergeLogs = heapGraph.getLog();

            for (Turn currentTurn : mergeLogs) {
                Platform.runLater(() -> {
                    heapGraph.drawCurrent(currentTurn.getHeap());
                    if (!heapGraph.checkValue(value)) {
                        Platform.runLater(() -> heapGraph.drawFutureNewElement(value));
                    }
                    heapGraph.unselect();
                    heapGraph.findNode(currentTurn.getSelectedNode());
                    navigateToSelected();
                    logAction(currentTurn.getTurnLog());
                });
                try {
                    Thread.sleep(AnimationDuration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Platform.runLater(heapGraph::draw);
            Platform.runLater(() -> heapGraph.findNode(value));
            Platform.runLater(this::navigateToSelected);
            Platform.runLater(() -> logAction(String.format(Action.INSERT.getAction(), value)));
            Platform.runLater(() -> this.disableAll(false));
        });

        insertThread.start();
    }

    @FXML
    private void getMin() {
        ++step;

        if (!isAnimation) {
            getMinWithoutAnimation();
            return;
        }
        if (!heapGraph.isEmpty()) {
            int min = heapGraph.getMin();

            Thread minThread = new Thread(() -> {
                try {
                    Platform.runLater(() -> disableAll(true));
                    Platform.runLater(heapGraph::unselect);
                    Thread.sleep(AnimationDuration);
                    Platform.runLater(() -> {
                        heapGraph.findNode(min);
                        navigateToSelected();
                        logAction(String.format(Action.MIN.getAction(), min));
                    });
                    Thread.sleep(AnimationDuration);

                    Platform.runLater(heapGraph::unselect);
                    heapGraph.extractMin();
                    ArrayList<Turn> mergeLogs = heapGraph.getLog();

                    for (Turn currentTurn : mergeLogs) {
                        Platform.runLater(() -> {
                            heapGraph.drawCurrent(currentTurn.getHeap());
                            heapGraph.unselect();
                            heapGraph.findNode(currentTurn.getSelectedNode());
                            navigateToSelected();
                            logAction(currentTurn.getTurnLog());
                        });
                        Thread.sleep(AnimationDuration);
                    }

                    Platform.runLater(() -> {
                        heapGraph.unselect();
                        heapGraph.draw();
                        logAction(String.format(Action.EXTRACT_MIN.getAction(), min));
                    });

                    Platform.runLater(() -> disableAll(false));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            minThread.start();
        } else {
            logAction(Action.EMPTY.getAction());
        }

    }

    private void getMinWithoutAnimation() {
        if (!heapGraph.isEmpty()) {
            int min = heapGraph.getMin();
            heapGraph.extractMin();
            heapGraph.draw();
            heapGraph.unselect();
            logAction(String.format(Action.EXTRACT_MIN.getAction(), min));
        } else {
            logAction(Action.EMPTY.getAction());
        }
    }

    @FXML
    public void stepBack() {
        heapGraph.unselect();
        boolean isBack = heapGraph.stepBack();
        if (isBack) {
            step++;
            logAction(Action.STEP_BACK.getAction());
        } else {
            log.error("Attempt to access non-existent version.");
            showError(Error.NO_VERSIONS);
        }
    }


    /**
     * Очистить дерево.
     */
    @FXML
    public void clean() {
        heapGraph.unselect();
        heapGraph.clear();
        inputValue.clear();
        ++step;
        logAction(Action.CLEAR.getAction());
    }


    /**
     * HIDE PANELS
     **/
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

    /**
     * CONSOLE PRINTER
     **/
    private void logAction(String action) {
        log.info(action);
        stepLabel.setText(step.toString());
        logLabel.setText(action);
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
            insert();
        }
    }

    /**
     * Проверить наличие целого числа в поле ввода и вернуть его внутри контейнера Optional.
     *
     * @return Optional, возможно содержащий целое число.
     */
    private Optional<Integer> getInput(TextField inputField) {
        String input = inputField.getText();

        Optional<Integer> optional;
        if (input.matches("^(-?)\\d+")) {
            optional = Optional.of(Integer.parseInt(input));
        } else {
            log.error(Error.INVALID_INPUT.getHeader());
            optional = Optional.empty();
            showError(Error.INVALID_INPUT);
        }
        inputField.clear();
        return optional;
    }

    @FXML
    private ToggleButton stepButton;

    /**
     * Метод для отключения и включения кнопок боковой панели.
     * Используется при запуске и остановке анимации.
     *
     * @param disable если true, то отключить кнопки боковой панели управления.
     *                Если false - то включить их.
     */
    private void disableControls(boolean disable) {
        Set<Node> controls = sideBar.getChildren().stream()
                .filter(node -> !node.getStyleClass().contains(Style.ANIMATION_BUTTON.getStyleClass()))
                .filter(node -> !node.equals(animationPane))
                .collect(Collectors.toSet());
        controls.forEach(node -> node.setDisable(disable));
        viewMenu.setDisable(disable);
        stepButton.setDisable(disable);
    }

    private void disableAll(boolean disable) {
        Set<Node> controls = new HashSet<>(sideBar.getChildren());
        controls.forEach(node -> node.setDisable(disable));
        viewMenu.setDisable(disable);
        stepButton.setDisable(disable);
    }

    /**
     * Запустить или продолжить анимацию.
     */
    @FXML
    public void play() {
        animation.play();
        disableControls(true);
    }

    /**
     * Приостановить анимацию.
     */
    @FXML
    public void pause() {
        animation.pause();
        disableControls(false);
    }

    /**
     * Остановить анимацию.
     */
    @FXML
    private void stop() {
        animation.stop();
        turns.clear();
        disableControls(false);
    }

    /**
     * Сдвинуть экран до выделенной ячейки.
     */
    private void navigateToSelected() {
        heapGraph.getSelected().ifPresent(label -> {
            Group content = heapGraph.getContent();

            double layoutX = label.getLayoutX() + label.getWidth();
            double layoutMaxX = content.getBoundsInLocal().getMaxX();
            double layoutMinX = content.getBoundsInLocal().getMinX();
            double newH = (layoutX + Math.abs(layoutMinX)) / (Math.abs(layoutMaxX) + Math.abs(layoutMinX));
            viewArea.setHvalue(new BigDecimal(newH).setScale(2, RoundingMode.HALF_UP).doubleValue());

            double layoutY = label.getLayoutY() + label.getWidth();
            double layoutMaxY = content.getBoundsInLocal().getMaxY();
            double layoutMinY = content.getBoundsInLocal().getMinY();
            double newV = (layoutY + Math.abs(layoutMinY)) / (Math.abs(layoutMaxY) + Math.abs(layoutMinY));
            viewArea.setVvalue(new BigDecimal(newV).setScale(3, RoundingMode.HALF_UP).doubleValue());
        });
    }

    /**
     * AUTO MODE
     **/

    @FXML
    private RadioButton insertButton;
    @FXML
    private RadioButton minButton;
    @FXML
    private RadioButton randomButton;

    private boolean onlyInsert;
    private boolean onlyMin;
    private boolean isAnimation = true;

    @FXML
    private void setAnimation() {
        isAnimation = !isAnimation;
    }

    @FXML
    private void autoMode() {
        getInput(turnValue).ifPresent(value -> {
            RANDOM.setSeed(System.currentTimeMillis());
            for (int i = 0; i < value; ++i) {
                if (!onlyMin && !onlyInsert) {
                    turns.add(RANDOM.nextInt(2));
                } else if (onlyInsert) {
                    turns.add(1);
                } else {
                    turns.add(0);
                }
            }
            play();
        });
    }

    @FXML
    public void changeMode(ActionEvent event) {
        insertButton.setSelected(false);
        minButton.setSelected(false);
        randomButton.setSelected(false);

        RadioButton currButton = (RadioButton) event.getSource();
        currButton.setSelected(true);

        final String text = currButton.getText();
        onlyInsert = false;
        onlyMin = false;

        if (text.equals("Only insert")) {
            onlyInsert = true;
        }

        if (text.equals("Only min")) {
            onlyMin = true;
        }
    }

    private void showError(Error error) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Error!");
        alert.setHeaderText(error.getHeader());
        alert.setContentText(error.getMessage());

        alert.showAndWait();
    }

    /**menu bar**/

    /**
     * Завершить работу программы.
     */
    @FXML
    public void close() {
        Platform.exit();
    }

    /**
     * View
     **/
    private List<ViewMode> mode;
    private ViewMode currMode;

    @FXML
    public void setDefault() {
        heapGraph.setMode(ViewMode.STANDART);
        currMode = ViewMode.STANDART;
    }

    @FXML
    public void zoomIn() {
        int index = mode.indexOf(currMode);
        if (index != 0) {
            currMode = mode.get(index - 1);
            heapGraph.setMode(currMode);
        } else {
            log.error(Error.MAX_CELL_SIZE.getHeader());
            showError(Error.MAX_CELL_SIZE);
        }
    }

    @FXML
    public void zoomOut() {
        int index = mode.indexOf(currMode);
        if (index != mode.size() - 1) {
            currMode = mode.get(index + 1);
            heapGraph.setMode(currMode);
        } else {
            log.error(Error.MIN_CELL_SIZE.getHeader());
            showError(Error.MIN_CELL_SIZE);
        }
    }


}