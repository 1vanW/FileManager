package com.file.manager;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PanelController implements Initializable {
    @FXML
    TableView<FileInfo> filesTable;

    @FXML
    ComboBox<String> diskBox;

    @FXML
    TextField pathField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<FileInfo,String> fileTypeColumn = new TableColumn<>();
        fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getName()));
        fileTypeColumn.setPrefWidth(24);

        TableColumn<FileInfo,String> fileNameColumn = new TableColumn<>("Name");
        fileNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        fileNameColumn.setPrefWidth(240);

        TableColumn<FileInfo,Long> fileSizeColumn = new TableColumn<>("Size");
        fileSizeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getSize()));
        fileSizeColumn.setPrefWidth(120);
        fileSizeColumn.setCellFactory(column ->{
            return new TableCell<FileInfo,Long>(){
                @Override
                protected void updateItem(Long item, boolean empty){
                    super.updateItem(item,empty);
                    if (item == null || empty){
                        setText(null);
                        setStyle("");
                    }
                    else{
                        String text = String.format("%,d bytes", item);
                        if (item == -1 ){
                            text = "[DIRECTORY]";
                        }

                        setText(text);

                    }
                }
            };


        });

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        TableColumn<FileInfo,String> fileDateColumn = new TableColumn<>("Date Modified");

        fileDateColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastModified().format(dateTimeFormatter)));
        fileDateColumn.setPrefWidth(200);



        filesTable.getColumns().addAll(fileTypeColumn,fileNameColumn,fileSizeColumn,fileDateColumn);
        filesTable.getSortOrder().add(fileTypeColumn);


        diskBox.getItems().clear();

        for (Path p: FileSystems.getDefault().getRootDirectories()){
            diskBox.getItems().add(p.toString());

        }

        diskBox.getSelectionModel().select(0);

        filesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    Path path = Paths.get(pathField.getText())
                            .resolve(filesTable.getSelectionModel()
                                    .getSelectedItem()
                                    .getName());
                    if (Files.isDirectory(path)){
                        updateList(path);
                    }
                }
            }
        });


        updateList(Paths.get("."));
    }


    public void updateList(Path path){

        pathField.setText(path.normalize().toAbsolutePath().toString());
        filesTable.getItems().clear();
        try {
            filesTable.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
            filesTable.sort();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Failed to update list of files", ButtonType.OK);
            alert.showAndWait();

        }
    }

    public void btnPathUpAction(ActionEvent actionEvent) {
            Path upperPath = Paths.get(pathField.getText()).getParent();
            if (upperPath != null){
                updateList(upperPath);
            }
    }

    public void selectDiskAction(ActionEvent actionEvent) {
        ComboBox<String> element = (ComboBox<String>) actionEvent.getSource();
        updateList(Paths.get(element.getSelectionModel().getSelectedItem()));
    }


    public String getSelectedFileName(){

        if (!filesTable.isFocused()){
            return null;
        }


        return filesTable.getSelectionModel().getSelectedItem().getName();
    }

    public String getCurrentPath(){
        return pathField.getText();
    }
}
