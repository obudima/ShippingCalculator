import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeliveryTests {

    @ParameterizedTest(name = "Cost for Distance {0}, Dimension {1}, Fragile {2}, Workload {3}")
    @CsvSource({
            "1, small, true, normal, 450",
            "1, small, false, increase, 400",
            "1,	small,	true,	high, 630",
            "1,	large,	false,	veryHigh, 400",
            "10,	small,	false,	veryHigh, 400",
            "10,	small,	true,	normal, 500",
            "10,	small,	false,	increase, 400",
            "10,	large,	true,	high, 840",
            "30,	small,	true,	high, 840",
            "30,	large,	false,	veryHigh, 640",
            "30,	small,	true,	normal, 600",
            "30,	large,	false,	increase, 480",
            "31,	small,	false,	increase, 480",
            "31,	small,	false,	veryHigh, 640"
    })
    @Tag("Positive")
    void deliveryPairTests(double destinationDistance, CargoDimension cargoDimension, boolean isFragile, ServiceWorkload serviceWorkload, double expectedDeliveryCost) {
        Delivery delivery = new Delivery(destinationDistance, cargoDimension, isFragile, serviceWorkload);
        assertEquals(expectedDeliveryCost, delivery.calculateShippingCost());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Fragile & Distance 29.999999")
    void BoundaryValueFirstTest() {
        Delivery delivery = new Delivery(29.999999, CargoDimension.large, true, ServiceWorkload.increase);
        assertEquals(840, delivery.calculateShippingCost());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Fragile & Distance 30")
    void BoundaryValueSecondTest() {
        Delivery delivery = new Delivery(30, CargoDimension.large, true, ServiceWorkload.increase);
        assertEquals(840, delivery.calculateShippingCost());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Fragile & Distance 30.000001")
    void deliveryFragileWithLargeDistanceTest() {
        Delivery delivery = new Delivery(30.000001, CargoDimension.large, true, ServiceWorkload.increase);

        Exception thrown = assertThrows(UnsupportedOperationException.class, () -> delivery.calculateShippingCost());
        Assertions.assertEquals("Fragile cargo cannot be delivered for the distance more than 30!", thrown.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Negative Distance")
    void deliveryNegativeDistanceTest() {
        Delivery delivery = new Delivery(-1, CargoDimension.large, true, ServiceWorkload.veryHigh);

        Exception thrown = assertThrows(IllegalArgumentException.class, () -> delivery.calculateShippingCost());
        Assertions.assertEquals("destinationDistance should be a positive number!", thrown.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Max Double for distance")
    void maxDoubleForDistanceTest() {
        Delivery delivery = new Delivery(Double.MAX_VALUE, CargoDimension.large, false, ServiceWorkload.veryHigh);
        Exception thrown = assertThrows(IllegalArgumentException.class, () -> delivery.calculateShippingCost());
        Assertions.assertEquals("It is not possible to place an order for a distance of more than 1000 km!", thrown.getMessage());
    }

}