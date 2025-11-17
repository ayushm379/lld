package io.at.learn.lld04;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Bridge {
    public static void main(String[] args) {
        Device tv = new TV();
        Device radio = new Radio();

        RemoteController tvRemote = new AdvancedRemote(tv);
        RemoteController radioRemote = new BasicRemote(radio);

        tv.printStatus();
        tv.enable();
        tvRemote.volumeUp();
        tvRemote.volumeUp();
        tvRemote.volumeUp();
        tvRemote.channelUp();
        tvRemote.channelUp();
        tvRemote.channelUp();
        tvRemote.channelUp();
        tv.printStatus();
        tv.disable();

        radio.printStatus();
        radio.enable();
        radioRemote.volumeUp();
        radioRemote.volumeUp();
        radioRemote.channelUp();
        radioRemote.channelUp();
        radioRemote.channelUp();
        radio.printStatus();
        radio.disable();
    }
}

interface Device {
    boolean isEnabled();
    void enable();
    void disable();
    int getVolume();
    void setVolume(int percentage);
    int getChannel();
    void setChannel(int channel);
    void printStatus();
}

@Data
class TV implements Device {
    private boolean enabled = false;
    private int volume = 30;
    private int channel = 1;

    @Override public boolean isEnabled() { return enabled; }
    @Override public void enable() { enabled = true; }
    @Override public void disable() { enabled = false; }
    @Override public void printStatus() {
        System.out.printf("""
            TV Status:
                Enabled: %s
                Volume: %d
                Channel: %d
            *******************
        """, enabled, volume, channel);
    }
}

@Data
class Radio implements Device {
    private boolean enabled;
    private int volume = 20;
    private int channel = 90;

    @Override public boolean isEnabled() { return enabled; }
    @Override public void enable() { enabled = true; }
    @Override public void disable() { enabled = false; }
    @Override public void printStatus() {
        System.out.printf("""
            Radio Status:
                Enabled: %s
                Volume: %d
                Channel: %d
            *******************
        """, enabled, volume, channel);
    }
}

@AllArgsConstructor
abstract class RemoteController {
    // This is the bridge
    private Device device;

    public void volumeUp() { device.setVolume(Math.min(100, device.getVolume() + 5)); }
    public void volumeDown() { device.setVolume(Math.max(0, device.getVolume() - 5)); }
    public void channelDown() { device.setChannel(device.getChannel() - 1); }
    public void channelUp() {
        device.setChannel(device.getChannel() + 1);
    }
}

class BasicRemote extends RemoteController {
    BasicRemote(Device device) {
        super(device);
    }
}

class AdvancedRemote extends RemoteController {
    AdvancedRemote(Device device) {
        super(device);
    }
}