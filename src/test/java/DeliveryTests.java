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
    void deliveryPairTests(int destinationDistance, CargoDimension cargoDimension, boolean isFragile, ServiceWorkload serviceWorkload, double expectedDeliveryCost) {
        Delivery delivery = new Delivery(destinationDistance, cargoDimension, isFragile, serviceWorkload);
        assertEquals(expectedDeliveryCost, delivery.calculateShippingCost());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Fragile & Distance 30+")
    void deliveryFragileWithLargeDistanceTest() {
        // Здесь размещаем код теста
        CargoDimension cargoDimensions = CargoDimension.large;
        ServiceWorkload serviceWorkload = ServiceWorkload.veryHigh;
        int destinationDistance = 31;
        boolean isFragile = true;
        Delivery delivery = new Delivery(destinationDistance, cargoDimensions, isFragile, serviceWorkload);

        Exception thrown = assertThrows(UnsupportedOperationException.class, () -> delivery.calculateShippingCost());
        Assertions.assertEquals("Fragile cargo cannot be delivered for the distance more than 30!", thrown.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Negative Distance")
    void deliveryNegativeDistanceTest() {
        // Здесь размещаем код теста
        CargoDimension cargoDimensions = CargoDimension.large;
        ServiceWorkload serviceWorkload = ServiceWorkload.veryHigh;
        int destinationDistance = -1;
        boolean isFragile = true;
        Delivery delivery = new Delivery(destinationDistance, cargoDimensions, isFragile, serviceWorkload);

        Exception thrown = assertThrows(IllegalArgumentException.class, () -> delivery.calculateShippingCost());
        Assertions.assertEquals("destinationDistance should be a positive number!", thrown.getMessage());
    }




/*    @ParameterizedTest(name = "Cost for Distance {0}, Dimension small, Fragile true, Workload normal")
    @ValueSource(ints = {1, 10, 30})
    @Tag("Positive")
    void deliveryPairOneTest(int parameter) {
        CargoDimension cargoDimensions = CargoDimension.small;
        ServiceWorkload serviceWorkload = ServiceWorkload.normal;
        int destinationDistance = parameter;
        boolean isFragile = true;
        Delivery delivery = new Delivery(destinationDistance, cargoDimensions, isFragile, serviceWorkload);

        double expectedDeliveryCost;
        switch (destinationDistance){
            case 1: expectedDeliveryCost = 450;
                break;
            case 10: expectedDeliveryCost = 500;
                break;
            default: expectedDeliveryCost = 600;
        }

        double actualDeliveryCost = delivery.calculateShippingCost();

        assertEquals(expectedDeliveryCost, actualDeliveryCost);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Cost for Distance 1, Dimension large, Fragile false, Workload increase")
    void deliveryPairTwoTest() {
        CargoDimension cargoDimensions = CargoDimension.large;
        ServiceWorkload serviceWorkload = ServiceWorkload.increase;
        int destinationDistance= 1;
        boolean isFragile = false;
        Delivery delivery = new Delivery(destinationDistance, cargoDimensions, isFragile, serviceWorkload);

        double expectedDeliveryCost = 400;
        double actualDeliveryCost = delivery.calculateShippingCost();

        assertEquals(expectedDeliveryCost, actualDeliveryCost);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Cost for Distance 1, Dimension small, Fragile true, Workload high")
    void deliveryPairThreeTest() {
        CargoDimension cargoDimensions = CargoDimension.small;
        ServiceWorkload serviceWorkload = ServiceWorkload.high;
        int destinationDistance= 1;
        boolean isFragile = true;
        Delivery delivery = new Delivery(destinationDistance, cargoDimensions, isFragile, serviceWorkload);

        double expectedDeliveryCost = 630;
        double actualDeliveryCost = delivery.calculateShippingCost();

        assertEquals(expectedDeliveryCost, actualDeliveryCost);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Cost for Distance 1, Dimension large, Fragile false, Workload veryHigh")
    void deliveryPairFourTest() {
        CargoDimension cargoDimensions = CargoDimension.large;
        ServiceWorkload serviceWorkload = ServiceWorkload.veryHigh;
        int destinationDistance= 1;
        boolean isFragile = false;
        Delivery delivery = new Delivery(destinationDistance, cargoDimensions, isFragile, serviceWorkload);

        double expectedDeliveryCost = 400;
        double actualDeliveryCost = delivery.calculateShippingCost();

        assertEquals(expectedDeliveryCost, actualDeliveryCost);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Cost for Distance 10, Dimension small, Fragile false, Workload veryHigh")
    void deliveryPairFiveTest() {
        CargoDimension cargoDimensions = CargoDimension.small;
        ServiceWorkload serviceWorkload = ServiceWorkload.veryHigh;
        int destinationDistance= 10;
        boolean isFragile = false;
        Delivery delivery = new Delivery(destinationDistance, cargoDimensions, isFragile, serviceWorkload);

        double expectedDeliveryCost = 400;
        double actualDeliveryCost = delivery.calculateShippingCost();

        assertEquals(expectedDeliveryCost, actualDeliveryCost);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Cost for Distance 10, Dimension large, Fragile true, Workload normal")
    void deliveryPairSixTest() {
        CargoDimension cargoDimensions = CargoDimension.large;
        ServiceWorkload serviceWorkload = ServiceWorkload.normal;
        int destinationDistance= 10;
        boolean isFragile = true;
        Delivery delivery = new Delivery(destinationDistance, cargoDimensions, isFragile, serviceWorkload);

        double expectedDeliveryCost = 600;
        double actualDeliveryCost = delivery.calculateShippingCost();

        assertEquals(expectedDeliveryCost, actualDeliveryCost);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Cost for Distance 10, Dimension small, Fragile false, Workload increase")
    void deliveryPairSevenTest() {
        CargoDimension cargoDimensions = CargoDimension.small;
        ServiceWorkload serviceWorkload = ServiceWorkload.increase;
        int destinationDistance= 10;
        boolean isFragile = false;
        Delivery delivery = new Delivery(destinationDistance, cargoDimensions, isFragile, serviceWorkload);

        double expectedDeliveryCost = 400;
        double actualDeliveryCost = delivery.calculateShippingCost();

        assertEquals(expectedDeliveryCost, actualDeliveryCost);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Cost for Distance 10, Dimension large, Fragile false, Workload high")
    void deliveryPairEightTest() {
        CargoDimension cargoDimensions = CargoDimension.large;
        ServiceWorkload serviceWorkload = ServiceWorkload.high;
        int destinationDistance= 10;
        boolean isFragile = false;
        Delivery delivery = new Delivery(destinationDistance, cargoDimensions, isFragile, serviceWorkload);

        double expectedDeliveryCost = 420;
        double actualDeliveryCost = delivery.calculateShippingCost();

        assertEquals(expectedDeliveryCost, actualDeliveryCost);
    }


    @Test
    @Tag("Positive")
    @DisplayName("Cost for Distance 30, Dimension small, Fragile true, Workload high")
    void deliveryPairNineTest() {
        CargoDimension cargoDimensions = CargoDimension.small;
        ServiceWorkload serviceWorkload = ServiceWorkload.high;
        int destinationDistance= 30;
        boolean isFragile = true;
        Delivery delivery = new Delivery(destinationDistance, cargoDimensions, isFragile, serviceWorkload);

        double expectedDeliveryCost = 840;
        double actualDeliveryCost = delivery.calculateShippingCost();

        assertEquals(expectedDeliveryCost, actualDeliveryCost);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Cost for Distance 30, Dimension large, Fragile false, Workload veryHigh")
    void deliveryPairTenTest() {
        CargoDimension cargoDimensions = CargoDimension.large;
        ServiceWorkload serviceWorkload = ServiceWorkload.veryHigh;
        int destinationDistance= 30;
        boolean isFragile = false;
        Delivery delivery = new Delivery(destinationDistance, cargoDimensions, isFragile, serviceWorkload);

        double expectedDeliveryCost = 640;
        double actualDeliveryCost = delivery.calculateShippingCost();

        assertEquals(expectedDeliveryCost, actualDeliveryCost);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Cost for Distance 30, Dimension large, Fragile false, Workload increase")
    void deliveryPairTwelveTest() {
        CargoDimension cargoDimensions = CargoDimension.large;
        ServiceWorkload serviceWorkload = ServiceWorkload.increase;
        int destinationDistance= 30;
        boolean isFragile = false;
        Delivery delivery = new Delivery(destinationDistance, cargoDimensions, isFragile, serviceWorkload);

        double expectedDeliveryCost = 480;
        double actualDeliveryCost = delivery.calculateShippingCost();

        assertEquals(expectedDeliveryCost, actualDeliveryCost);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Cost for Distance 30, Dimension small, Fragile false, Workload increase")
    void deliveryPairThirteenTest() {
        CargoDimension cargoDimensions = CargoDimension.small;
        ServiceWorkload serviceWorkload = ServiceWorkload.increase;
        int destinationDistance= 31;
        boolean isFragile = false;
        Delivery delivery = new Delivery(destinationDistance, cargoDimensions, isFragile, serviceWorkload);

        double expectedDeliveryCost = 480;
        double actualDeliveryCost = delivery.calculateShippingCost();

        assertEquals(expectedDeliveryCost, actualDeliveryCost);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Cost for Distance 30, Dimension small, Fragile false, Workload veryHigh")
    void deliveryPairFourteenTest() {
        CargoDimension cargoDimensions = CargoDimension.small;
        ServiceWorkload serviceWorkload = ServiceWorkload.veryHigh;
        int destinationDistance= 31;
        boolean isFragile = false;
        Delivery delivery = new Delivery(destinationDistance, cargoDimensions, isFragile, serviceWorkload);

        double expectedDeliveryCost = 640;
        double actualDeliveryCost = delivery.calculateShippingCost();

        assertEquals(expectedDeliveryCost, actualDeliveryCost);
    }
*/
}