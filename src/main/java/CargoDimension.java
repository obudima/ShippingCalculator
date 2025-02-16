public enum CargoDimension {
    large (200),
    small (100);

    private int costIncrease;

    CargoDimension(int costIncrease) {
        this.costIncrease = costIncrease;
    }

    public int getCostIncrease() {
        return costIncrease;
    }
}
