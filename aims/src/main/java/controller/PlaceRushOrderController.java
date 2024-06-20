package controller;

import model.Shipment;
import utils.Configs;

public class PlaceRushOrderController extends BaseController {
    public static void validatePlaceRushOrderData(Shipment deliveryData) {
        if (deliveryData.getShipType() == Configs.PLACE_RUSH_ORDER) {

        }
    }
}
