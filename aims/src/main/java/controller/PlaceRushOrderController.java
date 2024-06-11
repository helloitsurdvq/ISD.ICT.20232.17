package controller;

import model.Shipment;
import utils.Configs;

import java.util.logging.Logger;


public class PlaceRushOrderController extends BaseController {
    private static Logger LOGGER = utils.Format.getLogger(PlaceRushOrderController.class.getName());

    public static void validatePlaceRushOrderData(Shipment deliveryData) {
        if (deliveryData.getShipType() == Configs.PLACE_RUSH_ORDER) {
            // validate
        }
    }
}
