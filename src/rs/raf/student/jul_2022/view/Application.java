package rs.raf.student.jul_2022.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import rs.raf.student.jul_2022.view.form.Form;

public class Application extends javafx.application.Application {

    private static Stage window;

    public static void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        setForm(Form.main());

        window.show();
    }

    public static void setForm(Scene form) {
        window.setScene(form);
    }

}
