
public class Transaction {

	private String nameOnCard;
	
	private String cardNumber;
	
	private int id;
	
	private float price;
	
	private int quantity;
	
	private String expiryDate;
	
	private String createdBy;
	
	private float totalPrice;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNameOnCard() {
		return nameOnCard;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	
	
	
	public String toString(){
		
		String results = new String();
		
		results = "[id:" +id+",NameOnCard: " + nameOnCard +",CardNumber: " + cardNumber+",Price: "+price+",Quantity: "+quantity+",TotalPrice: "+totalPrice+",ExpiryDate: "+expiryDate
				+"CreatedBy: "+createdBy+"]";
		return results;

	}
	
	
}
