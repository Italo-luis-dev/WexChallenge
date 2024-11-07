package com.italoluisdev.gatewayproduct.utils.exceptions;

public class ConversionNotAllowedException extends Exception{

    public ConversionNotAllowedException() {
        super("The Country-Currency specified does not have available data" +
                " within the date selected. \n " +
                "This may be because the currency existed under a different name for that time period," +
                " or the selected transaction date may not have data available within the date selected." +
                " Please check to see if the currency you are looking for appears under a different name," +
                " or change the selected date for available results.");
    }
}
