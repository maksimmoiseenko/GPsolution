package model;

public class Hotel {


    private String price;
    private String name;
    private Address address;
    public Hotel(){};
    public Hotel(String price, String name, Address address) {
        this.price = price;
        this.name = name;
        this.address = address;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    @Override
    public String toString() {
        return "Hotel{" +
                "price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
