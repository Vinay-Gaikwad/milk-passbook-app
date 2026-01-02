package com.shiv.milkpassbook.api.validate_session;

public class SessionRequest {
    private String SoftCustCode;
    private String DeviceId;
    private String Mob;

    public SessionRequest(String softCustCode, String deviceId, String mob) {
        this.SoftCustCode = softCustCode;
        this.DeviceId = deviceId;
        this.Mob = mob;
    }
}
