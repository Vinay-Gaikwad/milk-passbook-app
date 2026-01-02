package com.shiv.milkpassbook.api.login_party;

public class LoginRequest {
    private String SoftCustCode;
    private String Mob;
    private String DeviceId;

    public LoginRequest(String softCustCode, String mob, String deviceId) {
        this.SoftCustCode = softCustCode;
        this.Mob = mob;
        this.DeviceId = deviceId;
    }
}
