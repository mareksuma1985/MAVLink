package pl.bezzalogowe.mavlink;

public class MAVLink {
    private final static String TAG = MAVLink.class.getName();

    /* Used to load the native library on application startup. */
    static {
        System.loadLibrary("mavlink_udp");
    }

    MainActivity main;

    public MAVLink(final MainActivity argActivity) {
        main = argActivity;
    }

    public static native void classInit();

    public native int setIDs(byte system, byte component);

    public native int receiveInit();

    public native int receiveStop();

    public native int heartBeatInit();

    public native int heartBeatStop();

    /* Displays a string from C code */
    public native String stringFromJNI();

    public native int sendProtocol();

    public native int sendHello();

    public native void setHeadingDegrees(double hdg);

    public native void sendAttitude(float roll, float pitch/*, float heading*/);

    public native void setBattery(int voltage, int level);

    public native void sendGlobalPosition(double lat, double lon, double alt);

    /* Called from native code. This sets the content of the TextView from the UI thread. */
    private void setMessage(final String message, boolean blink) {
        main.update.updateConversationHandler.post(new UpdateTextThread(main.textFeedback, message, blink));
    }

    private void setButtons(final String message, boolean blink) {
        main.update.updateConversationHandler.post(new UpdateTextThread(main.textButtons, message, blink));
    }

    private void setAddress(final String message, boolean blink) {
        main.update.updateConversationHandler.post(new UpdateTextThread(main.address_gcs, message, blink));
    }

    private void setLog(final String message) {
        //Log.d(TAG, message);
        System.out.println(message);
    }

    private void setProgress(short x, short y, short z, short r) {
        /* pitch, roll, thrust, yaw */
        main.update.updateConversationHandler.post(new UpdateProgressThread(main.seekbar1, (x + 1000) / 20));
        main.update.updateConversationHandler.post(new UpdateTextThread(main.dutyCycleTextX, Integer.toString(x), false));

        main.update.updateConversationHandler.post(new UpdateProgressThread(main.seekbar2, (y + 1000) / 20));
        main.update.updateConversationHandler.post(new UpdateTextThread(main.dutyCycleTextY, Integer.toString(y), false));

        main.update.updateConversationHandler.post(new UpdateProgressThread(main.seekbar3, z / 10));
        main.update.updateConversationHandler.post(new UpdateTextThread(main.dutyCycleTextZ, Integer.toString(z), false));

        main.update.updateConversationHandler.post(new UpdateProgressThread(main.seekbar4, (r + 1000) / 20));
        main.update.updateConversationHandler.post(new UpdateTextThread(main.dutyCycleTextR, Integer.toString(r), false));
    }

    private void processButton(short number, boolean status) {

        switch (number) {
            case 0: {
                System.out.println(String.format("button", "button: A, status: " + status));
                main.update.updateConversationHandler.post(new UpdateTextThread(main.textButtons, "button " + number + " " + (status ? "pressed" : "released"), false));
            }
            break;
            case 1: {
                System.out.println(String.format("button", "button: B, status: " + status));
                main.update.updateConversationHandler.post(new UpdateTextThread(main.textButtons, "button " + number + " " + (status ? "pressed" : "released"), false));
            }
            break;
            //TODO: process more buttons here
            /* keep in mind that controllers differ in button layouts */
            default:
                System.out.println(String.format("button", "button: " + number + ", status: " + status));
                main.update.updateConversationHandler.post(new UpdateTextThread(main.textButtons, "button " + number + " " + (status ? "pressed" : "released"), false));
                break;
        }
    }

    private void setSound(int soundID) {
        main.update.updateConversationHandler.post(new UpdateSoundThread(main, soundID));
    }
}
