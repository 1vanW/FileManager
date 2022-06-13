package com.file.manager;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller  {
    @FXML
    VBox leftPanel;
    @FXML
    VBox rightPanel;



    //Закрыть программу
    public void buttonExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void copyButtonAction(ActionEvent actionEvent) {
        PanelController leftPanelController = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightPanelController = (PanelController) rightPanel.getProperties().get("ctrl");

        if (leftPanelController.getSelectedFileName() == null && rightPanelController.getSelectedFileName() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "no files were selected",ButtonType.OK);
                alert.showAndWait();
                return;

        }
        PanelController srcPC= null;
        PanelController dstPC= null;

        if (leftPanelController.getSelectedFileName() != null){
            srcPC = leftPanelController;
            dstPC = rightPanelController;
        }

        if (rightPanelController.getSelectedFileName() != null){
            srcPC = rightPanelController;
            dstPC = leftPanelController;
        }

        Path srcPath = Paths.get(srcPC.getCurrentPath(),srcPC.getSelectedFileName());

        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

        try {
            Files.copy(srcPath,dstPath);
            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "failed to copy file", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void moveButtonAction(ActionEvent actionEvent) {
        PanelController leftPanelController = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightPanelController = (PanelController) rightPanel.getProperties().get("ctrl");

        if (leftPanelController.getSelectedFileName() == null && rightPanelController.getSelectedFileName() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "no files were selected",ButtonType.OK);
            alert.showAndWait();
            return;

        }
        PanelController srcPC= null;
        PanelController dstPC= null;

        if (leftPanelController.getSelectedFileName() != null){
            srcPC = leftPanelController;
            dstPC = rightPanelController;
        }

        if (rightPanelController.getSelectedFileName() != null){
            srcPC = rightPanelController;
            dstPC = leftPanelController;
        }

        Path srcPath = Paths.get(srcPC.getCurrentPath(),srcPC.getSelectedFileName());

        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

        try {
            Files.move(srcPath,dstPath);
            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "failed to copy file", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void deleteButtonAction(ActionEvent actionEvent) {
        PanelController leftPanelController = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightPanelController = (PanelController) rightPanel.getProperties().get("ctrl");

        if (leftPanelController.getSelectedFileName() == null && rightPanelController.getSelectedFileName() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "no files were selected",ButtonType.OK);
            alert.showAndWait();
            return;

        }

        PanelController srcPC= null;
        PanelController dstPC= null;

        if (leftPanelController.getSelectedFileName() != null){
            srcPC = leftPanelController;
            dstPC = rightPanelController;
        }

        if (rightPanelController.getSelectedFileName() != null){
            srcPC = rightPanelController;
            dstPC = leftPanelController;
        }

        Path srcPath = Paths.get(srcPC.getCurrentPath(),srcPC.getSelectedFileName());

        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

        try {
            Files.delete(srcPath);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "failed to delete file", ButtonType.OK);
            alert.showAndWait();
        }


    }
}
