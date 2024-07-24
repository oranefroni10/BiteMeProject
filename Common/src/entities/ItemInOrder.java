package entities;

public class ItemInOrder extends Item {
	private String size=null;
	private String donenessDegree=null;
	private String restrictions=null;
	public ItemInOrder(int itemID, String name, Category type, float price, boolean customSize, boolean customDonenessDegree, boolean customRestrictions, String size, String donenessDegree,String restrictions) {
		super(itemID, name, type, price, customSize, customDonenessDegree, customRestrictions);
		if(customSize)
			this.size=size;
		if(customDonenessDegree)
			this.donenessDegree=donenessDegree;
		if(customRestrictions)
			this.restrictions=restrictions;
	}
    //size getter and setter
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    
    //donenessDegree getter and setter
    public String getDonenessDegree() { return donenessDegree; }
    public void setDonenessDegree(String donenessDegree) { this.donenessDegree = donenessDegree; }
    //restrictions getter and setter
    public String getRestrictions() { return restrictions; }
    public void setRestrictions(String restrictions) { this.restrictions = restrictions; }
}
