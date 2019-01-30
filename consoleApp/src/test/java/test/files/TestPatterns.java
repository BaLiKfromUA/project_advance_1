package test.files;

public enum TestPatterns {
    SMALL("small",500),
    MEDIUM("medium",5000),
    BIG("big",50000),
    LARGE("large",500000);

    final String sizeName;
    final int size;

    TestPatterns(String sizeName, int size) {
        this.sizeName = sizeName;
        this.size = size;
    }

    public String getSizeName() {
        return sizeName;
    }

    public int getSize() {
        return size;
    }
}
