package advanced.balik.application.view;

public enum Hints {
    INSERT("Add an item to the heap or find it if it's already there."),
    MIN("Find minimum element and remove it from the heap."),
    RANDOM("Add random item to the heap."),
    CLEAR("Make heap empty"),
    STEP_BACK("Return current state to previous"),
    AUTO("Start auto mode running"),
    CONSOLE("Hide/show console"),
    SIDEBAR("Hide/show sidebar");

    private final String hint;

    Hints(String hint) {
        this.hint = hint;
    }

    public String getHint() {
        return hint;
    }
}
