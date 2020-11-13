package Dijkstra;

import java.util.List;

public class CheckTable {
    private int tableSize;
    private int count;
    private boolean[] table;

    public CheckTable(int size) {
        this.tableSize = size;
        this.table = new boolean[this.tableSize];
        this.count = 0;
    }

    public void check(int idx) {
        if (this.table[idx] == false) {
            this.table[idx] = true;
            this.count += 1;
        }
        return;
    }

    public void check(List<Integer> arr) {
        for (int idx : arr) {
            check(idx);
        }
        return;
    }

    public boolean show(int idx) {
        return this.table[idx];
    }

    public int getUnchecked() {
        if (this.count != this.tableSize) {
            for (int i = this.tableSize - 1; i >= 0; i--) {
                if (!this.table[i]) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean isAllChecked() {
        if (this.count == this.tableSize) {
            return true;
        }
        return false;
    }

    public int getCount() {
        return count;
    }

    public int getTableSize() {
        return tableSize;
    }

    public boolean[] getTable() {
        return table;
    }

}
