package in.tanjo.calorie.model.provisional;

public enum Unit {
    G("g"),
    MG("mg"),
    L("l"),
    ML("ml"),
    KCAL("kcal");

    String displayName;

    Unit(String displayName) {
        this.displayName = displayName;
    }
}
