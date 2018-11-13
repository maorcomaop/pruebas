package com.registel.rdw.logica;


import java.util.Date;

/**
 *
 * @author lider_desarrollador
 */
public class DataGPS {

    private int id;
    private String systemCode;
    private int messageType;
    private String unitID;
    private String unitHexID;
    private int messageNumerator;
    private String messageNumeratorHex;
    private int transReasson;
    private String transReassonText;
    private String transReasonSpecificData;

    private double longitude;
    private double latitude;
    private double altitude;
    private double mileageCounter;
    private double groundSpeed;
    private double speedDirection;
    private Date gpsDate;
    private int numberSatellites;

    private String commControlField;
    private String unitHardwareVersion;
    private String unitSoftwareVersion;
    private String protocolVersionId;
    private String unitStatus;
    private String gsmOperator_1;
    private String gsmOperator_2_3;
    private String gsmOperator_4_5;
    private String unitModeOperation;
    private String unitIoStatus_1;
    private String unitIoStatus_2;
    private String unitIoStatus_3;
    private String unitIoStatus_4;
    private String analogInput_1;
    private String analogInput_2;
    private String analogInput_3;
    private String analogInput_4;
    private String multiPurposeField;
    private String lastGPSField;
    private String locationStatus;
    private String mode_1;
    private String mode_2;
    private String errorCode;    
    private Date insertDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public String getAnalogInput_2() {
        return analogInput_2;
    }

    public void setAnalogInput_2(String analogInput_2) {
        this.analogInput_2 = analogInput_2;
    }

    public String getAnalogInput_3() {
        return analogInput_3;
    }

    public void setAnalogInput_3(String analogInput_3) {
        this.analogInput_3 = analogInput_3;
    }

    public String getAnalogInput_4() {
        return analogInput_4;
    }

    public void setAnalogInput_4(String analogInput_4) {
        this.analogInput_4 = analogInput_4;
    }

    public String getMultiPurposeField() {
        return multiPurposeField;
    }

    public void setMultiPurposeField(String multiPurposeField) {
        this.multiPurposeField = multiPurposeField;
    }

    public String getLastGPSField() {
        return lastGPSField;
    }

    public void setLastGPSField(String lastGPSField) {
        this.lastGPSField = lastGPSField;
    }

    public String getLocationStatus() {
        return locationStatus;
    }

    public void setLocationStatus(String locationStatus) {
        this.locationStatus = locationStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public String getUnitHexID() {
        return unitHexID;
    }

    public void setUnitHexID(String unitHexID) {
        this.unitHexID = unitHexID;
    }

    public int getMessageNumerator() {
        return messageNumerator;
    }

    public void setMessageNumerator(int messageNumerator) {
        this.messageNumerator = messageNumerator;
    }

    public String getMessageNumeratorHex() {
        return messageNumeratorHex;
    }

    public void setMessageNumeratorHex(String messageNumeratorHex) {
        this.messageNumeratorHex = messageNumeratorHex;
    }

    public int getTransReasson() {
        return transReasson;
    }

    public void setTransReasson(int transReasson) {
        this.transReasson = transReasson;
    }

    public String getTransReassonText() {
        return transReassonText;
    }

    public void setTransReassonText(String transReassonText) {
        this.transReassonText = transReassonText;
    }

    public String getTransReasonSpecificData() {
        return transReasonSpecificData;
    }

    public void setTransReasonSpecificData(String transReasonSpecificData) {
        this.transReasonSpecificData = transReasonSpecificData;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getMileageCounter() {
        return mileageCounter;
    }

    public void setMileageCounter(double mileageCounter) {
        this.mileageCounter = mileageCounter;
    }

    public double getGroundSpeed() {
        return groundSpeed;
    }

    public void setGroundSpeed(double groundSpeed) {
        this.groundSpeed = groundSpeed;
    }

    public double getSpeedDirection() {
        return speedDirection;
    }

    public void setSpeedDirection(double speedDirection) {
        this.speedDirection = speedDirection;
    }

    public Date getGpsDate() {
        return gpsDate;
    }

    public void setGpsDate(Date gpsDate) {
        this.gpsDate = gpsDate;
    }

    public int getNumberSatellites() {
        return numberSatellites;
    }

    public void setNumberSatellites(int numberSatellites) {
        this.numberSatellites = numberSatellites;
    }

    public String getCommControlField() {
        return commControlField;
    }

    public void setCommControlField(String commControlField) {
        this.commControlField = commControlField;
    }

    public String getUnitHardwareVersion() {
        return unitHardwareVersion;
    }

    public void setUnitHardwareVersion(String unitHardwareVersion) {
        this.unitHardwareVersion = unitHardwareVersion;
    }

    public String getUnitSoftwareVersion() {
        return unitSoftwareVersion;
    }

    public void setUnitSoftwareVersion(String unitSoftwareVersion) {
        this.unitSoftwareVersion = unitSoftwareVersion;
    }

    public String getProtocolVersionId() {
        return protocolVersionId;
    }

    public void setProtocolVersionId(String protocolVersionId) {
        this.protocolVersionId = protocolVersionId;
    }

    public String getUnitStatus() {
        return unitStatus;
    }

    public void setUnitStatus(String unitStatus) {
        this.unitStatus = unitStatus;
    }

    public String getGsmOperator_1() {
        return gsmOperator_1;
    }

    public void setGsmOperator_1(String gsmOperator_1) {
        this.gsmOperator_1 = gsmOperator_1;
    }

    public String getGsmOperator_2_3() {
        return gsmOperator_2_3;
    }

    public void setGsmOperator_2_3(String gsmOperator_2_3) {
        this.gsmOperator_2_3 = gsmOperator_2_3;
    }

    public String getGsmOperator_4_5() {
        return gsmOperator_4_5;
    }

    public void setGsmOperator_4_5(String gsmOperator_4_5) {
        this.gsmOperator_4_5 = gsmOperator_4_5;
    }

    public String getUnitModeOperation() {
        return unitModeOperation;
    }

    public void setUnitModeOperation(String unitModeOperation) {
        this.unitModeOperation = unitModeOperation;
    }

    public String getUnitIoStatus_1() {
        return unitIoStatus_1;
    }

    public void setUnitIoStatus_1(String unitIoStatus_1) {
        this.unitIoStatus_1 = unitIoStatus_1;
    }

    public String getUnitIoStatus_2() {
        return unitIoStatus_2;
    }

    public void setUnitIoStatus_2(String unitIoStatus_2) {
        this.unitIoStatus_2 = unitIoStatus_2;
    }

    public String getUnitIoStatus_3() {
        return unitIoStatus_3;
    }

    public void setUnitIoStatus_3(String unitIoStatus_3) {
        this.unitIoStatus_3 = unitIoStatus_3;
    }

    public String getUnitIoStatus_4() {
        return unitIoStatus_4;
    }

    public void setUnitIoStatus_4(String unitIoStatus_4) {
        this.unitIoStatus_4 = unitIoStatus_4;
    }

    public String getAnalogInput_1() {
        return analogInput_1;
    }

    public void setAnalogInput_1(String analogInput_1) {
        this.analogInput_1 = analogInput_1;
    }

    public String getMode_1() {
        return mode_1;
    }

    public void setMode_1(String mode_1) {
        this.mode_1 = mode_1;
    }

    public String getMode_2() {
        return mode_2;
    }

    public void setMode_2(String mode_2) {
        this.mode_2 = mode_2;
    }

}
