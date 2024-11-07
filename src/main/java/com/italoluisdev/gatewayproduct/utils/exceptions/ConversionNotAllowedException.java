package com.italoluisdev.gatewayproduct.utils.exceptions;

public class ConversionNotAllowedException extends Exception{

    public ConversionNotAllowedException() {
        super("The Country-Currency specified does not have available data." +
                "\nPossible causes: Wrong country or currency or transaction date may not have currency exchange data available within a 6 month period.");
    }
}
