package com.example.commands;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Stats 
{

    private List<Inventory> inventoryComparison;

    private List<RequestTarget> requestTargets;

    public Stats()
    {
        inventoryComparison = new ArrayList<>();
        requestTargets = new ArrayList<>();
    }

    @Data
    static class Inventory
    {
        private int sku;
        
        private int countedInv;

        private int dbInv;
    }

    @Data
    static class RequestTarget
    {
        private String target;

        private long requests;
    }
}
