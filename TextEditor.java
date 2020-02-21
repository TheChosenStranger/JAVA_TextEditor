/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
/**
 *
 * @author Moustafa Ghareeb
 */
public class TextEditor extends Application{


        String txt;
    @Override
    public void start(Stage myStage) throws Exception {
        MenuBar menuBar = new MenuBar();
        SeparatorMenuItem separator1 = new SeparatorMenuItem();
        SeparatorMenuItem separator2 = new SeparatorMenuItem();
        SeparatorMenuItem separator3 = new SeparatorMenuItem();
        //////Text Area
        TextArea editor = new TextArea();
        editor.setPromptText("Enter your text here......");    
        ////////Menus
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Button help = new Button("Help");
        ////////File Menu
        MenuItem fileNew = new MenuItem("New");
        fileNew.setAccelerator(KeyCombination.keyCombination("Ctrl + N"));
        MenuItem fileOpen = new MenuItem("Open");
        fileOpen.setAccelerator(KeyCombination.keyCombination("Ctrl + O"));
        MenuItem fileSave = new MenuItem("Save");
        fileSave.setAccelerator(KeyCombination.keyCombination("Ctrl + S"));
        MenuItem fileExit = new MenuItem("Exit");
        fileExit.setAccelerator(KeyCombination.keyCombination("Ctrl + F4"));
        file.getItems().addAll(fileNew,fileOpen,fileSave,separator1,fileExit);
        ////////Edit Menu
        MenuItem editUndo = new MenuItem("Undo");
        editUndo.setAccelerator(KeyCombination.keyCombination("Ctrl + Z"));
        MenuItem editCut = new MenuItem("Cut");
        editCut.setAccelerator(KeyCombination.keyCombination("Ctrl + X"));
        MenuItem editCopy = new MenuItem("Copy");
        editCopy.setAccelerator(KeyCombination.keyCombination("Ctrl + C"));
        MenuItem editPaste = new MenuItem("Paste");
        editPaste.setAccelerator(KeyCombination.keyCombination("Ctrl + V"));
        MenuItem editDelete = new MenuItem("Delete");
        editDelete.setAccelerator(KeyCombination.keyCombination("Ctrl + D"));
        MenuItem editSelAll = new MenuItem("Select All");
        editSelAll.setAccelerator(KeyCombination.keyCombination("Ctrl + A"));
        edit.getItems().addAll(editUndo,separator2,editCut,editCopy,editPaste,editDelete,separator3,editSelAll);
        
        ////Finalizing
        menuBar.getMenus().addAll(file,edit);
        FlowPane fPane = new FlowPane(menuBar,help);
        BorderPane myPane = new BorderPane(editor);
        myPane.setTop(fPane);
        ///////// New
        fileNew.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(" New ");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to save?");
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo); 
            Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeYes){
                    FileChooser fileChooser = new FileChooser();       
                    File file = fileChooser.showSaveDialog(myStage);
                     try {
                    FileWriter fileWriter = null;

                    fileWriter = new FileWriter(file);
                    fileWriter.write(editor.getText());
                    fileWriter.close();
                      } catch (IOException ex) {}
                } else if (result.get() == buttonTypeNo) {
                    editor.setText("");
                } 
            }
            
        });
        ///////// Open
        fileOpen.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t) {
                FileInputStream fis = null;
                try {
                    FileChooser fileChooser = new FileChooser();
                    File file = fileChooser.showOpenDialog(myStage);
                    fis = new FileInputStream(file);
                    int size = fis.available();
                    byte[] b = new byte[size];
                    fis.read(b);
                    editor.setText(new String(b));
                    fis.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        fis.close();
                    } catch (IOException ex) {
                        Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        ///////// Save
        fileSave.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t) {
                    FileChooser fileChooser = new FileChooser();       
                    File file = fileChooser.showSaveDialog(myStage);
                     try {
                    FileWriter fileWriter = null;

                    fileWriter = new FileWriter(file);
                    fileWriter.write(editor.getText());
                    fileWriter.close();
                      } catch (IOException ex) {}
            }
        });
        ///////// Exit
        fileExit.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(" Exit ");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure?");
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo); 
            Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeYes){
                     Platform.exit();
                } else if (result.get() == buttonTypeNo) {
                    // ... user chose "No"
                } 
            }
            
        });
        ///////// Undo
        editUndo.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t) {
                
                editor.undo();
            }
            
        });
        ///////// Copy
        editCopy.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t) {
                
                txt = editor.getSelectedText();
            }
            
        });
         ///////// Paste
        editPaste.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t) {
               editor.appendText(txt);
            }
            
        });
        ///////// Cut
        editCut.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t) {
               txt = editor.getSelectedText();
               editor.replaceSelection("");
            }
            
        });
        ///////// Delete
        editDelete.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t) {
               editor.setText("");
            }
            
        });
        ///////// SelectAll
        editSelAll.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t) {
               editor.selectAll();
            }
            
        });
        ///////// Help
        help.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("This Wonderful Editor was made by");
            alert.setHeaderText(null);
            alert.setContentText("The Chosen Stranger");
            alert.showAndWait();
               }
            
        }); 
        /////Start Scene
        Scene myScene = new Scene(myPane,500,500);
        myStage.setScene(myScene);
        myStage.show();
        }
    
    public static void main (String[] args)
    {
        Application.launch(args);
    }
    
}
