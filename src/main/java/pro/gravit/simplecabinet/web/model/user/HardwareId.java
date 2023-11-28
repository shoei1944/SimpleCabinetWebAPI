package pro.gravit.simplecabinet.web.model.user;

import javax.persistence.*;

@Entity(name = "HardwareId")
@Table(name = "hwids")
public class HardwareId {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hwids_generator")
    @SequenceGenerator(name = "hwids_generator", sequenceName = "hwids_seq", allocationSize = 1)
    private long id;

    private int bitness;
    private long totalMemory;
    private int logicalProcessors;
    private int physicalProcessors;
    private long processorMaxFreq;
    private boolean battery;
    private String hwDiskId;
    private byte[] displayId;
    private String baseboardSerialNumber;

    @Column(unique = true)
    private byte[] publicKey;
    private boolean banned;

    public long getId() {
        return id;
    }

    public int getBitness() {
        return bitness;
    }

    public void setBitness(int bitness) {
        this.bitness = bitness;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public int getLogicalProcessors() {
        return logicalProcessors;
    }

    public void setLogicalProcessors(int logicalProcessors) {
        this.logicalProcessors = logicalProcessors;
    }

    public int getPhysicalProcessors() {
        return physicalProcessors;
    }

    public void setPhysicalProcessors(int physicalProcessors) {
        this.physicalProcessors = physicalProcessors;
    }

    public long getProcessorMaxFreq() {
        return processorMaxFreq;
    }

    public void setProcessorMaxFreq(long processorMaxFreq) {
        this.processorMaxFreq = processorMaxFreq;
    }

    public boolean isBattery() {
        return battery;
    }

    public void setBattery(boolean battery) {
        this.battery = battery;
    }

    public String getHwDiskId() {
        return hwDiskId;
    }

    public void setHwDiskId(String hwDiskId) {
        this.hwDiskId = hwDiskId;
    }

    public byte[] getDisplayId() {
        return displayId;
    }

    public void setDisplayId(byte[] displayId) {
        this.displayId = displayId;
    }

    public String getBaseboardSerialNumber() {
        return baseboardSerialNumber;
    }

    public void setBaseboardSerialNumber(String baseboardSerialNumber) {
        this.baseboardSerialNumber = baseboardSerialNumber;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }
}
