package frontend.SyntaxTree;

import java.util.ArrayList;

public class Block {
    private ArrayList<BlockItem> blockItems;

    public Block() {
        this.blockItems = new ArrayList<>();
    }

    public void addBlockItem(BlockItem blockItem) {
        this.blockItems.add(blockItem);
    }

    public boolean isContainReturn() {
        for (BlockItem blockItem : blockItems) {
            if (blockItem.isContainReturn()) {
                return true;
            }
        }
        return false;
    }

    public boolean isReturnHaveExp() {
        for (BlockItem blockItem : blockItems) {
            if (blockItem.isReturnHaveExp()) {
                return true;
            }
        }
        return false;
    }

    public boolean lastIsReturn() {
        if (blockItems.size() == 0) {
            return false;
        }
        return blockItems.get(blockItems.size() - 1).isContainReturn();
    }
}
