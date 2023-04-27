package util;

import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static Object Validation(LinkedHashMap<TextField,Pattern> map) {
        for (TextField key:map.keySet()) {
            Pattern pattern = map.get(key);
            if (!pattern.matcher(key.getText()).matches()){
                addError(key);
                return key;
            }else {
                removeError(key);
            }
        }
        return true;
    }

    public static void removeError(TextField key) {
        key.setStyle("-fx-border-color: green; -fx-border-radius: 10; -fx-background-radius: 10");
    }

    public static void addError(TextField key) {
        if (key.getText().length()>0){
            key.setStyle("-fx-border-color: red; -fx-border-radius: 10; -fx-background-radius: 10");
        }
    }
}
