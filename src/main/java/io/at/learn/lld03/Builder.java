package io.at.learn.lld03;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

public class Builder {

    public static void main(String[] args) {
        Computer computer1 =  new Computer.ComputerBuilder("Intel i7", 8).hasGraphicCard(true).build();
        Computer computer2 =  new Computer.ComputerBuilder("AMD", 16).os("Mac").storage("256GB HDD").build();

        System.out.println("Computer 1 : " + computer1.toString());
        System.out.println("Computer 2 : " + computer2.toString());
    }

}

@Getter
@ToString
class Computer {
    private final String cpu;
    private final int ram;
    private final String storage;
    private final String os;
    private final boolean hasGraphicCard;

    public Computer(ComputerBuilder computerBuilder) {
        this.cpu = computerBuilder.getCpu();
        this.ram = computerBuilder.getRam();
        this.storage = computerBuilder.getStorage();
        this.os = computerBuilder.getOs();
        this.hasGraphicCard = computerBuilder.isHasGraphicCard();
    }

    @AllArgsConstructor
    @Getter
    public static class ComputerBuilder {
        private final String cpu;
        private final int ram;
        private String storage = "1TB SSD";
        private String os = "Windows";
        private boolean hasGraphicCard = false;

        public ComputerBuilder(String cpu, int ram) {
            if(ram < 0) {
                throw new IllegalArgumentException("Ram cannot be smaller than 0");
            }
            this.cpu = cpu;
            this.ram = ram;
        }

        public ComputerBuilder storage(String storage) {
            this.storage = storage;
            return this;
        }

        public ComputerBuilder os(String os) {
            this.os = os;
            return this;
        }

        public ComputerBuilder hasGraphicCard(boolean hasGraphicCard) {
            this.hasGraphicCard = hasGraphicCard;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }

}