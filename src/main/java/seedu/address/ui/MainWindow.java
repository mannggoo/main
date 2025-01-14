package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private EventListPanel eventListPanel;
    private TaskListPanel taskListPanel;
    private ReminderListPanel reminderListPanel;
    private ResultDisplay resultDisplay;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane taskListPanelPlaceholder;

    @FXML
    private StackPane eventListPanelPlaceholder;

    @FXML
    private StackPane reminderListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private TabPane viewsPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        //Listen to changes in tab selection
        viewsPlaceholder.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        try {
                            executeCommand("show " + t1.getId());
                        } catch (CommandException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {

        taskListPanel = new TaskListPanel(logic.getVisualList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        eventListPanel = new EventListPanel(logic.getVisualList());
        eventListPanelPlaceholder.getChildren().add(eventListPanel.getRoot());

        reminderListPanel = new ReminderListPanel(logic.getVisualList());
        reminderListPanelPlaceholder.getChildren().add(reminderListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());


    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        primaryStage.hide();
    }

    /**
     * Switches the view.
     *
     * @param targetView
     */
    private void handleSwitchView(String targetView) {
        switch(targetView) {
        case "T":
            viewsPlaceholder.getSelectionModel().select(0);
            break;
        case "E":
            viewsPlaceholder.getSelectionModel().select(1);
            break;
        case "R":
            viewsPlaceholder.getSelectionModel().select(2);
            break;
        case "C":
            viewsPlaceholder.getSelectionModel().select(3);
            break;
        default:
            break;
        }
    }

    public TaskListPanel getTaskListPanel() {
        return taskListPanel;
    }

    public EventListPanel getEventListPanel() {
        return eventListPanel;
    }

    public ReminderListPanel getReminderListPanel() {
        return reminderListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    @FXML
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            resultDisplay.setMessageFromUser(commandText);
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isSwitchViews()) {
                handleSwitchView(commandResult.getTargetView().trim());
            }

            taskListPanel = new TaskListPanel(logic.getVisualList());
            taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

            eventListPanel = new EventListPanel(logic.getVisualList());
            eventListPanelPlaceholder.getChildren().add(eventListPanel.getRoot());

            reminderListPanel = new ReminderListPanel(logic.getVisualList());
            reminderListPanelPlaceholder.getChildren().add(reminderListPanel.getRoot());

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
