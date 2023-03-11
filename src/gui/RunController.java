package gui;

import controller.Controller;
import exception.MyException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;
import model.adt.*;
import model.stmt.*;
import model.value.StringValue;
import model.value.Value;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RunController {
    public RunController (Controller ctrl){this.ctrl = ctrl;}
    private Controller ctrl;
    private int ID = 0;

    @FXML
    private ListView<Integer> IDView;

    @FXML
    private TextField NoPrgStates;

    @FXML
    private ListView<String> ExeStackView;

    @FXML
    private TableView<Map.Entry<String, Value>> SymTableView;

    @FXML
    private TableColumn<Map.Entry<String, Value>, String> SymName, SymValue;

    @FXML
    private TableView<Map.Entry<Integer, Value>> HeapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> HeapAddress, HeapValue;
    @FXML
    private TableView<Map.Entry<Integer, Integer>> LockTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> LockLocation, LockValue;

    @FXML
    private ListView<String> FileTableView;

    @FXML
    private ListView<String> OutView;

    @FXML
    private Button OneStepButton;

    @FXML
    void OnIDClicked(MouseEvent event) {
        if(IDView.getSelectionModel().getSelectedItem() != null){
            ID = IDView.getSelectionModel().getSelectedIndex();
            configureAll();
        }
    }

    @FXML
    void OneStepButtonOnAction(ActionEvent event) {
        try{
            if(ctrl != null){
                ctrl.oneStep();
                configureAll();
            }
        } catch (MyException e){
            System.out.println(e.getMessage());};
    }

    public void initialize() {
        configureAll();

        HeapAddress.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey().toString()));
        HeapValue.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));

        SymName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        SymValue.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));

        LockLocation.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey().toString()));
        LockValue.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));

    }

    private void configureAll(){
        if(!ctrl.getRepo().isEmpty()){
            //IDView
            List<Integer> range = ctrl.getRepo().getPrgList().stream().map(PrgState::getId).toList();
            IDView.setItems(FXCollections.observableList(range));
            IDView.refresh();

            //NoPrgStates
            NoPrgStates.setText(String.valueOf((Integer)ctrl.getRepo().size()));

            if(ctrl.getRepo().get(ID) == null || ctrl.getRepo().get(ID).getStk() == null){
                List<Integer> list = IntStream.range(0, ctrl.getRepo().size()).boxed().filter(i -> {if (ctrl.getRepo().get(i) != null) return ctrl.getRepo().get(i).getStk() != null; else return false;}).toList();
                ID = list.get(0);
            }

            //ExeStack
            List<IStmt> stmts = ctrl.getRepo().get(ID).getStk().getReversed();
            List<String> exe = new ArrayList<>();
            int size = stmts.size();
            for(int i = 0; i<size; i++){
                if(stmts.get(i) instanceof Comp stmt){
                    stmts.remove(i);
                    stmts.add(i, stmt.getFirst());
                    stmts.add(i+1, stmt.getSecond());
                    size++;
                }
                exe.add(stmts.get(i).toString());
            }

            ExeStackView.setItems(FXCollections.observableList(exe));
            ExeStackView.refresh();

            //SymTable
            List<Map.Entry<String, Value>> symTableList = new ArrayList<>(ctrl.getRepo().get(ID).getSymTable().getMap().entrySet());
            SymTableView.setItems(FXCollections.observableList(symTableList));
            SymTableView.refresh();

            //HeapTable
            List<Map.Entry<Integer, Value>> heapTableList = new ArrayList<>(ctrl.getRepo().get(ID).getHeapTable().getMap().entrySet());
            HeapTableView.setItems(FXCollections.observableList(heapTableList));
            HeapTableView.refresh();

            //FileTable
            FileTableView.setItems(FXCollections.observableList(ctrl.getRepo().get(0).getFileTable().getMap().keySet().stream().map(StringValue::toString).collect(Collectors.toList())));
            FileTableView.refresh();

            //Out
            OutView.setItems(FXCollections.observableList(ctrl.getRepo().get(0).getOut().getList().stream().map(Value::toString).collect(Collectors.toList())));
            OutView.refresh();

            //LockTable
            List<Map.Entry<Integer, Integer>> lockTableList = new ArrayList<>(ctrl.getRepo().get(ID).getLockTable().getMap().entrySet());
            LockTableView.setItems(FXCollections.observableList(lockTableList));
            LockTableView.refresh();
        }
        else {
            IDView.setItems(FXCollections.observableList(new ArrayList<>()));
            IDView.refresh();
            NoPrgStates.setText("0");

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "PrgState executed successfully!", ButtonType.OK);
            alert.showAndWait();

            Stage currentStage = (Stage) IDView.getScene().getWindow();
            currentStage.close();
        }
    }
}
