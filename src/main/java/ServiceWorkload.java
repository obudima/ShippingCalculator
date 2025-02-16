public enum ServiceWorkload {
    veryHigh(1.6),
    high (1.4),
    increase(1.2),
    normal(1);

    private double costMultiplier;

    ServiceWorkload (double costMultiplier){
        this.costMultiplier = costMultiplier;
    }

    public double getCostMultiplier() {
        return costMultiplier;
    }
}
