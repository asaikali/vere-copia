package com.example;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.shell.jline.PromptProvider;

@SpringBootApplication
public class VereCopiaTesterApplication implements PromptProvider
{

	public static void main(String[] args) 
	{
		new SpringApplicationBuilder(VereCopiaTesterApplication.class)
		  .web(WebApplicationType.NONE)
		  .build().run(args);
	}

    @Override
    public AttributedString getPrompt()
    {
        return new AttributedString("vere-copia:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.BLACK).background(AttributedStyle.BLACK));
    }
}
