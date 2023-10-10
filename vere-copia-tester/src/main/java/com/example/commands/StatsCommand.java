package com.example.commands;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import com.example.exchange.VeraCopiaClient;
import com.example.printers.ProductSearchPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.core.instrument.MeterRegistry;

@ShellComponent
public class StatsCommand {
    
	@Autowired
	protected MeterRegistry registry;

    @Autowired
    protected ObjectMapper mapper;

	protected VeraCopiaClient client;

	protected ProductSearchPrinter productSearchPrinter;

    public StatsCommand(VeraCopiaClient client)
    {
        this.client = client;

        productSearchPrinter = new ProductSearchPrinter();

        // initialize the counters with the current inventory from the database
        client.searchR("*").doOnNext(prod ->
        {
            registry.counter("inventory.instock", "sku", String.valueOf(prod.getSku()))
            .increment(prod.getQuantity());
        }).subscribe();
    }

	@ShellMethod(value = "Displays the expected inventory count in the local store based on purchases and restocks made in this test app", key = "getCountedInventory")
	public String getCountedInventory()
	{
        var resp =  client.searchR("*").map(prod -> {
            int quanity = (int)registry.counter("inventory.instock", "sku", String.valueOf(prod.getSku())).count();

            prod.setQuantity(quanity);

            return prod;
        })
        .collectList().block();

        return productSearchPrinter.generateRecordsReport(resp); 
	}

	@ShellMethod(value = "Displays actual inventory count stored in the database", key = "getActualInventory")
	public String getActualInventory()
	{
        var resp =  client.search("*");

        return productSearchPrinter.generateRecordsReport(resp); 
	}

    protected String generateStatsReport()
    {
        final var stats = new Stats();

        // Get the actual inventory vs counted inventory

        client.search("*").stream().forEach(prod -> {
            final var inv = new Stats.Inventory();
            inv.setSku(prod.getSku());
            inv.setDbInv(prod.getQuantity());
            inv.setCountedInv((int)registry.counter("inventory.instock", "sku", String.valueOf(prod.getSku())).count());

            stats.getInventoryComparison().add(inv);
        });

        registry.find("vc-client.http.request").counters().forEach(counter -> 
        {

            final var target = new Stats.RequestTarget();
            target.setRequests((long)counter.count());
            target.setTarget(counter.getId().getTag("target")); 

            stats.getRequestTargets().add(target);
        });

        try{
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(stats);
        }
        catch (Exception e)
        {
            return "Error creating report: " + e.getMessage();
        }
    }

	@ShellMethod(value = "Prints captured stats in raw JSON format", key = "printStats")
	public String printStats()
    {
        return generateStatsReport();
    }

	@ShellMethod(value = "Exports captured stats to a local JSON fiole", key = "exportStats")
	public String exportStats()
	{


        try{
            final File jsonFile = new File("vere-copia-stats.json");
            if (!jsonFile.exists())
                jsonFile.createNewFile();

            FileUtils.writeStringToFile(jsonFile, generateStatsReport(), StandardCharsets.US_ASCII, false);
        }
        catch (Exception e) {}

        return "Stats written to file vere-copia-stats.json";

	}

}
