package io.at.learn.lld03;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class Prototype {

    public static void main(String[] args) {
        Document pod1 = new Report("This is the content", List.of("Header", "SubHeader"));

        Document pod2 = pod1.clone();
        pod2.setContent("This is the new Content");
        pod2.getSections().add(1, "List");

        System.out.println("POD 1 : " + pod1.toString());
        System.out.println("POD 2 : " + pod2.toString());
    }

}

interface Document extends Cloneable {
    Document clone();
    void setContent(String content);
    String getContent();
    List<String> getSections();
}

@Data
@AllArgsConstructor
class Report implements Document {

    private String content;
    private List<String> sections;

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