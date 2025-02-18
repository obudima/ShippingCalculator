public class Delivery {
    public static final double MINIMUM_DELIVERY_AMOUNT = 400;

    private final double destinationDistance;
    private final CargoDimension cargoDimensions;
    private final boolean isFragile;
    private final ServiceWorkload deliveryServiceWorkload;

    public Delivery(double destinationDistance, CargoDimension cargoDimensions, boolean isFragile, ServiceWorkload deliveryServiceWorkload) {
        this.destinationDistance = destinationDistance;
        this.cargoDimensions = cargoDimensions;
        this.isFragile = isFragile;
        this.deliveryServiceWorkload = deliveryServiceWorkload;
    }

    public double calculateShippingCost (){
        if (this.isFragile&&this.destinationDistance>30) throw new UnsupportedOperationException("Fragile cargo cannot be delivered for the distance more than 30!");
        double calculateShippingCost = this.deliveryServiceWorkload.getCostMultiplier() * (distanceCost(this.destinationDistance) + this.cargoDimensions.getCostIncrease() + this.fragileCost(this.isFragile));
        return Math.max(calculateShippingCost, Delivery.MINIMUM_DELIVERY_AMOUNT) ;
    }

    private int distanceCost (double destinationDistance){
        if (destinationDistance>0&&destinationDistance<=2) return 50;
        if (destinationDistance>2&&destinationDistance<=10) return 100;
        if (destinationDistance>10&&destinationDistance<=30) return 200;
        if (destinationDistance>30&&destinationDistance<=1000) return 300;
        if (destinationDistance>1000) throw new IllegalArgumentException("It is not possible to place an order for a distance of more than 1000 km!");
        throw new IllegalArgumentException("destinationDistance should be a positive number!");
    }

    private int fragileCost(boolean isFragile){
        if (isFragile) return 300;
                else return 0;
    }


}
