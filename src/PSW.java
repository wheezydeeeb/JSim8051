public class PSW {
    private boolean[] flags = new boolean[8];

    public boolean getFlag(int index) { return flags[index]; }
    public void setFlag(int index, boolean value) { flags[index] = value; }

    public void reset() {
        flags = new boolean[8];
    }
}

