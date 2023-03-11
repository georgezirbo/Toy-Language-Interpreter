package gui;

import controller.Controller;
import exception.MyException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.adt.MyDictionary;
import model.adt.PrgState;
import model.exp.ValueExp;
import model.stmt.*;
import model.exp.*;
import model.type.*;
import model.value.*;
import repository.Repository;

import java.io.DataInput;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.IntToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StartController{
    private Controller ctrl;

    private List<IStmt> Programs;
    @FXML
    private ListView<String> PrgStatesView;

    @FXML
    private Button RunButton;

    @FXML
    void RunButtonOnAction(ActionEvent event) {
        int index = PrgStatesView.getSelectionModel().getSelectedIndex();
        if(index < 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No PrgState selected!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        else{
            try{
                Programs.get(index).typecheck(new MyDictionary<>());
            } catch(MyException e) {
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.WARNING, "The selected PrgState is not valid.", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            PrgState currentPrgState = new PrgState(Programs.get(index));
            Repository repo = new Repository(currentPrgState, "log" + (index + 1) + ".txt");
            ctrl = new Controller(repo);

            try{
                FXMLLoader RunLoader = new FXMLLoader(getClass().getResource("Run.fxml"));
                RunController RunCtrl = new RunController(ctrl);
                RunLoader.setController(RunCtrl);

                Parent RunWindow = RunLoader.load();
                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("Run");
                secondaryStage.setScene(new Scene(RunWindow, 800, 722));
                secondaryStage.getIcons().add(new Image("gui/logo.png"));
                secondaryStage.show();

            } catch (IOException e){
                System.out.println(e.getMessage());}
        }
    }

    void build_programs(){
        IStmt ex1 = new Comp(new VarDecl("v",new IntType()),
                   new Comp(new Assign("v",new ValueExp(new StringValue("string"))),
                   new Print(new VarExp("v"))));

        IStmt ex2 = new Comp( new VarDecl("a",new IntType()),
                    new Comp(new VarDecl("b",new IntType()), new Comp(new Assign("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                    new Comp(new Assign("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))),
                    new Print(new VarExp("b"))))));

        IStmt ex3 = new Comp(new VarDecl("a",new BoolType()),
                    new Comp(new VarDecl("v", new IntType()),
                    new Comp(new Assign("a", new ValueExp(new BoolValue(true))),
                    new Comp(new If(new VarExp("a"),new Assign("v",new ValueExp(new IntValue(2))), new Assign("v", new ValueExp(new IntValue(3)))),
                    new Print(new VarExp("v"))))));

        IStmt ex4 = new Comp(new VarDecl("varf", new StringType()),
                    new Comp(new Assign("varf", new ValueExp(new StringValue("test.in"))),
                    new Comp(new openFile(new VarExp("varf")),
                    new Comp(new VarDecl("varc", new IntType()),
                    new Comp(new readFile(new VarExp("varf"), "varc"),
                    new Comp(new Print(new VarExp("varc")),
                    new closeFile(new VarExp("varf"))))))));

        IStmt ex5 = new Comp(new VarDecl("v", new RefType(new IntType())),
                    new Comp(new New("v", new ValueExp(new IntValue(20))),
                    new Comp(new VarDecl("a", new RefType(new RefType(new IntType()))),
                    new Comp(new New("a", new VarExp("v")),
                    new Comp(new New("v", new ValueExp(new IntValue(30))),
                    new Print(new rH(new rH(new VarExp("a")))))))));

        IStmt ex6 = new Comp(new VarDecl("v", new IntType()),
                    new Comp(new Assign("v", new ValueExp(new IntValue(4))),
                    new Comp(new While(new RelExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                    new Comp(new Print(new VarExp("v")), new Assign("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                    new Print(new VarExp("v")))));

        IStmt ex7 = new Comp(new VarDecl("v", new IntType()),
                    new Comp(new VarDecl("a", new RefType(new IntType())),
                    new Comp(new Assign("v", new ValueExp(new IntValue(10))),
                    new Comp(new New("a", new ValueExp(new IntValue(22))),
                    new Comp(new Fork(new Comp(new wH("a", new ValueExp(new IntValue(30))),
                                      new Comp(new Assign("v", new ValueExp(new IntValue(32))),
                                      new Print(new VarExp("v"))))),
                    new Comp(new Print(new VarExp("v")),
                    new Comp(new Print(new rH(new VarExp("a"))),
                    new Comp(new wH("a", new ValueExp(new IntValue(15))),
                    new Print(new VarExp("a"))))))))));

        IStmt ex8 = new Comp(new VarDecl( "a", new IntType()),
                    new Comp(new Fork(new Comp(new VarDecl( "b", new IntType()),
                                      new VarDecl( "c", new IntType()))),
                    new VarDecl("d", new IntType())));

        IStmt ex9 = new Comp(new VarDecl("v1", new RefType(new IntType())),
                    new Comp(new VarDecl("v2", new RefType(new IntType())),
                    new Comp(new VarDecl("x", new IntType()),
                    new Comp(new VarDecl("q", new IntType()),
                    new Comp(new New("v1", new ValueExp(new IntValue(20))),
                    new Comp(new New("v2", new ValueExp(new IntValue(30))),
                    new Comp(new newLock("x"),
                    new Comp(new Fork(new Comp(new Fork(new Comp(new lock("x"),
                                                        new Comp(new wH("v1", new ArithExp('-', new rH(new VarExp("v1")), new ValueExp(new IntValue(1)))),
                                                        new unlock("x")))),
                                      new Comp(new lock("x"),
                                       new Comp(new wH("v1", new ArithExp('*', new rH(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                       new unlock("x"))))),
                    new Comp(new newLock("q"),
                    new Comp(new Fork(new Comp(new Fork(new Comp(new lock("q"),
                                                        new Comp(new wH("v2", new ArithExp('+', new rH(new VarExp("v2")), new ValueExp(new IntValue(5)))),
                                                        new unlock("q")))),
                                      new Comp(new lock("q"),
                                      new Comp(new wH("v2", new ArithExp('*', new rH(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                      new unlock("q"))))),
                    new Comp(new Nop(),
                    new Comp(new Nop(),
                    new Comp(new Nop(),
                    new Comp(new Nop(),
                    new Comp(new lock("x"),
                    new Comp(new Print(new rH(new VarExp("v1"))),
                    new Comp(new unlock("x"),
                    new Comp(new lock("q"),
                    new Comp(new Print(new rH(new VarExp("v2"))),
                    new unlock("q"))))))))))))))))))));



        Programs = new ArrayList<>(Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9));
    }

    public void initialize() {
        build_programs();
        List<String> list = IntStream.range(0, Programs.size()).mapToObj(index -> String.format("%d. %s", index + 1, Programs.get(index))).toList();
        PrgStatesView.setItems(FXCollections.observableArrayList(list));
    }
}
