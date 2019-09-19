package com.db.funnel_meterchartview;

public class FunnelChartData {

    private String colorCode;
    private String strLabel;

    public FunnelChartData(String colorCode, String strLabel) {
        this.colorCode = colorCode;
        this.strLabel = strLabel;
    }

    String getColorCode() {
        return colorCode;
    }

    String getStrLabel() {
        return strLabel;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public void setStrLabel(String strLabel) {
        this.strLabel = strLabel;
    }
}
