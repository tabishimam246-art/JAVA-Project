//UPI Class
class UPI {
    String upiId;
    String pin;
    int wrongAttempts = 0;

    /**
     *
     * @param upiId
     * @param pin
     */
    UPI(String upiId, String pin) {
        this.upiId = upiId;
        this.pin = pin;
    }
}

