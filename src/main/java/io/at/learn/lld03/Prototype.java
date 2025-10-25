package io.at.learn.lld03;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Prototype {

    public static void main(String[] args) {
        Document report1 = DocumentRegistry.getPrototype(DocumentRegistry.REPORT);
        report1.setContent("This is the report 1");
        report1.getSections().add("Report 1 Header");
        report1.getSections().add("Report 1 Subheader");

        Document report2 = DocumentRegistry.getPrototype(DocumentRegistry.REPORT);
        report2.setContent("This is the report 2");
        report2.getSections().add("Report 2 Header");
        report2.getSections().add("Report 2 Subheader");

        Document invoice1 = DocumentRegistry.getPrototype(DocumentRegistry.INVOICE);
        invoice1.setContent("This is the invoice 1");
        invoice1.getSections().add("Invoice 1 Header");
        invoice1.getSections().add("Invoice 1 Subheader");

        Document invoice2 = DocumentRegistry.getPrototype(DocumentRegistry.INVOICE);
        invoice2.setContent("This is the invoice 2");
        invoice2.getSections().add("Invoice 2 Header");
        invoice2.getSections().add("Invoice 2 Subheader");

        System.out.println("REPORT 1 : " + report1.toString());
        System.out.println("REPORT 2 : " + report2.toString());
        System.out.println("INVOICE 1 : " + invoice1.toString());
        System.out.println("INVOICE 2 : " + invoice2.toString());
    }

}

class DocumentRegistry {

    public static String REPORT = "REPORT";
    public static String INVOICE = "INVOICE";

    private static Map<String, Document> map = Map.of(
            REPORT, new Report(),
            INVOICE, new Invoice()
    );

    public static Document getPrototype(String type) {
        Document document = map.getOrDefault(type, null);
        if(document == null) throw new IllegalArgumentException("Type of document is invalid : " + type);
        return document.clone();
    }

}

class ProductCreation {

    static void expensiveTask(String className) {
        try {
            System.out.println("Initializing (expensive operation) : " + className);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

}

interface Document extends Cloneable {
    Document clone();
    void setContent(String content);
    String getContent();
    List<String> getSections();
    String getType();
    void setType(String type);
}

@Data
@AllArgsConstructor
class Report implements Document {

    private String content = "DEFAULT CONTENT";
    private String type = "REPORT";
    private List<String> sections;

    public Report() {
        this.content = "DEFAULT CONTENT";
        this.type = "REPORT";
        this.sections = new ArrayList<>();

        ProductCreation.expensiveTask(DocumentRegistry.REPORT);
    }

    @Override
    public Document clone() {
        try {
            Report cloned = (Report) super.clone();
            cloned.setContent(new String(this.content));
            cloned.setSections(new ArrayList<>(this.sections));
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }

}

@Data
@AllArgsConstructor
class Invoice implements Document {

    private String content = "DEFAULT CONTENT";
    private String type = "INVOICE";
    private List<String> sections;

    public Invoice() {
        this.content = "DEFAULT CONTENT";
        this.type = "INVOICE";
        this.sections = new ArrayList<>();

        ProductCreation.expensiveTask(DocumentRegistry.INVOICE);
    }

    @Override
    public Document clone() {
        try {
            Invoice cloned = (Invoice) super.clone();
            cloned.setContent(new String(this.content));
            cloned.setSections(new ArrayList<>(this.sections));
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }

}