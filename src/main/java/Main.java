import model.Address;
import model.Hotel;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void writeXml(List<Hotel> list) throws FileNotFoundException {
        File file = new File("src" + separ + "main" + separ + "resources" + separ + "Result.xml");
        PrintWriter pw = new PrintWriter(file);
        pw.println("<Lists>");
        pw.println("\t<Names>");
        list.forEach(hotel -> pw.println("\t\t<Name>" + hotel.getName() + "</Name>"));
        pw.println("\t</Names>");
        pw.println("\t<Prices>");
        list.forEach(hotel -> pw.println("\t\t<Price>" + hotel.getPrice() + "</Price>"));
        pw.println("\t</Prices>");
        pw.println("\t<Addresses>");
        list.forEach(hotel -> pw.println("\t\t<Address>" + hotel.getAddress().getAddressLine() + "</Address>"));
        pw.println("\t</Addresses>");
        pw.println("</Lists>");
        pw.close();
    }
    public final static String separ = File.separator;
    public static void main(String[] args) {
        File file = new File("src" + separ + "main" + separ + "resources" + separ + "Hotels.xml");
        DocumentBuilderFactory dbf  = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            List<Hotel> list = new ArrayList<>();
            doc = dbf.newDocumentBuilder().parse(file);
            // Забираем элемент Hotels
            Node listNode = doc.getDocumentElement();
            // Забираем все Hotel
            NodeList hotels = listNode.getChildNodes();
            //цикл по всем hotel
            for(int i = 0; i < hotels.getLength();i++){
                Node hotel = hotels.item(i);
                if (hotel.getNodeType() != Node.TEXT_NODE){
                    NodeList properties = hotel.getChildNodes();
                    Hotel hotelObj = new Hotel();
                    for(int j = 0; j < properties.getLength(); j++){
                        Node property = properties.item(j);
                        if(property.getNodeType() != Node.TEXT_NODE){
                            // сохраняем атрибут цена
                            hotelObj.setPrice(hotel.getAttributes().getNamedItem("Price").getTextContent());
                            if(property.getNodeName().equals("Name"))
                                hotelObj.setName(property.getTextContent());
                            if(property.getNodeName().equals("Address")){
                                NodeList addressProperties = property.getChildNodes();
                                hotelObj.setAddress(new Address(
                                        addressProperties.item(1).getTextContent(),
                                        addressProperties.item(3).getTextContent(),
                                        addressProperties.item(5).getTextContent(),
                                        addressProperties.item(7).getTextContent()));
                            }
                        }
                    }
                    list.add(hotelObj);
                }
            }
            //фильтрация
            list = list.stream().filter(hotel -> (hotel.getAddress().getState().contains("NY")
                    || hotel.getAddress().getState().contains("New York"))
                    && hotel.getName().contains("Hilton")).collect(Collectors.toList());

            Main.writeXml(list);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
